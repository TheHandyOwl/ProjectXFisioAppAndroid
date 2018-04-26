package com.projectx.fisioapp.repository.db.constants

object DBAppointmentConstants {

    const val TABLE_APPOINTMENT = "APPOINTMENT"

    // Table field constants
    const val KEY_DATABASE_ID = "_id"
    const val KEY_SERVICE_ID = "SERVICE_ID"
    const val KEY_SERVICE_PRICE = "SERVICE_PRICE"
    const val KEY_CUSTOMER_ID = "CUSTOMER_ID"
    const val KEY_CUSTOMER_NAME = "CUSTOMER_NAME"
    const val KEY_ADDRESS = "APPOINTMENT_ADDRESS"
    const val KEY_PROFESSIONAL_ID = "PROFESSIONAL_ID"
    const val KEY_IS_CONFIRMED = "IS_CONFIRMED"
    const val KEY_IS_CANCELLED = "IS_CANCELLED"
    const val KEY_DATE = "DATE"
    const val KEY_LATITUDE = "LATITUDE"
    const val KEY_LONGITUDE = "LONGITUDE"
    const val KEY_EXTRA_INFO = "EXTRA_INFO"

    val ALL_COLUMNS = arrayOf(
            KEY_DATABASE_ID,
            KEY_SERVICE_ID,
            KEY_SERVICE_PRICE,
            KEY_CUSTOMER_ID,
            KEY_CUSTOMER_NAME,
            KEY_ADDRESS,
            KEY_PROFESSIONAL_ID,
            KEY_IS_CONFIRMED,
            KEY_IS_CANCELLED,
            KEY_DATE,
            KEY_LATITUDE,
            KEY_LONGITUDE,
            KEY_EXTRA_INFO)

    private val SQL_SCRIPT_CREATE_APPOINTMENT_TABLE = (
            "create table " + TABLE_APPOINTMENT
                    + "( "
                    + DBCatalogConstants.KEY_ENTITY_DATABASE_ID + " text primary key, "
                    + KEY_SERVICE_ID + " text not null,"
                    + KEY_SERVICE_PRICE + " text not null,"
                    + KEY_CUSTOMER_ID + " text not null,"
                    + KEY_CUSTOMER_NAME + " text not null,"
                    + KEY_ADDRESS + " text not null,"
                    + KEY_PROFESSIONAL_ID + " text not null,"
                    + KEY_IS_CONFIRMED + " integer,"
                    + KEY_IS_CANCELLED + " integer,"
                    + KEY_DATE + " text, "
                    + KEY_LATITUDE + " real,"
                    + KEY_LONGITUDE + " real, "
                    + KEY_EXTRA_INFO + " text"
                    + ");")

    val DROP_DATABASE_SCRIPTS = ""

    val QUERY_COUNT = "SELECT COUNT(*) FROM $TABLE_APPOINTMENT"

    val CREATE_DATABASE_SCRIPTS = arrayOf(SQL_SCRIPT_CREATE_APPOINTMENT_TABLE)

    const val FORMAT_DATE = "yyyy-MM-dd hh:mm"
}