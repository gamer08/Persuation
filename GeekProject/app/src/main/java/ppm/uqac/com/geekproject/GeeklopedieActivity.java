package ppm.uqac.com.geekproject;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GeeklopedieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "Geeklopedie[CreationProfileA.onCreate", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_geeklopedie);



        // Accès a la bdd (ouverture)
        GADatabase db = new GADatabase(this);
        System.out.println("ouverture bd");

        InputStream is = getResources().openRawResource(
                getResources().getIdentifier("raw/contenu",
                        "raw", getPackageName()));

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        int d,u;

        try {
            while ((line = reader.readLine()) != null) {
        /*sb.append(line).append('\n');
        System.out.println("while "+sb.toString());*/
                System.out.println("index url: "+line.indexOf("name="));
                d=line.indexOf(";desc=");
                u=line.indexOf(";url=");
                Content contenu = new Content(line.substring(5,d),line.substring(d+6,u),line.substring(u+5));
                db.addContent(contenu);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


       /* // pour l'alpha on ajoute 3 contenu
        //Cette partie sera rmplacée plus tard
        Content c1 = new Content("Contenu 1","ceci est une image","http://google.fr");
        Content c2 = new Content("Contenu 2","ceci est une video","http://google.fr");
        Content c3 = new Content("Contenu 3","ceci est un son","http://google.fr");
        Content c4 = new Content("Contenu 4","ceci est une image","http://google.fr");
        Content c5 = new Content("Contenu 5","ceci est une video","http://google.fr");
        Content c6 = new Content("Contenu 6","ceci est un son","http://google.fr");
        Content c7 = new Content("Contenu 7","ceci est une image","http://google.fr");
        Content c8 = new Content("Contenu 8","ceci est une video","http://google.fr");
        Content c9 = new Content("Contenu 9","ceci est un son","http://google.fr");
        Content c10 = new Content("Contenu 10","ceci est un son","http://google.fr");
        db.addContent(c1);
        db.addContent(c2);
        db.addContent(c3);
        db.addContent(c4);
        db.addContent(c5);
        db.addContent(c6);
        db.addContent(c7);
        db.addContent(c8);
        db.addContent(c9);
        db.addContent(c10);*/

        //
        // Récupération de tout le contenu
        ArrayList<Content> List =  db.getContent();

        // On crée un adapter
        final ContentAdapter cAdapter = new ContentAdapter(this,List);

        // Récupération de la listView
        ListView ContentListView = (ListView) findViewById(R.id.ListView1);
        // Adaptation de la ListView avec notre Adapter
        ContentListView.setAdapter(cAdapter);

        ContentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Content c = cAdapter.getItem(position);
                Uri uri = Uri.parse(c.get_url()); // Si l'url ne contient pas http:// l'appli plante
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_geeklopedie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
