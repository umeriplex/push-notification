package com.example.pushnoti

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import com.example.pushnoti.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.messaging.FirebaseMessaging


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
                startActivityForResult(intent, 1)
            }
        }

        binding.topicSubscribe.setEndIconOnClickListener{
            if (!binding.topicToSubscribe.text.toString().isNullOrEmpty()) {
                MessagingServices.subscribeTopic(this@MainActivity,binding.topicToSubscribe.text.toString())
            } else {
                binding.topicToSubscribe.error = "Fill this field"
            }
        }

        binding.topicUnsubscribe.setEndIconOnClickListener {
            if (!binding.topicToUnsubscribe.text.toString().isNullOrEmpty()) {
                MessagingServices.unsubscribeTopic(this@MainActivity,binding.topicToUnsubscribe.text.toString())
            } else {
                binding.topicToUnsubscribe.error = "Fill this field"
            }
        }

        binding.sendBtn.setOnClickListener {
            when {
                binding.titleEdt.text.toString().isEmpty() -> binding.titleEdt.error = "Fill this field"
                binding.contentEdt.text.toString().isEmpty() -> binding.contentEdt.error = "Fill this field"
                else -> {
                    MessagingServices.sendMessage(binding.titleEdt.text.toString(),
                        binding.contentEdt.text.toString(),
                        binding.topicToSend.text.toString())
                }
            }
        }

    }
}