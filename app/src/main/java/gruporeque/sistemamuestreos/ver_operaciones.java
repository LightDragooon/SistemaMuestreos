package gruporeque.sistemamuestreos;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class ver_operaciones extends AppCompatActivity {

    int idOperacion;
    TextView tv_verOperacion_nombre, tv_verOperacion_desc, tv_verOperacion_fechaI, tv_verOperacion_fechaF;
    Button btn_verTareasProdutivas, btn_verTareasColaborativas, btn_verResumenTareas, btn_verTareasImproductivas, btn_verProductividadDia, btn_verEstadistica;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_operaciones);

        idOperacion = getIntent().getExtras().getInt("id");

        tv_verOperacion_nombre = (TextView) findViewById(R.id.tv_verOperacion_nombre);
        tv_verOperacion_desc = (TextView) findViewById(R.id.tv_verOperacion_desc);
        tv_verOperacion_fechaI = (TextView) findViewById(R.id.tv_verOperacion_fechaI);
        tv_verOperacion_fechaF = (TextView) findViewById(R.id.tv_verOperacion_fechaF);

        btn_verTareasProdutivas = (Button) findViewById(R.id.btn_verTareasProdutivas);
        btn_verTareasColaborativas = (Button) findViewById(R.id.btn_verTareasColaborativas);
        btn_verTareasImproductivas = (Button) findViewById(R.id.btn_verTareasImproductivas);
        btn_verResumenTareas = (Button) findViewById(R.id.btn_verResumenTareas);
        btn_verProductividadDia = (Button) findViewById(R.id.btn_verProductividadDia);
        btn_verEstadistica = (Button) findViewById(R.id.btn_verEstadistica);

        btn_verResumenTareas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirVerElementos = new Intent(ver_operaciones.this,ver_consolidado_tareas.class);
                abrirVerElementos.putExtra("id", idOperacion);
                //abrirVerElementos.putExtra("tipo", 2);//Operaciones
                startActivity(abrirVerElementos);
            }
        });

        btn_verTareasProdutivas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirVerElementos = new Intent(ver_operaciones.this,ver_consolidado_tareas_productivas.class);
                abrirVerElementos.putExtra("id", idOperacion);
                startActivity(abrirVerElementos);
            }
        });

        btn_verTareasImproductivas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirVerElementos = new Intent(ver_operaciones.this,ver_consolidado_tareas.class);
                abrirVerElementos.putExtra("id", idOperacion);
                startActivity(abrirVerElementos);
            }
        });

        btn_verTareasColaborativas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirVerElementos = new Intent(ver_operaciones.this,ver_consolidado_tareas.class);
                abrirVerElementos.putExtra("id", idOperacion);
                startActivity(abrirVerElementos);
            }
        });

        getOperacion();
    }

    private void getOperacion() {
        String URL = ClaseGlobal.Proyectos_Operaciones_Select + "?Id=NULL" + "&IdOper="+Integer.toString(idOperacion);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("value");
                    tv_verOperacion_nombre.setText(repairString(jsonArray.getJSONObject(0).get("NOMBRE_OPERACION").toString()));
                    tv_verOperacion_desc.setText(repairString(jsonArray.getJSONObject(0).get("DESCRIPCION").toString()));
                    tv_verOperacion_fechaI.setText(repairString(jsonArray.getJSONObject(0).get("FECHA_INICIO").toString()));
                    tv_verOperacion_fechaF.setText(repairString(jsonArray.getJSONObject(0).get("FECHA_FIN").toString()));

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

    private String repairString (String s){
        if (s.contains("_"))
            s = s.replaceAll("_", " ");
        return s;
    }
}
