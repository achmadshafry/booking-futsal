package com.Galang.Galang10621.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.Galang.Galang10621.Model.Obat;
import com.Galang.Galang10621.R;

import java.util.List;

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.TransaksiViewHolder> {
    private Context mContext;
    private List<Obat> mObats;
    private OnItemClickListener mListener;

    public TransaksiAdapter(Context context, List<Obat> obats) {
        mContext = context;
        mObats = obats;
    }

    @Override
    public TransaksiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_transaksi, parent, false);
        return new TransaksiViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TransaksiViewHolder holder, int position) {
        Obat obatCurrent = mObats.get(position);
        holder.nama.setText(obatCurrent.getNama());
        holder.harga.setText("Rp "+obatCurrent.getHarga());
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

    public class TransaksiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView nama, harga;
        public ImageView gambar;

        public TransaksiViewHolder(View itemView) {
            super(itemView);

            nama = itemView.findViewById(R.id.namaObatT);
            harga = itemView.findViewById(R.id.hargaObatT);
            gambar = itemView.findViewById(R.id.gambarObatT);

            itemView.setOnClickListener(this);
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
    }

    public void setOnItemClickListener(TransaksiAdapter.OnItemClickListener listener) {
        mListener = listener;
    }
}
