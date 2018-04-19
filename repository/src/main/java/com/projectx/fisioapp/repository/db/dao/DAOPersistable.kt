package com.projectx.fisioapp.repository.db.dao


interface DAOReadOps<T> {
    fun query(): List<T>
    fun query(id: String): List<T>
    fun count(): Int
}

interface DAOWirteOps<T> {
    fun insert(element: T, type: String): Long
    fun insert(element: T): Long
    fun insertOrUpdate(element: T): Long
    fun update(id: String, element: T): String

    /**
     * deletes the element with id from DB. It must start in 1
     */
    fun delete(id: String): String

    fun deleteAll(): Boolean
}

interface DAOPersistable<T> : DAOReadOps<T>, DAOWirteOps<T>