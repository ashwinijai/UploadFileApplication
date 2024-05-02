package com.fileupload;

import com.fileupload.config.RestConfig;
import com.fileupload.service.UploadFileService;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.FileInputStream;
import java.io.InputStream;

public class UploadFileMainApplication {
//    java -jar ./Downloads/UploadFileApplication/build/libs/UploadFileApplication-0.0.1-SNAPSHOT.jar BEDegree_AshwiniJayaraman.pdf /Users/ashwinijayaraman/Downloads
    public static void main(String [] args){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(RestConfig.class);
        UploadFileService service = applicationContext.getBean(UploadFileService.class);
        System.out.println("Main method execution");
        String fileName = args[0];
        String filePath  = args[1];
        String finalFilePath = filePath+"/"+fileName;
        System.out.println("FilePath is - "+finalFilePath);
        if(!finalFilePath.isBlank()){
           // try (InputStream is = UploadFileMainApplication.class.getClassLoader().getResourceAsStream(finalFilePath)) {
            try (InputStream is = new FileInputStream(finalFilePath)){
                byte[] docContent = IOUtils.toByteArray(is);
                String response = service.callFileUploadProducer(fileName, docContent);
                System.out.println("Response from other service - "+response);
            }
            catch(Exception e){
               System.out.println("Exception in reading file from path provided - "+ e.getMessage());
            }
        }
        else{
            System.out.println("No file name sent in command line ");
        }


    }
}
