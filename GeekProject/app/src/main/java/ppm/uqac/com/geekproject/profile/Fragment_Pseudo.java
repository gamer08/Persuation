package ppm.uqac.com.geekproject.profile;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
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
        ImageView buttonModification = (ImageView) rootview.findViewById(R.id.BTN_Modificate);

        buttonModification.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View v)
            {
                if (_userNameET.getText().length() != 0)
                    saveProfil();
                 else
                    Toast.makeText(getActivity(), "Veuillez entrer un pseudo conforme", Toast.LENGTH_LONG).show();

            }
        });

        // Petite difference dans un fragment pour utiliser le getIntent

        Intent intent = new Intent(getActivity().getIntent());
        //Intent intent = getIntent();
        if (intent != null)
        {
            _profile = (Profile) intent.getSerializableExtra("profile");
            _userNameET.setText(_profile.getUserName());
            _typeTV.setText(_profile.getType().toString());
            _levelTV.setText(String.valueOf(_profile.getLevel()));
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), _profile.getAvatar());
            Bitmap bMapScaled = Bitmap.createScaledBitmap(bitmap, 640, 640, false);
            _avatar.setImageBitmap(bMapScaled);

            // On recupère l'expérience de l'utilisateur

            double xp = _profile.getExperience();
            int percent = (int) (xp / _profile.getLevelLimit() * 100);
            System.out.println("Vue du profil: experience = " + xp + " pourcentage = " + percent + " niveau = " + _profile.getLevel());
            ProgressBar myprogressbar = (ProgressBar) rootview.findViewById(R.id.progress_bar);
            myprogressbar.setProgress(percent);
        }

        // Appui long sur la barre

        final TextView tv = (TextView) rootview.findViewById(R.id.TV_ProgressBarText);
        ProgressBar pb = (ProgressBar) rootview.findViewById(R.id.progress_bar);

        pb.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                tv.setText(String.valueOf(_profile.getExperience()));
                System.out.println("In Pseudo.longClic");
                return true;
            }
        });


        Typeface typeFace= Typeface.createFromAsset(getActivity().getAssets(), "octapost.ttf");
        _typeTV.setTypeface(typeFace);
        _levelTV.setTypeface(typeFace);
        _userNameET.setTypeface(typeFace);

        TextView t1 = (TextView) rootview.findViewById(R.id.TVL_Level);
        TextView t2 = (TextView) rootview.findViewById(R.id.TVL_Type);
        TextView t3 = (TextView) rootview.findViewById(R.id.TVL_UserName);

        t1.setTypeface(typeFace);
        t2.setTypeface(typeFace);
        t3.setTypeface(typeFace);

        return rootview;
    }

    /**
     * Sauvegarde les information du profil dans le fichier
     */
    public void saveProfil()
    {
        System.out.println("in Fragment_Pseudo.saveProfil()");

        _profile.setUserName(_userNameET.getText().toString());

        Intent intentSave = new Intent(getActivity(),SaveProfileService.class);
        intentSave.putExtra("profile", _profile);

        System.out.println("Fin de modification du profil et avant sauvegarde: profil: username = " +
                _profile.getUserName() + "score =  " + _profile.getScore() + " et type = " + _profile.getType() +
                " et level = " + _profile.getLevel() + " et experience = " + _profile.getExperience());

        getActivity().startService(intentSave);

        System.out.println("Fin de modification du profil et après sauvegarde: profil: username = " +
                _profile.getUserName() + "score =  " + _profile.getScore() + " et type = " + _profile.getType() +
                " et level = " + _profile.getLevel() + " et experience = " + _profile.getExperience());


        Intent intent = new Intent(getActivity(), MainActivity.class);
        getActivity().finish();
        intent.putExtra("profile", _profile);
        intent.putExtra("activite", "ViewProfileActivity");
        startActivity(intent);
    }
}