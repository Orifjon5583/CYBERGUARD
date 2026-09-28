# CYBERGUARD

**CYBERGUARD** — Android Enterprise (MDM / Device Owner) va Sun'iy Intelekt / Antivirus bazalariga integratsiya qilingan kiberxavfsizlik va APK tahlili dasturi.

---

## 🛡 Asosiy Imkoniyatlar

- **🔒 O'chirishni Bloklash (Device Owner / MDM):** Android Enterprise API orqali ilovani ma'mur (admin) parolisiz va ruxsatisiz o'chirib bo'lmaydi.
- **🔍 Chuqur APK Skaner (Static & Cloud Analysis):** 
  - Android Manifest, SHA-256 hash validatsiyasi.
  - VirusTotal (70+ antivirus dvigatel) va YARA qoidalari.
  - Risk Score ($0 - 100\%$) va Anomaliyalar tahlili (Tor Relay C2 connection, Overlay attack, SMS interception).
- **📩 Telegram Jonli Himoyasi:** Telegram orqali yuklanuvchi fayllarni real-vaqt rejimida ushlash va tahlil qilish.
- **⚙️ Admin Panel (Zero-Trust):** 6-xonali PIN tekshiruvi, SELinux monitoringi, MDM siyosatlarini boshqarish.

---

## 🚀 GitHub Actions orqali APK Build Qilish

Ushbu repozitoriy **GitHub Actions** bilan ta'minlangan. Har safar `main` tarmog'iga push qilinganda avtomatik ravishda `.apk` fayli yaratiladi:

1. Repozitoriyingizdagi **Actions** bo'limiga o'ting.
2. **Build CyberGuard APK** jarayonini tanlang.
3. **Artifacts** bo'limidan `CyberGuard-Debug-APK` faylini yuklab oling!
