package com.HamzehAdawi.OllamaApp.controllers;

import com.HamzehAdawi.OllamaApp.services.MessagesServiceImpl;
import com.HamzehAdawi.OllamaApp.services.OllamaServiceImpl;
import com.HamzehAdawi.OllamaApp.services.ResumeHelpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RefreshScope
@RequestMapping("/")
@Controller()
public class OllamaController {

    private final OllamaServiceImpl ollamaServiceImpl;
    private final MessagesServiceImpl messagesServiceImpl;
    private final ResumeHelpServiceImpl resumeHelpServiceImpl;

    @Autowired
    public OllamaController(OllamaServiceImpl ollamaServiceImpl, MessagesServiceImpl messagesServiceImpl,
                            ResumeHelpServiceImpl resumeHelpServiceImpl) {
        this.ollamaServiceImpl = ollamaServiceImpl;
        this.messagesServiceImpl = messagesServiceImpl;
        this.resumeHelpServiceImpl = resumeHelpServiceImpl;
    }

    @GetMapping({"/home", "/"})
    public String homePage(Model model, @RequestParam(required = false) String selectedModel) {

        System.out.println(selectedModel);
        if (selectedModel == null) {
            selectedModel = "qwen2.5";
        }

        if (!selectedModel.equals(ollamaServiceImpl.getActiveModel())) {
            messagesServiceImpl.getConversation().clear();
        }

        ollamaServiceImpl.setActiveModel(selectedModel);
        model.addAttribute("ollamaModel", selectedModel);

        ArrayList<String> list = new ArrayList<>();
        try {
            list.add("Ollama: "+ ollamaServiceImpl.chat("give a short one sentence friendly greeting. " +
                    "Nothing else"));
        } catch (
                Exception e) {
            model.addAttribute("ollamaModel", "None Detected");
            System.err.println("Please ensure an Ollama model is running.");
        }
        model.addAttribute("messageList", list);

        return "dashboard";
    }
    @PostMapping()
    public String generate(Model model, @ModelAttribute("input") String input) {

        model.addAttribute("ollamaModel", ollamaServiceImpl.getActiveModel());

        messagesServiceImpl.addUserMessage(input);
        messagesServiceImpl.addOllamaResponse(ollamaServiceImpl.chat(input));

        List<String> messageList = messagesServiceImpl.getConversation();
        model.addAttribute("messageList", messageList);

        return "dashboard";
    }

    @GetMapping("/Resume")
    public String getResumePage() {
        return "resume";
    }

    @PostMapping("/Resume")
    public String analyzeResume(
            @RequestParam("resumeFile") MultipartFile resumeFile,
            @ModelAttribute("jobDescription") String jobDescription) throws IOException {

        File resume = resumeHelpServiceImpl.convert(resumeFile);
        resumeHelpServiceImpl.resumeAnalysis(resume, jobDescription);

        return "resume";
    }
}
