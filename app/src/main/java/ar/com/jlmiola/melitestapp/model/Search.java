package ar.com.jlmiola.melitestapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import static ar.com.jlmiola.melitestapp.tools.Constants.SEARCH_LIMIT_INT;

/**
 * Contiene el listado de Productos, resultado de la busqueda
 * Y los valores de paginadoo
 * */

public class Search {
    @SerializedName("results")
    private List<Product> productos;

    @SerializedName("paging")
    private Paging paging;

    public Search(List<Product> productos, Paging paging) {
        this.productos = productos;
        this.paging = paging;
    }

    public Search() {
        this.productos = new ArrayList<>();
        this.paging = new Paging(SEARCH_LIMIT_INT, 0, 0, 0);
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public List<Product> getProductos() {
        return productos;
    }

    public void setProductos(List<Product> productos) {
        this.productos = productos;
    }

    public static class Paging {
        // Cantidad de resultados que se traen por consulta
        @SerializedName("limit")
        private Integer limit;

        // Inicio del indice de resultados a mostrar por el web API
        @SerializedName("offset")
        private Integer offset;

        @SerializedName("primary_results")
        private Integer primary_results;

        // Numero total de resultados para la busqueda
        @SerializedName("total")
        private Integer total;

        public Paging(Integer limit, Integer offset, Integer primary_results, Integer total) {
            this.limit = limit;
            this.offset = offset;
            this.primary_results = primary_results;
            this.total = total;
        }

        public Integer getLimit() {
            return limit;
        }

        public void setLimit(Integer limit) {
            this.limit = limit;
        }

        public Integer getOffset() {
            return offset;
        }

        public void setOffset(Integer offset) {
            this.offset = offset;
        }

        public Integer getPrimary_results() {
            return primary_results;
        }

        public void setPrimary_results(Integer primary_results) {
            this.primary_results = primary_results;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }
    }
}
