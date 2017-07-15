package appewtc.masterung.rm4it;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PrivateStatisticActivity extends AppCompatActivity {

    Button btnBack;
    TextView tv;
    int groupID;

    String strName;
    TextView tvName;
    TextView tvRiskGroupName;
    String  idUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_statistic);

    tv =(TextView) findViewById(R.id.tvOutput);
        btnBack = (Button) findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                finish();
            }
        });



        Bundle extras = getIntent().getExtras();

        tvName = (TextView)findViewById(R.id.tvName);
        tvRiskGroupName = (TextView)findViewById(R.id.tvRiskGroupName);



        if(extras!=null){
            groupID = extras.getInt("group");
            strName = extras.getString("name");
            idUser = extras.getString("idUser");

            tvName.setText(strName);

            String riskName = "";
            switch (groupID){
                case 1:
                    riskName = getResources().getString(R.string.risk1group);
                    break;
                case 2:
                    riskName = getResources().getString(R.string.risk2group);
                    break;
                case 3:
                    riskName = getResources().getString(R.string.risk3group);
                    break;
                case 4:
                    riskName = getResources().getString(R.string.risk4group);
                    break;
                case 5:
                    riskName = getResources().getString(R.string.risk5group);
                    break;
                case 6:
                    riskName = getResources().getString(R.string.risk6group);
                    break;
                case 7:
                    riskName = getResources().getString(R.string.risk7group);
                    break;
                case 8:
                    riskName = getResources().getString(R.string.risk8group);
                    break;
                case 9:
                    riskName = getResources().getString(R.string.risk9group);
                    break;
             default:
                 break;

            }
            tvRiskGroupName.setText(riskName);



            readData(groupID);




        }


    }
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
            Log.e("Http Post Response:",json_string);



             JSONArray jArray = new JSONArray(json_string);
           // Log.e("___count correct",jArray.length()+"");//ok
           int count=0;
            for(int i=0;i<jArray.length();i++){
                JSONObject obj = jArray.getJSONObject(i);
                String categorySTr = obj.getString("Category");

                if(categorySTr.equals(groupRisk)){
                    ++count;

                }
            }




            Log.e("_count target__",count+"");

            DecimalFormat precision = new DecimalFormat("0.00");

            float statValue = (float)count/100*26;

           //tv.setText((float)count/100*26+"%"); //26 total row of correct table in mysql


            tv.setText(precision.format(statValue)+"%");
        } catch (Exception e) {
            // Log exception
            e.printStackTrace();
        }




    }
}
