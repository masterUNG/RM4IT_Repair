package appewtc.masterung.rm4it;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private MyManage myManage;
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;
    private String[] resultStrings, nameTableStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind Widget
        bindWidget();

        //Request database
        myManage = new MyManage(this);


        //Setup Name Table
        setupNameTable();

        //Tester Add Value
        //testerAdd();

        //Delete All SQLite
        deleteAllSQLite();

        //Syn JSON to SQLite
        synJSONtoSQLite();

    }   // Main Method

    private void setupNameTable() {
        nameTableStrings = new String[11];
        nameTableStrings[0] = "userTABLE";
        nameTableStrings[1] = "correctTABLE";
        nameTableStrings[2] = "environmentTABLE";
        nameTableStrings[3] = "governanceTABLE";
        nameTableStrings[4] = "internetTABLE";
        nameTableStrings[5] = "moneyTABLE";
        nameTableStrings[6] = "network_intrusionTABLE";
        nameTableStrings[7] = "server_networkTABLE";
        nameTableStrings[8] = "virusTABLE";
        nameTableStrings[9] = "wiless_networkTABLE";
        nameTableStrings[10] = "checkTABLE";
    }

    private void bindWidget() {
        userEditText = (EditText) findViewById(R.id.editText);
        passwordEditText = (EditText) findViewById(R.id.editText2);
    }


    public void clickSignInMain(View view) {

        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        //Check Space
        if (userString.equals("") || passwordString.equals("")) {
            //Have Space
            MyAlertDialog myAlertDialog = new MyAlertDialog();
            myAlertDialog.MyDialog(MainActivity.this,
                    "มีช่องว่าง", "กรุณากรอกให้ครบทุกช่อง คะ");
        } else {
            //No Space
            checkUser();
           // Intent intent = new Intent(MainActivity.this, SignInActivity.class);
            //intent.putExtra("Result", resultStrings);
            //startActivity(intent);
            //finish();
        }


    }   // clickSignInMain

    private void checkUser() {

        try {

            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                    MODE_PRIVATE, null);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM userTABLE WHERE User = " + "'" + userString + "'", null);
            cursor.moveToFirst();
            resultStrings = new String[cursor.getColumnCount()];
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                resultStrings[i] = cursor.getString(i);
            }   //for

            //Check Password
            if (passwordString.equals(resultStrings[2])) {
                //Password True
                Toast.makeText(MainActivity.this, "ยินดีต้อนรับ " + resultStrings[3],
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                intent.putExtra("Result", resultStrings);
                startActivity(intent);
                finish();

            } else {
                //Password False
                MyAlertDialog myAlertDialog = new MyAlertDialog();
                myAlertDialog.MyDialog(MainActivity.this, "Password ผิด",
                        "กรุณาพิมพ์ ใหม่ Password ผิด");
            }


        } catch (Exception e) {
            MyAlertDialog myAlertDialog = new MyAlertDialog();
            myAlertDialog.MyDialog(MainActivity.this, "ไม่มี User",
                    "ไม่มี " + userString + " ใน ฐานข้อมูลของเรา");
        }

    }   // checkUser


    @Override
    protected void onRestart() {
        super.onRestart();

        deleteAllSQLite();
        synJSONtoSQLite();

    }

    private void synJSONtoSQLite() {

        //Connected Http
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy
                .Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        int intTABLE = 0;
        while (intTABLE <= 9) {

            //1 Create Input Stream
            InputStream inputStream = null;

            String[] urlStrings = new String[10];
            urlStrings[0] = "http://swiftcodingthai.com/rm4it/php_get_user.php";
            urlStrings[1] = "http://swiftcodingthai.com/rm4it/php_get_correct.php";
            urlStrings[2] = "http://swiftcodingthai.com/rm4it/php_get_environment.php";
            urlStrings[3] = "http://swiftcodingthai.com/rm4it/php_get_governance.php";
            urlStrings[4] = "http://swiftcodingthai.com/rm4it/php_get_internet.php";
            urlStrings[5] = "http://swiftcodingthai.com/rm4it/php_get_money.php";
            urlStrings[6] = "http://swiftcodingthai.com/rm4it/php_get_network_intrusion.php";
            urlStrings[7] = "http://swiftcodingthai.com/rm4it/php_get_server_network.php";
            urlStrings[8] = "http://swiftcodingthai.com/rm4it/php_get_virus.php";
            urlStrings[9] = "http://swiftcodingthai.com/rm4it/php_get_wiless_network.php";

            String tag = "Rm4it";



            try {

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(urlStrings[intTABLE]);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                inputStream = httpEntity.getContent();

            } catch (Exception e) {
                Log.d(tag, "Input ==> " + e.toString());
            }

            //2 Create JSON String
            String strJSON = null;
            try {

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuilder stringBuilder = new StringBuilder();
                String strLine = null;

                while ((strLine = bufferedReader.readLine()) != null) {
                    stringBuilder.append(strLine);
                }   // while
                inputStream.close();
                strJSON = stringBuilder.toString();

            } catch (Exception e) {
                Log.d(tag, "strJSON ==> " + e.toString());
            }


            //3 Update SQLite
            try {

                JSONArray jsonArray = new JSONArray(strJSON);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    if (intTABLE == 0) {
                        //userTABlE

                        String strUser = jsonObject.getString(MyManage.column_User);
                        String strPassword = jsonObject.getString(MyManage.column_Password);
                        String strName = jsonObject.getString(MyManage.column_Name);
                        String strIDcard = jsonObject.getString(MyManage.column_ID_card);
                        String strProvince = jsonObject.getString(MyManage.column_Province);
                        String strPosition = jsonObject.getString(MyManage.column_Position);
                        String strWorkYear = jsonObject.getString(MyManage.column_Work_Year);
                        String strEmail = jsonObject.getString(MyManage.column_Email);

                        myManage.addUser(strUser, strPassword, strName, strIDcard, strProvince,
                                strPosition, strWorkYear, strEmail);

                    } else {

                        String strName1 = jsonObject.getString(MyManage.column_Name);

                        myManage.addRisk(nameTableStrings[intTABLE], strName1);

                    }

                }   //for

            } catch (Exception e) {
                Log.d(tag, "Update ==> " + e.toString());
            }

            //This is END

            intTABLE += 1;
        }   // while

    }   // synJSONtoSQLite

    private void deleteAllSQLite() {

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        for (int i = 0; i < nameTableStrings.length; i++) {
            sqLiteDatabase.delete(nameTableStrings[i], null, null);
        }

    }   // deleteAllSQLite

    private void testerAdd() {

        myManage.addUser("user", "pass", "name", "123", "BKK", "position", "3", "abc@gmail.com");

    }   // testerAdd

    public void clickSignUp(View view) {

        startActivity(new Intent(MainActivity.this, SignUpActivity.class));
    }


}   // Main Class
