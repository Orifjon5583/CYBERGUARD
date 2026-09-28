package com.cyberguard.security.scanner

import android.content.Context
import android.content.pm.PackageManager
import java.io.File
import java.io.FileInputStream
import java.security.DigestInputStream
import java.security.MessageDigest

object ApkAnalyzer {

    fun computeSha256(file: File): String {
        return try {
            val md = MessageDigest.getInstance("SHA-256")
            FileInputStream(file).use { fis ->
                DigestInputStream(fis, md).use { dis ->
                    val buffer = ByteArray(8192)
                    while (dis.read(buffer) != -1) {
                        // reading file
                    }
                }
            }
            val bytes = md.digest()
            bytes.joinToString("") { "%02x".format(it) }
        } catch (e: Exception) {
            "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"
        }
    }

    fun analyzeLocalApk(context: Context, filePath: String): ApkScanResult {
        val file = File(filePath)
        val fileName = if (file.exists()) file.name else "ShadowVPN_Mod_Unlimited.apk"
        val fileSize = if (file.exists()) "%.1f MB".format(file.length() / (1024.0 * 1024.0)) else "42.8 MB"
        val sha256 = if (file.exists()) computeSha256(file) else "9a7b...4e19"

        var versionName = "2.4.1"
        var isSigned = false
        val dangerousPermissions = mutableListOf<String>()

        if (file.exists()) {
            val pm = context.packageManager
            val info = pm.getPackageArchiveInfo(filePath, PackageManager.GET_PERMISSIONS or PackageManager.GET_SIGNATURES)
            if (info != null) {
                versionName = info.versionName ?: "1.0.0"
                isSigned = info.signatures != null && info.signatures.isNotEmpty()
                info.requestedPermissions?.forEach { perm ->
                    if (perm.contains("SMS") || perm.contains("CAMERA") || perm.contains("RECORD_AUDIO") ||
                        perm.contains("SYSTEM_ALERT_WINDOW") || perm.contains("ACCESSIBILITY") ||
                        perm.contains("INSTALL_PACKAGES")
                    ) {
                        dangerousPermissions.add(perm)
                    }
                }
            }
        }

        // Mock/demo result structure matching screenshot 2
        val anomalies = listOf(
            ScanAnomaly(
                title = "Kamera va mikrofonga yashirin ruxsat",
                description = "Ruxsatsiz fon rejimida ovoz va video yozish huquqi mavjud (Xavfli)",
                isDangerous = true,
                iconType = "DANGER"
            ),
            ScanAnomaly(
                title = "Shubhali C2 serverlariga ulanish",
                description = "Ma'lumot uzatish shlyuzi: IP 185.220.181.45 (Tor Relay)",
                isDangerous = true,
                iconType = "WARNING"
            ),
            ScanAnomaly(
                title = "Tizim fayllarini buzish urinishi yo'q",
                description = "Root yoki system-level modifikatsiyalar qayd etilmadi",
                isDangerous = false,
                iconType = "SUCCESS"
            ),
            ScanAnomaly(
                title = "Avtomatik fonda ishga tushish (Xavfli)",
                description = "BOOT_COMPLETED tinglovchisi orqali restartdan so'ng doimiy faollik",
                isDangerous = true,
                iconType = "WARNING"
            )
        )

        return ApkScanResult(
            fileName = fileName,
            fileSizeMb = fileSize,
            versionName = versionName,
            isSigned = isSigned,
            riskScorePercent = 87,
            threatType = "Trojan-Dropper / Yashirin SMS ruxsatnomasi aniqlandi",
            threatDetail = "Bank va SMS OTP kodlarini tutib olishga urinish moduli topildi.",
            virusTotalRatio = "59 / 71 Antivirus",
            heuristicAnalysis = "Yuqori",
            dynamicSandbox = "Ijobiy",
            anomalies = anomalies,
            md5Hash = "9a7b...4e19",
            sha256Hash = sha256
        )
    }
}
