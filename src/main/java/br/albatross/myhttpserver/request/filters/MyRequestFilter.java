package br.albatross.myhttpserver.request.filters;

import java.io.InputStream;

public interface MyRequestFilter {

    void doFilter(InputStream requestStream);

}
