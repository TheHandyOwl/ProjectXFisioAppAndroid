package com.projectx.fisioapp.repository.cache


/*class CacheIntFakeImpl(context: Context) : CacheInteractor {
    override fun deleteService(id: String, success: () -> Unit, error: (errorMessage: String) -> Unit) {
class CacheIntFakeImpl(context: Context) : CacheInteractor {

    override fun deleteCatalogItem(id: String, success: () -> Unit, error: (errorMessage: String) -> Unit) {

    }

    private val context = WeakReference<Context>(context)

    /******** users ********/


    /******** catalog (products and services) ********/
    override fun countCatalogItems() = 10

    override fun getAllCatalogItems(type: String, success: (catalogList: List<CatalogData>) -> Unit, error: (errorMessage: String) -> Unit) {


        var listData = ArrayList<CatalogData>()

        for (i in 1..5) {
            listData.add(createDummyItem(i, "PRODUCT"))
        }

        for (i in 1..5) {
            listData.add(createDummyItem(i, "SERVICE"))
        }

        success(listData)

    }

    private fun createDummyItem(position: Int, type: String): CatalogData {

        return CatalogData(position.toString(),
                "Item " + position.toString(),
                "Description for item " + position.toString(),
                position.toFloat(),
                position.toString(),
                true,
               // "http://myrepo/" + type + ".com",
                CatalogType.SERVICE
        )
    }

    override fun saveCatalogItems(type: String, catalogList: List<CatalogData>, success: () -> Unit, error: (errorMessage: String) -> Unit) {
        success()
    }

    override fun deleteAllCatalogItems(success: () -> Unit, error: (errorMessage: String) -> Unit) {
        success()
    }

}
    override fun insertCatalogItem(item: CatalogData, success: () -> Unit, error: (errorMessage: String) -> Unit) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateCatalogItem(item: CatalogData, success: () -> Unit, error: (errorMessage: String) -> Unit) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}*/
