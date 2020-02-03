package ar.com.jlmiola.melitestapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ar.com.jlmiola.melitestapp.model.Product;
import ar.com.jlmiola.melitestapp.model.ProductDetail;
import ar.com.jlmiola.melitestapp.model.Search;

public class MeliViewModel extends ViewModel {
    // Se realizo una nueva consulta de Listado de Producto
    private MutableLiveData<Boolean> nuevaConsultaListado;

    // Se realizo una nueva consulta sobre el detalle de un Producto
    private MutableLiveData<Boolean> nuevaConsultaDetalle;

    // Visibilidad de ProgressBar
    private MutableLiveData<Boolean> visibilityProgressBar;

    // Consulta al API de Meli
    private MutableLiveData<String> query;

    // Resultado de una busqueda de Producto
    private MutableLiveData<Search> searchResult;

    // Producto seleccionado para su detalle de publicacion
    private MutableLiveData<Product> selectedProduct;

    // Detalle de un Producto
    private MutableLiveData<ProductDetail> productDetail;

    public MeliViewModel() {
        query = new MutableLiveData<>();
        searchResult = new MutableLiveData<>();
        productDetail = new MutableLiveData<>();
        selectedProduct = new MutableLiveData<>();
        nuevaConsultaListado = new MutableLiveData<>();
        nuevaConsultaDetalle = new MutableLiveData<>();
        visibilityProgressBar = new MutableLiveData<>();

        // Seteo valores por defecto
        nuevaConsultaListado.setValue(false);
        nuevaConsultaDetalle.setValue(true);
        searchResult.setValue(new Search());
        visibilityProgressBar.setValue(true);
    }

    public MutableLiveData<Boolean> getNuevaConsultaListado() {
        return nuevaConsultaListado;
    }

    public void setNuevaConsultaListado(Boolean nuevaConsulta) {
        this.nuevaConsultaListado.setValue(nuevaConsulta);
    }

    public LiveData<Boolean> getNuevaConsultaDetalle() {
        return nuevaConsultaDetalle;
    }

    public void setNuevaConsultaDetalle(Boolean nuevaConsulta) {
        this.nuevaConsultaDetalle.setValue(nuevaConsulta);
    }


    public Product getSelectedProduct() {
        return selectedProduct.getValue();
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct.setValue(selectedProduct);
    }

    public String getQuery() {
        return query.getValue();
    }

    public void setQuery(String query) {
        this.query.setValue(query);
    }

    public Boolean getVisibilityProgressBar() {
        return visibilityProgressBar.getValue();
    }

    public void setVisibilityProgressBar(Boolean visibilityProgressBar) {
        this.visibilityProgressBar.setValue(visibilityProgressBar);
    }

    public ProductDetail getProductDetail() {
        return productDetail.getValue();
    }

    public ProductDetail getProductDetailLiveData() {
        return productDetail.getValue();
    }

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail.setValue(productDetail);
    }

    public Search getSearchResult() {
        return searchResult.getValue();
    }

    public void updateSearchResult(Search searchResult) {
        // Tengo que setear los nuevos valores de Paging pero sin perder el listado de productos
        // que se encuentra ya cargado
        Search search = new Search();
        search.setPaging(searchResult.getPaging());

        search.setProductos(this.searchResult.getValue().getProductos());
        search.getProductos().addAll(searchResult.getProductos());

        this.searchResult.setValue(search);
    }

    public void setSearchResult(Search searchResult) {
        this.searchResult.setValue(searchResult);
    }


}
