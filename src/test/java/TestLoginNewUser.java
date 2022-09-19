import PageObject.MainPage;
import TestModel.UserClient;
import TestModel.UserCredentials;
import TestModel.UserModel;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class TestLoginNewUser extends StandartTest {

    private UserClient userClient;
    private UserCredentials creds;
    private UserModel userModel;
    private boolean afterToBeLaunched;

    @Before
    public void setUp() {
        afterToBeLaunched = true;
        userClient = new UserClient();
        userModel = UserModel.getRandom();
        creds = UserCredentials.from(userModel);
    }

    @After
    public void teardown() {
        if (!afterToBeLaunched) {
            return;
        }
        String bearerToken = userClient.login(creds)
                .then().log().all()
                .extract()
                .path("accessToken");
        userClient.delete(userModel.getEmail(), bearerToken);
    }

    @Test
    @DisplayName("Check registering a new user with an incorrect pass, with less than 6 symbols, fails")
    public void registerNewUserWithIncorrectPassFails() {
        userModel.setPassword("five");
        final boolean incorrectPasswordWarningDisplayed = Selenide.open(MainPage.URL, MainPage.class)
                .clickLoginButton()
                .clickRegisterLink()
                .registerNewUserWithIncorrectPass(userModel)
                .isIncorrectPassDisplayed();
        assertTrue(incorrectPasswordWarningDisplayed);
        afterToBeLaunched = false;
    }
}