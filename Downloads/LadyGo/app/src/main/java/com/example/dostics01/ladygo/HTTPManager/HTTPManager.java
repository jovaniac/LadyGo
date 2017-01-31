package com.example.dostics01.ladygo.HTTPManager;

import com.example.dostics01.ladygo.POJO.RequestPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Created by Dostics01 on 24/01/2017.
 */

public class HTTPManager {

    public static String GetData(RequestPackage requestPackage){
        BufferedReader bufferedReader = null;

        String uri = requestPackage.getUri();
        //Validar el método
        if(requestPackage.getMethod().equals("GET")){
            uri = "?" + requestPackage.getEncodeParams();
        }
        try {
            URL url = new URL(uri);
            HttpURLConnection Conexion = (HttpURLConnection) url.openConnection();
            //Usando el método POST
            if(requestPackage.getMethod().equals("POST")){
                Conexion.setDoOutput(true);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(Conexion.getOutputStream());
                outputStreamWriter.write(requestPackage.getEncodeParams());
                outputStreamWriter.flush();
            }
            StringBuilder stringBuilder = new StringBuilder();
            bufferedReader = new BufferedReader(new InputStreamReader(Conexion.getInputStream()));

            //Mostrar Valores recibidos
            String Linea;
            while ( (Linea= bufferedReader.readLine()) != null){
                stringBuilder.append(Linea +"\n");
            }
            return stringBuilder.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            if(bufferedReader != null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }
}
