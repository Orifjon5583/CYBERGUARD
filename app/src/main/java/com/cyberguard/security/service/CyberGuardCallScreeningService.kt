package com.cyberguard.security.service

import android.os.Build
import android.telecom.Call
import android.telecom.CallScreeningService
import android.util.Log

class CyberGuardCallScreeningService : CallScreeningService() {

    companion object {
        private const val TAG = "CyberGuardCallFilter"

        // Simulated IIB Blacklist & Spam Fraud numbers database
        private val FRAUD_BLACKLIST = setOf(
            "+998900000000",
            "+998911111111",
            "+998933333333",
            "+998999999999",
            "+79000000000",
            "1002",
            "0944"
        )

        var blockedCallsCount = 0
            private set
    }

    override fun onScreenCall(callDetails: Call.Details) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val handle = callDetails.handle
            val rawNumber = handle?.schemeSpecificPart ?: ""
            Log.d(TAG, "Tekshirilayotgan kiruvchi qo'ng'iroq: $rawNumber")

            val isFraudOrBlacklisted = isNumberBlacklisted(rawNumber)

            val response = CallResponse.Builder()
            if (isFraudOrBlacklisted) {
                blockedCallsCount++
                Log.w(TAG, "🚨 XAVFLI SAKAMER QO'NG'IROQ ANIQLANDI VA BLOKLANDI: $rawNumber")

                response.setDisallowCall(true)
                    .setRejectCall(true)
                    .setSkipCallLog(true)
                    .setSkipNotification(true)
            } else {
                response.setDisallowCall(false)
            }

            respondToCall(callDetails, response.build())
        }
    }

    private fun isNumberBlacklisted(number: String): Boolean {
        val cleanNumber = number.replace(Regex("[^0-9+]"), "")
        if (FRAUD_BLACKLIST.contains(cleanNumber)) return true
        if (cleanNumber.contains("*21*") || cleanNumber.contains("*67*")) return true // USSD Call Forwarding attacks
        if (cleanNumber.startsWith("+88") || cleanNumber.startsWith("+252")) return true // International Wangiri scam
        return false
    }
}
