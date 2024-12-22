package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.security.Key;

public class Yopmailer {
    private static WebDriver driver;
    private String yopmailEmail;

    // Constructor to initialize WebDriver and email
    public Yopmailer(WebDriver driver, String yopmailEmail) {
        this.driver = driver;
        this.yopmailEmail = yopmailEmail;
    }

    // Method to go to YOPmail and check the inbox for the verification email
    public void checkInboxAndVerify() {
        // Go to YOPmail inbox page
        driver.get("https://yopmail.com/en/");

        // Find and enter the YOPmail email address
        WebElement emailField = driver.findElement(By.id("login"));
        emailField.sendKeys(this.yopmailEmail+ Keys.ENTER);

        // Click on the "Check Inbox" button
        WebElement checkInboxButton = driver.findElement(By.id("refresh"));
        checkInboxButton.click();

        // Wait for the inbox to refresh and load emails
        try {
            Thread.sleep(5000);  // Wait for the inbox to load (can be optimized with WebDriverWait)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Find the verification email (e.g., contains the link text "Verify your email")
        WebElement verificationLink = driver.findElement(By.partialLinkText("Verify your email"));
        verificationLink.click();
    }

    // Getter for the email (if needed elsewhere)
    public String getYopmailEmail() {
        return yopmailEmail;
    }

}
