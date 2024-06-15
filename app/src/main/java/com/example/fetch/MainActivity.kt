package com.example.fetch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import adapter.ItemAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import model.Item
import network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.AdapterView
import android.view.View
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val spinner: Spinner = findViewById<Spinner>(R.id.spinnerListId)
        recyclerView.layoutManager = LinearLayoutManager(this)

        RetrofitClient.apiService.fetchItems().enqueue(object : Callback<List<Item>> {
            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                if (response.isSuccessful) {
                    val items = response.body()!!
                        .filter { it.name != null && it.name.isNotBlank() }
                        .sortedWith(compareBy({ it.listId }, { it.name }))
                    // This is listed by name (string), so "Item 280" is displayed before "Item 29". If we want to sort by ItemId, then try {it.id}

                    // Extract unique listIds and sort them
                    val listIds = items.mapNotNull { it.listId }.distinct().sorted()

                    val adapter = ArrayAdapter<Int>(this@MainActivity, android.R.layout.simple_spinner_item, listIds)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                    spinner.adapter = adapter

                    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                            val selectedListId = listIds[position]
                            val filteredItems = items.filter { it.listId == selectedListId }
                            recyclerView.adapter = ItemAdapter(filteredItems)
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {
                            recyclerView.adapter = ItemAdapter(items)
                        }
                    }
                } else {
                    // response failed, handle error
                    Toast.makeText(baseContext, "failed to get valid response from server", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                // Handle errors, possibly show a user-friendly message or log
                Toast.makeText(baseContext, "failed fetch data from server", Toast.LENGTH_LONG).show()
            }
        })
    }
}


//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//        RetrofitClient.apiService.fetchItems().enqueue(object : Callback<List<Item>> {
//            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
//                if (response.isSuccessful) {
//                    val items = response.body()!!
//                        .filter { it.name != null && it.name.isNotBlank() }
//                        .sortedWith(compareBy({ it.listId }, { it.name }))
//
//                    recyclerView.adapter = ItemAdapter(items)
//                }
//            }
//
//            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
//                // Handle errors, possibly show a user-friendly message or log
//            }
//        })
//    }
//}