package com.projectx.fisioapp.app.activity.dummy

import java.util.*

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    private val ITEMS: MutableList<DummyItem> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
    private val ITEM_MAP: MutableMap<String, DummyItem> = HashMap()

    private val COUNT = 25

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createDummyItem(i))
        }
    }

    private fun addItem(item: DummyItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.name, item)
    }

    private fun createDummyItem(position: Int): DummyItem {

        return DummyItem(position.toLong(),
                "Item " + position.toString(),
                "Description for item " + position.toString(),
                position.toFloat(),
                position.toLong(),
                true,
                "product",
                makeDetails(position)
        )
    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Item: ").append(position)
        for (i in 0 until position) {
            builder.append("\nMore details information here.")
        }
        return builder.toString()
    }

    /**
     * A dummy item representing a piece of content.
     */
    /* data class DummyItem(val id: String, val content: String, val details: String) {
         override fun toString(): String = content
     }*/

    data class DummyItem(val databaseId: Long,
                         val name: String,
                         val description: String,
                         val price: Float,
                         val professionalId: Long,
                         val isActive: Boolean,
                         val type: String,
                         val content: String) {
        override fun toString(): String = content
    }
}
