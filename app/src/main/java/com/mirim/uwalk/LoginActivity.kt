package com.mirim.uwalk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.internal.TelemetryLogging.getClient
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.mirim.uwalk.databinding.ActivityLoginBinding
import com.mirim.uwalk.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignIn.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }

        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail() // email addresses도 요청함
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(applicationContext, gso)
        mAuth = FirebaseAuth.getInstance()

        var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == 1) {
                val data: Intent? = result.data
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(task)
            }
        }

        binding.btnGoogleSignIn.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            resultLauncher.launch(signInIntent)
        }

        binding.btnSignIn.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
    }
    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>){
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val email = account?.email.toString()
            val familyName = account?.familyName.toString()
        } catch (e: ApiException){
            Log.w("failed", "signInResult:failed code=" + e.statusCode)
        }
    }

}