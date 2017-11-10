package com.rrd.controll;

import com.rrd.constant.FileConfig;
import com.rrd.pjo.Result;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件长传类
 * Created by xinghb on 2017/10/20.
 */
@RestController
@RequestMapping("upload")
public class UploadControll {

    @RequestMapping("productimg")
    public Result productimg(@RequestParam(value = "file") MultipartFile file) throws Exception {
        //将文件存入指定路径中
        String fileName = file.getOriginalFilename();
        //生成随机文件名称
        String tempFileName = UUID.randomUUID().toString().substring(0, 8) + "_" + fileName;
        File sourceFile = new File(FileConfig.FILE_PRUDDIR + File.separator + tempFileName);
        if(file.getSize() > 1024 * 1024) { // 1M
            throw new Exception("上传文件过大");
        }

        if (!sourceFile.exists()) {
            sourceFile.mkdirs();
        }
        file.transferTo(sourceFile);
        return new Result(tempFileName);
    }

    @RequestMapping("removeImgFile")
    public Result removeImgFile(String filename) {
        File file = new File(FileConfig.FILE_PRUDDIR + File.separator + filename);
        if (file.exists()) {
            file.delete();
        }
        return new Result();
    }

    @RequestMapping("BookProve")
    public Result BookProve(@RequestParam(value = "file") MultipartFile file) throws IOException {
        //将文件存入指定路径中
        String fileName = file.getOriginalFilename();
        //生成随机文件名称
        String tempFileName = UUID.randomUUID().toString().substring(0, 8) + "_" + fileName;
        File sourceFile = new File(FileConfig.FILE_BOOK + File.separator + tempFileName);
        if (!sourceFile.exists()) {
            sourceFile.mkdirs();
        }
        file.transferTo(sourceFile);
        return new Result(tempFileName);
    }

    @RequestMapping("removeFile")
    public Result removeFile(String filename) {
        File file = new File(FileConfig.FILE_BOOK + File.separator + filename);
        if(file.exists()) {
            file.delete();
        }
        return new Result();
    }

    /**
     * 下载证明文件
     */
    @RequestMapping("downProvefile")
    public ResponseEntity<byte[]> downProvefile(String provefile) throws Exception {
        System.out.println(provefile);
        File file = new File(FileConfig.FILE_BOOK + File.separator + provefile);
        if(!file.exists()) {
            throw new Exception("文件缺失!");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", provefile);
        return new ResponseEntity<>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
    }
}
