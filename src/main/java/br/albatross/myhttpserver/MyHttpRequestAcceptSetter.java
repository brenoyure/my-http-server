package br.albatross.myhttpserver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyHttpRequestAcceptSetter implements MyHttpRequestSetter {

    private static final String ACCEPT_HEADER_REGEX = "(?:Accept\\:\\s)(?<Accept>.+)";
    private static final String ACCEPT_HEADER_CAPTURE_GROUP = "Accept";

    private static final Pattern pattern = Pattern.compile(ACCEPT_HEADER_REGEX);

    @Override
    public void setRequestField(String request, MyHttpRequest myHttpRequest) {
        Matcher requestMatcher = pattern.matcher(request);
        if (requestMatcher.find()) {
            myHttpRequest.setAccept(requestMatcher.group(ACCEPT_HEADER_CAPTURE_GROUP));
        }
        
    }

}
