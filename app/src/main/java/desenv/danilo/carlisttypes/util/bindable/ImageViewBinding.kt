package desenv.danilo.carlisttypes.util.bindable

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import desenv.danilo.carlisttypes.R


@BindingAdapter("app:setUrlImageNoRound")
fun ImageView.setUrlImageNoRound(url: String?) {
    if (url != null && url.isNotEmpty())
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.progress_animation)
            .error(R.drawable.ic_broken_image)
            .into(this)
}
