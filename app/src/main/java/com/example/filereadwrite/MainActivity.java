package com.example.filereadwrite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    // Filename ohne Pfadangabe, also direkt im "Homedirectory" der App
    private final String FILENAME = "testfile.txt";

    // Datenfeld für den zuletzt aus der Datei gelesenen bzw. vom User eingegebenen String
    private String text = "";

    // Datenfelder für die beiden TextViews und das EditText-Field
    private TextView tv1, tv2;
    private EditText ed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Die View-Felder initialisieren
        tv1 = findViewById(R.id.textViewFilename);
        tv2 = findViewById(R.id.textViewFileContent);
        ed = findViewById(R.id.editTextYourText);

        // Den Filenamen und einen vorläufigen Text ausgeben
        tv1.setText(FILENAME);
        tv2.setText("not opened yet");
    }

    // Eventhandler für den "READ"-Button
    public void onClickReadButton(View v){
        try{
            // Lies den Inhalt des Files und zeige ihn in tv2 an
            text = readFromFile(FILENAME);
            tv1.setText(FILENAME);
            tv2.setText(text);
        } catch (IOException e) {
            tv1.setText("Error reading file");
        }
    }

    // Eventhandler für den "WRITE"-Button
    public void onClickWriteButton(View v){
        // Lies den Inhalt des EditText-Fields und schreib ihn in das File
        text =  ed.getText().toString();
        writeToFile(FILENAME, text);
    }

    // Interne Methode zum Schreiben in eine Datei
    private void writeToFile(String filename, String message)
    {
        try {
            FileOutputStream outfile = openFileOutput(filename, Context.MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outfile);
            outputStreamWriter.write(message);
            outputStreamWriter.close();

        } catch(FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Interne Methode zum Lesen aus der Datei
    private String readFromFile(String filename) throws IOException {
        String result = "";
        InputStream inputStream = openFileInput(filename);
        if(inputStream != null)
        {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();

            String temp = "";
            while((temp = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(temp);
                stringBuilder.append("|");
            }

            inputStream.close();
            result = stringBuilder.toString();
        }
        return result;
    }
}