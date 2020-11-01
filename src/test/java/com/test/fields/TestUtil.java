package com.test.fields;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TestUtil {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T getResource(String resourcePath, Class<T> clazz) {
        try {
            return objectMapper.readValue(getContents(resourcePath), clazz);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String getContents(String resourceFilePath) {
        try {
            InputStream inputStream = Objects.requireNonNull(TestUtil.class.getClassLoader().getResourceAsStream(resourceFilePath));
            return FileCopyUtils.copyToString(new InputStreamReader(inputStream));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

}
