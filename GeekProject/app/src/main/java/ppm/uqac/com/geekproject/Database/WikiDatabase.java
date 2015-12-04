package ppm.uqac.com.geekproject.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.HashMap;

import ppm.uqac.com.geekproject.geeklopedie.ItemWiki;
import ppm.uqac.com.geekproject.profile.Badge;

/**
 * Created by Valentin on 02/12/2015.
 */
public class WikiDatabase extends SQLiteOpenHelper {

    ArrayList<ItemWiki> _words;

    public WikiDatabase(Context context)
    {
        super(context, "GeekActivity.db", null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Création de la BDD pour les badges
        db.execSQL("DROP TABLE IF EXISTS geek_wiki");
        db.execSQL("CREATE TABLE IF NOT EXISTS geek_wiki(" +
                "name string, " +
                "definition string " +
                ")");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS geek_wiki");
        onCreate(db);
    }

    public void addWord(ItemWiki i) {
        this.getWritableDatabase().execSQL("INSERT INTO geek_wiki (name, definition) VALUES ('" +
               i.getName() + "','" +
                i.getDefinition() +
                "')");
        this.getWritableDatabase().close();
    }


    /**
     * Récupération des badges
     *
     * @return une ArrayList de Badges
     */
    public ArrayList<ItemWiki> getWords() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM geek_wiki", null);

        String name;
        String definition;


        cursor.moveToFirst();
        cursor.moveToLast();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            name = cursor.getString(1);
            definition = cursor.getString(2);

            _words.add(new ItemWiki(name, definition));
        }


        cursor.close();
        db.close();
        return _words;
    }




}