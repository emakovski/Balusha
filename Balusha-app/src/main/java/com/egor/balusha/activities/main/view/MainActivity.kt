package com.egor.balusha.activities.main.view

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.egor.balusha.activities.OwnerInfoAdd
import com.egor.balusha.activities.main.viewmodel.MainActivityViewModel
import com.egor.balusha.activities.main.adapter.NotifAdapter
import com.egor.balusha.databinding.ActivityMainBinding
import com.egor.balusha.dbpets.NotifEntity
import com.egor.balusha.util.BottomNavFragment
import com.egor.balusha.util.DatabaseRepository
import com.egor.balusha.activities.main.repository.NotifRepository
import com.egor.balusha.activities.main.model.NotifModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.util.*

private const val PET_PHOTO = "pet_photo"
private const val PET_NAME = "pet_name"
private const val PET_BIRTH = "pet_birth"
private const val PET_SEX = "pet_sex"
private const val PET_BREED = "pet_breed"

class MainActivity : AppCompatActivity(), NotifAdapter.OnNoteInteractionListener {
    lateinit var binding: ActivityMainBinding

    lateinit var mAdapter: NotifAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        DatabaseRepository.initDatabase(applicationContext)
//       getSharedPreferences("FIRST_RUN_PREF", 0).edit().clear().apply()
        val isFirstRun = getSharedPreferences("FIRST_RUN_PREF", Context.MODE_PRIVATE).getBoolean(
            "isFirstRun",
            true
        )
        if (isFirstRun) {
            //show sign up activity
            startActivity(Intent(this@MainActivity, OwnerInfoAdd::class.java))
        }
        getSharedPreferences("FIRST_RUN_PREF", Context.MODE_PRIVATE).edit()
            .putBoolean("isFirstRun", false).apply()
        setSupportActionBar(bottom_app_bar)
        setClickListeners()
        val prefs: SharedPreferences = getSharedPreferences("pets_info", Context.MODE_PRIVATE)
//        if (prefs.getString(PET_PHOTO,"").isEmpty()) binding.photoOfDog.setImageResource(
//            R.drawable.no_photo
//        )
//        else
        Glide.with(this).load(prefs.getString(PET_PHOTO, "no photo")).into(binding.photoOfDog)
        binding.nameOfDog.text = prefs.getString(PET_NAME, "not found")
        binding.birthOfDog.text = prefs.getString(PET_BIRTH, "not found")
        binding.sexOfDog.text = prefs.getString(PET_SEX, "not found")
        binding.breedOfDog.text = prefs.getString(PET_BREED, "not found")

        setupRecyclerView()
        registerViewModel()
    }

    private fun setClickListeners() {
        binding.mainMenuButton.setOnClickListener {
            val bottomNavDrawerFragment =
                BottomNavFragment()
            bottomNavDrawerFragment.show(supportFragmentManager, bottomNavDrawerFragment.tag)
        }
        binding.fabMain.setOnClickListener {
            openNotifCreationActivity()
        }

    }
    private fun openNotifCreationActivity(notifId: Int = 0) {
        val intent = Intent(this, NewNotifActivity::class.java)
        if (notifId != 0)
            intent.putExtra("notifId", notifId)
        startActivity(intent)
    }

    private fun setupRecyclerView() {
        binding.recyclerMain.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mAdapter = NotifAdapter(this)
        binding.recyclerMain.adapter = mAdapter
    }

    private fun registerViewModel() {
        val viewModel: MainActivityViewModel by lazy {
            ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        }
        viewModel.mNotif.observe(
            this,
            Observer(this::updateDataSet)
        )
    }

    private fun updateDataSet(updatedList: List<NotifModel>?) {
        mAdapter.updateDataSet(updatedList)
    }

    override fun onNotifClicked(notifModel: NotifModel?) {
        openNotifCreationActivity(notifId = notifModel?.Id ?: 0)
    }

    override fun onNotifPinClicked(notifModel: NotifModel?) {
        CoroutineScope(Dispatchers.IO).launch {
            updateNotifWithPin(notifModel)
        }
    }

    private fun updateNotifWithPin(notifModel: NotifModel?) {
        if (notifModel != null) {
            notifModel.isPinned = !notifModel.isPinned
            notifModel.pinnedOn =
                if (notifModel.isPinned) Calendar.getInstance().timeInMillis else null
            val notifEntity = NotifEntity(
                Id = notifModel.Id,
                Title = notifModel.title,
                Body = notifModel.body,
                Color = notifModel.color,
                Reminder = notifModel.reminder,
                IsPinned = notifModel.isPinned,
                PinnedOn = notifModel.pinnedOn
            )

            NotifRepository.insertNotif(note = notifEntity)
        }
    }


    override fun onNotifShareClicked(notifModel: NotifModel?) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, notifModel?.title + "\n" + notifModel?.body)
        startActivity(Intent.createChooser(shareIntent, "Share Notification Via"))
    }

    override fun onNotifDeleteClicked(notifModel: NotifModel?) {
        CoroutineScope(Dispatchers.IO).launch {
            val notif = NotifRepository.getNotifForId(notifModel!!.Id!!.toInt())
            NotifRepository.deleteNotif(notifId = notifModel.Id!!.toInt())

            withContext(Dispatchers.Main) {
                Snackbar.make(binding.root, "Notification Deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        undoNotifDelete(notif!!)
                    }.show()
            }
        }
    }

    private fun undoNotifDelete(notif: NotifEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            NotifRepository.insertNotif(notif)
        }
    }
}
