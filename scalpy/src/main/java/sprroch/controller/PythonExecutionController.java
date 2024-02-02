package sprroch.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/python")
public class PythonExecutionController {

    @PostMapping
    public String executePythonScript(@RequestBody String pythonScript) throws IOException {
        // 파이썬 스크립트를 실행하는 코드
        Process process = Runtime.getRuntime().exec("python -");

        // 파이썬 스크립트를 실행하여 결과를 받음
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            process.getOutputStream().write(pythonScript.getBytes());
            process.getOutputStream().close();

            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            return output.toString();
        }
    }
}