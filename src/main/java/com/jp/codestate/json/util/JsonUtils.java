package com.jp.codestate.json.util;

import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

public class JsonUtils {

    public static String getFieldFromJson(final String url, final String field) {

        String returnedField = null;

        try {

            final String response = requestProcessedData(url);
            returnedField = new JSONObject(response).getString(field);
        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("[ERROR] : [CUSTOM_LOG] : " + e);
        }

        if (returnedField == null) {
            returnedField = "No match found";
        }

        return returnedField;
    } // end static String getFieldFromJson(final String url, final String field)

    public static String requestProcessedData(final String url) {

        RestTemplate request = new RestTemplate();
        String result = request.getForObject(url, String.class);
        System.out.println(url);
        return (result);
    } // end static String requestProcessedData(String url)
} // end class JsonUtils
