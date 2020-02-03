package ar.com.jlmiola.melitestapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ar.com.jlmiola.melitestapp.R;
import ar.com.jlmiola.melitestapp.databinding.ProductBinding;
import ar.com.jlmiola.melitestapp.model.Product;
import ar.com.jlmiola.melitestapp.ui.ProductDetailFragment;
import ar.com.jlmiola.melitestapp.viewmodel.MeliViewModel;

/** Adapter del RecyclerView Listado de productos
 * Esta Bindeado
 * Implementa OnItemClickListener
 * */
public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> implements OnItemClickListener {

    private List<Product> items;
    private FragmentManager fragmentManager;
    private MeliViewModel meliViewModel;

    public ProductsAdapter(MeliViewModel meliViewModel, FragmentManager fragmentManager) {
        this.meliViewModel = meliViewModel;
        this.items = meliViewModel.getSearchResult().getProductos();
        this.fragmentManager = fragmentManager;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ProductBinding itemBinding = ProductBinding.inflate(layoutInflater, parent, false);
        return new ProductViewHolder(itemBinding, this);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder itemViewHolder, int position) {
        Product product = items.get(position);
        itemViewHolder.bind(product);
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    @Override
    public void onItemClick(View view, int position) {
        meliViewModel.setSelectedProduct(items.get(position));

        ProductDetailFragment productDetailFragment = new ProductDetailFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.activity_main_fragment, productDetailFragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ProductBinding binding;
        private OnItemClickListener productViewClickListener;

        public ProductViewHolder(ProductBinding binding, OnItemClickListener productViewClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.productViewClickListener = productViewClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            this.productViewClickListener.onItemClick(view, getAdapterPosition());
        }

        public void bind(Product product) {
            binding.setProduct(product);
            binding.executePendingBindings();
        }
    }
}