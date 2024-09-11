package com.neonusa.belajarkanjijlpt.utils

import com.neonusa.belajarkanjijlpt.model.KanjiItem
import com.neonusa.belajarkanjijlpt.model.KanjiSubitem

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

