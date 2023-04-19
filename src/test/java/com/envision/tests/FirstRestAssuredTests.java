package com.envision.tests;
import com.envision.core.DataProviderArgs;
import com.envision.core.DataProviderUtils;
import com.envision.core.RestAssuredActions;
import com.envision.utils.ApiUtils;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
public class FirstRestAssuredTests {
    String baseURI = "https://reqres.in/";
    String endpoints = "/api/users/2";
    int expectedResponseCode = 200;

    @Test
    public void testListUsersAPI() {
        Response response = given().log().all().
                baseUri(baseURI).
                basePath(endpoints).
                when().request(Method.GET);
        response.then().log().all();


        Assert.assertEquals(expectedResponseCode, response.getStatusCode());
        Assert.assertTrue(response.getBody().asPrettyString().contains("tracey.ramos@reqres.in"));
    }

    @Test
    public void testListUsersAPIWithoutTestNGAssertions() {
        given().log().all().
                baseUri(baseURI).
                basePath(endpoints).
                when().request(Method.GET).then().log().all().
                and().assertThat().statusCode(200).
                and().assertThat().body(containsStringIgnoringCase("tracey.ramos@reqres.in")).
                and().assertThat().contentType("application/json").
                and().assertThat().body("total_pages", equalTo(2)).
                and().assertThat().body("data[0].email",equalTo("george.bluth@reqres.in"));

    }
    @ DataProviderArgs(value = "createUser=baseUri,endPoint,payload,statusCode,method,name,job")
    @Test(dataProviderClass = DataProviderUtils.class, dataProvider = "jsonDataProvider")
    public void testUserCreationPostAPI(String baseUri, String endPoint,String payload,String statusCode,String method,String name,String job
    ) throws IOException {
        String jsonBody = ApiUtils.getStringBody(System.getProperty("user.dir")
                + payload);
               jsonBody = jsonBody.replaceAll("%name%", name);
               jsonBody = jsonBody.replaceAll("%job%", job);

        Response response = RestAssuredActions.doPostRequest(baseUri,endPoint,jsonBody);
        response.then(). and().assertThat().statusCode(Integer.parseInt(statusCode)).
        and().assertThat().body(containsString("createdAt"));



    }
}


