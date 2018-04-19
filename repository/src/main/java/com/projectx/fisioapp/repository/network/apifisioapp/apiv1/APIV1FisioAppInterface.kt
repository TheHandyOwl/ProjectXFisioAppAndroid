package com.projectx.fisioapp.repository.network.apifisioapp.apiv1

import com.projectx.fisioapp.repository.BuildConfig
import com.projectx.fisioapp.repository.entitymodel.responses.UpdateAppointmentResponse
import com.projectx.fisioapp.repository.entitymodel.responses.GetAppointmentsResponse
import com.projectx.fisioapp.repository.entitymodel.responses.*
import retrofit2.Call
import retrofit2.http.*
import java.util.*


internal interface APIV1FisioAppInterface {

    /******** USERS OPERATIONS *********/

    @GET(BuildConfig.FISIOAPP_USER_SERVER_PATH + "/{id}")
    fun doGetUser(@Header("x-access-token") token: String, @Path("id") id: String): Call<GetUserResponse>

    @FormUrlEncoded
    @POST(BuildConfig.FISIOAPP_USERS_AUTHENTICATE_SERVER_PATH)
    fun doGetToken(@Field("email") email: String, @Field("password") password: String): Call<AuthenticateUserResponse>

    @FormUrlEncoded
    @POST(BuildConfig.FISIOAPP_USERS_REGISTER_SERVER_PATH)
    fun doRegisterUser(@Field("name") name: String, @Field("email") email: String, @Field("password") password: String): Call<RegisterUserResponse>

    @FormUrlEncoded
    @PUT(BuildConfig.FISIOAPP_USER_SERVER_PATH + "/{id}")
    fun doUpdateUser(@Header("x-access-token") token: String,
                     @Path("id") id: String,
                     @Field("name") name: String,
                     @Field("lastName") lastName: String,
                     @Field("email") email: String,
                     @Field("isProfessional") isProfessional: Boolean,
                     @Field("fellowshipNumber") fellowshipNumber: String,
                     @Field("gender") gender: String,
                     @Field("address") address: String,
                     @Field("phone") phone: String,
                     @Field("birthDate") birthDate: Date,
                     @Field("nationalId") nationalId: String): Call<UpdateUserResponse>


    /******** SERVICES OPERATIONS *********/

    @GET(BuildConfig.FISIOAPP_SERVICES_SERVER_PATH)
    fun doGetServices(@Header("x-access-token") token: String): Call<GetCatalogResponse>

    @DELETE(BuildConfig.FISIOAPP_SERVICES_SERVER_PATH + "/{id}")
    fun doDeleteService(@Header("x-access-token") token: String, @Path("id") id: String): Call<DeleteCatalogResponse>

    @FormUrlEncoded
    @POST(BuildConfig.FISIOAPP_SERVICES_SERVER_PATH)
    fun doInsertService(@Header("x-access-token") token: String,
                        @Field("name") name: String,
                        @Field("description") description: String,
                        @Field("price") price: Float,
                        @Field("isActive") isActive: Boolean): Call<SaveCatalogResponse>

    @FormUrlEncoded
    @PUT(BuildConfig.FISIOAPP_SERVICES_SERVER_PATH + "/{id}")
    fun doUpdateService(@Header("x-access-token") token: String,
                        @Path("id") id: String,
                        @Field("name") name: String,
                        @Field("description") description: String,
                        @Field("price") price: Float,
                        @Field("isActive") isActive: Boolean): Call<SaveCatalogResponse>


    /******** PRODUCTS OPERATIONS *********/
    @GET(BuildConfig.FISIOAPP_PRODUCTS_SERVER_PATH)
    fun doGetProducts(@Header("x-access-token") token: String): Call<GetCatalogResponse>

    @DELETE(BuildConfig.FISIOAPP_PRODUCTS_SERVER_PATH + "/{id}")
    fun doDeleteProduct(@Header("x-access-token") token: String, @Path("id") id: String): Call<DeleteCatalogResponse>

    @FormUrlEncoded
    @POST(BuildConfig.FISIOAPP_PRODUCTS_SERVER_PATH)
    fun doInsertProduct(@Header("x-access-token") token: String,
                        @Field("name") name: String,
                        @Field("description") description: String,
                        @Field("price") price: Float,
                        @Field("isActive") isActive: Boolean): Call<SaveCatalogResponse>

    @FormUrlEncoded
    @PUT(BuildConfig.FISIOAPP_PRODUCTS_SERVER_PATH + "/{id}")
    fun doUpdateProduct(@Header("x-access-token") token: String,
                        @Path("id") id: String,
                        @Field("name") name: String,
                        @Field("description") description: String,
                        @Field("price") price: Float,
                        @Field("isActive") isActive: Boolean): Call<SaveCatalogResponse>


    /******** APPOINTMENTS OPERATIONS *********/
    @GET(BuildConfig.FISIOAPP_APPOINTMENTS_PROFESSIONAL_SERVER_PATH)
    fun doGetAppointments(@Header("x-access-token") token: String): Call<GetAppointmentsResponse>

    @GET(BuildConfig.FISIOAPP_APPOINTMENTS_PROFESSIONAL_SERVER_PATH)
    fun doGetAppointmentsForDate(@Header("x-access-token") token: String,
                                 @Query("dateFrom") dateFrom: String,
                                 @Query("dateTo") dateTo: String): Call<GetAppointmentsResponse>


    @DELETE(BuildConfig.FISIOAPP_APPOINTMENTS_SERVER_PATH + "/{id}")
    fun doDeleteAppointment(@Header("x-access-token") token: String, @Path("id") id: String): Call<UpdateAppointmentResponse>

    @FormUrlEncoded
    @PUT(BuildConfig.FISIOAPP_APPOINTMENTS_SERVER_PATH + "/{id}")
    fun doUpdateAppointment(@Header("x-access-token") token: String,
                            @Path("id") id: String,
                            @Field("isConfirmed") isConfirmed: Boolean,
                            @Field("isCancelled") isCancelled: Boolean): Call<UpdateAppointmentResponse>


}
