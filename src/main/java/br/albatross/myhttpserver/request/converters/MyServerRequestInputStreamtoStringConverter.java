package br.albatross.myhttpserver.request.converters;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class MyServerRequestInputStreamtoStringConverter implements MyServerRequestConverter<InputStream, String> {

    private static final int BYTE_BUFFER_DEFAULT_SIZE = 1024;

    public String convert(InputStream inputStream) {
        byte[] bff = new byte[BYTE_BUFFER_DEFAULT_SIZE];
        int off = 0;
        int length = bff.length;

        int readBytes = 0;
        try {
            readBytes = inputStream.read(bff, off, length);
        } catch (IOException e) {
            System.err.println("An error occurred when first trying to read client socket inputstream");
            throw new RuntimeException(e);
        }

        if (readBytes < length) {
            return new String(bff, off, readBytes, StandardCharsets.UTF_8);
        }

        StringBuilder sb = new StringBuilder();        
        sb.append(new String(bff, StandardCharsets.UTF_8));

        while (readBytes != -1 && readBytes == length) {
            try {
                readBytes = inputStream.read(bff, off, readBytes);
                sb.append(new String(bff, off, readBytes, StandardCharsets.UTF_8));
            } catch (IOException e) {
                System.err.println("An error occurred when reading client socket InputStream");
                throw new RuntimeException(e);                
            }
        }

        return sb.toString();

    }

}
