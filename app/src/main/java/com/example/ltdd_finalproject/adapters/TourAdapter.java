package com.example.ltdd_finalproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ltdd_finalproject.R;
import com.example.ltdd_finalproject.models.Tour;

import java.util.List;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.TourViewHolder>{
    private static final  String TAG="TourAdapter";
    private List<Tour> tourList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    public TourAdapter(Context context, List<Tour> datas){
        mContext = context;
        tourList = datas;
        mLayoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public TourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.tour_item,parent,false);
        return new TourViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TourViewHolder holder, int position) {
        Tour tour = tourList.get(position);
        if (tour==null){
            return;
        }
        //holder.imageViewTour.setImageResource(tour.getImageLink());
        holder.textViewNgayDi.setText(String.valueOf(tour.getDateGo()));
        holder.textViewPrice.setText(String.valueOf(tour.getPrice()));
        holder.textViewNoiDi.setText(String.valueOf(tour.getPlaceGo()));
        holder.textViewSoNguoi.setText(String.valueOf(tour.getNumPerson()));
        holder.textViewTourName.setText(String.valueOf(tour.getTourName()));
    }

    @Override
    public int getItemCount() {
        if (tourList != null){
            return tourList.size();
        }
        return 0;
    }

    public class TourViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView imageViewTour;
        private TextView textViewTourName,textViewNgayDi,textViewPrice,textViewSoNguoi,textViewNoiDi;
        public TourViewHolder(@NonNull View itemView) {
            super(itemView);
            anhXa();
        }
        public void anhXa(){
            imageViewTour = itemView.findViewById(R.id.imageViewHotel);
            textViewTourName = itemView.findViewById(R.id.textViewHotelName);
            textViewNgayDi = itemView.findViewById(R.id.textViewDiaChi);
            textViewPrice = itemView.findViewById(R.id.textViewProvince);
            textViewSoNguoi = itemView.findViewById(R.id.textViewSoNguoi);
            textViewNoiDi = itemView.findViewById(R.id.textViewNoiDi);
        }
    }
}
