package com.projectx.fisioapp.app.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.projectx.fisioapp.R
import com.projectx.fisioapp.app.adapter.SimpleItemRecyclerViewAdapter
import com.projectx.fisioapp.app.router.Router
import com.projectx.fisioapp.app.utils.*
import com.projectx.fisioapp.domain.interactor.ErrorCompletion
import com.projectx.fisioapp.domain.interactor.SuccessCompletion
import com.projectx.fisioapp.domain.interactor.catalog.GetCatalogListIntImpl
import com.projectx.fisioapp.domain.interactor.catalog.GetCatalogListInteractor
import com.projectx.fisioapp.domain.model.Catalogs
import kotlinx.android.synthetic.main.activity_catalog_list.*
import kotlinx.android.synthetic.main.catalog_list.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async


/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [CatalogDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class CatalogListActivity : ParentActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var mTwoPane: Boolean = false
    private var list: Catalogs? = null
    lateinit var type: CatalogType

    private val swipeLayout: SwipeRefreshLayout by lazy  { swipe_container }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog_list)

        if (!checkToken()) {
            Router.navigateFromCatalogListActivitytoLoginActivity(this)
        }

        type = intent.getSerializableExtra(EXTRA_CATALOG_TYPE) as CatalogType

        title = when(type){
            CatalogType.PRODUCT -> getString(R.string.catalog_products_title)
            CatalogType.SERVICE -> getString(R.string.catalog_services_title)
        }

        swipeLayout.setOnRefreshListener {
            refreshData()
        }

        addBottomBar(this)

    }

    override fun onResume() {
        super.onResume()

        catalog_list_add_element.setOnClickListener { _ ->
            Router.navigateFromParentActivityToNewCatalogActivity(this, type)
        }


        if (catalog_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true
        }

        getCatalogList(this, false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RQ_OPERATION && resultCode == RESULT_OK) {
            getCatalogList(this, true)
        } else {
            setupRecyclerView(catalog_list)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.statusbar_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.checkOptionSelected(item)
    }

    private fun refreshData() {
        Toast.makeText(this, "Refreshing data", Toast.LENGTH_LONG).show()

        getCatalogList(this, true)

        Handler().postDelayed({
            Toast.makeText(this, "Datos guardados", Toast.LENGTH_SHORT).show()
            swipeLayout.isRefreshing = false
        }, 4000)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {

        recyclerView.adapter = SimpleItemRecyclerViewAdapter(this, list, mTwoPane)
        // set two columns with the elements
        recyclerView.layoutManager = GridLayoutManager(this, 1)
    }

    private fun getCatalogList(context: Context, forceUpdate: Boolean) {
        async(UI) {

            val getAllCatalogListItems: GetCatalogListInteractor = GetCatalogListIntImpl(context)
            try {
                getAllCatalogListItems.execute(forceUpdate, token,
                        type.name,
                        success = object : SuccessCompletion<Catalogs> {
                            override fun successCompletion(e: Catalogs) {
                                list = e
                                // update list data
                                setupRecyclerView(catalog_list)
                            }
                        }, error = object : ErrorCompletion {
                    override fun errorCompletion(errorMessage: String) {
                        toastIt(baseContext, errorMessage)
                    }
                })
            } catch (e: Exception) {
                toastIt(context, "Error: " + e.localizedMessage )
            }
        }
    }

}
