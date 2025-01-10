package br.albatross.myhttpserver;

import java.io.InputStream;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static final byte FIXED_THREAD_POOL_SIZE = 2;
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
