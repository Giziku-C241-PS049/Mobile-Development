package com.example.giziku.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.SystemClock
import android.util.Log
import com.example.giziku.R
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.CastOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.vision.classifier.Classifications
import org.tensorflow.lite.task.vision.classifier.ImageClassifier
import java.io.BufferedReader
import java.io.InputStreamReader

data class NutritionData(
    val name: String,
    val calories: Double,
    val fat: Double,
    val carbohydrates: Double,
    val protein: Double
)

object FoodNutritionMap {
    private val map = mapOf(
        "anggur" to NutritionData("anggur", 3.0, 0.01, 0.9, 0.04),
        "apel" to NutritionData("apel", 58.0, 0.4, 14.9, 0.3),
        "ayam_betutu" to NutritionData("ayam betutu", 425.0, 34.8, 6.32, 22.6),
        "ayam_goreng" to NutritionData("ayam goreng", 260.0, 14.55, 10.76, 21.93),
        "ayam_pop" to NutritionData("ayam pop", 265.0, 8.45, 0.0, 13.67),
        "bakso" to NutritionData("bakso", 57.0, 3.69, 2.12, 3.47),
        "batagor" to NutritionData("batagor", 290.0, 14.92, 29.14, 10.28),
        "burger" to NutritionData("burger", 258.0, 7.8, 32.1, 12.8),
        "cherry" to NutritionData("cherry", 87.0, 0.3, 22.0, 1.4),
        "cireng" to NutritionData("cireng", 70.0, 4.53, 6.35, 0.94),
        "coto_makassar" to NutritionData("coto makassar", 289.0, 15.25, 12.92, 25.4),
        "dendeng" to NutritionData("dendeng", 82.0, 5.12, 2.2, 6.64),
        "gudeg" to NutritionData("gudeg", 127.0, 4.71, 22.54, 1.82),
        "gulai_ikan" to NutritionData("gulai ikan", 100.0, 4.59, 1.9, 12.4),
        "jeruk" to NutritionData("jeruk", 62.0, 0.16, 15.39, 1.23),
        "kerak_telor" to NutritionData("kerak telor", 452.0, 15.81, 55.58, 20.11),
        "kiwi" to NutritionData("kiwi", 46.0, 0.4, 11.14, 0.87),
        "mangga" to NutritionData("mangga", 65.0, 0.27, 17.0, 0.51),
        "mie_aceh" to NutritionData("mie aceh", 116.0, 3.58, 17.96, 3.34),
        "nasi_goreng" to NutritionData("nasi goreng", 166.0, 6.32, 21.06, 6.3),
        "nasi_kuning" to NutritionData("nasi kuning", 150.0, 0.27, 32.96, 2.99),
        "nasi_padang" to NutritionData("nasi padang", 664.0, 15.0, 70.0, 70.0),
        "nasi_pecel" to NutritionData("nasi pecel", 230.0, 9.33, 31.74, 6.58),
        "pempek" to NutritionData("pempek", 39.0, 1.04, 4.72, 2.52),
        "pisang" to NutritionData("pisang", 89.0, 0.33, 22.84, 1.09),
        "rawon" to NutritionData("rawon", 119.0, 7.4, 3.48, 9.6),
        "rendang" to NutritionData("rendang", 195.0, 11.07, 4.49, 19.68),
        "sate" to NutritionData("sate", 32.0, 2.11, 0.72, 2.84),
        "sawo" to NutritionData("sawo", 83.0, 1.1, 19.96, 0.44),
        "serabi" to NutritionData("serabi", 108.0, 1.8, 21.05, 1.59),
        "soto" to NutritionData("soto", 312.0, 14.29, 19.55, 24.01),
        "strawberi" to NutritionData("strawberi", 32.0, 0.3, 7.68, 0.67),
        "tahu_sumedang" to NutritionData("tahu sumedang", 35.0, 2.42, 1.3, 2.12),
        "telur_balado" to NutritionData("telur balado", 71.0, 5.75, 1.22, 3.57),
        "telur_dadar" to NutritionData("telur dadar", 93.0, 7.33, 0.42, 6.48),
    )

    fun getNutritionData(label: String): NutritionData? {
        return map[label]
    }
}

class ImageClassifierHelper(
    private var threshold: Float = 0.1f,
    private var maxResults: Int = 3,
    private val modelName: String = "model_mobile_valacc_0.9032.tflite",
    val context: Context,
    val classifierListener: ClassifierListener?
) {

    private var imageClassifier: ImageClassifier? = null
    private lateinit var labels: List<String>

    init {
        loadLabels()
        setupImageClassifier()
    }

    interface ClassifierListener {
        fun onError(error: String)
        fun onResults(
            formattedResults: String,
            inferenceTime: Long,
            confidenceScore: Float,
        )
    }

    private fun loadLabels() {
        try {
            context.assets.open("label_map.txt").use { inputStream ->
                BufferedReader(InputStreamReader(inputStream)).use { reader ->
                    labels = reader.readLines()
                }
            }
        } catch (e: Exception) {
            val errorMessage = "Error loading labels: ${e.message}"
            classifierListener?.onError(errorMessage)
            Log.e(TAG, errorMessage)
        }
    }

    private fun setupImageClassifier() {
        val optionBuilder = ImageClassifier.ImageClassifierOptions.builder()
            .setScoreThreshold(threshold)
            .setMaxResults(maxResults)
        val baseOptionBuilder = BaseOptions.builder()
            .setNumThreads(4)
        optionBuilder.setBaseOptions(baseOptionBuilder.build())

        try {
            Log.d(TAG, "Initializing ImageClassifier with model: $modelName")
            imageClassifier = ImageClassifier.createFromFileAndOptions(
                context,
                modelName,
                optionBuilder.build()
            )
            Log.d(TAG, "ImageClassifier initialized successfully")
        } catch (e: IllegalArgumentException) {
            val errorMessage = "Error initializing ImageClassifier: ${e.message}"
            classifierListener?.onError(errorMessage)
            Log.e(TAG, errorMessage)
        } catch (e: Exception) {
            val errorMessage = "Unexpected error initializing ImageClassifier: ${e.message}"
            classifierListener?.onError(errorMessage)
            Log.e(TAG, errorMessage)
        }
    }

    fun classifyStaticImage(imageUri: Uri) {
        if (imageClassifier == null) {
            setupImageClassifier()
        }

        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, imageUri))
        } else {
            context.contentResolver.openInputStream(imageUri)?.use {
                BitmapFactory.decodeStream(it)
            }
        }

        val argb8888Bitmap = bitmap?.copy(Bitmap.Config.ARGB_8888, true)

        if (argb8888Bitmap == null) {
            classifierListener?.onError("Failed to decode bitmap")
            return
        }

        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.BILINEAR))
            .add(CastOp(DataType.FLOAT32))
            .build()

        val tensorImage = imageProcessor.process(TensorImage.fromBitmap(argb8888Bitmap))

        val inferenceTime = SystemClock.uptimeMillis()
        val results = imageClassifier?.classify(tensorImage)
        val inferenceTimeDuration = SystemClock.uptimeMillis() - inferenceTime

        if (results != null && results.isNotEmpty()) {
            val classificationResults = results[0].categories
            val formattedResults = StringBuilder()
            for (category in classificationResults) {
                val label = category.label
                val confidence = category.score
                val nutritionData = FoodNutritionMap.getNutritionData(label)

                formattedResults.append("Label: $label\n")
                formattedResults.append("Confidence: %.2f\n".format(confidence))
                if (nutritionData != null) {
                    formattedResults.append("Calories: ${nutritionData.calories}\n")
                    formattedResults.append("Fat: ${nutritionData.fat}\n")
                    formattedResults.append("Carbohydrates: ${nutritionData.carbohydrates}\n")
                    formattedResults.append("Protein: ${nutritionData.protein}\n")
                }
                formattedResults.append("\n")
            }
            classifierListener?.onResults(
                formattedResults.toString(),
                inferenceTimeDuration,
                classificationResults[0].score
            )
        } else {
            classifierListener?.onError("No classification results.")
        }
    }

    companion object {
        private const val TAG = "ImageClassifierHelper"
    }
}
