package ar.com.jlmiola.melitestapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Se integra con la clase ProductDetail para mostrar los Atributos
 * de la publicacion del producto seleccionado
 * */

public class Attribute{

    @SerializedName("id")
    public final String id;

    @SerializedName("name")
    public final String name;

    @SerializedName("value_id")
    public final String value_id;

    @SerializedName("value_name")
    public final String value_name;

    public Attribute(String id, String name, String value_id, String value_name) {
        this.id = id;
        this.name = name;
        this.value_id = value_id;
        this.value_name = value_name;
    }
}