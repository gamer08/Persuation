package ppm.uqac.com.geekproject.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import ppm.uqac.com.geekproject.geeklopedie.ItemWiki;

/**
 * Created by Valentin on 02/12/2015.
 */
public class WikiDatabase extends SQLiteOpenHelper {

    ArrayList<ItemWiki> _words;

    public WikiDatabase(Context context)
    {
        super(context, "GeekActivity.db", null, 1);
        _words = new ArrayList<ItemWiki>();
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);

        System.out.println("CONSTRUCTOR DE WIKIDB");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Création de la BDD  pour les item de wiki
        db.execSQL("CREATE TABLE IF NOT EXISTS geek_wiki(" +
                "name string, " +
                "definition string" +
                ")");
        System.out.println("onCREATE DE WIKIDB");
        getWords();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        System.out.println("UPGRADE DE WIKIDB");
        getWords();
        db.execSQL("DROP TABLE IF EXISTS geek_wiki");
        onCreate(db);
    }

    public void addWord(ItemWiki i)
    {
    this.getWritableDatabase().execSQL("INSERT INTO geek_wiki (name, definition) VALUES ('" +
               i.getName() + "','" +
                i.getDefinition() +
                "')");
        this.getWritableDatabase().close();
        System.out.println("UPGRADE DE WIKIDB");
        getWords();

    }


    /**
     * Récupération des mots du wiki
     *
     * @return une ArrayList d'ItemWiki
     */
    public ArrayList<ItemWiki> getWords() {

        _words.clear();
        System.out.println("RECUPERATION DES MOTS");
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM geek_wiki", null);

        String name;
        String definition;
        cursor.moveToFirst();
        System.out.println("Wiki DATABASE - cursor en 1ère position =  " + cursor.getPosition());
        cursor.moveToLast();
        System.out.println("Wiki DATABASE - cursor en dernière position =  " + cursor.getPosition());

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            name = cursor.getString(0);
            definition = cursor.getString(1);
            System.out.println("Mot trouvé = " + name + " avec definition = " + definition);

            _words.add(new ItemWiki(name, definition));
        }
        cursor.close();
        db.close();
        return _words;
    }




}