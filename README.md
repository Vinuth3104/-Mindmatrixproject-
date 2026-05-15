# Channapatna-Namma

> An Android app that verifies the authenticity of Channapatna wooden toys and tells the story of the artisans behind each piece.

Built during an internship with **MindMatrix** (VTU Industry Internship, 2025–2026).

---

## The Problem

Channapatna's GI-tagged wooden toys — handcrafted with *hale wood* and natural lac dyes by generations of artisans — are losing market share to cheap plastic imitations sold under the same name. Buyers cannot distinguish authentic toys from fakes, and the artisans behind each piece remain anonymous. This erodes both artisan livelihoods and the cultural heritage of the craft.

**Channapatna-Namma** solves this by letting buyers verify a toy's authenticity using a unique ID (or QR scan) and revealing the story of the artisan who made it.

---

## Features

- **Verify My Toy** — Enter a 6-digit toy ID or scan its QR code to instantly confirm authenticity and view the maker's profile
- **How It's Made** — Educational section on the traditional *hale wood* and lac-dye process, with a video showing the craft in action
- **Meet the Maker** — Interactive Google Map with pins on registered Channapatna workshops you can visit
- **Toy Catalog** — Browse the latest designs — rocking horses, puzzles, dolls, and more
- **Counterfeit Warning** — Invalid IDs show a clear warning to protect buyers from fakes

---

## Tech Stack

| Layer | Technology |
|-------|------------|
| Language | Kotlin |
| UI | XML Layouts + Material Design 3 |
| Backend | Firebase Firestore |
| Media Storage | Firebase Cloud Storage |
| Maps | Google Maps SDK for Android |
| QR Scanning | Google ML Kit Barcode Scanner |
| Video | Android VideoView / ExoPlayer |
| Min SDK | API 24 (Android 7.0) |
| Target SDK | API 34 (Android 14) |

---

## Screenshots

| Home | Verify My Toy | Artisan Profile |
|------|---------------|-----------------|
| _coming soon_ | _coming soon_ | _coming soon_ |

| Meet the Maker | How It's Made |
|----------------|---------------|
| _coming soon_ | _coming soon_ |

---

## Getting Started

### Prerequisites
- Android Studio Hedgehog or newer
- JDK 17+
- A Firebase project (Firestore + Storage enabled)
- A Google Maps API key

### Setup

1. **Clone the repo**
   ```bash
   git clone https://github.com/<your-username>/channapatna-namma.git
   cd channapatna-namma
   ```

2. **Add your Firebase config**
   - Create a Firebase project at [console.firebase.google.com](https://console.firebase.google.com)
   - Download `google-services.json` and place it in `app/`

3. **Add your Google Maps API key**
   - Get a key from [Google Cloud Console](https://console.cloud.google.com)
   - Add it to `local.properties`:
     ```
     MAPS_API_KEY=your_key_here
     ```

4. **Open in Android Studio** → Sync Gradle → Run on emulator or device

---

## Firestore Data Model

**Collection: `toys`**
```
{
  toyId: "123456",
  toyName: "Rocking Horse",
  artisanId: "ART001",
  imageUrl: "...",
  videoUrl: "...",
  dateOfMaking: "2026-03-15"
}
```

**Collection: `artisans`**
```
{
  artisanId: "ART001",
  name: "Ramesh Acharya",
  photoUrl: "...",
  workshopName: "Acharya Toy Works",
  yearsOfExperience: 24,
  location: { lat: 12.6516, lng: 77.2073 }
}
```

**Collection: `workshops`**
```
{
  workshopId: "WS001",
  name: "Acharya Toy Works",
  address: "Channapatna, Karnataka",
  lat: 12.6516,
  lng: 77.2073,
  contact: "+91 XXXXX XXXXX"
}
```

---

## Impact Goals

- **GI Tag Protection** — Use technology to safeguard Geographical Indication products from counterfeits
- **Artisan Recognition** — Give anonymous makers a name, a face, and a story
- **Sustainable Play** — Promote natural, non-toxic toys over mass-produced plastic
- **Cultural Preservation** — Document and transmit traditional craft knowledge digitally

---

## Future Scope

- Real QR code stickers issued to registered artisans with anti-tamper holograms
- Direct purchase / pre-order from artisan workshops
- Kannada localization (`values-kn`)
- Multi-language expansion (Hindi, Tamil, Telugu)
- Firebase Authentication for artisan-side dashboards
- Offline-first sync using Room as a caching layer

---

## Project Structure

```
app/
├── src/main/java/com/example/channapatna_namma/
│   ├── MainActivity.kt
│   ├── ui/
│   │   ├── HomeFragment.kt
│   │   ├── VerifyToyFragment.kt
│   │   ├── ArtisanProfileFragment.kt
│   │   ├── ToyCatalogFragment.kt
│   │   ├── MeetTheMakerFragment.kt
│   │   └── HowItsMadeFragment.kt
│   ├── data/
│   │   ├── FirestoreHelper.kt
│   │   └── models/
│   └── util/
└── src/main/res/
    ├── layout/
    ├── values/
    └── drawable/
```

---

## Built With

This project was developed as part of an **Industry Internship** with [MindMatrix](https://mindmatrix.io) for the course *Android App Development using GenAI* (VTU course code: BINT803B).

**Institution:** RNS Institute of Technology, Department of Information Science and Engineering
**Academic Year:** 2025–2026

---

## License

This project is for educational and internship-evaluation purposes.

---

## Acknowledgements

- **Channapatna artisans** of Karnataka who keep this 200-year-old craft alive
- **MindMatrix** for the internship opportunity and mentorship
- **RNS Institute of Technology** for academic support
- Google Firebase, ML Kit, and Maps Platform for the developer tooling
