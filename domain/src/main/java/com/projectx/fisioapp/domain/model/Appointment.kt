package com.projectx.fisioapp.domain.model

import java.io.Serializable
import java.util.*

open class Appointment (val id: String,
                        val servicePrice: String,
                        val customerName: String,
                        val address: String,
                        val isConfirmed: Boolean,
                        val isCancelled: Boolean,
                        val date: Date,
                        val latitude: String,
                        val longitude: String,
                        val extraInfo: String): Serializable {

    // Useless?
    /*init {
        Appointments(ArrayList())
    }*/
}


class Appointments(val appointments: MutableList<Appointment>): Aggregate<Appointment> {
    override fun count(): Int = appointments.size

    override fun all(): List<Appointment> = appointments

    override operator fun get(position: Int): Appointment = appointments[position]

    override fun add(element: Appointment) {
        appointments.add(element)
    }

    override fun delete(position: Int) {
        appointments.removeAt(position)
    }

    override fun delete(element: Appointment) {
        appointments.remove(element)
    }

    fun toArray() = appointments.toTypedArray()
}

