package ru.aleksandrorlove.appname

class Movies {

    companion object {
        fun createMovieList(): List<Movie> {
            return mutableListOf(
                Movie(
                    R.drawable.cover_aeg,
                    R.drawable.background_aeg,
                    "13+",
                    false,
                    "Action, Adventure, Fantasy",
                    1,
                    "4",
                    "Avengers: End Game",
                    "137",
                    "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos\\' actions and restore balance to the universe.",
                    arrayListOf(
                        Actor("Robert Downey Jr.", R.drawable.photo_aeg_01),
                        Actor("Chris Evans", R.drawable.photo_aeg_02),
                        Actor("Mark Ruffalo", R.drawable.photo_aeg_03),
                        Actor("Chris Hemsworth", R.drawable.photo_aeg_04)
                    )
                ),
                Movie(
                    R.drawable.cover_msb,
                    R.drawable.background_msb,
                    "12+",
                    false,
                    "Documentary, War",
                    3,
                    "4",
                    "Moscow Strikes Back",
                    "55",
                    "Soviet documentary about the defeat of the Nazis near Moscow. Warning - graphic images. Edward G. Robinson narrates the English language version.",
                    arrayListOf(
                        Actor("N. Dubravin", R.drawable.photo_msb_01),
                        Actor("Edward G. Robinson", R.drawable.photo_msb_02),
                        Actor("Joseph Stalin", R.drawable.photo_msb_03)
                    )
                ),

                Movie(
                    R.drawable.cover_kdd,
                    R.drawable.background_kdd,
                    "12",
                    true,
                    "Comedy, Drama, Science Fiction",
                    5,
                    "66",
                    "Kin-dza-dza!",
                    "135",
                    "Two Soviet humans previously unknown to each other are transported to the planet Pluke in the Kin-dza-da galaxy due to a chance encounter with an alien teleportation device. They must come to grips with a language barrier and Plukian social norms (not to mention the laws of space and time) if they ever hope to return to Earth.",
                    arrayListOf(
                        Actor("Stanislav Lyubshin", R.drawable.photo_kdd_01),
                        Actor("Evgeniy Leonov", R.drawable.photo_kdd_02),
                        Actor("Yuriy Yakovlev", R.drawable.photo_kdd_03),
                        Actor("Levan Gabriadze", R.drawable.photo_kdd_04)
                    )
                ),

                Movie(
                    R.drawable.cover_tpoh,
                    R.drawable.background_tpoh,
                    "13",
                    false,
                    "Drama",
                    4,
                    "991",
                    "The Pursuit of Happyness",
                    "117",
                    "A struggling salesman takes custody of his son as he's poised to begin a life-changing professional career.",
                    arrayListOf(
                        Actor("Will Smith", R.drawable.photo_tpoh_01),
                        Actor("Jaden Smith", R.drawable.photo_tpoh_02),
                        Actor("Thandie Newton", R.drawable.photo_tpoh_03),
                        Actor("Brian Howe", R.drawable.photo_tpoh_04)
                    )
                )
            )
        }
    }
}