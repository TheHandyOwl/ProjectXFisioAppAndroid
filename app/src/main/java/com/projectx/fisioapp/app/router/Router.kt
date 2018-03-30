package com.projectx.fisioapp.app.router

import android.content.Intent
import com.projectx.fisioapp.app.activity.*
import com.projectx.fisioapp.app.utils.CatalogType
import com.projectx.fisioapp.app.utils.EXTRA_CATALOG_TYPE


class Router {

    fun navigateFromAppointmentsActivitytoLoginActivity (main: AppointmentsActivity) {
        main.startActivity(Intent(main, LoginActivity::class.java))
    }

    fun navigateFromLoginActivitytoAppointmentsActivity (main: LoginActivity) {
        main.startActivity(Intent(main, AppointmentsActivity::class.java))
    }

    fun navigateFromLoginActivitytoBlankActivity (main: LoginActivity) {
        main.startActivity(Intent(main, BlankActivity::class.java))
    }

    fun navigateFromCatalogListActivitytoLoginActivity(main: CatalogListActivity) {
        main.startActivity(Intent(main, LoginActivity::class.java))
    }

    fun navigateFromParentActivityToServicesActivity(mainActivity: CatalogParentActivity) {
        navigateToDetailActivity(mainActivity, CatalogType.SERVICE)
    }

    fun navigateFromParentActivityToProductsActivity(mainActivity: CatalogParentActivity) {
        navigateToDetailActivity(mainActivity, CatalogType.PRODUCT)
    }

    private fun navigateToDetailActivity(mainActivity: CatalogParentActivity, type: CatalogType) {
        val intent = Intent(mainActivity, CatalogListActivity::class.java)
        intent.putExtra(EXTRA_CATALOG_TYPE, type)
        mainActivity.startActivity(intent)
    }

    fun navigateFromParentActivityToNewCatalogActivity(mainActivity: CatalogListActivity, type: CatalogType) {
        val intent = Intent(mainActivity, CatalogDetailActivity::class.java)
        intent.putExtra(EXTRA_CATALOG_TYPE, type)
        mainActivity.startActivity(intent)
    }
}
