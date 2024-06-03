package com.org.wfnr_2024.Utils

import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.org.wfnr_2024.Activity.MyQuestionActivity
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.CMPL_Local_Model.Session
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.ItineraryDetails
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.Topic_Itinerary
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_LOCAL_ViewModel

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class ConstantsApp {

    companion object
    {

        const val BASE_URL="https://programme-api.conventus.de/api/"
        //const val LOCAL_URL="http://192.168.0.160:8095/ImpactWebService/rest/LLEwebcall/"
        const val BASE_URL1="https://iam.conventus-apps.de/auth/realms/external_services/protocol/openid-connect/"
        const val BASE_URL2="https://regasus-api.conventus.de/external/api/v1/"
        const val BASE_URL3="https://www.telemedocket.com/WFNR-2024/public/api/"





        const val TAG="mytag"
        const val Filter="Filter"
        const val Session="Session"


        fun checkInternetConenction(mContext: Context): Boolean {
            val cm = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = cm.activeNetworkInfo
            return info != null && info.isConnected
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getCurrentDate(): String {
           /* val currentDate = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd") // Choose your desired date format
            return currentDate.format(formatter)*/

            val currentDateTime = LocalDateTime.now()
            //val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss") // Choose your desired date and time format
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy") // Choose your desired date and time format

            return currentDateTime.format(formatter)
        }



        fun saveBitmapToFile(bitmap: Bitmap, context: Context): String {
            val filesDir = context.filesDir
            val imageFile = File(filesDir, "image.png")

            val outputStream: FileOutputStream
            try {
                outputStream = FileOutputStream(imageFile)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.flush()
                outputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return imageFile.absolutePath
        }



        fun saveBitmapToFile1(bitmap: Bitmap, fileName: String, context: Context): File {
            // Get the directory for the user's public pictures directory.
            val picturesDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

            // Create a file to save the image.
            val file = File(picturesDirectory, fileName)

            try {
                FileOutputStream(file).use { outStream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
                    outStream.flush()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return file
        }

        fun addTextViewValues(textView1: TextView, textView2: TextView): Float {
            // Get text from TextViews, remove leading '+' or '-' signs, then convert to BigDecimal, considering empty as zero
            val text1 = textView1.text.toString().replace("^[-+]+".toRegex(), "")
            val text2 = textView2.text.toString().replace("^[-+]+".toRegex(), "")

            val value1 = BigDecimal(text1.toDoubleOrNull() ?: 0.0)
            val value2 = BigDecimal(text2.toDoubleOrNull() ?: 0.0)

            // Perform subtraction and return the result as Float
            return value1.add(value2).toFloat()
        }

        fun moveImageToLLEFolder(context: Context, sourceUri: Uri, fileName: String): String? {
            val lleFolder = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "LLE")

            if (!lleFolder.exists()) {
                lleFolder.mkdirs()
            }

            //val fileName = "surgical_image.jpg" // You can use a specific file name
            val destinationFile = File(lleFolder, fileName)

            try {
                copyFile(context, sourceUri, destinationFile)
                return destinationFile.absolutePath
            } catch (e: IOException) {
                Log.e(ConstantsApp.TAG, "Error moving image to LLE folder: ${e.message}")
                return null
            }
        }

        private fun copyFile(context: Context, sourceUri: Uri, destinationFile: File) {
            context.contentResolver.openInputStream(sourceUri)?.use { inputStream ->
                FileOutputStream(destinationFile).use { outputStream ->
                    val buf = ByteArray(1024)
                    var bytesRead: Int
                    while (inputStream.read(buf).also { bytesRead = it } > 0) {
                        outputStream.write(buf, 0, bytesRead)
                    }
                }
            }
        }

        fun getFileNameFromPath(filePath: String): String {
            val file = File(filePath)
            return file.name
        }

        fun formatAadharNumber(input: String): String {
            return input.chunked(4).joinToString("-")
        }

        fun getCurrentOnlyDate(): String {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH) + 1 // Month index starts from 0
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            return "$year-$month-$day"
        }

        fun getCurrentTime(): String {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY) // 24-hour format
            val minute = calendar.get(Calendar.MINUTE)
            val second = calendar.get(Calendar.SECOND)
            return "$hour:$minute:$second"
        }

        fun convertDateFormat(inputDate: String, inputFormat: String, outputFormat: String): String {
            val inputDateFormat = SimpleDateFormat(inputFormat, Locale.getDefault())
            val outputDateFormat = SimpleDateFormat(outputFormat, Locale.getDefault())

            return try {
                val date = inputDateFormat.parse(inputDate)
                outputDateFormat.format(date!!)
            } catch (e: Exception) {
                e.printStackTrace()
                "" // Return empty string in case of any error
            }
        }

         fun getFormattedDate(dateString: String): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = inputFormat.parse(dateString) ?: return ""

            val dayOfWeek = SimpleDateFormat("E", Locale.getDefault()).format(date)
            val dayOfMonth = SimpleDateFormat("dd", Locale.getDefault()).format(date)
            val monthName = SimpleDateFormat("MMM", Locale.getDefault()).format(date)

            return "$dayOfWeek, $dayOfMonth $monthName"
        }

         fun formatTime(timestamp: String): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())
            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val date = dateFormat.parse(timestamp) ?: return ""
            return timeFormat.format(date)
        }

        fun formatDate(inputDate: String): String {
            val sdfInput = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = sdfInput.parse(inputDate)

            val sdfOutput = SimpleDateFormat("EEE,dd-MM-yyyy", Locale.getDefault())
            return sdfOutput.format(date)
        }


        /* fun convertGMT7ToLocalTime(gmt7Time: String): String {
            val gmt7TimeZone = TimeZone.getTimeZone("GMT-7")
            val localTimeZone = TimeZone.getDefault()

            // Define the date format and set the time zone to GMT-7
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            dateFormat.timeZone = gmt7TimeZone

            // Parse the GMT-7 time
            val date = dateFormat.parse(gmt7Time)

            // Set the date format to the local time zone
            dateFormat.timeZone = localTimeZone
            return dateFormat.format(date)
        }*/

        fun convertGMT7ToLocalTime(gmt7Time: String): String {
            // Use the ISO 8601 format to parse the input string
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("GMT-7")

            // Parse the input date string
            val date = inputFormat.parse(gmt7Time)

            // Format the date into the local time zone
            val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            outputFormat.timeZone = TimeZone.getDefault()

            return outputFormat.format(date)
        }

        fun separateDateAndTime(dateTime: String): Pair<String, String> {
            val parts = dateTime.split(" ")
            return Pair(parts[0], parts[1])
        }

        // Function to parse the date-time string
        @RequiresApi(Build.VERSION_CODES.O)
        fun parseDateTime(dateTimeString: String): LocalDateTime {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            return LocalDateTime.parse(dateTimeString, formatter)
        }

        /*@RequiresApi(Build.VERSION_CODES.O)
        fun getEarliestFutureDateTime(topicItineraries: List<Topic_Itinerary>): Pair<String, String>? {
            if (topicItineraries.isEmpty()) return null

            val now = LocalDateTime.now()

            // Filter out itineraries that are in the past
            val futureItineraries = topicItineraries.filter { itinerary ->
                val itineraryDateTime = LocalDateTime.parse(
                    "${itinerary.begin_date_formated} ${itinerary.begin_time_formated}",
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                )
                itineraryDateTime.isAfter(now)
            }

            if (futureItineraries.isEmpty()) return null

            // Find the earliest future itinerary
            val earliestFutureItinerary = futureItineraries.minByOrNull { itinerary ->
                LocalDateTime.parse(
                    "${itinerary.begin_date_formated} ${itinerary.begin_time_formated}",
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                )
            }

            // Extract date and time from the earliest future itinerary
            val earliestDate = earliestFutureItinerary?.begin_date_formated ?: ""
            val earliestTime = earliestFutureItinerary?.begin_time_formated ?: ""

            return if (earliestDate.isNotEmpty() && earliestTime.isNotEmpty()) {
                earliestDate to earliestTime
            } else {
                null
            }
        }*/

        @RequiresApi(Build.VERSION_CODES.O)
        fun getEarliestFutureDateTime(topicItineraries: List<Topic_Itinerary>): ItineraryDetails? {
            if (topicItineraries.isEmpty()) return null

            val now = LocalDateTime.now()

            // Filter out itineraries that are in the past
            val futureItineraries = topicItineraries.filter { itinerary ->
                val itineraryDateTime = LocalDateTime.parse(
                    "${itinerary.begin_date_formated} ${itinerary.begin_time_formated}",
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                )
                itineraryDateTime.isAfter(now)
            }

            if (futureItineraries.isEmpty()) return null

            // Find the earliest future itinerary
            val earliestFutureItinerary = futureItineraries.minByOrNull { itinerary ->
                LocalDateTime.parse(
                    "${itinerary.begin_date_formated} ${itinerary.begin_time_formated}",
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                )
            }

            // Extract details from the earliest future itinerary
            val earliestDate = earliestFutureItinerary?.begin_date_formated ?: ""
            val earliestTime = earliestFutureItinerary?.begin_time_formated ?: ""
            val topicName = earliestFutureItinerary?.topic_name ?: ""
            val roomName = earliestFutureItinerary?.room_name ?: ""

            return if (earliestDate.isNotEmpty() && earliestTime.isNotEmpty() && topicName.isNotEmpty() && roomName.isNotEmpty()) {
                ItineraryDetails(earliestDate, earliestTime, topicName, roomName)
            } else {
                null
            }
        }

        fun getChairAndSpeakerNames(session: Session): Pair<List<String>, List<String>> {
            val chairNames = session.chairs?.map { chair ->
                "${chair.person.firstName} ${chair.person.lastName}"
            } ?: emptyList()

            val speakerNames = session.program_points?.flatMap { programPoint ->
                programPoint.speakers.map { speaker ->
                    speaker.person?.let { "${it.firstName} ${it.lastName}" } ?: "Unknown Speaker"
                }
            } ?: emptyList()

            return Pair(chairNames, speakerNames)
        }
        fun getChairAndSpeakerNames(sessions: List<Session>): Pair<List<String>, List<String>> {
            val chairNames = mutableListOf<String>()
            val speakerNames = mutableListOf<String>()

            sessions.forEach { session ->
                session.chairs?.map { chair ->
                    chairNames.add("${chair.person.title?:""} ${chair.person.firstName} ${chair.person.lastName}")
                }

                session.program_points?.flatMap { programPoint ->
                    programPoint.speakers.map { speaker ->
                        speaker.person?.let {
                            speakerNames.add("${it.title?:""} ${it.firstName} ${it.lastName}")
                        } ?: speakerNames.add("Unknown Speaker")
                    }
                }
            }

            return Pair(chairNames, speakerNames)
        }








































    }
}