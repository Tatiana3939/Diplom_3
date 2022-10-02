import pageObject.LoginPage;
import testModel.UserClient;
import testModel.UserCredentials;
import testModel.UserModel;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class TestUserProfile extends StandartTest {

    private UserClient userClient;
    private UserModel userModel;
    private String bearerToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        userModel = UserModel.getRandom();
        UserCredentials creds = UserCredentials.from(userModel);
        userClient.registerNewUser(userModel);
        bearerToken = userClient.login(creds)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("accessToken");
    }

    @After
    public void teardown() {
        userClient.delete(userModel.getEmail(), bearerToken);
    }


    @Test
    @DisplayName("Check user is able to get into the user profile successfully")
    public void successfullyDisplayUserProfile() {
        final boolean profileTextDisplayed = Selenide.open(LoginPage.URL, LoginPage.class)
                .userLogin(userModel)
                .clickProfileLinkUserLogged()
                .isProfileTextDisplayed();
        assertTrue(profileTextDisplayed);
    }

    @Test
    @DisplayName("Check user is able to click the create burger link from the user profile page")
    public void successfullyDisplayCreateBurgerTextByClickingTheCreateBurgerLink() {
        final boolean createBurgerTextDisplayed = Selenide.open(LoginPage.URL, LoginPage.class)
                .userLogin(userModel)
                .clickProfileLinkUserLogged()
                .clickCreateBurger()
                .isCreateBurgerTextDisplayed();
        assertTrue(createBurgerTextDisplayed);
    }

    @Test
    @DisplayName("Check user is able to click the stellar burger logo from the user profile page")
    public void successfullyDisplayCreateBurgerTextByClickingTheBurgerLogo() {
        final boolean createBurgerTextDisplayed = Selenide.open(LoginPage.URL, LoginPage.class)
                .userLogin(userModel)
                .clickProfileLinkUserLogged()
                .clickBurgerLogo()
                .isCreateBurgerTextDisplayed();
        assertTrue(createBurgerTextDisplayed);
    }


    @Test
    @DisplayName("Check user is able to logout successfully")
    public void successfullyLogoutUser() {
        final boolean userLoginTextDisplayed = Selenide.open(LoginPage.URL, LoginPage.class)
                .userLogin(userModel)
                .clickProfileLinkUserLogged()
                .clickLogoutButton()
                .isUserLoginTextDisplayed();
        assertTrue(userLoginTextDisplayed);
    }
}