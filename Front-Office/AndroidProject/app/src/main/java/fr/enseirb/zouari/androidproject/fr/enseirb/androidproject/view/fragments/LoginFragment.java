package fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import fr.enseirb.zouari.androidproject.R;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.presenter.Constants;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.presenter.LoginPresenter;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.view.MainActivity;

public class LoginFragment extends Fragment implements LoginPresenter.View {

    private static final String LOG_TAG = LoginFragment.class.getSimpleName();

    // Champes de formulaires
    private Map<Integer, EditText> data;
    // Soumettre le formulaire
    private Button validate;
    // Annulation
    private Button reset;

    private LoginPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        SharedPreferences preferences = getActivity().getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        presenter = new LoginPresenter(this, preferences);

        this.setData(view);
        validate = (Button)view.findViewById(R.id.login_validate);
        setValidateListener();
        reset = (Button)view.findViewById(R.id.login_reset);
        setResetListener();

        return view;
    }

    // Réccupération de champs EditText
    private void setData(View view){
        data = new HashMap<>();

        data.put(R.id.user_login, (EditText)view.findViewById(R.id.user_login));
        data.put(R.id.login_password, (EditText)view.findViewById(R.id.login_password));
    }

    // Soumission de formulaire
    private void setValidateListener(){
        validate.setOnClickListener((v -> {
            String login = data.get(R.id.user_login).getText().toString();
            String password = data.get(R.id.login_password).getText().toString();

            // Appel de presenter pour mettre à jour le modèle
            presenter.login(login, password);
        }));
    }

    // Remettre les champs à vide
    private void setResetListener(){
        reset.setOnClickListener(v -> {
            for(Integer key:data.keySet())
                data.get(key).setText("");
        });
    }

    // Traitement de success de authentification
    @Override
    public void onLoginSuccess() {
        MainActivity mainActivity = (MainActivity) getActivity();
        Toast.makeText(getActivity(), "Vous etes connecté", Toast.LENGTH_SHORT).show();

        // Affihcage es statistiques globales
        mainActivity.displayGeneralStatsFragment();
    }

    // Traitement de l'echec de l'authentification
    @Override
    public void onLoginFailure() {
        Toast.makeText(getActivity(), "Vérfier vos données", Toast.LENGTH_SHORT).show();
    }
}
