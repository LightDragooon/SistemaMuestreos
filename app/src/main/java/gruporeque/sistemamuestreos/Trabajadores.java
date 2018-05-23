package gruporeque.sistemamuestreos;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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


public class Trabajadores extends AppCompatActivity {

    Button btnAtrasTrabajadores;
    Button btnCrearTrabajadores;
    ListView listTrabajadores;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trabajadores);
        btnAtrasTrabajadores = (Button)findViewById(R.id.btn_AtrasTrabajadores);
        btnCrearTrabajadores=(Button)findViewById(R.id.btn_TrabajadoresCrearTrabajador);
        listTrabajadores = (ListView)findViewById(R.id.listview_Trabajadores);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_activated_1, obtenerTrabajadores());
        listTrabajadores.setAdapter(arrayAdapter);


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
        String URL = ClaseGlobal.Trabajadores_Select;
        final List<String> arraySpinner = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("value");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String trabajadoresName = jsonArray.getJSONObject(i).get("APODO").toString();
                        //if (trabajadoresName.contains("_")) trabajadoresName = trabajadoresName.replaceAll("_", " ");
                        arraySpinner.add(trabajadoresName);
                    }
                } catch (JSONException e) { e.printStackTrace();
                errorMessageDialog("No sirvo");}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorMessageDialog("No se puede conectar al servidor.");
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


}
