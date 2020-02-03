package ar.com.jlmiola.melitestapp.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ar.com.jlmiola.melitestapp.databinding.AtributeProductItemBinding;
import ar.com.jlmiola.melitestapp.model.Attribute;

/** Adapter del RecyclerView Atributos
 * Esta Bindeado
 * */
public class AtributesAdapter extends RecyclerView.Adapter<AtributesAdapter.ItemViewHolder> {

    private List<Attribute> items;

    public AtributesAdapter(List<Attribute> items) {
        this.items = items;
    }

    @Override
    public AtributesAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        AtributeProductItemBinding itemBinding = AtributeProductItemBinding.inflate(layoutInflater, parent, false);
        return new ItemViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(AtributesAdapter.ItemViewHolder itemViewHolder, int position) {
        Attribute atribute = items.get(position);
        itemViewHolder.bind(atribute);
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private AtributeProductItemBinding binding;

        public ItemViewHolder(AtributeProductItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Attribute atributo) {
            binding.setAttribute(atributo);
            binding.executePendingBindings();
        }
    }

}