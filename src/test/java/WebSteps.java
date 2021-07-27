import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.By.linkText;
import static org.openqa.selenium.By.partialLinkText;

public class WebSteps {

    @Step("Открываем главную страницу")
    public void openMainPage() {
        open("https://github.com");
    }

    @Step("Ищем репозиторий [{repository}]")
    public void searchForRepository(String repository) {
        $(".header-search-input").click();
        $(".header-search-input").sendKeys(repository);
        $(".header-search-input").pressEnter();
    }

    @Step("Переходим в репозиторий [{repository}]")
    public void goToRepository(String repository) {
        $(linkText(repository)).click();
    }

    @Step("Переходим в раздел Issues")
    public void openIssuesTab() {
        $(partialLinkText("Issues")).click();
    }

    @Step("Проверяем название Issue с номером {number}")
    public void issueShouldHaveNameByNumber(int number, String name) {
        $("#issue_"+number+"_link").shouldHave(text(name));
    }
}
