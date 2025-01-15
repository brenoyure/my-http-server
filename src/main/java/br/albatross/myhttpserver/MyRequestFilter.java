package br.albatross.myhttpserver;

import java.io.InputStream;

public interface MyRequestFilter {

    void doFilter(InputStream requestStream);

}
