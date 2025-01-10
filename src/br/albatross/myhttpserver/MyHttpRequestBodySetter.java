package br.albatross.myhttpserver;

import java.util.regex.Pattern;

public class MyHttpRequestBodySetter implements MyHttpRequestSetter {

    private static final String BODY_CAPTURE_GROUP = "Body";
    private static final String BODY_REGEX = "\\w+\\:\\s.+(?:\\n{2})(?<Body>(.|\\s)*)";
    private static final Pattern pattern = Pattern.compile(BODY_CAPTURE_GROUP);    

    @Override
    public void setRequestField(String request, MyHttpRequest myHttpRequest) {
        myHttpRequest.setBody(pattern.matcher(request).group(BODY_REGEX));
    }

}
