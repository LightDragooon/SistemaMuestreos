package gruporeque.sistemamuestreos;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Usuarios extends AppCompatActivity {

    Button btnCrearUsuario,btnAtrasUsuarios,btnEliminarUser;

    Spinner spinnerUsuarios;

    List<String> listaPrueba = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);

        btnEliminarUser = (Button)findViewById(R.id.btnEliminarUsuario);
        btnAtrasUsuarios = (Button) findViewById(R.id.btn_AtrasUsuarios);
        btnCrearUsuario = (Button) findViewById(R.id.btn_UsuariosCrearUsuario);
        spinnerUsuarios=(Spinner)findViewById(R.id.UsuariosSpinner);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, getUsuarios());
        //arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUsuarios.setAdapter(arrayAdapter);

        btnAtrasUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnCrearUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirCrearUsuario = new Intent(Usuarios.this,CrearUsuario.class);
                startActivity(abrirCrearUsuario);
            }
        });

        btnEliminarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarUsuario();
            }
        });


    }
    private List<String> getUsuarios() {
        String URL = ClaseGlobal.Usuarios_Select;
        final List<String> arraySpinner = new ArrayList<>();
        arraySpinner.add("Seleccione un Usuario");
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("value");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String userName = jsonArray.getJSONObject(i).get("NOMBRE_USUARIO").toString();
                        userName = userName+"   ";
                        userName = userName+ jsonArray.getJSONObject(i).get("ID_USUARIO").toString();
                        if (userName.contains("_")) userName = userName.replaceAll("_", " ");
                        arraySpinner.add(userName);
                        listaPrueba.add(userName);
                    }
                } catch (JSONException e) { e.printStackTrace(); }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorMessageDialog("No se puede conectar al servidor en estos momentos.");
            }
        }); queue.add(stringRequest);
        return arraySpinner;

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

    private void eliminarUsuario(){
        if(!spinnerUsuarios.getSelectedItem().toString().equals("Seleccione un Usuario")){
            String[] separado = spinnerUsuarios.getSelectedItem().toString().split("   ");
            deleteUser(ClaseGlobal.Eliminar_Usuario+"?IdUsuario="+separado[1]);
        }
        else{
            errorMessageDialog("Seleccione un usuario para poder eliminarlo");
        }
    }

    private void deleteUser(String URL){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                deleteUserAux(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorMessageDialog("No se pudo conectar al servidor");
            }
        });queue.add(stringRequest);
    }

    private void deleteUserAux(String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            if(jsonObject.getString("status").equals("false") ) errorMessageDialog("No se ha podido eliminar el usuario");
            else correctMessageDialog("Se ha eliminado el usuario exitosamente");
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}

