package gruporeque.sistemamuestreos;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;

public class MainActivity extends AppCompatActivity {

    Button btn_IniciarSesion;
    EditText txt_Usuario,txt_Password;
    private Intent intent;
    public static String usuario="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_IniciarSesion = (Button)findViewById(R.id.btn_login);
        txt_Usuario= (EditText)findViewById(R.id.txt_usuario);
        txt_Password= (EditText)findViewById(R.id.txt_contraseña);

        btn_IniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loguear();
            }
        });
    }

    private void loguear(){
        if(!txt_Password.getText().equals("")&&!txt_Usuario.getText().equals("")){
            iniciarSesion(ClaseGlobal.Iniciar_Sesion+"?Usuario="+txt_Usuario.getText().toString()+"&Password="+txt_Password.getText().toString(),true);
        }
        else{
            errorMessageDialog("Rellene todos los campos para poder iniciar sesión");
        }
    }

    private void errorMessageDialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setMessage(message).setTitle("Error").setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void correctMessageDialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setMessage(message).setTitle("Éxito").setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void iniciarSesion(String URL, final boolean estado){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                if (estado) loginResponse(response);  /* Para inicio de sesión */
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorMessageDialog("No se puede conectar al servidor.");
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void loginResponse(String response) {
        try{
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("false"))
                errorMessageDialog("No se pudo iniciar sesión.\nVerifique el usuario y contraseña ingresados");
            else {
                if (jsonObject.getJSONObject("value").getString("TIPO_USUARIO").equals("Admin"))
                    intent = new Intent(this, MenuAdministrador.class);
                else intent = new Intent(this, MenuAsistente.class);
                usuario=jsonObject.getJSONObject("value").getString("ID_USUARIO");
                startActivity(intent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
