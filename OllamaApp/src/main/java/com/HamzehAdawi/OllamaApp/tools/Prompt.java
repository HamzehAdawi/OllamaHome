package com.HamzehAdawi.OllamaApp.tools;

import org.springframework.stereotype.Component;

@Component
public final class Prompt {

    public final String SUMMARY_PROMPT =
            """
            You are rewriting a resume summary.
            
            INPUTS
            Resume Summary:
            {summary}
            
            Job Description:
            {jobDescription}
            
            RULES
            1. Output ONLY the rewritten summary.
            2. No explanations.
            3. No bullet points.
            4. No quotes.
            5. Keep the same tone and general structure.
            6. Do NOT invent or assume experience.
            7. Maximum 239 characters.
            8. If the result exceeds 239 characters, rewrite it until it is ≤239 characters.
            
            TASK
            Rewrite the resume summary so it better aligns with the job description while following all rules.
            """;

    public final String LENGTH_PROMPT =
            """
            Shorten the following resume summary to 239 characters or less.
            
            Rules:
            - Output ONLY the shortened summary.
            - No explanations.
            - Do not change meaning.
            - Do not add new information.
            - Maximum 239 characters.
            
            Summary:
            {text}
            """;
}
