package br.albatross.myhttpserver;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Logger;

public class SocketClientConnectionHandler {

    private static final Logger log = Logger.getLogger(SocketClientConnectionHandler.class.getName());

    private final MyServerRequestConverter<InputStream, MyHttpRequest> requestConverter;

    public SocketClientConnectionHandler(
            MyServerRequestConverter<InputStream, MyHttpRequest> requestConverter) {
        this.requestConverter = requestConverter;
    }

    public void handle(Socket clientSocket) {
        
            log.info("Recebido de: " + clientSocket.getRemoteSocketAddress());
            MyHttpRequest httpRequest = null;            
            try(InputStream clientInputStream = clientSocket.getInputStream();
                OutputStream clientOutputStream = clientSocket.getOutputStream()) {
                httpRequest = requestConverter.convert(clientInputStream);
                MyHttpResponse httpResponse = new MyHttpResponse(
                        "HTTP/1.1", 
                        200, 
                        "OK", 
                        httpRequest.getAccept(), 
                        httpRequest.getBody());

                byte[] responseByteArray = httpResponse.toClientResponse();

                try (ByteArrayInputStream bis = new ByteArrayInputStream(responseByteArray)) {
                    byte[] bff = new byte[1024];
                    int length = bff.length;
                
                    int readBytes = bis.read(bff, 0, length);
                    clientOutputStream.write(bff, 0, readBytes);
                    while (readBytes != -1 && readBytes == length) {
                        readBytes = bis.read(bff, 0, readBytes);
                        clientOutputStream.write(bff, 0, readBytes);
                    }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
