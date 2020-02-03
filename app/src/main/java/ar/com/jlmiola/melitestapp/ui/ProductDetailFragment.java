package ar.com.jlmiola.melitestapp.ui;


import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ar.com.jlmiola.melitestapp.R;
import ar.com.jlmiola.melitestapp.adapters.AtributesAdapter;
import ar.com.jlmiola.melitestapp.adapters.PicturesAdapter;
import ar.com.jlmiola.melitestapp.api.RetrofitConnector;
import ar.com.jlmiola.melitestapp.databinding.FragmentProductDetailBinding;
import ar.com.jlmiola.melitestapp.model.Attribute;
import ar.com.jlmiola.melitestapp.model.Picture;
import ar.com.jlmiola.melitestapp.model.ProductDetail;
import ar.com.jlmiola.melitestapp.viewmodel.MeliViewModel;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailFragment extends Fragment {

    private MeliViewModel meliViewModel;

    private RetrofitConnector retrofitConnector;

    private FragmentProductDetailBinding binding;

    public ProductDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Obtengo las llamadas al API de Meli
        retrofitConnector = new RetrofitConnector();

        // Obtengo el View Model
        meliViewModel = ViewModelProviders.of(getActivity()).get(MeliViewModel.class);

        // Binding al Layout de Detalle de Producto
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_detail, container, false);
        binding.setLifecycleOwner(this);

        // Instancio los Adapters
        binding.recyclerViewImagesProducto.setHasFixedSize(true);
        binding.recyclerViewAtributosProducto.setHasFixedSize(true);

        // Set Adapters vacios
        binding.recyclerViewImagesProducto.setAdapter(new PicturesAdapter(new ArrayList<>()));
        binding.recyclerViewAtributosProducto.setAdapter(new AtributesAdapter(new ArrayList<>()));

        // Veo si se necesita traer el detalle del API o se llamo por una rotacion u otro evento del sistema
        final Observer<Boolean> nuevaConsultaDetalle = nuevaConsultaDetalle1 -> {
            if (nuevaConsultaDetalle1) {
                // Inicio la carga mostrando el ProgressBar
                binding.progressBarProductDetail.setVisibility(View.VISIBLE);
                binding.txtViewCaracteristicas.setVisibility(View.INVISIBLE);
                binding.linearLayoutDescripcion.setVisibility(View.INVISIBLE);

                getWepServiceApiProductoDetalle();
            }else{
                updateDisplayDatos();
            }
        };

        meliViewModel.getNuevaConsultaDetalle().observe(this, nuevaConsultaDetalle );
        return binding.getRoot();
    }

    private void updateDisplayDatos(){

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        // Actualizo los datos
        binding.txtViewNombreProducto.setText(Html.fromHtml(meliViewModel.getProductDetail().titulo));
        binding.txtViewPrecioProducto.setText(meliViewModel.getProductDetail().precio != null ? "$ " + meliViewModel.getProductDetail().precio : "");
        binding.txtViewGarantiaProducto.setText(meliViewModel.getProductDetail().warranty);

        // Actualizo el RecyclerView de Pictures
        RecyclerView recyclerViewPictures = binding.recyclerViewImagesProducto;

        List<Picture> pictureList = new ArrayList<>();

        if (meliViewModel.getProductDetail() != null) {
            pictureList.addAll(meliViewModel.getProductDetail().pictures);
        }

        PicturesAdapter picturesAdapter = new PicturesAdapter(pictureList);
        recyclerViewPictures.setAdapter(picturesAdapter);

        // Actualizo el RecyclerView de atributos
        RecyclerView recyclerViewAttribute = binding.recyclerViewAtributosProducto;
        recyclerViewAttribute.setLayoutManager(layoutManager);

        List<Attribute> attributeList = new ArrayList<>();

        if (meliViewModel.getProductDetail() != null) {
            attributeList.addAll(meliViewModel.getProductDetail().attributes);
        }

        AtributesAdapter atributesAdapter = new AtributesAdapter(attributeList);
        recyclerViewAttribute.setAdapter(atributesAdapter);

        binding.progressBarProductDetail.setVisibility(View.INVISIBLE);
        binding.txtViewCaracteristicas.setVisibility(View.VISIBLE);
        binding.linearLayoutDescripcion.setVisibility(View.VISIBLE);
    }

    private void getWepServiceApiProductoDetalle() {

        final Call<ProductDetail> searchListCall = retrofitConnector.meliWebServiceApi.getProductDetail(meliViewModel.getSelectedProduct().id);
        searchListCall.enqueue(new Callback<ProductDetail>() {
            @Override
            public void onResponse(Call<ProductDetail> call, retrofit2.Response<ProductDetail> response) {
                // Guardo el nuevo resultado
                meliViewModel.setProductDetail(response.body());

                meliViewModel.setVisibilityProgressBar(false);

                // Termine la carga
                binding.progressBarProductDetail.setVisibility(View.INVISIBLE);
                binding.txtViewCaracteristicas.setVisibility(View.VISIBLE);

                meliViewModel.setNuevaConsultaDetalle(false);
            }

            @Override
            public void onFailure(Call<ProductDetail> call, Throwable t) {
                call.cancel();

                binding.progressBarProductDetail.setVisibility(View.GONE);

                new AlertDialog.Builder(
                        getContext())
                        .setTitle(Html.fromHtml(getString(R.string.download_failed_alert_dialog_title)))
                        .setMessage(Html.fromHtml(getString(R.string.download_failed_alert_dialog_message)))
                        .setPositiveButton(Html.fromHtml(getString(R.string.download_failed_alert_dialog_ok)), null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

}
