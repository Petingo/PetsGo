package com.brozhao.petsgo;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by petingo on 2017/7/8.
 */

public class BrowsePetsActivity extends Activity {
    List<PetsData> petsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_pets);
        ListView petsListView = (ListView) findViewById(R.id.petsList);
        String[] info = {"2017-07-13", "你家門口", "汪汪", "無", "小型", "無中生有", "這是一隻虛擬汪汪，讓你評估每月花費",
                "http://imgur.com/d2Mkc3L.png"};
        String[] info2 = {"2017-07-13", "你家門口", "喵喵", "無", "小型", "無中生有", "這是一隻虛擬喵喵，讓你評估每月花費",
                "http://imgur.com/VvR0god.png"};
        //"http://imgur.com/qNRo8LX.png"
        for (int i = 0; i < 5; i++) {
            petsList.add(new PetsData(info));
            petsList.add(new PetsData(info2));
        }
        final PetsListAdapter petsListAdapter = new PetsListAdapter(this, petsList);
        petsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showFullInfo(petsList.get(position));
            }
        });
        petsListView.setAdapter(petsListAdapter);
    }

    private void showFullInfo(PetsData petsData) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        final View v = LayoutInflater.from(this).inflate(R.layout.pet_info_full, null);
        dialog.setView(v).setTitle("詳細資料");
        final ImageView pic = (ImageView) v.findViewById(R.id.fullPetInfoPic);
        final TextView main = (TextView) v.findViewById(R.id.fullPetInfoVariety);
        final TextView sex = (TextView) v.findViewById(R.id.fullPetInfoSex);
        final TextView cLocation = (TextView) v.findViewById(R.id.fullPetInfoCatchLocation);
        final TextView memo = (TextView) v.findViewById(R.id.fullPetInfoMemo);
        final TextView catchDate = (TextView) v.findViewById(R.id.fullPetInfoCatchDate);
        new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... params) {
                String url = params[0];
                return Util.getBitmapFromURL(url);
            }
            @Override
            protected void onPostExecute(Bitmap result) {
                pic.setImageBitmap(result);
                super.onPostExecute(result);
            }
        }.execute(petsData.getPicURL());

        main.setText(petsData.getVariety() + "，" + petsData.getSize());
        sex.setText(petsData.getSex());
        cLocation.setText(petsData.getCatchLocation());
        catchDate.setText(petsData.getCatchDate());
        if(!petsData.getMemo().isEmpty()) memo.setText(petsData.getMemo());
        dialog.show();
    }
}
