package sprroch.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/download")
public class FileDownloadController {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Autowired
    public FileDownloadController(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @GetMapping("/predictions")
    public ResponseEntity<Resource> downloadPredictionsCSV() throws IOException {
        // S3 버킷에서 파일을 읽어옴
        S3Object s3Object = amazonS3.getObject(bucket, "predictions/predictions.csv");
        S3ObjectInputStream inputStream = s3Object.getObjectContent();

        // 응답 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "predictions.csv"); // 다운로드할 때의 파일 이름

        // 파일을 응답으로 전달
        InputStreamResource resource = new InputStreamResource(inputStream);
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }
}