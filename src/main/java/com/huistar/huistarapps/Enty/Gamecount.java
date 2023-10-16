package com.huistar.huistarapps.Enty;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Gamecount {
    @TableId(type = IdType.INPUT)
    private Integer id;
    private int count;
    private String time;
    private String ip;


    public Gamecount() {
    }

    public Gamecount(Integer id, int count, String time, String ip) {
        this.id = id;
        this.count = count;
        this.time = time;
        this.ip = ip;
    }
}
