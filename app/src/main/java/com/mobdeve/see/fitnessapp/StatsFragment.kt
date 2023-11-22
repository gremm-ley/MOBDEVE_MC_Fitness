package com.mobdeve.see.fitnessapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.anychart.*
import com.anychart.chart.common.dataentry.*
import com.mobdeve.see.fitnessapp.databinding.FragmentStatsBinding
import kotlinx.coroutines.*
import androidx.fragment.app.Fragment
import android.view.View

class StatsFragment : Fragment() {
    private lateinit var stepLogDao: StepLogDao

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewBinding: FragmentStatsBinding = FragmentStatsBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appDatabase = AppDatabase.getDatabase(requireContext())
        stepLogDao = appDatabase.stepLogDao()

        val userId = arguments?.getInt("userId", 0) ?: 0

        lifecycleScope.launch(Dispatchers.IO) {
            val stepLogs = stepLogDao.getStepLogsForUser(userId)

            val stepData = mutableListOf<DataEntry>()
            val goalData = mutableListOf<DataEntry>()

            // Display the past 15 days or all available logs if less than 15
            val startIndex = maxOf(0, stepLogs.size - 15)
            val endIndex = stepLogs.size

            for (i in startIndex until endIndex) {
                val stepLog = stepLogs[i]
                val date = stepLog.date

                stepData.add(ValueDataEntry(date, stepLog.steps))
                goalData.add(ValueDataEntry(date, stepLog.goal))
            }

            withContext(Dispatchers.Main) {
                val lineChart = AnyChart.line()
                val stepSeries = lineChart.line(stepData)
                val goalSeries = lineChart.line(goalData)

                stepSeries.name("Steps")
                goalSeries.name("Goal")

                val yAxis = lineChart.yAxis(0)
                yAxis.title("Steps, Goal")

                val xAxis = lineChart.xAxis(0)
                xAxis.labels().fontSize(10)
                xAxis.labels().rotation(-45)

                val chartView = view.findViewById<AnyChartView>(R.id.graphView)
                chartView.setChart(lineChart)
            }
        }
    }
}

/*
class StatsFragment : AppCompatActivity(){
    private lateinit var stepLogDao: StepLogDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding : ActivityStatsBinding = ActivityStatsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val appDatabase = AppDatabase.getDatabase(this)
        stepLogDao = appDatabase.stepLogDao()

        val userId = intent.getIntExtra("userId", 0)

        lifecycleScope.launch(Dispatchers.IO) {
            val stepLogs = stepLogDao.getStepLogsForUser(userId)

            val stepData = mutableListOf<DataEntry>()
            val goalData = mutableListOf<DataEntry>()

            // Display the past 15 days or all available logs if less than 15
            val startIndex = maxOf(0, stepLogs.size - 15)
            val endIndex = stepLogs.size

            for (i in startIndex until endIndex) {
                val stepLog = stepLogs[i]
                val date = stepLog.date

                stepData.add(ValueDataEntry(date, stepLog.steps))
                goalData.add(ValueDataEntry(date, stepLog.goal))
            }

            withContext(Dispatchers.Main) {
                val lineChart = AnyChart.line()
                val stepSeries = lineChart.line(stepData)
                val goalSeries = lineChart.line(goalData)

                stepSeries.name("Steps")
                goalSeries.name("Goal")

                val yAxis = lineChart.yAxis(0)
                yAxis.title("Steps, Goal")

                val xAxis = lineChart.xAxis(0)
                xAxis.labels().fontSize(10)
                xAxis.labels().rotation(-45)

                val chartView = findViewById<AnyChartView>(R.id.graphView)
                chartView.setChart(lineChart)
            }
        }
    }
}
 */