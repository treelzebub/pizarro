package net.treelzebub.pizarro.activity

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.GestureDetectorCompat
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.*
import android.widget.Toast
import butterknife.bindView
import net.treelzebub.kapsule.extensions.TAG
import net.treelzebub.pizarro.R
import net.treelzebub.pizarro.adapter.FileTreeAdapter
import net.treelzebub.pizarro.dialog.NewFolderDialogFragment
import net.treelzebub.pizarro.explorer.entities.FileMetadata
import net.treelzebub.pizarro.listener.FileTreeOnTouchListener
import net.treelzebub.pizarro.presenter.FileTreePresenter
import net.treelzebub.pizarro.presenter.FileTreePresenterImpl
import net.treelzebub.pizarro.view.FileTreeView
import net.treelzebub.pizarro.presenter.PresenterHolder
import kotlin.properties.Delegates

/**
 * Created by Tre Murillo on 3/19/16
 */
class FileTreeActivity : AppCompatActivity(), FileTreeView, NavigationView.OnNavigationItemSelectedListener,
        FileTreeOnTouchListener, OnNewFolderListener {

    private val toolbar: Toolbar            by bindView(R.id.toolbar)
    private val fab: FloatingActionButton   by bindView(R.id.fab)
    private val drawer: DrawerLayout        by bindView(R.id.drawer_layout)
    private val nav: NavigationView         by bindView(R.id.nav_view)
    private val recycler: RecyclerView      by bindView(R.id.recycler)

    private var presenter: FileTreePresenter by Delegates.notNull()

    private val fileTreeAdapter = FileTreeAdapter()
    private val gestureDetector by lazy { GestureDetectorCompat(this, RecyclerGestureListener()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setupView()
        setupRecycler()
    }

    override fun onResume() {
        super.onResume()
        presenter = createPresenter()
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

    override fun onNewFolder(name: String) {
        val result = if (presenter.mkDir(name)) {
            presenter.reload()
            "$name created."
        } else {
            "Can't create folder here."
        }
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
    }

    fun createPresenter(): FileTreePresenter {
        val presenter = PresenterHolder.getPresenter(FileTreePresenter::class.java)
                ?: FileTreePresenterImpl(this)
        presenter.view = this
        return presenter
    }

    private fun setupView() {
        fab.setOnClickListener {
            NewFolderDialogFragment().apply {
                listener = this@FileTreeActivity
                show(supportFragmentManager, TAG)
            }
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
            val view = getViewAtXY(e) ?: return false
            onClick(view)
            return super.onSingleTapConfirmed(e)
        }

        override fun onLongPress(e: MotionEvent) {
            val view = getViewAtXY(e) ?: return
            onLongClick(view)
        }
    }

    private fun getViewAtXY(e: MotionEvent): View? {
        return recycler.findChildViewUnder(e.x, e.y)
    }

    private fun onClick(view: View) {
        val position = recycler.getChildLayoutPosition(view)
        val data = fileTreeAdapter.getItem(position) ?: return
        presenter.changeDirOrOpen(this, data)
    }

    private fun onLongClick(view: View) {
        val position = recycler.getChildLayoutPosition(view)
        val data = fileTreeAdapter.getItem(position) ?: return
        AlertDialog.Builder(this)
                .setIcon(ContextCompat.getDrawable(this, R.drawable.ic_delete))
                .setTitle("Delete file?")
                .setPositiveButton("Yes") {
                    di, which -> presenter.rm(data)
                }
                .setNegativeButton("No") {
                    di, which -> di.dismiss()
                }
                .show()

    }
}

interface OnNewFolderListener {
    fun onNewFolder(name: String)
}
