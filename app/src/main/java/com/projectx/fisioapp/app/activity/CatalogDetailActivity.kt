package com.projectx.fisioapp.app.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.projectx.fisioapp.R
import com.projectx.fisioapp.app.fragment.CatalogDetailFragment
import com.projectx.fisioapp.app.fragment.CatalogItemListener
import com.projectx.fisioapp.app.utils.CatalogType
import com.projectx.fisioapp.app.utils.EXTRA_CATALOG_TYPE
import com.projectx.fisioapp.app.utils.toastIt
import com.projectx.fisioapp.domain.interactor.ErrorCompletion
import com.projectx.fisioapp.domain.interactor.SuccessCompletion
import com.projectx.fisioapp.domain.interactor.catalog.DeleteCatalogIntImpl
import com.projectx.fisioapp.domain.interactor.catalog.DeleteCatalogInteractor
import com.projectx.fisioapp.domain.interactor.catalog.SaveCatalogIntImpl
import com.projectx.fisioapp.domain.interactor.catalog.SaveCatalogInteractor
import com.projectx.fisioapp.domain.model.Catalog
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

/**
 * An activity representing a single Service detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [CatalogListActivity].
 */
class CatalogDetailActivity : ParentActivity(), CatalogItemListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog_detail)

        //Back button
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val arguments = Bundle()

        if(intent.getSerializableExtra(CatalogDetailFragment.ARG_ITEM) != null){
            arguments.putSerializable(CatalogDetailFragment.ARG_ITEM,
                    intent.getSerializableExtra(CatalogDetailFragment.ARG_ITEM))

        }

        val type = intent.getSerializableExtra(EXTRA_CATALOG_TYPE)
        if(type != null){
            arguments.putSerializable(EXTRA_CATALOG_TYPE,
                    type)

            title = when(type as CatalogType){
                CatalogType.SERVICE -> "Service detail"
                CatalogType.PRODUCT -> "Product detail"
            }
        }


        val fragment = CatalogDetailFragment()
        fragment.arguments = arguments
        supportFragmentManager.beginTransaction()
                .replace(R.id.activity_list_detail_fragment, fragment)
                .commit()

    }



    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> {
                    // This ID represents the Home or Up button. In the case of this
                    // activity, the Up button is shown. For
                    // more details, see the Navigation pattern on Android Design:
                    //
                    // http://developer.android.com/design/patterns/navigation.html#up-vs-back

                    finalizeActivity(Activity.RESULT_CANCELED, Intent())
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }


    override fun onSavePressed(view: View, item: Catalog) {
        async(UI) {

            val saveItem: SaveCatalogInteractor = SaveCatalogIntImpl(view.context)
            try {
                saveItem.execute(token,
                        item,
                        success = object : SuccessCompletion<String> {
                            override fun successCompletion(e: String) {
                                toastIt(view.context, e)

                                val intent = Intent()
                                intent.putExtra("result", -1)
                                finalizeActivity(Activity.RESULT_OK, intent)
                            }
                        }, error = object : ErrorCompletion {
                    override fun errorCompletion(errorMessage: String) {
                        toastIt(view.context, errorMessage)
                        finalizeActivity(Activity.RESULT_CANCELED, Intent())
                    }
                })
            } catch (e: Exception) {
                toastIt(view.context, "Error: " + e.localizedMessage )
            }
        }
    }

    override fun onDeletePressed(view: View, id: String) {
        async(UI) {

            val type = intent.getSerializableExtra(EXTRA_CATALOG_TYPE) as CatalogType
            val deleteItem: DeleteCatalogInteractor = DeleteCatalogIntImpl(view.context)
            try {
                deleteItem.execute(token,
                        id,
                        type.name,
                        success = object : SuccessCompletion<String> {
                            override fun successCompletion(e: String) {
                                toastIt(view.context, e)

                                val intent = Intent()
                                intent.putExtra("result", -1)
                                finalizeActivity(Activity.RESULT_OK, intent)
                            }
                        }, error = object : ErrorCompletion {
                            override fun errorCompletion(errorMessage: String) {
                                toastIt(view.context, errorMessage)

                                finalizeActivity(Activity.RESULT_CANCELED, Intent())
                            }
                        })
            } catch (e: Exception) {
                toastIt(view.context, "Error: " + e.localizedMessage )
            }
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finalizeActivity(Activity.RESULT_CANCELED, Intent())
    }

}
