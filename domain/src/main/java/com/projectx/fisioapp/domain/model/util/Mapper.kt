package com.projectx.fisioapp.domain.model.util

import com.projectx.fisioapp.domain.model.Catalog
import com.projectx.fisioapp.domain.model.Catalogs
import com.projectx.fisioapp.repository.entitymodel.catalog.CatalogData
import com.projectx.fisioapp.repository.entitymodel.catalog.CatalogType
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
            catalog.databaseId,
            catalog.name,
            catalog.description,
            catalog.professionalId,
            catalog.price,
            catalog.isActive,
            type)

    internal fun mapCatalogToBenefit(item: Catalog) : CatalogData = CatalogData(
            item.id,
            item.name,
            item.description,
            item.price,
            item.professionalId,
            item.isActive,
            //"",
            getType(item.type))

    private fun getType(type: BenefitType): CatalogType {
        return when(type){
            BenefitType.SERVICE -> CatalogType.SERVICE
            BenefitType.PRODUCT -> CatalogType.PRODUCT
        }
    }

}
