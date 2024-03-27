package com.renodesor.morpionapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PLAYER", schema = "MORPION")
public class Player extends AccessInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_generator")
    @SequenceGenerator(name = "player_generator", sequenceName = "PLAYER_SEQ", schema = "MORPION", allocationSize = 1)
    @Column(name = "PLAYER_ID")
    private Integer playerId;
    @Column(name = "DISPLAY_NAME", nullable = false, length = 3)
    private String displayName;
    @Column(name = "FIRST_NAME", length = 50)
    private String firstName;
    @Column(name = "LAST_NAME", length = 50)
    private String lastName;
    @Column(name = "EMAIL", nullable = false, length = 100)
    private String email;
    @Column(name = "PHONE", length = 12)
    private String phone;
    @Column(name = "USERNAME", nullable = false, length = 30)
    private String username;
    @Column(name = "PASSWORD", nullable = false, length = 100)
    private String password;

    public Player(Integer playerId, String displayName, String firstName, String lastName,
                  String email, String phone, String username, String password,
                  String createBy, Date createOn, String UpdateBy, Date updateOn) {
        super(createBy, createOn, UpdateBy, updateOn);
        this.playerId = playerId;
        this.displayName = displayName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.password = password;
    }

    public Player(Integer playerId, String displayName, String firstName, String lastName,
                  String email, String phone, String username, String password,
                  String createBy, Date createOn) {
        super(createBy, createOn, null, null);
        this.playerId = playerId;
        this.displayName = displayName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "{\"playerId\":" + playerId +","+
                "\"displayName\":\"" + displayName + "\"," +
                "\"firstName\":\"" + firstName + "\"," +
                "\"lastName\":\"" + lastName + "\"," +
                "\"email\":\"" + email + "\"," +
                "\"phone\":" + phone +","+
                "\"username\":\"" + username + "\"," +
                "\"password\":\"" + password + "\"," +
                "\"createBy\":\"" + this.getCreateBy() + "\"," +
                "\"createOn\":\"" + this.getCreateOn() + "\"," +
                "\"updateBy\":\"" + this.getUpdateBy() + "\"," +
                "\"updateOn\":\"" + this.getUpdateOn() + "\"" +
                "}";
    }
}
