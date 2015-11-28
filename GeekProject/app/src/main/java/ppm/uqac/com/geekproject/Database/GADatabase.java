package ppm.uqac.com.geekproject.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.graphics.Point;

import java.util.ArrayList;

import ppm.uqac.com.geekproject.geekactivity.GA;
import ppm.uqac.com.geekproject.profile.Badge;

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
     * Liste des badges
     */

    private ArrayList<Badge> listBadges;


    /**
     * Liste des questionnaires
     */

    private ArrayList<Point> listQuestionnaries;
    /**
     * Constructeur de la base de donnée des GeekActivity
     * Créé la base geek_activity.db et de la table geek_activity
     * @param context : paramètre obligatoire
     */
    public GADatabase(Context context)
    {
        super(context, "GeekActivity.db", null, 1);
        listActivities = new ArrayList<GA>();
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


        db.execSQL("DROP TABLE IF EXISTS geek_badges");
        db.execSQL("CREATE TABLE IF NOT EXISTS geek_badges(" +
                "number_content INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name string, " +
                "description string, " +
                "image int " +
                ")");

        db.execSQL("DROP TABLE IF EXISTS geek_questionnaries");
        db.execSQL("CREATE TABLE IF NOT EXISTS geek_questionnaries(" +
                "number_content INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "number int, " +
                "result int " +
                ")");

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
    public void addActivity(GA activity) {

        int done = 0;
        this.getWritableDatabase().execSQL("INSERT INTO geek_activity (title, description, level, experience, is_done) VALUES ('" +
                activity.get_name() + "','" +
                activity.get_description() + "','" +
                activity.get_level() + "','" +
                activity.get_experience() + "','" +
                done + "')");
        this.getWritableDatabase().close();
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


}
