package com.HamzehAdawi.OllamaApp.services;

import java.io.File;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface ResumeHelpService {
    void resumeAnalysis(File resumeFile, String jobDescription);
    File convert(MultipartFile file) throws IOException;
}
