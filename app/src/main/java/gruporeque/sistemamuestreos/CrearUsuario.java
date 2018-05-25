package gruporeque.sistemamuestreos;

import android.content.DialogInterface;
import android.content.Intent;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

public class CrearUsuario extends AppCompatActivity {

    Button btn_CrearUsuario,btn_salir;
    EditText txt_NombreUsuario,txt_Password;
    CheckBox Check_tipoUsuario;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);
        btn_CrearUsuario = (Button)findViewById(R.id.btn_Crear);
        btn_salir = (Button)findViewById(R.id.btn_Salir);
        txt_NombreUsuario = (EditText)findViewById(R.id.txt_NombreUsuario);
        txt_Password = (EditText)findViewById(R.id.txt_Contraseña);
        Check_tipoUsuario = (CheckBox)findViewById(R.id.check_TipoUsuario);

        btn_CrearUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearUsuario();
            }
        });

        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void crearUsuario(){
        if(!txt_NombreUsuario.getText().toString().equals("")&&!txt_Password.getText().toString().equals("")){
            if(Check_tipoUsuario.isChecked()) {
                createUser(ClaseGlobal.Usuario_Insert+
                        "?Usuario="+txt_NombreUsuario.getText().toString()+
                        "&Password="+txt_Password.getText().toString()+
                        "&Tipo_Usuario=Admin");

            }
            else{
                createUser(ClaseGlobal.Usuario_Insert+
                        "?Usuario="+txt_NombreUsuario.getText().toString()+
                        "&Password="+txt_Password.getText().toString()+
                        "&Tipo_Usuario=Asist");
            }
        }else{
            errorMessageDialog("Llene todos las casillas para crear el usuario");
        }
    }

    private void createUser(String URL){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                createUserAux(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorMessageDialog("No se pudo conectar al servidor");
            }
        });queue.add(stringRequest);
    }

    private void createUserAux(String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            if(jsonObject.getString("status").equals("false") ) errorMessageDialog("No se pudo hacer xd");
            else correctMessageDialog("Si se pudo hacer alv");
        }catch (JSONException e){
            e.printStackTrace();
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


}
