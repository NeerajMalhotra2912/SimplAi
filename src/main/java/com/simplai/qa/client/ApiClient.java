package com.simplai.qa.client;

import com.simplai.qa.config.ConfigManager;
import com.simplai.qa.model.ExecuteRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ApiClient {

    public static Response executeTool(ExecuteRequest request) {

        return given()
                .baseUri(ConfigManager.get("base.url"))
                .header("X-DEVICE-ID", ConfigManager.get("device.id"))
                .header("PIM-SID", ConfigManager.get("pim.sid"))
                .contentType(ContentType.JSON)
                .body(request)
                .log().all()
                .when()
                .post("/interact/api/v2/tool/execute")
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static Response getExecutionStatus(String executionId) {

        return given()
                .baseUri(ConfigManager.get("base.url"))
                .header("X-DEVICE-ID", ConfigManager.get("device.id"))
                .header("PIM-SID", ConfigManager.get("pim.sid"))
                .contentType(ContentType.JSON)
                .when()
                .get("/interact/api/v2/tool/executions/" + executionId + "/status")
                .then()
                .log().all()
                .extract()
                .response();
    }
}
