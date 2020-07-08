package com.Galang.Galang10621.Adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.Galang.Galang10621.Model.Obat;
import com.Galang.Galang10621.R;

import java.util.List;

public class StokAdapter extends RecyclerView.Adapter<StokAdapter.StokViewHolder> {
    private Context mContext;
    private List<Obat> mObats;
    private OnItemClickListener mListener;

    public StokAdapter(Context context, List<Obat> uploads) {
        mContext = context;
        mObats = uploads;
    }

    @Override
    public StokViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_obat, parent, false);
        return new StokViewHolder(v);
    }

    @Override
    public void onBindViewHolder(StokViewHolder holder, int position) {
        Obat obatCurrent = mObats.get(position);
        holder.nama.setText(obatCurrent.getNama());
        holder.jumlah.setText("Stok : "+obatCurrent.getJumlah());
        Picasso.get()
                .load(obatCurrent.getGambar())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.gambar);
    }

    @Override
    public int getItemCount() {
        return mObats.size();
    }

    public class StokViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener{
        public TextView nama, jumlah;
        public ImageView gambar;

        public StokViewHolder(View itemView) {
            super(itemView);

            nama = itemView.findViewById(R.id.namaObat);
            jumlah = itemView.findViewById(R.id.jmlObat);
            gambar = itemView.findViewById(R.id.gambarObat);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Menu");
            MenuItem UpdateItem = menu.add( Menu.NONE, 1, 1, "Edit");
            MenuItem deleteItem = menu.add(Menu.NONE, 2, 2, "Hapus");

            UpdateItem.setOnMenuItemClickListener(this);
            deleteItem.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {

                    switch (item.getItemId()) {
                        case 1:
                            mListener.onUpdateItemClick(position);
                            return true;
                        case 2:
                            mListener.onDeleteItemClick(position);
                            return true;
                    }
                }
            }
            return false;
        }


        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onUpdateItemClick(int position);
        void onDeleteItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}