package appewtc.masterung.rm4it;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    //Explicit
    private ImageView hub1ImageView, hub2ImageView, hub3ImageView,
            hub4ImageView, hub5ImageView;
    private String[] resultStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //Bind Widget
        bindWidget();

        //Receive from Intent
        resultStrings = getIntent().getStringArrayExtra("Result");

        storeStringArray(resultStrings);



        //Image Controller
        imageController();

    }   // Main Method

    private void storeStringArray(String [] userList){
        SharedPreferences settings = getSharedPreferences("USERS", 0);
        SharedPreferences.Editor editor = settings.edit();

        try {
            for(int i=0 ; i< userList.length ; i++){
                editor.putString("user" + "_" + i, userList[i]);
            }
            editor.putInt("length",userList.length);
        } catch (Exception e) {

        }

        editor.commit();
    }

    private void imageController() {
        hub1ImageView.setOnClickListener(this);
        hub2ImageView.setOnClickListener(this);
        hub3ImageView.setOnClickListener(this);
        hub4ImageView.setOnClickListener(this);
        hub5ImageView.setOnClickListener(this);

    }

    private void bindWidget() {
        hub1ImageView = (ImageView) findViewById(R.id.imageButton2);
        hub2ImageView = (ImageView) findViewById(R.id.imageButton6);
        hub3ImageView = (ImageView) findViewById(R.id.imageButton7);
        hub4ImageView = (ImageView) findViewById(R.id.imageButton8);
        hub5ImageView = (ImageView) findViewById(R.id.imageButton9);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.imageButton2:

                Intent intent = new Intent(SignInActivity.this, InformationActivity.class);
                //intent.putExtra("Result", resultStrings);
                startActivity(intent);

                break;
            case R.id.imageButton6:

                Intent intent1 = new Intent(SignInActivity.this, ChooseRisk.class);
              //  intent1.putExtra("User", resultStrings);
                startActivity(intent1);

                break;
            case R.id.imageButton7:

                Intent intent2 = new Intent(SignInActivity.this, Static.class);
                //intent2.putExtra("User", resultStrings);
                startActivity(intent2);

                break;
            case R.id.imageButton8:

                Intent intent3 = new Intent(SignInActivity.this, ReportGroupActivity.class);//ShowListView
               // intent3.putExtra("User", resultStrings);
               // Log.e("_resultString_",resultStrings.toString());
                startActivity(intent3);

                break;
            case R.id.imageButton9:
                finish();
                break;

        } // switch

    }   // onClick

}   // Main Class
