package com.Galang.Galang10621.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.Galang.Galang10621.Model.Jual;
import com.Galang.Galang10621.R;

import java.util.List;

public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatAdapter.RiwayatViewHolder>{

    private Context mContext;
    private List<Jual> mJuals;
    private OnItemClickListener mListener;

    public RiwayatAdapter(Context context, List<Jual> juals) {
        mContext = context;
        mJuals = juals;
    }

    @Override
    public RiwayatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_riwayat, parent, false);
        return new RiwayatViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RiwayatViewHolder holder, int position) {
        Jual jualCurrent = mJuals.get(position);
        holder.noFak.setText(jualCurrent.getNoFak());
        holder.kode.setText(jualCurrent.getKode());
        holder.tglJual.setText(jualCurrent.getTglJual());
        holder.totalHarga.setText("Rp "+jualCurrent.getTotalJual());
    }

    @Override
    public int getItemCount() {
        return mJuals.size();
    }


    public class RiwayatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView noFak, kode, tglJual, totalHarga;

        public RiwayatViewHolder(View itemView) {
            super(itemView);

            noFak = itemView.findViewById(R.id.noFakIR);
            kode = itemView.findViewById(R.id.kodeIR);
            tglJual = itemView.findViewById(R.id.tglJualIR);
            totalHarga = itemView.findViewById(R.id.totalJualIR);

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

    public void setOnItemClickListener(RiwayatAdapter.OnItemClickListener listener) {
        mListener = listener;
    }
}
