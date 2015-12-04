package ppm.uqac.com.geekproject.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import java.util.ArrayList;

import ppm.uqac.com.geekproject.geeklopedie.ItemContent;

/**
 * Created by Arnaud on 28/11/2015.
 */
public class ContentDatabase extends SQLiteOpenHelper {

    /**
     * Liste du contenu de la geeklopedie
     */
    private ArrayList<ItemContent> listContent;

    /**
     * Liste des videos de la geeklopedie
     */
    private ArrayList<ItemContent> listVideo;

    public ContentDatabase(Context context)
    {
        super(context, "GeekActivity.db", null, 1);
        listContent = new ArrayList<ItemContent>();
        listVideo = new ArrayList<ItemContent>();
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
    }

    public void onCreate(SQLiteDatabase db)
    {
        // Table relative aux contenus de la Geeklopédie
        db.execSQL("DROP TABLE IF EXISTS geek_content");
        //
        db.execSQL("CREATE TABLE IF NOT EXISTS geek_content(" +
                "number_content INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name string, " +
                "description string, " +
                "url string " +
                ")");

        // Table relative aux vidéos présentes dans la Geeklopédie
        db.execSQL("DROP TABLE IF EXISTS geek_video");
        db.execSQL("CREATE TABLE IF NOT EXISTS geek_video(" +
                "number_content INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name string, " +
                "description string, " +
                "url string " +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS geek_content");
        onCreate(db);
    }

    /**
     * Ajout d'une video dans la BDD
     * @param c
     */
    public void addVideo(ItemContent c)
    {
        this.getWritableDatabase().execSQL("INSERT INTO geek_video (name, description,url) VALUES ('" +
                c.get_name() + "','" +
                c.get_description() + "','" +
                c.get_url() +
                "')");
        this.getWritableDatabase().close();
    }

    /**
     * Récupération des vidéos
     * @return une ArrayList de ItemContent
     */
    public ArrayList<ItemContent> getVideo()
    {
        //listActivities.clear();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor activitiesSaved = db.rawQuery("SELECT * FROM geek_video", null);

        String name;
        String description;
        String url;

        activitiesSaved.moveToFirst();
        System.out.println("movetofirst " + activitiesSaved.getPosition());
        activitiesSaved.moveToLast();

        for(activitiesSaved.moveToFirst(); !activitiesSaved.isAfterLast(); activitiesSaved.moveToNext())
        {
            name = activitiesSaved.getString(1);
            description = activitiesSaved.getString(2);
            url = activitiesSaved.getString(3);

            ItemContent c = new ItemContent(name,description,url);

            listVideo.add(c);
            System.out.println("nom vidéo " + c.get_name());
        }
        System.out.println("Videos " + listVideo.get(0).get_name());

        activitiesSaved.close();
        db.close();
        return listVideo;
    }

    /**
     * Ajout d'un contenu dans la BDD
     * @param c
     */
    public void addContent(ItemContent c)
    {
        this.getWritableDatabase().execSQL("INSERT INTO geek_content (name, description,url) VALUES ('" +
                c.get_name() + "','" +
                c.get_description() + "','" +
                c.get_url() +
                "')");
        this.getWritableDatabase().close();
    }

    /**
     * Récupération du contenu
     * @return une ArrayList de ItemContent
     */
    public ArrayList<ItemContent> getContent()
    {
        //listActivities.clear();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor activitiesSaved = db.rawQuery("SELECT * FROM geek_content", null);

        String name;
        String description;
        String url;

        System.out.println("test");

        activitiesSaved.moveToFirst();
        System.out.println("movetofirst " + activitiesSaved.getPosition());
        activitiesSaved.moveToLast();

        for(activitiesSaved.moveToFirst(); !activitiesSaved.isAfterLast(); activitiesSaved.moveToNext())
        {
            name = activitiesSaved.getString(1);
            description = activitiesSaved.getString(2);
            url = activitiesSaved.getString(3);

            ItemContent c = new ItemContent(name,description,url);

            listContent.add(c);
            System.out.println("non activité " + c.get_name());
        }
        System.out.println("Activités " + listContent.get(0).get_name());

        activitiesSaved.close();
        db.close();
        return listContent;
    }
}