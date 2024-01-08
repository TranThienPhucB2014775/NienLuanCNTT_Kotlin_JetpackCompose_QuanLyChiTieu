//import com.example.nln.Models.ExpenseRecord
//import com.example.nln.Models.ExpenseRecordFireBases
//import com.example.nln.Models.ExpenseRecords
//import com.google.gson.Gson
//import com.google.gson.reflect.TypeToken
//
//
//fun main() {
//    val jsonString = """
//        {
//            "-NmkVxMfYk8q4sW8z8IQ": {
//                "date": 1703768469720,
//                "descriptor": "123",
//                "isIncome": false,
//                "money": 123,
//                "purpose": "Freelance"
//            },
//            "-NmoGxSRirpKNhX7Rt_P": {
//                "date": 160376846972,
//                "descriptor": "Some descriptor",
//                "isIncome": true,
//                "money": 100,
//                "purpose": "Some purpose"
//            },
//            "-NmoGyGdUr3U6KYN6c6B": {
//                "date": 150376846972,
//                "descriptor": "Some descriptor",
//                "isIncome": true,
//                "money": 100,
//                "purpose": "Some purpose"
//            }
//        }
//    """.trimIndent()
//
//    val gson = Gson()
//
//    val type = object : TypeToken<Map<String, Any>>() {}.type
//    val data: Map<String, Any> = gson.fromJson(jsonString, type)
//
//    val expenseRecordFireBases = data.map { (id, recordData) ->
//        val expenseRecord = gson.fromJson(gson.toJson(recordData), ExpenseRecord::class.java)
//        ExpenseRecordFireBases(expenseRecord, id)
//    }
//
//    val expenseRecords = ExpenseRecords(expenseRecordFireBases)
//    println(expenseRecords.TransactionFireBases[1].id)
//}
