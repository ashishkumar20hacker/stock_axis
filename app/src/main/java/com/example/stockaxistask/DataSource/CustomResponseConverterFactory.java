package com.example.stockaxistask.DataSource;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class CustomResponseConverterFactory extends Converter.Factory {
    private final Gson gson;

    public CustomResponseConverterFactory(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new CustomResponseConverter<>(gson, adapter);
    }
}

class CustomResponseConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    CustomResponseConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response;
        try {
            response = value.string();
            // Use regular expression to extract JSON object or array from the response
            Pattern pattern = Pattern.compile("\\{(?:[^{}]|\\{[^{}]*\\})*\\}|\\[(?:[^\\[\\]]|\\[[^\\[\\]]*\\])*\\]", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(response);
            String jsonPart = null;
            if (matcher.find()) {
                jsonPart = matcher.group();
            }

            if (jsonPart != null) {
                Log.d("CustomResponseConverter", "Extracted JSON: " + jsonPart);
                try {
                    return adapter.fromJson(jsonPart);
                } catch (JsonSyntaxException e) {
                    Log.e("CustomResponseConverter", "JsonSyntaxException: " + e.getMessage());
                    throw new IOException("Error parsing JSON: " + e.getMessage(), e);
                }
            } else {
                throw new IOException("No valid JSON found in response: " + response);
            }
        } catch (IOException e) {
            Log.e("CustomResponseConverter", "Error reading response body", e);
            throw e;
        } finally {
            value.close();
        }
    }
}
