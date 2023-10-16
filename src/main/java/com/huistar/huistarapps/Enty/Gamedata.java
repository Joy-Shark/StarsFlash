package com.huistar.huistarapps.Enty;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Gamedata {
    @TableId(type = IdType.INPUT)
    private Integer id;
    private int gameguanqia;
    private String gameinner;
    private Integer fast;
    private int gamelevel;

    public Gamedata() {
    }

    public Gamedata(Integer id, int gameguanqia, String gameinner, Integer fast, int gamelevel) {
        this.id = id;
        this.gameguanqia = gameguanqia;
        this.gameinner = gameinner;
        this.fast = fast;
        this.gamelevel = gamelevel;
    }
}
