package com.cyberguard.security.scanner

enum class RiskLevel {
    SAFE,
    WARNING,
    HIGH_RISK,
    CRITICAL
}

data class ScanAnomaly(
    val title: String,
    val description: String,
    val isDangerous: Boolean,
    val iconType: String = "WARNING" // DANGER, WARNING, INFO
)

data class ApkScanResult(
    val fileName: String,
    val fileSizeMb: String,
    val versionName: String,
    val isSigned: Boolean,
    val riskScorePercent: Int,
    val threatType: String,
    val threatDetail: String,
    val virusTotalRatio: String, // e.g. "59 / 71 Antivirus"
    val heuristicAnalysis: String, // e.g. "Yuqori"
    val dynamicSandbox: String, // e.g. "Ijobiy"
    val anomalies: List<ScanAnomaly>,
    val md5Hash: String,
    val sha256Hash: String,
    val ruleVersion: String = "CyberGuard Qoida: V-2024.11.08"
)

data class TelegramDownloadItem(
    val id: String,
    val fileName: String,
    val channelOrGroup: String,
    val timeAgo: String,
    val fileSize: String,
    val threatScore: Int,
    val threatName: String?,
    val status: String, // BLOKLANGAN, KARANTINDA, XAVFSIZ, OGOHLANTIRILGAN
    val iconType: String
)

data class RecentEvent(
    val fileName: String,
    val path: String,
    val threatScorePercent: Int,
    val statusText: String,
    val timeStr: String,
    val isQuarantined: Boolean
)
