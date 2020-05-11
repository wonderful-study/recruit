package com.hzu.recruit.biz.service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

@Service
public class FileService {

    @Value("${file.path}")
    private String filePath;

    //将上传文件列表返回一个列表路径
    public List<String> getImgPath(List<MultipartFile> files) {
        if(Strings.isNullOrEmpty(filePath)) {
            filePath = getResourcePath();
        }
        List<String> paths = Lists.newArrayList();
//        files.forEach(file -> {
//            File localFile = null;
//            if (!file.isEmpty()) {
//                try {
//                   //把上传文件保存到本地
//                    localFile = saveToLocal(file,filePath);
//                    //只保存最后的相对路径，前面的绝对路径去掉
//                    String path = StringUtils.substringAfterLast(localFile.getAbsolutePath(),filePath);
//                    paths.add(path);
//                }catch (Exception e) {
//                    throw new IllegalArgumentException(e);
//                }
//            }
//        });
        MultipartFile multipartFile = files.get(0);
        File localFile = null;
        if(!multipartFile.isEmpty()) {
            try {
                   //把上传文件保存到本地
                    localFile = saveToLocal(multipartFile,filePath);
                    //只保存最后的相对路径，前面的绝对路径去掉
                    String path = StringUtils.substringAfterLast(localFile.getAbsolutePath(),"D:\\project\\recruit\\recruit-web\\src\\main\\resources\\static\\images");
                    paths.add(path);
                }catch (Exception e) {
                    throw new IllegalArgumentException(e);
                }
        }
        return paths;
    }

    private static String getResourcePath() {
        File file = new File(".");
        String absolutePath = file.getAbsolutePath();
        return absolutePath;
    }

    private File saveToLocal(MultipartFile file, String filePath2) throws IOException {
        //获取一个以秒为单位的时间戳
        File newFile = new File(filePath + "/" + Instant.now().getEpochSecond() + "/" + file.getOriginalFilename());
        if (!newFile.exists()) {
            //创建上级目录
            newFile.getParentFile().mkdirs();
            newFile.createNewFile();
        }
        //把上传的文件写到刚刚创建的newFile
        Files.write(file.getBytes(),newFile);
        return newFile;
    }


    //---------------------------------------重写上面的----------------------------------------------------------------------

    public List<String> getImageList(List<MultipartFile> imageFiles) {
        if (Strings.isNullOrEmpty(filePath)) {
            filePath = getResourcePath();
        }
        List<String> paths = Lists.newArrayList();
        long instant = Instant.now().getEpochSecond();
        imageFiles.forEach(imageFile ->{
            File localFile = null;
            if (!imageFile.isEmpty()) {
                try {
                    localFile = saveListTOLocal(imageFile,filePath,instant);
                    String path = StringUtils.substringAfterLast(localFile.getAbsolutePath(),"D:\\project\\recruit\\recruit-web\\src\\main\\resources\\static\\images");
                    paths.add(path);
                }catch (Exception e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
        return paths;
    }

    private File saveListTOLocal(MultipartFile imageFile, String filePath2,long instant) throws IOException {
        File newFile = new File(filePath + "/" + instant + "/" + Instant.now().getEpochSecond() + imageFile.getOriginalFilename() );
        if (!newFile.exists()) {
            newFile.getParentFile().mkdirs();
            newFile.createNewFile();
        }
        Files.write(imageFile.getBytes(),newFile);
        return newFile;
    }
}
