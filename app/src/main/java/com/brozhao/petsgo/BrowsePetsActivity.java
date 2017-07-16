package com.brozhao.petsgo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by petingo on 2017/7/8.
 */

public class BrowsePetsActivity extends Activity {
    public static List<PetsData> petsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_pets);
        final ListView petsListView = (ListView) findViewById(R.id.petsList);
        final Context mContext = this;
        final PetsListAdapter petsListAdapter = new PetsListAdapter(mContext);
        String[] info = {"2017-07-13", "你家門口", "汪汪", "無", "小型", "無中生有", "這是一隻虛擬汪汪，讓你評估每月花費",
                "https://raw.githubusercontent.com/Petingo/PetsGo/master/app/src/main/res/drawable/dog01.png", "dog"};
        String[] info2 = {"2017-07-13", "你家門口", "喵喵", "無", "小型", "無中生有", "這是一隻虛擬喵喵，讓你評估每月花費",
                "https://raw.githubusercontent.com/Petingo/PetsGo/master/app/src/main/res/drawable/meow02.png", "cat"};
        String[] info3 = {"2017-07-13", "民眾拾獲", "混種", "公", "大型", "民眾拾獲", "",
                "http://dog.chiayi.gov.tw/upload/bigphoto/thumbnail/announcement20170713112212-1.jpg", "cat"};
        String[] info4 = {"2017-07-13", "民眾拾獲", "貴賓", "公", "小型", "民眾拾獲", "僅供飼主指認.不予開放領養",
                "http://dog.chiayi.gov.tw/upload/bigphoto/thumbnail/announcement20170713112156-1.jpg", "dog"};
        String[] info5 = {"2017-07-12", "民眾拾獲", "金吉拉", "公", "大型", "民眾拾獲", "僅供飼主指認.不予開放領養",
                "http://dog.chiayi.gov.tw/upload/bigphoto/thumbnail/announcement20170712150051-1.jpg", "dog"};
        String[] info6 = {"2017-07-10", "飼主送交", "混種", "母", "幼貓", "飼主送交", "",
                "http://dog.chiayi.gov.tw/upload/bigphoto/thumbnail/announcement20170712094816-1.jpg", "dog"};
        String[] info7 = {"2017-07-08", "仁義里", "混種", "母", "幼貓", "政府捕捉", "",
                "http://dog.chiayi.gov.tw/upload/bigphoto/thumbnail/announcement20170712094616-1.jpg", "dog"};
        petsList.add(new PetsData(info));
        petsList.add(new PetsData(info2));
        petsList.add(new PetsData(info3));
        petsList.add(new PetsData(info4));
        petsList.add(new PetsData(info5));
        petsList.add(new PetsData(info6));
        petsList.add(new PetsData(info7));

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    petsList.addAll(parse());
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            final PetsListAdapter petsListAdapter = new PetsListAdapter(mContext, petsList);
//                            petsListView.setAdapter(petsListAdapter);
//                        }
//                    });
//                } catch (URISyntaxException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();


        petsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showFullInfo(petsList.get(position));
            }
        });

        petsListView.setAdapter(petsListAdapter);


    }

    private void showFullInfo(PetsData petsData) {
        final Context mContext = this;
        final AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        final View v = LayoutInflater.from(this).inflate(R.layout.pet_info_full, null);
        dialog.setView(v).setTitle("詳細資料");
        final ImageView pic = (ImageView) v.findViewById(R.id.fullPetInfoPic);
        final TextView main = (TextView) v.findViewById(R.id.fullPetInfoVariety);
        final TextView sex = (TextView) v.findViewById(R.id.fullPetInfoSex);
        final TextView cLocation = (TextView) v.findViewById(R.id.fullPetInfoCatchLocation);
        final TextView memo = (TextView) v.findViewById(R.id.fullPetInfoMemo);
        final TextView catchDate = (TextView) v.findViewById(R.id.fullPetInfoCatchDate);
        final Button adoptIt = (Button) v.findViewById(R.id.adoptIt);
        final Button donate = (Button) v.findViewById(R.id.donateForIt);
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
        if (!petsData.getMemo().isEmpty()) memo.setText(petsData.getMemo());

        adoptIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder confirmDialog = new AlertDialog.Builder(mContext);
                confirmDialog.setTitle("確定要領養他嗎？");
                confirmDialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DBHelper.insertDB(mContext, "定春", 5.0, 3.0, 5.0, "just get it!");
                        Toast.makeText(mContext, "meow!!!", Toast.LENGTH_SHORT).show();
                    }
                });
                confirmDialog.show();
            }
        });

        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View v = LayoutInflater.from(mContext).inflate(R.layout.donate_list, null);
                final AlertDialog.Builder confirmDialog = new AlertDialog.Builder(mContext);
                confirmDialog.setTitle("請選擇捐款項目");
                confirmDialog.setView(v);

                Spinner donateListSpinner = (Spinner) v.findViewById(R.id.donate_list_spinner);
                ArrayList<String> donateItem = new ArrayList<>();
                donateItem.add("全基本費用 $5500");
                donateItem.add("結紮 $3000");
                donateItem.add("五合一疫苗 $1500");
                donateItem.add("十二日照料 $1000");
                donateItem.add("小額捐款 $50");
                ArrayAdapter<String> donateItemAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, donateItem);
                donateListSpinner.setAdapter(donateItemAdapter);
                confirmDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(mContext, "謝謝你的捐款!!", Toast.LENGTH_SHORT).show();
                    }
                });
                confirmDialog.show();
            }
        });

        dialog.show();
    }
//
//    public List<PetsData> parse() throws URISyntaxException {
//        List<PetsData> tempList = new ArrayList<>();
//        String tagName = null;
//        try {
//            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//            XmlPullParser parser = factory.newPullParser();
//            InputStream IS = Util.getDataFromURL("http://dog.chiayi.gov.tw/opendata/xml/latest.xml");
//            parser.setInput(new InputStreamReader(IS));
//
//            int eventType = parser.getEventType();
//            String in[] = new String[9];
//            while (eventType != XmlPullParser.END_DOCUMENT) {
//                switch (eventType) {
//                    case XmlPullParser.START_DOCUMENT:
//                        break;
//                    case XmlPullParser.START_TAG:
//                        tagName = parser.getName();
//                        break;
//                    case XmlPullParser.TEXT:
//                        switch (tagName){
//                            case "catchDate":
//                                in[0] = parser.getText();
////                                Log.e("catch",in[0]);
//                                break;
//                            case "catchLocation":
//                                in[1] = parser.getText();
////                                Log.e("catch",in[1]);
//                                break;
//                            case "variety":
//                                in[2] = parser.getText();
////                                Log.e("catch",in[2]);
//                                break;
//                            case "sex":
//                                in[3] = parser.getText();
////                                Log.e("catch",in[3]);
//                                break;
//                            case "size":
//                                in[4] = parser.getText();
////                                Log.e("catch",in[4]);
//                                break;
//                            case "source":
//                                in[5] = parser.getText();
////                                Log.e("catch",in[5]);
//                                break;
//                            case "memo":
//                                in[6] = parser.getText();
////                                Log.e("catch",in[6]);
//                                break;
//                            case "pic":
//                                in[7] = parser.getText();
////                                Log.e("catch",in[7]);
//                                break;
//                        }
//                        break;
//                    case XmlPullParser.END_TAG:
////                        //嘗試取得當前標籤名稱，若是Data才可以增加到arrayList，並且重置
//                        String tryTagName = parser.getName();
//                        if (tryTagName.equals("catanddog")) {
//                            tempList.add(new PetsData(in));
//                        }
//                        break;
//                    case XmlPullParser.END_DOCUMENT:
//                        break;
//                }
//                eventType = parser.next();
//            }
//            return tempList;
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return tempList;
//    }


}
