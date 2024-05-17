package com.example.memezgram

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MemesAdapter(val context: Context,val meme:List<Meme>):RecyclerView.Adapter<MemesAdapter.MemesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false)
        return MemesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return meme.size
    }

    override fun onBindViewHolder(holder: MemesViewHolder, position: Int) {
        val meme = meme.get(position)
        holder.textView.text = meme.title
        Glide.with(context).load(meme.url).into(holder.imageView)
        holder.shareBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            val message = "Watch this Crazy Meme on Memezly\n${meme.title},${meme.url.toString()}"
            intent.putExtra(Intent.EXTRA_TEXT, message)
            intent.setType("text/plain")
            context.startActivity(Intent.createChooser(intent, "Send To"))

        }

        holder.saveBtn.setOnClickListener {
            ImageDownloader.downloadImage(context,meme.author, meme.url)

        }
    }
    class MemesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView = itemView.findViewById<TextView>(R.id.newsTitle)
        val imageView = itemView.findViewById<ImageView>(R.id.newsImage)
        val shareBtn = itemView.findViewById<Button>(R.id.shareButton)
        val saveBtn = itemView.findViewById<Button>(R.id.saveButton)

    }
}

object ImageDownloader {

    fun downloadImage(context: Context,filename: String, downloadUrlOfImage: String) {
        try {
            val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val downloadUri = Uri.parse(downloadUrlOfImage)
            val request = DownloadManager.Request(downloadUri)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(filename)
                .setMimeType("image/png")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, "$filename.jpg")
            dm.enqueue(request)
            Toast.makeText(context, "Download Started.", Toast.LENGTH_SHORT).show()


        } catch (e: Exception) {
            Toast.makeText(context, "Image download failed.", Toast.LENGTH_SHORT).show()

        }
    }

}
