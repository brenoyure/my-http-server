package br.albatross.myhttpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class MyHttpServer implements MyServer {

    private static Logger log = Logger.getLogger(MyHttpServer.class.getName());
    private static final byte FIXED_THREAD_POOL_SIZE = 2;
    private static final AtomicInteger threadsInUse = new AtomicInteger(0);

    private final ServerSocket serverSocket;
    private final ExecutorService executorService;
    private final SocketClientConnectionHandler clientConnectionHandler;
    private final long clientConnectionTimeout;

    public MyHttpServer(ServerSocket serverSocket, 
                        ExecutorService executorService, 
                        SocketClientConnectionHandler clientConnectionHandler,
                        long clientConnectionTimeout) {
        this.serverSocket = serverSocket;
        this.executorService = executorService;
        this.clientConnectionHandler = clientConnectionHandler;
        this.clientConnectionTimeout = clientConnectionTimeout;
    }

    @Override
    public boolean isRunning() {
        return serverSocket.isBound() && !serverSocket.isClosed();
    }

    @Override
    public void start() {
        log.info("MyHttpServer started on port: " + serverSocket.getLocalPort());
        while (isRunning()) {
            if (threadsInUse.get() < FIXED_THREAD_POOL_SIZE) {
                executorService.submit(() -> {
                    try {
                        threadsInUse.incrementAndGet();
                        Socket clientSocket = serverSocket.accept();
                        clientSocket.setSoTimeout((int) this.clientConnectionTimeout);
                        clientConnectionHandler.handle(clientSocket);
                        threadsInUse.decrementAndGet();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    @Override
    public void stop() {
        synchronized (this) {
            if (isRunning()) {
                try {
                    serverSocket.close();
                } catch(IOException e) {
                    log.severe("An error occurred when trying to stop the internal ServerSocket, see the StackTrace for details");
                    e.printStackTrace();
                }
            } else {
                if (serverSocket.isClosed()) {
                    log.warning("The internal serverSocket is already closed");
                } else {
                    log.warning("There is probally a request running trying to shutdown the internal serverSocket");
                }
            }
        }
    }

    @Override
    public long connectionTimeout() {
        return this.clientConnectionTimeout;
    }

}
