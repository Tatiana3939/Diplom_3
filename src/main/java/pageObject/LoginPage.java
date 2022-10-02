package pageObject;

import io.qameta.allure.Step;
import testModel.UserModel;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static com.codeborne.selenide.Condition.visible;

public class LoginPage {

    final public static String URL = MainPage.URL + "login";

     @FindBy(how = How.XPATH,using = ("//a[text()='Восстановить пароль']"))
    private SelenideElement restorePasswordLink;

    @FindBy(how = How.XPATH,using = ("//a[text()='Зарегистрироваться']"))
    private SelenideElement registerLink;

    @FindBy(how = How.XPATH,using = ("//button[text()='Войти']"))
    private SelenideElement loginButton;

    @FindBy(how = How.XPATH,using =("//label[@class='input__placeholder text noselect text_type_main-default'][text()" +
            "='Email']/parent::div/input"))
    private SelenideElement emailInputField;

   @FindBy(how = How.XPATH,using =("//label[@class='input__placeholder text noselect text_type_main-default'][text()" +
            "='Пароль']/parent::div/input"))
    private SelenideElement passwordInputField;

    @FindBy(how = How.XPATH,using = ("//h2[text()='Вход']"))
    private SelenideElement userLoginText;

    @Step("Нажали кнопку Регистрация")
    public RegisterPage clickRegisterLink() {
        registerLink.shouldBe(visible).click();
        return Selenide.page(RegisterPage.class);
    }
    @Step("Нажали Сбросить пароль")
    public ForgotPasswordPage clickRestorePasswordLink() {
        restorePasswordLink.click();
        return Selenide.page(ForgotPasswordPage.class);
    }
    @Step("Нажали Войти")
    public MainPage clickLoginButton() {
        loginButton.click();
        return Selenide.page(MainPage.class);
    }
    @Step("Заполнили Email")
    public LoginPage inputEmail(String email) {
        emailInputField.sendKeys(email);
        return this;
    }

    @Step("Заполнили Email")
    public LoginPage inputPassword(String password) {
        passwordInputField.sendKeys(password);
        return this;    }

    @Step("Заполнили имейл и пароль, нажали Войти")
    public MainPage userLogin(UserModel user) {
        inputEmail(user.getEmail());
        inputPassword(user.getPassword());
        clickLoginButton();
        return Selenide.page(MainPage.class);
    }

    public boolean isUserLoginTextDisplayed() {
        return userLoginText.shouldBe(visible).isDisplayed();
    }
}