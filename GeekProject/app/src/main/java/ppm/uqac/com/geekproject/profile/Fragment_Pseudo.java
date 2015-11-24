package ppm.uqac.com.geekproject.profile;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View.OnLongClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;

import ppm.uqac.com.geekproject.R;
import ppm.uqac.com.geekproject.mainmenu.MainActivity;


public class Fragment_Pseudo extends Fragment {

    View rootview;
    private EditText _userNameET;
    private TextView _typeTV;
    private TextView _score;
    private Profile _profile;
    private ImageView _avatar;
    private TextView _levelTV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragment_pseudo, container, false);

        _userNameET = (EditText) rootview.findViewById(R.id.TV_UserName);
        _typeTV = (TextView) rootview.findViewById(R.id.TV_Type);
        _score = (TextView) rootview.findViewById(R.id.TV_Score);
        _avatar = (ImageView) rootview.findViewById(R.id.image);
        _levelTV = (TextView) rootview.findViewById(R.id.TV_Level);

        // Listener pour le bouton de sauvegarde des modifications

        Button buttonModification = (Button) rootview.findViewById(R.id.BTN_Modificate);

        buttonModification.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (_userNameET.getText().length() != 0) {
                    saveProfil();

                } else {
                    Toast.makeText(getContext(), "Veuillez entrer un pseudo conforme", Toast.LENGTH_LONG).show();
                }
            }
        });


        // Petite difference dans un fragment pour utiliser le getIntent

        Intent intent = new Intent(getActivity().getIntent());
        //Intent intent = getIntent();
        if (intent != null) {
            _profile = (Profile) intent.getSerializableExtra("profile");
            _userNameET.setText(_profile.getUserName());
            System.out.println("NIVEAU: " + _profile._level);
            _typeTV.setText(_profile.getType().toString());
            _levelTV.setText(String.valueOf(_profile._level));
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), _profile.getAvatar());
            Bitmap bMapScaled = Bitmap.createScaledBitmap(bitmap, 640, 640, false);
            _avatar.setImageBitmap(bMapScaled);

            // On recupère l'expérience de l'utilisateur

            double xp = _profile.getExperience();
            int percent = (int) (xp / _profile.getLevelLimit() * 100);
            System.out.println("Vue du profil: experience = " + xp + " pourcentage = " + percent + " niveau = " + _profile.get_level());
            ProgressBar myprogressbar = (ProgressBar) rootview.findViewById(R.id.progress_bar);
            myprogressbar.setProgress(percent);




        }

        // Appui long sur la barre

        final TextView tv = (TextView) rootview.findViewById(R.id.TV_ProgressBarText);
        ProgressBar pb = (ProgressBar) rootview.findViewById(R.id.progress_bar);
        pb.setLongClickable(true);

        pb.setOnLongClickListener(new OnLongClickListener() {


            @Override
            public boolean onLongClick(View v) {

                tv.setText(String.valueOf(_profile.getExperience()));;
                return true;
            }
        });

        return rootview;

    }

    /**
     * Sauvegarde les information du profil dans le fichier
     */
    public void saveProfil()
    {
        System.out.println("in CPA.saveProfil()");
        String userName = "userName=";
        userName = userName.concat(_userNameET.getText().toString()).concat(System.getProperty("line.separator"));
        _profile.setUserName(_userNameET.getText().toString());

        String lastName = "lastName=";

        String type = "type=";
        type = type.concat(_profile._type.toString()).concat(System.getProperty("line.separator"));

        String experience = "experience=";
        experience = experience.concat((String.valueOf(_profile._experience)).concat(System.getProperty("line.separator")));
        String level = "level=";
        level = level.concat((String.valueOf(_profile._level)).concat(System.getProperty("line.separator")));


        try
        {
            FileOutputStream out = getActivity().openFileOutput(Profile.PROFIL_FILE_NAME, Context.MODE_PRIVATE);
            System.out.println(Profile.PROFIL_FILE_NAME);
            out.write(userName.getBytes());

            out.write(type.getBytes());
            out.write(experience.getBytes());
            out.write(level.getBytes());
            out.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Intent intent = new Intent(getActivity(), MainActivity.class);
        //Intent intent = new Intent(this,MainActivity.class);
        getActivity().finish();
        intent.putExtra("profile", _profile);
        intent.putExtra("activite", "ViewProfileActivity");
        startActivity(intent);
        System.out.println("fin save file et type est "+_profile._type.toString());
    }


}
