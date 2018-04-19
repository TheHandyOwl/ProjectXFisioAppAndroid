package com.projectx.fisioapp.repository

//import com.projectx.fisioapp.repository.cache.CacheIntFakeImpl


/*class RepositoryIntFakeImpl(val context: Context): RepositoryInteractor {
    override fun deleteService(token: String, id: String, success: (successMessage: String) -> Unit, error: (errorMessage: String) -> Unit) {
=======
class RepositoryIntFakeImpl(val context: Context): RepositoryInteractor {
>>>>>>> services_and_products_gema

    private val weakContext = WeakReference<Context>(context)
    private val cache: CacheInteractor = CacheIntFakeImpl(weakContext.get() !!)


    /******** users ********/
    override fun authenticateUser(email: String, password: String, success: (user: UserData, token: String) -> Unit, error: (errorMessage: String) -> Unit) {
        //var allOk = false
        var allOk = true

        // connect to the repository

        // check response from repository
        if (allOk) {
            val user = createFakeUser()
            val token = createFakeToken()
            success(user, token)
        } else {
            error("Error while authenticating user")
        }
    }

    override fun getUser(token: String, id: String, success: (user: UserData) -> Unit, error: (errorMessage: String) -> Unit) {
        //var allOk = false
        var allOk = true

        // connect to the repository

        // check response from repository
        if (allOk) {
            val user = createFakeUser()
            success(user)
        } else {
            error("Error while authenticating user")
        }
    }

    override fun registerUser(name: String, email: String, password: String, success: (ok: Boolean, msg: String) -> Unit, error: (errorMessage: String) -> Unit) {
        //var allOk = false
        var allOk = true

        // connect to the repository

        // check response from repository
        if (allOk) {
            var msg = "Fake ok"
            var ok = true
            success(ok, msg)
        } else {
            error("Error while getting registering user")
        }
    }

    override fun updateUser(token: String, user: UserData, success: (ok: Boolean, user: UserData) -> Unit, error: (errorMessage: String) -> Unit) {
        //var allOk = false
        var allOk = true

        // connect to the repository

        // check response from repository
        if (allOk) {
            val user = createFakeUser()
            success(allOk, user)
        } else {
            error("Error while authenticating user")
        }
    }

    fun createFakeUser(): UserData {
        val user = UserData(
                "33",
                "name",
                "lastName",
                "email",
                true,
                "fellowshipNumber",
                "gender",
                "address",
                "phone",
                Date(),
                "nationalId",
                "registrationDate",
                "lastLoginDate"
        )
        return user
    }

    fun createFakeToken(): String {
        val token = "FakeToken.1234567890"
        return token
    }

    /******** catalog (products and services) ********/
    override fun countCatalogItems(): Int = 10

    override fun getCatalogItems(forceUpdate: Boolean, token: String, type: String, success: (catalogList: List<CatalogData>) -> Unit, error: (errorMessage: String) -> Unit) {

        cache.getAllCatalogItems(type,
                success = {
                    // if there are entities in the cache, return them
                    success(it)
                }, error = {
            // if no catalog in cache --> network
            populateCache(type, success, error)
        })

    }

    private fun populateCache(type: String, success: (catalogList: List<CatalogData>) -> Unit, error: (errorMessage: String) -> Unit) {
        // perform network request
    }

    override fun deleteAllCatalogItems(success: () -> Unit, error: (errorMessage: String) -> Unit) = cache.deleteAllCatalogItems(success, error)

    override fun saveCatalogData(token: String, item: CatalogData, success: (successMessage: String) -> Unit, error: (errorMessage: String) -> Unit) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteCatalogData(token: String, id: String, type: String, success: (successMessage: String) -> Unit, error: (errorMessage: String) -> Unit) {


    }
}
}*/