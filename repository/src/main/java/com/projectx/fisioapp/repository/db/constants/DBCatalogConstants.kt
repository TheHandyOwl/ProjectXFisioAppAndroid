package com.projectx.fisioapp.repository.db.constants


object DBCatalogConstants {

    val TABLE_CATALOG = "CATALOG"

    // Table field constants
    val KEY_ENTITY_DATABASE_ID = "_id"
    val KEY_ENTITY_NAME = "NAME"
    val KEY_ENTITY_DESCRIPTION = "DESCRIPTION"
    val KEY_ENTITY_PRICE = "PRICE"
    val KEY_ENTITY_IS_ACTIVE = "IS_ACTIVE"
    val KEY_ENTITY_PROFESSIONAL_ID = "PROFESSIONAL_ID"
    //val KEY_ENTITY_IMAGE_URL = "IMAGE_URL"
    val KEY_ENTITY_TYPE = "TYPE"

    val ALL_COLUMNS = arrayOf(
            KEY_ENTITY_DATABASE_ID,
            KEY_ENTITY_NAME,
            KEY_ENTITY_DESCRIPTION,
            KEY_ENTITY_PRICE,
            KEY_ENTITY_IS_ACTIVE,
            KEY_ENTITY_PROFESSIONAL_ID,
            //KEY_ENTITY_IMAGE_URL,
            KEY_ENTITY_TYPE)

    private val SQL_SCRIPT_CREATE_CATALOG_TABLE = (
            "create table " + TABLE_CATALOG
                    + "( "
                    + KEY_ENTITY_DATABASE_ID + " string primary key, "
                    + KEY_ENTITY_NAME + " text not null,"
                    + KEY_ENTITY_DESCRIPTION + " text, "
                    + KEY_ENTITY_PRICE + " real,"
                    + KEY_ENTITY_IS_ACTIVE + " integer,"
                    + KEY_ENTITY_PROFESSIONAL_ID + " string,"
                    //+ KEY_ENTITY_IMAGE_URL + " text, "
                    + KEY_ENTITY_TYPE + " text"
                    + ");")

    val DROP_DATABASE_SCRIPTS = ""

    val CREATE_DATABASE_SCRIPTS = arrayOf(SQL_SCRIPT_CREATE_CATALOG_TABLE)

    val QUERY_COUNT = "SELECT COUNT(*) FROM $TABLE_CATALOG"



}