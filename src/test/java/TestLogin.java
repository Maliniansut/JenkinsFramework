import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestLogin {
        WebDriver driver;

        @BeforeMethod
        public void setup(){
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }

        @Test
        public void testVwoLogin(){
            WebDriver driver = new ChromeDriver();
            driver.get("https://app.vwo.com");
            System.out.println(driver.getTitle());
            Assert.assertEquals(driver.getTitle(),"Login - VWO");
            Assert.assertEquals(driver.getCurrentUrl(),"https://app.vwo.com/#/login");

            WebElement emailField = driver.findElement(By.id("login-username"));
            WebElement passwordField = driver.findElement(By.id("login-password"));
            WebElement signupButton = driver.findElement(By.id("js-login-btn"));

            emailField.sendKeys("maliniansut@gmail.com");
            passwordField.sendKeys("123@Malini");
            signupButton.click();

            //Added try catch because WebDriver reads elements very fast so it may skip some things so that is why added thread.sleep(5000),so that it processes these things.
            try{
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            WebElement error_msg = driver.findElement(By.className("notification-box-description"));
            String error_msg_text = error_msg.getText();
            Assert.assertEquals(error_msg_text,"Your email, password, IP address or location did not match");
        }
    }

