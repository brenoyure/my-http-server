package br.albatross.myhttpserver.request.converters;

import br.albatross.myhttpserver.request.MyHttpRequest;
import br.albatross.myhttpserver.request.setters.MyHttpRequestSetter;

public class MyServerRequestStringToMyHttpRequestConverter implements MyServerRequestConverter<String, MyHttpRequest> {

    private final Iterable<MyHttpRequestSetter> fieldSetters;

    public MyServerRequestStringToMyHttpRequestConverter(Iterable<MyHttpRequestSetter> fieldSetters) {
        this.fieldSetters = fieldSetters;
    }

    @Override
    public MyHttpRequest convert(String plainTextRequest) {
        MyHttpRequest httpRequest = new MyHttpRequest();
        fieldSetters.forEach(fieldSetter -> fieldSetter.setRequestField(plainTextRequest, httpRequest));
        return httpRequest;
    }

}
