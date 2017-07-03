package com.qainfotech.tap.training.snl.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class BoardTest {
	BoardModel testBoardModel;
	Board testBoard;
	JSONObject jsonData;

	@BeforeTest
	public void loadOptions() throws FileNotFoundException, UnsupportedEncodingException, IOException,
			PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption {
		testBoard = new Board();
		testBoardModel = new BoardModel();
		testBoard.registerPlayer("Player1");

	}

	@Test(priority = 0)
	public void getOptionValue_Method_Should_Return_uuidValues_From_Properties_File() throws IOException {
		String s = testBoard.getUUID().toString();
		testBoard.addOption("UUID", s);

		assertThat(testBoard.getOptionValue("UUID")).isEqualTo(s);
		testBoard.deleteOptionValue("UUID");
	}

	@Test(expectedExceptions = NoUserWithSuchUUIDException.class, priority = 1)
	public void deletePlayer_method_return_NoUserWithSuchUUIDException_when_player_with_UUID_not_exist()
			throws FileNotFoundException, UnsupportedEncodingException, PlayerExistsException, GameInProgressException,
			MaxPlayersReachedExeption, IOException, NoUserWithSuchUUIDException {
		UUID uuid = UUID.randomUUID();

		assertThat(testBoard.deletePlayer(uuid));

	}

	@Test(expectedExceptions = PlayerExistsException.class, priority = 2)
	public void registerPlayer_method_return_PlayerExistsException_when_player_with_same_name_added()
			throws FileNotFoundException, UnsupportedEncodingException, PlayerExistsException, GameInProgressException,
			MaxPlayersReachedExeption, IOException {

		assertThat(testBoard.registerPlayer("Player1"));

	}
	

	@Test(expectedExceptions = MaxPlayersReachedExeption.class, priority = 3)
	public void registerPlayer_method_return_MaxPlayersReachedExeption_when_more_than_4_players_added()
			throws FileNotFoundException, UnsupportedEncodingException, PlayerExistsException, GameInProgressException,
			MaxPlayersReachedExeption, IOException {
		
		testBoard.registerPlayer("Player6");
		testBoard.registerPlayer("Player3");
		JSONArray a = testBoard.registerPlayer("Player4");
		
		if (a.length() == 4) {
			assertThat(testBoard.registerPlayer("Player5"));
		}
	}

	@Test(priority = 4)
	public void deletePlayer_method_delete_from_list_if_uuid_matches()
			throws IOException, JSONException, NoUserWithSuchUUIDException {
		
		 JSONObject olm=testBoard.getData().getJSONArray("players").getJSONObject(0);
		 
		int actualLength = testBoard.getData().getJSONArray("players").length();
		testBoard.deletePlayer((UUID) olm.get("uuid"));
		int expectedLength = testBoard.getData().getJSONArray("players").length();

		assertThat(actualLength - 1).isEqualTo(expectedLength);

	}
	@Test(expectedExceptions = InvalidTurnException.class, priority = 5)
	public void rollDice_method_throw_InvalidTurnException_when_incorrect_uuid_passed() throws FileNotFoundException, UnsupportedEncodingException, JSONException, InvalidTurnException
			 
	{
	JSONObject a=testBoard.rollDice((UUID)testBoard.data.getJSONArray("players").getJSONObject(0).get("uuid"));
			
		assertThat(testBoard.rollDice((UUID)testBoard.data.getJSONArray("players").getJSONObject(0).get("uuid"))).isEqualTo(a);
	}
	
	@Test(expectedExceptions = GameInProgressException.class, priority = 6)
	public void registerPlayer_method_return_GameInProgressException_when_player_is_not_at_0_position()
			throws FileNotFoundException, UnsupportedEncodingException, PlayerExistsException, GameInProgressException,
			MaxPlayersReachedExeption, IOException {
		
		//Assigning player a position of 1 explicitly and then asserting
		 testBoard.getData().getJSONArray("players").getJSONObject(0).put("position", 1);
		assertThat(testBoard.registerPlayer("Player2"));
	
	}
}
