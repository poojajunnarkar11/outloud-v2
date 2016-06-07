package com.example.world.outloud;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends Activity {

    protected static final int REQCODE_SPEECH = 1;
    private static final String TAG = MainActivity.class.getSimpleName();
    public ImageButton btnIcon;
    public TextView txtResult;
    public TextToSpeech tts;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);
        btnIcon = (ImageButton) findViewById(R.id.btnIcon);
        txtResult = (TextView) findViewById(R.id.txtResult);

        //Toast.makeText(getApplicationContext(),TAG ,Toast.LENGTH_LONG).show();

        //Creating an object of TTS
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.US);
                }
            }
        });

        btnIcon.setOnClickListener(new View.OnClickListener() {

                                       @Override
                                       public void onClick(View v) {
                /*Triggers an intent that pops a dialog box and waits for speech input
                    Pass an 'extra' let the intent know that it is expecting speech in English*/
                                           Intent in = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                           in.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
                                           try {
                    /*Activity waits for speech input*/
                                               startActivityForResult(in, REQCODE_SPEECH);
                                               txtResult.setText("");
                                           } catch (ActivityNotFoundException a) {
                                               Toast.makeText(getApplicationContext(), "Speech to Text not supported", Toast.LENGTH_LONG).show();
                                           }

                                       }
                                   }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent in) {
        super.onActivityResult(requestCode, resultCode, in);
        switch (requestCode) {
            case REQCODE_SPEECH: {
                if (resultCode == RESULT_OK && null != in) {
                    ArrayList<String> text = in.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String print = "You just said: \"" + text.get(0) + "\"";
                    txtResult.setText(print);
                    //Log.d(TAG, text.get(0));
                    //Toast.makeText(getApplicationContext(),print,Toast.LENGTH_SHORT).show();
                    tts.speak(print, TextToSpeech.QUEUE_FLUSH, null);

                    /*
                    String logic
                    */
                    String sentence = text.get(0) + text.get(1) + text.get(2);
                    int index1 = sentence.indexOf("1");
                    int index2 = sentence.indexOf("2");
                    int index3 = sentence.indexOf("3");

                    if (index1 != -1) {
                        Intent in1 = new Intent(MainActivity.this, pageone.class);
                        String msg = "Okay, opening page 1";
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                        tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
                        startActivity(in1);
                    }
                    else if (index2 != -1) {
                        Intent in2 = new Intent(MainActivity.this, pagetwo.class);
                        String msg = "Okay, opening page 2";
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                        tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
                        startActivity(in2);
                    }
                    else if (index3 != -1) {
                        Intent in3 = new Intent(MainActivity.this, pagethree.class);
                        String msg = "Okay, opening page 3";
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                        tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
                        startActivity(in3);
                    }
                    else {
                        String msg = "Sorry we could not find the page you are looking for.";
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                        tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
                break;
            }
        }
    }
    public void onDestroy() {

        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}
