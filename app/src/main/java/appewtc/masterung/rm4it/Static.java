package appewtc.masterung.rm4it;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class Static extends AppCompatActivity {

    private static final String urlPHP = "http://swiftcodingthai.com/rm4it/get_check_where.php";
    private String[] userStrings;
    private String jsonString;
    private double rick1AnInt, rick2AnInt, rick3AnInt, rick4AnInt, rick5AnInt, rick6AnInt,
            rick7AnInt, rick8AnInt, rick9AnInt;


    TextView tvName;
    TextView tvLastName;
    TextView tvProvince;

    String idUser;
    String strName;

    Button btnAllStatistic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_new); //activity_static

        btnAllStatistic = (Button)findViewById(R.id.btnAllStatistic);

        tvName = (TextView)findViewById(R.id.tvName);
       // tvLastName = (TextView)findViewById(R.id.tvLastName);
        tvProvince = (TextView)findViewById(R.id.tvProvince);

        userStrings =  getSharePreference();

        tvName.setText(userStrings[3].toString());
        tvProvince.setText(userStrings[5].toString());

        if(userStrings[3]!=null && userStrings[3].toString().length()>0){
            strName = userStrings[3].toString();
        }else{
            strName = "Master";
        }


        if(userStrings[0]!=null && userStrings[0].toString().length()>0){
            idUser = userStrings[0].toString();
        }else{
            idUser = "1";
        }
        //userStrings[1] = username
        //userstring[2] = password
        //userstring[3] = name
        //userstring[4] =id
        //userstring[5] = province


       // userStrings = getIntent().getStringArrayExtra("User");
        //Log.d("23AugV1", "NameUser ==> " + userStrings[3]);

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("NameUser", userStrings[3])
                .build(); // userStrings[3]
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(urlPHP).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                jsonString = response.body().string();

                Log.e("23AugV1", "JSON ==> " + jsonString);
                findDataPoint(jsonString);
            }
        });


    }   // Main Method


    private String[] getSharePreference(){
        SharedPreferences settings = getSharedPreferences("USERS", 0);
        try {
            int length = settings.getInt("length",0);
            String array[] = new String[length];
            for(int i=0 ; i< length ; i++){
                array[i]  =  settings.getString("user" + "_" + i, null);
                Log.e("__array__",array[i]);
            }
            return array;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
  public void onButtonClick(View v){
      if(v.getId()==R.id.btnAllStatistic){

          //Show All Statistic
          Intent i = new Intent(Static.this,ShowAllStatisticActivity.class);
          i.putExtra("idUser",idUser);
          startActivity(i);
      }else if(v.getId()==R.id.btnHome){
          Intent i = new Intent(Static.this,SignInActivity.class);
          startActivity(i);
          finish();
      }else if(v.getId() == R.id.btnGroup1){
             Intent i = new Intent(Static.this,PrivateStatisticActivity.class);
             i.putExtra("group",1);
             i.putExtra("name",strName);
          i.putExtra("idUser",idUser);
             startActivity(i);
      }else if(v.getId()==R.id.btnGroup2){
          Intent i = new Intent(Static.this,PrivateStatisticActivity.class);
          i.putExtra("group",2);
          i.putExtra("name",strName);
          i.putExtra("idUser",idUser);

          startActivity(i);

      }else if(v.getId()==R.id.btnGroup3){
          Intent i = new Intent(Static.this,PrivateStatisticActivity.class);
          i.putExtra("group",3);
          i.putExtra("name",strName);
          i.putExtra("idUser",idUser);

          startActivity(i);

      }else if(v.getId()==R.id.btnGroup4){
          Intent i = new Intent(Static.this,PrivateStatisticActivity.class);
          i.putExtra("group",4);
          i.putExtra("name",strName);
          i.putExtra("idUser",idUser);
          startActivity(i);

      }else if(v.getId()==R.id.btnGroup5){
          Intent i = new Intent(Static.this,PrivateStatisticActivity.class);
          i.putExtra("group",5);
          i.putExtra("name",strName);
          i.putExtra("idUser",idUser);
          startActivity(i);
      }else if(v.getId()==R.id.btnGroup6){
          Intent i = new Intent(Static.this,PrivateStatisticActivity.class);
          i.putExtra("group",6);
          i.putExtra("name",strName);
          i.putExtra("idUser",idUser);
          startActivity(i);
      }else if(v.getId()==R.id.btnGroup7){
          Intent i = new Intent(Static.this,PrivateStatisticActivity.class);
          i.putExtra("group",7);
          i.putExtra("name",strName);
          i.putExtra("idUser",idUser);
          startActivity(i);
      }else if(v.getId()==R.id.btnGroup8){
          Intent i = new Intent(Static.this,PrivateStatisticActivity.class);
          i.putExtra("group",8);
          i.putExtra("name",strName);
          i.putExtra("idUser",idUser);
          startActivity(i);
      }else if(v.getId()==R.id.btnGroup9){
          Intent i = new Intent(Static.this,PrivateStatisticActivity.class);
          i.putExtra("group",9);
          i.putExtra("name",strName);
          i.putExtra("idUser",idUser);
          startActivity(i);
      }

  }

    private void findDataPoint(String jsonString) {

        try {

            JSONArray jsonArray = new JSONArray(jsonString);
            JSONObject jsonObject = jsonArray.getJSONObject(jsonArray.length()-1);

            rick1AnInt = Double.parseDouble(jsonObject.getString("Risk1"))/26*100;
            rick2AnInt = Double.parseDouble(jsonObject.getString("Risk2"))/16*100;
            rick3AnInt = Double.parseDouble(jsonObject.getString("Risk3"))/8*100;
            rick4AnInt = Double.parseDouble(jsonObject.getString("Risk4"))/7*100;
            rick5AnInt = Double.parseDouble(jsonObject.getString("Risk5"))/2*100;
            rick6AnInt = Double.parseDouble(jsonObject.getString("Risk6"))/7*100;
            rick7AnInt = Double.parseDouble(jsonObject.getString("Risk7"))/7*100;
            rick8AnInt = Double.parseDouble(jsonObject.getString("Risk8"))/2*100;
            rick9AnInt = Double.parseDouble(jsonObject.getString("Risk9"))/16*100;

            Log.e("23AugV2", "rick1 = " + rick1AnInt);
            Log.e("23AugV2", "rick2 = " + rick2AnInt);
            Log.e("23AugV2", "rick3 = " + rick3AnInt);
            Log.e("23AugV2", "rick4 = " + rick4AnInt);
            Log.e("23AugV2", "rick5 = " + rick5AnInt);
            Log.e("23AugV2", "rick6 = " + rick6AnInt);
            Log.e("23AugV2", "rick7 = " + rick7AnInt);
            Log.e("23AugV2", "rick8 = " + rick8AnInt);
            Log.e("23AugV2", "rick9 = " + rick9AnInt);

          //  showGraph(rick1AnInt, rick2AnInt, rick3AnInt, rick4AnInt,
                   // rick5AnInt, rick6AnInt, rick7AnInt, rick8AnInt, rick9AnInt);

        } catch (Exception e) {
            Log.d("23AugV2", "e ==> " + e.toString());
        }


    }   // findDataPoint

    private void showGraph(double rick1AnInt, double rick2AnInt, double rick3AnInt,
                           double rick4AnInt, double rick5AnInt, double rick6AnInt,
                           double rick7AnInt, double rick8AnInt, double rick9AnInt) {



        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(0, 0.5),
                new DataPoint(1, rick1AnInt),
                new DataPoint(2, rick2AnInt),
                new DataPoint(3, rick3AnInt),
                new DataPoint(4, rick4AnInt),
                new DataPoint(5, rick5AnInt),
                new DataPoint(6, rick6AnInt),
                new DataPoint(7, rick7AnInt),
                new DataPoint(8, rick8AnInt),
                new DataPoint(9, rick9AnInt)

        });

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(10);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(100);
        graph.setTitle("Rick");


        graph.addSeries(series);


    }

}   // Main Class
