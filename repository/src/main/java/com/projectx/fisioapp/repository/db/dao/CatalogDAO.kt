package com.projectx.fisioapp.repository.db.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.projectx.fisioapp.repository.db.DBHelper
import com.projectx.fisioapp.repository.db.constants.DBCatalogConstants
import com.projectx.fisioapp.repository.entitymodel.catalog.CatalogData
import com.projectx.fisioapp.repository.entitymodel.catalog.CatalogType


class CatalogDAO(dbHelper: DBHelper) : DAOPersistable<CatalogData> {


    private val dbReadOnlyConn: SQLiteDatabase = dbHelper.readableDatabase
    private val dbReadWriteOnlyConn: SQLiteDatabase = dbHelper.writableDatabase

    private fun contentValues(entityData: CatalogData): ContentValues {
        val content = ContentValues()

        content.put(DBCatalogConstants.KEY_ENTITY_DATABASE_ID, entityData.databaseId)
        content.put(DBCatalogConstants.KEY_ENTITY_NAME, entityData.name)
        content.put(DBCatalogConstants.KEY_ENTITY_DESCRIPTION, entityData.description)
        content.put(DBCatalogConstants.KEY_ENTITY_PRICE, entityData.price)
        content.put(DBCatalogConstants.KEY_ENTITY_IS_ACTIVE, entityData.isActive)
        content.put(DBCatalogConstants.KEY_ENTITY_PROFESSIONAL_ID, entityData.professionalId)
        //content.put(DBCatalogConstants.KEY_ENTITY_IMAGE_URL, entityData.image)
        content.put(DBCatalogConstants.KEY_ENTITY_TYPE, entityData.type.name)
        return content
    }


    override fun count(): Int {
        val cursor = dbReadOnlyConn.rawQuery(DBCatalogConstants.QUERY_COUNT, null)
        cursor.moveToFirst()
        return cursor.getInt(0)
    }

    override fun query(): List<CatalogData> {
        val result = ArrayList<CatalogData>()

        val cursor = dbReadOnlyConn.query(DBCatalogConstants.TABLE_CATALOG,
                DBCatalogConstants.ALL_COLUMNS,
                null,
                null,
                "",
                "",
                DBCatalogConstants.KEY_ENTITY_NAME + " ASC")

        while (cursor.moveToNext()) {
            val entity = entityFromCursor(cursor)!!
            result.add(entity)
        }

        return result
    }

    override fun query(id: String): List<CatalogData> {
        val result = ArrayList<CatalogData>()

        val cursor = dbReadOnlyConn.query(DBCatalogConstants.TABLE_CATALOG,
                DBCatalogConstants.ALL_COLUMNS,
                DBCatalogConstants.KEY_ENTITY_DATABASE_ID + " = ?",
                arrayOf(id),
                "",
                "",
                DBCatalogConstants.KEY_ENTITY_NAME + " ASC")

        while (cursor.moveToNext()) {
            val entity = entityFromCursor(cursor)!!
            result.add(entity)
        }

        return result
    }

    private fun entityFromCursor(cursor: Cursor): CatalogData? {

        if (cursor.isAfterLast || cursor.isBeforeFirst) {
            return null
        }

        val isActive = cursor.getInt(cursor.getColumnIndex(DBCatalogConstants.KEY_ENTITY_IS_ACTIVE)) == 1
        val type : CatalogType = if (cursor.getString(cursor.getColumnIndex(DBCatalogConstants.KEY_ENTITY_TYPE)) === "SERVICE")  CatalogType.SERVICE else CatalogType.PRODUCT

        return CatalogData(cursor.getString(cursor.getColumnIndex(DBCatalogConstants.KEY_ENTITY_DATABASE_ID)),
                cursor.getString(cursor.getColumnIndex(DBCatalogConstants.KEY_ENTITY_NAME)),
                cursor.getString(cursor.getColumnIndex(DBCatalogConstants.KEY_ENTITY_DESCRIPTION)),
                cursor.getFloat(cursor.getColumnIndex(DBCatalogConstants.KEY_ENTITY_PRICE)),
                cursor.getString(cursor.getColumnIndex(DBCatalogConstants.KEY_ENTITY_PROFESSIONAL_ID)),
                isActive,
                //cursor.getString(cursor.getColumnIndex(DBCatalogConstants.KEY_ENTITY_IMAGE_URL)),
                type
        )
    }


    override fun insert(element: CatalogData, type: String): Long = dbReadWriteOnlyConn.insert(DBCatalogConstants.TABLE_CATALOG, null, contentValues(element))

    override fun insertOrUpdate(element: CatalogData): Long {

        val result: Long = 1
        val foundIntem = query(element.databaseId)

        if(foundIntem.isNotEmpty()){
            update(element.databaseId, element)
            return result
        }
        return dbReadWriteOnlyConn.insert(DBCatalogConstants.TABLE_CATALOG, null, contentValues(element))

    }

    override fun update(id: String, element: CatalogData): String =
            dbReadWriteOnlyConn.update(
                    DBCatalogConstants.TABLE_CATALOG,
                    contentValues(element),
                    DBCatalogConstants.KEY_ENTITY_DATABASE_ID + " = ?",
                    arrayOf(id)).toString()

    override fun delete(id: String): String = dbReadWriteOnlyConn.delete(DBCatalogConstants.TABLE_CATALOG,
            DBCatalogConstants.KEY_ENTITY_DATABASE_ID + " = ?",
            arrayOf(id)).toString()

    override fun deleteAll(): Boolean = dbReadWriteOnlyConn.delete(DBCatalogConstants.TABLE_CATALOG,
            null,
            null).toLong() >= 0

    // Empty implementation as this method is used only for AppointmentDAO
    override fun insert(element: CatalogData): Long {
        return 0
    }

    // Specific functions for CatalogDao

    fun queryType(type: String): List<CatalogData> {
        val result = ArrayList<CatalogData>()

        val cursor = dbReadOnlyConn.query(DBCatalogConstants.TABLE_CATALOG,
                DBCatalogConstants.ALL_COLUMNS,
                DBCatalogConstants.KEY_ENTITY_TYPE + " = ?",
                arrayOf(type),
                "",
                "",
                DBCatalogConstants.KEY_ENTITY_NAME + " ASC")

        while (cursor.moveToNext()) {
            val entity = entityFromCursor(cursor)!!
            result.add(entity)
        }

        return result
    }

}