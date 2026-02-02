package com.simplai.qa.utils;

import com.simplai.qa.client.ApiClient;
import com.simplai.qa.config.ConfigManager;
import io.restassured.response.Response;

public class PollingUtil {

    public static Response pollUntilTerminalState(String executionId)
            throws InterruptedException {

        int timeout = Integer.parseInt(ConfigManager.get("poll.timeout"));
        int interval = Integer.parseInt(ConfigManager.get("poll.interval"));
        int elapsedTime = 0;

        while (elapsedTime < timeout) {

            Response response = ApiClient.getExecutionStatus(executionId);
            String status = response.jsonPath().getString("status");

            if (status == null) {
                throw new AssertionError("Status field is missing in response");
            }

            if ("COMPLETED".equalsIgnoreCase(status)
                    || "FAILED".equalsIgnoreCase(status)) {
                return response;
            }

            Thread.sleep(interval * 1000L);
            elapsedTime += interval;
        }

        throw new RuntimeException(
                "Execution stuck in non-terminal state after "
                        + timeout + " seconds"
        );
    }
}
