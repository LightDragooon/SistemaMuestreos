package gruporeque.sistemamuestreos;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

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

public class Proyectos extends AppCompatActivity {


    Button btnCrearProyecto, btnVerProyecto;
    Spinner spinnerProyectos;
    ArrayList<Integer> idList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proyectos);

        btnCrearProyecto = (Button) findViewById(R.id.btn_ProyectosCrearProyectos);
        btnVerProyecto = (Button) findViewById(R.id.btn_ProyectosVerProyectos);
        spinnerProyectos = (Spinner)findViewById(R.id.spinnerProyectos);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, getProyectos());
        //arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProyectos.setAdapter(arrayAdapter);

        btnCrearProyecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirCrearProyecto = new Intent(Proyectos.this,crear_proyecto.class);
                startActivity(abrirCrearProyecto);
            }
        });

        btnVerProyecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirVerProyecto = new Intent(Proyectos.this,ver_proyecto.class);
                abrirVerProyecto.putExtra("id", idList.get(spinnerProyectos.getSelectedItemPosition()));
                startActivity(abrirVerProyecto);
            }
        });

    }

    private List<String> getProyectos() {
        String URL = ClaseGlobal.Proyectos_Select + "?Id=NULL";
        final List<String> arraySpinner = new ArrayList<>();
        arraySpinner.add("Seleccione un Proyecto");
        idList.add(0);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("value");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        String projectName = jsonArray.getJSONObject(i).get("ID_PROYECTO").toString() + "-" + jsonArray.getJSONObject(i).get("NOMBRE_PROYECTO").toString();
                        if (projectName.contains("_")) projectName = projectName.replaceAll("_", " ");
                            arraySpinner.add(projectName);
                            idList.add(Integer.parseInt(jsonArray.getJSONObject(i).get("ID_PROYECTO").toString()));
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
        if(!spinnerProyectos.getSelectedItem().toString().equals("Seleccione un Usuario")){
            deleteUser(ClaseGlobal.Eliminar_Usuario+"?Usuario="+spinnerProyectos.getSelectedItem().toString());
        }
        else{
            errorMessageDialog("Seleccione un proyecto para poder eliminarlo");
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
            if(jsonObject.getString("status").equals("false") ) errorMessageDialog("No se ha podido eliminar el proyecto");
            else correctMessageDialog("Se ha eliminado el proyecto exitosamente");
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
