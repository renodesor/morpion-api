package com.renodesor.morpionapi.entity;

import com.renodesor.morpionapi.utils.Converter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Date;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@Entity
@Table(name = "GAME_CONTENT", schema = "MORPION")
public class GameContent extends AccessInfo {

    @Id
    @Column(name = "GAME_ID")
    private Integer gameId;
    @Column(name = "SQUARES_DATA")
    private String squaresData;
    @Column(name = "MOVES_HISTORY")
    private String movesHistory;

    public GameContent(Integer gameId, String squaresData, String movesHistory,
                       String createBy, Date createOn, String UpdateBy, Date updateOn) {
        super(createBy, createOn, UpdateBy, updateOn);
        this.gameId = gameId;
        this.squaresData = squaresData;
        this.movesHistory = movesHistory;
    }

    public GameContent(Integer gameId, String squaresData, String movesHistory,
                       String createBy, Date createOn) {
        super(createBy, createOn, null, null);
        this.gameId = gameId;
        this.squaresData = squaresData;
        this.movesHistory = movesHistory;
    }

/*
    public GameContent(Integer gameId, Clob squaresData, Clob movesHistory,
                       String createBy, Date createOn, String UpdateBy, Date updateOn) throws SQLException, IOException {
        super(createBy, createOn, UpdateBy, updateOn);
        this.gameId = gameId;
        this.squaresData = Converter.getStringFromClob(squaresData);
        this.movesHistory = Converter.getStringFromClob(movesHistory);
    }

    public GameContent(Integer gameId, Clob squaresData, Clob movesHistory,
                       String createBy, Date createOn) throws SQLException, IOException {
        super(createBy, createOn, null, null);
        this.gameId = gameId;
        this.squaresData = Converter.getStringFromClob(squaresData);
        this.movesHistory = Converter.getStringFromClob(movesHistory);
    }
*/

    @SneakyThrows
    @Override
    public String toString() {
        return "{"+
                "\"gameId\":" + gameId +","+
                "\"squaresData\":" + squaresData +","+
                "\"movesHistory\":" + movesHistory +","+
                "\"createBy\":\"" + this.getCreateBy() +"\","+
                "\"createOn\":\"" + this.getCreateOn() +"\","+
                "\"updateBy\":\"" + this.getUpdateBy() +"\","+
                "\"updateOn\":\"" + this.getUpdateOn() +"\""+
                "}";
    }
}
