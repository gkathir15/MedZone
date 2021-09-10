package com.kani.medzone

/**Created by Guru kathir.J on 17,May,2021 **/
class Constants {
    companion object {
        var usermap: MutableMap<String, Any>? = null
        val DATE: String = "DATE"
        val GCMIDS: String = "GCMIDS"
        val ISGCMIDSENT: String = "IS_GCMID_SENT"
        val users: String = "USERS"
        val takeAll = "TAKEAll"
        val NotificationAction = "NotificationAction"
        const val SKIP: String = "SKIP"
        const val SNOOZE: String = "SNOOZE"
        const val TAKE: String = "TAKE"
        const val SYNC = "SYNC"
        const val DURATION = "DURATION"
        const val TABLET_ALARM = "TABLET_ALARM"
        const val BREAKFAST = "BREAKFAST"
        const val LUNCH = "LUNCH"
        const val DINNER = "DINNER"
        const val EVENING = "EVENING"

        const val callFOR = "callFor"
        const val setNewAlarm = "setNewAlarm"
        const val resetAlarmPostBoot = "resetAlarmPostBoot"
        const val isAlarmSET = "ISALARMSET"
        const val ISLoggedIN: String = "ISLoggedIN"
        const val TASK_CANCELLED = "Task Cancelled"
        const val ENTER_DOSAGE_SIZE = "Enter dosage size"
        const val ENTER_TABLET_NAME = "Enter Tablet name"
        const val CAPTURE_IMAGE_OF_TABLET = "Capture image of Tablet"
        const val TABLETS1 = "TABLETS1"
        const val TABLETS2 = "TABLETS2"
        const val TABLETS = "TABLETS"

        const val PHONE_NUMBER = "PHONE_NUMBER"
        const val NAME = "NAME"
        const val PATIENT_NO = "PATIENT_NO"
        const val ILLNESS = "ILLNESS"


        const val CARETAKER_NO = "CARETAKER_NO"

        const val SEND_CARETAKERsms = "SEND_CARETAKERsms"
        const val SNOOZETIME = "SNOOZETIME"

        const val BREAKFAST_Hour = "BREAKFAST_Hour"
        const val DINNER_Hour = "DINNER_Hour"
        const val LUNCH_Hour = "LUNCH_Hour"
        const val EVENING_Hour = "EVENING_Hour"

        const val BREAKFAST_min = "BREAKFAST_min"
        const val DINNER_min = "DINNER_min"
        const val LUNCH_min = "LUNCH_min"
        const val EVENING_Min = "EVENING_Min"


        val type = arrayOf(
            "Complete hemogram",
            "Blood sugar profile",
            "Blood Pressure",
            "Renal Function Tests",
            "Liver Function Tests",
            "Lipid profile",
            "Thyroid profile",
            "Urine investigations"
        )

        fun getLabelArray(label: String): Array<String> {
            return when (label) {
                "Complete hemogram" -> hemogramLabel
                "Blood sugar profile" -> bloodSugarProfileLabel
                "Blood Pressure" -> bloodPressureLabel
                "Renal Function Tests" -> renalFnTestsLabel
                "Liver Function Tests" -> liverFnTestsLabel
                "Thyroid profile" -> thyroidProfileLabel
                "Urine investigations" -> urineInvestigationsLabel
                else -> renalFnTestsLabel
            }
        }

        fun getUnitArray(label: String): Array<String> {
            return when (label) {
                "Complete hemogram" -> hemogramUnits
                "Blood sugar profile" -> bloodSugarProfileUnits
                "Blood Pressure" -> bloodPressureUnits
                "Renal Function Tests" -> renalFnTestsUnits
                "Liver Function Tests" -> liverFnTestsUnits
                "Thyroid profile" -> thyroidProfileUnits
                "Urine investigations" -> urineUits
                else -> renalFnTestsUnits
            }
        }


        val hemogramlabel = arrayOf(
            "FBS", "PPBS", "Random", "HbA1c"
        )

        val hemogramLabel = arrayOf(
            "Hb",
            "RBC count",
            "Platelet",
            "Total WBC count",
            "Neutrophils",
            "Lymphocytes",
            "Monocytes",
            "Eosinophils",
            "Basophil",
            "PCV",
            "ESR",
            "CRP"
        )

        val bloodSugarProfileLabel = arrayOf(
            "FBS",
            "PPBS",
            "Random",
            "HbA1c"
        )

        val bloodPressureLabel = arrayOf(
            "Systolic",
            "Diastolic"
        )

        val renalFnTestsLabel = arrayOf(
            "Blood urea",
            "Serum creatinine",
            "Serum uric acid"
        )

        val liverFnTestsLabel = arrayOf(
            "Total bilirubin",
            "Direct",
            "Indirect",
            "Total protein",
            "Serum albumin",
            "Serum globulin",
            "AST",
            "ALT",
            "GGT",
            "ALP"
        )

        val lipidProfileLabel = arrayOf(
            "Total cholesterol",
            "LDL",
            "HDL",
            "Triglycerides",
            "VLDL"
        )

        val thyroidProfileLabel = arrayOf(
            "TSH",
            "Free T3",
            "Free T4",
            "Total T3",
            "Total T4"
        )

        val urineInvestigationsLabel = arrayOf(
            "Urine color",
            "pH",
            "Specific gravity",
            "Sugar",
            "Protein",
            "Ketone",
            "Bilirubin",
            "RBC",
            "WBC",
            "Pus cells",
            "Epithelial cells",
            "Casts",
            "Crystals"
        )

        val hemogramUnits = arrayOf(
            "g/dL or g/L",
            "106 /µL or ×1012 /L",
            "103/µL or ×109 /L",
            "%",
            "mm/hr",
            "mg/L or µg/mL"
        )

        val bloodSugarProfileUnits = arrayOf(
            "mg/dL or mmol/L",
            "%",
            "mmHg"
        )
        val bloodPressureUnits = arrayOf(
            "mmHg"
        )

        val renalFnTestsUnits = arrayOf(
            "mg/dL or mmol/L",
            "mg/dL or µmol/L"
        )

        val liverFnTestsUnits = arrayOf(
            "mg/dL or µmol/L",
            "g/dL or g/L",
            "U/L"
        )

        val lipidProfileUnits = arrayOf(
            "mg/dL or mmol/L"
        )
        val urineUits = arrayOf(
            "RBCs", "WBCs", "cells/hpf", "hyaline casts/ lpf"
        )

        val thyroidProfileUnits = arrayOf(
            "µIU/mL or mIU/mL",
            "pg/dL or pmol/L",
            "ng/dL or pmol/L",
            "ng/dL or nmol/L",
            "µg/dL or nmol/L"
        )

        fun Int.toSuperScript(): String {
            return when (this) {
                0 -> "\u2070"
                1 -> "\u00B9"
                2 -> "\u00B2"
                3 -> "\u00B3"
                4 -> "\u2074"
                5 -> "\u2075"
                6 -> "\u2076"
                7 -> "\u2077"
                8 -> "\u2078"
                9 -> "\u2079"
                else -> ""
            }

        }
    }
}