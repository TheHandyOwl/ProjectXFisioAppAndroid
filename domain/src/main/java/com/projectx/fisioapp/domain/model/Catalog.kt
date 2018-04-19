package com.projectx.fisioapp.domain.model

import com.projectx.fisioapp.domain.model.util.BenefitType
import java.io.Serializable

open class Catalog(val id: String,
                   val name: String,
                   val description: String,
                   val professionalId: String,
                   val price: Float,
                   val isActive: Boolean,
                   val type: BenefitType) : Serializable {
    init {
        Catalogs(ArrayList())
    }
}

/**
 *
 */
class Catalogs(private val catalogs: MutableList<Catalog>) : Aggregate<Catalog> {
    override fun count() = catalogs.size

    override fun all() = catalogs

    override operator fun get(position: Int) = catalogs[position]


    override fun add(element: Catalog) {
        catalogs.add(element)
    }

    override fun delete(position: Int) {
        catalogs.removeAt(position)
    }

    override fun delete(element: Catalog) {
        catalogs.remove(element)
    }


}