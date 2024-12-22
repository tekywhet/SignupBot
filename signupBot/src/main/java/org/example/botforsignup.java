package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class botforsignup {
    private WebDriver driver;
    private WebDriverWait wait;

    // Constants for commonly used XPaths
    private static final String OK_GOT_IT_BUTTON_XPATH = "//*[contains(text(),'Ok Got It')]";
    private static final String SKIP_BUTTON_XPATH = "//span[@class='font-[inherit] text-inherit ' and text()='Skip']";
    private static final String SEARCH_FIELD_XPATH = "//input[@placeholder='Search for anything']";
    private static final String FOLLOW_BUTTON_XPATH = "//button[@class='btn-primary bg-primary hover:bg-primary border-0 hover:border-0 disabled:bg-primary-content/20 cursor-pointer disabled:cursor-not-allowed disabled:text-primary-focus/80 btn normal-case px-2 text-[13px] true  btn-sm min-w-[40px] max-h-[20px] text-[11px]  text-primary-focus'] //span[text()='Follow']";

    // File path to store usernames and passwords
    private static final String FILE_PATH = "usernames_and_passwords.txt";

    public void setup() {
        // Set up WebDriver
        System.setProperty("webdriver.chrome.driver", "C:\\Chrome Webdriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Step 1: Open the signup page
        driver.get("https://dev.mavenx.gg/");

        // Step 2: Click 'Ok Got It' and agree to terms
        clickWhenClickable(By.xpath(OK_GOT_IT_BUTTON_XPATH));
        clickWhenClickable(By.xpath("//button[@type='button' and @class='btn-primary bg-primary hover:bg-primary border-0 hover:border-0 disabled:bg-primary-content/20 cursor-pointer disabled:cursor-not-allowed disabled:text-primary-focus/80 btn normal-case px-2 text-[13px] true  btn-sm min-w-[80px] h-[37px]  font-normal text-[13px] rounded-[12px] text-primary-focus']"));

        // Step 3: Generate a random email and username
        String randomEmail = "randompms" + System.currentTimeMillis() + "@yopmail.com";
        String uniqueUsername = "testuser" + System.currentTimeMillis();

        // Store or print username and password
        storeCredentials(uniqueUsername, "ABc@3456");  // Store the username and password

        // Print credentials to console for debugging
        System.out.println("Generated Username: " + uniqueUsername);
        System.out.println("Generated Password: ABc@3456");

        // Step 4: Fill the signup form
        fillSignupForm(uniqueUsername, randomEmail);

        // Step 5: Submit the form
        submitForm();

        // Step 6: Skip and navigate refresh after submission
        clickWhenClickable(By.xpath(SKIP_BUTTON_XPATH));
        driver.navigate().refresh();

        // Step 7: Search for the user and follow
//        searchAndFollowUser("teky2");

        // Step 8: Wait for the signup process to complete
        waitForSignupCompletion();

        // Step 9: Optionally, handle email verification process
        // Yopmailer yopmailer = new Yopmailer(driver, randomEmail);
        // yopmailer.checkInboxAndVerify();

        // Step 10: Close WebDriver after completing the process
        driver.quit();
    }

    private void clickWhenClickable(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }

    private void fillSignupForm(String username, String email) {
        WebElement nameField = driver.findElement(By.xpath("//input[@name='firstName']"));
        nameField.sendKeys("Test");

        WebElement lastNameField = driver.findElement(By.xpath("//input[@name='lastName']"));
        lastNameField.sendKeys("User");

        WebElement usernameField = driver.findElement(By.xpath("//input[@name='userName']"));
        usernameField.sendKeys(username);

        WebElement emailField = driver.findElement(By.xpath("//input[@name='email']"));
        emailField.sendKeys(email);

        WebElement passwordField = driver.findElement(By.xpath("//input[@name='password']"));
        passwordField.sendKeys("ABc@3456");

        // Select Date of Birth
        selectDateOfBirth("March", "3", "2011");
    }

    private void selectDateOfBirth(String month, String day, String year) {
        driver.findElement(By.xpath("//*[contains(text(),'Month')]")).click();
        driver.findElement(By.xpath("//*[contains(text(),'" + month + "')]")).click();

        driver.findElement(By.xpath("//label[contains(text(),'Day')]")).click();
        driver.findElement(By.xpath("//div[text()='" + day + "']")).click();

        driver.findElement(By.xpath("//label[contains(text(),'Year')]")).click();
        driver.findElement(By.xpath("//div[text()='" + year + "']")).click();
    }

    private void submitForm() {
        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
        submitButton.click();
    }

//    SearchAndFollow

//    private void searchAndFollowUser(String username) {
//        WebElement searchField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SEARCH_FIELD_XPATH)));
//        searchField.click();
//        searchField.sendKeys(username);
//
//        WebElement followButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(FOLLOW_BUTTON_XPATH)));
//        followButton.click();
//    }

    private void waitForSignupCompletion() {
        try {
            Thread.sleep(5000); // Wait for the signup process to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void storeCredentials(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(FILE_PATH), true))) {
            // Write the credentials to the file (appends new data)
            writer.write("Username: " + username + ", Password: " + password);
            writer.newLine();  // Move to the next line after each entry
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new botforsignup().setup();
    }
}
