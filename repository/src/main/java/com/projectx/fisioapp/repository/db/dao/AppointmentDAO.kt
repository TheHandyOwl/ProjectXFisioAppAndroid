package com.projectx.fisioapp.repository.db.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.projectx.fisioapp.repository.db.DBHelper
import com.projectx.fisioapp.repository.db.constants.DBAppointmentConstants
import com.projectx.fisioapp.repository.entitymodel.appointments.AppoinmentData
import java.text.SimpleDateFormat
import java.util.*

class AppointmentDAO(dbHelper: DBHelper) : DAOPersistable<AppoinmentData> {

    private val dbReadOnlyConn: SQLiteDatabase = dbHelper.readableDatabase
    private val dbReadWriteOnlyConn: SQLiteDatabase = dbHelper.writableDatabase


    private fun contentValues(entityData: AppoinmentData): ContentValues {
        val content = ContentValues()

        content.put(DBAppointmentConstants.KEY_DATABASE_ID, entityData.databaseId)
        content.put(DBAppointmentConstants.KEY_SERVICE_ID, entityData.serviceId)
        content.put(DBAppointmentConstants.KEY_SERVICE_PRICE, entityData.servicePrice)
        content.put(DBAppointmentConstants.KEY_CUSTOMER_ID, entityData.customerId)
        content.put(DBAppointmentConstants.KEY_CUSTOMER_NAME, entityData.customerName)
        content.put(DBAppointmentConstants.KEY_ADDRESS, entityData.address)
        content.put(DBAppointmentConstants.KEY_PROFESSIONAL_ID, entityData.professionalId)
        content.put(DBAppointmentConstants.KEY_IS_CONFIRMED, entityData.isConfirmed)
        content.put(DBAppointmentConstants.KEY_IS_CANCELLED, entityData.isCancelled)
        content.put(DBAppointmentConstants.KEY_DATE, getDate(entityData.date))
        content.put(DBAppointmentConstants.KEY_LATITUDE, entityData.latitude)
        content.put(DBAppointmentConstants.KEY_LONGITUDE, entityData.longitude)
        content.put(DBAppointmentConstants.KEY_EXTRA_INFO, entityData.extraInfo)

        return content
    }



    private fun contentValues(id: String, isConfirmed: Boolean, isCancelled: Boolean): ContentValues {
        val content = ContentValues()

        content.put(DBAppointmentConstants.KEY_DATABASE_ID, id)
        content.put(DBAppointmentConstants.KEY_IS_CONFIRMED, isConfirmed)
        content.put(DBAppointmentConstants.KEY_IS_CANCELLED, isCancelled)

        return content
    }

    override fun count(): Int {
        val cursor = dbReadOnlyConn.rawQuery(DBAppointmentConstants.QUERY_COUNT, null)
        cursor.moveToFirst()
        return cursor.getInt(0)
    }

    override fun query(): List<AppoinmentData> {
        val result = ArrayList<AppoinmentData>()

        val cursor = dbReadOnlyConn.query(DBAppointmentConstants.TABLE_APPOINTMENT,
                DBAppointmentConstants.ALL_COLUMNS,
                null,
                null,
                "",
                "",
                DBAppointmentConstants.KEY_DATE + " ASC")

        while (cursor.moveToNext()) {
            val entity = entityFromCursor(cursor)!!
            result.add(entity)
        }

        return result
    }

    private fun entityFromCursor(cursor: Cursor): AppoinmentData? {

        if (cursor.isAfterLast || cursor.isBeforeFirst) {
            return null
        }

        val isConfirmed = cursor.getInt(cursor.getColumnIndex(DBAppointmentConstants.KEY_IS_CONFIRMED)) == 1
        val isCancelled = cursor.getInt(cursor.getColumnIndex(DBAppointmentConstants.KEY_IS_CANCELLED)) == 1
        val date = cursor.getString(cursor.getColumnIndex(DBAppointmentConstants.KEY_DATE))

        return AppoinmentData(cursor.getString(cursor.getColumnIndex(DBAppointmentConstants.KEY_DATABASE_ID)),
                cursor.getString(cursor.getColumnIndex(DBAppointmentConstants.KEY_SERVICE_ID)),
                cursor.getString(cursor.getColumnIndex(DBAppointmentConstants.KEY_SERVICE_PRICE)),
                cursor.getString(cursor.getColumnIndex(DBAppointmentConstants.KEY_CUSTOMER_ID)),
                cursor.getString(cursor.getColumnIndex(DBAppointmentConstants.KEY_CUSTOMER_NAME)),
                cursor.getString(cursor.getColumnIndex(DBAppointmentConstants.KEY_ADDRESS)),
                cursor.getString(cursor.getColumnIndex(DBAppointmentConstants.KEY_PROFESSIONAL_ID)),
                isConfirmed,
                isCancelled,
                getDate(date),
                cursor.getString(cursor.getColumnIndex(DBAppointmentConstants.KEY_LATITUDE)),
                cursor.getString(cursor.getColumnIndex(DBAppointmentConstants.KEY_LONGITUDE)),
                cursor.getString(cursor.getColumnIndex(DBAppointmentConstants.KEY_EXTRA_INFO))
        )
    }


    override fun query(id: String): List<AppoinmentData> {
        val result = ArrayList<AppoinmentData>()

        val cursor = dbReadOnlyConn.query(DBAppointmentConstants.TABLE_APPOINTMENT,
                DBAppointmentConstants.ALL_COLUMNS,
                DBAppointmentConstants.KEY_DATABASE_ID + " = ?",
                arrayOf(id),
                "",
                "",
                DBAppointmentConstants.KEY_DATE + " ASC")

        while (cursor.moveToNext()) {
            val entity = entityFromCursor(cursor)!!
            result.add(entity)
        }

        return result
    }

    override fun insert(element: AppoinmentData, type: String): Long = dbReadWriteOnlyConn.insert(DBAppointmentConstants.TABLE_APPOINTMENT, null, contentValues(element))

    override fun insert(element: AppoinmentData): Long = dbReadWriteOnlyConn.insert(DBAppointmentConstants.TABLE_APPOINTMENT, null, contentValues(element))

        override fun delete(id: String): String = dbReadWriteOnlyConn.delete(DBAppointmentConstants.TABLE_APPOINTMENT,
            DBAppointmentConstants.KEY_DATABASE_ID + " = ?",
            arrayOf(id)).toString()


    override fun deleteAll(): Boolean = dbReadWriteOnlyConn.delete(DBAppointmentConstants.TABLE_APPOINTMENT,
            null,
            null).toLong() >= 0


    override fun insertOrUpdate(element: AppoinmentData): Long {

        val result: Long = 1
        val foundAppointment = query(element.databaseId)

        if (foundAppointment.isNotEmpty()) {
            update(element.databaseId, element)
            return result
        }


        return dbReadWriteOnlyConn.insert(DBAppointmentConstants.TABLE_APPOINTMENT, null, contentValues(element))

    }

    override fun update(id: String, element: AppoinmentData): String =
            dbReadWriteOnlyConn.update(
                    DBAppointmentConstants.TABLE_APPOINTMENT,
                    contentValues(element),
                    DBAppointmentConstants.KEY_DATABASE_ID + " = ?",
                    arrayOf(id)).toString()


    /* Specific methods for AppointmentDao */

    fun update(id: String, confirmed: Boolean, cancelled: Boolean): Long {
        return dbReadWriteOnlyConn.update(
                DBAppointmentConstants.TABLE_APPOINTMENT,
                contentValues(id, confirmed, cancelled),
                DBAppointmentConstants.KEY_DATABASE_ID + " = ?",
                arrayOf(id)).toLong()
    }

    fun queryDate(type: String): List<AppoinmentData> {
        val result = ArrayList<AppoinmentData>()

        val cursor = dbReadOnlyConn.query(DBAppointmentConstants.TABLE_APPOINTMENT,
                DBAppointmentConstants.ALL_COLUMNS,
                DBAppointmentConstants.KEY_DATE + " LIKE '%"+type+"%'",
                null,
                "",
                "",
                DBAppointmentConstants.KEY_DATE + " ASC")

        while (cursor.moveToNext()) {
            val entity = entityFromCursor(cursor)!!
            result.add(entity)
        }

        return result
    }


    private fun getDate(date: String): Date {
      val format = SimpleDateFormat(DBAppointmentConstants.FORMAT_DATE)
        return format.parse(date)
    }

    private fun getDate(date: Date) : String {
        val format = SimpleDateFormat(DBAppointmentConstants.FORMAT_DATE)
        return format.format(date);
    }

}