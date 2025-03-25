package br.albatross.myhttpserver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyHttpRequestBodySetter implements MyHttpRequestSetter {

    private static final String REGEX = 
            "\\w+\\:\\s*.+\\s{3,}(?<Body>(.*\\s*\\n*)+)";

    private static final Pattern pattern = Pattern.compile(REGEX);

    private static final String GROUP_NAME = "Body";

    @Override
    public void setRequestField(String request, MyHttpRequest myHttpRequest) {
        Matcher matcher = pattern.matcher(request);
        if (matcher.find()) {
            myHttpRequest.setBody(matcher.group(GROUP_NAME));
        }

    }

}
