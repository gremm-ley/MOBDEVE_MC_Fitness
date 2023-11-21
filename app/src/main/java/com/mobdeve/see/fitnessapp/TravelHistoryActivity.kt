package com.mobdeve.see.fitnessapp

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.mobdeve.see.fitnessapp.databinding.ActivityStatsBinding
import com.mobdeve.see.fitnessapp.databinding.ActivityTravelHistoryBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class TravelHistoryActivity : AppCompatActivity(){
    private lateinit var stepLogDao: StepLogDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding : ActivityTravelHistoryBinding = ActivityTravelHistoryBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val userId = intent.getIntExtra("userId", -1) // Retrieve userId from Intent, provide default value if not found

        val appDatabase = AppDatabase.getDatabase(this)
        stepLogDao = appDatabase.stepLogDao()

        lifecycleScope.launch {
            val stepLogs = stepLogDao.getStepLogsForUser(userId)
            Log.d("UserIdMeessage", "" + userId + " " + stepLogs)
            displayStepLogs(stepLogs, viewBinding.tableLayout)
        }
    }

    private fun displayStepLogs(stepLogs: List<StepLogWithGoal>, tableLayout: TableLayout) {

        for (stepLog in stepLogs) {
            val tableRow = TableRow(this)
            val params = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT)
            tableRow.layoutParams = params

            setTableRowStyle(tableRow)

            val stepsTextView = createTextView(stepLog.steps.toString())
            tableRow.addView(stepsTextView)

            val goalTextView = createTextView(stepLog.goal.toString())
            tableRow.addView(goalTextView)

            val dateTextView = createTextView(stepLog.date)
            tableRow.addView(dateTextView)

            tableLayout.addView(tableRow)
        }
    }

    private fun createTextView(text: String): TextView {
        val textView = TextView(this)
        textView.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT)
        textView.text = text
        textView.setPadding(8, 8, 8, 8)
        textView.setTextColor(ContextCompat.getColor(this, R.color.black)) // Adjust text color if needed
        return textView
    }

    private fun setTableRowStyle(row: TableRow) {

        row.setBackgroundResource(R.drawable.roundlayoutalt)
        row.setPadding(0, 20, 0, 0)
        row.layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT
        )
    }
}