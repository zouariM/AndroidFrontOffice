package fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.view.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import fr.enseirb.zouari.androidproject.R;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.view.RunStatsActivity;

/**
 * Options de Tri (distance, date et durée)
 */
public class TriStatsDialog extends DialogFragment {

    private RadioGroup criterias;
    private static final String LOG_TAG = TriStatsDialog.class.getSimpleName();
    private int idCriteria;
    private Button validate;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreateView call");
        View view = inflater.inflate(R.layout.dialog_tri_stats, container, false);

        setCriterias(view);
        setValidate(view);

        return view;
    }

    private void setCriterias(View view){
        criterias = (RadioGroup)view.findViewById(R.id.stats_sort_by);
        criterias.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                idCriteria = checkedId;
            }
        });
    }

    private void setValidate(View view){
        validate = (Button)view.findViewById(R.id.sort_validate);
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "CHecked id :" + idCriteria);
                try{
                    RunStatsActivity activity = (RunStatsActivity)getActivity();
                    activity.handle(idCriteria);
                }
                catch(ClassCastException ex){
                    Log.e(LOG_TAG, ex.getMessage());
                }

                getDialog().dismiss();
            }
        });
    }

}
