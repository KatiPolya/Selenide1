import com.codeborne.selenide.Condition;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {

    String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    String date = generateDate(3);


    private WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }


    @Test
    void shouldOrderCard() {

        open("http://localhost:9999/");

        $("[placeholder=\"Город\"]").setValue("Москва");
        $("[placeholder=\"Дата встречи\"]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        $("[placeholder=\"Дата встречи\"]").sendKeys(Keys.DELETE);
        $("[placeholder=\"Дата встречи\"]").setValue(date);
        $("[name=\"name\"]").setValue("Яна Куль");
        $("[name=\"phone\"]").setValue("+12345678901");
        $(".checkbox__box").click();
        $(byText("Забронировать")).click();

        $(byText("Встреча успешно забронирована на")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(byText(date)).shouldBe(Condition.visible, Duration.ofSeconds(15));


    }

}
