package gruporeque.sistemamuestreos;

import android.content.DialogInterface;
import android.content.Intent;
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

public class crear_trabajador extends AppCompatActivity {

    Button btnCrearTrabajador;
    Button btnAtrasCrearTrabajador;
    EditText txtNombreOperador,txtApodoOperador,txtOcupacionOperador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_trabajador);
        btnCrearTrabajador = (Button)findViewById(R.id.btn_CrearTrabajador);
        btnAtrasCrearTrabajador = (Button)findViewById(R.id.btn_AtrasCrearTrabajador);
        txtNombreOperador = (EditText)findViewById(R.id.txt_NombreNuevoTrabajador);
        txtApodoOperador = (EditText)findViewById(R.id.txt_ApodoNuevoTrabajador);
        txtOcupacionOperador = (EditText)findViewById(R.id.txt_OcupacionNuevoTrabajador);

        btnCrearTrabajador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearTrabajador();
            }
        });

        btnAtrasCrearTrabajador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirMenuAdministrador = new Intent(crear_trabajador.this,Trabajadores.class);
                startActivity(abrirMenuAdministrador);
            }
        });
    }

    private void crearTrabajador(){
        if(!txtNombreOperador.getText().toString().equals("")&&!txtApodoOperador.getText().toString().equals("")&&!txtOcupacionOperador.getText().toString().equals("")){
            createOperador(ClaseGlobal.Trabajador_Insert+
                    "?NombreOperador="+txtNombreOperador.getText().toString()+
                    "&ApodoOperador="+txtApodoOperador.getText().toString()+
                    "&OcupacionOperador="+txtOcupacionOperador.getText().toString());


        }else{
            errorMessageDialog("Llene todos las casillas para crear el trabajador");
        }
    }

    private void createOperador(String URL){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                createOperadorAux(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorMessageDialog("No se pudo conectar al servidor");
            }
        });queue.add(stringRequest);
    }

    private void createOperadorAux(String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            if(jsonObject.getString("status").equals("false") ) errorMessageDialog("No se ha podido crear el trabajador");
            else correctMessageDialog("Se ha creado el trabajador exitosamente");
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
