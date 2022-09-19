package TestModel;

import TestModel.RestClient;
import io.restassured.response.Response;

public class UserClient extends RestClient {
    private final String REGISTER = "/auth/register";
    private final String LOGIN = "/auth/login";
    private final String DELETE = "/auth/user?={user}";

    public Response registerNewUser(UserModel user) {
        return reqSpec
                .body(user)
                .when()
                .post(REGISTER);
    }

    public Response login(UserCredentials creds) {
        return reqSpec
                .body(creds)
                .when()
                .post(LOGIN);
    }

    public void delete(String user, String bearerToken) {

        reqSpec
                .header("authorization", bearerToken)
                .pathParams("user", user)
                .when()
                .delete(DELETE)
                .then().log().all()
                .assertThat()
                .statusCode(202);
    }
}