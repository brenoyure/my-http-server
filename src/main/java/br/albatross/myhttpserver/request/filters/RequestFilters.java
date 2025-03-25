package br.albatross.myhttpserver.request.filters;

import java.net.ServerSocket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class RequestFilters {

    private static List<MyRequestFilter> filters;

    public static Iterable<MyRequestFilter> getThen(ServerSocket serverSocket) {
        if (filters == null) {
            filters = new LinkedList<>();
        }

        filters
          .add(new ServerShutdownRequestFilter(serverSocket));

        return Collections.unmodifiableList(filters);
    }
    
}
