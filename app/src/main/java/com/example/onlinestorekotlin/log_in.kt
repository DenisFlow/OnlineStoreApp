package com.example.onlinestorekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_log_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class log_in : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        editUserMailLogIn.setText("hello@mail.com")
        editPasswordLogIn.setText("123456")

        buttonLogIn.setOnClickListener {
            val loginURL = "http://192.168.56.1//OnlineStoreApp/login_app_user.php?email=" + editUserMailLogIn.text.toString() + "&password=" + editPasswordLogIn.text.toString()

            val requestQ = Volley.newRequestQueue(this)
            val stringRequest = StringRequest(Request.Method.GET, loginURL, Response.Listener {
                    response ->
                if (response.equals("The user exists")) {
                    Person.email = editUserMailLogIn.text.toString()
                    Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
                    val homeIntent = Intent(this, HomeScreen::class.java)
                    startActivity(homeIntent)
                } else {
                    val ddialogBuilder = AlertDialog.Builder(this)
                    ddialogBuilder.setTitle("Message")
                    ddialogBuilder.setMessage(response)
                    ddialogBuilder.create().show()


                }
            }, Response.ErrorListener {
                    error ->
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("Message")
                dialogBuilder.setMessage(error.message)
                dialogBuilder.create().show()

            })

            requestQ.add(stringRequest)

        }
    }

    fun goToSignUpForm(view: View) {
        var signupIntent = Intent(this, sign_up::class.java)
        startActivity(signupIntent)
        finish()
    }


}

