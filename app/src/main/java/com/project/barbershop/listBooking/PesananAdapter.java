package com.project.barbershop.listBooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.project.barbershop.R;

import java.util.List;

public class PesananAdapter extends ArrayAdapter<Pesanan> {

    private Context context;
    private List<Pesanan> pesananList;

    public PesananAdapter(Context context, List<Pesanan> pesananList) {
        super(context, 0, pesananList);
        this.context = context;
        this.pesananList = pesananList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.card_item, parent, false);
        }

        Pesanan pesanan = pesananList.get(position);

        TextView tvId = view.findViewById(R.id.tv_id);
        TextView tvPelayanan = view.findViewById(R.id.tv_model);
        TextView tvTanggal = view.findViewById(R.id.tv_tanggal);
        TextView tvJam = view.findViewById(R.id.tv_jam);
        TextView tvStatus = view.findViewById(R.id.tv_stats);



        tvId.setText(pesanan.getId());
        tvPelayanan.setText(pesanan.getPelayanan());
        tvTanggal.setText(pesanan.getTanggal());
        tvJam.setText(pesanan.getJam());
        tvStatus.setText(pesanan.getStatus());



        return view;
    }
}

