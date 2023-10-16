package com.huistar.huistarapps.Contraller.Game;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.huistar.huistarapps.Enty.Gamecount;
import com.huistar.huistarapps.Enty.Gamedata;
import com.huistar.huistarapps.Mapper.GamecountMapper;
import com.huistar.huistarapps.Mapper.GamedataMapper;
import com.huistar.huistarapps.UtilsEnty.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequestMapping("/game")
@RestController
public class GameContraller {
    @Autowired
    private GamedataMapper gamedataMapper;
    @Autowired
    private GamecountMapper gamecountMapper;
//    @GetMapping("/lowgamedata")
    @PostMapping("/sendgamedata")
    public String sendgamedata(@RequestBody Map<String,Object> map){
        System.out.println("this is sendgamedata");
        int guanqia = Integer.parseInt(map.get("guanqia").toString());
        String inner = map.get("inner").toString();
        int level = Integer.parseInt(map.get("level").toString());


        Gamedata gamedata = new Gamedata(null, guanqia, inner,null, level);
        System.out.println(gamedata.toString());
        int insert = gamedataMapper.insert(gamedata);
        return "ok!";
    }
    @PostMapping("/getdata")
    public ReturnObject getGamedata(@RequestBody Map<String,Object> map) {
        try{
            int guanqia = Integer.parseInt(map.get("guanqia").toString());
            int level = Integer.parseInt(map.get("level").toString());
            QueryWrapper<Gamedata> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("gameguanqia",guanqia).eq("gamelevel",level);
            List<Gamedata> gamedata = gamedataMapper.selectList(queryWrapper);
            if(gamedata.size()>0){
                return new ReturnObject("200","获取成功",gamedata);
            }else{
                return new ReturnObject("400","获取失败",gamedata);
            }
        }catch (Exception e){
            return new ReturnObject("400","获取失败",e);
        }


    }
    @PostMapping("/getlevel")
    public ReturnObject getlevel(@RequestBody Map<String,Object> map) {
        int level = Integer.parseInt(map.get("level").toString());
        QueryWrapper<Gamedata> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gamelevel",level);
        List<Gamedata> gamedata = gamedataMapper.selectList(queryWrapper);
        if(gamedata.size()>0){
            return new ReturnObject("200","获取成功",gamedata.size());
        }else{
            return new ReturnObject("400","获取400",gamedata.size());
        }

    }
    @PostMapping("/setfast")
    public ReturnObject setfast(@RequestBody Map<String,Object> map) {
        int level = Integer.parseInt(map.get("level").toString());
        int guanqia = Integer.parseInt(map.get("guanqia").toString());
        int fast = Integer.parseInt(map.get("fast").toString());
        UpdateWrapper<Gamedata> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("gamelevel", level).eq("gameguanqia",guanqia); // 设置更新条件，这里假设根据id进行更新
        updateWrapper.set("fast", fast); // 设置要更新的字段名和新的字段值

        int update = gamedataMapper.update(null, updateWrapper);
        if(update==1){
            return new ReturnObject("200","获取成功",update);
        }
        return new ReturnObject("400","获取400",update);
//        if(update.size()>0){
//            return new ReturnObject("200","获取成功",gamedata.size());
//        }else{
//            return new ReturnObject("400","获取400",gamedata.size());
//        }

    }
    @PostMapping("/count")
    public ReturnObject count(HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();
        QueryWrapper<Gamecount> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id"); // 假设id是你的表中的自增主键或者唯一标识字段
        queryWrapper.last("LIMIT 1"); // 限制只返回一条记录
        List<Gamecount> gameCounts = gamecountMapper.selectList(queryWrapper);
        int count =  gameCounts.get(0).getCount()+1;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(new Date());
        Gamecount gamecount = new Gamecount(null, count, formattedDate,clientIp);
        int insert = gamecountMapper.insert(gamecount);

        return new ReturnObject("200","获取成功！",gameCounts.get(0));

    }


}
