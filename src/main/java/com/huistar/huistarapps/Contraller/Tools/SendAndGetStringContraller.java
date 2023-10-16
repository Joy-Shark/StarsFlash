package com.huistar.huistarapps.Contraller.Tools;

import com.huistar.huistarapps.UtilsEnty.ReturnObject;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@RestController
@RequestMapping("/huistartools")
public class SendAndGetStringContraller {
    private String string = "";
    @PostMapping("/sendstring")
    public ReturnObject sendString(@RequestBody Map<String,Object> map){
        String string = map.get("string").toString();
        this.string = string;
//        String filePath =  System.getProperty("user.dir") + "\\src\\main\\resources\\static\\huistartools\\sendAndGetString.txt";
//
//        Path file = Paths.get(filePath);
//
//        try {
//
//            Files.createDirectories(file.getParent());
//
//            Files.createFile(file);
//
//            System.out.println("File and directories created successfully.");
//        } catch (IOException e) {
//            System.err.println("Failed to create file and directories: " + e.getMessage());
//        }
//
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "\\src\\main\\resources\\static\\huistartools\\sendAndGetString.txt"))) {
//            writer.write(string);
            return new ReturnObject("200","上传成功!",string);
//        } catch (IOException e) {
//
//            return new ReturnObject("200","传递发生未知错误!",e.toString());
//        }

    }
    @GetMapping("/getstring")
    public ReturnObject getstring(){
//        String stringPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\huistartools\\sendAndGetString.txt";
//
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(stringPath), "UTF-8"))) {
//            StringBuilder content = new StringBuilder();
//            String line;
//            while ((line = reader.readLine()) != null) {
//                content.append(line);
//            }
//            String contentString = content.toString();
            return new ReturnObject("200", "获取成功!", string);
//        } catch (IOException e) {
//            return new ReturnObject("200", "获取字符串发生未知错误!", e.toString());
//        }
    }
}
