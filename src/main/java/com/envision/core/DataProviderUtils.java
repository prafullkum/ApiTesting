package com.envision.core;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class DataProviderUtils {
    @DataProvider(name = "loginDataProvider")
    public static Object[][] provideMultipleDataSet() {
        Object[][] obj = new Object[][]
                {{"Admin", "admin123"},
                        {"Admin123", "admin123"}
                };
        return obj;
    }


    @DataProvider(name = "jsonDataProvider")
    public static Object[][] fetchTestCaseData(Method method) throws IOException, ParseException {
        String methodName = method.getName();
        String testDataName = method.getAnnotation(DataProviderArgs.class).value().split("=")[0];
        String testDataFields = method.getAnnotation(DataProviderArgs.class).value().split("=")[1];
        String loginDataFields = testDataFields;
        String loginDataArr[] = loginDataFields.split(",");
        File jsonFile = new File(System.getProperty("user.dir")
                +File.separator +
                "\\src\\test\\resources\\testdata\\testData-"+System.getProperty("envName")+".json");
        JSONParser parser = new JSONParser();
        FileReader fr = new FileReader(jsonFile);
        Object obj = parser.parse(fr);
        System.out.println(obj);
        JSONObject jsonObject = (JSONObject) obj;
        System.out.println(jsonObject);
        JSONArray jsonArray = (JSONArray) jsonObject.get(testDataName);
        List<List<String>> allRows = new ArrayList<List<String>>();

        for (int i = 0; i < jsonArray.size(); i++) {
            List<String> allColumnsInRow = new ArrayList<>();
            JSONObject testCaseObj = (JSONObject) jsonArray.get(i);
            for (int j = 0; j < loginDataArr.length; j++) {
                allColumnsInRow.add(testCaseObj.get(loginDataArr[j]).toString());
            }
            allRows.add(allColumnsInRow);
        }
        System.out.println(allRows);
        Object[][] dataObj = new Object[allRows.size()][allRows.get(0).size()];
        int index = 0;
        for (List<String> list : allRows) {
            for (int i = 0; i < list.size(); i++) {
                dataObj[index][i] = list.get(i);
            }
            index++;
        }
        return dataObj;
    }


    public static void main(String[] args) throws IOException, ParseException {
        String loginDataFields = "username,password";
        String loginDataArr[] = loginDataFields.split(",");
        File jsonFile = new File(System.getProperty("user.dir") + "\\src\\test\\resources\\testdata\\testData.json");
        JSONParser parser = new JSONParser();
        FileReader fr = new FileReader(jsonFile);
        Object obj = parser.parse(fr);
        System.out.println(obj);
        JSONObject jsonObject = (JSONObject) obj;
        System.out.println(jsonObject);
        JSONArray jsonArray = (JSONArray) jsonObject.get("testUserCreationPostAPI");
        List<List<String>> allRows = new ArrayList<List<String>>();

        for (int i = 0; i < jsonArray.size(); i++) {
            List<String> allColumnsInRow = new ArrayList<>();
            JSONObject testCaseObj = (JSONObject) jsonArray.get(i);
            for (int j = 0; j < loginDataArr.length; j++) {
                allColumnsInRow.add(testCaseObj.get(loginDataArr[j]).toString());
            }
            allRows.add(allColumnsInRow);
        }
        System.out.println(allRows);
        Object[][] dataObj = new Object[allRows.size()][allRows.get(0).size()];
        int index = 0;
        for (List<String> list : allRows) {
            for (int i = 0; i < list.size(); i++) {
                dataObj[index][i] = list.get(i);
            }
            index++;
        }

    }
}

