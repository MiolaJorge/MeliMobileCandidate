package ar.com.jlmiola.melitestapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * En esta clase tengo el Modelo de Producto con los atributos elegidos para traer del API de Meli:
 *
 * @see <a href = "https://developers.mercadolibre.com.ar/es_ar/publica-productos" /> Dar caracteristicas de un Producto </a>
 * <p>
 * Se crean también acá dos clases: Pictures y Attributes para utilizar dichos datos.
 * @see Attribute
 * @see Picture
 */

public class ProductDetail {

    @SerializedName("id")
    public String id;

    @SerializedName("title")
    public String titulo;

    @SerializedName("price")
    public String precio;

    @SerializedName("warranty")
    public String warranty;

    @SerializedName("pictures")
    public List<Picture> pictures;

    @SerializedName("attributes")
    public List<Attribute> attributes;

    public ProductDetail() {

    }

    public ProductDetail(String id, String titulo, String precio, String warranty, List<Picture> pictures, List<Attribute> attributes) {
        this.id = id;
        this.titulo = titulo;
        this.precio = precio;
        this.warranty = warranty;
        this.pictures = pictures;
        this.attributes = attributes;
    }

    public ProductDetail(ProductDetail fromJson) {
        this.id = fromJson.id;
        this.titulo = fromJson.titulo;
        this.precio = fromJson.precio;
        this.warranty = fromJson.warranty;
        this.pictures = fromJson.pictures;
        this.attributes = fromJson.attributes;
    }

}