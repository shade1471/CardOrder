package ru.netology.webinterface;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

public class CardOrderTest {
    private WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
//        driver = new ChromeDriver();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldSendForm() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Андрей Грибанов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79123456790");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] .checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, actual);
    }

    @Test
    public void shouldSendFormWithEmptyName() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79123456790");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] .checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(actual, expected);
    }

    @Test
    public void shouldSendFormWithEmptyPhone() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Андрей Грибанов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] .checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(actual, expected);
    }

    @Test
    public void shouldSendFormWithOneSymbolInName() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("А");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79123456790");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] .checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, actual);
    }

    @Test
    public void shouldSendFormWithDoubleSurname() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Михаил Салтыков-Щедрин");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79123456790");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] .checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, actual);
    }

    @Test
    public void shouldSendFormWithDoubleSurnameWithBottomLine() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Михаил Салтыков_Щедрин");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79123456790");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] .checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, actual);
    }

    @Test
    public void shouldSendFormWithWrongValueByNameWithRuAndEn() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Андрей Gribanov");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79123456790");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] .checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, actual);
    }

    @Test
    public void shouldSendFormWithWrongValueByNameWithYo() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Артём Иванов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79123456790");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] .checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, actual);
    }

    @Test
    public void shouldSendFormWithWrongValueByNameWithEnSymbols() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Andrey Gribanov");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79123456790");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] .checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, actual);
    }

    @Test
    public void shouldSendFormWithWrongValueByNameWithNumbers() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Андрей 2");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79123456790");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] .checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, actual);
    }

    @Test
    public void shouldSendFormWithWrongValueByPhoneWith12Digits() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Андрей Грибанов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+791234567901");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] .checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, actual);
    }

    @Test
    public void shouldSendFormWithWrongValueByPhoneWith10Digits() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Андрей Грибанов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7912345679");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] .checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, actual);
    }

    @Test
    public void shouldSendFormWithValueByPhone11DigitsAndWithoutPlus() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Андрей Грибанов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("89019123455");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] .checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, actual);
    }

    @Test
    public void shouldSendFormWithoutCheckBox() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Андрей Грибанов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79123456790");
        driver.findElement(By.tagName("button")).click();

        assertTrue(driver.findElement(By.cssSelector(".checkbox.input_invalid")).isDisplayed());
    }
}
