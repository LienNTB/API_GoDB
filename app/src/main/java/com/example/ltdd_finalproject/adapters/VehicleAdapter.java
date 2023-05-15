package com.example.ltdd_finalproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ltdd_finalproject.R;
import com.example.ltdd_finalproject.models.Tour;
import com.example.ltdd_finalproject.models.Vehicle;

import java.util.List;

public class VehicleAdapter extends BaseAdapter {
    private Context mContext;

    private int layout;
    private LayoutInflater mLayoutInflater;
    private List<Vehicle> vehicleList;

    public VehicleAdapter(Context context, List<Vehicle> datas, int layout){
        this.mContext=context;
        this.vehicleList = datas;
        this.layout = layout;
    }
    @Override
    public int getCount() {
        if (vehicleList != null){
            return vehicleList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
////gọi view chứa layout
//        view = inflater.inflate(layout,null);
////ánh xạ view
//        TextView txtviewModel = (TextView) view.findViewById(R.id.txtviewModel);
//        TextView txtLicense = (TextView) view.findViewById(R.id.txtLicense);
//        //ImageView imagePic = (ImageView) view.findViewById(R.id.imagePic);
////gán giá trị
//        Vehicle vehicle = vehicleList.get(i);
//        txtviewModel.setText(vehicle.getModel());
//        txtLicense.setText(vehicle.getLicensePlate());
//        //imagePic.setImageResource(monHoc.getPic());
////trả về view
//        return view;
//
//    }
    private class ViewHolder{
        TextView txtviewModel,txtLicense;
        ImageView imageViewVehicle;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view==null){
            LayoutInflater inflater = (LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            viewHolder = new ViewHolder();
            viewHolder.txtviewModel = (TextView) view.findViewById(R.id.txtviewModel);
            viewHolder.txtLicense = (TextView) view.findViewById(R.id.txtLicense);
            viewHolder.imageViewVehicle = (ImageView) view.findViewById(R.id.imageViewVehicle);
            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }
        Vehicle monHoc = vehicleList.get(i);
        viewHolder.txtviewModel.setText(monHoc.getModel());
        viewHolder.txtLicense.setText(monHoc.getLicensePlate());
        //viewHolder.imageViewVehicle.setImageResource(monHoc.ge());
//trả về view
        return view;
    }
}
