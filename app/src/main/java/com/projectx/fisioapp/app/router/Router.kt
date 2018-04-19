package com.projectx.fisioapp.app.router

import android.app.Activity
import android.content.Intent
import com.projectx.fisioapp.app.activity.*
import com.projectx.fisioapp.app.fragment.CatalogDetailFragment
import com.projectx.fisioapp.app.utils.CatalogType
import com.projectx.fisioapp.app.utils.EXTRA_CATALOG_TYPE
import com.projectx.fisioapp.domain.model.Appointment
import com.projectx.fisioapp.app.utils.RQ_OPERATION
import com.projectx.fisioapp.domain.model.Catalog


object Router {

    fun navigateFromCalendarActivityToAppointmentDetailActivity(main: CalendarActivity, appointment: Appointment){
        val intent = AppointmentDetailActivity.newInstance(main.baseContext, appointment)
        main.startActivityForResult(intent, RQ_OPERATION)
    }

    fun navigateFromCalendarActivityToLoginActivity(main: CalendarActivity) {
        main.startActivity(Intent(main, LoginActivity::class.java))
    }

    fun navigateFromCatalogListActivitytoLoginActivity(main: CatalogListActivity) {
        main.startActivity(Intent(main, LoginActivity::class.java))
    }

    fun navigateFromParentActivityToDetailCatalogActivity(item: Catalog, mainActivity: CatalogListActivity, type: CatalogType) {
        val intent = Intent(mainActivity, CatalogDetailActivity::class.java)
        intent.putExtra(EXTRA_CATALOG_TYPE, type)
        intent.putExtra(CatalogDetailFragment.ARG_ITEM, item)
        mainActivity.startActivityForResult(intent, RQ_OPERATION)
    }

    fun navigateFromParentActivityToNewCatalogActivity(mainActivity: CatalogListActivity, type: CatalogType) {
        val intent = Intent(mainActivity, CatalogDetailActivity::class.java)
        intent.putExtra(EXTRA_CATALOG_TYPE, type)
        mainActivity.startActivityForResult(intent, RQ_OPERATION)
    }

    // Navigation in tabs
    fun moveToLoginActivity (main: Activity) {
        main.startActivity(Intent(main, LoginActivity::class.java))
    }

    fun moveToAboutMeActivity (main: Activity) {
        main.startActivity(Intent(main, UserDetailActivity::class.java))
    }

    fun moveToAboutUsActivity (main: Activity) {
        main.startActivity(Intent(main, AboutUsActivity::class.java))
    }

    fun moveToCalendarActivity (main: Activity) {
        if(main !is CalendarActivity){
            main.startActivity(Intent(main, CalendarActivity::class.java))
        }

    }

    fun moveToProductsActivity(main: Activity) {
        if(main !is CatalogListActivity || (main).type != CatalogType.PRODUCT){
            moveToDetailActivity(main, CatalogType.PRODUCT)
        }

    }

    fun moveToServicesActivity(main: Activity) {
        if(main !is CatalogListActivity || (main).type != CatalogType.SERVICE){
            moveToDetailActivity(main, CatalogType.SERVICE)
        }

    }

    private fun moveToDetailActivity(mainActivity: Activity, type: CatalogType) {
        val intent = Intent(mainActivity, CatalogListActivity::class.java)
        intent.putExtra(EXTRA_CATALOG_TYPE, type)
        mainActivity.startActivityForResult(intent, RQ_OPERATION)
    }

    /*fun navigateFromAppointmentsActivitytoLoginActivity (main: CalendarActivity) {
        main.startActivity(Intent(main, LoginActivity::class.java))
    }

    fun navigateFromLoginActivitytoAppointmentsActivity (main: LoginActivity) {
        main.startActivity(Intent(main, CalendarActivity::class.java))
    }*/

}
