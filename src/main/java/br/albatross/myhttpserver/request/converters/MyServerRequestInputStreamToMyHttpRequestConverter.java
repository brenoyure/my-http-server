package br.albatross.myhttpserver.request.converters;

import java.io.InputStream;

import br.albatross.myhttpserver.request.MyHttpRequest;

public class MyServerRequestInputStreamToMyHttpRequestConverter implements MyServerRequestConverter<InputStream, MyHttpRequest> {

    private final MyServerRequestConverter<InputStream, String> inputStreamToStringConverter;
    private final MyServerRequestConverter<String, MyHttpRequest> stringToHttpRequestConverter;

    public MyServerRequestInputStreamToMyHttpRequestConverter(
            MyServerRequestConverter<InputStream, String> inputStreamToStringConverter,
            MyServerRequestConverter<String, MyHttpRequest> stringToHttpRequestConverter) {
        this.inputStreamToStringConverter = inputStreamToStringConverter;
        this.stringToHttpRequestConverter = stringToHttpRequestConverter;
    }

    @Override
    public MyHttpRequest convert(InputStream inputType) {
        String requestString = inputStreamToStringConverter.convert(inputType);
        MyHttpRequest httpRequest = stringToHttpRequestConverter.convert(requestString);

        return httpRequest;
    }

}
