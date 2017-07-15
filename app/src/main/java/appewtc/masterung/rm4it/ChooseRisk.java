package appewtc.masterung.rm4it;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChooseRisk extends AppCompatActivity implements View.OnClickListener {

    //Explicit
    private String[] nameTableStrings;//, userStrings;
    private Button risk1Button, risk2Button,
            risk3Button, risk4Button, risk5Button,
            risk6Button, risk7Button, risk8Button, risk9Button;
    private ImageView risk1ImageView, risk2ImageView, risk3ImageView,
            risk4ImageView, risk5ImageView, risk6ImageView, risk7ImageView,
            risk8ImageView, risk9ImageView;
    private int indexAnInt = 0;
    private String riskString;
    private boolean bolStatus = true;
    private String[] checkStrings;
    public static final int PICK_IMAGE = 1, PICK_IMAGE2 = 2, PICK_IMAGE3 = 3;
    private String[] userStrings;

    TextView tvName;
    TextView txtProvince;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_risk);

        //Bind Widget
        bindWidget();

        setupNameTable();

        tvName = (TextView)findViewById(R.id.tvName);
        txtProvince = (TextView)findViewById(R.id.tvProvince);
        try {
           // userStrings = getIntent().getStringArrayExtra("User");
            userStrings =  getSharePreference();
            tvName.setText(userStrings[3].toString());
            txtProvince.setText(userStrings[5].toString());
        }catch(Exception ex){
            userStrings = null;
        }
        buttonController();

        //Add SQLite
        if (bolStatus) {

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            String strDate = dateFormat.format(date);

            MyManage myManage = new MyManage(this);
            myManage.addCheckRisk(userStrings[3], userStrings[5], strDate);
        }

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

    public void clickSavetoServer(View view) {

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM checkTABLE", null);
        cursor.moveToFirst();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "กรุณาทำแบบประเมิม ด้วยคะ", Toast.LENGTH_SHORT).show();
        } else {

            checkStrings = new String[cursor.getColumnCount()];

            for (int i=0;i<cursor.getColumnCount();i++) {

                checkStrings[i] = cursor.getString(i);

            }   // for
        }   // if
        cursor.close();

        try {

            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("isAdd", "true")
                    .add("NameUser", checkStrings[1])
                    .add("ProvinceUser", checkStrings[2])
                    .add("Date", checkStrings[3])
                    .add("Risk1", checkStrings[4])
                    .add("Risk2", checkStrings[5])
                    .add("Risk3", checkStrings[6])
                    .add("Risk4", checkStrings[7])
                    .add("Risk5", checkStrings[8])
                    .add("Risk6", checkStrings[9])
                    .add("Risk7", checkStrings[10])
                    .add("Risk8", checkStrings[11])
                    .add("Risk9", checkStrings[12])
                    .add("Total", sumTotal())
                    .build();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url("http://swiftcodingthai.com/rm4it/php_add_risk.php").post(requestBody).build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                         Log.e("_failure_",e.toString());
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    finish();
                }
            });





        } catch (Exception e) {
            e.printStackTrace();
        }

    }   // clickSave

    private String sumTotal() {

        int intSum = 0;

        for (int i=4;i<=12;i++) {
            intSum = intSum + Integer.parseInt(checkStrings[i]);
        }   // for

        return Integer.toString(intSum);
    }


    private void buttonController() {
        risk1Button.setOnClickListener(this);
        risk2Button.setOnClickListener(this);
        risk3Button.setOnClickListener(this);
        risk4Button.setOnClickListener(this);
        risk5Button.setOnClickListener(this);
        risk6Button.setOnClickListener(this);
        risk7Button.setOnClickListener(this);
        risk8Button.setOnClickListener(this);
        risk9Button.setOnClickListener(this);
    }

    private void bindWidget() {

        risk1Button = (Button) findViewById(R.id.button8);
        risk2Button = (Button) findViewById(R.id.button9);
        risk3Button = (Button) findViewById(R.id.button10);
        risk4Button = (Button) findViewById(R.id.button11);
        risk5Button = (Button) findViewById(R.id.button12);
        risk6Button = (Button) findViewById(R.id.button13);
        risk7Button = (Button) findViewById(R.id.button14);
        risk8Button = (Button) findViewById(R.id.button15);
        risk9Button = (Button) findViewById(R.id.button16);

        risk1ImageView = (ImageView) findViewById(R.id.imageView3);
        risk2ImageView = (ImageView) findViewById(R.id.imageView4);
        risk3ImageView = (ImageView) findViewById(R.id.imageView5);
        risk4ImageView = (ImageView) findViewById(R.id.imageView6);
        risk5ImageView = (ImageView) findViewById(R.id.imageView7);
        risk6ImageView = (ImageView) findViewById(R.id.imageView8);
        risk7ImageView = (ImageView) findViewById(R.id.imageView9);
        risk8ImageView = (ImageView) findViewById(R.id.imageView10);
        risk9ImageView = (ImageView) findViewById(R.id.imageView11);

    }

    private void setupNameTable() {

        nameTableStrings = new String[9];

        nameTableStrings[0] = "correctTABLE";
        nameTableStrings[1] = "environmentTABLE";
        nameTableStrings[2] = "governanceTABLE";
        nameTableStrings[3] = "internetTABLE";
        nameTableStrings[4] = "moneyTABLE";
        nameTableStrings[5] = "network_intrusionTABLE";
        nameTableStrings[6] = "server_networkTABLE";
        nameTableStrings[7] = "virusTABLE";
        nameTableStrings[8] = "wiless_networkTABLE";
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.button8:
                indexAnInt = 0;
                risk1ImageView.setImageResource(R.drawable.mytrue);
                riskString = getResources().getString(R.string.risk1group);
                break;
            case R.id.button9:
                indexAnInt = 1;
                risk2ImageView.setImageResource(R.drawable.mytrue);
                riskString = getResources().getString(R.string.risk2group);
                break;
            case R.id.button10:
                indexAnInt = 2;
                risk3ImageView.setImageResource(R.drawable.mytrue);
                riskString = getResources().getString(R.string.risk3group);
                break;
            case R.id.button11:
                indexAnInt = 3;
                risk4ImageView.setImageResource(R.drawable.mytrue);
                riskString = getResources().getString(R.string.risk4group);
                break;
            case R.id.button12:
                indexAnInt = 4;
                risk5ImageView.setImageResource(R.drawable.mytrue);
                riskString = getResources().getString(R.string.risk5group);
                break;
            case R.id.button13:
                indexAnInt = 5;
                risk6ImageView.setImageResource(R.drawable.mytrue);
                riskString = getResources().getString(R.string.risk6group);
                break;
            case R.id.button14:
                indexAnInt = 6;
                risk7ImageView.setImageResource(R.drawable.mytrue);
                riskString = getResources().getString(R.string.risk7group);
                break;
            case R.id.button15:
                indexAnInt = 7;
                risk8ImageView.setImageResource(R.drawable.mytrue);
                riskString = getResources().getString(R.string.risk8group);
                break;
            case R.id.button16:
                indexAnInt = 8;
                risk9ImageView.setImageResource(R.drawable.mytrue);
                riskString = getResources().getString(R.string.risk9group);
                break;

        }   // switch

        chooseData();



    }   // onClick

    private void chooseData() {
        bolStatus = false;

        Intent intent = new Intent(ChooseRisk.this, CheckRiskActivity.class);
        intent.putExtra("User", userStrings);
        intent.putExtra("rickTABLE", nameTableStrings[indexAnInt]);
        intent.putExtra("risk", riskString);
        startActivity(intent);
    }

    private void chooseImage() {

        Log.d("11JuneV1", "Click ChooseImage");

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent
                , "Select Picture"), PICK_IMAGE);

        //chooseData();

    }   // chooseImage

}   // Main Class
