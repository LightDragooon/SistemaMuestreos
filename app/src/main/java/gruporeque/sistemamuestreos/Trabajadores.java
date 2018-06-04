package gruporeque.sistemamuestreos;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

////////////////////////////////////


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

////////////////////


import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import static java.security.AccessController.getContext;


public class Trabajadores extends AppCompatActivity {

    public static String OperadorAModificar;
    Button btnAtrasTrabajadores;
    Button btnCrearTrabajadores;
    Button btnModificarTrabajador;
    Button btnEliminarTrabajador;
    Spinner spinnerBreteadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trabajadores);
        btnAtrasTrabajadores = (Button)findViewById(R.id.btn_AtrasTrabajadores);
        btnCrearTrabajadores=(Button)findViewById(R.id.btn_TrabajadoresCrearTrabajador);
        btnModificarTrabajador=(Button)findViewById(R.id.btn_ModificarTrabajador);
        btnEliminarTrabajador=(Button)findViewById(R.id.btn_EliminarTrabajador);
        spinnerBreteadores = (Spinner)findViewById(R.id.spinnerTrabajadores);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, obtenerTrabajadores());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBreteadores.setAdapter(arrayAdapter);

        btnEliminarTrabajador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarTrabajador();
            }
        });

        btnModificarTrabajador.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                modificarTrabajador();
            }
        });

        btnAtrasTrabajadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirMenuAdministrador = new Intent(Trabajadores.this,MenuAdministrador.class);
                startActivity(abrirMenuAdministrador);
            }
        });
        btnCrearTrabajadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirVentanaCrearTrabajador = new Intent(Trabajadores.this,crear_trabajador.class);
                startActivity(abrirVentanaCrearTrabajador);
            }
        });
    }

    private List<String> obtenerTrabajadores(){
        final List<String> arraySpinner = new ArrayList<>();
        arraySpinner.add("Lista de trabajadores");
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ClaseGlobal.Trabajadores_Select, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("value");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String trabajadoresName = jsonArray.getJSONObject(i).get("APODO").toString();
                        trabajadoresName = trabajadoresName + "   ";
                        trabajadoresName = trabajadoresName + jsonArray.getJSONObject(i).get("ID_OPERADOR").toString();
                        if (trabajadoresName.contains("_")) trabajadoresName = trabajadoresName.replaceAll("_", " ");
                        arraySpinner.add(trabajadoresName);
                    }
                } catch (JSONException e) { e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorMessageDialog("No se puede conectar al servidor.");
            }
        }); queue.add(stringRequest);
        return arraySpinner;
    }

    private void modificarTrabajador(){
        if(!spinnerBreteadores.getSelectedItem().toString().equals("Lista de trabajadores")){
            String[] separado = spinnerBreteadores.getSelectedItem().toString().split("   ");
            OperadorAModificar=separado[1];
            Intent abrirModificar = new Intent(Trabajadores.this,modificar_trabajador.class);
            startActivity(abrirModificar);
        }
        else{
            errorMessageDialog("Seleccione un trabajador para poder modificarlo.");
        }
    }

    private void eliminarTrabajador(){
        if(!spinnerBreteadores.getSelectedItem().toString().equals("Lista de trabajadores")){
            String[] separado = spinnerBreteadores.getSelectedItem().toString().split("   ");
            deleteTrabajador(ClaseGlobal.Eliminar_Trabajador + "?IdTrabajador=" + separado[1]);
        }
        else{
            errorMessageDialog("Seleccione un trabajador para poder eliminarlo");
        }
    }

    private void deleteTrabajador(String URL){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                deleteTrabajadorAux(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorMessageDialog("No se pudo conectar al servidor");
            }
        });queue.add(stringRequest);
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

    private void deleteTrabajadorAux(String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            if(jsonObject.getString("status").equals("false") ) errorMessageDialog("No se ha podido eliminar el trabajador");
            else correctMessageDialog("Se ha eliminado el trabajador exitosamente");
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

}
