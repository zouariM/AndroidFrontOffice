package fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.user;

import android.util.Log;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Classe utilisé par API Gson pour dé/sérialisation de objets JSON
 */
public class UserJsonAdapter extends TypeAdapter<User> {

    // Sérialisation
    @Override
    public void write(JsonWriter out, User value) throws IOException {
        out.beginObject();
        out.name("login").value(value.getLogin());
        out.name("firstName").value(value.getFirstName());
        out.name("lastName").value(value.getLastName());
        out.name("password").value(value.getPassword());
        out.endObject();
    }

    // Désirialisation
    @Override
    public User read(JsonReader in) throws IOException {
        User u = User.getCurrentUser();
        in.beginObject();

        while(in.hasNext()){
            String key = in.nextName();
            if(key.equals("id"))
                u.setId(in.nextString());

            else if(key.equals("user")){
                in.beginObject();
                while(in.hasNext()){
                    String attr = in.nextName();
                    if(attr.equals("firstName"))
                        u.setFirstName(in.nextString());
                    else if(attr.equals(("lastName")))
                        u.setLastName(in.nextString());
                    else if(attr.equals("login"))
                        u.setLogin(in.nextString());

                }

                in.endObject();
            }

        }

        in.endObject();
        Log.i(this.getClass().getName(), u.toString());
        return u;
    }

}
