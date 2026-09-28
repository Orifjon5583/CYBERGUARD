package com.cyberguard.security.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import android.widget.Toast

class CyberGuardSmsReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "CyberGuardSmsFilter"
        var blockedSmsCount = 0
            private set

        private val PHISHING_KEYWORDS = listOf(
            "parol", "kod", "parolni ayting", "karta raqamingiz", "karta bloklandi",
            "shoshilinch", "yutuq", "linkga bosing", "click.me", "payme-login",
            "bank-verify", "otp", "verification code"
        )
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            for (sms in messages) {
                val sender = sms.originatingAddress ?: "Unknown"
                val body = sms.messageBody ?: ""
                Log.d(TAG, "Kiruvchi SMS: $sender -> $body")

                val isPhishing = PHISHING_KEYWORDS.any { body.lowercase().contains(it) }
                if (isPhishing) {
                    blockedSmsCount++
                    Log.w(TAG, "🚨 SHUBHALI / PHISHING SMS ANIQLANDI VA BLOKLANDI! Sender: $sender")
                    Toast.makeText(context, "🛡 CyberGuard: Xavfli SMS aniqlandi va bloklandi ($sender)", Toast.LENGTH_LONG).show()

                    // Abort broadcast to prevent user from receiving phishing SMS if default handler allows
                    try {
                        abortBroadcast()
                    } catch (e: Exception) {
                        Log.e(TAG, "Error aborting SMS broadcast: ${e.message}")
                    }
                }
            }
        }
    }
}
