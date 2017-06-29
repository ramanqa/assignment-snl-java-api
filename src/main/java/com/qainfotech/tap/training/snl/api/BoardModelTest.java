package com.qainfotech.tap.training.snl.api;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class BoardModelTest {
BoardModel testBoardModel;
    JSONObject data;
  
    @BeforeTest
    public void loadOptions() throws FileNotFoundException, UnsupportedEncodingException, IOException {
    	testBoardModel = new BoardModel();
    }
    
    @Test
    public void getStep_Method_Should_return_JSONObject_to_be_set_as_key_in_jsonArray() 
    {
    	
    }
    
}
