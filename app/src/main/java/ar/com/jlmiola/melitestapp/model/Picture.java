package ar.com.jlmiola.melitestapp.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.gson.annotations.SerializedName;

import ar.com.jlmiola.melitestapp.R;

/**
 * Se integra con la clase ProductDetail para mostrar las Imagenes
 * de la publicacion del producto seleccionado
 * */
public class Picture {
    @SerializedName("id")
    public final String id;

    @SerializedName("url")
    public final String url;

    public Picture(String id, String url) {
        this.id = id;
        this.url = url;
    }

    @BindingAdapter({"imageListProduct"})
    public static void loadImage(ImageView imageView, String imageURL) {
        Glide.with(imageView.getContext())
                .load(imageURL)
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_404)
                .into(imageView);
    }
}