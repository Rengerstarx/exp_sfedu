import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.sfedu_exponent.R
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class AdapterForHidePanel : RecyclerView.Adapter<AdapterForHidePanel.ImageViewHolder>() {
    private var images: MutableList<String> = arrayListOf<String>() as MutableList<String>

    fun addImage(imageResId: MutableList<String>) {
        images = imageResId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_smena, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageResId = images[position]
        //holder.imageView.setImageResource(R.drawable.ictis1)
        val storage = FirebaseStorage.getInstance()
        storage.reference.child("Photos/${imageResId}").downloadUrl.addOnSuccessListener { uri ->
            val imageUrl = uri.toString()
            Picasso.get().load(imageUrl).into(holder.imageView)
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }

    fun getSize(): Int {
        return images.size
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView4)
    }
}

