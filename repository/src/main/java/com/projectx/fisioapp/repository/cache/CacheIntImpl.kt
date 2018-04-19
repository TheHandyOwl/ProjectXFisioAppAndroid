package com.projectx.fisioapp.repository.cache

import android.content.Context
import com.projectx.fisioapp.repository.BuildConfig
import com.projectx.fisioapp.repository.db.DBHelper
import com.projectx.fisioapp.repository.db.buildHelper
import com.projectx.fisioapp.repository.db.dao.AppointmentDAO
import com.projectx.fisioapp.repository.db.dao.CatalogDAO
import com.projectx.fisioapp.repository.entitymodel.appointments.AppoinmentData
import com.projectx.fisioapp.repository.entitymodel.catalog.CatalogData
import com.projectx.fisioapp.repository.thread.DispatchOnMainThread
import java.lang.ref.WeakReference


class CacheIntImpl(context: Context): CacheInteractor {

    private val context = WeakReference<Context>(context)
    private val dbHelper = cacheDBHelper()

    private fun cacheDBHelper(): DBHelper {
        return buildHelper(context.get()!!, BuildConfig.FISIOAPP_CACHE_DB_NAME, 1)
    }


    /******** users ********/


    /******** catalog (products and services) ********/
    override fun countCatalogItems(): Int {

        val total = CatalogDAO(dbHelper).count()
        dbHelper.close()
        return total

    }

    override fun getAllCatalogItems(type: String, success: (catalogList: List<CatalogData>) -> Unit, error: (errorMessage: String) -> Unit) {

        Thread(Runnable {
            val entityList = CatalogDAO(dbHelper).queryType(type)

            if (entityList.isNotEmpty()) {
                success(entityList)
            } else {
                error("Error getting $type list")
            }
            dbHelper.close()
        }).run()
    }


    override fun saveCatalogItems(type: String, catalogList: List<CatalogData>, success: () -> Unit, error: (errorMessage: String) -> Unit) {
        Thread(Runnable {
            try {
                catalogList.forEach { CatalogDAO(dbHelper).insertOrUpdate(it) }

                DispatchOnMainThread(Runnable {
                    dbHelper.close()
                    success()
                })
            } catch (ex: Exception) {
                DispatchOnMainThread(Runnable {
                    error("Error inserting items: " + ex.message.toString())
                    dbHelper.close()
                })
            }
        }).run()
    }

    override fun insertCatalogItem(item: CatalogData, success: () -> Unit, error: (errorMessage: String) -> Unit) {
        Thread(Runnable {
            try {
                val successInsert = CatalogDAO(dbHelper).insert(item, item.type.name )

                DispatchOnMainThread(Runnable {

                    if(successInsert > 0){
                        dbHelper.close()
                        success()
                    } else {
                        error("Error inserting")
                    }
                    dbHelper.close()
                })
            } catch (ex: Exception) {
                DispatchOnMainThread(Runnable {
                    error("Error inserting item: " + ex.message.toString())
                    dbHelper.close()
                })
            }
        }).run()
    }

    override fun updateCatalogItem(item: CatalogData, success: () -> Unit, error: (errorMessage: String) -> Unit) {
        Thread(Runnable {
            try {
                val successUpdate = CatalogDAO(dbHelper).update(item.databaseId, item)

                DispatchOnMainThread(Runnable {

                    if(successUpdate.isNotEmpty()){
                        dbHelper.close()
                        success()
                    } else {
                        error("Error updating")
                    }
                    dbHelper.close()
                })
            } catch (ex: Exception) {
                DispatchOnMainThread(Runnable {
                    error("Error updating item: " + ex.message.toString())
                    dbHelper.close()
                })
            }
        }).run()
    }

    override fun deleteCatalogItem(id: String, success: () -> Unit, error: (errorMessage: String) -> Unit) {
        Thread(Runnable {
            val successDeleting = CatalogDAO(dbHelper).delete(id)

            DispatchOnMainThread(Runnable {
                if (successDeleting.isNotEmpty()) {
                    success()
                } else {
                    error("Error deleting")
                }
                dbHelper.close()
            })

        }).run()
    }


    override fun deleteAllCatalogItems(success: () -> Unit, error: (errorMessage: String) -> Unit) {
        Thread(Runnable {
            val successDeleting = CatalogDAO(dbHelper).deleteAll()

            DispatchOnMainThread(Runnable {
                if (successDeleting) {
                    success()
                } else {
                    error("Error deleting")
                }
                dbHelper.close()
            })

        }).run()
    }




    /******** appointments ********/

    override fun getAllAppointments(success: (appointmentsList: List<AppoinmentData>) -> Unit, error: (errorMessage: String) -> Unit) {

        Thread(Runnable {
            val entityList = AppointmentDAO(dbHelper).query()

            if (entityList.isNotEmpty()) {
                success(entityList)
            } else {
                error("Error getting appointments from cache")
            }
            dbHelper.close()
        }).run()
    }

    override fun getAppointmentsForDate(date: String, success: (appointmentsList: List<AppoinmentData>) -> Unit, error: (errorMessage: String) -> Unit) {
        Thread(Runnable {

            val entityList = AppointmentDAO(dbHelper).queryDate(date)

            if (entityList.isNotEmpty()) {
                success(entityList)
            } else {
                error("Error getting appointments for date $date from cache")
            }
            dbHelper.close()
        }).run()
    }

    override fun saveAllAppointments(appointmentsList: List<AppoinmentData>, success: () -> Unit, error: (errorMessage: String) -> Unit) {
        Thread(Runnable {
            try {
                appointmentsList.forEach {AppointmentDAO(dbHelper).insertOrUpdate(it)}

                DispatchOnMainThread(Runnable {
                    dbHelper.close()
                    success()
                })
            } catch (ex: Exception) {
                DispatchOnMainThread(Runnable {
                    error("Error inserting appointments into cache. " + ex.message.toString())
                    dbHelper.close()
                })
            }
        }).run()
    }


    override fun deleteAllAppointments(success: () -> Unit, error: (errorMessage: String) -> Unit) {
        Thread(Runnable {
            val successDeleting = AppointmentDAO(dbHelper).deleteAll()

            DispatchOnMainThread(Runnable {
                if (successDeleting) {
                    success()
                } else {
                    error("Error deleting")
                }
                dbHelper.close()
            })
        }).run()
    }


    override fun deleteAppointment(id: String, success: () -> Unit, error: (errorMessage: String) -> Unit) {
        Thread(Runnable {
            val successDeleting = AppointmentDAO(dbHelper).delete(id)

            DispatchOnMainThread(Runnable {
                if (successDeleting.isNotEmpty()) {
                    success()
                } else {
                    error("Error deleting")
                }
                dbHelper.close()
            })
        }).run()
    }

    override fun updateAppointment(id: String, isConfirmed: Boolean, isCancelled: Boolean, success: () -> Unit, error: (errorMessage: String) -> Unit) {

        Thread(Runnable {

            val successUpdating = AppointmentDAO(dbHelper).update(id, isConfirmed, isCancelled)

            DispatchOnMainThread(Runnable {
                if (successUpdating > 0) {
                    success()
                } else {
                    error("Error deleting")
                }
                dbHelper.close()
            })
        }).run()
    }

}


