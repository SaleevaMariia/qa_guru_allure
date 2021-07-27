import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.parameter;
import static io.qameta.allure.Allure.step;
import static org.openqa.selenium.By.linkText;
import static org.openqa.selenium.By.partialLinkText;

public class CheckIssueTests {

    public static final String REPOSITORY = "eroshenkoam/allure-example";
    public static final int ISSUE_NUMBER = 68;
    public static final String ISSUE_NAME="Listeners NamedBy";
    private WebSteps webSteps = new WebSteps();

    @Test
    public void checkNameOfIssueSelenide(){
        SelenideLogger.addListener("allure", new AllureSelenide());

        open("https://github.com");

        $(".header-search-input").click();
        $(".header-search-input").sendKeys(REPOSITORY);
        $(".header-search-input").pressEnter();

        $(linkText(REPOSITORY)).click();
        $(partialLinkText("Issues")).click();
        $("#issue_"+ISSUE_NUMBER+"_link").shouldHave(text(ISSUE_NAME));
    }

    @Test
    public void checkNameOfIssueSteps(){
        webSteps.openMainPage();
        webSteps.searchForRepository(REPOSITORY);
        webSteps.goToRepository(REPOSITORY);
        webSteps.openIssuesTab();
        webSteps.issueShouldHaveNameByNumber(ISSUE_NUMBER, ISSUE_NAME);
    }

    @Test
    public void checkNameOfIssueLambdaStep() {
        step("Открываем главную страницу", () -> {
            open("https://github.com");
        });
        step("Ищем репозиторий " + REPOSITORY, () -> {
            $(".header-search-input").click();
            $(".header-search-input").sendKeys(REPOSITORY);
            $(".header-search-input").pressEnter();
        });
        step("Переходим в репозиторий", () -> {
            $(linkText(REPOSITORY)).click();
        });
        step("Переходим в раздел Issues", () -> {
            $(partialLinkText("Issues")).click();
        });
        step("Проверяем название Issue с номером " + ISSUE_NUMBER, () -> {
            $("#issue_"+ISSUE_NUMBER+"_link").shouldHave(text(ISSUE_NAME));
        });
    }

    @ParameterizedTest(name = "Проверка названий задач")
    @CsvSource({
            "68,Listeners NamedBy",
            "66,новая Issue",
            "65,с днем археолога!"
    })
    public void checkNameOfSeveralIssues(int number, String name){
        parameter("номер задачи", number);
        parameter("название задачи", name);

        webSteps.openMainPage();
        webSteps.searchForRepository(REPOSITORY);
        webSteps.goToRepository(REPOSITORY);
        webSteps.openIssuesTab();
        webSteps.issueShouldHaveNameByNumber(number, name);
    }


}
