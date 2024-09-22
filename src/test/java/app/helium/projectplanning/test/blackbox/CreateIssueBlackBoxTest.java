package app.helium.projectplanning.test.blackbox;

import static app.helium.projectplanning.test.shared.file.FileReader.readJsonFromFile;
import static io.restassured.RestAssured.given;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

import app.helium.projectplanning.infra.api.rest.controller.ApiEndPoint;
import io.restassured.http.ContentType;
import java.util.UUID;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlConfig.TransactionMode;

@BlackBoxTest
public class CreateIssueBlackBoxTest {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Test
    @Sql(
            scripts = {
                    "classpath:/sql_scripts/clear_all_tables.sql",
                    "classpath:/sql_scripts/insert_project.sql"
            },
            config = @SqlConfig(transactionMode = TransactionMode.ISOLATED)
    )
    void issue_should_be_created_when_call_api_with_valid_payload() throws JSONException {
        String response = given()
                .basePath(contextPath)
                .body(readJsonFromFile("api/create_issue/valid_request.json"))
                .pathParam("project_id", UUID.fromString("1d6846ce-0a75-4a39-a49e-dea0d4be8b40"))
                .contentType(ContentType.JSON)
                .when()
                .post(ApiEndPoint.CREATE_NEW_ISSUE)
                .then()
                .statusCode(200)
                .and().extract().body().asString();
        String expectedResponse = readJsonFromFile("api/create_issue/response.json");

        assertEquals(expectedResponse, response, JSONCompareMode.LENIENT);
    }
}
