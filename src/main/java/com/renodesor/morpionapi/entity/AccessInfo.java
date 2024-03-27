package com.renodesor.morpionapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class AccessInfo implements Serializable {
    @Column(name = "CREATE_BY", length = 30, nullable = false)
    private String createBy;
    @Column(name = "CREATE_ON", nullable = false)
    private Date createOn;
    @Column(name = "UPDATE_BY", length = 30)
    private String UpdateBy;
    @Column(name = "UPDATE_ON")
    private Date updateOn;

    @Override
    public String toString() {
        return "{" +
                "\"createBy\":\"" + createBy + "\","+
                "\"createOn\":\"" + createOn + "\","+
                "\"UpdateBy\":\"" + UpdateBy + "\","+
                "\"updateOn\":\"" + updateOn + "\""
                +"}"
                ;
    }
}
