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
            // RIDDLES (LILOTHO)
            if (level.equalsIgnoreCase("easy")) {
                questions.add(new Question(
                        "Nthethe a bina moholo a lutse",
                        new String[]{"Sehlahla", "Pula", "Sefate le Makala", "Lihlahla", "Mokolane"},
                        2,
                        category, level
                ));
                questions.add(new Question(
                        "Majoana mabeli mabetsa hole",
                        new String[]{"Mahloa", "Litsebe", "Molala", "Mahlo", "Leleme"},
                        3,
                        category, level
                ));
                questions.add(new Question(
                        "Lehalima, lereli le pota motse",
                        new String[]{"Sefefo", "Letšoele", "Namane", "Mokoloko", "Pula"},
                        2,
                        category, level
                ));
            }
            else if (level.equalsIgnoreCase("medium")) {
                questions.add(new Question(
                        "Khare ea leifo",
                        new String[]{"Phiri", "Tau", "Ntja", "Katse", "Mokokobetsi"},
                        2,
                        category, level
                ));
                questions.add(new Question(
                        "Se ea le methati",
                        new String[]{"Mopipi", "Lehare", "Mokete", "Lipere", "Thipa"},
                        1,
                        category, level
                ));
                questions.add(new Question(
                        "Manchobela, ntlo ea moroa ha e koaloe",
                        new String[]{"Molomo", "Hloho", "Letsoho", "Nko", "Tsebeng"},
                        3,
                        category, level
                ));
            }
            else if (level.equalsIgnoreCase("hard")) {
                questions.add(new Question(
                        "Mosali ea linyao mpeng",
                        new String[]{"Thari", "Letsoho", "Sesiu", "Letheka", "Seaparo"},
                        2,
                        category, level
                ));
                questions.add(new Question(
                        "Mala nku marang-rang",
                        new String[]{"Meokho", "Mohloa", "Mahola", "Matlakala", "Litlhaka"},
                        1,
                        category, level
                ));
                questions.add(new Question(
                        "Kopo la tlou",
                        new String[]{"Mosifa", "Leballo", "Mokokotlo", "Lekhoakhoa", "Hoofolo"},
                        1,
                        category, level
                ));
            }
        }
        else if (category.equalsIgnoreCase("Lipapali")) {
            // GAMES (LIPAPALI)
            if (level.equalsIgnoreCase("easy")) {
                questions.add(new Question(
                        "Papali eo banana le basali ba e bapalang ho bitsa pula",
                        new String[]{"Mokete", "Tjale", "Lesokoana", "Molikilikili", "Khotso"},
                        2,
                        category, level
                ));
                questions.add(new Question(
                        "Papali eo bashanyana ba e bapalang e le ho ithuta ho qoba",
                        new String[]{"Khotso-pere", "Litempe", "Seqata-majoana", "Tšepe", "Likhohlopo"},
                        2,
                        category, level
                ));
            }
            else if (level.equalsIgnoreCase("medium")) {
                questions.add(new Question(
                        "Papali eo banana ba bapalang e le ho ithuta ho bala",
                        new String[]{"Moshanyana", "Liketo", "Meqoqo", "Lihloeki", "Lepere"},
                        1,
                        category, level
                ));
                questions.add(new Question(
                        "Papali eo bashanyana ba e bapalang e le ho ithuta maqiti",
                        new String[]{"Likhomo", "Moraba-raba", "Molangoana", "Choko", "Matsoho"},
                        1,
                        category, level
                ));
            }
            else if (level.equalsIgnoreCase("hard")) {
                questions.add(new Question(
                        "Papali eo banana ba e bapalang e le ho chorisa kelello",
                        new String[]{"Mehoho", "Cheko", "Setulo sa letsatsi", "Tlolo-masene", "Lekase"},
                        1,
                        category, level
                ));
            }
        }
        else if (category.equalsIgnoreCase("Maele")) {
            // PROVERBS (MAELE)
            if (level.equalsIgnoreCase("easy")) {
                questions.add(new Question(
                        "Ntja peli hae hloloe ke Sebata",
                        new String[]{"Batho ba se nang kelello", "Motho ha hloloe ke moralo oa hae",
                                "Batho ba sa tsebeng ho rera", "Motho ea tšabang", "Ho hloloa ke bohlale"},
                        1,
                        category, level
                ));
                questions.add(new Question(
                        "Matsoho a hlatsoana",
                        new String[]{"Batho ba hlatsoang ka metsi", "Batho ba thusana",
                                "Batho ba hlapisoang", "Hanyane ka hanyane", "Batho ba sa tsebeng"},
                        1,
                        category, level
                ));
            }
            else if (level.equalsIgnoreCase("medium")) {
                questions.add(new Question(
                        "Ho hloaea tsebe",
                        new String[]{"Ho loma ka tsebe", "Ho bontša ho mamela ka hloko",
                                "Ho tšoara tsebe", "Ho hlaba ka tsebe", "Ho nka qeto"},
                        1,
                        category, level
                ));
                questions.add(new Question(
                        "Ho hapa pelo",
                        new String[]{"Ho nka pelo ka likhoka", "Ho bontša ho khahla kapa ho ratwa ke batho",
                                "Ho otla pelo", "Ho ferekana kelellong", "Ho kenya mohopolo"},
                        1,
                        category, level
                ));
            }
            else if (level.equalsIgnoreCase("hard")) {
                questions.add(new Question(
                        "Ha lina motloha pele",
                        new String[]{"Batho ha ba qalle hong", "Liphoofolo ha li na toka",
                                "Batho ba ea pele", "Ha ho motho ea hlonngoang", "Linaha ha li tšeloe ka lebelo"},
                        0,
                        category, level
                ));
                questions.add(new Question(
                        "Ts'oene ha e ipone lekopo",
                        new String[]{"Motho ha a bone liphoso tsa hae empa o bona tsa ba bang",
                                "Ts'oene e bona tsohle", "Ts'oene e tšoere lekopo",
                                "Lekopo ke sefahleho", "Ts'oene e rata ho ithorisa"},
                        0,
                        category, level
                ));
            }
        }
        else if (category.equalsIgnoreCase("Lijo")) {
            // FOOD (LIJO)
            if (level.equalsIgnoreCase("easy")) {
                questions.add(new Question(
                        "Mabele a kopantsoeng le linaoa haqeta a pehoa",
                        new String[]{"Phuthu", "Likhoho", "Nyekoe", "Likhobe", "Mokopu"},
                        2,
                        category, level
                ));
                questions.add(new Question(
                        "Mokopu o pheheletsoeng le likhaketlana",
                        new String[]{"Lephalapha", "Makoenya", "Likhetso", "Liphaphatha", "Leqheku"},
                        2,
                        category, level
                ));
            }
            else if (level.equalsIgnoreCase("medium")) {
                questions.add(new Question(
                        "Lesheleshele la mabele le phehiloeng ka libese",
                        new String[]{"Mofao", "Lehala", "Nyekoe", "Lijo-thollo", "Mahe"},
                        1,
                        category, level
                ));
                questions.add(new Question(
                        "Poone e halikiloeng ha qeta ea siloa ea ts'eloa ka tsoekere le lets'oai",
                        new String[]{"Lekhoaba", "Lipabi", "Lethoko", "Sejo sa hoseng", "Lehe"},
                        1,
                        category, level
                ));
            }
            else if (level.equalsIgnoreCase("hard")) {
                questions.add(new Question(
                        "Moroho o misitsoeng",
                        new String[]{"Lihare", "Makoabacho", "Leiee", "Letlobo", "Sesepa"},
                        1,
                        category, level
                ));
            }
        }
        else if (category.equalsIgnoreCase("Puo")) {
            // LANGUAGE (PUO)
            if (level.equalsIgnoreCase("easy")) {
                questions.add(new Question(
                        "Fana ka lereho la sehlopha sa boraro: mosali",
                        new String[]{"Lesali", "Mosali", "Besali", "Lisali", "Nosali"},
                        1,
                        category, level
                ));
                questions.add(new Question(
                        "Fana ka bongata ba lereho mosali",
                        new String[]{"Mofumahali", "Basali", "Basare", "Masali", "Bosali"},
                        1,
                        category, level
                ));
            }
            else if (level.equalsIgnoreCase("medium")) {
                questions.add(new Question(
                        "Fana ka lereho la sehlopha sa bohlano: lesale",
                        new String[]{"Lisale", "Lesale", "Sesale", "Mosale", "Basale"},
                        1,
                        category, level
                ));
                questions.add(new Question(
                        "Fana ka seemeli-tsupo sa sehlopha sa bosupa",
                        new String[]{"Eo", "Sane", "Tseno", "Mono", "Tsane"},
                        1,
                        category, level
                ));
            }
            else if (level.equalsIgnoreCase("hard")) {
                questions.add(new Question(
                        "Fana ka seemeli-tlhakiso-mala sa sehlopha sa leshome le motso o mong: tala-tlaka",
                        new String[]{"Talana", "Tala-tlaka", "Botala", "Tatla-laka", "Ts'oana"},
                        1,
                        category, level
                ));
            }
        }

        // Add default question if no questions were added
        if (questions.isEmpty()) {
            questions.add(new Question(
                    "Default question - add more questions",
                    new String[]{"Option 1", "Option 2", "Option 3", "Option 4", "Option 5"},
                    0,
                    category, level
            ));
        }

        QUESTION_CACHE.put(key, questions);
        return questions;
    }
}