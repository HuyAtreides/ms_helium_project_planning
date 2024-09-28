package app.helium.projectplanning.test.blackbox;

import static app.helium.projectplanning.test.shared.file.FileReader.readJsonFromFile;
import static io.restassured.RestAssured.given;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

import app.helium.projectplanning.infra.api.rest.controller.ApiEndPoint;
import app.helium.projectplanning.test.shared.constant.CommonTestConstant;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlConfig.TransactionMode;

@BlackBoxTest
public class CreateSprintBlackBoxTest {
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
    void sprint_should_be_created_when_call_api_with_valid_payload() throws JSONException {
        String response = given()
                .basePath(contextPath)
                .body(readJsonFromFile("api/create_sprint/valid_request.json"))
                .pathParam("project_id", CommonTestConstant.DEFAULT_TEST_PROJECT_ID)
                .contentType(ContentType.JSON)
                .when()
                .post(ApiEndPoint.CREATE_NEW_SPRINT)
                .then()
                .statusCode(201)
                .and().extract().body().asString();
        String expectedResponse = readJsonFromFile("api/create_sprint/response.json");

        assertEquals(expectedResponse, response, JSONCompareMode.LENIENT);
    }

}
