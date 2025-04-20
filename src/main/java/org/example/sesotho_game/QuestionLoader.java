package org.example.sesotho_game;

import java.util.*;

public class QuestionLoader {
    private static final Map<String, List<Question>> QUESTION_CACHE = new HashMap<>();

    public static List<Question> loadQuestions(String category, String level) {
        String key = category + ":" + level;
        if (QUESTION_CACHE.containsKey(key)) {
            return QUESTION_CACHE.get(key);
        }

        List<Question> questions = new ArrayList<>();

        if (category.equalsIgnoreCase("Lilotho")) {
            // BASIC PHRASES (LILOTHO)
            if (level.equalsIgnoreCase("easy")) {
                questions.add(new Question(
                        "What is the Sesotho word for 'hello'?",
                        new String[]{"Lumela", "Dumela", "Sawubona", "Avuxeni"},
                        0,
                        "/org/example/sesotho_game/images/greetings.jpg",
                        category, level, false
                ));
                questions.add(new Question(
                        "How do you say 'thank you' in Sesotho?",
                        new String[]{"Ke a leboha", "Ngiyabonga", "Enkosi", "Asante"},
                        0,
                        "/org/example/sesotho_game/images/thanks.jpg",
                        category, level, false
                ));
                questions.add(new Question(
                        "What does 'Ke lapile' mean?",
                        new String[]{"I'm tired", "I'm hungry", "I'm happy", "I'm sick"},
                        1,
                        "/org/example/sesotho_game/images/hungry.jpg",
                        category, level, false
                ));
            }
            else if (level.equalsIgnoreCase("medium")) {
                questions.add(new Question(
                        "How would you say 'Where is the bathroom?'",
                        new String[]{"Ntloana ea boithuso e kae?", "Ke kopa ho tsamaea", "Ke na le bothata", "U phela joang?"},
                        0,
                        "/org/example/sesotho_game/images/bathroom.jpg",
                        category, level, false
                ));
                questions.add(new Question(
                        "What is the correct response to 'U phela joang?'?",
                        new String[]{"Ke phela hantle", "Ke a leboha", "Lumela", "Ke lapile"},
                        0,
                        "/org/example/sesotho_game/images/how_are_you.jpg",
                        category, level, false
                ));
            }
            else if (level.equalsIgnoreCase("hard")) {
                questions.add(new Question(
                        "How would you say 'I would like to order food'?",
                        new String[]{"Ke kopa ho odara lijo", "Ke lapile", "Ke nyaka dijo", "Nka fumana lijo"},
                        0,
                        "/org/example/sesotho_game/images/order_food.jpg",
                        category, level, false
                ));
                questions.add(new Question(
                        "What does 'Ha ke utloisise' mean?",
                        new String[]{"I don't understand", "I don't know", "I don't care", "I don't like it"},
                        0,
                        "/org/example/sesotho_game/images/confused.jpg",
                        category, level, false
                ));
            }
        }
        else if (category.equalsIgnoreCase("Lipapali")) {
            // GAMES (LIPAPALI)
            if (level.equalsIgnoreCase("easy")) {
                questions.add(new Question(
                        "What is 'Morabaraba'?",
                        new String[]{"Traditional board game", "Dancing style", "Type of food", "Children's song"},
                        0,
                        "/org/example/sesotho_game/images/morabaraba.jpg",
                        category, level, false
                ));
            }
            else if (level.equalsIgnoreCase("medium")) {
                questions.add(new Question(
                        "How many pieces does each player have in Morabaraba?",
                        new String[]{"12", "10", "15", "8"},
                        0,
                        "/org/example/sesotho_game/images/morabaraba_pieces.jpg",
                        category, level, false
                ));
            }
            else if (level.equalsIgnoreCase("hard")) {
                questions.add(new Question(
                        "What is the Sesotho name for the game similar to 'Hide and Seek'?",
                        new String[]{"Mantlwane", "Diketo", "Kgati", "Dibeke"},
                        0,
                        "/org/example/sesotho_game/images/hide_seek.jpg",
                        category, level, false
                ));
            }
        }
        else if (category.equalsIgnoreCase("Maele")) {
            // PROVERBS (MAELE)
            if (level.equalsIgnoreCase("easy")) {
                questions.add(new Question(
                        "What does 'Ntja e tla loma ka letsatsi le letona' mean?",
                        new String[]{"The dog will bite on a sunny day", "Prepare for problems in good times", "Dogs are dangerous", "Sunny days are unpredictable"},
                        1,
                        "/org/example/sesotho_game/images/dog_sun.jpg",
                        category, level, false
                ));
            }
            else if (level.equalsIgnoreCase("medium")) {
                questions.add(new Question(
                        "Complete the proverb: 'Ho ja ka ______' (Eating like...)",
                        new String[]{"lehloyo", "ntja", "mohloli", "tsela"},
                        0,
                        "/org/example/sesotho_game/images/eating.jpg",
                        category, level, false
                ));
            }
            else if (level.equalsIgnoreCase("hard")) {
                questions.add(new Question(
                        "What is the meaning of 'Mohloli oa hae ke ntoa'?",
                        new String[]{"His wealth comes from war", "War is destructive", "He fights for wealth", "War brings poverty"},
                        0,
                        "/org/example/sesotho_game/images/war_wealth.jpg",
                        category, level, false
                ));
            }
        }
        else if (category.equalsIgnoreCase("Culture")) {
            // CULTURE
            if (level.equalsIgnoreCase("easy")) {
                questions.add(new Question(
                        "What is the traditional Basotho hat called?",
                        new String[]{"Mokorotlo", "Basotho hat", "Seana Marena", "Tlhaba"},
                        0,
                        "/org/example/sesotho_game/images/mokorotlo.jpg",
                        category, level, false
                ));
            }
            else if (level.equalsIgnoreCase("medium")) {
                questions.add(new Question(
                        "What is the traditional Basotho blanket called?",
                        new String[]{"Seanamarena", "Kobo", "Lehlwe", "Serope"},
                        0,
                        "/org/example/sesotho_game/images/blanket.jpg",
                        category, level, false
                ));
            }
            else if (level.equalsIgnoreCase("hard")) {
                questions.add(new Question(
                        "What is the significance of the Basotho blanket patterns?",
                        new String[]{"They represent different life stages", "They show wealth", "They indicate marital status", "They're just decorative"},
                        0,
                        "/org/example/sesotho_game/images/blanket_patterns.jpg",
                        category, level, false
                ));
            }
        }
        else if (category.equalsIgnoreCase("History")) {
            // HISTORY
            if (level.equalsIgnoreCase("easy")) {
                questions.add(new Question(
                        "Who was the first king of Lesotho?",
                        new String[]{"Moshoeshoe I", "Letsie I", "Moshoeshoe II", "Motlotlehi"},
                        0,
                        "/org/example/sesotho_game/images/moshoeshoe.jpg",
                        category, level, false
                ));
            }
            else if (level.equalsIgnoreCase("medium")) {
                questions.add(new Question(
                        "When did Lesotho gain independence?",
                        new String[]{"1966", "1956", "1976", "1986"},
                        0,
                        "/org/example/sesotho_game/images/independence.jpg",
                        category, level, false
                ));
            }
            else if (level.equalsIgnoreCase("hard")) {
                questions.add(new Question(
                        "What was Lesotho called before independence?",
                        new String[]{"Basutoland", "Bechuanaland", "Sotholand", "Moshoeshoeland"},
                        0,
                        "/org/example/sesotho_game/images/basutoland.jpg",
                        category, level, false
                ));
            }
        }

        // Add more questions as needed
        if (questions.isEmpty()) {
            questions.add(new Question(
                    "Default question - add more questions",
                    new String[]{"Option 1", "Option 2", "Option 3", "Option 4"},
                    0,
                    "/org/example/sesotho_game/images/default.jpg",
                    category, level, false
            ));
        }

        QUESTION_CACHE.put(key, questions);
        return questions;
    }
}