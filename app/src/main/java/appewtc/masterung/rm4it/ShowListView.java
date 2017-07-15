package appewtc.masterung.rm4it;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ShowListView extends AppCompatActivity {

    private ListView listView;
    private String[] userStrings, iconStrings, titleStrings, detailStrings, detailLongStrings, contentStrings;
    private static final String urlPHP = "http://swiftcodingthai.com/rm4it/get_addList_where_IdUser.php";
    private String JSONString, resultString, myResultString;

    private String categoryStr;


    TextView txtTitle;
    String  idUser;
    int groupdid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list_view); //activity_report

        txtTitle = (TextView)findViewById(R.id.txtTitle);
        listView = (ListView) findViewById(R.id.listView);

        userStrings =  getSharePreference();//getIntent().getStringArrayExtra("User");


        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            categoryStr = extras.getString("category","correct");
            txtTitle.setText(categoryStr);
            idUser = extras.getString("idUser","1");
            groupdid = extras.getInt("groupid",1);



            String textName= "";
            switch(groupdid){
                case 1:
                    textName = getResources().getString(R.string.risk1group);
                    break;
                case 2:
                    textName = getResources().getString(R.string.risk2group);
                    break;
                case 3:
                    textName = getResources().getString(R.string.risk3group);
                    break;
                case 4:
                    textName = getResources().getString(R.string.risk4group);
                    break;
                case 5:
                    textName = getResources().getString(R.string.risk5group);
                    break;
                case 6:
                    textName = getResources().getString(R.string.risk6group);
                    break;
                case 7:
                    textName = getResources().getString(R.string.risk7group);
                    break;
                case 8:
                    textName = getResources().getString(R.string.risk8group);
                    break;
                case 9:
                    textName = getResources().getString(R.string.risk9group);
                    break;
                default:
                    textName = "";
                    break;
            }
            txtTitle.setText(textName);
        }


        readData(groupdid);
        //Get Array
//        OkHttpClient okHttpClient = new OkHttpClient();
//        RequestBody requestBody = new FormEncodingBuilder()
//                .add("isAdd", "true")
//                .add("IdUser",idUser)
//                .build();
//        Request.Builder builder = new Request.Builder();
//        Request request = builder.url(urlPHP).post(requestBody).build();
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                JSONString = response.body().string();
//                Log.d("23AugV3", "JSON ==> " + JSONString);
//                createListView(JSONString);
//            }
//        });



    }   // Main Method


    public void readData(int id){
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        String riskGroupInAddListTAble= "";
        switch(id){
            case 1:
                riskGroupInAddListTAble = "correctTABLE";
                break;
            case 2:
                riskGroupInAddListTAble = "environmentTABLE";
                break;
            case 3:
                riskGroupInAddListTAble = "governanceTABLE";
                break;
            case 4:
                riskGroupInAddListTAble = "internetTABLE";
                break;
            case 5:
                riskGroupInAddListTAble = "moneyTABLE";
                break;
            case 6:
                riskGroupInAddListTAble = "network_intrusionTABLE";
                break;
            case 7:
                riskGroupInAddListTAble = "server_networkTABLE";
                break;
            case 8:
                riskGroupInAddListTAble = "virusTABLE";
                break;
            case 9:
                riskGroupInAddListTAble = "wiless_networkTABLE";
                break;
            default:
                riskGroupInAddListTAble = "invalidTABLE";
                break;
        }


        callAddListWhereIDUser(riskGroupInAddListTAble);



    }
    public void callAddListWhereIDUser(String groupRisk){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        String urlData="http://swiftcodingthai.com/rm4it/get_addList_where_IdUser.php";
        HttpClient httpClient = new DefaultHttpClient();
        // replace with your url
        HttpPost httpPost = new HttpPost(urlData);


        //Post Data
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
        nameValuePair.add(new BasicNameValuePair("isAdd", "true"));
        nameValuePair.add(new BasicNameValuePair("IdUser",idUser));


        //Encoding POST data
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
        } catch (UnsupportedEncodingException e) {
            // log exception
            e.printStackTrace();
        }

        //making POST request.
        try {
            HttpResponse response = httpClient.execute(httpPost);
            // write response to log
            String json_string = EntityUtils.toString(response.getEntity());




            JSONArray jArray = new JSONArray(json_string);
            // Log.e("___count correct",jArray.length()+"");//ok
            ArrayList<String> listIconString = new ArrayList<String>();
            ArrayList<String> listTitleString = new ArrayList<String>();
            ArrayList<String> listDetailString = new ArrayList<String>();
            ArrayList<String> listDetailLongString = new ArrayList<String>();
            ArrayList<String> listContentString = new ArrayList<String>();
            for(int i=0;i<jArray.length();i++){
                JSONObject obj = jArray.getJSONObject(i);
                String categorySTr = obj.getString("Category");
                String imgSTr = obj.getString("Image");
                String idListName = obj.getString("IdListName");
                String content = obj.getString("content");
                if(categorySTr.equals(groupRisk)){
                    listIconString.add(imgSTr);
                    listDetailString.add(idListName);

                    listTitleString.add(categoryStr);

                    listContentString.add(content);
                    listDetailLongString.add(findTableDetailFromIdList(categoryStr,idListName));

                }

            }


            iconStrings = new String[listTitleString.size()];
            titleStrings = new String[listTitleString.size()];
            detailStrings = new String[listTitleString.size()];
            detailLongStrings = new String[listTitleString.size()];
            contentStrings = new String[listContentString.size()];

            for (int i=0;i<listTitleString.size();i++) {

                iconStrings[i] = listIconString.get(i);
                titleStrings[i] = listTitleString.get(i);
                detailLongStrings[i] = listDetailLongString.get(i);
                contentStrings[i] = listContentString.get(i);
            }   //for
            runOnUiThread(new Runnable() {
                @Override
                public void run() {


                    ShowAdapter showAdapter = new ShowAdapter(ShowListView.this, iconStrings, contentStrings, detailLongStrings);
                    listView.setAdapter(showAdapter);

                }
            });




        } catch (Exception e) {
            // Log exception
            e.printStackTrace();
        }




    }

    private String[] getSharePreference(){
        SharedPreferences settings = getSharedPreferences("USERS", 0);
        try {
            int length = settings.getInt("length",0);
            String array[] = new String[length];
            for(int i=0 ; i< length ; i++){
                array[i]  =  settings.getString("user" + "_" + i, null);
                //Log.e("__array__",array[i]);
            }
            return array;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
    private void createListView(String jsonString) {

        try {

            JSONArray jsonArray = new JSONArray(jsonString);

//            iconStrings = new String[jsonArray.length()];
//            titleStrings = new String[jsonArray.length()];
//            detailStrings = new String[jsonArray.length()];
//            detailLongStrings = new String[jsonArray.length()];


             ArrayList<String> listIconString = new ArrayList<String>();
             ArrayList<String> listTitleString = new ArrayList<String>();
            ArrayList<String> listDetailString = new ArrayList<String>();
             ArrayList<String> listDetailLongString = new ArrayList<String>();

            for (int i=0;i<jsonArray.length();i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(jsonObject.getString("Category").split("T")[0].trim().toLowerCase().equals(categoryStr.trim().toLowerCase())) {

                    listIconString.add(jsonObject.getString("Image"));
                    String[] strTitle = jsonObject.getString("Category").split("T");
                    listTitleString.add(strTitle[0]);
                    listDetailString.add(jsonObject.getString("IdListName"));
                    listDetailLongString.add(findDtailBySQLite(listTitleString.get(i),listDetailString.get(i)));
                }else{

                }

            }   //for

            iconStrings = new String[listTitleString.size()];
            titleStrings = new String[listTitleString.size()];
            detailStrings = new String[listTitleString.size()];
            detailLongStrings = new String[listTitleString.size()];


            for (int i=0;i<listTitleString.size();i++) {

                iconStrings[i] = listIconString.get(i);
                titleStrings[i] = listTitleString.get(i);
                detailLongStrings[i] = listDetailLongString.get(i);

            }   //for














//            for (int i=0;i<jsonArray.length();i++) {
//
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//
//                if(jsonObject.getString("Category").split("T")[0].trim().toLowerCase().equals(categoryStr.trim().toLowerCase())) {
//
//                    iconStrings[i] = jsonObject.getString("Image");
//
//                    String[] strTitle = jsonObject.getString("Category").split("T");
//
//                    titleStrings[i] = strTitle[0];
//                    detailStrings[i] = jsonObject.getString("IdListName");
//
//                    //detailLongStrings[i] = findDetail(titleStrings[i], detailStrings[i]);
//
//                    detailLongStrings[i] = findDtailBySQLite(titleStrings[i], detailStrings[i]);
//
//
//                    Log.d("23AugV6", "detailShout ==> " + i + " = " + titleStrings[i]);
//                    Log.d("23AugV6", "detailLong ==> " + detailLongStrings[i]);
//
//                    Log.d("23AugV4", "test ==> " + i + " = " + detailLongStrings[i]);
//
//                    Log.d("23AugV3", "Title ==> " + i + " = " + titleStrings[i]);
//                    Log.d("23AugV3", "Image ==> " + i + " = " + iconStrings[i]);
//                    Log.d("23AugV3", "Detail ==> " + i + " = " + detailStrings[i]);
//
//                }else{
//                    continue;
//                }
//
//            }   //for

            runOnUiThread(new Runnable() {
                @Override
                public void run() {


                    ShowAdapter showAdapter = new ShowAdapter(ShowListView.this, iconStrings, titleStrings, detailLongStrings);
                    listView.setAdapter(showAdapter);

                }
            });

        } catch (Exception e) {
            Log.d("23AugV4", "e ==> " + e.toString());
        }

    }   // createListView

    private String findDtailBySQLite(String titleString, String detailString) {

        String strResult = null;
        String strTable = titleString + "TABLE";
        Log.e("23AugV6", "strTable ==> " + strTable);
        Log.e("23AugV6", "Name ==> " + detailString);

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        //Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM correctTABLE WHERE _id = " + "'" + detailString + "'", null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+strTable+" WHERE _id = " + "'" + detailString + "'", null);
        cursor.moveToFirst();
        Log.d("23AugV6", "LengthCursor ==> " + cursor.getCount());
        strResult = cursor.getString(cursor.getColumnIndex("Name"));
        Log.d("23AugV6", "strResult ==> " + strResult);

        return strResult;
    }


    private String findTableDetailFromIdList(String category,String idListStr){
        HttpClient httpClient = new DefaultHttpClient();
        // replace with your url
        HttpPost httpPost = new HttpPost("http://swiftcodingthai.com/rm4it/get_detail_where_Id.php");


        //Post Data
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
        nameValuePair.add(new BasicNameValuePair("isAdd", "true"));
        nameValuePair.add(new BasicNameValuePair("Table",category));
        nameValuePair.add(new BasicNameValuePair("id",idListStr));

        //Encoding POST data
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
        } catch (UnsupportedEncodingException e) {
            // log exception
            e.printStackTrace();
        }

        //making POST request.
        try {
            HttpResponse response = httpClient.execute(httpPost);
            // write response to log
            String json_string = EntityUtils.toString(response.getEntity());
            Log.e("Detail List:",json_string);



            JSONArray jArray = new JSONArray(json_string);
            for(int i=0;i<jArray.length();i++){
                JSONObject obj = jArray.getJSONObject(i);
                String name = obj.getString("Name");
                return name;

            }


        } catch (Exception e) {
            // Log exception
            e.printStackTrace();
        }
        return "invalid";

    }
    private String findDetail(String titleString, String detailString) {

        String urlPHP2 = "http://swiftcodingthai.com/rm4it/get_detail_where_Id.php";


        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("Table", titleString)
                .add("id", detailString)
                .build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(urlPHP2).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d("23AugV5", "Result e ==> " + e.toString());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resultString = response.body().string();
                Log.e("23AugV5", "Result ==> " + resultString);

                try {

                    JSONArray jsonArray = new JSONArray(resultString);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    myResultString = jsonObject.getString("Name");


                } catch (Exception e) {
                    e.toString();
                }

            }
        });

        return myResultString;
    }

}   // Main Class

//package appewtc.masterung.rm4it;
//
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.ListView;
//
//import com.squareup.okhttp.Call;
//import com.squareup.okhttp.Callback;
//import com.squareup.okhttp.FormEncodingBuilder;
//import com.squareup.okhttp.OkHttpClient;
//import com.squareup.okhttp.Request;
//import com.squareup.okhttp.RequestBody;
//import com.squareup.okhttp.Response;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.io.IOException;
//
//public class ShowListView extends AppCompatActivity {
//
//    private ListView listView;
//    private String[] userStrings, iconStrings, titleStrings, detailStrings, detailLongStrings;
//    private static final String urlPHP = "http://swiftcodingthai.com/rm4it/get_addList_where_IdUser.php";
//    private String JSONString, resultString, myResultString;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_show_list_view); //activity_report
//
//        listView = (ListView) findViewById(R.id.listView);
//
//        userStrings = getIntent().getStringArrayExtra("User");
//
//        //Get Array
//        OkHttpClient okHttpClient = new OkHttpClient();
//        RequestBody requestBody = new FormEncodingBuilder()
//                .add("isAdd", "true")
//                .add("IdUser", userStrings[0])
//                .build();
//        Request.Builder builder = new Request.Builder();
//        Request request = builder.url(urlPHP).post(requestBody).build();
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                JSONString = response.body().string();
//                Log.d("23AugV3", "JSON ==> " + JSONString);
//                createListView(JSONString);
//            }
//        });
//
//
//
//    }   // Main Method
//
//    private void createListView(String jsonString) {
//
//        try {
//
//            JSONArray jsonArray = new JSONArray(jsonString);
//
//            iconStrings = new String[jsonArray.length()];
//            titleStrings = new String[jsonArray.length()];
//            detailStrings = new String[jsonArray.length()];
//            detailLongStrings = new String[jsonArray.length()];
//
//            for (int i=0;i<jsonArray.length();i++) {
//
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//
//                iconStrings[i] = jsonObject.getString("Image");
//
//                String[] strTitle = jsonObject.getString("Category").split("T");
//
//                titleStrings[i] = strTitle[0];
//                detailStrings[i] = jsonObject.getString("IdListName");
//
//               //detailLongStrings[i] = findDetail(titleStrings[i], detailStrings[i]);
//
//               detailLongStrings[i] = findDtailBySQLite(titleStrings[i], detailStrings[i]);
//
//
//                Log.d("23AugV6", "detailShout ==> " + i + " = " + titleStrings[i]);
//                Log.d("23AugV6", "detailLong ==> " + detailLongStrings[i]);
//
//                Log.d("23AugV4", "test ==> " + i + " = " + detailLongStrings[i]);
//
//                Log.d("23AugV3", "Title ==> " + i + " = " + titleStrings[i]);
//                Log.d("23AugV3", "Image ==> " + i + " = " + iconStrings[i]);
//                Log.d("23AugV3", "Detail ==> " + i + " = " + detailStrings[i]);
//
//            }   //for
//
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//
//                    ShowAdapter showAdapter = new ShowAdapter(ShowListView.this, iconStrings, titleStrings, detailLongStrings);
//                    listView.setAdapter(showAdapter);
//
//                }
//            });
//
//        } catch (Exception e) {
//            Log.d("23AugV4", "e ==> " + e.toString());
//        }
//
//    }   // createListView
//
//    private String findDtailBySQLite(String titleString, String detailString) {
//
//        String strResult = null;
//        String strTable = titleString + "TABLE";
//        Log.d("23AugV6", "strTable ==> " + strTable);
//        Log.d("23AugV6", "Name ==> " + detailString);
//
//        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
//                MODE_PRIVATE, null);
//        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM correctTABLE WHERE _id = " + "'" + detailString + "'", null);
//        cursor.moveToFirst();
//        Log.d("23AugV6", "LengthCursor ==> " + cursor.getCount());
//        strResult = cursor.getString(cursor.getColumnIndex("Name"));
//        Log.d("23AugV6", "strResult ==> " + strResult);
//
//        return strResult;
//    }
//
//    private String findDetail(String titleString, String detailString) {
//
//        String urlPHP2 = "http://swiftcodingthai.com/rm4it/get_detail_where_Id.php";
//
//
//        OkHttpClient okHttpClient = new OkHttpClient();
//        RequestBody requestBody = new FormEncodingBuilder()
//                .add("isAdd", "true")
//                .add("Table", titleString)
//                .add("id", detailString)
//                .build();
//        Request.Builder builder = new Request.Builder();
//        Request request = builder.url(urlPHP2).post(requestBody).build();
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//                Log.d("23AugV5", "Result e ==> " + e.toString());
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                resultString = response.body().string();
//                Log.d("23AugV5", "Result ==> " + resultString);
//
//                try {
//
//                    JSONArray jsonArray = new JSONArray(resultString);
//                    JSONObject jsonObject = jsonArray.getJSONObject(0);
//                    myResultString = jsonObject.getString("Name");
//
//
//                } catch (Exception e) {
//                    e.toString();
//                }
//
//            }
//        });
//
//        return myResultString;
//    }
//
//}   // Main Class
