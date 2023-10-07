package controller;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;



@RestController
public class Controller {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
    @PostMapping("upload")
    public String upload(MultipartFile uploadFile, HttpServletRequest req) {
        String realPath = req.getSession().getServletContext().getRealPath("/uploadFile/");
        String format = sdf.format(new Date());
        File folder = new File(realPath+format);
        if (!folder.isDirectory()) {folder.mkdirs();}
        String oldName = uploadFile.getOriginalFilename();
        String newName = null;
        if (oldName != null) {
            newName = UUID.randomUUID() +oldName.substring(oldName.lastIndexOf("."),oldName.length());
        }
        try{
            if (newName != null) {
                uploadFile.transferTo(new File(folder,newName));
            }
            String filePath = req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+"/uploadFile/"+format + newName;
        return filePath;
    }catch (IOException e){
            logger.error("发生了IO异常", e);
    }


        return "上传失败！";
    }

}
