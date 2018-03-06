package com.gumtree.api.stepdefinitions;

import com.jayway.restassured.RestAssured;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;


public class BaseSetup {

    private static final String baseHost = "https://jsonplaceholder.typicode.com/";
    private WebDriver driver;


    @cucumber.api.java.Before
    public  void setUpEnvironment(){
        driver = new HtmlUnitDriver();
        RestAssured.baseURI = baseHost;
    }

    public WebDriver getDriver() {
        return driver;
    }
}
