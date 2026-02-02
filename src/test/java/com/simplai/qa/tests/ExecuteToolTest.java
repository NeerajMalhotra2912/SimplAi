package com.simplai.qa.tests;

import com.simplai.qa.base.BaseTest;
import com.simplai.qa.client.ApiClient;
import com.simplai.qa.model.ExecuteRequest;
import com.simplai.qa.utils.PollingUtil;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ExecuteToolTest extends BaseTest {

    @Test
    public void testExecuteToolHappyPath() throws InterruptedException {

        // Step 1: Trigger execution
        ExecuteRequest request = new ExecuteRequest(1, 2);
        Response executeResponse = ApiClient.executeTool(request);

        // Step 2: Validate execute API response
        executeResponse.then().statusCode(200);

        String executionId =
                executeResponse.jsonPath().getString("execution_id");

        Assert.assertNotNull(executionId,
                "execution_id should not be null");

        // Step 3: Poll status API until terminal state
        Response finalStatusResponse =
                PollingUtil.pollUntilTerminalState(executionId);

        // Step 4: Validate final status
        String finalStatus =
                finalStatusResponse.jsonPath().getString("status");

        Assert.assertEquals(finalStatus, "COMPLETED",
                "Execution should be COMPLETED");
    }
}
