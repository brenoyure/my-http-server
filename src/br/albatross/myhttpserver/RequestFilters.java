package br.albatross.myhttpserver;

import java.net.ServerSocket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class RequestFilters {

    private static List<MyServerRequestFilter> filters;

    public static Iterable<MyServerRequestFilter> getThen(ServerSocket serverSocket) {
        if (filters == null) {
            filters = new LinkedList<>();
        }

        filters
          .add(new ServerShutdownRequestFilter(serverSocket));

        return Collections.unmodifiableList(filters);
    }
    
}
