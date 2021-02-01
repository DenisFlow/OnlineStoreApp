package com.example.onlinestorekotlin

import android.app.DialogFragment
import android.app.DownloadManager
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


class AmountFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var fragmentView = inflater.inflate(R.layout.fragment_amount, container, false)
        var edtEnterAmount = fragmentView.findViewById<EditText>(R.id.edt_enter_amount)
        var btnAddToCart = fragmentView.findViewById<ImageButton>(R.id.image_button)

        btnAddToCart.setOnClickListener {
            var ptoUrl = "http://192.168.56.1//OnlineStoreApp/insert_temprorary_order.php?email=${Person.email}&product_id=${Person.addToCartProductID}&amount=${edtEnterAmount.text.toString()}"
            var requestQ = Volley.newRequestQueue(activity)
            var stringRequest = StringRequest(Request.Method.GET, ptoUrl, Response.Listener { response ->
                var intent = Intent(activity, CartProductsActivity::class.java)

                startActivity(intent)
            }, Response.ErrorListener { error ->
                val ddialogBuilder = AlertDialog.Builder(activity)
                ddialogBuilder.setTitle("Message")
                ddialogBuilder.setMessage(error.message)
                ddialogBuilder.create().show()
            })
            requestQ.add(stringRequest)
        }
        return fragmentView
    }

}