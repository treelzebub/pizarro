package net.treelzebub.pizarro.activity

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import butterknife.bindView
import net.treelzebub.pizarro.R
import net.treelzebub.pizarro.adapter.FileTreeAdapter

/**
 * Created by Tre Murillo on 3/19/16
 */
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val toolbar: Toolbar            by bindView(R.id.toolbar)
    private val fab: FloatingActionButton   by bindView(R.id.fab)
    private val drawer: DrawerLayout        by bindView(R.id.drawer_layout)
    private val nav: NavigationView         by bindView(R.id.nav_view)
    private val recycler: RecyclerView      by bindView(R.id.recycler)

    private val fileTreeAdapter = FileTreeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setupView()
        setupRecycler()
    }

    override fun onResume() {
        super.onResume()
        fileTreeAdapter.refresh()
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_camera -> {
            }
            R.id.nav_gallery -> {
            }
            R.id.nav_slideshow -> {
            }
            R.id.nav_manage -> {
            }
            R.id.nav_share -> {
            }
            R.id.nav_send -> {
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setupView() {
        fab.setOnClickListener {
            Snackbar.make(it, "TODO!", Snackbar.LENGTH_LONG).setAction("Action", null).show()
        }
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        nav.setNavigationItemSelectedListener(this)
    }

    private fun setupRecycler() {
        recycler.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = fileTreeAdapter
        }
    }
}
