package com.projectx.fisioapp.repository.cache

import com.projectx.fisioapp.repository.entitymodel.appointments.AppoinmentData
import com.projectx.fisioapp.repository.entitymodel.catalog.CatalogData


interface CacheInteractor {

    /******** users ********/


    /******** catalog (products and services) ********/
    fun getAllCatalogItems(type: String, success: (catalogList: List<CatalogData>) -> Unit, error: (errorMessage: String) -> Unit)
    fun deleteAllCatalogItems(success: () -> Unit, error: (errorMessage: String) -> Unit)
    fun countCatalogItems(): Int
    fun saveCatalogItems(type: String, catalogList: List<CatalogData>, success: () -> Unit, error: (errorMessage: String) -> Unit)
    fun insertCatalogItem(item: CatalogData, success: () -> Unit, error: (errorMessage: String) -> Unit)
    fun updateCatalogItem(item: CatalogData, success: () -> Unit, error: (errorMessage: String) -> Unit)
    fun deleteCatalogItem(id: String, success: () -> Unit, error: (errorMessage: String) -> Unit)


    /******** appointments ********/
    fun getAllAppointments(success: (appointmentsList: List<AppoinmentData>) -> Unit, error: (errorMessage: String) -> Unit)
    fun getAppointmentsForDate(date: String, success: (appointmentsList: List<AppoinmentData>) -> Unit, error: (errorMessage: String) -> Unit)
    fun saveAllAppointments(appointmentsList: List<AppoinmentData>, success: () -> Unit, error: (errorMessage: String) -> Unit)
    fun deleteAllAppointments(success: () -> Unit, error: (errorMessage: String) -> Unit)
    fun deleteAppointment(id: String, success: () -> Unit, error: (errorMessage: String) -> Unit)
    fun updateAppointment(id: String, isConfirmed: Boolean, isCancelled: Boolean, success: () -> Unit, error: (errorMessage: String) -> Unit)
}