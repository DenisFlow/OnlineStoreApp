package com.example.onlinestorekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_fetch_e_products.*

class FetchEProductsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetch_e_products)

        val selectedBrand: String? = intent.getStringExtra("BRAND")
        txtBrandName.text = "Products of $selectedBrand"

        var productsList = ArrayList<EProduct>()


        val productsURL = "http://192.168.56.1//OnlineStoreApp/fetch_eproducts.php?brand=$selectedBrand"

        val requestQ = Volley.newRequestQueue(this)

        val jsonAR = JsonArrayRequest(Request.Method.GET, productsURL, null, Response.Listener { response ->
            for (productJOIndex in 0.until(response.length())) {


                productsList.add(EProduct(response.getJSONObject(productJOIndex).getInt("id"), response.getJSONObject(productJOIndex).getString("name"), response.getJSONObject(productJOIndex).getInt("price"), response.getJSONObject(productJOIndex).getString("picture")))


            }

            val pAdapter = EProductAdapter(this@FetchEProductsActivity, productsList)
            productsRV.layoutManager  = LinearLayoutManager(this@FetchEProductsActivity)
            productsRV.adapter = pAdapter

        }, Response.ErrorListener { error ->
            val ddialogBuilder = AlertDialog.Builder(this)
            ddialogBuilder.setTitle("Message")
            ddialogBuilder.setMessage(error.message)
            ddialogBuilder.create().show()
        })

        requestQ.add(jsonAR)
    }
}