package ppm.uqac.com.geekproject.Database;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;

import java.util.ArrayList;

import ppm.uqac.com.geekproject.R;
import ppm.uqac.com.geekproject.profile.Badge;

/**
 * Created by Arnaud on 28/11/2015.
 */
public class ProfileDatabase extends SQLiteOpenHelper {

    /**
     * Liste des badges
     */
    private ArrayList<Badge> listBadges;

    /**
     * Liste des questionnaires
     */
    private ArrayList<Point> listQuestionnaries;

    public ProfileDatabase(Context context)
    {
        super(context, "GeekActivity.db", null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
        listBadges = new ArrayList<Badge>();
        listQuestionnaries = new ArrayList<Point>();
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        // Création de la BDD pour les badges
        db.execSQL("CREATE TABLE IF NOT EXISTS geek_badges(" +
                "number_content INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name string, " +
                "description string, " +
                "image int, " +
                "got boolean" +
                ")");

        // Création de la BDD pour le résultat des questions
        db.execSQL("DROP TABLE IF EXISTS geek_questionnaries");
        db.execSQL("CREATE TABLE IF NOT EXISTS geek_questionnaries(" +
                "number_content INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "number int, " +
                "result int " +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS geek_activity");
        onCreate(db);
    }

    /**
     * Ajout d'un badge dans la BDD
     * @param b Badge à ajouter
     */
    public void addBadge(String n, String d, int i, boolean b)
    {

        System.out.println("Ajout d'un badge avec comme id = " + i);
        this.getWritableDatabase().execSQL("INSERT INTO geek_badges (name, description, image, got) VALUES ('" +
                n + "','" +
                d + "','" +
                i + "','" +
                b +
                "')");
        this.getWritableDatabase().close();
    }


    /**
     * Récupération des badges
     * @return une ArrayList de Badges
     */
    public ArrayList<Badge> getBadges()
    {

        listBadges.clear();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM geek_badges", null);

        int n=0;
        String name;
        String description;
        int image=0;
        String got;

        cursor.moveToFirst();
        System.out.println("movetofirst " + cursor.getPosition());
        cursor.moveToLast();

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {

            name = cursor.getString(1);
            System.out.println("Nom du badge =  " + name);
            description = cursor.getString(2);
            n = Integer.parseInt(cursor.getString(3));
            got = cursor.getString(4);

            switch (n)
            {
                case (0):
                    image = R.drawable.badge_newbie;

                    break;
                case (1):
                    image = R.drawable.badge_bronze;
                    break;
                case (2):
                    image = R.drawable.badge_silver;
                    break;
                case (3):
                    image = R.drawable.badge_gold;
                    break;
                case (4):
                    image = R.drawable.badge_sapphire;
                    break;
                case (5):
                    image = R.drawable.badge_ruby;
                    break;
                case(6):
                    image = R.drawable.badge_emerald;
                    break;
                case (7):
                    image = R.drawable.badge_amethyst;
                    break;

                default:
                    break;
            }


            Badge b = new Badge(name,description,image, Boolean.valueOf(got));

            listBadges.add(b);

        }

        cursor.close();
        db.close();
        return listBadges;
    }

    /**
     * Ajout d'un badge dans la BDD
     * @param p Résultat d'un questionnaire à rajouter
     */
    public void addQuestionary(Point p)
    {
        this.getWritableDatabase().execSQL("INSERT INTO geek_questionaries (number, result) VALUES ('" +
                p.x + "','" +
                p.y + "','" +
                "')");
        this.getWritableDatabase().close();
    }


    /**
     * Récupération des badges
     * @return une ArrayList de Points pour le graphique
     */
    public ArrayList<Point> getQuestionaries()
    {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM geek_questionaries", null);

        int number;
        int result;

        cursor.moveToFirst();
        System.out.println("movetofirst " + cursor.getPosition());
        cursor.moveToLast();

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
            number = Integer.parseInt(cursor.getString(1));
            result = Integer.parseInt(cursor.getString(2));

            Point p = new Point(number, result);

            listQuestionnaries.add(p);
            System.out.println("Numéro du questionnaire =  " + p.x);
        }
        System.out.println("Questionnaires " + listQuestionnaries.get(0).x);

        cursor.close();
        db.close();
        return listQuestionnaries;
    }


    public void gainBadge(Badge b)
    {

        System.out.println("GAIN BADGE IN PDB");
        this.getWritableDatabase().execSQL("UPDATE geek_badges " +
                "SET got = 'true' " +
                "WHERE name = '" + b.getName() + "'");
        this.getWritableDatabase().close();
        System.out.println("Obtention d'un badge " + b.getName() + " a ete obtenu " + b.isGot());
    }
}
