package sprroch.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/upload")
public class FileUploadController {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Autowired
    public FileUploadController(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            System.out.println("Debug message: This is a log message for debugging.");
            return ResponseEntity.badRequest().body("File is empty or not provided");
        }

        try {
            String fileName = "scalpy.jpg"; // 고정된 파일 이름

            String fileUrl = "https://" + bucket + ".s3.amazonaws.com/" + fileName;
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            amazonS3.putObject(bucket, fileName, file.getInputStream(), metadata);
            System.out.println("Debug message: This is a log message for debugging.");

            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Debug message: This is a log message for debugging.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing file upload");
        }
    }
}