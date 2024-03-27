package com.renodesor.morpionapi.controller;

import com.renodesor.morpionapi.entity.GameContent;
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
class GameContentControllerTest {
    private String currentDate;
    @Autowired
    private MockMvc mvc;
    private final WebApplicationContext webApplicationContext;
    @Autowired
    GameContentControllerTest(WebApplicationContext webApplicationContext) {
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
    @DisplayName("Add a GameContent")
    @Sql({"classpath:db-scripts/schema.sql"})
    void testAddGameContent() throws Exception {

        String squaresData = "{'s00':2, 's01':0, 's02':1, 's03':0}";
        String movesHistory = "{'s00':2, 's01':0, 's02':1, 's03':0}";

        String gameContentInJson = "{" +
                "\"gameId\": 1," +
                "\"squaresData\": \""+squaresData+"\"," +
                "\"movesHistory\": \""+movesHistory+"\"," +
                "\"createBy\": \"toto\"," +
                "\"createOn\": \"2024-03-23T19:16:26\"," +
                "\"updateBy\": \"toto\"," +
                "\"updateOn\": \"2024-03-23T19:16:26\"" +
                "}";

        String response = mvc
                .perform(post("/api/game-contents/add")
                        .contentType("application/json")
                        .content(gameContentInJson))
                .andReturn().getResponse().getContentAsString();

        GameContent gameContentToAdd = (GameContent) Converter.convertJsonToObject(gameContentInJson, "GameContent");

        GameContent addedGameContent = (GameContent) Converter.convertJsonToObject(response, "GameContent");

        assertNotNull(gameContentToAdd);
        assertNotNull(addedGameContent);

        assertEquals(gameContentToAdd.toString(), addedGameContent.toString());

    }

    @Test
    @DisplayName("Update a GameContent")
    @Sql({"classpath:db-scripts/schema.sql", "classpath:db-scripts/data.sql"})
    void testUpdateGameContent() throws Exception {

        String squaresData = "{'s00':2, s01':0, 's02':1, 's03':0, 's04':2, 's05':1, 's06':0, 's07':2}";
        String movesHistory = "{'s00':2, s01':0, 's02':1, 's03':0, 's04':2, 's05':1, 's06':0, 's07':2}";

        String gameContentInJson = "{" +
                "\"gameId\": 1," +
                "\"squaresData\": \""+squaresData+"\"," +
                "\"movesHistory\": \""+movesHistory+"\"," +
                "\"createBy\": \"toto\"," +
                "\"createOn\": \"2024-03-23T19:16:26\"," +
                "\"updateBy\": \"toto\"," +
                "\"updateOn\": \"2024-03-23T19:16:26\"" +
                "}";

        String response = mvc
                .perform(put("/api/game-contents/update")
                        .contentType("application/json")
                        .content(gameContentInJson))
                .andReturn().getResponse().getContentAsString();

        GameContent gameContentToAdd = (GameContent) Converter.convertJsonToObject(gameContentInJson, "GameContent");

        GameContent addedGameContent = (GameContent) Converter.convertJsonToObject(response, "GameContent");

        assertNotNull(gameContentToAdd);
        assertNotNull(addedGameContent);

        assertEquals(gameContentToAdd.toString(), addedGameContent.toString());
    }

    @Test
    @DisplayName("Delete a gameContent by id")
    @Sql({"classpath:db-scripts/schema.sql", "classpath:db-scripts/data.sql"})
    void testDeleteGameContentById() throws Exception {
        int idGame = 1;
        String response = mvc
                .perform(delete("/api/game-contents/delete?id="+idGame)
                        .contentType("application/json"))
                .andReturn().getResponse().getContentAsString();

        assertEquals(1, Integer.parseInt(response));
    }

    @Test
    @DisplayName("Get a gameContent by id")
    @Sql({"classpath:db-scripts/schema.sql", "classpath:db-scripts/data.sql"})
    void testGetGameContentById() throws Exception {
        String squaresData = "{'s00':2, 's01':0, 's02':1, 's03':0, 's04':2, 's05':1, 's06':0, 's07':2}";
        String movesHistory = "{'s00':2, 's01':0, 's02':1, 's03':0, 's04':2, 's05':1, 's06':0, 's07':2}";

        int idGame = 2;
        String response = mvc
                .perform(get("/api/game-contents?id="+idGame))
                .andReturn().getResponse().getContentAsString();

        GameContent gameContent = (GameContent) Converter.convertJsonToObject(response, "GameContent");

        assertNotNull(gameContent);
        assertEquals(squaresData, gameContent.getSquaresData());
        assertEquals(movesHistory, gameContent.getMovesHistory());
    }

    @Test
    @DisplayName("Get all gameContents")
    @Sql({"classpath:db-scripts/schema.sql", "classpath:db-scripts/data.sql"})
    void testGetAllPlayer() throws Exception {
        String response = mvc
                .perform(get("/api/game-contents/all"))
                .andReturn().getResponse().getContentAsString();

        List<GameContent> gameContents = Converter.convertJsonToObjectList(response, "GameContent");

        assertNotNull(gameContents);
        assertEquals(2, gameContents.size());
    }
}
