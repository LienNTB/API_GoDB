package com.example.ltdd_finalproject.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;

import com.example.ltdd_finalproject.R;
import com.example.ltdd_finalproject.adapters.HotelAdapter;
import com.example.ltdd_finalproject.adapters.VehicleAdapter;
import com.example.ltdd_finalproject.models.Hotel;
import com.example.ltdd_finalproject.models.Vehicle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AllHotelActivity extends AppCompatActivity {
    private ListView listView;
    private HotelAdapter hotelAdapter;
    private List<Hotel> hotelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_hotel);
        listView = (ListView) findViewById(R.id.listviewHotel);
        themData();
        hotelAdapter = new HotelAdapter(AllHotelActivity.this, hotelList, R.layout.hotel_item);
        listView.setAdapter(hotelAdapter);
    }

    protected void themData() {
        hotelList.add(new Hotel(
                "12345",
                "Grand Hotel",
                "123 Main Street",
                "California",
                "(123) 456-7890",
                "info@grandhotel.com",
                "www.grandhotel.com",
                "https://example.com/grandhotel.jpg"
        ));
        hotelList.add(new Hotel(
                "67890",
                "Luxury Resort",
                "456 Oak Street",
                "Florida",
                "(555) 555-1212",
                "reservations@luxuryresort.com",
                "www.luxuryresort.com",
                "https://example.com/luxuryresort.jpg"
        ));
        hotelList.add(new Hotel(
                "54321",
                "Beachfront Inn",
                "789 Beach Avenue",
                "Hawaii",
                "(808) 555-1234",
                "info@beachfrontinn.com",
                "www.beachfrontinn.com",
                "https://example.com/beachfrontinn.jpg"
        ));
    }
}