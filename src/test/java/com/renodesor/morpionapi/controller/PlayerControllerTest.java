package com.renodesor.morpionapi.controller;

import com.renodesor.morpionapi.entity.Player;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class PlayerControllerTest {
    private String currentDate;
    @Autowired
    private MockMvc mvc;
    private final WebApplicationContext webApplicationContext;
    @Autowired
    PlayerControllerTest(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }
    @BeforeEach
    public void setup() {
        currentDate = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")).format(new Date());
        //Init MockMvc Object and build
        mvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    @DisplayName("Add a new player")
    @Sql({"classpath:db-scripts/schema.sql"})
    void testAddPlayer() throws Exception {

        String playerInJson = "{\"playerId\":1," +
                "\"displayName\":\"REN\"," +
                "\"firstName\":\"Reno\"," +
                "\"lastName\":\"Desor\"," +
                "\"email\":\"renodesor@yahoo.fr\"," +
                "\"username\":\"reno\"," +
                "\"password\":\"reno\"," +
                "\"createBy\":\"toto\"," +
                "\"createOn\":\"" + currentDate + "\"" +
                "}";

        String response = mvc
                .perform(post("/api/players/add")
                        .contentType("application/json")
                        .content(playerInJson))
                .andReturn().getResponse().getContentAsString();

        Player playerToAdd = (Player) Converter.convertJsonToObject(playerInJson, "Player");

        Player addedPlayer = (Player) Converter.convertJsonToObject(response, "Player");

        assertNotNull(playerToAdd);
        assertNotNull(addedPlayer);
        playerToAdd.setPlayerId(addedPlayer.getPlayerId());

        assertEquals(playerToAdd.toString(), addedPlayer.toString());

    }

    @Test
    @DisplayName("Update an existing player")
    @Sql({"classpath:db-scripts/schema.sql"})
    void testUpdatePlayer() throws Exception {

        String playerInJson = "{\"playerId\":1," +
                "\"displayName\":\"NAU\"," +
                "\"firstName\":\"Reneaud\"," +
                "\"lastName\":\"Desormeau\"," +
                "\"email\":\"renodesor@yahoo.fr\"," +
                "\"username\":\"reno\"," +
                "\"password\":\"reno\"," +
                "\"createBy\":\"toto\"," +
                "\"createOn\":\"" + currentDate + "\"" +
                "}";

        String response = mvc
                .perform(put("/api/players/update")
                        .contentType("application/json")
                        .content(playerInJson))
                .andReturn().getResponse().getContentAsString();

        Player playerToUpdate = (Player) Converter.convertJsonToObject(playerInJson, "Player");

        Player updatedPlayer = (Player) Converter.convertJsonToObject(response, "Player");

        assertNotNull(playerToUpdate);
        assertNotNull(updatedPlayer);

        playerToUpdate.setPlayerId(updatedPlayer.getPlayerId());
        assertEquals(playerToUpdate.toString(), updatedPlayer.toString());

    }

    @Test
    @DisplayName("Delete a player by id")
    @Sql({"classpath:db-scripts/schema.sql", "classpath:db-scripts/data.sql"})
    void testDeletePlayerById() throws Exception {
        int idPlayer = 4;
        String response = mvc
                .perform(delete("/api/players/delete?id="+idPlayer)
                        .contentType("application/json"))
                .andReturn().getResponse().getContentAsString();

        assertEquals(2, Integer.parseInt(response));
    }

@Test
@DisplayName("Get a player by id")
@Sql({"classpath:db-scripts/schema.sql", "classpath:db-scripts/data.sql"})
void testGetPlayerById() throws Exception {
    int idPlayer = 1;
    String response = mvc
            .perform(get("/api/players?id="+idPlayer))
            .andReturn().getResponse().getContentAsString();

    Player player = (Player) Converter.convertJsonToObject(response, "Player");

    assertNotNull(player);
    assertEquals("NAU", player.getDisplayName());
    assertEquals("Reneaud", player.getFirstName());
    assertEquals("Desormeau", player.getLastName());
}

    @Test
    @DisplayName("Get a player by id")
    @Sql({"classpath:db-scripts/schema.sql", "classpath:db-scripts/data.sql"})
    void testGetPlayerByUsernameAndPassword() throws Exception {
        String username = "reno";
        String password = "reno";
        String response = mvc
                .perform(get("/api/players/usr-psw?username="+username+"&password="+password))
                .andReturn().getResponse().getContentAsString();

        Player player = (Player) Converter.convertJsonToObject(response, "Player");

        assertNotNull(player);
        assertEquals("NAU", player.getDisplayName());
        assertEquals("Reneaud", player.getFirstName());
        assertEquals("Desormeau", player.getLastName());
    }

    @Test
    @DisplayName("Get all players")
    @Sql({"classpath:db-scripts/schema.sql", "classpath:db-scripts/data.sql"})
    void testGetAllPlayer() throws Exception {
        String response = mvc
                .perform(get("/api/players/all"))
                .andReturn().getResponse().getContentAsString();

        List<Player> players = Converter.convertJsonToObjectList(response, "Player");

        assertNotNull(players);
        assertEquals(3, players.size());
    }

}
