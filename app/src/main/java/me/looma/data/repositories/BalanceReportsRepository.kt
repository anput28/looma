package me.looma.data.repositories

import me.looma.data.models.MonthlyReport
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class BalanceReportsRepository {

    private val _firestore = FirebaseFirestore.getInstance()
    private val _balanceReportsCollection = _firestore.collection("balance_reports")

    fun getAllMonthlyReports(year: Int): Flow<List<MonthlyReport>> = callbackFlow {
         _balanceReportsCollection
            .whereEqualTo("year", year)
            .get()
            .addOnSuccessListener { balanceReportsSnapshot ->
                if(!balanceReportsSnapshot.isEmpty) {
                    val yearReportDocumentId = balanceReportsSnapshot.documents.first().id

                    _balanceReportsCollection
                        .document(yearReportDocumentId)
                        .collection("monthly_reports")
                        .get()
                        .addOnSuccessListener { monthlyReportsSnapshot ->
                            val monthlyReports = monthlyReportsSnapshot.toObjects(MonthlyReport::class.java)
                            trySend(monthlyReports)
                        }
                        .addOnFailureListener { exception ->
                            close(exception)
                        }
                } else {
                    trySend(emptyList())
                }
            }
            .addOnFailureListener { exception ->
                close(exception)
            }

        awaitClose { }
    }

}