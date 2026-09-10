package com.sahabatulashara.app

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DashboardActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var tvWelcome: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Zamu yi simple text da farko
        tvWelcome = TextView(this)
        tvWelcome.textSize = 24f
        tvWelcome.setPadding(32, 100, 32, 32)
        setContentView(tvWelcome)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val uid = auth.currentUser?.uid
        if (uid != null) {
            db.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    val name = document.getString("name")
                    val role = document.getString("role")
                    tvWelcome.text = "Barka da zuwa $name\n\nKana cikin: $role"
                }
                .addOnFailureListener {
                    tvWelcome.text = "Ba a samu bayanin User ba"
                }
        }
    }
}
