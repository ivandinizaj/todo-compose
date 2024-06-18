package com.ivandjr.listcompose.data.source

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import com.ivandjr.listcompose.model.Priority
import com.ivandjr.listcompose.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class DataSource(
    private val collectionPath: String = "tasks",
) {

    private val db = FirebaseFirestore.getInstance()

    fun saveTask(id: String, title: String, description: String, priority: Priority) {
        val taskMap = hashMapOf(
            "id" to id,
            "title" to title,
            "description" to description,
            "priority" to priority,

        )

        db.collection(collectionPath)
            .document(id)
            .set(taskMap)
            .addOnCompleteListener { }
            .addOnFailureListener { }
    }

    fun fetchTasks(): Flow<List<Task>> {
        return db.collection(collectionPath).snapshots().map { querySnapshot ->
            querySnapshot.toObjects(Task::class.java)
        }
    }

    fun removeTask(id: String): Boolean {
        val result = db.collection(collectionPath).document(id).delete()
        return result.isSuccessful
    }
}
