package com.example.network_doctor.data.repository

import android.util.Log
import com.example.network_doctor.domain.model.SpeedTestResult
import com.example.network_doctor.domain.model.TestStage
import com.example.network_doctor.domain.repository.SpeedTestRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import javax.inject.Inject
import kotlin.system.measureTimeMillis

class SpeedTestRepositoryImpl @Inject constructor(
    private val okHttpClient: OkHttpClient
) : SpeedTestRepository {

    private val TEST_FILE_URL = "https://proof.ovh.net/files/10Mb.dat" // Reliable HTTPS source
    private val PING_URL = "http://clients3.google.com/generate_204"

    override fun startSpeedTest(): Flow<SpeedTestResult> = flow {
        // 1. PING TEST
        emit(SpeedTestResult(stage = TestStage.PING, progress = 0f))
        val ping = measurePing()
        emit(SpeedTestResult(stage = TestStage.PING, pingMs = ping.toFloat(), progress = 1f))
        delay(500) // Brief pause

        // 2. DOWNLOAD TEST
        emit(SpeedTestResult(stage = TestStage.DOWNLOAD, pingMs = ping.toFloat(), progress = 0f))
        
        try {
            val request = Request.Builder().url(TEST_FILE_URL).build()
            val response = okHttpClient.newCall(request).execute()
            
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val body = response.body ?: throw IOException("Empty body")
            val inputStream = body.byteStream()
            val buffer = ByteArray(16384) // 16KB buffer
            var bytesRead: Int
            var totalBytesRead = 0L
            val startTime = System.currentTimeMillis()
            var lastUpdate = 0L

            // We limit the test to 15 seconds to ensure enough data for reliable speed
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                totalBytesRead += bytesRead
                val currentTime = System.currentTimeMillis()
                val duration = currentTime - startTime
                
                if (duration > 15000) break 

                // Update UI every ~100ms
                if (currentTime - lastUpdate > 100) {
                    // Use Double for calculation to avoid overflow/truncation
                    val timeSeconds = maxOf(1L, duration) / 1000.0
                    val bits = totalBytesRead * 8.0
                    val speedBps = bits / timeSeconds
                    val speedMbps = (speedBps / 1_000_000.0).toFloat()
                    
                    val progress = (duration / 15000f).coerceIn(0f, 1f)

                    emit(
                        SpeedTestResult(
                            stage = TestStage.DOWNLOAD,
                            speedMbps = speedMbps,
                            pingMs = ping.toFloat(),
                            progress = progress
                        )
                    )
                    lastUpdate = currentTime
                }
            }
            body.close()
            
            // Final Download Result
            val totalDuration = maxOf(1L, System.currentTimeMillis() - startTime)
            val finalBits = totalBytesRead * 8.0
            val finalSpeedMbps = (finalBits / (totalDuration / 1000.0) / 1_000_000.0).toFloat()
            
            emit(
                SpeedTestResult(
                    stage = TestStage.DOWNLOAD,
                    speedMbps = finalSpeedMbps,
                    pingMs = ping.toFloat(),
                    progress = 1f
                )
            )

        } catch (e: Exception) {
            Log.e("SpeedTest", "Error", e)
             emit(
                SpeedTestResult(
                    stage = TestStage.FINISHED,
                    error = e.message
                )
            )
            return@flow
        }
        
        delay(500)
        
        // 3. UPLOAD TEST (Simulated for now due to complexity of finding upload endpoints)
        // Todo: Implement real upload to a server that accepts POST
        emit(SpeedTestResult(stage = TestStage.UPLOAD, speedMbps = 0f, pingMs = ping.toFloat(), progress = 0f))
        // ... (Skipping real upload for this iteration, just finishing)
        
        emit(SpeedTestResult(stage = TestStage.FINISHED, progress = 1f))

    }.flowOn(Dispatchers.IO)

    private fun measurePing(): Long {
        return try {
            var totalTime = 0L
            val count = 3
            for (i in 1..count) {
                val request = Request.Builder().url(PING_URL).build()
                val time = measureTimeMillis {
                    okHttpClient.newCall(request).execute().close()
                }
                totalTime += time
            }
            totalTime / count
        } catch (e: Exception) {
            -1L
        }
    }
}
