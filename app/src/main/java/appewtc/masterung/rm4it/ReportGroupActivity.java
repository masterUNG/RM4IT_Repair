package appewtc.masterung.rm4it;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ReportGroupActivity extends AppCompatActivity {
    String idUser;
    private String[] userStrings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_group);

        userStrings =  getSharePreference();
        if(userStrings[0]!=null && userStrings[0].toString().length()>0){
            idUser = userStrings[0].toString();
        }else{
            idUser = "1";
        }
    }


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

    public void onButtonClick(View view) {

        switch (view.getId()) {
            case R.id.btnHome:
                finish();
                break;
            case R.id.btnGroup1:
               //correctTABLE
                Intent i1 = new Intent(ReportGroupActivity.this, ShowListView.class);
                // intent3.putExtra("User", resultStrings);
                i1.putExtra("idUser",idUser);
                i1.putExtra("groupid",1);
                i1.putExtra("category","correct");
                startActivity(i1);
                break;
            case R.id.btnGroup2:
                 //environmentTABLE
                Intent i2 = new Intent(ReportGroupActivity.this, ShowListView.class);
                i2.putExtra("idUser",idUser);
                i2.putExtra("groupid",2);
                i2.putExtra("category","environment");
                startActivity(i2);
                break;
            case R.id.btnGroup3:
                 //gevernanceTABLE
                Intent i3 = new Intent(ReportGroupActivity.this, ShowListView.class);
                i3.putExtra("idUser",idUser);
                i3.putExtra("groupid",3);
                i3.putExtra("category","governance");
                startActivity(i3);
                break;
            case R.id.btnGroup4:
                //internetTABLE
                Intent i4 = new Intent(ReportGroupActivity.this, ShowListView.class);
                i4.putExtra("idUser",idUser);
                i4.putExtra("groupid",4);
                i4.putExtra("category","internet");
                startActivity(i4);
                break;
            case R.id.btnGroup5:
               //moneyTABLE
                Intent i5 = new Intent(ReportGroupActivity.this, ShowListView.class);
                i5.putExtra("idUser",idUser);
                i5.putExtra("groupid",5);
                i5.putExtra("category","money");
                startActivity(i5);
                break;
            case R.id.btnGroup6:
               //network_intrusionTABLE
                Intent i6 = new Intent(ReportGroupActivity.this, ShowListView.class);
                i6.putExtra("idUser",idUser);
                i6.putExtra("groupid",6);
                i6.putExtra("category","network_intrusion");
                startActivity(i6);
                break;
            case R.id.btnGroup7:
               //server_networkTABLE
                Intent i7 = new Intent(ReportGroupActivity.this, ShowListView.class);
                i7.putExtra("idUser",idUser);
                i7.putExtra("groupid",7);
                i7.putExtra("category","server_network");
                startActivity(i7);
                break;
            case R.id.btnGroup8:
                  //virusTABLE
                Intent i8 = new Intent(ReportGroupActivity.this, ShowListView.class);
                i8.putExtra("idUser",idUser);
                i8.putExtra("groupid",8);
                i8.putExtra("category","virus");
                startActivity(i8);
                break;
            case R.id.btnGroup9:
                //wiless_networkTABLE
                Intent i9 = new Intent(ReportGroupActivity.this, ShowListView.class);
                i9.putExtra("idUser",idUser);
                i9.putExtra("groupid",9);
                i9.putExtra("category","wiless_network");
                startActivity(i9);
                break;

        }   // switch


    }   // onClick
}
