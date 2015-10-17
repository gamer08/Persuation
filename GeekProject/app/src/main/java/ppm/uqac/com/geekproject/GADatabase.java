package ppm.uqac.com.geekproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import java.security.PublicKey;
import java.util.ArrayList;

/**
 * Created by Arnaud on 15/10/2015.
 * Cette classe gère les interactions avec la base de données des GeekActivity
 */
public class GADatabase extends SQLiteOpenHelper {




    private ArrayList<GA> listActivities;
    /**
     * Constructeur de la base de donnée des GeekActivity
     * @param context : paramètre obligatoire
     */
    public GADatabase(Context context)
    {
        super(context, "GeekActivity.db", null, 1);
        listActivities = new ArrayList<GA>();
        System.out.println("bdd geek_activity créée");
        //juste pour avoir moins de ligne
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS geek_activity");
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
    }

    /**
     * Création de la table GeekActivity
     * @param db : correspond à la base de donnée
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //juste pour avoir moins de ligne
        db.execSQL("DROP TABLE IF EXISTS geek_activity");
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS geek_activity");
        onCreate(db);
    }

    public void addActivity(GA activity)
    {
        boolean isDone = activity.get_isDone();
        int done;
        if (isDone == false)
            done = 0;
        else
            done = 1;



        this.getWritableDatabase().execSQL("INSERT INTO geek_activity (title, description, level, experience, is_done) VALUES ('" +
                activity.get_name() + "','" +
                activity.get_description() + "','" +
                activity.get_level() + "','" +
                activity.get_experience() + "','" +
                done + "')");
        this.getWritableDatabase().close();
        System.out.println("Activite " + activity.get_name() + " ajoutée");
    }

    public ArrayList<GA> getActivities()
    {
        //listActivities.clear();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor activitiesSaved = db.rawQuery("SELECT * FROM geek_activity", null);

        String nameActivity;
        String descriptionActivity;
        int levelActivity;
        int experienceActivity;
        int done;
        boolean isDone;
        System.out.println("test");

        activitiesSaved.moveToFirst();
        System.out.println("movetofirst " + activitiesSaved.getPosition());

        activitiesSaved.moveToLast();

        for(activitiesSaved.moveToFirst(); !activitiesSaved.isAfterLast(); activitiesSaved.moveToNext())
            {
            nameActivity = activitiesSaved.getString(1);
            descriptionActivity = activitiesSaved.getString(2);
            levelActivity = activitiesSaved.getInt(3);
            experienceActivity = activitiesSaved.getInt(4);
            done = activitiesSaved.getInt(5);
            if(done == 0)
                isDone=false;
            else
                isDone=true;

            GA activity = new GA(nameActivity, descriptionActivity, levelActivity, experienceActivity, isDone);
            listActivities.add(activity);
            System.out.println("non activité " + activity.get_name());
        }
        System.out.println("Activités " + listActivities.get(0).get_name());

        db.close();
        return listActivities;

    }

}
