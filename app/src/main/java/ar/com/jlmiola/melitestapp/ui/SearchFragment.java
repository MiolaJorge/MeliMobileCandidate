package ar.com.jlmiola.melitestapp.ui;


import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ar.com.jlmiola.melitestapp.R;
import ar.com.jlmiola.melitestapp.adapters.ProductsAdapter;
import ar.com.jlmiola.melitestapp.api.RetrofitConnector;
import ar.com.jlmiola.melitestapp.databinding.FragmentSearchBinding;
import ar.com.jlmiola.melitestapp.model.Search;
import ar.com.jlmiola.melitestapp.viewmodel.MeliViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ar.com.jlmiola.melitestapp.tools.Constants.SEARCH_LIMIT_INT;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private MeliViewModel meliViewModel;

    private RetrofitConnector retrofitConnector;

    private FragmentSearchBinding binding;

    // Variable de control para evitar "Saltos" en las nuevas cargas por Scroll
    private boolean terminoCargaScroll = true;

    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Obtengo las llamadas al API de Meli
        retrofitConnector = new RetrofitConnector();

        // Obtengo el View Model
        meliViewModel = ViewModelProviders.of(getActivity()).get(MeliViewModel.class);

        // Binding al Layout de Busqueda de Productos
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        binding.setLifecycleOwner(this);

        // Seteo sobre Search Recycler
        LinearLayoutManager lManager1 = new LinearLayoutManager(getContext());
        binding.recyclerViewProductos.setLayoutManager(lManager1);
        binding.recyclerViewProductos.setHasFixedSize(true);

        // Seteo un divisor para Search Recycler
        binding.recyclerViewProductos.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        // Veo si se necesita traer el Listado de productos del API o se llamo por una rotacion u otro evento del sistema
        final Observer<Boolean> nuevaConsultaObserver = nuevaConsultaObserver1 -> {
            if (nuevaConsultaObserver1) {
                // Cargo la nueva consulta
                getWepServiceApiProductos();
            } else {
                init_Listado();
            }
        };

        meliViewModel.getNuevaConsultaListado().observe(this, nuevaConsultaObserver);

        return binding.getRoot();
    }

    private void init_Listado() {
        // Seteo los Listener del buscador
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // Seteo los valores de la busqueda en el Default
                meliViewModel.setSearchResult(new Search());

                // Seteo la Query
                meliViewModel.setQuery(query);

                // Tengo una nueva busqueda
                meliViewModel.setNuevaConsultaListado(true);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                return false;
            }
        });

        // Vuelvo a mostrar lo que habia seleccionado
        binding.searchView.setQuery(meliViewModel.getQuery(), false);

        // Seteo la variable de control para nuevas consultas sobre detalles de productos
        // Al iniciar la pantalla de Busqueda -> variable nuevaConsultaDetalle seteada en TRUE
        meliViewModel.setNuevaConsultaDetalle(true);

        // Obtengo el Adapter
        ProductsAdapter productsAdapter = new ProductsAdapter(meliViewModel, getFragmentManager());

        // Inicializo el adaptador con productsAdapter
        binding.recyclerViewProductos.setAdapter(productsAdapter);

        // Seteo el Listener OnScroll de Search Recycler para ver si se llego al final del listado
        // Si se llego al final se evalua si se necesita una nueva carga o ya no se tienen mas datos
        binding.recyclerViewProductos.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // Condiciones para actualizar el listado de articulos ya cargados
                // - Si llegue al final del listado
                // - Si el Offset mas el proximo salto es Menor al total: si no es menor ya llegue al maximo de articulos para la busqueda
                // - Si se termino de cargar el ultimo scroll: Variable de control para evitar "Saltos"
                if (!recyclerView.canScrollVertically(1)
                        && (meliViewModel.getSearchResult().getPaging().getOffset() + SEARCH_LIMIT_INT) < meliViewModel.getSearchResult().getPaging().getTotal()
                        && terminoCargaScroll) {

                    terminoCargaScroll = false;

                    meliViewModel.getSearchResult().getPaging().setOffset(meliViewModel.getSearchResult().getPaging().getOffset() + SEARCH_LIMIT_INT);

                    meliViewModel.setNuevaConsultaListado(false);

                    getWepServiceApiProductos();
                }
            }
        });
    }

    private void getWepServiceApiProductos() {
        // Arranco la carga, seteo el ProgressBar a visible
        binding.progressBarSearch.setVisibility(View.VISIBLE);

        final Call<Search> searchListCall = retrofitConnector.meliWebServiceApi.getItems(meliViewModel.getQuery(), meliViewModel.getSearchResult().getPaging().getOffset(), SEARCH_LIMIT_INT);
        searchListCall.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {

                // Si es un Scroll tengo que sumar a la lista los productos
                // Si es una busqueda nueva tengo que setear el listado Nuevo
                if (meliViewModel.getNuevaConsultaListado().getValue()) {
                    meliViewModel.setSearchResult(response.body());
                } else {
                    meliViewModel.updateSearchResult(response.body());
                }

                // Inicializo el adaptador adaptador
                ProductsAdapter productsAdapter = new ProductsAdapter(meliViewModel, getFragmentManager());

                binding.recyclerViewProductos.setAdapter(productsAdapter);

                binding.progressBarSearch.setVisibility(View.GONE);
                terminoCargaScroll = true;
                meliViewModel.setNuevaConsultaListado(false);
            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {
                call.cancel();

                binding.progressBarSearch.setVisibility(View.GONE);

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
