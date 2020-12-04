package com.example.bmi

data class Unit(val Unit: String) {

    object ObjUnit {

        private val sem = arrayOf(
                "Select unit", "cm/kg", "inch/kg"
        )

        var list: ArrayList<Unit>? = null
            get() {

                if (field != null)
                    return field

                field = ArrayList()
                for (i in sem.indices) {

                    val countryName = sem[i]

                    val sem = Unit(countryName)
                    field!!.add(sem)
                }

                return field
            }
    }
}