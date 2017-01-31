package com.example.dostics01.ladygo.Parsers;

import com.example.dostics01.ladygo.POJO.RequestPackage;
import com.example.dostics01.ladygo.POJO.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dostics01 on 24/01/2017.
 */

public class UsuarioJSONParser {

    public static Usuario parser(String content){
        try {
            JSONObject jsonObject= new JSONObject(content);
            Usuario usuario = new Usuario();

            usuario.setData(jsonObject.getString("data"));
            usuario.setStatus(jsonObject.getInt("status"));
            return usuario;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
