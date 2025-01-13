package br.albatross.myhttpserver;

import java.io.InputStream;

public interface MyServerRequestFilter {

    void doFilter(InputStream requestStream);

}
