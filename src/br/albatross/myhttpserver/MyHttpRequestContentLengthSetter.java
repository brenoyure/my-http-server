package br.albatross.myhttpserver;

import java.util.logging.Logger;
import java.util.regex.Pattern;

public class MyHttpRequestContentLengthSetter implements MyHttpRequestSetter {

    private static final String CONTENT_LENGTH_REGEX = "(?:Content-Length\\:\\s)(?<ContentLength>.+)";
    private static final String CONTENT_LENGTH_CAPTURE_GROUP = "ContentLength";
    private static final Pattern pattern = Pattern.compile(CONTENT_LENGTH_REGEX);

    private static Logger log = 
            Logger.getLogger(MyHttpRequestContentLengthSetter.class.getName());

    @Override
    public void setRequestField(String request, MyHttpRequest myHttpRequest) {
        String contentLength = pattern.matcher(request).group(CONTENT_LENGTH_CAPTURE_GROUP);
        if (contentLength != null && !contentLength.isBlank()) {
            try {
                myHttpRequest.setContentLength(Integer.parseInt(contentLength));
            } catch (NumberFormatException e) {
                myHttpRequest.setContentLength(0);
                log.warning("An error ocurred when trying to set the Content Length header value, setting it to 0");
                e.printStackTrace();
            }
        }
        
    }

}
