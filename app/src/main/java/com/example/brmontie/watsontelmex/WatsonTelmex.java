package com.example.brmontie.watsontelmex;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ibm.watson.developer_cloud.android.library.audio.StreamPlayer;
import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;

public class WatsonTelmex extends AppCompatActivity {

    TextView textView;
    EditText editText;
    Button button;

    StreamPlayer streamPlayer;


    private TextToSpeech initTextToSpeechService(){
        TextToSpeech service = new TextToSpeech();
        String username = "83840b60-70aa-4892-abb9-f56a700ff2f4";
        String password = "zTU0Eem0m6MB";
        service.setUsernameAndPassword(username, password);
        return service;
    }

    private class WatsonTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... textToSpeak) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView.setText("running the Watson thread");
                }
            });

            TextToSpeech textToSpeech = initTextToSpeechService();
            streamPlayer = new StreamPlayer();
            streamPlayer.playStream(textToSpeech.synthesize(String.valueOf(editText.getText()), Voice.EN_MICHAEL).execute());

            return "text to speech done";
        }

        @Override
        protected void onPostExecute(String result) {
            textView.setText("TTS status: " + result);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watson_telmex);

        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("the text to speech: " + editText.getText());
                textView.setText("TTS: " + editText.getText());

                WatsonTask task = new WatsonTask();
                task.execute(new String[]{});


            }
        });
    }
}