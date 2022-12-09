package com.example.filereadwrite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private final String FILENAME = "content.txt";
    private String text = "";

    private TextView tv1, tv2;
    private EditText ed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = (TextView) findViewById(R.id.textViewFilename);
        tv1.setText(FILENAME);

        tv2 = (TextView) findViewById(R.id.textViewFileContent);
        tv2.setText("not opened yet");

        ed = (EditText) findViewById(R.id.editTextYourText);
    }

    public void onClickReadButton(View v){
        try{
            text = readFromFile(FILENAME);
            tv2.setText(text);
        } catch (IOException e) {
            tv1.setText("Error reading file");
        }
    }

    public void onClickWriteButton(View v){
        String newText =  ed.getText().toString();
        writeToFile(FILENAME, newText);
    }


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

    private String readFromFile(String filename) throws IOException {
        String result = "";
        InputStream inputStream = openFileInput(filename);
        if(inputStream != null)
        {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String temp = "";
            StringBuilder stringBuilder = new StringBuilder();

            while((temp = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(temp);
                stringBuilder.append("\n");
            }

            inputStream.close();
            result = stringBuilder.toString();
        }
        return result;
    }
}