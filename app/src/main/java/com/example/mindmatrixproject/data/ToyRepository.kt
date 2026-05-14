package com.example.mindmatrixproject.data

/**
 * Mock "Firebase" repository.
 *
 * Cooperatives are real, publicly-listed entities tied to the Channapatna GI ecosystem.
 * Artisans are FICTIONAL personas attached to real cooperatives — used to give the
 * "Verify a Toy" flow a name + face. Treat them as illustrative.
 */
object ToyRepository {

    private val cooperatives = listOf(
        Cooperative(
            id = "C01",
            name = "Channapatna Crafts Park",
            type = "Karnataka State Government Cluster",
            locality = "Channapatna, Ramanagara District",
            address = "Bangalore-Mysore Highway, Channapatna, Karnataka 562160",
            openHours = "Mon-Sat · 10:00 AM – 6:00 PM",
            publicContact = "Karnataka State Handicrafts Development Corporation",
            website = "https://kshdcltd.karnataka.gov.in",
            story = "A government-run craft cluster supporting hundreds of Channapatna artisans with shared lathes, lac processing, and market access. The institutional home of the GI tag.",
            emoji = "🏛️",
            accentHex = 0xFFD64545,
            lat = 12.6485, lng = 77.2078,
        ),
        Cooperative(
            id = "C02",
            name = "Maya Organic — Channapatna Workshop",
            type = "Social Enterprise · Fair-trade",
            locality = "Channapatna town",
            address = "Channapatna, Karnataka 562160 (visit by appointment)",
            openHours = "Mon-Fri · 10:00 AM – 5:00 PM",
            publicContact = "Maya Organic",
            website = "https://mayaorganic.com",
            story = "A fair-trade enterprise partnering with Channapatna artisans to make Montessori-style wooden toys exported worldwide. Famous for child-safe lac dyes.",
            emoji = "🌱",
            accentHex = 0xFF3B9A8E,
            lat = 12.6511, lng = 77.2120,
        ),
        Cooperative(
            id = "C03",
            name = "Bharat Ratna Sir M. Visvesvaraya Industrial Co-op Society",
            type = "Artisan Cooperative Society",
            locality = "Channapatna town",
            address = "Channapatna, Karnataka 562160",
            openHours = "Mon-Sat · 9:00 AM – 6:00 PM",
            publicContact = "Co-operative Society Office",
            website = null,
            story = "One of the oldest Channapatna cooperatives, named after Bharat Ratna Sir M. Visvesvaraya. Members specialise in geometric puzzles and traditional dolls.",
            emoji = "🛠️",
            accentHex = 0xFF4A7B9D,
            lat = 12.6531, lng = 77.2055,
        ),
        Cooperative(
            id = "C04",
            name = "Karnataka State Lac & Wood Crafts Co-op Society",
            type = "Wood-craft Cooperative",
            locality = "Channapatna industrial area",
            address = "Industrial Area, Channapatna, Karnataka 562160",
            openHours = "Mon-Sat · 9:30 AM – 5:30 PM",
            publicContact = "Society Secretariat",
            website = null,
            story = "Dedicated to lac-finished wooden crafts. Trains young artisans and runs apprentice batches every quarter.",
            emoji = "🪵",
            accentHex = 0xFFE89B5C,
            lat = 12.6502, lng = 77.2098,
        ),
        Cooperative(
            id = "C05",
            name = "Channapatna Toys Cluster Programme",
            type = "Cluster Development Office",
            locality = "Channapatna town",
            address = "Channapatna, Karnataka 562160",
            openHours = "Mon-Fri · 10:00 AM – 5:00 PM",
            publicContact = "Cluster Development Officer (KVIC / MSME)",
            website = "https://msme.gov.in",
            story = "A Government of India cluster programme supporting infrastructure, design innovation, and export market access for Channapatna toy makers.",
            emoji = "🏷️",
            accentHex = 0xFFD68B95,
            lat = 12.6493, lng = 77.2085,
        ),
        Cooperative(
            id = "C06",
            name = "Cauvery Karnataka State Handicrafts Emporium",
            type = "Government Retail Outlet",
            locality = "MG Road, Bengaluru (showcase store)",
            address = "23, MG Road, Bengaluru, Karnataka 560001",
            openHours = "Mon-Sun · 10:30 AM – 7:30 PM",
            publicContact = "Cauvery Emporium · KSHDC",
            website = "https://kshdcltd.karnataka.gov.in",
            story = "The flagship retail outlet for Karnataka's GI-tagged crafts including Channapatna toys. A reliable place to buy verified authentic pieces.",
            emoji = "🛍️",
            accentHex = 0xFFEFC368,
            lat = 12.9750, lng = 77.6050,
        ),
    )

    private val artisans = listOf(
        Artisan(
            id = "A01",
            name = "Bhaskar Acharya",
            cooperativeId = "C01",
            village = "Channapatna",
            yearsOfCraft = 32,
            photoEmoji = "👨‍🎨",
            story = "Bhaskar learned the craft from his father at age 9. He specialises in lac-turned rocking horses and has trained over 40 young artisans at the Crafts Park.",
            signatureToys = listOf("Rocking Horse", "Spinning Top"),
            videoTitle = "Lac dye on the lathe — 4 min",
        ),
        Artisan(
            id = "A02",
            name = "Lakshmi Devi",
            cooperativeId = "C02",
            village = "Neelasandra, Channapatna",
            yearsOfCraft = 18,
            photoEmoji = "👩‍🎨",
            story = "Lakshmi leads the women's cluster at Maya Organic. Her group revived the traditional turmeric-yellow dye recipe that had nearly been lost.",
            signatureToys = listOf("Stacking Rings", "Animal Puzzle"),
            videoTitle = "Natural dyes from kitchen plants — 3 min",
        ),
        Artisan(
            id = "A03",
            name = "Ramesh Kumar",
            cooperativeId = "C03",
            village = "Kalkere, Channapatna",
            yearsOfCraft = 24,
            photoEmoji = "👴",
            story = "A third-generation toy maker, Ramesh holds a state award for his geometric Channapatna puzzles.",
            signatureToys = listOf("Geometric Puzzle", "Dancing Doll"),
            videoTitle = "Hand-turned puzzle pieces — 5 min",
        ),
        Artisan(
            id = "A04",
            name = "Suma Bai",
            cooperativeId = "C02",
            village = "Hosadoddi, Channapatna",
            yearsOfCraft = 12,
            photoEmoji = "👩",
            story = "Suma blends modern Montessori design with traditional lac. Her sets are exported to Japan and Germany.",
            signatureToys = listOf("Montessori Set", "Wooden Rattle"),
            videoTitle = "From log to lullaby — 6 min",
        ),
        Artisan(
            id = "A05",
            name = "Venkatesh Bhat",
            cooperativeId = "C04",
            village = "Channapatna Old Town",
            yearsOfCraft = 40,
            photoEmoji = "🧓",
            story = "At 62, Venkatesh is the oldest active artisan in his cluster. He is documenting 70 traditional designs before they fade away.",
            signatureToys = listOf("Traditional Doll", "Toy Cart"),
            videoTitle = "Forty years at the lathe — 7 min",
        ),
    )

    private val toys = listOf(
        Toy("100001", "Royal Rocking Horse", "Rocking Horse", "A01", "🐎",
            listOf("Crimson", "Gold"), 1499,
            "Lac-turned rocking horse — ridable from age 2. Hand-painted with crimson and gold lac dyes.",
            0xFFD64545),
        Toy("100002", "Spinning Top — Classic", "Top", "A05", "🌀",
            listOf("Red", "Yellow", "Green"), 149,
            "The traditional Channapatna spinning top — a timeless first toy.",
            0xFFEFC368),
        Toy("200015", "Sunshine Stacker", "Stacking Toy", "A02", "🟡",
            listOf("Turmeric", "Indigo"), 549,
            "Five-ring stacker tinted with turmeric-yellow and indigo. Encourages motor skills.",
            0xFFEFC368),
        Toy("200016", "Forest Friends Puzzle", "Puzzle", "A02", "🧩",
            listOf("Green", "Brown"), 399,
            "8-piece animal puzzle painted with forest greens and bark browns.",
            0xFF3B9A8E),
        Toy("300042", "Geometric Mind Puzzle", "Puzzle", "A03", "🔷",
            listOf("Blue", "Red"), 499,
            "Build geometric shapes — sharpens spatial reasoning. State-award-winning design.",
            0xFF4A7B9D),
        Toy("300043", "Dancing Doll", "Doll", "A03", "💃",
            listOf("Pink", "Cream"), 299,
            "Hand-painted classic doll with movable arms — a Channapatna tradition for over a century.",
            0xFFD68B95),
        Toy("400077", "Baby's First Rattle", "Rattle", "A04", "🎶",
            listOf("Pastel Pink", "Mint"), 199,
            "Non-toxic, safe-to-mouth lac. Soft pastel finish, the perfect first toy.",
            0xFFE89B5C),
        Toy("400078", "Montessori Discovery Set", "Educational", "A04", "🧸",
            listOf("Natural", "Red"), 1899,
            "Five-piece sensory set used in Montessori classrooms across the world.",
            0xFF2A7A75),
        Toy("500099", "Heritage Toy Cart", "Cart", "A05", "🚜",
            listOf("Brick Red", "Mustard"), 799,
            "Pull-along cart with wooden wheels. A direct copy of designs documented 150 years ago.",
            0xFFD64545),
    )

    fun verifyToy(id: String): VerificationResult {
        if (!id.matches(Regex("^\\d{6}$"))) return VerificationResult.InvalidFormat
        val toy = toys.firstOrNull { it.id == id } ?: return VerificationResult.NotFound
        val artisan = artisans.first { it.id == toy.artisanId }
        val coop = cooperatives.first { it.id == artisan.cooperativeId }
        return VerificationResult.Authentic(toy, artisan, coop)
    }

    fun allToys(): List<Toy> = toys
    fun toyById(id: String): Toy? = toys.firstOrNull { it.id == id }
    fun toysByArtisan(artisanId: String): List<Toy> = toys.filter { it.artisanId == artisanId }
    fun toysByCooperative(cooperativeId: String): List<Toy> {
        val artisanIds = artisans.filter { it.cooperativeId == cooperativeId }.map { it.id }.toSet()
        return toys.filter { it.artisanId in artisanIds }
    }

    fun allCooperatives(): List<Cooperative> = cooperatives
    fun cooperativeById(id: String): Cooperative? = cooperatives.firstOrNull { it.id == id }

    fun allArtisans(): List<Artisan> = artisans
    fun artisanById(id: String): Artisan? = artisans.firstOrNull { it.id == id }
    fun artisansFor(cooperativeId: String): List<Artisan> =
        artisans.filter { it.cooperativeId == cooperativeId }

    fun categories(): List<String> = toys.map { it.category }.distinct()

    fun stats(): Triple<Int, Int, Int> = Triple(toys.size, cooperatives.size, artisans.size)
}

sealed class VerificationResult {
    data class Authentic(
        val toy: Toy,
        val artisan: Artisan,
        val cooperative: Cooperative,
    ) : VerificationResult()
    data object NotFound : VerificationResult()
    data object InvalidFormat : VerificationResult()
}