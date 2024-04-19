package com.renodesor.morpionapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "GAME", schema = "MORPION")
public class Game extends AccessInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_generator")
    @SequenceGenerator(name = "game_generator", sequenceName = "GAME_SEQ", schema = "MORPION", allocationSize = 1)
    @Column(name = "GAME_ID")
    private Integer gameId;
    @Column(name = "NUMBER_OF_LINES", nullable = false, length = 2)
    private Integer numberOfLines;
    @Column(name = "NUMBER_OF_COLUMNS", nullable = false, length = 2)
    private Integer numberOfColumns;
    @Column(name = "QTY_SQUARES_FOR_WINNER", nullable = false, length = 2)
    private Integer qtySquaresForWinner;
    @Column(name = "GAME_STATUS", nullable = false, length = 12)
    private String gameStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="FIRST_PLAYER_ID", referencedColumnName = "PLAYER_ID")
    private Player firstPlayer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SECOND_PLAYER_ID", referencedColumnName = "PLAYER_ID")
    private Player secondPlayer;

    public Game(Integer gameId, Integer numberOfLines, Integer numberOfColumns, Integer qtySquaresForWinner,
                String gameStatus, Player firstPlayer, Player secondPlayer,
                String createBy, Date createOn, String UpdateBy, Date updateOn) {
        super(createBy, createOn, UpdateBy, updateOn);
        this.gameId = gameId;
        this.numberOfLines = numberOfLines;
        this.numberOfColumns = numberOfColumns;
        this.qtySquaresForWinner = qtySquaresForWinner;
        this.gameStatus = gameStatus;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    public Game(Integer gameId, Integer numberOfLines, Integer numberOfColumns, Integer qtySquaresForWinner,
                String gameStatus, Player firstPlayer, Player secondPlayer,
                String createBy, Date createOn) {
        super(createBy, createOn, null, null);
        this.gameId = gameId;
        this.numberOfLines = numberOfLines;
        this.numberOfColumns = numberOfColumns;
        this.qtySquaresForWinner = qtySquaresForWinner;
        this.gameStatus = gameStatus;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    @Override
    public String toString() {
        return "{" +
                "\"gameId\":" + gameId +","+
                "\"numberOfLines\":" + numberOfLines +","+
                "\"numberOfColumns\":" + numberOfColumns +","+
                "\"qtySquaresForWinner\":" + qtySquaresForWinner +","+
                "\"gameStatus\":\"" + gameStatus +"\","+
                "\"firstPlayer\":\"" + firstPlayer +"\","+
                "\"secondPlayer\":\"" + secondPlayer +"\","+
                "\"createBy\":\"" + this.getCreateBy() +"\","+
                "\"createOn\":\"" + this.getCreateOn() +"\","+
                "\"updateBy\":\"" + this.getUpdateBy() +"\","+
                "\"updateOn\":\"" + this.getUpdateOn() +"\""+
                "}";
    }
}
