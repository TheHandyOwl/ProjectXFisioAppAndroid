package com.projectx.fisioapp.repository

import android.content.Context
import com.projectx.fisioapp.repository.cache.CacheIntImpl
import com.projectx.fisioapp.repository.cache.CacheInteractor
import com.projectx.fisioapp.repository.entitymodel.appointments.AppoinmentData
import com.projectx.fisioapp.repository.entitymodel.catalog.CatalogData
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.authenticateuser.AuthenticateUserIntImpl
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.authenticateuser.AuthenticateUserInteractor
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.deleteAppointment.DeleteAppointmentIntImpl
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.deleteAppointment.DeleteAppointmentInteractor
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.getAppointments.GetAppointmentsIntImpl
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.getAppointments.GetAppointmentsInteractor
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.getservice.*
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.registeruser.RegisterUserIntImpl
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.registeruser.RegisterUserInteractor
import retrofit2.Response.success
import java.lang.ref.WeakReference


class RepositoryIntImpl(val context: Context) : RepositoryInteractor {



    private val weakContext = WeakReference<Context>(context)
    private val cache: CacheInteractor = CacheIntImpl(weakContext.get()!!)
    private val authenticateUser: AuthenticateUserInteractor = AuthenticateUserIntImpl()
    private val registerUser: RegisterUserInteractor = RegisterUserIntImpl()
    private val getAllServices: GetServicesInteractor = GetServicesIntImpl()
    private val deleteService: DeleteServiceInteractor = DeleteServiceIntImpl()
    private val getAllProducts: GetProductsInteractor = GetProductsIntImpl()
    private val getAllAppointments: GetAppointmentsInteractor = GetAppointmentsIntImpl()
    private val deleteAppointment: DeleteAppointmentInteractor = DeleteAppointmentIntImpl()

    /******** users ********/
    override fun authenticateUser(email: String, password: String, success: (token: String) -> Unit, error: (errorMessage: String) -> Unit) {

        // perform network request
        authenticateUser.execute(email, password,
                success = {
                    success(it)
                }, error = {
                    error(it)
                }
        )
    }

    override fun registerUser(name: String, email: String, password: String, success: (ok: Boolean) -> Unit, error: (errorMessage: String) -> Unit) {

        // perform network request
        registerUser.execute(name, email, password,
                success = {
                    success(it)
                }, error = {
                    error(it)
                }
        )
    }


    /******** catalog (products and services) ********/
    override fun countCatalogItems(): Int {
        return cache.countCatalogItems()
    }

    override fun getAllCatalogItems(token: String, type: String, success: (catalogList: List<CatalogData>) -> Unit, error: (errorMessage: String) -> Unit) {

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
        cache.saveAllCatalogItems(type, list, success = {
            success(list)
        }, error = {
            error("Something happened on the way to heaven!")
        })
    }


    override fun deleteAllCatalogItems(success: () -> Unit, error: (errorMessage: String) -> Unit) = cache.deleteAllCatalogItems(success, error)


    override fun deleteService(token: String, id: String, success: (successMessage: String) -> Unit, error: (errorMessage: String) -> Unit){
        deleteService.execute(token, id,
                success = {
                    deleteServiceFromCache(id)
                    success(it)

                }, error = {
                    error(it)
                })
    }



    private fun deleteServiceFromCache(id: String) {
        cache.deleteService(id,
                success = {
                    // if there are entities in the cache, return them
                    success("Service $id removed successfully")
                }, error = {
            // if no catalog in cache --> network
            error("Error deleting service: $id")
            }
        )
    }


    /******** Appointments ********/

    override fun getAllAppointments(token: String, success: (appointmentsList: List<AppoinmentData>) -> Unit, error: (errorMessage: String) -> Unit) {
        cache.getAllAppointments(success = {
                success(it)
        }, error = {
            populateCacheWithAppointments(token, success, error)
        })
    }


    override fun deleteAppointment(token: String, id: String, success: (successMessage: String) -> Unit, error: (errorMessage: String) -> Unit) {
        deleteAppointment.execute(token, id, success = {
            deleteAppointmentFromCache(id)
            if (it == true) {
                success("Appointment $id removed successfully")
            }
        }, error = {
            error(it)
        })
    }


    private fun populateCacheWithAppointments(token: String, success: (appointmentsList: List<AppoinmentData>) -> Unit, error: (errorMessage: String) -> Unit){
        getAllAppointments.execute(token,
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
