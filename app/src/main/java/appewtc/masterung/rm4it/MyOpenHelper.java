package appewtc.masterung.rm4it;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by masterUNG on 2/17/16 AD.
 */
public class MyOpenHelper extends SQLiteOpenHelper {

    //Explicit
    public static final String database_name = "rm4it.db";
    private static final int database_version = 1;
    private static final String create_userTABLE = "create table userTABLE (" +
            "_id integer primary key, " +
            "User text, " +
            "Password text, " +
            "Name text, " +
            "ID_card text, " +
            "Province text, " +
            "Position text, " +
            "Work_Year text, " +
            "Email text);";

    private static final String create_correct = "create table correctTABLE (" +
            "_id integer primary key, " +
            "Name text);";

    private static final String create_environment = "create table environmentTABLE (" +
            "_id integer primary key, " +
            "Name text);";

    private static final String create_governance = "create table governanceTABLE (" +
            "_id integer primary key, " +
            "Name text);";

    private static final String create_internet = "create table internetTABLE (" +
            "_id integer primary key, " +
            "Name text);";

    private static final String create_money = "create table moneyTABLE (" +
            "_id integer primary key, " +
            "Name text);";

    private static final String create_network_intrusion = "create table network_intrusionTABLE (" +
            "_id integer primary key, " +
            "Name text);";

    private static final String create_server_network = "create table server_networkTABLE (" +
            "_id integer primary key, " +
            "Name text);";

    private static final String create_virus = "create table virusTABLE (" +
            "_id integer primary key, " +
            "Name text);";

    private static final String create_wiless_network = "create table wiless_networkTABLE (" +
            "_id integer primary key, " +
            "Name text);";

    private static final String create_checkTABLE = "create table checkTABLE (" +
            "_id integer primary key," +
            "NameUser text," +
            "ProvinceUser text," +
            "Date text," +
            "correctTABLE text, " +
            "environmentTABLE text, " +
            "governanceTABLE text, " +
            "internetTABLE text, " +
            "moneyTABLE text, " +
            "network_intrusionTABLE text, " +
            "server_networkTABLE text, " +
            "virusTABLE text, " +
            "wiless_networkTABLE text, " +
            "Total text);";


    public MyOpenHelper(Context context) {
        super(context, database_name, null, database_version);
    }   // Constructor

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(create_userTABLE);
        sqLiteDatabase.execSQL(create_correct);
        sqLiteDatabase.execSQL(create_environment);
        sqLiteDatabase.execSQL(create_governance);
        sqLiteDatabase.execSQL(create_internet);
        sqLiteDatabase.execSQL(create_money);
        sqLiteDatabase.execSQL(create_network_intrusion);
        sqLiteDatabase.execSQL(create_server_network);
        sqLiteDatabase.execSQL(create_virus);
        sqLiteDatabase.execSQL(create_wiless_network);
        sqLiteDatabase.execSQL(create_checkTABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}   // Main Class
