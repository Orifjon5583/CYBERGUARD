package com.cyberguard.security.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cyberguard.security.ui.theme.*
import com.cyberguard.security.viewmodel.MainViewModel

@Composable
fun AdminScreen(viewModel: MainViewModel) {
    val isUninstallProtected by viewModel.isUninstallProtected.collectAsState()
    val isDeviceAdminGranted by viewModel.isDeviceAdminGranted.collectAsState()
    val pinInput by viewModel.pinInput.collectAsState()
    val pinSuccessMessage by viewModel.pinSuccessMessage.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(CyberDarkBg)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 24.dp)
    ) {
        // Zero Trust Header Banner
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CyberCardBg),
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(CyberCardBorder, Color(0xFF1D3246))))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Shield, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "TIZIM DARAJASIDAGI MDM", color = NeonCyan, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(SuccessGreenBg)
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(text = "Zero-Trust Faol", color = SuccessGreen, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Device Policy & Administrator Himoyasi", color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Ushbu qurilma markazlashtirilgan kiberxavfsizlik protokollari bilan boshqariladi.", color = TextSecondary, fontSize = 11.sp)
                }
            }
        }

        // Active MDM Protection Main Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF131D28)),
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(CyberCardBorder, AlertRedBg)))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF0F2636)),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(contentAlignment = Alignment.BottomEnd) {
                            Icon(imageVector = Icons.Default.Shield, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(36.dp))
                            Box(
                                modifier = Modifier
                                    .size(18.dp)
                                    .clip(CircleShape)
                                    .background(SuccessGreen),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = CyberDarkBg, modifier = Modifier.size(10.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(AlertRedBg)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "🛡 O'chirish Bloklangan (Active MDM Protection)",
                            color = AlertRed,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "Tizimli Immunitet O'natilgan", color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Bu ilovani oddiy foydalanuvchi sozlamalardan o'chirib tashlay olmaydi. O'chirish yoki to'xtatish uchun maxsus Administrator PIN kodi va ruxsati kerak bo'ladi.",
                        color = TextSecondary,
                        fontSize = 11.sp,
                        lineHeight = 15.sp,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF0B141E), shape = RoundedCornerShape(8.dp))
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "🛡 SELinux Holati: Enforcing", color = SuccessGreen, fontSize = 10.sp, fontWeight = FontWeight.Medium)
                        Text(text = "🌐 Policy Versiyasi: v4.10.2-SEC", color = NeonCyan, fontSize = 10.sp, fontWeight = FontWeight.Medium)
                    }
                }
            }
        }

        // Admin PIN Entry Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CyberCardBg),
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(CyberCardBorder, Color(0xFF22364B))))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "Ilovani O'chirish Uchun Admin Ruxsati", color = TextPrimary, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Qurilma boshqaruvchilari tomonidan berilgan bir martalik yoki doimiy ma'muriy xavfsizlik kodini kiriting.", color = TextSecondary, fontSize = 10.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 6 Pin digit boxes
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        repeat(6) { index ->
                            val digit = pinInput.getOrNull(index)?.toString() ?: ""
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFF0C141F))
                                    .border(width = 1.dp, color = if (digit.isNotEmpty()) NeonCyan else CyberCardBorder, shape = RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (digit.isNotEmpty()) "●" else "",
                                    color = NeonCyan,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Full 0-9 Keypad buttons grid
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            listOf("1", "2", "3", "4", "5").forEach { num ->
                                IconButton(
                                    onClick = { viewModel.updatePinInput(num) },
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF162534))
                                ) {
                                    Text(text = num, color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            listOf("6", "7", "8", "9", "0").forEach { num ->
                                IconButton(
                                    onClick = { viewModel.updatePinInput(num) },
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF162534))
                                ) {
                                    Text(text = num, color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                            IconButton(
                                onClick = { viewModel.clearPin() },
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(AlertRedBg)
                            ) {
                                Icon(imageVector = Icons.Default.Backspace, contentDescription = null, tint = AlertRed, modifier = Modifier.size(18.dp))
                            }
                        }
                    }

                    pinSuccessMessage?.let { msg ->
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = msg, color = if (msg.contains("tasdiqlandi")) SuccessGreen else AlertRed, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = { viewModel.verifyAdminPin() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = NeonCyan)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = CyberDarkBg, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "Ruxsatni Tasdiqlash", color = CyberDarkBg, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "🔑 Admin Parolini Unutdingizmi?",
                            color = TextSecondary,
                            fontSize = 11.sp,
                            modifier = Modifier.clickable { }
                        )
                    }
                }
            }
        }

        // MDM Policies List Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "MDM Siyosatlari va Sozlamalar", color = TextPrimary, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(SuccessGreenBg)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(text = "5/5 Faol", color = SuccessGreen, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        // 5 Policies
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                AdminPolicyItem(
                    title = "Ilovani O'chirishni taqiqlash",
                    subtitle = "Uninstall Protection Active",
                    statusText = "FAOL (Qulflangan)",
                    isLocked = true,
                    icon = Icons.Default.Lock
                )
                AdminPolicyItem(
                    title = "Xavfli APK'larni O'rnatishni cheklash",
                    subtitle = "Noma'lum manbalarni cheklash",
                    statusText = "FAOL",
                    isLocked = false,
                    icon = Icons.Default.Security
                )
                AdminPolicyItem(
                    title = "Telegram Yuklamalarini Avto-skan",
                    subtitle = "Inline sandbox tahlili",
                    statusText = "FAOL",
                    isLocked = false,
                    icon = Icons.Default.Send
                )
                AdminPolicyItem(
                    title = "Xavfsiz Rejim (Safe Boot Protection)",
                    subtitle = "Bootloader nazorati ostida",
                    statusText = "FAOL",
                    isLocked = false,
                    icon = Icons.Default.PhonelinkSetup
                )
                AdminPolicyItem(
                    title = "Device Admin Imtiyozi",
                    subtitle = "Android Enterprise Policy",
                    statusText = if (isDeviceAdminGranted) "Berilgan" else "Berilmagan",
                    isLocked = false,
                    icon = Icons.Default.VerifiedUser
                )
            }
        }

        // Server Sync Footer
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0F1823)),
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(CyberCardBorder, CyberCardBorder)))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Sync, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(text = "Oxirgi ma'muriy sinxronizatsiya: Bugun, 14:30", color = TextSecondary, fontSize = 10.sp)
                        Text(text = "Server: mdm.aegis-security.uz • Status: Muvaffaqiyatli", color = NeonCyan, fontSize = 10.sp)
                    }
                }
            }
        }

        // Author & About App Footer
        item {
            var showAboutDialog by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(false) }

            if (showAboutDialog) {
                AlertDialog(
                    onDismissRequest = { showAboutDialog = false },
                    confirmButton = {
                        Button(
                            onClick = { showAboutDialog = false },
                            colors = ButtonDefaults.buttonColors(containerColor = NeonCyan)
                        ) {
                            Text(text = "Yopish", color = CyberDarkBg, fontWeight = FontWeight.Bold)
                        }
                    },
                    containerColor = CyberCardBg,
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Security, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(24.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "CYBERGUARD Security", color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    },
                    text = {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(text = "📌 Yaratuvchi: Kenjaboyev Orifjon", color = NeonCyan, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Text(text = "⏱ Yaratilgan sana va vaqt: 2026-09-29 09:37:40", color = TextSecondary, fontSize = 11.sp)
                            Text(text = "📦 Ilova Versiyasi: v1.0.0 (Build 2026.09.29)", color = TextSecondary, fontSize = 11.sp)
                            Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(CyberCardBorder).padding(vertical = 4.dp))
                            Text(text = "🛠 Asosiy Funksiyalar va Imkoniyatlar:", color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Text(text = "• 🔒 Device Owner & Uninstall Blocking (MDM qat'iy himoya)", color = TextSecondary, fontSize = 11.sp)
                            Text(text = "• 🔍 Telefoni va APK fayllarni to'liq real skanerlash", color = TextSecondary, fontSize = 11.sp)
                            Text(text = "• 📡 APK manbasini (Telegram, Chrome) Super Adminga yetkazish", color = TextSecondary, fontSize = 11.sp)
                            Text(text = "• 📞 Firibgarlik qo'ng'iroqlari va USSD kodlarni avto-bloklash", color = TextSecondary, fontSize = 11.sp)
                            Text(text = "• 💬 SMS OTP va phishing xavflardan avtomatik himoya", color = TextSecondary, fontSize = 11.sp)
                            Text(text = "• 🧹 Telegram va tizim keshini bir bosishda tozalash", color = TextSecondary, fontSize = 11.sp)
                        }
                    }
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { showAboutDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B2B3A))
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Info, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Ilova Haqida To'liq Ma'lumot", color = NeonCyan, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Yaratuvchi: Kenjaboyev Orifjon",
                    color = TextPrimary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "CYBERGUARD Security & MDM Engine • 2026",
                    color = TextSecondary,
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Composable
fun AdminPolicyItem(
    title: String,
    subtitle: String,
    statusText: String,
    isLocked: Boolean,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = CyberCardBg),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(CyberCardBorder, Color(0xFF1E2D3D))))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFF003847)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = icon, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(18.dp))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(text = title, color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Text(text = subtitle, color = TextSecondary, fontSize = 10.sp)
                }
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(if (isLocked) AlertRedBg else SuccessGreenBg)
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (isLocked) {
                        Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = AlertRed, modifier = Modifier.size(10.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                    Text(
                        text = statusText,
                        color = if (isLocked) AlertRed else SuccessGreen,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
