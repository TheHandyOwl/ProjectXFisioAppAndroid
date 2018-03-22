package com.projectx.fisioapp.domain.model.util

import com.projectx.fisioapp.domain.model.Catalog
import com.projectx.fisioapp.domain.model.Catalogs
import com.projectx.fisioapp.repository.model.CatalogData
import java.io.Serializable

enum class BenefitType(val type: Int) : Serializable {
    SERVICE(1),
    PRODUCT(2)
}

class Mapper {

    internal fun benefitsMapper(list: List<CatalogData>, type: BenefitType): Catalogs {

        val tempList = ArrayList<Catalog>()

        when (type) {
            BenefitType.PRODUCT -> {
                list.forEach {
                    tempList.add(mapBenefit(it, BenefitType.PRODUCT))
                }
            }
            BenefitType.SERVICE -> {
                list.forEach {
                    tempList.add(mapBenefit(it, BenefitType.SERVICE))
                }
            }
        }

        return Catalogs(tempList)
    }

    private fun mapBenefit(catalog: CatalogData, type: BenefitType): Catalog = Catalog(
            catalog.databaseId.toInt(),
            catalog.name,
            catalog.description,
            catalog.professionalId.toInt(),
            catalog.price.toFloat(),
            catalog.isActive,
            type)

}