package com.mirim.uwalk

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.mirim.uwalk.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var auth: FirebaseAuth
    lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignIn.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN) //기본 로그인 방식 사용
            .requestIdToken(getString(R.string.default_web_client_id))
            //requestIdToken :필수사항이다. 사용자의 식별값(token)을 사용하겠다.
            //(App이 구글에게 요청)
            .requestEmail()
            // 사용자의 이메일을 사용하겠다.(App이 구글에게 요청)
            .build()

        googleSignInClient = GoogleSignIn.getClient(this,gso)

//        var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == 1) {
//                val data: Intent? = result.data
//                val task: Task<GoogleSignInAccount> =
//                    GoogleSignIn.getSignedInAccountFromIntent(data)
//                handleSignInResult(task)
//            }
//        }

        binding.btnGoogleSignIn.setOnClickListener {
            googleLogin()
        }

        binding.btnSignIn.setOnClickListener {
            val email = binding.editEmail.text.toString().trim()
            val password = binding.editPassword.text.toString().trim()
            if(email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if(it.isSuccessful) {
                        Toast.makeText(applicationContext, "Successfully Logged In", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else {
                        Toast.makeText(applicationContext, "Fail to Log in", Toast.LENGTH_SHORT).show()
                    }
                }

            }
            else if(email.isEmpty()) {
                Toast.makeText(applicationContext, "Please fill email", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(applicationContext, "Please fill password", Toast.LENGTH_SHORT).show()
            }
        }

        binding.txtSignUp.setOnClickListener {
            startActivity(Intent(applicationContext, SignupActivity::class.java))
        }
    }

    fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken!!, null)
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener {
                if(it.isSuccessful) {
                    Toast.makeText(applicationContext, "Successfully Logged In", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    finish()
                }
                else {
                    Log.d("LoginActivity", "firebaseAuthWithGoogle")
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1004) {
            if(resultCode == Activity.RESULT_OK) {
                val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)!!
                if(result.isSuccess) {
                    val account = result.signInAccount
                    firebaseAuthWithGoogle(account)
                }
                else {
                    Log.d("LoginActivity", "onActivityResult")
                }
            }
        }
        else {
            Log.d("LoginActivity", "error requestCode")
        }
    }

    fun googleLogin() {
        val signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, 1004)
    }
}