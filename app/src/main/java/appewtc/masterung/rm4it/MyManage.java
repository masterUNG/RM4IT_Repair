package appewtc.masterung.rm4it;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by masterUNG on 2/17/16 AD.
 */
public class MyManage {

    //Explicit
    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase writeSqLiteDatabase, readSqLiteDatabase;

    public static final String table_user = "userTABLE";
    public static final String column_id = "_id";
    public static final String column_User = "User";
    public static final String column_Password = "Password";
    public static final String column_Name = "Name";
    public static final String column_ID_card = "ID_card";
    public static final String column_Province = "Province";
    public static final String column_Position = "Position";
    public static final String column_Work_Year = "Work_Year";
    public static final String column_Email = "Email";

    public MyManage(Context context) {

        //Connected Database
        myOpenHelper = new MyOpenHelper(context);
        writeSqLiteDatabase = myOpenHelper.getWritableDatabase();
        readSqLiteDatabase = myOpenHelper.getReadableDatabase();

    }   // Constructor

    public long addCheckRisk(String strNameUser,
                             String strProvinceUser,
                             String strDate
    ) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("NameUser", strNameUser);
        contentValues.put("ProvinceUser", strProvinceUser);
        contentValues.put("Date", strDate);
        contentValues.put("correctTABLE", "0");
        contentValues.put("environmentTABLE", "0");
        contentValues.put("governanceTABLE", "0");
        contentValues.put("internetTABLE", "0");
        contentValues.put("moneyTABLE", "0");
        contentValues.put("network_intrusionTABLE", "0");
        contentValues.put("server_networkTABLE", "0");
        contentValues.put("virusTABLE", "0");
        contentValues.put("wiless_networkTABLE", "0");
        contentValues.put("Total", "0");

        return writeSqLiteDatabase.insert("checkTABLE", null, contentValues);
    }


    public long addRisk(String strTABLE,
                        String strName) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(column_Name, strName);

        return writeSqLiteDatabase.insert(strTABLE, null, contentValues);
    }

    public long addUser(String strUser,
                        String strPassword,
                        String strName,
                        String strIDcard,
                        String strProvince,
                        String strPosition,
                        String strWorkYear,
                        String strEmail) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(column_User, strUser);
        contentValues.put(column_Password, strPassword);
        contentValues.put(column_Name, strName);
        contentValues.put(column_ID_card, strIDcard);
        contentValues.put(column_Province, strProvince);
        contentValues.put(column_Position, strPosition);
        contentValues.put(column_Work_Year, strWorkYear);
        contentValues.put(column_Email, strEmail);

        return writeSqLiteDatabase.insert(table_user, null, contentValues);
    }

}   // Main Class
