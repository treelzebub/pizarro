package net.treelzebub.pizarro.activity

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GestureDetectorCompat
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.*
import butterknife.bindView
import net.treelzebub.pizarro.R
import net.treelzebub.pizarro.adapter.FileTreeAdapter
import net.treelzebub.pizarro.explorer.entities.FileMetadata
import net.treelzebub.pizarro.listener.FileTreeOnTouchListener
import net.treelzebub.pizarro.presenter.FileTreePresenter
import net.treelzebub.pizarro.presenter.FileTreePresenterImpl
import net.treelzebub.pizarro.presenter.FileTreeView
import net.treelzebub.pizarro.presenter.PresenterHolder

/**
 * Created by Tre Murillo on 3/19/16
 */
class FileTreeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
        FileTreeOnTouchListener, FileTreeView {

    private val toolbar: Toolbar            by bindView(R.id.toolbar)
    private val fab: FloatingActionButton   by bindView(R.id.fab)
    private val drawer: DrawerLayout        by bindView(R.id.drawer_layout)
    private val nav: NavigationView         by bindView(R.id.nav_view)
    private val recycler: RecyclerView      by bindView(R.id.recycler)

    private val presenter: FileTreePresenter by lazy { createPresenter() }

    private val fileTreeAdapter = FileTreeAdapter()
    private val gestureDetector by lazy { GestureDetectorCompat(this, RecyclerGestureListener()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setupView()
        setupRecycler()
        presenter.create()
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else if (presenter.canGoBack()) {
            presenter.onBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.file_tree_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_back -> {
                presenter.onBack()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(bundle: Bundle) {
        presenter.view = null
        PresenterHolder.putPresenter(FileTreePresenter::class.java, presenter)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            PresenterHolder.remove(FileTreePresenter::class.java)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            //TODO
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        return false
    }

    override fun setFileTree(treeItems: List<FileMetadata>?) {
        treeItems ?: return
        fileTreeAdapter.treeItems = treeItems
    }

    fun createPresenter(): FileTreePresenter {
        val presenter = PresenterHolder.getPresenter(FileTreePresenter::class.java)
                ?: FileTreePresenterImpl(this)
        presenter.view = this
        return presenter
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
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(this@FileTreeActivity)
            addOnItemTouchListener(this@FileTreeActivity)
            adapter = fileTreeAdapter
        }
    }

    private inner class RecyclerGestureListener: GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            val view = recycler.findChildViewUnder(e.x, e.y) ?: return false
            onClick(view)
            return super.onSingleTapConfirmed(e)
        }
    }

    private fun onClick(view: View) {
        val position = recycler.getChildLayoutPosition(view)
        val data = fileTreeAdapter.getItem(position) ?: return
        presenter.changeDirOrOpen(this, data)
    }
}
