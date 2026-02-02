package com.simplai.qa.model;

import java.util.HashMap;
import java.util.Map;

public class ExecuteRequest {

    private String app_id;
    private Map<String, Integer> inputs = new HashMap<>();

    public ExecuteRequest(int number1, int number2) {
        this.app_id = "6974be035483f616e563dc4a";
        inputs.put("number_1", number1);
        inputs.put("number_2", number2);
    }

    // 🔹 Jackson needs getters

    public String getApp_id() {
        return app_id;
    }

    public Map<String, Integer> getInputs() {
        return inputs;
    }
}
