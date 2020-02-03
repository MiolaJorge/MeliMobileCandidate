package ar.com.jlmiola.melitestapp.model;

import android.text.Html;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.gson.annotations.SerializedName;

import ar.com.jlmiola.melitestapp.R;

/**
 * Contiene los atributos de Producto que se van a mostrar
 * en la pagina Principal de la App como listado, resultado de la busqueda
 * */

public class Product {

    public String id;

    public String thumbnail;

    @SerializedName("title")
    public String description;

    public String price;

    public String condition;

    @SerializedName("sold_quantity")
    public int soldQuantity;

    @SerializedName("available_quantity")
    public int availableQuantity;

    @SerializedName("shipping")
    public Shipping shipping;


    public Product() {
    }

    public Product(String id, String thumbnail, String description, String price, String condition, int soldQuantity, int availableQuantity, Shipping shipping) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.description = description;
        this.price = price;
        this.condition = condition;
        this.soldQuantity = soldQuantity;
        this.availableQuantity = availableQuantity;
        this.shipping = shipping;
    }

    public static class Shipping {
        @SerializedName("free_shipping")
        public String freeShipping;
    }

    public String getDescription() {
        // Formateo el texto para mostrarlo
        return String.valueOf(Html.fromHtml(description));
    }

    /**
     * Binding para cargar la imagen del Producto
     * */
    @BindingAdapter({"imageProduct"})
    public static void loadImage(ImageView imageView, String imageURL) {
        Glide.with(imageView.getContext())
                .load(imageURL)
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_404)
                .into(imageView);
    }
}
