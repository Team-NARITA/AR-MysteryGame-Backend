package com.teamnarita.armysterygamebackend

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.nio.file.Paths
import kotlin.io.path.inputStream

@Service
class FirebaseInitializer {
    companion object {
        private val logger = LoggerFactory.getLogger(FirebaseInitializer::class.java)
    }

    @PostConstruct
    fun onStart() {
        logger.info("Initializing FirebaseApp...")
        try {
            initApp()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun initApp() {
        if (FirebaseApp.getApps() == null || FirebaseApp.getApps().isEmpty()) {
            val file = Paths.get("serviceAccountKey.json")
            val options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(file.inputStream()))
                .build()

            FirebaseApp.initializeApp(options)
        }
    }
}