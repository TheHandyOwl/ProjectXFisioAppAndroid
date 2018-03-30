package com.projectx.fisioapp.repository.network.apifisioapp.apiv1

import com.projectx.fisioapp.repository.BuildConfig
import com.projectx.fisioapp.repository.entitymodel.responses.*

import retrofit2.Call
import retrofit2.http.*


internal interface APIV1FisioAppInterface {

    /******** USERS OPERATIONS *********/

    @FormUrlEncoded
    @POST(BuildConfig.FISIOAPP_USERS_AUTHENTICATE_SERVER_PATH)
    fun doGetToken(@Field("email") email: String, @Field("password") password: String): Call<AuthenticateUserResponse>

    @FormUrlEncoded
    @POST(BuildConfig.FISIOAPP_USERS_REGISTER_SERVER_PATH)
    fun doRegisterUser(@Field("name") name: String, @Field("email") email: String, @Field("password") password: String): Call<RegisterUserResponse>


    /******** SERVICES OPERATIONS *********/

    @GET(BuildConfig.FISIOAPP_SERVICES_SERVER_PATH)
    fun doGetServices(@Header("x-access-token") token: String): Call<GetCatalogResponse>

    @DELETE(BuildConfig.FISIOAPP_SERVICES_SERVER_PATH + "/{id}")
    fun doDeleteService(@Header("x-access-token") token: String, @Path("id") id: String): Call<DeleteCatalogResponse>

    @POST(BuildConfig.FISIOAPP_SERVICES_SERVER_PATH)
    fun doInsertService(@Header("x-access-token") token: String,
                        @Field("name") name: String,
                        @Field("description") description: String,
                        @Field("price") price: Float): Call<SaveCatalogResponse>

    @PUT(BuildConfig.FISIOAPP_SERVICES_SERVER_PATH + "/{id}")
    fun doUpdateService(@Header("x-access-token") token: String,
                        @Path("id") id: String,
                        @Field("name") name: String,
                        @Field("description") description: String,
                        @Field("price") price: Float): Call<SaveCatalogResponse>


    /******** PRODUCTS OPERATIONS *********/
    @GET(BuildConfig.FISIOAPP_PRODUCTS_SERVER_PATH)
    fun doGetProducts(@Header("x-access-token") token: String): Call<GetCatalogResponse>
}
