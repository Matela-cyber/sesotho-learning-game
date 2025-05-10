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
            if (level.equalsIgnoreCase("easy")) {
                questions.add(new Question("Ka u lotha-kang? Nthethe a bina moholo a lutse",
                        new String[]{"Sehlahla", "Pula", "Sefate le Makala", "Lihlahla", "Mokolane"},
                        2, category, level));
                questions.add(new Question("Majoana mabeli mabetsa hole",
                        new String[]{"Mahloa", "Litsebe", "Molala", "Mahlo", "Leleme"},
                        3, category, level));
                questions.add(new Question("Lehalima, lereli le pota motse",
                        new String[]{"Sefefo", "Letšoele", "Namane", "Mokoloko", "Pula"},
                        2, category, level));
                questions.add(new Question("Khare ea leifo",
                        new String[]{"Phiri", "Tau", "Ntja", "Katse", "Mokokobetsi"},
                        2, category, level));
                questions.add(new Question("Se ea le methati",
                        new String[]{"Mopipi", "Lehare", "Mokete", "Lipere", "Thipa"},
                        1, category, level));
            } else if (level.equalsIgnoreCase("medium")) {
                questions.add(new Question("Manchobela, ntlo ea moroa ha e koaloe",
                        new String[]{"Molomo", "Hloho", "Letsoho", "Nko", "Tsebeng"},
                        3, category, level));
                questions.add(new Question("Mosali ea linyao mpeng",
                        new String[]{"Thari", "Letsoho", "Sesiu", "Letheka", "Seaparo"},
                        2, category, level));
                questions.add(new Question("Mala nku marang-rang",
                        new String[]{"Meokho", "Mohloa", "Mahola", "Matlakala", "Litlhaka"},
                        1, category, level));
                questions.add(new Question("Letsa le teetsong hare ke methoto",
                        new String[]{"Litsebe", "Letlalo", "Leihlo", "Linoha", "Leleme"},
                        2, category, level));
                questions.add(new Question("Khomo ea bohali ba 'M 'e e selota mpeng",
                        new String[]{"Lekoko", "Leloala le t'silo", "Mola", "Liphala", "Molamu"},
                        1, category, level));
            } else if (level.equalsIgnoreCase("hard")) {
                questions.add(new Question("Kopo la tlou",
                        new String[]{"Mosifa", "Leballo", "Mokokotlo", "Lekhoakhoa", "Hoofolo"},
                        1, category, level));
                questions.add(new Question("Petje e tsoa bolalu",
                        new String[]{"Selomong", "Mahare", "Selebelo", "Tsoene", "Hlooho"},
                        2, category, level));
                questions.add(new Question("Mafisoana a matle a ngoan'a morena",
                        new String[]{"Molomo", "Mahlo", "Leino", "Seledu", "Liphio"},
                        1, category, level));
                questions.add(new Question("Mahlanya a e-ja khomo a e qeta",
                        new String[]{"Masapo", "Masenke", "Lithakhisa", "Lipeke", "Mapheo"},
                        2, category, level));
                questions.add(new Question("Sehoaba se sa sisinyeheng",
                        new String[]{"Pako", "Selomo", "Leqhaka", "Ntlo", "Mokatong"},
                        1, category, level));
            }
        } else if (category.equalsIgnoreCase("Lipapali")) {
            if (level.equalsIgnoreCase("easy")) {
                questions.add(new Question("Mokou o bapaloa ke bo mang?",
                        new String[]{"Banna", "Bashanyana", "Bacha", "Banana", "Batho bohle"},
                        3, category, level));
                questions.add(new Question("Liketo li bapaloa ke bo mang?",
                        new String[]{"Banana", "Banna", "Bashanyana", "Masole", "Basali"},
                        0, category, level));
                questions.add(new Question("Tleki / Ho kalla e bapaloa ke bo mang?",
                        new String[]{"Banana", "Bacha", "Bashanyana", "Basali", "Batsoali"},
                        2, category, level));
                questions.add(new Question("Kharete e bapaloa ke bo mang?",
                        new String[]{"Basali", "Banana", "Bashanyana", "Banna", "Batho bohle"},
                        2, category, level));
                questions.add(new Question("Selia-lia e bapaloa ke bo mang?",
                        new String[]{"Banna", "Banana feela", "Bashanyana le Banana", "Basali", "Batho bohle"},
                        2, category, level));
            } else if (level.equalsIgnoreCase("medium")) {
                questions.add(new Question("Mohobelo o bapaloa ke bo mang?",
                        new String[]{"Banana", "Basali", "Bashanyana", "Banna", "Bana bohle"},
                        3, category, level));
                questions.add(new Question("Mokopu o bapaloa ke bo mang?",
                        new String[]{"Banna", "Banana", "Bashanyana", "Batho bohle", "Bacha"},
                        1, category, level));
                questions.add(new Question("Mokhibo o bapaloa ke bo mang?",
                        new String[]{"Banna", "Bashanyana", "Basali kapa Banana", "Batsoali", "Masole"},
                        2, category, level));
                questions.add(new Question("Mokallo o bapaloa ke bo mang?",
                        new String[]{"Banana", "Bashanyana", "Basali", "Banna", "Batho bohle"},
                        1, category, level));
                questions.add(new Question("Ndlamo e bapaloa ke bo mang?",
                        new String[]{"Banana", "Bacha", "Basali", "Banna", "Bana ba banyenyane"},
                        3, category, level));
            } else if (level.equalsIgnoreCase("hard")) {
                questions.add(new Question("Papali eo banana le basali ba e bapalang ho bitsa pula",
                        new String[]{"Mokete", "Tjale", "Lesokoana", "Molikilikili", "Khotso"},
                        2, category, level));
                questions.add(new Question("Papali eo bashanyana ba e bapalang e le ho ithuta ho qoba",
                        new String[]{"Khotso-pere", "Litempe", "Seqata-majoana", "Tšepe", "Likhohlopo"},
                        2, category, level));
                questions.add(new Question("Papali eo banana ba bapalang e le ho ithuta ho bala",
                        new String[]{"Moshanyana", "Liketo", "Meqoqo", "Lihloeki", "Lepere"},
                        1, category, level));
                questions.add(new Question("Papali eo bashanyana ba e bapalang e le ho ithuta maqiti",
                        new String[]{"Likhomo", "Moraba-raba", "Molangoana", "Choko", "Matsoho"},
                        1, category, level));
                questions.add(new Question("Papali eo banana ba e bapalang e le ho chorisa kelello",
                        new String[]{"Mehoho", "Cheko", "Setulo sa letsatsi", "Tlolo-masene", "Lekase"},
                        1, category, level));
            }
        } else if (category.equalsIgnoreCase("Maele")) {
            if (level.equalsIgnoreCase("easy")) {
                questions.add(new Question("Ntja peli hae hloloe ke Sebata",
                        new String[]{"Batho ba se nang kelello", "Motho ha hloloe ke moralo oa hae", "Batho ba sa tsebeng ho rera",
                                "Motho ea tšabang", "Ho hloloa ke bohlale"},
                        1, category, level));
                questions.add(new Question("Matsoho a hlatsoana",
                        new String[]{"Batho ba hlatsoang ka metsi", "Batho ba thusana", "Batho ba hlapisoang", "Hanyane ka hanyane",
                                "Batho ba sa tsebeng"},
                        1, category, level));
                questions.add(new Question("Kopano ke matla",
                        new String[]{"Mosebetsi hao kopanetsoe o bobebe", "Batho ba bangata ba sitoa", "Thuto ke matla",
                                "Kopano e baka mathata", "Moshanyana ke matla"},
                        0, category, level));
                questions.add(new Question("Monna ke nku ha lle",
                        new String[]{"Monna ha a hlalose", "Monna o tisitsa mathata", "Monna ha a lumele",
                                "Monna o thabela bohloko", "Monna ha a hlaleke"},
                        1, category, level));
                questions.add(new Question("Tapole e bolisa tse ling",
                        new String[]{"Ho hlapa le batsoali", "Ha motho a tsamaya le batho ba ketso tse mpe le eena o shebahala joalo ka bona", "Motho a se tšoare tapole",
                                "Tapole ke moriana", "Tapole ke motho ea hlomphehang"},
                        1, category, level));
            } else if (level.equalsIgnoreCase("medium")) {
                questions.add(new Question("Ho hloaea tsebe",
                        new String[]{"Ho loma ka tsebe", "Ho bontša ho mamela ka hloko", "Ho tšoara tsebe",
                                "Ho hlaba ka tsebe", "Ho nka qeto"},
                        1, category, level));
                questions.add(new Question("Ho hapa pelo",
                        new String[]{"Ho nka pelo ka likhoka", "Ho bontša ho khahla kapa ho ratwa ke batho",
                                "Ho otla pelo", "Ho ferekana kelellong", "Ho kenya mohopolo"},
                        1, category, level));
                questions.add(new Question("Ho ba le serethe",
                        new String[]{"Ho ba le likobo", "Ho bontša ho ba le leqheka kapa ho ba le moqekoa",
                                "Ho thaba haholo", "Ho ba le botsitso", "Ho ba moriana"},
                        1, category, level));
                questions.add(new Question("Ho ba tsebetutu",
                        new String[]{"Ho bontša ho se utloe litsebeng kapa ho se mamele", "Ho utloa hampe",
                                "Ho senyeha litsebeng", "Ho hlola ho se tsebe", "Ho tšoaroa ke sefuba"},
                        0, category, level));
                questions.add(new Question("Ho betoa ke pelo",
                        new String[]{"Ho bontša ho halefa haholo kapa ho teneha", "Ho otloa ke pelo ea sebete", "Ho utloa bohloko",
                                "Ho tlohela pelo", "Ho ba le khatello"},
                        0, category, level));
            } else if (level.equalsIgnoreCase("hard")) {
                questions.add(new Question("Ha lina motloha pele",
                        new String[]{"Batho ha ba qalle hong", "Liphoofolo ha li na toka", "Batho ba ea pele", "Ha ho motho ea hlonngoang",
                                "Linaha ha li tšeloe ka lebelo"},
                        0, category, level));
                questions.add(new Question("Ha fete khomo leje motho",
                        new String[]{"Khomo ha e lome motho", "Motho a keke a shoa empa hona le khomo e ka mo shoelang",
                                "Khomo e re ha e fete", "Khomo e shoela motho", "Motho a tšoare khomo"},
                        1, category, level));
                questions.add(new Question("Marabe a jeoa ke bana",
                        new String[]{"Bana ba ja litlhare", "Lijo li telloa bana", "Bana ba a bolaoa",
                                "Bana ke marabe", "Marabe a ba batho ba baholo"},
                        1, category, level));
                questions.add(new Question("Ts'oene ha e ipone lekopo",
                        new String[]{"Motho ha a bone liphoso tsa hae empa o bona tsa ba bang", "Ts'oene e bona tsohle",
                                "Ts'oene e tšoere lekopo", "Lekopo ke sefahleho", "Ts'oene e rata ho ithorisa"},
                        0, category, level));
                questions.add(new Question("Ntja ea selahloa le boea",
                        new String[]{"Ntja e shoa e sa hlapa", "Ntja e nyolla bohobe", "Motho ea senang thuso", "Ntja e leleka boea",
                                "Ntja e na le botle bo fosahetseng"},
                        2, category, level));
            }
        } else if (category.equalsIgnoreCase("Lijo")) {
            if (level.equalsIgnoreCase("easy")) {
                questions.add(new Question("Mokopu o tse tsoetsoeng ka letsoai le tsoekerele mafura hao pheoa",
                        new String[]{"Sekele", "Likhetso", "Lehapu", "Lephutsi", "Likhobe"},
                        3, category, level));
                questions.add(new Question("Bohobe bo hehiloeng ka metsi",
                        new String[]{"Ntsoanatsiki", "Motoho", "Leqebelekoane", "Lesheleshele", "Likhobe"},
                        2, category, level));
                questions.add(new Question("Poone e halikuoeng haqeta ya siloa ya tseloa ka tsoekere le letsoai",
                        new String[]{"Lipabi", "Lihoapa", "Likhoabe", "Lehala", "Lehapu"},
                        2, category, level));
                questions.add(new Question("Nama e bekuoeng haqeta ya aneoa",
                        new String[]{"Letlala", "Leqatha", "Lihoapa", "Qati", "Lit'sela"},
                        1, category, level));
                questions.add(new Question("Motoho o phehiloeng ka mofo ya poone",
                        new String[]{"Tsoeu koto", "Mahleu", "Bojoala", "Lebese", "Motoho"},
                        1, category, level));
            } else if (level.equalsIgnoreCase("medium")) {
                questions.add(new Question("Qali e jooa ke bo mang?",
                        new String[]{"Phuthu", "Banana", "Balisana", "Banna", "Mosali"},
                        2, category, level));
                questions.add(new Question("Banana ke lijo life tseo basa tsoanetseng ho lija?",
                        new String[]{"Lephalapha", "Makoenya", "Mahe", "Liphaphatha", "Leqheku"},
                        2, category, level));
                questions.add(new Question("Likahare tsa kolobe li jelloa kae?",
                        new String[]{"Lapeng", "Nokeng", "Sakeng", "Lebaleng", "Mahaheng"},
                        1, category, level));
                questions.add(new Question("Matshela nokana a jooa ke bo mang?",
                        new String[]{"Bomme", "Bonkhono", "Bana", "Banna", "Batho bohle"},
                        1, category, level));
                questions.add(new Question("Sefuba sa phoofolo se jooa ke bo mang?",
                        new String[]{"Lelapa", "Basetsana", "Balisana", "Bomme", "Bontate"},
                        1, category, level));
            } else if (level.equalsIgnoreCase("hard")) {
                questions.add(new Question("Likhobe tsa poone",
                        new String[]{"Poone", "Qhubu", "Linaoa", "Nyekoe", "Leha"},
                        1, category, level));
                questions.add(new Question("Nama e seupeng likarolong tsohle tsa phoofolo e phehoe hore e butsoe haholo",
                        new String[]{"Likuku", "Matlala", "Bohobe", "Likhetso", "Molala"},
                        1, category, level));
                questions.add(new Question("Mali a khakelelitsoeng hao hlaba phofolo e be a phehoa",
                        new String[]{"Bobete", "Mali", "Mahloele", "Likahare", "Libete"},
                        2, category, level));
                questions.add(new Question("Moroho oa Sesotho e kang tlhako ea khomo ,haobela o t'sela phoofo ea papa e be o lubella mmoho",
                        new String[]{"Lekhotloane", "Makoakoa", "Leshoabe", "Seruoe", "Sekele"},
                        3, category, level));
                questions.add(new Question("Bohobe ba phoofo ea poone, e tsoakiloeng lea bohobe",
                        new String[]{"Mochahlama", "Maqebelekoane", "Sekele", "Lehapu", "Tlhapi"},
                        1, category, level));
            }
        } else if (category.equalsIgnoreCase("Puo")) {
            if (level.equalsIgnoreCase("easy")) {
                questions.add(new Question("Fana ka lereho la sehlopha sa boraro: mosali",
                        new String[]{"Lesali", "Mosali", "Besali", "Lisali", "Nosali"},
                        1, category, level));
                questions.add(new Question("Fana ka lereho la sehlopha sa pele",
                        new String[]{"Moshanyana", "Moseme", "Mosamo", "Lesenke", "Kobo"},
                        0, category, level));
                questions.add(new Question("fana ka lereho la sehlopha sa bone",
                        new String[]{"Mesamo", "Molamo", "Sefate", "Lejoe", "Mohloa"},
                        2, category, level));
                questions.add(new Question("Fana ka lereho la sehlopha sa bohlano",
                        new String[]{"Lebese", "Linaoa", "Litapole", "Khati", "Tlhapi"},
                        1, category, level));
                questions.add(new Question("Fana ka lereho la sehlopha sa bobeli",
                        new String[]{"Bashanyana", "Mosali", "Moshanyana", "Ntlo", "Sefate"},
                        0, category, level));
            } else if (level.equalsIgnoreCase("medium")) {
                questions.add(new Question("Fana ka seemeli sa sehlopha sa pele",
                        new String[]{"Eena", "Eane", "Lona", "Bona", "Sona"},
                        0, category, level));
                questions.add(new Question("Fana ka seemeli sa sehlopha sa bobeli",
                        new String[]{"Bona", "Ona", "Sona", "Sane", "Eona"},
                        0, category, level));
                questions.add(new Question("Fana ka seemeli sa sehlopha sa boraro",
                        new String[]{"Ona", "Bane", "Sale", "Bale", "Lona"},
                        3, category, level));
                questions.add(new Question("Fana ka seemeli sa sehlopha sa bone",
                        new String[]{"Eona", "Ona", "Lona", "Sona", "Eena", "Eane"},
                        3, category, level));
                questions.add(new Question("Fana ka seemeli-tsupo sa sehlopha sa bosupa",
                        new String[]{"Eo", "Sane", "Tseno", "Mono", "Tsane"},
                        1, category, level));
            } else if (level.equalsIgnoreCase("hard")) {
                questions.add(new Question("Fana ka bongata ba lereho Sefate",
                        new String[]{"Lifate", "Leholimo", "Patsi", "Mollo", "Lifaha"},
                        0, category, level));
                questions.add(new Question("Fana ka bongata ba lereho mosali",
                        new String[]{"Mofumahali", "Basali", "Basare", "Masali", "Bosali"},
                        1, category, level));
                questions.add(new Question("Fana ka seemeli-tlhakiso-mala sa sehlopha sa leshome le motso o mong: tala-tlaka",
                        new String[]{"Talana", "Tala-tlaka", "Botala", "Tatla-laka", "Ts'oana"},
                        1, category, level));
                questions.add(new Question("Fana ka bongata ba lereho Metsi",
                        new String[]{"Mobu", "Metsi", "Noka", "Lengope", "Leholimo"},
                        1, category, level));
                questions.add(new Question("Fana ka bongata ba lereho lehapu",
                        new String[]{"Mahapu", "Mahe", "Manyai", "Malana", "Lioete"},
                        0, category, level));
            }
        }

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