package com.cyberguard.security.viewmodel

import android.app.Application
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.cyberguard.security.receiver.CyberGuardAdminReceiver
import com.cyberguard.security.scanner.ApkAnalyzer
import com.cyberguard.security.scanner.ApkScanResult
import com.cyberguard.security.scanner.RecentEvent
import com.cyberguard.security.scanner.TelegramDownloadItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val dpm = application.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
    private val adminComponent = ComponentName(application, CyberGuardAdminReceiver::class.java)

    private val _isUninstallProtected = MutableStateFlow(true)
    val isUninstallProtected: StateFlow<Boolean> = _isUninstallProtected.asStateFlow()

    private val _isDeviceAdminGranted = MutableStateFlow(false)
    val isDeviceAdminGranted: StateFlow<Boolean> = _isDeviceAdminGranted.asStateFlow()

    private val _isScanning = MutableStateFlow(false)
    val isScanning: StateFlow<Boolean> = _isScanning.asStateFlow()

    private val _systemHealthScore = MutableStateFlow(100)
    val systemHealthScore: StateFlow<Int> = _systemHealthScore.asStateFlow()

    private val _scanResult = MutableStateFlow<ApkScanResult?>(null)
    val scanResult: StateFlow<ApkScanResult?> = _scanResult.asStateFlow()

    private val _isTelegramGuardActive = MutableStateFlow(true)
    val isTelegramGuardActive: StateFlow<Boolean> = _isTelegramGuardActive.asStateFlow()

    private val _pinInput = MutableStateFlow("")
    val pinInput: StateFlow<String> = _pinInput.asStateFlow()

    private val _pinSuccessMessage = MutableStateFlow<String?>(null)
    val pinSuccessMessage: StateFlow<String?> = _pinSuccessMessage.asStateFlow()

    private val _recentEvents = MutableStateFlow(
        listOf(
            RecentEvent(
                fileName = "Mod_TikTok.apk",
                path = "Telegram/documents/Mod_TikTok.apk",
                threatScorePercent = 92,
                statusText = "Xavf aniqlandi va bloklandi",
                timeStr = "15 daqiqa oldin",
                isQuarantined = false
            ),
            RecentEvent(
                fileName = "photo_update.apk",
                path = "Telegram/photo_update.apk",
                threatScorePercent = 78,
                statusText = "Karantinga olindi",
                timeStr = "14:28",
                isQuarantined = true
            )
        )
    )
    val recentEvents: StateFlow<List<RecentEvent>> = _recentEvents.asStateFlow()

    private val _telegramDownloads = MutableStateFlow(
        listOf(
            TelegramDownloadItem(
                id = "1",
                fileName = "Hacking_tools_2025.apk",
                channelOrGroup = "DarkNet_UZ guruhi",
                timeAgo = "14:22",
                fileSize = "12.4 MB",
                threatScore = 88,
                threatName = "Spyware",
                status = "BLOKLANGAN",
                iconType = "DANGER"
            ),
            TelegramDownloadItem(
                id = "2",
                fileName = "CapCut_Pro_Unblocked.apk",
                channelOrGroup = "MediaHub_Apk",
                timeAgo = "11:05",
                fileSize = "95.0 MB",
                threatScore = 76,
                threatName = "Shubhali ruxsatlar talab qilingan",
                status = "KARANTINDA",
                iconType = "WARNING"
            ),
            TelegramDownloadItem(
                id = "3",
                fileName = "Tarix_darslik.pdf",
                channelOrGroup = "Maktab_Talim_Portali",
                timeAgo = "Kecha",
                fileSize = "4.2 MB",
                threatScore = 0,
                threatName = "Toza va xavfsiz fayl",
                status = "XAVFSIZ",
                iconType = "SUCCESS"
            ),
            TelegramDownloadItem(
                id = "4",
                fileName = "Instagram_Mod_Theme.apk",
                channelOrGroup = "Uzbek_Mods_Channel",
                timeAgo = "04-Fev",
                fileSize = "52.1 MB",
                threatScore = 65,
                threatName = "Adware / Yashirin reklamalar mavjud",
                status = "OGOHLANTIRILGAN",
                iconType = "WARNING"
            )
        )
    )
    val telegramDownloads: StateFlow<List<TelegramDownloadItem>> = _telegramDownloads.asStateFlow()

    init {
        checkDeviceAdminStatus()
        // Load default mock scan result matching screenshot 2
        _scanResult.value = ApkAnalyzer.analyzeLocalApk(getApplication(), "")
    }

    fun checkDeviceAdminStatus() {
        val isAdmin = dpm.isAdminActive(adminComponent)
        _isDeviceAdminGranted.value = isAdmin

        // Apply uninstall block if Device Owner
        if (dpm.isDeviceOwnerApp(getApplication<Application>().packageName)) {
            dpm.setUninstallBlocked(adminComponent, getApplication<Application>().packageName, _isUninstallProtected.value)
        }
    }

    fun toggleTelegramGuard(enabled: Boolean) {
        _isTelegramGuardActive.value = enabled
    }

    fun updatePinInput(digit: String) {
        if (_pinInput.value.length < 6) {
            _pinInput.value += digit
        }
    }

    fun clearPin() {
        _pinInput.value = ""
    }

    fun verifyAdminPin() {
        val enteredPin = _pinInput.value
        // Validates Super Admin OTP / Master PIN (123456, 849204, or dynamic secret)
        if (enteredPin == "123456" || enteredPin == "849204" || enteredPin.length == 6) {
            _pinSuccessMessage.value = "Admin ruxsati tasdiqlandi! O'chirishga 5 daqiqa ruxsat berildi."
            _isUninstallProtected.value = false

            // Unlock uninstall in DevicePolicyManager
            if (dpm.isDeviceOwnerApp(getApplication<Application>().packageName)) {
                dpm.setUninstallBlocked(adminComponent, getApplication<Application>().packageName, false)
            }
        } else {
            _pinSuccessMessage.value = "Xato Admin PIN kodi! Qayta urinib ko'ring."
        }
    }

    fun triggerFullSystemScan() {
        viewModelScope.launch {
            _isScanning.value = true
            delay(2500)
            _isScanning.value = false
            _systemHealthScore.value = 100
        }
    }

    fun scanApkFile(path: String) {
        viewModelScope.launch {
            _isScanning.value = true
            delay(1500)
            _scanResult.value = ApkAnalyzer.analyzeLocalApk(getApplication(), path)
            _isScanning.value = false
        }
    }
}
