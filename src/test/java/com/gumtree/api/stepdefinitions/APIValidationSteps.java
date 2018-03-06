package com.gumtree.api.stepdefinitions;

import com.google.gson.Gson;
import com.gumtree.api.deserializerobjects.GetPostResponse;
import com.gumtree.api.deserializerobjects.PostRequestBody;
import com.gumtree.api.deserializerobjects.PostResponse;
import com.gumtree.api.deserializerobjects.User;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Random;

import static com.jayway.restassured.RestAssured.given;


public class APIValidationSteps {
    private WebDriver driver;
    private User user;
    private User[] users;
    private BaseSetup baseSetup;
    private int userId;
    private GetPostResponse[] getPostResponses;
    private PostResponse postResponse;
    private PostRequestBody requestBody;

    public APIValidationSteps(BaseSetup baseSetup) {
        this.baseSetup = baseSetup;
        driver = baseSetup.getDriver();
    }

    @Given("^I have navigated to users page$")
    public void i_have_navigated_to_users_page() throws Throwable {
        driver.get("https://jsonplaceholder.typicode.com/users");
        String source = driver.getPageSource();
        driver.quit();
        users = new Gson().fromJson(source, User[].class);
    }

    @When("^I extract the response$")
    public void i_extract_the_response() throws Throwable {
        Random random = new Random();
        user = users[random.nextInt(users.length)];
    }

    @Then("^I extract a random userId and print its email to the console$")
    public void i_extract_a_random_userId_and_print_its_email_to_the_console() throws Throwable {
        String randomEmail = user.getEmail();
        System.out.println("Random Email is "+randomEmail);
        System.out.println("Random UserId is "+user.getId());
        userId = user.getId();
    }

    @Then("^I make a get call for all the posts for the userId from above$")
    public void i_make_a_get_call_for_all_the_posts_for_the_userId_from_above() throws Throwable {
        getPostResponses =   given().relaxedHTTPSValidation().with().queryParam("userId", String.valueOf(userId)).when().get("posts").as(GetPostResponse[].class);
    }

    @And("^I validate the post Ids for each post$")
    public void i_validate_the_post_Ids_for_each_post() throws Throwable {
        for(GetPostResponse singlePost: getPostResponses){
            GetPostResponse post = singlePost;
            Assert.assertEquals("Integer", post.getId().getClass().getSimpleName());
            assertThat(post.getId(), greaterThanOrEqualTo(1));
            assertThat(post.getId(), lessThanOrEqualTo(100));
        }

    }

    @Then("^I make a post based on the userId from above with valid \"([^\"]*)\" and \"([^\"]*)\"$")
    public void i_make_a_post_based_on_the_userId_from_above_with_valid_and(String title, String body) throws Throwable {
        requestBody = new PostRequestBody();
        requestBody.setTitle(title);
        requestBody.setBody(body);
        requestBody.setUserId(userId);
        postResponse=
                given().
                with().
                header("Content-type", "application/json; charset=UTF-8")
                .body(requestBody)
                .when().post("posts").then().statusCode(201).extract().as(PostResponse.class);
    }

    @And("^I validate the response status for the post request$")
    public void i_validate_the_response_status_for_the_post_request() throws Throwable {
        System.out.println("Response Body "+postResponse.getBody());
        System.out.println("Response Title "+postResponse.getTitle());
        System.out.println("Response userId "+postResponse.getUserId());
        System.out.println("Response ID "+postResponse.getId());

        Assert.assertEquals(requestBody.getBody(), postResponse.getBody());
        Assert.assertEquals(requestBody.getTitle(), postResponse.getTitle());
        Assert.assertEquals(requestBody.getUserId(), postResponse.getUserId());
        Assert.assertTrue(101 == postResponse.getId());
    }






}
