package com.huistar.huistarapps.Contraller.File;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huistar.huistarapps.Utils.FileUtils;
import com.huistar.huistarapps.UtilsEnty.ReturnObject;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
//@Mapping("/file")
@RequestMapping("/file")
public class FileContraller {
    @PostMapping("/up")
    public ReturnObject upload(String path, @RequestParam("newfile") MultipartFile file, HttpServletRequest request) throws IOException {
        System.out.println(file.toString());
        if(file.isEmpty()){
            return new ReturnObject("404","您未上传任何文件",null);
        }
        try {
            String filename = FileUtils.saveFile(file,path);
            if(filename!=null){
                return new ReturnObject("200","成功上传！",filename);
            }
        }catch (Exception e){
            return new ReturnObject("404","服务器发生未知错误",e);
        }
        return new ReturnObject("200",null,null);
    }
    @PostMapping("/disk")
    public ReturnObject disklist(){
        ArrayList<String> fileList = new ArrayList<>();
        String[] pathSegments = {"src", "main", "resources", "static","file"};

        File newfile = new File(System.getProperty("user.dir"));
        for (String segment : pathSegments) {
            newfile = new File(newfile, segment);
        }
        if(!newfile.exists()){
            newfile.mkdir();
        }
        System.out.println("………………………………………………………………………………………………………………………………");
        System.out.println(newfile.toString());
        System.out.println("………………………………………………………………………………………………………………………………");

        File[] files = newfile.listFiles();
        for (File f:files ) {
            String name = f.getName();
            fileList.add(name);
        }
        return new ReturnObject("200", "获取成功！", fileList);

    }

    @PostMapping("/down")
    public ReturnObject down(@RequestBody Map<String,Object> map, HttpServletResponse response) throws UnsupportedEncodingException {
        String filename = map.get("filename").toString();


        String[] pathSegments = {"src", "main", "resources", "static","file"};

        File newfile = new File(System.getProperty("user.dir"));
        for (String segment : pathSegments) {
            newfile = new File(newfile, segment);
        }
        if(!newfile.exists()){
            newfile.mkdir();
        }
        System.out.println(newfile.toString());


        File file = new File(newfile ,filename);
        System.out.println(file.toString());
        if(file.exists()){
            return new ReturnObject("200","ok","/static/file/"+file.getName());
        }else {
            return new ReturnObject("404","没有找到此文件",null);
        }
    }
    @PostMapping("/del")
    public ReturnObject del(@RequestBody Map<String,Object> map){
        String filename = map.get("filename").toString();


        String[] pathSegments = {"src", "main", "resources", "static","file"};

        File newfile = new File(System.getProperty("user.dir"));
        for (String segment : pathSegments) {
            newfile = new File(newfile, segment);
        }
        if(!newfile.exists()){
            newfile.mkdir();
        }

        File file = new File(newfile , filename);
        if(file.delete()){
            return new ReturnObject("200","删除ok","/static/file/"+file.getName());
        }else {
            return new ReturnObject("404","没有找到此文件",null);
        }
    }

    @PostMapping("/lzyup")
    public ReturnObject convertToForm(@RequestParam("upfile") MultipartFile newfile) throws IOException {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        headers.add("Cookie", "ylogin=" + "1423947");
        headers.add("Cookie", "uag=" + "f09a11db0af308eac7aa3923b61fe41e");
        headers.add("Cookie", "__tins__21412745=" + "%7B%22sid%22%3A%201696751091799%2C%20%22vd%22%3A%202%2C%20%22expires%22%3A%201696752924665%7D%7B%22sid%22%3A%201696751091799%2C%20%22vd%22%3A%202%2C%20%22expires%22%3A%201696752924665%7D");
        headers.add("Cookie", "phpdisk_info=" + "VGFRZwRkDTIFPg5qXjNSAVM3V1wJYQJgBTcBaVVlBzRYalNlB2QEOVRlB14NYgRtAGANOAs2VDJSYlU2BjVUYFRhUWQEMw0xBTYOa15nUjFTMlczCWMCZwU2ATRVZAdhWGtTZwcxBGlUZQdgDV4EbwBlDT4LalQ1UmBVMwY0VGdUYVFg");



        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();

        formData.add("task", "1");
        formData.add("vie", "3");
        formData.add("ve", "2");
        formData.add("id", "WU_FILE_0");
        formData.add("type", "text/plain");
        formData.add("folder_id_bb_n", "-1");



        ByteArrayResource byteArrayResource = new ByteArrayResource(newfile.getBytes()) {
            @Override
            public String getFilename() {
                return newfile.getOriginalFilename();
            }
        };


        Date date = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z '('zzz')'");
        String formattedDate = dateFormat.format(date);
        formData.add("name", newfile.getName());
        formData.add("lastModifiedDate", formattedDate);
        formData.add("size", String.valueOf(newfile.getSize()));

        formData.add("upload_file",byteArrayResource);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(formData, headers);

        String response = restTemplate.postForObject("https://pc.woozooo.com/html5up.php", requestEntity, String.class);

        JSONObject jsonObject = JSON.parseObject(response);

        String state = jsonObject.get("info").toString();
        if(state.equals("上传成功")){
            return new ReturnObject("200","上传成功",null);
        }
        return new ReturnObject("500","上传失败",null);

    }


    @PostMapping("/lzydisk")
    private String lzydisk(@RequestBody Map<String,Object> map) {
        int pg = Integer.parseInt(map.get("pg").toString());
        String url = "https://pc.woozooo.com/doupload.php";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        headers.add("Cookie", "ylogin=" + "1423947");
        headers.add("Cookie", "uag=" + "f09a11db0af308eac7aa3923b61fe41e");
        headers.add("Cookie", "__tins__21412745=" + "%7B%22sid%22%3A%201696751091799%2C%20%22vd%22%3A%202%2C%20%22expires%22%3A%201696752924665%7D%7B%22sid%22%3A%201696751091799%2C%20%22vd%22%3A%202%2C%20%22expires%22%3A%201696752924665%7D");
        headers.add("Cookie", "phpdisk_info=" + "VGFRZwRkDTIFPg5qXjNSAVM3V1wJYQJgBTcBaVVlBzRYalNlB2QEOVRlB14NYgRtAGANOAs2VDJSYlU2BjVUYFRhUWQEMw0xBTYOa15nUjFTMlczCWMCZwU2ATRVZAdhWGtTZwcxBGlUZQdgDV4EbwBlDT4LalQ1UmBVMwY0VGdUYVFg");

        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();

        formData.add("task", "5");
        formData.add("folder_id", "-1");
        formData.add("pg", pg);
        formData.add("vei", "UFRTUVBWBQlXBA5eD1U=");

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(formData, headers);

        String response = restTemplate.postForObject("https://pc.woozooo.com/doupload.php?uid=1423947", requestEntity, String.class);

//        return JSON.parseObject(response);
//        System.out.println(JSON.parseObject(response).get("text").toString());
//        System.out.println(JSON.parseObject(JSON.parseObject(response).get("text").toString()));
        return JSON.parseObject(response).get("text").toString();


    }
    @PostMapping("/lzydown")
    private ReturnObject lzydown(@RequestBody Map<String,Object> map) {
        String fileid = map.get("fileid").toString();

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        headers.add("Cookie", "ylogin=" + "1423947");
        headers.add("Cookie", "uag=" + "f09a11db0af308eac7aa3923b61fe41e");
        headers.add("Cookie", "__tins__21412745=" + "%7B%22sid%22%3A%201696751091799%2C%20%22vd%22%3A%202%2C%20%22expires%22%3A%201696752924665%7D%7B%22sid%22%3A%201696751091799%2C%20%22vd%22%3A%202%2C%20%22expires%22%3A%201696752924665%7D");
        headers.add("Cookie", "phpdisk_info=" + "VGFRZwRkDTIFPg5qXjNSAVM3V1wJYQJgBTcBaVVlBzRYalNlB2QEOVRlB14NYgRtAGANOAs2VDJSYlU2BjVUYFRhUWQEMw0xBTYOa15nUjFTMlczCWMCZwU2ATRVZAdhWGtTZwcxBGlUZQdgDV4EbwBlDT4LalQ1UmBVMwY0VGdUYVFg");

        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();

        formData.add("task", "22");
        formData.add("file_id", fileid);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(formData, headers);

        String response = restTemplate.postForObject("https://pc.woozooo.com/doupload.php", requestEntity, String.class);

        JSONObject info = JSON.parseObject(JSON.parseObject(response).get("info").toString());
        String f_id = info.get("f_id").toString();
        String is_newd = info.get("is_newd").toString();
        String download = is_newd+"/"+f_id;
        return new ReturnObject("200","获取成功!",download);
    }
    @PostMapping("/lzydel")
    private ReturnObject lzydel(@RequestBody Map<String,Object> map) {
        String fileid = map.get("fileid").toString();
        String password = map.get("password").toString();
        if(!password.equals("5201314")){
            return new ReturnObject("200","删除失败!",null);
        }

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        headers.add("Cookie", "ylogin=" + "1423947");
        headers.add("Cookie", "uag=" + "f09a11db0af308eac7aa3923b61fe41e");
        headers.add("Cookie", "__tins__21412745=" + "%7B%22sid%22%3A%201696751091799%2C%20%22vd%22%3A%202%2C%20%22expires%22%3A%201696752924665%7D%7B%22sid%22%3A%201696751091799%2C%20%22vd%22%3A%202%2C%20%22expires%22%3A%201696752924665%7D");
        headers.add("Cookie", "phpdisk_info=" + "VGFRZwRkDTIFPg5qXjNSAVM3V1wJYQJgBTcBaVVlBzRYalNlB2QEOVRlB14NYgRtAGANOAs2VDJSYlU2BjVUYFRhUWQEMw0xBTYOa15nUjFTMlczCWMCZwU2ATRVZAdhWGtTZwcxBGlUZQdgDV4EbwBlDT4LalQ1UmBVMwY0VGdUYVFg");

        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();

        formData.add("task", "6");
        formData.add("file_id", fileid);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(formData, headers);

        String response = restTemplate.postForObject("https://pc.woozooo.com/doupload.php", requestEntity, String.class);

        if((JSON.parseObject(response).get("info").toString().equals("已删除"))){
            return new ReturnObject("200","删除成功!",null);
        }
        return new ReturnObject("200","删除失败!",null);
    }


}
