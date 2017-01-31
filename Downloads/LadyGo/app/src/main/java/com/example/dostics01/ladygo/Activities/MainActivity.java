package com.example.dostics01.ladygo.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dostics01.ladygo.HTTPManager.HTTPManager;
import com.example.dostics01.ladygo.POJO.RequestPackage;
import com.example.dostics01.ladygo.POJO.Usuario;
import com.example.dostics01.ladygo.Parsers.UsuarioJSONParser;
import com.example.dostics01.ladygo.R;

public class MainActivity extends AppCompatActivity {
    EditText txtEmail, txtPassword;
    Button btnIngresar;
    int status;

    private static final String LOGTAG = "LOGTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtEmail = (EditText) findViewById(R.id.txtEMail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        btnIngresar = (Button) findViewById(R.id.btnIngresar);
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PedirDatos("http://192.168.0.7:3000/api/login-users?key=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwbGF0Zm9ybSI6IkJBQ0tFTkQiLCJlbnZpcm9ubWVudCI6IkRFVkVMT1BNRU5UIiwiY3VycmVudCI6IjIwMTctMDEtMTlUMTc6NDk6MDEuMjA5WiJ9.CnCCqOjY_JgmVwzHzSo-pt8L5BCN2fsxevPB_jy8Pyg");

                if (status == 200){
                    Intent intent = new Intent (v.getContext(), MapsActivity.class);
                    startActivityForResult(intent, 0);
                }else{
                    Toast.makeText(getApplicationContext(),"E-Mail o Contraseña incorrectos",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void PedirDatos(String uri) {
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setMethod("POST");
        requestPackage.setUri(uri);
        requestPackage.setParam("email", txtEmail.getText().toString());
        requestPackage.setParam("password", txtPassword.getText().toString());
        Log.d(LOGTAG, requestPackage.getParams().toString());

        MyAsyncTask tarea = new MyAsyncTask();
        tarea.execute(requestPackage);

    }

    private class MyAsyncTask extends AsyncTask<RequestPackage, String, String>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(RequestPackage... params) {
            String content = HTTPManager.GetData(params[0]);
            Log.d(LOGTAG, content);
            return content;
        }

        @Override
        protected void onProgressUpdate(String...values){

        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            if(result==null){
                Toast.makeText(getApplicationContext(), "No se pudo Conectar", Toast.LENGTH_SHORT).show();
                return;
            }
            CargarDatos(result);
        }
    }

    public void CargarDatos(String dato){
        Usuario usuario = UsuarioJSONParser.parser(dato);

        status= usuario.getStatus();

        //Guardar el nombre de usuario
        SharedPreferences preferences = getSharedPreferences("Preferencias", getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();
        editor.putString("NombreUsuario", txtEmail.getText().toString());
        editor.commit();
    }
}
