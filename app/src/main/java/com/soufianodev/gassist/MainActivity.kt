package com.soufianodev.gassist

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.core.net.toUri


class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchAssistant()
        finish()
    }

    private fun launchAssistant() {
        val googlePkg = "com.google.android.googlequicksearchbox"
        

        try {
            val voiceIntent = Intent(Intent.ACTION_VOICE_COMMAND).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(googlePkg)
            }
            startActivity(voiceIntent)
            return
        } catch (_: ActivityNotFoundException) {
        }


        val assistantActivities = listOf(
            "com.google.android.apps.gsa.staticplugins.opa.OpaActivity",
            "com.google.android.googlequicksearchbox.VoiceSearchActivity"
        )

        for (activityName in assistantActivities) {
            try {
                val intent = Intent(Intent.ACTION_MAIN).apply {
                    component = ComponentName(googlePkg, activityName)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                startActivity(intent)
                return
            } catch (_: ActivityNotFoundException) {
                continue
            } catch (_: SecurityException) {
                continue
            }
        }

        try {
            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$googlePkg")).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
            )
        } catch (_: ActivityNotFoundException) {
            startActivity(
                Intent(Intent.ACTION_VIEW,
                    "https://play.google.com/store/apps/details?id=$googlePkg".toUri()).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
            )
        }
    }
}
