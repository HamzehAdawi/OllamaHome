package com.HamzehAdawi.OllamaApp.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import com.HamzehAdawi.OllamaApp.tools.Prompt;
import com.HamzehAdawi.OllamaApp.tools.ResumeParseTool;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlCursor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ResumeHelpServiceImpl implements ResumeHelpService {

    private final ChatClient chatClient;
    private final Prompt prompt;
    private final ResumeParseTool resumeParseTool;

    @Autowired
    public ResumeHelpServiceImpl(ChatClient.Builder builder, Prompt prompt, ResumeParseTool resumeParseTool) {
        this.resumeParseTool = resumeParseTool;
        ChatMemory memory = MessageWindowChatMemory.builder()
                                                   .maxMessages(10)
                                                   .build();

        MessageChatMemoryAdvisor advisor = MessageChatMemoryAdvisor.builder(memory).build();

        this.chatClient = builder
                .defaultAdvisors(advisor)
                .build();
        this.prompt = prompt;
    }

    // THIS CAN BE CHANGED AND PROBABLY HAS TO FOR YOUR RESUME
    public void resumeAnalysis(File resumeFile, String jobDescription) {

        try (XWPFDocument wordDocument = new XWPFDocument(OPCPackage.open(resumeFile))) {

            String resumeText = getResumeText(wordDocument);
            int i = 0;
            HashMap<Integer, String> updateWithGPT = new HashMap<>();
            ArrayList<String> resumeTextSeen = new ArrayList<>();


            for (XWPFParagraph paragraph : wordDocument.getParagraphs()) {
                resumeTextSeen.add(paragraph.getText());

                String paragraphText = paragraph.getText();

                if (paragraphText.length() > 110 && resumeTextSeen.stream().noneMatch(ResumeParseTool.AFTER_SUMMARY::contains))
                {
                    String aiResponse = chatClient.prompt()
                          .user( u -> u.text(prompt.SUMMARY_PROMPT)
                                       .param("jobDescription",jobDescription)
                                       .param("summary", paragraphText)
                                       .param("resumeText", resumeText))
                          .call()
                          .content();

                    String shortened = chatClient.prompt()
                                       .user(u -> {
                                           assert aiResponse != null;
                                           u.text(prompt.LENGTH_PROMPT)
                                                .param("text", aiResponse);
                                       })
                                       .call()
                                       .content();

                    updateWithGPT.put(i, shortened);
                }
                // IMPLEMENT FINDING SKILL LOGIC
                if (ResumeParseTool.RESUME_PORTIONS.get(resumeParseTool.SKILLS).stream().anyMatch(u -> u.equals(paragraphText.toLowerCase()))) {
                    int j = i+1;
                    XWPFParagraph currParagraph = wordDocument.getParagraphs().get(j);
                    while (j < wordDocument.getParagraphs().size() && !currParagraph.getText().isBlank()) {
                        System.out.println(currParagraph.getText());
                        currParagraph = wordDocument.getParagraphs().get(++j);
                    }
                }
                i++;
            }


            for (int ind:  updateWithGPT.keySet()) {
                XWPFParagraph oldPara = wordDocument.getParagraphArray(ind);

                XmlCursor cursor = oldPara.getCTP().newCursor();
                XWPFParagraph newPara = wordDocument.insertNewParagraph(cursor);
                newPara.createRun().setText(updateWithGPT.get(ind));

                for (XWPFRun run: newPara.getRuns()) {
                    run.setFontFamily("Calibri");
                    run.setFontSize(11);
                }

                wordDocument.removeBodyElement(wordDocument.getPosOfParagraph(oldPara));
            }


//            CHANGE TO DESTINED LOCATION
            FileOutputStream out = new FileOutputStream("CHOOSE A LOCATION TO SAVE TO");
            wordDocument.write(out);
            out.close();

            convertDocxToPDF();


        } catch (IOException e) {
            throw new RuntimeException("Not a valid file.");
        } catch (
                InvalidFormatException |
                InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String getResumeText(XWPFDocument resumeFile) {

        StringBuilder sb = new StringBuilder();
        for (XWPFParagraph paragraph : resumeFile.getParagraphs()) {
            sb.append(paragraph.getText());
        }
        return sb.toString();
    }

    //CHANGE TO LOCATIONS FOR YOU LOCALLY.
    private void convertDocxToPDF() throws IOException, InterruptedException {
        String docxPath = "LOCATION YOU SAVED TO";
        String outputDir = "CHOOSE DIRECTORY";

        // Make sure this path points to your LibreOffice installation
        String officePath = "SET THIS TO LIBREOFFICE PATHFILE";

        ProcessBuilder pb = new ProcessBuilder(
                officePath,
                "--headless",
                "--convert-to", "pdf",
                "--outdir", outputDir,
                docxPath
        );

        Process process = pb.start();
        int exitCode = process.waitFor();

        if (exitCode == 0) {
            System.out.println("PDF conversion successful!");
        } else {
            System.err.println("PDF conversion failed. Exit code: " + exitCode);
        }
    }



}
