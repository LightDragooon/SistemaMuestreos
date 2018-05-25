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
import android.widget.Toast;

import static java.security.AccessController.getContext;


public class Trabajadores extends AppCompatActivity {

    Button btnAtrasTrabajadores;
    Button btnCrearTrabajadores;
    Button btnModificarTrabajador;
    ListView listTrabajadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trabajadores);
        btnAtrasTrabajadores = (Button)findViewById(R.id.btn_AtrasTrabajadores);
        btnCrearTrabajadores=(Button)findViewById(R.id.btn_TrabajadoresCrearTrabajador);
        btnModificarTrabajador=(Button)findViewById(R.id.btn_ModificarTrabajador);
        listTrabajadores = (ListView)findViewById(R.id.listview_Trabajadores);

        List<String> listaAux = new ArrayList<>();
        listaAux.add("Lista de trabajadores");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listaAux);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        listTrabajadores.setAdapter(arrayAdapter);

        /*listTrabajadores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                errorMessageDialog("Me seleccionaron");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                errorMessageDialog("No me seleccionaron");
            }
        });*/

        btnModificarTrabajador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        String URL = ClaseGlobal.Usuarios_Select;
        final List<String> arraySpinner = new ArrayList<>();
        arraySpinner.add("Lista de trabajadores");
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://sistemamuestreos.000webhostapp.com/Queries/seleccionar_trabajadores.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("value");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String trabajadoresName = jsonArray.getJSONObject(i).get("APODO").toString();
                        if (trabajadoresName.contains("_")) trabajadoresName = trabajadoresName.replaceAll("_", " ");
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
