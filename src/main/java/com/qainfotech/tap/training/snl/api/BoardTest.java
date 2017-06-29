package com.qainfotech.tap.training.snl.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;



public class BoardTest {
	Board testBoard;
	
    
    @BeforeTest
    public void loadOptions() throws FileNotFoundException, UnsupportedEncodingException, IOException, PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption {
    	testBoard = new Board();
    	testBoard.registerPlayer("Shivam");
		testBoard.registerPlayer("Raman");
		testBoard.registerPlayer("priyanka");
    }
    
    
    @Test(priority=0)
    public void getOptionValue_Method_Should_Return_uuidValues_From_Properties_File() throws IOException
    {
    	String s=testBoard.getUUID().toString();
    	testBoard.addOption("UUID",s);
    	
    	assertThat(testBoard.getOptionValue("UUID")).isEqualTo(s);
    	testBoard.deleteOptionValue("UUID");
    }
    @Test(expectedExceptions = NoUserWithSuchUUIDException.class, priority=1)
    public void deletePlayer_method_return_NoUserWithSuchUUIDException_when_player_with_UUID_not_exist() throws FileNotFoundException, UnsupportedEncodingException, PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption, IOException, NoUserWithSuchUUIDException{
    	UUID uuid=UUID.randomUUID();
    	
    	assertThat(testBoard.deletePlayer(uuid));
   
    	
    }
    @Test(expectedExceptions = PlayerExistsException.class,priority=2)
    public void registerPlayer_method_return_PlayerExistsException_when_player_with_same_name_added() throws FileNotFoundException, UnsupportedEncodingException, PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption, IOException{
    
    	assertThat(testBoard.registerPlayer("Shivam"));
   
    	
    }
    /*@Test(expectedExceptions = GameInProgressException.class,priority=3)
    public void registerPlayer_method_return_GameInProgressException_when_try_register_on_board_where_players_have_already_started_movement() throws FileNotFoundException, UnsupportedEncodingException, PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption, IOException{
   JSONObject o=(JSONObject) testBoard.registerPlayer("hivam").get(3);
   int so=(int) o.get("position");
   System.out.println(so!=0);
  if(so!=0){
	  System.out.println("here");
	  
  }else{
	  assertThat(testBoard.registerPlayer("ivam"));
  }
    	
    }*/
    @Test(expectedExceptions = MaxPlayersReachedExeption.class,priority=4)
    public void registerPlayer_method_return_MaxPlayersReachedExeption_when_more_than_4_players_added() throws FileNotFoundException, UnsupportedEncodingException, PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption, IOException{
    	JSONArray a=testBoard.registerPlayer("Pari");
    	if(a.length()<=4){
    	assertThat(testBoard.registerPlayer("Hari"));
    	}
    }
   
   
   
    
}
