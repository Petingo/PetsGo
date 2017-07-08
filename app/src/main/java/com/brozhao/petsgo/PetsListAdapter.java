package com.brozhao.petsgo;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by petingo on 2017/7/8.
 */

public class PetsListAdapter extends BaseAdapter {
    private List<PetsData> petsList;
    private Context context;
    private LayoutInflater inflater;

    PetsListAdapter(Context context, List<PetsData> petsList) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.petsList = petsList;
    }

    @Override
    public int getCount() {
        return petsList.size();
    }

    @Override
    public Object getItem(int i) {
        return petsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return petsList.indexOf(getItem(i));
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final PetsData petsData = (PetsData) getItem(position);
        if (view == null) {
            //viewGroup?
            view = inflater.inflate(R.layout.pets_info_list, null);
            final ImageView pic = (ImageView) view.findViewById(R.id.petInfoPic);
            TextView main = (TextView) view.findViewById(R.id.petInfoMain);
            TextView sex = (TextView) view.findViewById(R.id.petInfoSex);
            TextView catchDate = (TextView) view.findViewById(R.id.petInfoCatchDate);

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
            catchDate.setText(petsData.getCatchDate());



        }
        return view;
    }
}
