package br.albatross.myhttpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerShutdownRequestFilter implements MyServerRequestFilter {

    private final ServerSocket serverSocket;
    private static final Logger log = Logger.getLogger(ServerShutdownRequestFilter.class.getName());;

    public ServerShutdownRequestFilter(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void doFilter(InputStream requestStream) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(requestStream))) {
            String requestTextBodyFirstLine = null; {
                try {
                    requestTextBodyFirstLine = br.readLine();
                    if (requestTextBodyFirstLine.equals("shutdown-my-http-server")) {
                        synchronized (this) {
                            if (serverSocket.isBound() && !serverSocket.isClosed()) {
                                try {
                                    serverSocket.close();
                                } catch (IOException e) {
                                    log.log(Level.WARNING,
                                            "An error ocurred when trying to Shutdown the server via shutdown command");
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    log.log(Level.WARNING, "An error ocurred when trying to read the request");
                    throw new RuntimeException(e);
                }
            }
        } catch(IOException e) {
            log.log(Level.WARNING, "An error ocurred when trying to closed the BufferedReader");
            throw new RuntimeException(e);
        }
    }

}
