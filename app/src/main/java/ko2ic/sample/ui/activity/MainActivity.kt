package ko2ic.sample.ui.activity

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import ko2ic.sample.R
import ko2ic.sample.databinding.ActivityMainBinding
import ko2ic.sample.ui.viewmodel.MainViewModel
import ko2ic.sample.ui.viewmodel.common.TransitionType
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var viewModel: MainViewModel

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        compositeDisposable.add(viewModel.event.subscribe { message ->
            Snackbar.make(fab, message, Snackbar.LENGTH_SHORT).show();
        })

        compositeDisposable.add(viewModel.transitionEvent.subscribe { type ->
            when (type) {
                TransitionType.ItemList -> {
                    val intent = Intent(this, ItemListActivity::class.java)
                    this.startActivity(intent)
                }
            }
            drawer_layout.closeDrawer(GravityCompat.START)
        })


        binding.viewModel = viewModel

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onDestroy() {
        this.compositeDisposable.clear()
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
}
