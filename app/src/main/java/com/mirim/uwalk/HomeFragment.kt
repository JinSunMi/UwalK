package com.mirim.uwalk

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.*
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mirim.uwalk.databinding.FragmentHomeBinding
import java.util.*
import kotlin.math.min
import kotlin.math.max


class HomeFragment: Fragment(), SensorEventListener {
    lateinit var binding: FragmentHomeBinding
    private var sensorManager: SensorManager? = null
    private var running = false
    private var totalSteps = 0f
    private var previousTotalSteps = 0f
    var currentSteps: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.toolbar.imageToolbarProfile.setOnClickListener {
            startActivity(Intent(context, MypageActivity::class.java))
        }

        saveData()
        loadData()
        resetSteps()

        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val alarmMgr = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val alarmIntent: PendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        intent.putExtra("steps", totalSteps)

        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, 0)

        alarmMgr.setInexactRepeating(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY, alarmIntent
        )

       // updateDrawing()
//
//        val bitmap = drawCircle(
//            color = getResources().getColor(R.color.main),
//            radius = 300f,
//            canvasBackground = Color.TRANSPARENT
//        )
//
////        binding.circleMainGray.setImageBitmap(bitmap)
//
//        val bitmap = Bitmap.createBitmap(
//            (280 * Resources.getSystem().displayMetrics.density + 0.5f).toInt(),
//            (280 * Resources.getSystem().displayMetrics.density + 0.5f).toInt(),
//            Bitmap.Config.ARGB_8888
//        )
//
//        binding.circleMainGray.setImageBitmap(bitmap)
//
//        val canvas = Canvas(
//            bitmap
//        )
//        val stepCircle = StepCircle(ctx = requireContext())
//        stepCircle.draw(canvas)

        setProgressTo((totalSteps / 10000 * 100).toInt())

        Log.d("progress", (totalSteps / 10000 * 100).toInt().toString())


        return view
    }

    fun setProgressTo(progress: Int){
        binding.circleMain.progress = progress
        Log.d("progress", (totalSteps / 10000 * 100).toInt().toString())
    }
//
//    private fun updateDrawing(){
//        val bitmap = drawArcBetweenTwoPoints()
//        binding.circleMainColor.setImageBitmap(bitmap)
//    }
//
//    fun drawArcBetweenTwoPoints():Bitmap?{
//        val bitmap = Bitmap.createBitmap(
//            (280 * Resources.getSystem().displayMetrics.density + 0.5f).toInt(),
//            (280 * Resources.getSystem().displayMetrics.density + 0.5f).toInt(),
//            Bitmap.Config.ARGB_8888
//        )
//
//        // canvas for drawing
//        val canvas = Canvas(bitmap).apply {
//            drawColor(Color.TRANSPARENT)
//        }
//
//        // paint to draw
//        val paint = Paint()
//
//        // manage points
//        var point = Point(canvas.width/2, 0)
//        var point2 = Point((canvas.width*0.25).toInt(), (canvas.width*0.75).toInt())
//        var topPoint = point
//        var bottomPoint = point2
//        if (point.y >= point2.y) {
//            topPoint = point2
//            bottomPoint = point
//        }
//        var centerPoint = Point(topPoint.x,bottomPoint.y)
//
//
//        // manage strait vertical and horizontal line
//        var isVerticalLine = false
//        var isHorizontalLine = false
//
//        if (bottomPoint.x == topPoint.x){isVerticalLine = true}
//        if (bottomPoint.y == topPoint.y){isHorizontalLine = true}
//
//        if (isVerticalLine){
//            val lineLength = bottomPoint.y - topPoint.y
//            centerPoint = Point(bottomPoint.x, topPoint.y + lineLength/2)
//        }
//        if (isHorizontalLine){
//            val lineLength = max(topPoint.x,bottomPoint.x) -
//                    min(topPoint.x,bottomPoint.x)
//            centerPoint = Point(bottomPoint.x + lineLength/2, topPoint.y)
//        }
//
//
//        // manage rectF
//        var xLength = max(centerPoint.x,bottomPoint.x) -
//                min(centerPoint.x,bottomPoint.x)
//        var yLength = centerPoint.y - topPoint.y
//
//        if (isVerticalLine){
//            yLength = bottomPoint.y - centerPoint.y
//            xLength = yLength
//        }
//        if (isHorizontalLine){
//            xLength = centerPoint.x - min(topPoint.x,bottomPoint.x)
//            yLength = xLength
//        }
//        val rectF = RectF(
//            centerPoint.x - xLength.toFloat(),
//            centerPoint.y - yLength.toFloat(),
//            centerPoint.x + xLength.toFloat(),
//            centerPoint.y + yLength.toFloat()
//        )
//
//
//        // manage angle for ark
//        var startAngle = 270F
//        var sweepAngle = 90F
//        if (bottomPoint.x < centerPoint.x){startAngle = 180F}
//        if (isHorizontalLine){
//            startAngle = 180F
//            sweepAngle = 180F
//        }
//        if (isVerticalLine){
//            startAngle = 270F
//            sweepAngle = 180F
//        }
//
//
//        // finally, draw ark between two points
//        canvas.drawArc(
//            rectF,
//            startAngle,
//            sweepAngle,
//            true,
//            paint.apply { color = resources.getColor(R.color.main) }
//        )
//
////
//
//        val paintStroke = Paint().apply {
//            isAntiAlias = true
//            color = resources.getColor(R.color.main)
//            style = Paint.Style.STROKE
//            strokeWidth = 10F
//        }
//
//        // draw arc border
//        canvas.drawArc(
//            rectF,
//            startAngle,
//            sweepAngle,
//            true,
//            paintStroke
//        )
//
//        return bitmap
//    }

    override fun onResume() {
        super.onResume()
        running = true
        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if(stepSensor == null) {
            Toast.makeText(context, "No sensor detected on this device", Toast.LENGTH_SHORT).show()

        }
        else {
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)

        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(running) {
            totalSteps = event!!.values[0]
            currentSteps = totalSteps.toInt() - previousTotalSteps.toInt()
            binding.txtCurrentStep.text = "${currentSteps}"
            setProgressTo((currentSteps.toDouble() / 10000 * 100).toInt())

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        saveData()
        Log.d("HomeFragment-previous", previousTotalSteps.toString())
        Log.d("HomeFragment-total", totalSteps.toString())
    }

    fun resetSteps() {
        binding.txtCurrentStep.setOnClickListener {
            // This will give a toast message if the user want to reset the steps
            Toast.makeText(context, "Long tap to reset steps", Toast.LENGTH_SHORT).show()
        }

        binding.txtCurrentStep.setOnLongClickListener {

            previousTotalSteps = totalSteps

            // When the user will click long tap on the screen,
            // the steps will be reset to 0
            binding.txtCurrentStep.text = 0.toString()

            // This will save the data
            saveData()

            true
        }
    }

    private fun saveData() {
        val sharedPreferences = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)

        val editor = sharedPreferences?.edit()
        editor?.putFloat("key1", totalSteps)
        editor?.commit()
    }

    private fun loadData() {

        // In this function we will retrieve data
        val sharedPreferences = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        val savedNumber = sharedPreferences.getFloat("key1", 0f)

        // Log.d is used for debugging purposes
        Log.d("HomeFragment", "$savedNumber")

        previousTotalSteps = savedNumber
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}