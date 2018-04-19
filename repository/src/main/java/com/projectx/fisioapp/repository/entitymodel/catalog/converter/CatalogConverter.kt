package com.projectx.fisioapp.repository.entitymodel.catalog.converter

import com.projectx.fisioapp.repository.entitymodel.responses.GetCatalogResponse
import com.projectx.fisioapp.repository.entitymodel.catalog.CatalogData
import com.projectx.fisioapp.repository.entitymodel.catalog.CatalogType


fun convertList(response: GetCatalogResponse, type: CatalogType): List<CatalogData> {
    val catalogDataList: MutableList<CatalogData> = arrayListOf()


    response.result.let {

        response.result!!.rows.let {

            val rows = response.result!!.rows
            rows.let {

                rows!!.map {
                    val catalog = CatalogData(
                            it.id!!,
                            it.name!!,
                            it.description!!,
                            it.price!!.toFloat(),
                            // CHANGING PROFESSIONAL X PERSON
                            it.professional!!.id!!,
                            it.isActive!!,
                            //row?.image!!,
                            type
                    )
                    catalogDataList.add(catalog)
                }
            }

        }

    }



    return catalogDataList
}