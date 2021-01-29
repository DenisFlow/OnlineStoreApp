package com.example.onlinestorekotlin

import android.app.DownloadManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_home_screen.*

class HomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        val brandsURL = "http://192.168.56.1//OnlineStoreApp/fetch_brands.php"

        var brandsList = ArrayList<String>()
        var requestQ = Volley.newRequestQueue(this)

        var jsonAR = JsonArrayRequest(Request.Method.GET, brandsURL, null, Response.Listener { response ->
            for (jsonObject in 0.until(response.length())) {
                brandsList.add(response.getJSONObject(jsonObject).getString("brand"))
            }

            var brandsListAdapter = ArrayAdapter(this, R.layout.brand_item_text_view, brandsList)
            brandsListView.adapter = brandsListAdapter

        }, Response.ErrorListener { error ->
            val ddialogBuilder = AlertDialog.Builder(this)
            ddialogBuilder.setTitle("Message")
            ddialogBuilder.setMessage(error.message)
            ddialogBuilder.create().show()
        })

        requestQ.add(jsonAR)

        brandsListView.setOnItemClickListener { parent, view, position, id ->

            val tappedBrand = brandsList.get(position)
            val intent = Intent(this, FetchEProductsActivity::class.java)

            intent.putExtra("BRAND", tappedBrand)
            startActivity(intent)

        }



    }
}