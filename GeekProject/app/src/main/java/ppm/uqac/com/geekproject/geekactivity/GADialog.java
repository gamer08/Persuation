package ppm.uqac.com.geekproject.geekactivity;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import ppm.uqac.com.geekproject.R;

/**
 * Created by Simon on 14/10/2015.
 * Classe pour gérer la fenetre de dialogue affichée après la création du profil
 * Attribut dialogDonelistener pour savoir quand on appuie sur le bouton
 * le fichier dialog_ga.xml permet de gérer l'affichage de la "fenetre"
 *
 */

public class GADialog extends DialogFragment
{
    private dialogDoneListener mListener;

    /**
     * OnclickListener
     */
    View.OnClickListener onOK=
            new View.OnClickListener()
            {
                @Override public void onClick(View view){
                    mListener.onDone(true);
                    dismiss();
                }
            };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.dialog_ga,container, false);
        Dialog myDialog=getDialog();
        myDialog.setTitle("Avant tout..."); //Pas forcément de mettre un titre
        Button OK= (Button) v.findViewById(R.id.buttonDialog); // on lie un bouton java au bouton défini dans le xml
        OK.setOnClickListener(onOK); // Ajout du click sur le bouton dans un listener

        // Font
        Typeface typeFace= Typeface.createFromAsset(getActivity().getAssets(), "octapost.ttf");

        OK.setTypeface(typeFace);
        TextView t = (TextView) v.findViewById(R.id.TV_Dialog);
        t.setTypeface(typeFace);
        return v;
    }

    /**
     * Méthode pour gérer le comportement de la fenetre
     * lorsqu'elle est lié à l'activité.
     * Depuis l'API 23 il vaut mieux utiliser
     * public void onAttach(Context c)
     */

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            mListener = (dialogDoneListener) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString()
                    + " must implement dialogDoneistener");
        }
    }

    /**
     * Méthode pour gérer le comportement de
     * la fenetre lorsqu'elle n'est plus liéé à l'activity
     */
    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    /**
     * Interface pour permettre à l'activity d'exécuter quelque chose
     */
    public interface dialogDoneListener
    {
        void onDone(boolean state);
    }
}