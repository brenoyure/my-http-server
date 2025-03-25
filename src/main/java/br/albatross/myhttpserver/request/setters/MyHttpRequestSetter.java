package br.albatross.myhttpserver.request.setters;

import br.albatross.myhttpserver.request.MyHttpRequest;

public interface MyHttpRequestSetter {

    void setRequestField(String request, MyHttpRequest myHttpRequest);

}
