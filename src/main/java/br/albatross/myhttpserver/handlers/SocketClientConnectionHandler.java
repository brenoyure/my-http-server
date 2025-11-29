package br.albatross.myhttpserver.handlers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.Socket;
import java.net.URL;

import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import br.albatross.myhttpserver.request.MyHttpRequest;
import br.albatross.myhttpserver.request.converters.MyServerRequestConverter;
import br.albatross.myhttpserver.response.MyHttpResponse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SocketClientConnectionHandler {

    private static final Logger log = Logger.getLogger(SocketClientConnectionHandler.class.getName());

    private final MyServerRequestConverter<InputStream, MyHttpRequest> requestConverter;
    private final Map<String, String> cachedResource = new ConcurrentHashMap<>();

    public SocketClientConnectionHandler(
            MyServerRequestConverter<InputStream, MyHttpRequest> requestConverter) {
        this.requestConverter = requestConverter;
    }

    public void handle(Socket clientSocket) {

        log.info("Recebido de: " + clientSocket.getRemoteSocketAddress());
        MyHttpRequest httpRequest = null;
        MyHttpResponse httpResponse = null;
        try(InputStream clientInputStream = clientSocket.getInputStream();
            OutputStream clientOutputStream = clientSocket.getOutputStream()) {
            httpRequest = requestConverter.convert(clientInputStream);
            httpResponse = new MyHttpResponse("HTTP/1.1", 
                                              200, 
                                              "OK", 
                                              "text/plain", 
                                              null);

            URL webResource = getClass().getClassLoader().getResource(httpRequest.getUri().replaceFirst("/", ""));
            if (webResource == null) {
                httpResponse.setCode(404);
                httpResponse.setStatus("NOT FOUND");
                httpResponse.setContentType("text/html; charset=UTF-8");
                httpResponse.setBody("<html><h1>404 - NOT FOUND</h1></html>");
                writeHttpResponseToClientSocketOutputStream(clientOutputStream, httpResponse);
                return;
            }

            String requestedResource = cachedResource.get( httpRequest.getUri() );

            if (requestedResource != null) {
                httpResponse.setBody(requestedResource);
                setContentTypeAndSendResponseToClient(httpRequest, httpResponse, clientOutputStream);
                return;
            }

            try (InputStream webResourceStream = webResource.openStream()) {
                byte[] bff = new byte[1024];
                int length = bff.length;
                int readBytes = webResourceStream.read(bff, 0, length);

                if (readBytes < length) {
                    httpResponse.setBody(new String(bff, 0, readBytes, StandardCharsets.UTF_8));
                    cachedResource.put(httpRequest.getUri(), httpResponse.getBody());

                    setContentTypeAndSendResponseToClient(httpRequest, httpResponse, clientOutputStream);
                    return;
                }

                StringBuilder sb = new StringBuilder();
                while (readBytes != -1) {
                    sb.append(new String(bff, 0, readBytes, StandardCharsets.UTF_8));
                    readBytes = webResourceStream.read(bff, 0, readBytes);
                }

                httpResponse.setBody(sb.toString());
                cachedResource.put(httpRequest.getUri(), httpResponse.getBody());

                setContentTypeAndSendResponseToClient(httpRequest, httpResponse, clientOutputStream);

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void setContentTypeAndSendResponseToClient(MyHttpRequest httpRequest, MyHttpResponse httpResponse, OutputStream clientOutputStream) {
        setResponseContentType(httpRequest, httpResponse);
        writeHttpResponseToClientSocketOutputStream(clientOutputStream, httpResponse);
    }

    void writeHttpResponseToClientSocketOutputStream(OutputStream clientOutputStream, MyHttpResponse httpResponse) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(httpResponse.toClientResponse())) {
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
    }

    void setResponseContentType(MyHttpRequest httpRequest, MyHttpResponse httpResponse) {
        Pattern pattern = Pattern.compile("\\.(?<extension>\\w+)$");
        Matcher matcher = pattern.matcher(httpRequest.getUri());

        if (matcher.find()) {
            String extension = matcher.group("extension");
            if (extension.equals("js")) {
                extension = "javascript";
            }
            httpResponse.setContentType("text/" + extension);
        }

    }

}
