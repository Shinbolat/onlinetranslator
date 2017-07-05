package com.example.dev.wordstosqlite;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    TextView translatedText;
    EditText text;
    Button trans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trans = (Button)findViewById(R.id.translate);
        translatedText = (TextView)findViewById(R.id.translated_text);
        text = (EditText)findViewById(R.id.text);

        trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

                //Toast.makeText(getApplication(),"YYYEEESSS"+Boolean.toString(activeNetwork.isConnected()),Toast.LENGTH_LONG).show();

                if(activeNetwork.isConnected()==true){

                    //English to French ("<source_language>-<target_language>")
                    //Executing the translation function

                    String languagePair = "en-ru";

                    try {
                        Translate(text.getText().toString(),languagePair);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                else {
                    Toast.makeText(getApplicationContext(),"No internet connection",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    void Translate(String textToBeTranslated,String languagePair) throws ExecutionException, InterruptedException {
        TranslatorBackgroundTask translatorBackgroundTask= new TranslatorBackgroundTask(getApplicationContext());
        String translationResult = translatorBackgroundTask.execute(textToBeTranslated,languagePair).get(); // Returns the translated text as a String
        translatedText.setText(translationResult); // Logs the result in Android Monitor
    }
}
