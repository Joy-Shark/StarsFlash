package com.huistar.huistarapps.Utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUtils {
    public static String saveFile(MultipartFile file,String savepath) throws IOException {

//        File dir = new File(System.getProperty("user.dir"),"src","main","resources","static",savepath);

        String[] pathSegments = {"src", "main", "resources", "static",savepath};

        File newfile = new File(System.getProperty("user.dir"));
        for (String segment : pathSegments) {
            newfile = new File(newfile, segment);
        }


        if(!newfile.exists()){
            newfile.mkdir();
        }

        File xfile = new File(newfile ,file.getOriginalFilename());
        file.transferTo(xfile);

        return xfile.toString();
    }
    public static File getStaticPath(String path){
        String[] pathSegments = {"src", "main", "resources", "static"};

        File newfile = new File(System.getProperty("user.dir"));
        for (String segment : pathSegments) {
            newfile = new File(newfile, segment);
        }
        if(!newfile.exists()){
            newfile.mkdir();
        }
        return newfile;
    }


}
