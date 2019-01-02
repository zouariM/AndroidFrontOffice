package fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.repository;

import android.util.Log;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Gestion de connexion avec Back-Office ( HTTP request/response)
 */
public class HttpUtils {

    // Débogage
    private static final String LOG_TAG = HttpUtils.class.getSimpleName();
    private static AtomicInteger nbRequest = new AtomicInteger(0);

    // Get Request
    public static StringBuffer getHttpResponse(String url){
        StringBuffer stringBuffer = new StringBuffer();
        Log.d(LOG_TAG, "new GET request " + url + " ** Total number : " + nbRequest.addAndGet(1) );

        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = client.execute(httpGet);

            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                HttpEntity httpEntity = response.getEntity();
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                String str;

                while((str = reader.readLine()) != null)
                    stringBuffer.append(str);
            }

            else
                return null;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return stringBuffer;
    }

    // Post Request
    public static StringBuffer postHttpResponse(String url, Object body){
        StringBuffer stringBuffer = new StringBuffer();
        Log.d(LOG_TAG, "new POST request " + url + " ** total number : " + nbRequest.addAndGet(1) );

        try{
            HttpClient client = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            Gson gson = new Gson();
            StringEntity entity = new StringEntity(gson.toJson(body));
            httpPost.setEntity(entity);
            httpPost.setHeader("Content-type", "application/json");

            HttpResponse response = client.execute(httpPost);
            int status = response.getStatusLine().getStatusCode();

            Log.i(HttpUtils.class.getSimpleName(), "Status = " + status);
            if(status < 400) {
                HttpEntity httpEntity = response.getEntity();
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                String str;

                while ((str = reader.readLine()) != null)
                    stringBuffer.append(str);
            }

            else
                return null;
        }

        catch(Exception ex){
            ex.printStackTrace();
        }

        return stringBuffer;
    }

}
