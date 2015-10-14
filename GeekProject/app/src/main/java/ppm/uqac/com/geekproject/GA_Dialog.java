package ppm.uqac.com.geekproject;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by Simon on 14/10/2015.
 */


public class GA_Dialog extends DialogFragment {


    private dialogDoneListener mListener;

    View.OnClickListener onOK=
            new View.OnClickListener(){
                @Override public void onClick(View view){
                    mListener.onDone(true);
                    dismiss();
                }
            };

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.dialog_ga,container, false);
        Dialog myDialog=getDialog();
        myDialog.setTitle("Voir activités");
        Button OK= (Button) v.findViewById(R.id.buttonActivites);
        OK.setOnClickListener(onOK);
        return v;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (dialogDoneListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement dialogDoneistener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface dialogDoneListener{
        void onDone(boolean state);
    }

}
