package br.albatross.myhttpserver;

import java.io.InputStream;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.albatross.myhttpserver.handlers.SocketClientConnectionHandler;
import br.albatross.myhttpserver.request.MyHttpRequest;
import br.albatross.myhttpserver.request.converters.MyServerRequestConverter;
import br.albatross.myhttpserver.request.converters.MyServerRequestInputStreamToMyHttpRequestConverter;
import br.albatross.myhttpserver.request.converters.MyServerRequestInputStreamtoStringConverter;
import br.albatross.myhttpserver.request.converters.MyServerRequestStringToMyHttpRequestConverter;
import br.albatross.myhttpserver.request.setters.MyHttpRequestSetters;

public class Main {

    private static final byte FIXED_THREAD_POOL_SIZE = 5;
    private static final short CLIENT_SOCKET_TIMEOUT = 10000;
    private static final ExecutorService SERVER_THREAD_POOL = Executors.newFixedThreadPool(FIXED_THREAD_POOL_SIZE);

    public static void main(String[] args) throws Exception {

        MyServerRequestConverter<InputStream, String> inputStreamToStringConverter = new MyServerRequestInputStreamtoStringConverter();
        MyServerRequestConverter<String, MyHttpRequest> stringToHttpRequestConverter = new MyServerRequestStringToMyHttpRequestConverter(MyHttpRequestSetters.getSetters());

        MyServerRequestConverter<InputStream, MyHttpRequest> inputStreamToHttpRequestConverter = 
                new MyServerRequestInputStreamToMyHttpRequestConverter(inputStreamToStringConverter, stringToHttpRequestConverter);

        SocketClientConnectionHandler clientConnectionHandler = 
                new SocketClientConnectionHandler(inputStreamToHttpRequestConverter);

        MyServer myHttpServer = 
                new MyHttpServer(new ServerSocket(8080), 
                                 SERVER_THREAD_POOL, 
                                 clientConnectionHandler,
                                 CLIENT_SOCKET_TIMEOUT);
        myHttpServer.start();
    }

}
