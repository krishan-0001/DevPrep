package com.krish.devprep.data.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class AuthViewModel: ViewModel(){

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun firebaseAuthWithGoogle(idToken: String, onResult: (Boolean) -> Unit){
        val credential = GoogleAuthProvider.getCredential(idToken,null)

        auth.signInWithCredential(credential)
            .addOnCompleteListener { task->
                if(task.isSuccessful){
                    Log.d("Auth","Sign in success")
                    onResult(true)
                }
                else{
                    Log.d("Auth","Sign in failed")
                    onResult(false)
                }

            }
    }
    fun signOut(){
        auth.signOut()
    }
}