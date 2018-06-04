package gruporeque.sistemamuestreos;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class Tareas extends AppCompatActivity {

    Button btnCrearTarea,btnEliminarTarea;

    Spinner spinnerTareas;

    List<String> listaPrueba = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas);

        btnCrearTarea = (Button)findViewById(R.id.btn_TareasCrearTarea);
        btnEliminarTarea = (Button) findViewById(R.id.btnEliminarTarea);
        spinnerTareas=(Spinner)findViewById(R.id.TareasSpinner);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, getTareas());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTareas.setAdapter(arrayAdapter);

        btnCrearTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirCrearTarea = new Intent(Tareas.this,crear_tarea.class);
                startActivity(abrirCrearTarea);
            }
        });

        btnEliminarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarTarea();
            }
        });
    }

    private List<String> getTareas() {
        String URL = ClaseGlobal.Tareas_Select;
        final List<String> arraySpinner = new ArrayList<>();
        arraySpinner.add("Seleccione una Tarea");
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("value");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String workName = jsonArray.getJSONObject(i).get("ID_TAREA").toString();
                        workName = workName+"   ";
                        workName = workName+ jsonArray.getJSONObject(i).get("NOMBRE_TAREA").toString();
                        if (workName.contains("_")) workName = workName.replaceAll("_", " ");
                        arraySpinner.add(workName);
                        listaPrueba.add(workName);
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

    private void eliminarTarea(){
        if(!spinnerTareas.getSelectedItem().toString().equals("Seleccione una Tarea")){
            deleteWork(ClaseGlobal.Eliminar_Tarea+"?Tarea="+spinnerTareas.getSelectedItem().toString());
        }
        else{
            errorMessageDialog("Seleccione una tarea para poder eliminarlo");
        }
    }

    private void deleteWork(String URL){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                deleteWorkAux(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorMessageDialog("No se pudo conectar al servidor");
            }
        });queue.add(stringRequest);
    }

    private void deleteWorkAux(String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            if(jsonObject.getString("status").equals("false") ) errorMessageDialog("No se ha podido eliminar el usuario");
            else correctMessageDialog("Se ha eliminado el usuario exitosamente");
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
