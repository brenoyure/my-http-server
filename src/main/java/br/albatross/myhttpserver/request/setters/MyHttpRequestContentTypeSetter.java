package br.albatross.myhttpserver.request.setters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.albatross.myhttpserver.request.MyHttpRequest;

public class MyHttpRequestContentTypeSetter implements MyHttpRequestSetter {

    private static final String CONTENT_TYPE_REGEX = "(?:Content-Type\\:\\s)(?<ContentType>.+)";
    private static final String CONTENT_TYPE_CAPTURE_GROUP = "ContentType";

    private static final Pattern pattern = Pattern.compile(CONTENT_TYPE_REGEX);

    @Override
    public void setRequestField(String request, MyHttpRequest myHttpRequest) {
        Matcher requestMatcher = pattern.matcher(request);
        if (requestMatcher.find()) {
            myHttpRequest.setContentType(requestMatcher.group(CONTENT_TYPE_CAPTURE_GROUP));
        }
        
    }

}
