package ppm.uqac.com.geekproject.geekactivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import java.util.ArrayList;

import ppm.uqac.com.geekproject.geeklopedie.Content;
import ppm.uqac.com.geekproject.profile.Profile;

/**
 * Created by Arnaud on 15/10/2015.
 * Cette classe gère les interactions avec la base de données des GeekActivity
 */
public class GADatabase extends SQLiteOpenHelper {


    /**
     * Liste d'activité contenue dans la base de données
     */
    private ArrayList<GA> listActivities;

    /**
     * Liste du contenu de la geeklopedie
     */
    private ArrayList<Content> listContent;

    /**
     * Liste des videos de la geeklopedie
     */
    private ArrayList<Content> listVideo;

    /**
     * Constructeur de la base de donnée des GeekActivity
     * Créé la base geek_activity.db et de la table geek_activity
     * @param context : paramètre obligatoire
     */
    public GADatabase(Context context)
    {
        super(context, "GeekActivity.db", null, 1);
        listActivities = new ArrayList<GA>();
        System.out.println("bdd geek_activity créée");
        listContent = new ArrayList<Content>();
        listVideo = new ArrayList<Content>();
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
    }

    /**
     * Méthode de création de la table GeekActivity
     * @param db : correspond à la base de donnée
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //juste pour avoir moins de ligne
        //db.execSQL("DROP TABLE IF EXISTS geek_activity");
        //
        db.execSQL("CREATE TABLE IF NOT EXISTS geek_activity(" +
                "number_activity INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title string, " +
                "description string, " +
                "level INTEGER, " +
                "experience INTEGER, " +
                "is_done INTEGER" +
                ")");
        System.out.println("table geek_activity créée");

        db.execSQL("DROP TABLE IF EXISTS geek_content");
        //
        db.execSQL("CREATE TABLE IF NOT EXISTS geek_content(" +
                "number_content INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name string, " +
                "description string, " +
                "url string " +
                ")");

        System.out.println("table geek_content créée");

        db.execSQL("DROP TABLE IF EXISTS geek_video");
        db.execSQL("CREATE TABLE IF NOT EXISTS geek_video(" +
                "number_content INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name string, " +
                "description string, " +
                "url string " +
                ")");

        System.out.println("table geek_video créée");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS geek_activity");
        onCreate(db);
    }

    /**
     * Ajout d'une activité dans la BDD
     * @param activity
     */
    public void addActivity(GA activity)
    {

        int done =0;
        this.getWritableDatabase().execSQL("INSERT INTO geek_activity (title, description, level, experience, is_done) VALUES ('" +
                activity.get_name() + "','" +
                activity.get_description() + "','" +
                activity.get_level() + "','" +
                activity.get_experience() + "','" +
                done + "')");
        this.getWritableDatabase().close();
        System.out.println("Activite " + activity.get_name() + " ajoutée");
    }

    /**
     * Récupération de la liste des activités que l'utilisateur est en train de réaliser
     * @return une ArrayList de GA
     */
    public ArrayList<GA> getActivitiesDoing(int levelProfile)
    {

        listActivities.clear();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor activitiesSaved = db.rawQuery("SELECT * FROM geek_activity", null);

        String nameActivity;
        String descriptionActivity;
        int levelActivity;
        int experienceActivity;
        int done;
        boolean isDone = true;


        activitiesSaved.moveToFirst();


        activitiesSaved.moveToLast();

        for(activitiesSaved.moveToFirst(); !activitiesSaved.isAfterLast(); activitiesSaved.moveToNext()) {
            if(activitiesSaved.getInt(3)<= levelProfile)
            {
                int numActivity = activitiesSaved.getInt(0);
                System.out.println("GADatabase : " + numActivity);
                nameActivity = activitiesSaved.getString(1);
                descriptionActivity = activitiesSaved.getString(2);
                levelActivity = activitiesSaved.getInt(3);
                experienceActivity = activitiesSaved.getInt(4);
                done = activitiesSaved.getInt(5);
                System.out.println("GADatabase Bool = " + done);
                if (done == 0) {
                    isDone = false;
                    GA activity = new GA(nameActivity, descriptionActivity, levelActivity, experienceActivity, isDone);
                    listActivities.add(activity);
                }

            }
        }

        db.close();
        return listActivities;

    }

    public ArrayList<GA> getActivitiesDone()
    {

        listActivities.clear();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor activitiesSaved = db.rawQuery("SELECT * FROM geek_activity", null);

        String nameActivity;
        String descriptionActivity;
        int levelActivity;
        int experienceActivity;
        int done;
        boolean isDone = false;


        activitiesSaved.moveToFirst();
        activitiesSaved.moveToLast();

        for(activitiesSaved.moveToFirst(); !activitiesSaved.isAfterLast(); activitiesSaved.moveToNext()) {
            int numActivity = activitiesSaved.getInt(0);
            System.out.println("GADatabase : " + numActivity);
            nameActivity = activitiesSaved.getString(1);
            descriptionActivity = activitiesSaved.getString(2);
            levelActivity = activitiesSaved.getInt(3);
            experienceActivity = activitiesSaved.getInt(4);
            done = activitiesSaved.getInt(5);
            System.out.println("GADatabase Bool = " + done);
            if (done == 1)
            {
                isDone = true;
                GA activity = new GA(nameActivity, descriptionActivity, levelActivity, experienceActivity, isDone);
                listActivities.add(activity);
            }
        }

        db.close();
        return listActivities;
    }

    public void updateActivity(GA activity)
    {
        this.getWritableDatabase().execSQL("UPDATE geek_activity " +
                "SET is_done = '1' " +
                "WHERE title = '" + activity.get_name() + "'");
        this.getWritableDatabase().close();
        System.out.println("GADatabase : Activity" + activity.get_name() + "done");
    }
    /**
     * Ajout d'une video dans la BDD
     * @param c
     */
    public void addVideo(Content c)
    {
        this.getWritableDatabase().execSQL("INSERT INTO geek_video (name, description,url) VALUES ('" +
                c.get_name() + "','" +
                c.get_description() + "','" +
                c.get_url()+
                "')");
        this.getWritableDatabase().close();
        System.out.println("Video " + c.get_name() + " ajoutée");
    }

    /**
     * Récupération des vidéos
     * @return une ArrayList de Content
     */
    public ArrayList<Content> getVideo()
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

            Content c = new Content(name,description,url);

            listVideo.add(c);
            System.out.println("nom vidéo " + c.get_name());
        }
        System.out.println("Videos " + listVideo.get(0).get_name());

        db.close();
        return listVideo;
    }

    /**
     * Ajout d'une activité dans la BDD
     * @param c
     */
    public void addContent(Content c)
    {
        this.getWritableDatabase().execSQL("INSERT INTO geek_content (name, description,url) VALUES ('" +
                c.get_name() + "','" +
                c.get_description() + "','" +
                c.get_url()+
                "')");
        this.getWritableDatabase().close();
        System.out.println("Content " + c.get_name() + " ajouté");
    }

    /**
     * Récupération du contenu
     * @return une ArrayList de Content
     */
    public ArrayList<Content> getContent()
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

            Content c = new Content(name,description,url);

            listContent.add(c);
            System.out.println("non activité " + c.get_name());
        }
        System.out.println("Activités " + listContent.get(0).get_name());

        db.close();
        return listContent;
    }


}
