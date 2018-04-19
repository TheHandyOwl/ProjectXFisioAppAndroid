package com.projectx.fisioapp.app.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projectx.fisioapp.R
import com.projectx.fisioapp.app.utils.CatalogType
import com.projectx.fisioapp.app.utils.EXTRA_CATALOG_TYPE
import com.projectx.fisioapp.domain.model.Catalog
import com.projectx.fisioapp.domain.model.util.BenefitType
import kotlinx.android.synthetic.main.fragment_catalog_detail.*

class CatalogDetailFragment : Fragment() {

    /**
     * The dummy content this fragment is presenting.
     */
    private var mItem: Catalog? = null
    private lateinit var type: CatalogType
    private var catalogItemListener: CatalogItemListener? = null
    private lateinit var root: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        inflater.let {
            root = inflater.inflate(R.layout.fragment_catalog_detail, container, false)
        }

        return root
    }

    override fun onResume() {
        super.onResume()

        activity_catalog_detail_name_text.hint = "name"
        activity_catalog_detail_desc_text.hint = "description"
        activity_catalog_detail_price_text.hint = "20"

        is_active_box.isChecked = false

        arguments?.let {

            if(arguments.containsKey(EXTRA_CATALOG_TYPE)){
                type = arguments.getSerializable(EXTRA_CATALOG_TYPE) as CatalogType
            }

            if(arguments.containsKey(ARG_ITEM)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                mItem = arguments.getSerializable(ARG_ITEM) as Catalog

                mItem?.let {

                    activity_catalog_detail_name_text.setText(it.name)
                    activity_catalog_detail_desc_text.setText(it.description)
                    activity_catalog_detail_price_text.setText(it.price.toString())
                    is_active_box.isChecked = it.isActive
                    activity_catalog_detail_save_bttn.text = getString(R.string.catalog_update)
                }

                activity_catalog_detail_save_bttn.setOnClickListener {
                    Log.d(CatalogDetailFragment::class.java.canonicalName, "Clicked on SAVE")
                    val newItem = Catalog(mItem!!.id,
                            activity_catalog_detail_name_text.text.toString(),
                            activity_catalog_detail_desc_text.text.toString(),
                            mItem!!.professionalId,
                            activity_catalog_detail_price_text.text.toString().toFloat(),
                            is_active_box.isChecked,
                            BenefitType.valueOf(type.name))
                    catalogItemListener?.onSavePressed(root.rootView, newItem)
                }

                activity_catalog_detail_delete_bttn.setOnClickListener {
                    Log.d(CatalogDetailFragment::class.java.canonicalName, "Clicked on DELETE")
                    catalogItemListener?.onDeletePressed(root.rootView, mItem!!.id)
                }

            } else {
                activity_catalog_detail_save_bttn.setOnClickListener {
                    Log.d(CatalogDetailFragment::class.java.canonicalName, "Clicked on SAVE")
                    val newItem = Catalog("",
                            activity_catalog_detail_name_text.text.toString(),
                            activity_catalog_detail_desc_text.text.toString(),
                            "",
                            activity_catalog_detail_price_text.text.toString().toFloat(),
                            is_active_box.isChecked,
                            BenefitType.valueOf(type.name))
                    catalogItemListener?.onSavePressed(root.rootView, newItem)
                }


                activity_catalog_detail_delete_bttn.visibility = View.INVISIBLE
                activity_catalog_detail_delete_bttn.isClickable = false

            }

        }
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM = "item_id"
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        commonOnAttach(context)
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        commonOnAttach(activity)
    }

    private fun commonOnAttach(context: Context?) {
        if (context is CatalogItemListener) {
            catalogItemListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        catalogItemListener = null
    }
}

interface CatalogItemListener {
    fun onSavePressed(view: View, item: Catalog)
    fun onDeletePressed(view: View, id: String)
}
