package com.erezShevach.seleniumTask;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


import java.time.Duration;
import java.util.List;

public class Main {
    private final static By signInButtonHomePage = By.xpath("/html/body/nav/div/a[2]");
    private final static By usernameInput = By.id("username");
    private final static By passwordInput = By.id("password");
    private final static By signInButtonForm = By.xpath("//button[text() = 'Sign in']");
    private final static By me = By.xpath("//*[text() = 'Me']");
    private final static By viewProfile = By.xpath("//*[text() = 'View Profile']");
    private final static By myName = By.xpath("//h1[@class=\"text-heading-xlarge inline t-24 v-align-middle break-words\"]");
    private final static By myHeadline = By.xpath("//div[@class=\"text-body-medium break-words\"]");
    private final static By myLocation = By.xpath("//span[@class=\"text-body-small inline t-black--light break-words\"]");
    private final static By myNetwork = By.xpath("//*[text() = 'My network']");
    private final static By connections = By.xpath("//*[text() = 'Connections']");
    private final static By connectionElement = By.xpath("//*[@class=\"mn-connection-card__details\"]");
    private final static By connectionName = By.className("mn-connection-card__name");
    private final static By connectionOccupation = By.className("mn-connection-card__occupation");

    public static void main(String[] args) {
        WebDriver browser = new ChromeDriver();
        try {
            browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            //opening LinkedIn
            browser.get("https://www.linkedin.com/home");
            //clicking "Sign In"
            browser.findElement(signInButtonHomePage).click();
            //signing in
            browser.findElement(usernameInput).sendKeys("gilnada007@gmail.com");
            browser.findElement(passwordInput).sendKeys("gtgtgt");
            browser.findElement(signInButtonForm).click();
            //clicking "Me">"View Profile"
            browser.findElement(me).click();
            browser.findElement(viewProfile).click();
            String name = browser.findElement(myName).getText();
            String headline = browser.findElement(myHeadline).getText();
            String location = browser.findElement(myLocation).getText();
            //clicking "My Network">"Connections"
            browser.findElement(myNetwork).click();
            browser.findElement(connections).click();

            List<WebElement> connectionsList = browser.findElements(connectionElement);
            JSONObject res = new JSONObject();
            res.put("myName", name);
            res.put("city", location);
            res.put("work", headline);
            res.put("connections", createConnectionsArray(connectionsList));
            System.out.println(res);

        } catch (Exception e) {
            System.out.println("Failed to execute: "+ e.getMessage());
        } finally {
               browser.quit();
        }
    }

    private static JSONArray createConnectionsArray(List<WebElement> connectionsList) {
        JSONArray connectionsArr = new JSONArray();
        for (WebElement connection : connectionsList) {
            JSONArray connectionArr = new JSONArray();
            connectionArr.put(connection.findElement(connectionName).getText());
            connectionArr.put(connection.findElement(connectionOccupation).getText());
            connectionsArr.put(connectionArr);
        }
        return connectionsArr;
    }
}