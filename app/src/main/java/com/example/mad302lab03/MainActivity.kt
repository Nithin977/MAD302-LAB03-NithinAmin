/**
 * Course Code: MAD302
 * Lab Number: LAB 3
 * Name: Nithin
 * Student ID: A0019432
 * Date of Submission: April 2026
 *
 * Description:
 * This Android application demonstrates asynchronous data fetching using Kotlin Coroutines,.
 */

package com.example.mad302lab03

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * MainActivity is the main screen of the application.
 * It handles UI interaction, runtime permission requests,
 * asynchronous mock data fetching, and result display.
 */
class MainActivity : AppCompatActivity() {

    /** Button used to start the fetch operation. */
    private lateinit var btnFetchData: Button

    /** TextView used to display result, error, or permission messages. */
    private lateinit var tvResult: TextView

    /**
     * Launcher used to request Camera permission at runtime.
     * If permission is granted, data fetching starts.
     * If permission is denied, a message is shown in the UI.
     */
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                fetchData()
            } else {
                tvResult.text = "Permission denied. Camera permission is required to fetch data."
            }
        }

    /**
     * Called when the activity is created.
     *
     * Initializes the layout, connects UI elements,
     * and sets the click listener for the fetch button.
     *
     * @param savedInstanceState previously saved activity state, if available
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnFetchData = findViewById(R.id.btnFetchData)
        tvResult = findViewById(R.id.tvResult)

        btnFetchData.setOnClickListener {
            checkPermissionAndFetch()
        }
    }

    /**
     * Checks whether Camera permission is already granted.
     * If permission exists, it starts fetching data.
     * Otherwise, it requests permission from the user.
     */
    private fun checkPermissionAndFetch() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fetchData()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    /**
     * Simulates an API call using Kotlin Coroutines.
     *
     * The method:
     * - shows a loading message
     * - waits for 2 seconds using delay(2000)
     * - randomly simulates success or network failure
     * - updates the UI with either the result or an error message
     *
     * Error handling is done using try-catch.
     */
    private fun fetchData() {
        lifecycleScope.launch {
            tvResult.text = "Fetching data..."

            try {
                // Simulate a network/API delay of 2 seconds
                delay(2000)

                // Randomly choose whether the simulated request should fail
                val simulateFailure = Random.nextBoolean()

                if (simulateFailure) {
                    throw Exception("Simulated network failure.")
                }

                // Show successful data fetch result
                tvResult.text = "Data fetched successfully: Mock API response received."
            } catch (e: Exception) {
                // Show error message when simulated failure occurs
                tvResult.text = "Error: ${e.message}"
            }
        }
    }
}