package appewtc.masterung.rm4it;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class InformationActivity extends AppCompatActivity {

    //Explicit
    private TextView nameTextView, positionTextView,
            ageWorksTextView, provinceTextView, emailTextView;

    private static final int REQUEST_CODE = 1;
    private Bitmap bitmap;
    Button button5;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        //Bind Widget
        nameTextView = (TextView) findViewById(R.id.textView12);
        positionTextView = (TextView) findViewById(R.id.textView13);
        ageWorksTextView = (TextView) findViewById(R.id.textView23);
        provinceTextView = (TextView) findViewById(R.id.textView24);
        emailTextView = (TextView) findViewById(R.id.textView25);
        imageView = (ImageView)findViewById(R.id.imageView2);

        button5 = (Button)findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //Receive from Intent
        //String[] resultStrings = getIntent().getStringArrayExtra("Result");
        String[] resultStrings =  getSharePreference();
        //Show View

        nameTextView.setText(resultStrings[3]);
        positionTextView.setText(resultStrings[6]);
        ageWorksTextView.setText(resultStrings[7] + " ปี");
        provinceTextView.setText(resultStrings[5]);
        emailTextView.setText(resultStrings[8]);

    }      //Main Method
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

    public void pickImage(View View) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK)
            try {
                // We need to recyle unused bitmaps
                if (bitmap != null) {
                    bitmap.recycle();
                }
                InputStream stream = getContentResolver().openInputStream(
                        data.getData());
                bitmap = BitmapFactory.decodeStream(stream);
                stream.close();
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        super.onActivityResult(requestCode, resultCode, data);
    }


}   // Main Class
