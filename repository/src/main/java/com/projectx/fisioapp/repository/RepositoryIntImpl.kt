package com.projectx.fisioapp.repository

import android.content.Context
import com.projectx.fisioapp.repository.cache.CacheIntImpl
import com.projectx.fisioapp.repository.cache.CacheInteractor
import com.projectx.fisioapp.repository.entitymodel.appointments.AppoinmentData
import com.projectx.fisioapp.repository.entitymodel.catalog.CatalogData
import com.projectx.fisioapp.repository.entitymodel.catalog.CatalogType
import com.projectx.fisioapp.repository.entitymodel.user.UserData
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.appointments.deleteAppointment.DeleteAppointmentIntImpl
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.appointments.deleteAppointment.DeleteAppointmentInteractor
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.appointments.getAppointments.GetAppointmentsForDateIntImpl
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.appointments.getAppointments.GetAppointmentsForDateInteractor
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.appointments.getAppointments.GetAppointmentsIntImpl
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.appointments.getAppointments.GetAppointmentsInteractor
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.appointments.updateAppointment.UpdateAppointmentIntImpl
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.appointments.updateAppointment.UpdateAppointmentInteractor
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.products.delete.DeleteProductIntImpl
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.products.delete.DeleteProductInteractor
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.products.get.GetProductsIntImpl
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.products.get.GetProductsInteractor
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.products.insert.InsertProductIntImpl
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.products.insert.InsertProductInteractor
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.products.update.UpdateProductIntImpl
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.products.update.UpdateProductInteractor
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.services.delete.DeleteServiceIntImpl
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.services.delete.DeleteServiceInteractor
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.services.get.GetServicesIntImpl
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.services.get.GetServicesInteractor
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.services.insert.InsertServiceIntImpl
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.services.insert.InsertServiceInteractor
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.services.update.UpdateServiceIntImpl
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.services.update.UpdateServiceInteractor
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.user.authenticateuser.AuthenticateUserIntImpl
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.user.authenticateuser.AuthenticateUserInteractor
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.user.getuser.GetUserIntImpl
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.user.getuser.GetUserInteractor
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.user.registeruser.RegisterUserIntImpl
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.user.registeruser.RegisterUserInteractor
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.user.update.UpdateUserIntImpl
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.user.update.UpdateUserInteractor
import retrofit2.Response.success
import java.lang.ref.WeakReference


class RepositoryIntImpl(val context: Context) : RepositoryInteractor {

    private val weakContext = WeakReference<Context>(context)
    private val cache: CacheInteractor = CacheIntImpl(weakContext.get()!!)

    // ***** Users *****
    private val authenticateUser: AuthenticateUserInteractor = AuthenticateUserIntImpl()
    private val getUser: GetUserInteractor = GetUserIntImpl()
    private val registerUser: RegisterUserInteractor = RegisterUserIntImpl()
    private val updateUser: UpdateUserInteractor = UpdateUserIntImpl()

    // ***** Appointments *****
    private val getAllAppointments: GetAppointmentsInteractor = GetAppointmentsIntImpl()
    private val getAppointmentsForDate: GetAppointmentsForDateInteractor = GetAppointmentsForDateIntImpl()
    private val deleteAppointment: DeleteAppointmentInteractor = DeleteAppointmentIntImpl()
    private val updateAppointment: UpdateAppointmentInteractor = UpdateAppointmentIntImpl()

    // ***** Products *****
    private val getAllProducts: GetProductsInteractor = GetProductsIntImpl()
    private val insertProduct: InsertProductInteractor = InsertProductIntImpl()
    private val deleteProduct: DeleteProductInteractor = DeleteProductIntImpl()
    private val updateProduct: UpdateProductInteractor = UpdateProductIntImpl()

    // ***** Services *****
    private val getAllServices: GetServicesInteractor = GetServicesIntImpl()
    private val insertService: InsertServiceInteractor = InsertServiceIntImpl()
    private val deleteService: DeleteServiceInteractor = DeleteServiceIntImpl()
    private val updateService: UpdateServiceInteractor = UpdateServiceIntImpl()


    /******** users ********/
    override fun authenticateUser(email: String, password: String, success: (user: UserData, token: String) -> Unit, error: (errorMessage: String) -> Unit) {

        // perform network request
        authenticateUser.execute(email, password,
                success = { user: UserData, token: String ->
                    success(user, token)
                }, error = {
                    error(it)
                }
        )

    }

    override fun getUser(token: String, id: String, success: (user: UserData) -> Unit, error: (errorMessage: String) -> Unit) {

        // perform network request
        getUser.execute(token, id,
                success = {
                    success(it)
                }, error = {
                    error(it)
                }
        )
    }

    override fun registerUser(name: String, email: String, password: String, success: (ok: Boolean, msg: String) -> Unit, error: (errorMessage: String) -> Unit) {

        // perform network request
        registerUser.execute(name, email, password,
                success = { ok: Boolean, msg: String ->
                    success(ok, msg)
                }, error = {
                    error(it)
                }
        )
    }

    override fun updateUser(token: String, user: UserData, success: (ok: Boolean, user: UserData) -> Unit, error: (errorMessage: String) -> Unit) {

        // perform network request
        updateUser.execute(token, user,
                success = { ok: Boolean, userData: UserData ->
                    success(ok, userData)
                }, error = {
                    error(it)
                }
        )
    }


    /******** catalog (products and services) ********/
    override fun countCatalogItems(): Int {
        return cache.countCatalogItems()
    }


    override fun getCatalogItems(forceUpdate: Boolean, token: String, type: String, success: (catalogList: List<CatalogData>) -> Unit, error: (errorMessage: String) -> Unit) {

        if(forceUpdate){
            populateCache(token, type, success, error)
        } else {
            cache.getAllCatalogItems(type,
                    success = {
                        // if there are entities in the cache, return them
                        success(it)
                    }, error = {
                // if no catalog in cache --> network
                populateCache(token, type, success, error)
            }
            )
        }

    }

    private fun populateCache(token: String, type: String, success: (catalogList: List<CatalogData>) -> Unit, error: (errorMessage: String) -> Unit) {

        when(type){
            "SERVICE" -> {
                // perform network request
                getAllServices.execute(token,
                        success = {
                            saveCatalog(it, type)
                            success(it)

                        }, error = {
                            error(it)
                        }
                )
            }
            "PRODUCT" -> {
                // perform network request
                getAllProducts.execute(token,
                        success = {
                            saveCatalog(it, type)
                            success(it)
                        }, error = {
                            error(it)
                        }
                )
            }
        }
    }


    private fun saveCatalog(list: List<CatalogData>, type: String) {
        cache.saveCatalogItems(type, list, success = {
            success(list)
        }, error = {
            error("Something happened on the way to heaven!")
        })
    }


    override fun saveCatalogData(token: String, item: CatalogData, success: (successMessage: String) -> Unit, error: (errorMessage: String) -> Unit) {

        if (item.databaseId != ""){
            //update option

            when(item.type) {
                CatalogType.SERVICE -> {

                    updateService.execute(token,
                            item,
                            success = {
                                updateCatalogInCache(item)
                                success(it)

                            }, error = {
                        error(it)
                    })
                }
                CatalogType.PRODUCT -> {
                    updateProduct.execute(token,
                            item,
                            success = {
                                updateCatalogInCache(item)
                                success(it)

                            }, error = {
                        error(it)
                    })
                }
            }

        } else {
            // insert option

            when(item.type) {
                CatalogType.SERVICE -> {
                    insertService.execute(token,
                            item,
                            success = {
                                insertCatalogInCache(it, success)
                                //success(it)

                            }, error = {
                        error(it)
                    })
                }
                CatalogType.PRODUCT -> {
                    insertProduct.execute(token,
                            item,
                            success = {
                                insertCatalogInCache(it, success)
                                //success(it)

                            }, error = {
                        error(it)
                    })
                }
            }

        }

    }

    private fun insertCatalogInCache(item: CatalogData, success: (successMessage: String) -> Unit) {
        cache.insertCatalogItem(item,
                success = {
                    success("Item ${item.name} inserted successfully")
                }, error = {
                    // if no catalog in cache --> network
                    error("Error inserting item: ${item.name}")
                })

    }

    private fun updateCatalogInCache(item: CatalogData) {
        cache.updateCatalogItem(item,
                success = {
                    success("Item ${item.name} updated successfully")
                }, error = {
            // if no catalog in cache --> network
            error("Error updating item: ${item.name}")
        })
    }

    override fun deleteCatalogData(token: String, id: String, type: String, success: (successMessage: String) -> Unit, error: (errorMessage: String) -> Unit){

        when(type) {
            "SERVICE" -> {
                deleteService.execute(token, id,
                        success = {
                            deleteCatalogItemFromCache(id)
                            success(it)

                        }, error = {
                    error(it)
                })
            }
            "PRODUCT" -> {
                deleteProduct.execute(token, id,
                        success = {
                            deleteCatalogItemFromCache(id)
                            success(it)

                        }, error = {
                    error(it)
                })
            }
        }
    }

    private fun deleteCatalogItemFromCache(id: String) {
        cache.deleteCatalogItem(id,
                success = {
                    success("Service $id removed successfully")
                }, error = {
            // if no catalog in cache --> network
            error("Error deleting service: $id")
        })
    }

    override fun deleteAllCatalogItems(success: () -> Unit, error: (errorMessage: String) -> Unit) = cache.deleteAllCatalogItems(success, error)


    /******** Appointments ********/

    override fun getAllAppointments(token: String, success: (appointmentsList: List<AppoinmentData>) -> Unit, error: (errorMessage: String) -> Unit) {
        cache.getAllAppointments(success = {
                success(it)
        }, error = {
            populateCacheWithAppointments(token, success, error)
        })
    }

    override fun getAppointmentsForDate(token: String, date: String, success: (appointmentsList: List<AppoinmentData>) -> Unit, error: (errorMessage: String) -> Unit) {
        cache.getAppointmentsForDate(date, success = {
            success(it)
        }, error = {
            populateCacheWithAppointmentsForDate(token, date, success, error)
        })
    }


    override fun deleteAppointment(token: String, id: String, success: (successMessage: String) -> Unit, error: (errorMessage: String) -> Unit) {
        deleteAppointment.execute(token, id, success = {
            deleteAppointmentFromCache(id)
            if (it) {
                success("Appointment $id removed successfully")
            }
        }, error = {
            error(it)
        })
    }

    override fun updateAppointment(token: String, id: String, isConfirmed: Boolean, isCancelled: Boolean, success: (successMessage: String) -> Unit, error: (errorMessage: String) -> Unit) {
        updateAppointment.execute(token,
                id,
                isConfirmed,
                isCancelled,
                success = {
                    updateAppointmentInCache(id, isConfirmed, isCancelled, success, error)
                    //success(it)

                }, error = {
                    error(it)
                })
    }



    private fun updateAppointmentInCache(id: String, isConfirmed: Boolean, isCancelled: Boolean, success: (successMessage: String) -> Unit, error: (errorMessage: String) -> Unit) {
        cache.updateAppointment(id, isConfirmed, isCancelled,
                success = {
                    success("Appointment $id} updated successfully")
                }, error = {
                    kotlin.error("Error updating item: $id")
                })
    }


    /******** Appointments Utils ********/

    private fun populateCacheWithAppointments(token: String, success: (appointmentsList: List<AppoinmentData>) -> Unit, error: (errorMessage: String) -> Unit){
        getAllAppointments.execute(token,
                success = {
                    saveAppointments(it)
                    success(it)
                }, error = {
            error(it)
        })
    }


    private fun populateCacheWithAppointmentsForDate(token: String, date: String, success: (appointmentsList: List<AppoinmentData>) -> Unit, error: (errorMessage: String) -> Unit) {
        getAppointmentsForDate.execute(token, date,
                success = {
                    saveAppointments(it)
                    success(it)

        }, error = {
            error(it)
        })
    }


    private fun saveAppointments(list: List<AppoinmentData>) {
        cache.saveAllAppointments(list, success = {
            success(list)
        }, error = {
            error("Something happened on the way to heaven!")
        })
    }


    private fun deleteAppointmentFromCache(id: String) {
        cache.deleteAppointment(id, success = {
            success("Appointment $id removed successfully")
        }, error = {
            error("Error deleting appointment: $id")
        })
    }
}
