package view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import model.Item
import network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemViewModel : ViewModel() {
    private val _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>> = _items

    init {
        fetchItems()
    }

    private fun fetchItems() {
        RetrofitClient.apiService.fetchItems().enqueue(object : Callback<List<Item>> {
            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                if (response.isSuccessful) {
                    _items.value = response.body()?.filterNot { it.name.isNullOrEmpty() }
                        ?.sortedWith(compareBy<Item> { it.listId }.thenBy { it.name })
                }
            }

            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                // Handle the error scenario
            }
        })
    }
}