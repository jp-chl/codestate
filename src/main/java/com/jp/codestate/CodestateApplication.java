package com.jp.codestate;

import com.jp.codestate.json.util.JsonUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class CodestateApplication {

    public static final String serverUrl = "http://localhost:9090";

    public static void main(String[] args) {
        SpringApplication.run(CodestateApplication.class, args);
    }

    public static String requestProcessedData(String url) {

        RestTemplate request = new RestTemplate();
        String result = request.getForObject(url, String.class);
        System.out.println(url);
        return (result);
    } // end static String requestProcessedData(String url)

    @GetMapping("/")
    public static String hello() {
        return "HELLO I'M YOUR CONVERTOR";
    } // end static String hello()

    @GetMapping("/codeToState")
    public static String codeToState(String code) {

        String state = null;

        try {

            final String response = requestProcessedData(serverUrl+"/readDataForCode");
            state = new JSONObject(response).getString(code.toUpperCase());
        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("[ERROR] : [CUSTOM_LOG] : " + e);
        }

        if (state == null) {
            state = "No match found";
        }

        return state;
    } // end static String codeToState(String code)

    @GetMapping("/stateToCode")
    public static String stateToCode(String state) {

        String value = null;

        try {

            final String response = requestProcessedData(serverUrl + "/readDataForState");
            JSONArray jsonArray = new JSONArray(response);

            for(int n=0; n<jsonArray.length(); n++) {

                JSONObject object = jsonArray.getJSONObject(n);
                String name = object.getString("name");

                if (state.equalsIgnoreCase(name)) {
                    value = object.getString("abbreviation");
                    break;
                }
            } // end for json array iteration
        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("[ERROR] : [CUSTOM_LOG] : " + e);
        }

        if (value == null) {
            value = "No match found";
        }

        return value;
    } // end static String stateToCode(String state)
} // end class CodestateApplication
