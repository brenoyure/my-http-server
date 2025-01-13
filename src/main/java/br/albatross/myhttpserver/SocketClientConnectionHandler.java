package br.albatross.myhttpserver;

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
        try (OutputStream clientOutputStream = clientSocket.getOutputStream()) {
            log.info("Recebido de: " + clientSocket.getRemoteSocketAddress());
            MyHttpRequest httpRequest = requestConverter.convert(clientSocket.getInputStream());
            System.out.println(httpRequest.getMethod());
            System.out.println(httpRequest.getUri());
            System.out.println(httpRequest.getHttpVersion());
            System.out.println(httpRequest.getContentType());
            System.out.println(httpRequest.getContentLength());
            System.out.println(httpRequest.getBody());

            clientOutputStream.write(
                    new MyHttpResponse(
                            "HTTP/1.1", 
                            200, 
                            "OK", 
                            "text/html", 
                            httpRequest.getBody().concat(" --- Modified by br.albatross.MyHttpServer xD")).toClientResponse());

           } catch (IOException e) { throw new RuntimeException(e); }

    }

}
