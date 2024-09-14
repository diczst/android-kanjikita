package com.neonusa.belajarkanjijlpt.utils

import com.neonusa.belajarkanjijlpt.data.model.HiraganaKatakanaItem
import com.neonusa.belajarkanjijlpt.data.model.KanjiItem
import com.neonusa.belajarkanjijlpt.data.model.KanjiSubitem

fun hiraganaGenerator(): List<HiraganaKatakanaItem> {
    val hiraganaData = listOf(
        HiraganaKatakanaItem("あ", "a"),
        HiraganaKatakanaItem("い", "i"),
        HiraganaKatakanaItem("う", "u"),
        HiraganaKatakanaItem("え", "e"),
        HiraganaKatakanaItem("お", "o"),
        HiraganaKatakanaItem("か", "ka"),
        HiraganaKatakanaItem("き", "ki"),
        HiraganaKatakanaItem("く", "ku"),
        HiraganaKatakanaItem("け", "ke"),
        HiraganaKatakanaItem("こ", "ko"),
        HiraganaKatakanaItem("さ", "sa"),
        HiraganaKatakanaItem("し", "shi"),
        HiraganaKatakanaItem("す", "su"),
        HiraganaKatakanaItem("せ", "se"),
        HiraganaKatakanaItem("そ", "so"),
        HiraganaKatakanaItem("た", "ta"),
        HiraganaKatakanaItem("ち", "chi"),
        HiraganaKatakanaItem("つ", "tsu"),
        HiraganaKatakanaItem("て", "te"),
        HiraganaKatakanaItem("と", "to"),
        HiraganaKatakanaItem("な", "na"),
        HiraganaKatakanaItem("に", "ni"),
        HiraganaKatakanaItem("ぬ", "nu"),
        HiraganaKatakanaItem("ね", "ne"),
        HiraganaKatakanaItem("の", "no"),
        HiraganaKatakanaItem("は", "ha"),
        HiraganaKatakanaItem("ひ", "hi"),
        HiraganaKatakanaItem("ふ", "fu"),
        HiraganaKatakanaItem("へ", "he"),
        HiraganaKatakanaItem("ほ", "ho"),
        HiraganaKatakanaItem("ま", "ma"),
        HiraganaKatakanaItem("み", "mi"),
        HiraganaKatakanaItem("む", "mu"),
        HiraganaKatakanaItem("め", "me"),
        HiraganaKatakanaItem("も", "mo"),
        HiraganaKatakanaItem("や", "ya"),
        HiraganaKatakanaItem("", ""),
        HiraganaKatakanaItem("ゆ", "yu"),
        HiraganaKatakanaItem("", ""),
        HiraganaKatakanaItem("よ", "yo"),
        HiraganaKatakanaItem("ら", "ra"),
        HiraganaKatakanaItem("り", "ri"),
        HiraganaKatakanaItem("る", "ru"),
        HiraganaKatakanaItem("れ", "re"),
        HiraganaKatakanaItem("ろ", "ro"),
        HiraganaKatakanaItem("わ", "wa"),
        HiraganaKatakanaItem("", ""),
        HiraganaKatakanaItem("を", "wo"),
        HiraganaKatakanaItem("", ""),
        HiraganaKatakanaItem("ん", "n")
    )
    return hiraganaData
}

fun katakanaGenerator(): List<HiraganaKatakanaItem> {
    val katakanaData = listOf(
        HiraganaKatakanaItem("ア", "a"),
        HiraganaKatakanaItem("イ", "i"),
        HiraganaKatakanaItem("ウ", "u"),
        HiraganaKatakanaItem("エ", "e"),
        HiraganaKatakanaItem("オ", "o"),
        HiraganaKatakanaItem("カ", "ka"),
        HiraganaKatakanaItem("キ", "ki"),
        HiraganaKatakanaItem("ク", "ku"),
        HiraganaKatakanaItem("ケ", "ke"),
        HiraganaKatakanaItem("コ", "ko"),
        HiraganaKatakanaItem("サ", "sa"),
        HiraganaKatakanaItem("シ", "shi"),
        HiraganaKatakanaItem("ス", "su"),
        HiraganaKatakanaItem("セ", "se"),
        HiraganaKatakanaItem("ソ", "so"),
        HiraganaKatakanaItem("タ", "ta"),
        HiraganaKatakanaItem("チ", "chi"),
        HiraganaKatakanaItem("ツ", "tsu"),
        HiraganaKatakanaItem("テ", "te"),
        HiraganaKatakanaItem("ト", "to"),
        HiraganaKatakanaItem("ナ", "na"),
        HiraganaKatakanaItem("ニ", "ni"),
        HiraganaKatakanaItem("ヌ", "nu"),
        HiraganaKatakanaItem("ネ", "ne"),
        HiraganaKatakanaItem("ノ", "no"),
        HiraganaKatakanaItem("ハ", "ha"),
        HiraganaKatakanaItem("ヒ", "hi"),
        HiraganaKatakanaItem("フ", "fu"),
        HiraganaKatakanaItem("ヘ", "he"),
        HiraganaKatakanaItem("ホ", "ho"),
        HiraganaKatakanaItem("マ", "ma"),
        HiraganaKatakanaItem("ミ", "mi"),
        HiraganaKatakanaItem("ム", "mu"),
        HiraganaKatakanaItem("メ", "me"),
        HiraganaKatakanaItem("モ", "mo"),
        HiraganaKatakanaItem("ヤ", "ya"),
        HiraganaKatakanaItem("", ""),
        HiraganaKatakanaItem("ユ", "yu"),
        HiraganaKatakanaItem("", ""),
        HiraganaKatakanaItem("ヨ", "yo"),
        HiraganaKatakanaItem("ラ", "ra"),
        HiraganaKatakanaItem("リ", "ri"),
        HiraganaKatakanaItem("ル", "ru"),
        HiraganaKatakanaItem("レ", "re"),
        HiraganaKatakanaItem("ロ", "ro"),
        HiraganaKatakanaItem("ワ", "wa"),
        HiraganaKatakanaItem("", ""),
        HiraganaKatakanaItem("ヲ", "wo"),
        HiraganaKatakanaItem("", ""),
        HiraganaKatakanaItem("ン", "n")
    )
    return katakanaData
}

fun generateDummyKOTD(): List<KanjiSubitem>{
    val subItemData = listOf(
        // Subitems for KanjiItem with ID 1 ("生")
        KanjiSubitem(1, 1, "生", "せい", "sei", "Hidup"),
        KanjiSubitem(2, 1, "生まれる", "うまれる", "umareru", "Dilahirkan"),
        KanjiSubitem(3, 1, "生きる", "いきる", "ikiru", "Hidup"),
        KanjiSubitem(4, 1, "生命", "せいめい", "seimei", "Kehidupan"),
        KanjiSubitem(5, 1, "生産", "せいさん", "seisan", "Produksi"),

        // Subitems for KanjiItem with ID 2 ("学")
        KanjiSubitem(6, 2, "学", "がく", "gaku", "Pembelajaran"),
        KanjiSubitem(7, 2, "学生", "がくせい", "gakusei", "Pelajar"),
        KanjiSubitem(8, 2, "学校", "がっこう", "gakkou", "Sekolah"),
        KanjiSubitem(9, 2, "学年", "がくねん", "gakunen", "Tahun ajaran"),
        KanjiSubitem(10, 2, "学習", "がくしゅう", "gakushuu", "Pembelajaran"),

        // Subitems for KanjiItem with ID 3 ("人")
        KanjiSubitem(11, 3, "人", "ひと", "hito", "Orang"),
        KanjiSubitem(12, 3, "人口", "じんこう", "jinkou", "Populasi"),
        KanjiSubitem(13, 3, "人生", "じんせい", "jinsei", "Kehidupan manusia"),
        KanjiSubitem(14, 3, "人事", "じんじ", "jinji", "Urusan personalia"),
        KanjiSubitem(15, 3, "人間", "にんげん", "ningen", "Manusia"),

        // Subitems for KanjiItem with ID 4 ("水")
        KanjiSubitem(16, 4, "水", "みず", "mizu", "Air"),
        KanjiSubitem(17, 4, "水道", "すいどう", "suidou", "Sistem air"),
        KanjiSubitem(18, 4, "水力", "すいりょく", "suiryoku", "Tenaga air"),
        KanjiSubitem(19, 4, "水中", "すいちゅう", "suichuu", "Di dalam air"),
        KanjiSubitem(20, 4, "水分", "すいぶん", "suibun", "Kandungan air"),

        // Subitems for KanjiItem with ID 5 ("火")
        KanjiSubitem(21, 5, "火", "ひ", "hi", "Api"),
        KanjiSubitem(22, 5, "火山", "かざん", "kazan", "Gunung berapi"),
        KanjiSubitem(23, 5, "火災", "かさい", "kasai", "Kebakaran"),
        KanjiSubitem(24, 5, "火力", "かりょく", "karyoku", "Tenaga api"),
        KanjiSubitem(25, 5, "火曜日", "かようび", "kayoubi", "Hari Selasa"),

        // Subitems for KanjiItem dengan ID 6 ("木")
        KanjiSubitem(26, 6, "木", "き", "ki", "Pohon"),
        KanjiSubitem(27, 6, "木材", "もくざい", "mokuzai", "Kayu"),
        KanjiSubitem(28, 6, "木立", "こだち", "kodachi", "Hutan kecil"),
        KanjiSubitem(29, 6, "木陰", "こかげ", "kokage", "Bayangan pohon"),
        KanjiSubitem(30, 6, "木曜日", "もくようび", "mokuyoubi", "Hari Kamis")
    )
    return subItemData
}


fun generateDummyKanjiData(): List<KanjiItem>{
    val kanjiData = listOf(
        KanjiItem(1, "生", "Kehidupan"),
        KanjiItem(2, "学", "Belajar"),
        KanjiItem(3, "人", "Orang"),
        KanjiItem(4, "水", "Air"),
        KanjiItem(5, "火", "Api"),
        KanjiItem(6, "木", "Pohon")
    )
    return kanjiData
}

fun generateDummySubitemKanjiData(): List<KanjiSubitem>{
    val subItemData = listOf(
        // Subitems for KanjiItem with ID 1 ("生")
        KanjiSubitem(1, 1, "生", "せい", "sei", "Hidup"),
        KanjiSubitem(2, 1, "生まれる", "うまれる", "umareru", "Dilahirkan"),
        KanjiSubitem(3, 1, "生きる", "いきる", "ikiru", "Hidup"),
        KanjiSubitem(4, 1, "生命", "せいめい", "seimei", "Kehidupan"),
        KanjiSubitem(5, 1, "生産", "せいさん", "seisan", "Produksi"),

        // Subitems for KanjiItem with ID 2 ("学")
        KanjiSubitem(6, 2, "学", "がく", "gaku", "Pembelajaran"),
        KanjiSubitem(7, 2, "学生", "がくせい", "gakusei", "Pelajar"),
        KanjiSubitem(8, 2, "学校", "がっこう", "gakkou", "Sekolah"),
        KanjiSubitem(9, 2, "学年", "がくねん", "gakunen", "Tahun ajaran"),
        KanjiSubitem(10, 2, "学習", "がくしゅう", "gakushuu", "Pembelajaran"),

        // Subitems for KanjiItem with ID 3 ("人")
        KanjiSubitem(11, 3, "人", "ひと", "hito", "Orang"),
        KanjiSubitem(12, 3, "人口", "じんこう", "jinkou", "Populasi"),
        KanjiSubitem(13, 3, "人生", "じんせい", "jinsei", "Kehidupan manusia"),
        KanjiSubitem(14, 3, "人事", "じんじ", "jinji", "Urusan personalia"),
        KanjiSubitem(15, 3, "人間", "にんげん", "ningen", "Manusia"),

        // Subitems for KanjiItem with ID 4 ("水")
        KanjiSubitem(16, 4, "水", "みず", "mizu", "Air"),
        KanjiSubitem(17, 4, "水道", "すいどう", "suidou", "Sistem air"),
        KanjiSubitem(18, 4, "水力", "すいりょく", "suiryoku", "Tenaga air"),
        KanjiSubitem(19, 4, "水中", "すいちゅう", "suichuu", "Di dalam air"),
        KanjiSubitem(20, 4, "水分", "すいぶん", "suibun", "Kandungan air"),

        // Subitems for KanjiItem with ID 5 ("火")
        KanjiSubitem(21, 5, "火", "ひ", "hi", "Api"),
        KanjiSubitem(22, 5, "火山", "かざん", "kazan", "Gunung berapi"),
        KanjiSubitem(23, 5, "火災", "かさい", "kasai", "Kebakaran"),
        KanjiSubitem(24, 5, "火力", "かりょく", "karyoku", "Tenaga api"),
        KanjiSubitem(25, 5, "火曜日", "かようび", "kayoubi", "Hari Selasa"),

        // Subitems for KanjiItem dengan ID 6 ("木")
        KanjiSubitem(26, 6, "木", "き", "ki", "Pohon"),
        KanjiSubitem(27, 6, "木材", "もくざい", "mokuzai", "Kayu"),
        KanjiSubitem(28, 6, "木立", "こだち", "kodachi", "Hutan kecil"),
        KanjiSubitem(29, 6, "木陰", "こかげ", "kokage", "Bayangan pohon"),
        KanjiSubitem(30, 6, "木曜日", "もくようび", "mokuyoubi", "Hari Kamis")
    )
    return subItemData
}

