package com.example.memezgram

import android.os.Bundle
import android.util.Log
import android.util.Xml
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.memezgram.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    private lateinit var adapterOne: MemesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        getMeme()
    }

    fun getMeme() {
          val meme=MemesService.MemesInstance.getMemes(50)
           meme.enqueue(object :Callback<MemeModel> {
               override fun onResponse(call: Call<MemeModel>, response: Response<MemeModel>) {
                   val new=response.body()
                   new?.let {
                       val adapterOne=MemesAdapter(this@MainActivity,new.memes)
                       binding.memesList.adapter=adapterOne
                       binding.progressBar.visibility=View.GONE
                   }
               }

               override fun onFailure(call: Call<MemeModel>, t: Throwable) {
                   binding.errorImage.visibility= View.VISIBLE
                   binding.Oops.visibility=View.VISIBLE
                   binding.connectTointernet.visibility=View.VISIBLE
               }

           })
    }
}