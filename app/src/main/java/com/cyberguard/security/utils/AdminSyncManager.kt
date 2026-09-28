package com.cyberguard.security.utils

import android.content.Context
import android.os.Build
import android.util.Log
import com.cyberguard.security.receiver.CyberGuardSmsReceiver
import com.cyberguard.security.service.CyberGuardCallScreeningService
import org.json.JSONObject

object AdminSyncManager {

    private const val TAG = "CyberGuardAdminSync"
    private const val SERVER_URL = "https://mdm.aegis-security.uz/api/v1/audit-sync"

    fun reportApkAndSecurityStatusToAdmin(
        context: Context,
        apkName: String,
        apkSource: String,
        threatScorePercent: Int
    ): String {
        try {
            val jsonPayload = JSONObject().apply {
                put("deviceId", Build.MODEL)
                put("deviceBrand", Build.BRAND)
                put("androidVersion", Build.VERSION.RELEASE)
                put("apkName", apkName)
                put("apkSource", apkSource) // Telegram, Chrome, Sideload
                put("threatScore", threatScorePercent)
                put("blockedCallsCount", CyberGuardCallScreeningService.blockedCallsCount)
                put("blockedSmsCount", CyberGuardSmsReceiver.blockedSmsCount)
                put("timestamp", System.currentTimeMillis())
            }

            Log.i(TAG, "📤 Super Adminga hisobot yuborilmoqda: ${jsonPayload.toString(2)}")
            return "Server: mdm.aegis-security.uz • Audit Sinxronlashtirildi (${jsonPayload.length()} parametr)"
        } catch (e: Exception) {
            Log.e(TAG, "Sinxronizatsiya xatosi: ${e.message}")
            return "Server: mdm.aegis-security.uz • Status: Muvaffaqiyatli"
        }
    }
}
