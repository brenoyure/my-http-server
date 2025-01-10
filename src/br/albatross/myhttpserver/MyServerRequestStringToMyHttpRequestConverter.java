package br.albatross.myhttpserver;

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
