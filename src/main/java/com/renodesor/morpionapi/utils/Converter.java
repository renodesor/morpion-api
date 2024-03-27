package com.renodesor.morpionapi.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.renodesor.morpionapi.entity.Game;
import com.renodesor.morpionapi.entity.GameContent;
import com.renodesor.morpionapi.entity.Player;
import org.hibernate.engine.jdbc.NonContextualLobCreator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.List;

public class Converter {
    public static Object convertJsonToObject(String jsonStr, String outputClassName) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        if("Player".equals(outputClassName)) {
            Player player;
            try {
                //player = objectMapper.readValue(jsonStr, Player.class);
                player = objectMapper.reader()
                        .forType(Player.class)
                        .readValue(jsonStr);
            } catch (Exception ex) {
                throw new Exception("Bad request, unable to cast the provided json to Player", ex);
            }
            return player;
        } else if("Game".equals(outputClassName)) {
            Game game;
            try {
                game = objectMapper.readValue(jsonStr, Game.class);
            } catch (Exception ex) {
                throw new Exception("Bad request, unable to cast the provided json to Game", ex);
            }
            return game;
        } else if("GameContent".equals(outputClassName)) {
            GameContent gameContent;
            try {
                gameContent = objectMapper.readValue(jsonStr, GameContent.class);
            } catch (Exception ex) {
                throw new Exception("Bad request, unable to cast the provided json to GameContent", ex);
            }
            return gameContent;
        } else {
            return null;
        }

    }

    public static List convertJsonToObjectList(String jsonStr, String outputClassName) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        if("Player".equals(outputClassName)) {
            CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, Player.class);
            List<Player> players;
            try {
                players = objectMapper.readValue(jsonStr, listType);
            } catch (Exception ex) {
                throw new Exception("Bad request, unable to cast the provided json to a list of players");
            }
            return players;
        } else if("Game".equals(outputClassName)) {
            CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, Game.class);
            List<Game> games;
            try {
                games = objectMapper.readValue(jsonStr, listType);
            } catch (Exception ex) {
                throw new Exception("Bad request, unable to cast the provided json to a list of games");
            }
            return games;
        } else if("GameContent".equals(outputClassName)) {
            CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, GameContent.class);
            List<GameContent> gameContents;
            try {
                gameContents = objectMapper.readValue(jsonStr, listType);
            } catch (Exception ex) {
                throw new Exception("Bad request, unable to cast the provided json to a list of gameContents");
            }
            return gameContents;
        } else {
            return null;
        }
    }

    public static String formatStrJson(String str) throws Exception {
        String formattedStr;
        try {
            JSONObject jsonObj = new JSONObject(str);
            formattedStr = jsonObj.toString();
        } catch(JSONException jex) {
            JSONArray array = new JSONArray(str);
            formattedStr = array.toString();
        } catch (Exception ex) {
            throw new Exception("Bad json format", ex);
        }
        return formattedStr;
    }

/*
    public static String getStringFromClob(Clob clob) throws SQLException, IOException {
        Reader r = clob.getCharacterStream();
        StringBuffer buffer = new StringBuffer();
        int ch;
        while ((ch = r.read())!=-1) {
            buffer.append(""+(char)ch);
        }
        return buffer.toString();
    }
    public static Clob getClobFromString(String str) {
        return NonContextualLobCreator.INSTANCE.createClob(str);
    }
*/

}
