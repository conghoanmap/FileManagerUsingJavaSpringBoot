package com.javaconnectoracle.filemanager.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
    public static String saveFile(String fileName, MultipartFile multipartFile, String folder) throws IOException {
        Path uploadDirecotry = Paths.get("" + folder);

        String fileCode = RandomStringUtils.randomAlphanumeric(8);// Random chuỗi có 8 kí tự

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadDirecotry.resolve(fileCode + "-" + fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            System.out.println("Lỗi: " + ioe.getMessage());
            throw new IOException("Error saving uploaded file:" + fileName, ioe);
        }
        return fileCode;
    }
}
