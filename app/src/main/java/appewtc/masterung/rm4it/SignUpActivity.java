package appewtc.masterung.rm4it;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {

    //Explicit
    private EditText nameEditText, idCardEditText, positionEditText,
            workYearEditText, emailEditText;
    private Spinner provinceSpinner;
    private String nameString, idCardString, positionString, workYearString,
            emailString, provinceString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Bind Widget
        bindWidget();

        //Create Spinner
        createSpinner();

    }   // Main Method

    private void createSpinner() {

        final String[] provinceStrings = getResources().getStringArray(R.array.province);
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, provinceStrings);
        provinceSpinner.setAdapter(stringArrayAdapter);

        provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                provinceString = provinceStrings[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                provinceString = provinceStrings[0];
            }
        });

    }   // createSpinner

    public void clickCancel(View view){
        finish();
    }
    public void clickSaveData(View view) {

        nameString = nameEditText.getText().toString().trim();
        idCardString = idCardEditText.getText().toString().trim();
        positionString = positionEditText.getText().toString().trim();
        workYearString = workYearEditText.getText().toString().trim();
        emailString = emailEditText.getText().toString().trim();

        //Check Space
        if (checkSpace()) {
            //Have Space
            MyAlertDialog myAlertDialog = new MyAlertDialog();
            myAlertDialog.MyDialog(SignUpActivity.this, "มีช่องว่าง", "กรุณากรอกทุกช่อง คะ");

        } else {


           if(!checkAvailableProvince(provinceString)){
               Log.e("___result___",provinceString);
               //No Space
               updateValueToMySQL();
           }else{
              //Exising Province , doesn't insert data,not sign in

               AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
               builder1.setMessage("คุณไม่สามารถเพิ่มจังหวัดที่ซ้ำกับในระบบได้");
               builder1.setCancelable(true);


               builder1.setNegativeButton(
                       "OK",
                       new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {
                               dialog.cancel();
                           }
                       });

               AlertDialog alert11 = builder1.create();
               alert11.show();

           }


        }   // if

    }   // clickSaveData


    private boolean checkAvailableProvince(String provinceName){

        try {

            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpPost = new HttpGet("http://swiftcodingthai.com/rm4it/php_get_user_master.php");
            HttpResponse response = httpClient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity());

            JSONArray ja = new JSONArray(result);
            int n = ja.length();
            for (int i = 0; i < n; i++) {
                // GET INDIVIDUAL JSON OBJECT FROM JSON ARRAY
                JSONObject jo = ja.getJSONObject(i);
                // RETRIEVE EACH JSON OBJECT'S FIELDS
                String province = jo.getString("Province");

                if(province.toLowerCase().trim().equals(provinceName.toLowerCase().trim())){
                    Log.e("___is available__",provinceName);
                    return true;
                }else{


                }

            }
        }catch(Exception ex){
             Log.e("__getProvince Error__",ex.toString());
            return  false;
        }
        return false;
    }
    private void updateValueToMySQL() {

        //Permission
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy
                .Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        try {

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("isAdd", "true"));
            nameValuePairs.add(new BasicNameValuePair(MyManage.column_Name, nameString));
            nameValuePairs.add(new BasicNameValuePair(MyManage.column_ID_card, idCardString));
            nameValuePairs.add(new BasicNameValuePair(MyManage.column_Province, provinceString));
            nameValuePairs.add(new BasicNameValuePair(MyManage.column_Position, positionString));
            nameValuePairs.add(new BasicNameValuePair(MyManage.column_Work_Year, workYearString));
            nameValuePairs.add(new BasicNameValuePair(MyManage.column_Email, emailString));

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://swiftcodingthai.com/rm4it/php_add_user_master.php");
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            httpClient.execute(httpPost);

            Toast.makeText(SignUpActivity.this, "บันทึกข้อมูลเรียบร้อย แล้ว", Toast.LENGTH_SHORT).show();
            finish();


        } catch (Exception e) {
            MyAlertDialog myAlertDialog = new MyAlertDialog();
            myAlertDialog.MyDialog(SignUpActivity.this, "ไม่สามารถอัพข้อมูลได้", "ลองตรวจสอบว่า ต่อ Internet หรือเปล่า ?");
        }

    }   // updateValueToMySQL

    private boolean checkSpace() {

        boolean bolResult = true;

        bolResult = nameString.equals("") || idCardString.equals("") ||
                positionString.equals("") || workYearString.equals("") ||
                emailString.equals("");

        return bolResult;
    }


    private void bindWidget() {

        nameEditText = (EditText) findViewById(R.id.editText3);
        idCardEditText = (EditText) findViewById(R.id.editText4);
        positionEditText = (EditText) findViewById(R.id.editText6);
        workYearEditText = (EditText) findViewById(R.id.editText7);
        emailEditText = (EditText) findViewById(R.id.editText8);
        provinceSpinner = (Spinner) findViewById(R.id.spinner);

    }   // bindWidget

}   // Main Class
