package com.envision.core;

import com.envision.utils.ApiUtils;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class RestAssuredActions {

    public static Response makeGetRequest(String baseURI, String endpoint,String methodType){
           Response response = given()
                    .log()
                    .all()
                    .baseUri(baseURI)
                    .basePath(endpoint)
                    .when()
                    .request(ApiUtils.getMethodType(methodType));
           return response;

    }
    public static Response doPostRequest(String baseURI, String endpoint, String methodType){
        Response response = given()
                .log()
                .all()
                .baseUri(baseURI)
                .basePath(endpoint)

                .when()
                .request(ApiUtils.getMethodType(methodType));
        return response;

    }
}
