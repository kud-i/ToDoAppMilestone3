package ch.ethz.inf.milestone1.io;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by m on 24/11/15.
 */
public class IO {

    private String filename = "personal.txt";

    public ArrayList LoadData(Context context) {

        int i;
        ArrayList dataset = new ArrayList();


        String str = readFromFile(context);

        try {
            JSONObject jObject = new JSONObject(str);

        JSONArray jArray;

        jArray = jObject.getJSONArray("ToDoList");

        for (i = 0; i < jArray.length(); i++) {
            dataset.add(jArray.getJSONObject(i).get("title").toString());

        }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dataset;

    }

    public void WriteData(ArrayList dataset, Context context){
        JSONObject jObject = new JSONObject();
        JSONArray jArray = new JSONArray();

        try{
            for (Object obj : dataset) {
                JSONObject jsonObj = new JSONObject();

                jsonObj.put("title", obj.toString());

                jArray.put(jsonObj);

            }
            jObject.put("ToDoList", jArray);

            writeToFile(jObject.toString(), context);
        } catch (JSONException e) {
        e.printStackTrace();
        }

    }

    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(filename);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }




}
