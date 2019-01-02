package fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import fr.enseirb.zouari.androidproject.R;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.presenter.InscriptionPresenter;

/**
 * Vue Inscrription d'un nouveau utilisateur
 */
public class InscriptionActivity extends AppCompatActivity implements InscriptionPresenter.View {

    // Débogage
    private static final String LOG_TAG = InscriptionActivity.class.getSimpleName();
    // Mapping entre les champs text les attributs
    private Map<Integer, EditText> data;
    // Presenter
    private InscriptionPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // setup vue
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        // setup data
        presenter = new InscriptionPresenter(this);
        this.setData();

    }

    // Réccupérer les champs de formulaire
    private void setData(){
        data = new HashMap<>();

        data.put(R.id.add_user_login, (EditText)findViewById(R.id.add_user_login));
        data.put(R.id.add_user_first_name, (EditText)findViewById(R.id.add_user_first_name));
        data.put(R.id.add_user_last_name, (EditText)findViewById(R.id.add_user_last_name));
        data.put(R.id.add_user_password, (EditText)findViewById(R.id.add_user_password));
    }

    // Vider champs
    public void reset(View view){
        for(Integer key:data.keySet())
            data.get(key).setText("");
    }

    // Réccupérer les contenu d'un champs identifié par un id key
    private String getData(Integer key){
        if(data.containsKey(key))
            return data.get(key).getText().toString();
        else
            return null;
    }

    // Soumettre le formulaire
    public void validate(View view){
        String login = this.getData(R.id.add_user_login);
        String password = this.getData(R.id.add_user_password);
        String firstName = this.getData(R.id.add_user_first_name);
        String lastName = this.getData(R.id.add_user_last_name);

        // Action de presenter
        presenter.addUser(firstName, lastName, login, password);
    }

    // Traitement de cas d'ajout d'uitlisateur valide
    @Override
    public void onAddUserSuccess() {
        Toast.makeText(InscriptionActivity.this, "Well Done!", Toast.LENGTH_SHORT).show();

        // Retour au MainActivity
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    // Traitement le cas d'echec d'ajout d'utilisateur invalide
    @Override
    public void onAddUserFailure() {
        Toast.makeText(InscriptionActivity.this, "Vérifier tes données", Toast.LENGTH_SHORT).show();
    }
}
