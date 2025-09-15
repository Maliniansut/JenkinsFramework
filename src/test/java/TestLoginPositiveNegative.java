import io.qameta.allure.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestLoginPositiveNegative {
    WebDriver driver = new ChromeDriver();

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test(enabled = true, priority = 1, groups = {"positive","sanity","stage"})
    @Description("Verify url is same and no redirection is done.")
    public void testVwoLoginPositive() {
        driver.get("https://app.vwo.com");
        Assert.assertEquals(driver.getTitle(), "Login - VWO");
        Assert.assertEquals(driver.getCurrentUrl(), "https://app.vwo.com/#/login");

        WebElement emailField = driver.findElement(By.id("login-username"));
        WebElement passwordField = driver.findElement(By.id("login-password"));
        WebElement signupButton = driver.findElement(By.id("js-login-btn"));

        emailField.sendKeys("maliniansut@gmail.com");
        passwordField.sendKeys("123@Malini");
        signupButton.click();

        try {
            Thread.sleep(5000);  // Wait for any post-login processing on same page
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Verify URL is still the login page URL (no redirect)
        Assert.assertEquals(driver.getCurrentUrl(), "https://app.vwo.com/#/login",
                "Expected no page redirection after login");
    }

    @Test(priority = 2 , groups={"negative","sanity","regression"})
    public void testVwoLoginNegative() {
        driver.get("https://app.vwo.com");
        Assert.assertEquals(driver.getTitle(), "Login - VWO");
        Assert.assertEquals(driver.getCurrentUrl(), "https://app.vwo.com/#/login");
        WebElement emailField = driver.findElement(By.id("login-username"));
        WebElement passwordField = driver.findElement(By.id("login-password"));
        WebElement signupButton = driver.findElement(By.id("js-login-btn"));
        // Use invalid credentials for this test
        emailField.sendKeys("wrong_email@example.com");
        passwordField.sendKeys("wrong_password");
        signupButton.click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // Assert error message for invalid login
        WebElement errorMsg = driver.findElement(By.className("notification-box-description"));
        String errorText = errorMsg.getText();
        Assert.assertTrue(
                errorText.contains("did not match") || errorText.contains("exceeded the maximum attempts"),
                "Expected error message for invalid login"
        );
    }
}


