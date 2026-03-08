package com.HamzehAdawi.OllamaApp.tools;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public final class ResumeParseTool {

    public final int EDUCATION = 0;
    public final int SKILLS = 1;
    public final int EXPERIENCE = 2;
    public final int PROJECTS = 3;
    public final int CERTIFICATIONS = 4;
    public final int AWARDS = 5;
    public final int ACTIVITIES = 6;
    public final int INTERESTS = 7;

    public static final List<String> AFTER_SUMMARY = List.of(
            "education",
            "educational background",
            "academic background",
            "academic history",
            "qualifications",

            "skills",
            "technical skills",
            "core skills",
            "key skills",
            "relevant skills",
            "professional skills",
            "competencies",
            "core competencies",
            "technical competencies",
            "areas of expertise",
            "technical proficiencies",
            "tools",
            "technologies",

            "experience",
            "work experience",
            "professional experience",
            "employment history",
            "work history",
            "career history",
            "professional background",
            "relevant experience",

            "projects",
            "personal projects",
            "academic projects",
            "professional projects",
            "key projects",
            "project experience",

            "certifications",
            "licenses",
            "certifications & licenses",
            "professional certifications",
            "awards",
            "honors",
            "achievements",
            "volunteer experience",
            "leadership",
            "activities",
            "affiliations",
            "professional affiliations",
            "interests",
            "languages"
    );

    public static final List<List<String>> RESUME_PORTIONS = List.of(

            List.of(
                    "education",
                    "educational background",
                    "academic background",
                    "academic history",
                    "qualifications"
            ),

            List.of(
                    "skills",
                    "technical skills",
                    "core skills",
                    "key skills",
                    "relevant skills",
                    "professional skills",
                    "competencies",
                    "core competencies",
                    "technical competencies",
                    "areas of expertise",
                    "technical proficiencies",
                    "tools",
                    "technologies"
            ),

            List.of(
                    "experience",
                    "work experience",
                    "professional experience",
                    "employment history",
                    "work history",
                    "career history",
                    "professional background",
                    "relevant experience"
            ),

            List.of(
                    "projects",
                    "personal projects",
                    "academic projects",
                    "professional projects",
                    "key projects",
                    "project experience"
            ),

            List.of(
                    "certifications",
                    "licenses",
                    "certifications & licenses",
                    "professional certifications"
            ),

            List.of(
                    "awards",
                    "honors",
                    "achievements"
            ),

            List.of(
                    "volunteer experience",
                    "leadership",
                    "activities",
                    "affiliations",
                    "professional affiliations"
            ),

            List.of(
                    "interests",
                    "languages"
            )
    );
}
