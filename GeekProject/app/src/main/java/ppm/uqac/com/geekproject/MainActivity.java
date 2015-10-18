package ppm.uqac.com.geekproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements GADialog.dialogDoneListener{
    private TextView _FirstName;
    private TextView _LastName;
    private TextView _Type;
    private ImageView _avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "MainA.onCreate", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_main);


        //test pour afficher nom de la personne connecté
        _FirstName = (TextView) findViewById(R.id.TV_FirstName);
        _LastName = (TextView) findViewById(R.id.TV_LastName);
        _Type = (TextView) findViewById(R.id.TV_Type);
        _avatar = (ImageView) findViewById(R.id.image);


        Intent intent = getIntent();
        if (intent != null) {
            String firstName = (String) intent.getSerializableExtra("firstName");
            _FirstName.setText(firstName);
            String lastName = (String) intent.getSerializableExtra("lastName");
            _LastName.setText(lastName);
            String type = (String) intent.getSerializableExtra("type");
            _Type.setText(type);
            Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.antigeek);
            Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, 480, 320, false);
            ImageView image = (ImageView) findViewById(R.id.image);
            _avatar.setImageBitmap(bMapScaled);

            String previousActivity = (String) intent.getSerializableExtra("activite");

            if(previousActivity.toString().equals("CreateProfil"))
            {
                //test dialog
                GADialog myDiag=new GADialog();
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


    public void onClickProfile(View v)
    {
        Intent intent = new Intent(this,ViewProfileActivity.class);

        //System.out.println("in MA.onClickProfil()" + _FirstName.)

        intent.putExtra("firstname", _FirstName.getText().toString());
        intent.putExtra("lastname", _LastName.getText().toString());
        intent.putExtra("type", _Type.getText().toString());
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
