package com.nexus.studio.ui.workspace

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ChevronDown
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShieldCheck
import androidx.compose.material.icons.filled.Sliders
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nexus.studio.ui.theme.*

@Composable
fun WorkspaceScreen(
    onNavigateBackToAuth: () -> Unit
) {
    var isLeftSidebarExpanded by remember { mutableStateOf(true) }
    var isRightSidebarExpanded by remember { mutableStateOf(true) }
    var selectedModel by remember { mutableStateOf("Google Gemini 2.5 Pro") }
    var temperatureVal by remember { mutableStateOf(0.20f) }
    var chatInputText by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceLight)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // 1. LEFT SIDEBAR (Collapsible & Monaco Text Sections)
        AnimatedVisibility(visible = isLeftSidebarExpanded) {
            Surface(
                modifier = Modifier
                    .width(250.dp)
                    .fillMaxHeight(),
                color = PureWhite,
                border = BorderStroke(1.dp, BorderLight)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        // Header & Logo
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text("✦", fontSize = 16.sp, color = TextPrimary, fontWeight = FontWeight.Bold)
                                Text("NEXUS STUDIO", fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = TextPrimary, letterSpacing = 0.5.sp)
                            }
                            IconButton(onClick = { isLeftSidebarExpanded = false }, modifier = Modifier.size(24.dp)) {
                                Icon(Icons.Outlined.Close, contentDescription = "Close Left", tint = TextSecondary, modifier = Modifier.size(16.dp))
                            }
                        }

                        HorizontalDivider(color = BorderLight)
                        Spacer(modifier = Modifier.height(12.dp))

                        // WORKSPACE SECTION
                        Text("WORKSPACE", fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = TextMuted, letterSpacing = 0.5.sp)
                        Spacer(modifier = Modifier.height(6.dp))
                        TreeItemRow("ScreenRecorderService.kt", isActive = true)
                        TreeItemRow("EglEncoderPipeline.kt", isActive = false)
                        TreeItemRow("gateway-engine.ts", isActive = false)
                        TreeItemRow("tsconfig.json", isActive = false)

                        Spacer(modifier = Modifier.height(16.dp))

                        // TOOLS SECTION
                        Text("TOOLS & AUDITS", fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = TextMuted, letterSpacing = 0.5.sp)
                        Spacer(modifier = Modifier.height(6.dp))
                        ActionItemRow("Bugbot Static Audit")
                        ActionItemRow("Composer Multi-File")
                        ActionItemRow("@codebase Indexing")

                        Spacer(modifier = Modifier.height(16.dp))

                        // SYSTEM CONFIG SECTION
                        Text("SYSTEM CONFIG", fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = TextMuted, letterSpacing = 0.5.sp)
                        Spacer(modifier = Modifier.height(6.dp))
                        ActionItemRow("API Provider Vault")
                        ActionItemRow("Environment Vars")
                    }

                    // Left Sidebar Footer: Google Sign-In Profile
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(10.dp),
                        color = PureWhite,
                        border = BorderStroke(1.dp, BorderLight)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .background(TextPrimary, shape = CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("LL", color = PureWhite, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Leon Leon", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                                Text("leonpresistforever@gmail.com", fontSize = 10.sp, color = TextMuted)
                            }
                            Icon(Icons.Default.MoreVert, contentDescription = "More", tint = TextMuted, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }

        // 2. MIDDLE PLATE (Monaco Code Viewport + Sleek Bottom Chatbox)
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(PureWhite)
        ) {
            // Workspace Top Bar
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                color = PureWhite,
                border = BorderStroke(1.dp, BorderLight)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        if (!isLeftSidebarExpanded) {
                            IconButton(onClick = { isLeftSidebarExpanded = true }, modifier = Modifier.size(28.dp)) {
                                Icon(Icons.Default.Folder, contentDescription = "Open Left", tint = TextSecondary, modifier = Modifier.size(18.dp))
                            }
                        }
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = SurfaceLight,
                            border = BorderStroke(1.dp, BorderLight)
                        ) {
                            Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Code, contentDescription = null, tint = PrimaryBlue, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("ScreenRecorderService.kt", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            }
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Surface(
                            onClick = onNavigateBackToAuth,
                            shape = RoundedCornerShape(6.dp),
                            color = SurfaceLight,
                            border = BorderStroke(1.dp, BorderLight)
                        ) {
                            Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextSecondary, modifier = Modifier.size(12.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Auth", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
                            }
                        }
                        if (!isRightSidebarExpanded) {
                            IconButton(onClick = { isRightSidebarExpanded = true }, modifier = Modifier.size(28.dp)) {
                                Icon(Icons.Default.Sliders, contentDescription = "Open Right", tint = TextSecondary, modifier = Modifier.size(18.dp))
                            }
                        }
                    }
                }
            }

            // Code Viewport & Response Stream
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    // Monaco Code Editor Block
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        color = SurfaceLight,
                        border = BorderStroke(1.dp, BorderLight)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(BorderLight)
                                    .padding(horizontal = 12.dp, vertical = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("app/src/main/java/com/example/core/ScreenRecorderService.kt", fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = TextMuted)
                                Text("Kotlin", fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = TextPrimary, fontWeight = FontWeight.Bold)
                            }
                            Text(
                                text = """private fun cleanupAndStopService() {
    isCurrentlyRecording = false
    try {
        if (wakeLock?.isHeld == true) { wakeLock?.release() }
    } catch (e: Exception) { Log.w(TAG, e.message) }

    stopForeground(true)
    stopSelf()
    // Post notification directly to avoid ForegroundServiceStartNotAllowedException
    showControlCenter(applicationContext, currentConfig)
}""",
                                fontSize = 12.sp,
                                fontFamily = FontFamily.Monospace,
                                color = TextPrimary,
                                lineHeight = 18.sp,
                                modifier = Modifier.padding(14.dp)
                            )
                        }
                    }
                }

                item {
                    // Agent Chat Stream Message Block
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        color = PureWhite,
                        border = BorderStroke(1.dp, BorderLight)
                    ) {
                        Row(modifier = Modifier.padding(14.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(26.dp)
                                    .background(SurfaceLight, shape = RoundedCornerShape(6.dp))
                                    .border(1.dp, BorderLight, shape = RoundedCornerShape(6.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("✦", fontSize = 12.sp, color = TextPrimary)
                            }
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Text("Nexus AI Agent", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                                    Text("Just now", fontSize = 10.sp, color = TextMuted)
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Analyzed ScreenRecorderService.kt. Swapped teardown order to release virtualDisplay before eglPipeline, preventing native GPU driver surface crashes on Android 12+.",
                                    fontSize = 12.sp,
                                    color = TextSecondary,
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    }
                }
            }

            // SLEEK WHITE CHATBOX (At Bottom of Middle Plate)
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                shape = RoundedCornerShape(16.dp),
                color = PureWhite,
                border = BorderStroke(1.dp, BorderLight),
                shadowElevation = 4.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Plus Button (Left)
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(SurfaceLight, shape = RoundedCornerShape(8.dp))
                            .border(1.dp, BorderLight, shape = RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add Context", tint = TextSecondary, modifier = Modifier.size(16.dp))
                    }

                    // Input Textfield
                    TextField(
                        value = chatInputText,
                        onValueChange = { chatInputText = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Ask Nexus AI to generate code, refactor, or run @codebase audit...", fontSize = 12.sp, color = TextMuted) },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )

                    // Wand Button
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(SurfaceLight, shape = RoundedCornerShape(8.dp))
                            .border(1.dp, BorderLight, shape = RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = "Wand Prompt", tint = PrimaryPurple, modifier = Modifier.size(15.dp))
                    }

                    // 3D Send Accent Button (Right)
                    Surface(
                        onClick = { chatInputText = "" },
                        shape = RoundedCornerShape(8.dp),
                        color = TextPrimary,
                        shadowElevation = 2.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text("Send", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PureWhite)
                            Icon(Icons.Default.Send, contentDescription = "Send", tint = PureWhite, modifier = Modifier.size(12.dp))
                        }
                    }
                }
            }
        }

        // 3. RIGHT SIDEBAR (Model Selection & Temperature Slider)
        AnimatedVisibility(visible = isRightSidebarExpanded) {
            Surface(
                modifier = Modifier
                    .width(270.dp)
                    .fillMaxHeight(),
                color = PureWhite,
                border = BorderStroke(1.dp, BorderLight)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("MODEL PARAMETERS", fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = TextMuted, letterSpacing = 0.5.sp)
                        IconButton(onClick = { isRightSidebarExpanded = false }, modifier = Modifier.size(24.dp)) {
                            Icon(Icons.Outlined.Close, contentDescription = "Close Right", tint = TextSecondary, modifier = Modifier.size(16.dp))
                        }
                    }

                    HorizontalDivider(color = BorderLight)

                    // Model Selection
                    Column {
                        Text("Model Selection", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        Spacer(modifier = Modifier.height(6.dp))
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            color = SurfaceLight,
                            border = BorderStroke(1.dp, BorderLight)
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(selectedModel, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                                Icon(Icons.Default.ChevronDown, contentDescription = null, tint = TextMuted, modifier = Modifier.size(14.dp))
                            }
                        }
                    }

                    // Temperature Slider
                    Column {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Temperature", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Text(String.format("%.2f", temperatureVal), fontSize = 11.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, color = TextPrimary)
                        }
                        Slider(
                            value = temperatureVal,
                            onValueChange = { temperatureVal = it },
                            valueRange = 0.0f..1.0f,
                            colors = SliderDefaults.colors(
                                thumbColor = TextPrimary,
                                activeTrackColor = TextPrimary,
                                inactiveTrackColor = BorderLight
                            )
                        )
                    }

                    // System Instructions
                    Column {
                        Text("System Instructions", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        Spacer(modifier = Modifier.height(6.dp))
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(90.dp),
                            shape = RoundedCornerShape(8.dp),
                            color = SurfaceLight,
                            border = BorderStroke(1.dp, BorderLight)
                        ) {
                            Text(
                                text = "// Directives:\n- Enforce Monaco monochrome style\n- Defensive null safety & clean Kotlin DSL",
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace,
                                color = TextSecondary,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TreeItemRow(name: String, isActive: Boolean) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        shape = RoundedCornerShape(6.dp),
        color = if (isActive) SurfaceLight else Color.Transparent
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(Icons.Default.Code, contentDescription = null, tint = TextMuted, modifier = Modifier.size(14.dp))
            Text(name, fontSize = 12.sp, fontWeight = if (isActive) FontWeight.Bold else FontWeight.Medium, color = TextPrimary)
        }
    }
}

@Composable
private fun ActionItemRow(name: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(Icons.Default.ShieldCheck, contentDescription = null, tint = TextMuted, modifier = Modifier.size(14.dp))
        Text(name, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = TextSecondary)
    }
}
