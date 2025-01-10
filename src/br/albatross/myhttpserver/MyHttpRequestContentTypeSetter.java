package br.albatross.myhttpserver;

import java.util.regex.Pattern;

public class MyHttpRequestContentTypeSetter implements MyHttpRequestSetter {

    private static final String CONTENT_TYPE_REGEX = "(?:Content-Type\\:\\s)(?<ContentType>.+)";
    private static final String CONTENT_TYPE_CAPTURE_GROUP = "ContentType";

    private static final Pattern pattern = Pattern.compile(CONTENT_TYPE_REGEX);

    @Override
    public void setRequestField(String request, MyHttpRequest myHttpRequest) {
        myHttpRequest.setContentType(pattern.matcher(request).group(CONTENT_TYPE_CAPTURE_GROUP));
    }

}
