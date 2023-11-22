package com.mobdeve.see.fitnessapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.mobdeve.see.fitnessapp.databinding.FragmentStatsBinding
import com.mobdeve.see.fitnessapp.databinding.FragmentTravelHistoryBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.fragment.app.Fragment

class HistoryFragment : Fragment() {

    private lateinit var stepLogDao: StepLogDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewBinding: FragmentTravelHistoryBinding =
            FragmentTravelHistoryBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = arguments?.getInt("userId", -1) ?: -1

        val appDatabase = AppDatabase.getDatabase(requireContext())
        stepLogDao = appDatabase.stepLogDao()

        lifecycleScope.launch {
            val stepLogs = stepLogDao.getStepLogsForUser(userId)
            Log.d("UserIdMeessage", "$userId $stepLogs")
            displayStepLogs(stepLogs, view.findViewById(R.id.tableLayout))
        }
    }

    private fun displayStepLogs(stepLogs: List<StepLogWithGoal>, tableLayout: TableLayout) {
        for (stepLog in stepLogs) {
            val tableRow = TableRow(requireContext())
            val params =
                TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT)
            tableRow.layoutParams = params

            setTableRowStyle(tableRow, stepLog.steps, stepLog.goal)

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
        val textView = TextView(requireContext())
        textView.layoutParams =
            TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT)
        textView.text = text
        textView.setPadding(8, 8, 8, 8)
        textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        return textView
    }

    private fun setTableRowStyle(row: TableRow, steps: Int, goal: Int) {
        row.setPadding(0, 20, 0, 0)
        val layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT
        )
        row.layoutParams = layoutParams

        // Check if steps are equal to or more than the goal
        if (steps >= goal) {
            // If achieved or exceeded, set background color to a color of your choice
            row.setBackgroundResource(R.drawable.roundlayout_achieved)
        } else {
            // If not achieved, set background color to the default color
            row.setBackgroundResource(R.drawable.roundlayoutalt)
        }
    }
}