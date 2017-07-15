package appewtc.masterung.rm4it;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ShowAllStatisticActivity extends AppCompatActivity {

    Button btnBack;
    TextView txt1;
    TextView txt2;
    TextView txt3;
    TextView txt4;
    TextView txt5;
    TextView txt6;
    TextView txt7;
    TextView txt8;
    TextView txt9;
    String idUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_statistic);



        btnBack = (Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                finish();
            }
        });




        txt1 = (TextView)findViewById(R.id.txt1);
        txt2 = (TextView)findViewById(R.id.txt2);
        txt3 = (TextView)findViewById(R.id.txt3);
        txt4 = (TextView)findViewById(R.id.txt4);
        txt5 = (TextView)findViewById(R.id.txt5);
        txt6 = (TextView)findViewById(R.id.txt6);
        txt7 = (TextView)findViewById(R.id.txt7);
        txt8 = (TextView)findViewById(R.id.txt8);
        txt9 = (TextView)findViewById(R.id.txt9);


        Bundle extras = getIntent().getExtras();
        if(extras!=null){

            idUser = extras.getString("idUser","1");

            readData(Integer.parseInt(idUser));
        }


        txt1.setText(readData(1));
        txt2.setText(readData(2));
        txt3.setText(readData(3));
        txt4.setText(readData(4));
        txt5.setText(readData(5));
        txt6.setText(readData(6));
        txt7.setText(readData(7));
        txt8.setText(readData(8));
        txt9.setText(readData(9));

    }

    public String readData(int id){
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


       return  callAddListWhereIDUser(riskGroupInAddListTAble);



    }
    public String callAddListWhereIDUser(String groupRisk){
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


//String.format("%.2f", floatValue);

            Log.e("_count target__",count+"");

            float statValue = (float)count/100*26;



            DecimalFormat precision = new DecimalFormat("0.00");
// dblVariable is a number variable and not a String in this case

            return precision.format(statValue)+"%";//(float)count/100*26+"%";

        } catch (Exception e) {
           Log.e("___error all stat__",e.toString());
        }

return  "0%";


    }
}
