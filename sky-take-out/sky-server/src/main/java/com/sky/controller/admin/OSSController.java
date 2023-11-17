package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.MinioOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

@Slf4j
@RestController
@RequestMapping("/admin/common1")
@Api(tags = "通用接口Minio")
public class OSSController {

    @Autowired
    private MinioOssUtil minioOssUtil;

    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file){
        log.info("上传图片");
        return Result.success("https://images.pexels.com/photos/2286895/pexels-photo-2286895.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2");
//        try {
//            //文件名
//            String fileName = multipartFile.getOriginalFilename();
//            log.info("上传文件原始名称,{}",fileName);
//            String newFileName = System.currentTimeMillis() + "." + StringUtils.substringAfterLast(fileName, ".");
//            log.info("上传文件新名称,{}",newFileName);
//            //类型
//            String contentType = multipartFile.getContentType();
//            minioOssUtil.uploadFile(multipartFile, newFileName, contentType);
//            log.error("上传成功");
//            return Result.success("上传成功");
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error("上传失败");
//            log.error(e.toString());
//            return Result.error(MessageConstant.UPLOAD_FAILED);
//        }
    }
    /**
     * 删除
     *
     * @param fileName
     */

    @DeleteMapping("/")
    @ApiOperation("删除文件")
    public void delete(@RequestParam("fileName") String fileName) {
        minioOssUtil.removeFile(fileName);
    }

    /**
     * 获取文件信息
     *
     * @param fileName
     * @return
     */
    @GetMapping("/info")
    @ApiOperation("获取文件信息")
    public String getFileStatusInfo(@RequestParam("fileName") String fileName) {
        return minioOssUtil.getFileStatusInfo(fileName);
    }

    /**
     * 获取文件外链
     *
     * @param fileName
     * @return
     */
    @GetMapping("/url")
    @ApiOperation("获取文件外链")
    public String getPresignedObjectUrl(@RequestParam("fileName") String fileName) {
        return minioOssUtil.getPresignedObjectUrl(fileName);
    }

    /**
     * 文件下载
     *
     * @param fileName
     * @param response
     */
    @GetMapping("/download")
    @ApiOperation("文件下载")
    public void download(@RequestParam("fileName") String fileName, HttpServletResponse response) {
        try {
            InputStream fileInputStream = minioOssUtil.getObject(fileName);
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setContentType("application/force-download");
            response.setCharacterEncoding("UTF-8");
            IOUtils.copy(fileInputStream, response.getOutputStream());
        } catch (Exception e) {
            log.error(MessageConstant.UPLOAD_FAILED);

        }
    }
}
