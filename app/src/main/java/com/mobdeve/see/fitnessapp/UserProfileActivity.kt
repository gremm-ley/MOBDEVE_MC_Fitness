package com.mobdeve.see.fitnessapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mobdeve.see.fitnessapp.databinding.ActivityDashboardBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import androidx.fragment.app.Fragment

class UserProfileActivity : AppCompatActivity() {
    private lateinit var userDao: UserDao
    private lateinit var stepLogDao: StepLogDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding : ActivityDashboardBinding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val userFullName = intent.getStringExtra("userFullName") // Get user's full name
        val userId = intent.getIntExtra("userId", 0)
        viewBinding.welcome.text = "Welcome, $userFullName"

        val appDatabase = AppDatabase.getDatabase(this)
        userDao = appDatabase.userDao()
        stepLogDao = appDatabase.stepLogDao()

        createNewStepLog(userId)

        val stepFragment = StepCounterFragment()
        val statsFragment = StatsFragment()
        val userFragment = UserFragment()
        val historyFragment = HistoryFragment()
        val bundle = Bundle()
        bundle.putInt("userId", userId)

        stepFragment.arguments = bundle
        statsFragment.arguments = bundle
        userFragment.arguments = bundle
        historyFragment.arguments = bundle

        setCurrentFragment(stepFragment)

        viewBinding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.main_menu->setCurrentFragment(stepFragment)
                R.id.stats->setCurrentFragment(statsFragment)
                R.id.user->setCurrentFragment(userFragment)
                R.id.history->setCurrentFragment(historyFragment)
            }
            true
        }

        /*
        viewBinding.btnStepCounter.setOnClickListener {
            val intent = Intent(applicationContext, StepsCounterActivity::class.java)
            intent.putExtra("userId", userId)
            this.startActivity(intent)
        }
        viewBinding.btnSetGoal.setOnClickListener{
            val intent = Intent(applicationContext, SetGoalActivity::class.java)
            intent.putExtra("userId", userId)
            this.startActivity(intent)
        }
        viewBinding.btnStats.setOnClickListener{
            val intent = Intent(applicationContext, StatsActivity::class.java)
            intent.putExtra("userId", userId)
            this.startActivity(intent)
        }
        viewBinding.btnStepHistory.setOnClickListener{
            val intent = Intent(applicationContext, TravelHistoryActivity::class.java)
            intent.putExtra("userId", userId)
            this.startActivity(intent)
        }
        viewBinding.btnDeleteProfile.setOnClickListener{
            val intent = Intent(applicationContext, DeleteProfileActivity::class.java)
            intent.putExtra("userId", userId)
            this.startActivity(intent)
        }
        viewBinding.btnLogout.setOnClickListener {
            finish()
        }
         */
    }

    private fun createNewStepLog(userId: Int) {
        val date = getTodayDate()

        lifecycleScope.launch {
            val stepLog = stepLogDao.getStepLogForUserAndDate(userId, date)
            if (stepLog == null) {
                val newStepLog = StepLog(userId = userId, steps = 0, goal = 5000, date = date)
                Log.d("stepLogMeessage", "Made it here " + newStepLog.toString())
                stepLogDao.insertStepLog(newStepLog)
            }
        }
    }

    private fun getTodayDate(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val calendar = Calendar.getInstance()
        return dateFormat.format(calendar.time)
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
}
