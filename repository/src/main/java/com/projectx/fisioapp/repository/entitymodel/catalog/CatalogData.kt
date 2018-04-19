package com.projectx.fisioapp.repository.entitymodel.catalog

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CatalogData(
        @SerializedName("_id") val databaseId: String,
        @SerializedName("name")val name: String,
        @SerializedName("description")val description: String,
        @SerializedName("price")val price: Float,
        @SerializedName("professional")val professionalId: String,
        @SerializedName("isActive")val isActive: Boolean,
      //  @SerializedName("image")val image: String,
        var type: CatalogType
)


enum class CatalogType : Serializable {
    SERVICE,
    PRODUCT
}