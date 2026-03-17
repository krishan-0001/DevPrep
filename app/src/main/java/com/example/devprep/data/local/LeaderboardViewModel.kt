package com.example.devprep.data.local

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.devprep.data.User
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore


class LeaderboardViewModel: ViewModel(){

    var users by mutableStateOf<List<User>>(emptyList())
        private set
    init {
        fetchLeaderboard()
    }
    private fun fetchLeaderboard() {
        FirebaseFirestore.getInstance()
            .collection("users")
            .orderBy("score", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, _ ->
                if(snapshot!=null){
                    users = snapshot.documents.mapNotNull{
                        it.toObject(User::class.java)
                    }
                }
            }
    }

}