package ar.com.jlmiola.melitestapp.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ar.com.jlmiola.melitestapp.databinding.ImageProductItemBinding;
import ar.com.jlmiola.melitestapp.model.Picture;

/** Adapter del RecyclerView de Fotos de publicacion
 * Esta Bindeado
 * */
public class PicturesAdapter extends RecyclerView.Adapter<PicturesAdapter.ItemViewHolder> {

    private List<Picture> items;

    public PicturesAdapter(List<Picture> items) {
        this.items = items;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ImageProductItemBinding itemBinding = ImageProductItemBinding.inflate(layoutInflater, parent, false);
        return new ItemViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder itemViewHolder, int position) {
        Picture picture = items.get(position);
        itemViewHolder.bind(picture);
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageProductItemBinding binding;

        public ItemViewHolder(ImageProductItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Picture picture) {
            binding.setPicture(picture);
            binding.executePendingBindings();
        }
    }

}