package com.renodesor.morpionapi.controller;

import com.renodesor.morpionapi.entity.Game;
import com.renodesor.morpionapi.utils.Converter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class GameControllerTest {
    private String currentDate;
    @Autowired
    private MockMvc mvc;
    private final WebApplicationContext webApplicationContext;
    @Autowired
    GameControllerTest(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }
    @BeforeEach
    public void setup() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withLocale(Locale.CANADA_FRENCH);
        currentDate = (LocalDateTime.now()).format(formatter);
        //Init MockMvc Object and build
        mvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    @DisplayName("Add a Game")
    @Sql({"classpath:db-scripts/schema.sql", "classpath:db-scripts/data.sql"})
    void testAddGame() throws Exception {

        String gameInJson = "{" +
                "\"gameId\": 1," +
                "\"numberOfLines\": 5," +
                "\"numberOfColumns\": 5," +
                "\"qtySquaresForWinner\": 3," +
                "\"gameStatus\": \"RUNNING\"," +
                "\"firstPlayer\": {" +
                "\"playerId\": 1," +
                "\"displayName\": \"NAU\"," +
                "\"firstName\": \"Reneaud\"," +
                "\"lastName\": \"Desormeau\"," +
                "\"email\": \"renodesor@yahoo.fr\"," +
                "\"phone\": null," +
                "\"username\": \"reno\"," +
                "\"password\": \"reno\"," +
                "\"createBy\": \"toto\"," +
                "\"createOn\": \"2024-03-23T04:00:00\"," +
                "\"updateBy\": null," +
                "\"updateOn\": null" +
                "}," +
                "\"secondPlayer\": {" +
                "\"playerId\": 2," +
                "\"displayName\": \"WIS\"," +
                "\"firstName\": \"Wisline\"," +
                "\"lastName\": \"Pierre-Louis\"," +
                "\"email\": \"wisline_pl@yahoo.fr\"," +
                "\"phone\": null," +
                "\"username\": \"wisl\"," +
                "\"password\": \"wisl\"," +
                "\"createBy\": \"toto\"," +
                "\"createOn\": \"2024-03-23T04:00:00\"," +
                "\"updateBy\": null," +
                "\"updateOn\": null" +
                "}," +
                "\"createBy\": \"toto\"," +
                "\"createOn\": \"2024-03-23T19:16:26\"," +
                "\"updateBy\": \"toto\"," +
                "\"updateOn\": \"2024-03-23T19:16:26\"" +
                "}";

        String response = mvc
                .perform(post("/api/games/add")
                        .contentType("application/json")
                        .content(gameInJson))
                .andReturn().getResponse().getContentAsString();

        Game gameToAdd = (Game) Converter.convertJsonToObject(gameInJson, "Game");

        Game addedGame = (Game) Converter.convertJsonToObject(response, "Game");

        assertNotNull(gameToAdd);
        assertNotNull(addedGame);
        gameToAdd.setGameId(addedGame.getGameId());

        assertEquals(gameToAdd.toString(), addedGame.toString());

    }

    @Test
    @DisplayName("Update a Game")
    @Sql({"classpath:db-scripts/schema.sql", "classpath:db-scripts/data.sql"})
    void testUpdateGame() throws Exception {

        String gameInJson = "{" +
                "\"gameId\": 2," +
                "\"numberOfLines\": 10," +
                "\"numberOfColumns\": 10," +
                "\"qtySquaresForWinner\": 4," +
                "\"gameStatus\": \"COMPLETED\"," +
                "\"firstPlayer\": {" +
                "\"playerId\": 1," +
                "\"displayName\": \"NAU\"," +
                "\"firstName\": \"Reneaud\"," +
                "\"lastName\": \"Desormeau\"," +
                "\"email\": \"renodesor@yahoo.fr\"," +
                "\"phone\": null," +
                "\"username\": \"reno\"," +
                "\"password\": \"reno\"," +
                "\"createBy\": \"toto\"," +
                "\"createOn\": \"2024-03-23T04:00:00\"," +
                "\"updateBy\": null," +
                "\"updateOn\": null" +
                "}," +
                "\"secondPlayer\": {" +
                "\"playerId\": 2," +
                "\"displayName\": \"WIS\"," +
                "\"firstName\": \"Wisline\"," +
                "\"lastName\": \"Pierre-Louis\"," +
                "\"email\": \"wisline_pl@yahoo.fr\"," +
                "\"phone\": null," +
                "\"username\": \"wisl\"," +
                "\"password\": \"wisl\"," +
                "\"createBy\": \"toto\"," +
                "\"createOn\": \"2024-03-23T04:00:00\"," +
                "\"updateBy\": null," +
                "\"updateOn\": null" +
                "}," +
                "\"createBy\": \"toto\"," +
                "\"createOn\": \"2024-03-23T19:16:26\"," +
                "\"updateBy\": \"toto\"," +
                "\"updateOn\": \"2024-03-23T19:16:26\"" +
                "}";

        String response = mvc
                .perform(put("/api/games/update")
                        .contentType("application/json")
                        .content(gameInJson))
                .andReturn().getResponse().getContentAsString();

        Game gameToAdd = (Game) Converter.convertJsonToObject(gameInJson, "Game");

        Game addedGame = (Game) Converter.convertJsonToObject(response, "Game");

        assertNotNull(gameToAdd);
        assertNotNull(addedGame);
        gameToAdd.setGameId(addedGame.getGameId());

        assertEquals(gameToAdd.toString(), addedGame.toString());
    }

    @Test
    @DisplayName("Delete a game by id")
    @Sql({"classpath:db-scripts/schema.sql", "classpath:db-scripts/data.sql"})
    void testDeleteGameById() throws Exception {
        int idGame = 1;
        String response = mvc
                .perform(delete("/api/games/delete?id="+idGame)
                        .contentType("application/json"))
                .andReturn().getResponse().getContentAsString();

        assertEquals(1, Integer.parseInt(response));
    }

    @Test
    @DisplayName("Get a game by id")
    @Sql({"classpath:db-scripts/schema.sql", "classpath:db-scripts/data.sql"})
    void testGetGameById() throws Exception {
        int idGame = 1;
        String response = mvc
                .perform(get("/api/games?id="+idGame))
                .andReturn().getResponse().getContentAsString();

        Game game = (Game) Converter.convertJsonToObject(response, "Game");

        assertNotNull(game);
        assertEquals(20, game.getNumberOfLines());
        assertEquals(10, game.getNumberOfColumns());
        assertEquals("RUNNING", game.getGameStatus());
    }

    @Test
    @DisplayName("Get all games")
    @Sql({"classpath:db-scripts/schema.sql", "classpath:db-scripts/data.sql"})
    void testGetAllPlayer() throws Exception {
        String response = mvc
                .perform(get("/api/games/all"))
                .andReturn().getResponse().getContentAsString();

        List<Game> games = Converter.convertJsonToObjectList(response, "Game");

        assertNotNull(games);
        assertEquals(2, games.size());
    }
}
