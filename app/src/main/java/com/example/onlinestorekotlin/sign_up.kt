package com.example.onlinestorekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_sign_up.*

class sign_up : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        buttonLogInSwitch.setOnClickListener {
            var loginIntent = Intent(this, log_in::class.java)
            startActivity(loginIntent)
            finish()
        }

        buttonSugnUp.setOnClickListener {
            if (editPasswordSignUp.text.toString().equals(editConfirmPasswordSignUp.text.toString()) && editPasswordSignUp.text.toString().toString().isNotBlank()){
                // Registration process
                val signUpURL = "http://192.168.56.1//OnlineStoreApp/join_new_user.php?email=" + editUserMailSignUp.text.toString() + "&username=" + editUsernameSignUp.text.toString() + "&password=" + editPasswordSignUp.text.toString()
                val requestQ = Volley.newRequestQueue(this)
                val stringRequest = StringRequest(Request.Method.GET, signUpURL, Response.Listener {
                    response ->
                    if (response.equals("A user with this Email address already exists")) {
                        val ddialogBuilder = AlertDialog.Builder(this)
                        ddialogBuilder.setTitle("Alert")
                        ddialogBuilder.setMessage(response)
                        ddialogBuilder.create().show()
                    } else {
                        val ddialogBuilder = AlertDialog.Builder(this)
//                        ddialogBuilder.setTitle("Message")
//                        ddialogBuilder.setMessage(response)
//                        ddialogBuilder.create().show()

                        Person.email = editUserMailSignUp.text.toString()


                        Toast.makeText(this, response, Toast.LENGTH_SHORT).show()

                        val homeIntent = Intent(this, HomeScreen::class.java)
                        startActivity(homeIntent)

                    }
                }, Response.ErrorListener { 
                    error ->
                    val dialogBuilder = AlertDialog.Builder(this)
                    dialogBuilder.setTitle("Message")
                    dialogBuilder.setMessage(error.message)
                    dialogBuilder.create().show()

                })

                requestQ.add(stringRequest)
            } else {
                val ddialogBuilder = AlertDialog.Builder(this)
                ddialogBuilder.setTitle("Alert")
                ddialogBuilder.setMessage("Password Mismatch")
                ddialogBuilder.create().show()
            }
        }



    }
}