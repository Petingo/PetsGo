package com.brozhao.petsgo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by petingo on 2017/7/8.
 */

public class MyPetsListAdapter extends BaseAdapter {
    private List<MyPetInfo> myPetList;
    private Context context;
    private LayoutInflater inflater;

    MyPetsListAdapter(Context context, List<MyPetInfo> myPetList) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.myPetList = myPetList;
    }

    @Override
    public int getCount() {
        return myPetList.size();
    }

    @Override
    public Object getItem(int i) {
        return myPetList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return myPetList.indexOf(getItem(i));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final MyPetInfo myPetInfo = (MyPetInfo) getItem(position);
        if (view == null) {
            //viewGroup?
            view = inflater.inflate(R.layout.my_pets_list, null);
            TextView name = (TextView) view.findViewById(R.id.my_pet_list_name);
            TextView foodCookie = (TextView) view.findViewById(R.id.my_pet_list_foodCookie);
            TextView foodCan = (TextView) view.findViewById(R.id.my_pet_list_foodCan);
            TextView water = (TextView) view.findViewById(R.id.my_pet_list_water);
            name.setText(myPetInfo.getName());

            SpannableString spanTextCookie = new SpannableString("1 " + String.format(context.getString(R.string.UnitKg), myPetInfo.getFoodCookie()));
            Drawable foodCookiePic = context.getDrawable(R.drawable.cookie);
            int foodCookieTextSize = (int) foodCookie.getTextSize();
            assert foodCookiePic != null;
            foodCookiePic.setBounds(0, 0, foodCookieTextSize, foodCookieTextSize);
            ImageSpan imageSpanCookie = new ImageSpan(foodCookiePic, ImageSpan.ALIGN_BASELINE);
            spanTextCookie.setSpan(imageSpanCookie, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            foodCookie.setText(spanTextCookie);

            SpannableString spanTextCan = new SpannableString("1 " + String.format(context.getString(R.string.UnitCan), myPetInfo.getFoodCan()));
            Drawable foodCanPic = context.getDrawable(R.drawable.can);
            int foodCanTextSize = (int) foodCan.getTextSize();
            assert foodCanPic != null;
            foodCanPic.setBounds(0, 0, foodCanTextSize, foodCanTextSize);
            ImageSpan imageSpanCan = new ImageSpan(foodCanPic, ImageSpan.ALIGN_BASELINE);
            spanTextCan.setSpan(imageSpanCan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            foodCan.setText(spanTextCan);

            SpannableString spanTextWater = new SpannableString("1 " + String.format(context.getString(R.string.UnitL), myPetInfo.getWater()));
            Drawable waterPic = context.getDrawable(R.drawable.toy);
            int waterTextSize = (int) water.getTextSize();
            assert waterPic != null;
            waterPic.setBounds(0, 0, waterTextSize, waterTextSize);
            ImageSpan imageSpanWater = new ImageSpan(waterPic, ImageSpan.ALIGN_BASELINE);
            spanTextWater.setSpan(imageSpanWater, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            water.setText(spanTextWater);

//            new AsyncTask<String, Void, Bitmap>() {
//                @Override
//                protected Bitmap doInBackground(String... params) {
//                    String url = params[0];
//                    return Util.getBitmapFromURL(url);
//                }
//                @Override
//                protected void onPostExecute(Bitmap result) {
//                    pic.setImageBitmap(result);
//                    super.onPostExecute(result);
//                }
//            }.execute(petsData.getPicURL());
//


        }
        return view;
    }
}
