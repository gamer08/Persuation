package ppm.uqac.com.geekproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements GA_Dialog.dialogDoneListener{
    private TextView _FirstName;
    private TextView _LastName;
    private TextView _Type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //test pour afficher nom de la personne connecté
        _FirstName = (TextView) findViewById(R.id.TV_FirstName);
        _LastName = (TextView) findViewById(R.id.TV_LastName);
        _Type = (TextView) findViewById(R.id.TV_Type);

        Intent intent = getIntent();
        if (intent != null) {
            String firstName = (String) intent.getSerializableExtra("firstName");
            _FirstName.setText(firstName);
            String lastName = (String) intent.getSerializableExtra("lastName");
            _LastName.setText(lastName);
            String type = (String) intent.getSerializableExtra("type");
            _Type.setText(type);

            String previousActivity = (String) intent.getSerializableExtra("activite");

            if(previousActivity.toString().equals("CreateProfil"))
            {
                //test dialog
                GA_Dialog myDiag=new GA_Dialog();
                myDiag.show(getFragmentManager(),"Diag");
            }
            else
            {
                System.out.println(previousActivity.toString());
            }
        }





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    public void onClickProfil(View v)
    {
        Intent intent = new Intent(this,ViewProfileActivity.class);
        startActivity(intent);
    }

    public void onClickActivites(View v)
    {
        Intent intent = new Intent(this,ViewListActivity.class);
        startActivity(intent);
    }

    public void onClickContent(View v)
    {
        Intent intent = new Intent(this,ViewContentActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed()
    {
        System.out.println("Bouton retour");
    }

    /*
    Méthode pour gérer l'action de la fenetre de dialogue
     */
    @Override
    public void onDone(boolean state) {
        Intent intent = new Intent(this,ViewListActivity.class);
        startActivity(intent);
    }
}
