package ar.com.jlmiola.melitestapp.api;

import ar.com.jlmiola.melitestapp.model.ProductDetail;
import ar.com.jlmiola.melitestapp.model.Search;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WebServiceApi {

    /** SITE_ID: Consulta de valores
     * @see <a href = "https://developers.mercadolibre.com.ar/es_ar/llego-catalogo-conoce-como-adaptar-tu-integracion" />  opciones de Paises para site_id </a>
     * site_id: string que representa el país. Obligatorio. Ejemplos:
     *          MLA - > Argentina
     *          MLM - > Mexico
     *          MLB - > Brasil
     *
     * A los fines del ejercicio se deja constante en MLA
     * */


    /**
     * Busqueda de un Producto en Meli
     * Tener en cuenta: se debe realizar la consulta formada por -> URL_BASE_MELI + SITE_IDE + (query)
     *
     * @param query  -> Lo que se desea buscar
     * @param limit  -> Limite de productos a traer por consulta
     * @param offset -> Mover el limite inferior del bloque de resultados.
     * @see <a href = "https://developers.mercadolibre.com.ar/es_ar/pagina-de-resultados" />  Paginar Resultados </a>
     */
    @GET("sites/MLA/search")
    Call<Search> getItems(@Query("q") String query, @Query("offset") Integer offset, @Query("limit") Integer limit);


    /**
     * Consultar por los detalles de un Producto
     * La consulta se debe realizar -> URL_BASE_MELI + {ID_PRODUCTO}
     *
     * @param id -> id del producto a buscar
     */
    @GET("items/{id}")
    Call<ProductDetail> getProductDetail(@Path("id") String id);
    
}
