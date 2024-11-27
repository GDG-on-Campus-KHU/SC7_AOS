//package com.example.controlward.ui
//
//import android.util.Log
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions
//import com.google.android.gms.common.api.ApiException
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.GoogleAuthProvider
//
//@Composable
//fun GoogleSignInLayout(navController: NavController) {
//    val context = LocalContext.current
//    val token = "143760206286-n0u60ukgs2g7huao0p8li7t5a6lqatlv.apps.googleusercontent.com"
//
//    // Define the launcher for handling result from Google Sign-In
//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.StartActivityForResult(),
//    ) { result ->
//        try {
//            val account = GoogleSignIn.getSignedInAccountFromIntent(result.data)
//                .getResult(ApiException::class.java)
//            val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
//
//            // Sign in with Firebase using the Google credential
//            FirebaseAuth.getInstance().signInWithCredential(credential)
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        // If login is successful, navigate to the TabLayout screen
//                        navController.navigate("TabLayout")
//                    } else {
//                        // If login failed, log the error
//                        Log.e("TAG", "Firebase Sign-In Failed", task.exception)
//                    }
//                }
//        } catch (e: ApiException) {
//            Log.e("TAG", "Google Sign-In Failed", e)
//        }
//    }
//
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Button(
//            onClick = {
//                // Configure Google Sign-In options
//                val gso = GoogleSignInOptions
//                    .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                    .requestIdToken(token)  // Pass Firebase client ID
//                    .requestEmail()  // Request email
//                    .build()
//
//                // Initialize the Google Sign-In client
//                val googleSignInClient = GoogleSignIn.getClient(context, gso)
//
//                // Launch Google Sign-In intent
//                launcher.launch(googleSignInClient.signInIntent)
//            }
//        ) {
//            Text(text = "Sign In")
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewGoogleSignIn() {
//    GoogleSignInLayout(rememberNavController())
//}
