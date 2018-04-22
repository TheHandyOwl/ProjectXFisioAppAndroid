package com.projectx.fisioapp.app.adapter

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.projectx.fisioapp.R
import com.projectx.fisioapp.app.activity.CatalogListActivity
import com.projectx.fisioapp.app.fragment.CatalogDetailFragment
import com.projectx.fisioapp.app.router.Router
import com.projectx.fisioapp.app.utils.EXTRA_CATALOG_TYPE
import com.projectx.fisioapp.domain.model.Catalog
import com.projectx.fisioapp.domain.model.Catalogs
import kotlinx.android.synthetic.main.catalog_list_content.view.*

class SimpleItemRecyclerViewAdapter(private val mParentActivity: CatalogListActivity,
                                    private val mValues: Catalogs?,
                                    private val mTwoPane: Boolean) :
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Catalog
            if (mTwoPane) {
                val fragment = CatalogDetailFragment().apply {
                    arguments = Bundle()
                    arguments.putSerializable(CatalogDetailFragment.ARG_ITEM, item)
                    arguments.putSerializable(EXTRA_CATALOG_TYPE, mParentActivity.type)
                }
                mParentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.catalog_detail_container, fragment)
                        .commit()
            } else {
                Router.navigateFromParentActivityToDetailCatalogActivity(item, mParentActivity, mParentActivity.type)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues!![position]
        holder.mIdView.text = item.name
        holder.mContentView.text = item.price.toString() + " €"

        with(holder.itemView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.catalog_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = mValues?.count() ?: 0

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.catalog_list_element_name
        val mContentView: TextView = mView.catalog_list_element_price
    }

}