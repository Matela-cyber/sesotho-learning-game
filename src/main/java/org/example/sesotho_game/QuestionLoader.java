package org.example.sesotho_game;

import java.util.*;

public class QuestionLoader {
    private static final Map<String, List<Question>> QUESTION_CACHE = new HashMap<>();

    public static List<Question> loadQuestions(String category, String level) {
        String key = category + ":" + level;
        if (QUESTION_CACHE.containsKey(key)) {
            return QUESTION_CACHE.get(key);
        }

        // In a real app, you would load these from a file or database
        List<Question> questions = new ArrayList<>();

        // Sample questions - replace with your actual content
        if (category.equalsIgnoreCase("Lilotho")) {
            if (level.equalsIgnoreCase("easy")) {
                questions.add(new Question(
                        "What is the Sesotho word for 'hello'?",
                        new String[]{"Lumela", "Dumela", "Sawubona", "Avuxeni"},
                        0,
                        "/org/example/sesotho_game/images/hello.jpg",
                        category, level, false
                ));
                questions.add(new Question(
                        "How do you say 'thank you' in Sesotho?",
                        new String[]{"Ke a leboha", "Ngiyabonga", "Enkosi", "Asante"},
                        0,
                        "/org/example/sesotho_game/images/thanks.jpg",
                        category, level, false
                ));
            }
            // Add more questions for other levels and categories
        }

        QUESTION_CACHE.put(key, questions);
        return questions;
    }
}