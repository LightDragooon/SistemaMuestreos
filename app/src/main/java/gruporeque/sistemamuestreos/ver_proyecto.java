package gruporeque.sistemamuestreos;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

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

public class ver_proyecto extends AppCompatActivity {
    TextView nombreP, cliente, fechaI, fechaF, desc;
    ImageButton verAnalistas, verTrabajadores, verOperaciones;
    int idProyecto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_proyecto);

        idProyecto = getIntent().getExtras().getInt("id");

        nombreP = (TextView) findViewById(R.id.tv_verProyecto_nombreP);
        cliente = (TextView) findViewById(R.id.tv_verProyecto_cliente);
        fechaI = (TextView) findViewById(R.id.tv_verProyecto_fechaI);
        fechaF = (TextView) findViewById(R.id.tv_verProyecto_fechaF);
        desc = (TextView) findViewById(R.id.tv_verProyecto_desc);

        verAnalistas = (ImageButton) findViewById(R.id.ibtn_verProyecto_analista);
        verTrabajadores = (ImageButton) findViewById(R.id.ibtn_verProyecto_trabajador);
        verOperaciones = (ImageButton) findViewById(R.id.ibtn_verProyecto_operaciones);

        getProyecto();
    }

    private void getProyecto() {
        String URL = ClaseGlobal.Proyectos_Select + "?Id="+Integer.toString(idProyecto);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("value");
                    nombreP.setText(jsonArray.getJSONObject(0).get("NOMBRE_PROYECTO").toString());
                    cliente.setText(jsonArray.getJSONObject(0).get("CLIENTE").toString());
                    fechaI.setText(jsonArray.getJSONObject(0).get("FECHA_INICIO").toString());
                    fechaF.setText(jsonArray.getJSONObject(0).get("FECHA_FIN").toString());
                    desc.setText(jsonArray.getJSONObject(0).get("DESCRIPCION").toString());

                } catch (JSONException e) { e.printStackTrace(); }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorMessageDialog("No se puede conectar al servidor en estos momentos.");
            }
        }); queue.add(stringRequest);
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
}
