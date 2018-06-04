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

public class ver_elementos extends AppCompatActivity {
    int idProyecto, tipoElemento;
    TextView tv_descSeleccion;
    Spinner spinner_elementos;
    Button btn_nuevoElemento, btn_verOperacion;
    ArrayList<Integer> idList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_elementos);
        idProyecto = getIntent().getExtras().getInt("id");
        tipoElemento = getIntent().getExtras().getInt("tipo");

        tv_descSeleccion = (TextView) findViewById(R.id.tv_descSeleccion);
        spinner_elementos = (Spinner) findViewById(R.id.spinner_elementos);
        btn_nuevoElemento = (Button) findViewById(R.id.btn_nuevoElemento);
        btn_verOperacion = (Button) findViewById(R.id.btn_verOperacion);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, getElemento());
        //arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_elementos.setAdapter(arrayAdapter);

        btn_nuevoElemento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirNuevoIntent;

                switch (tipoElemento){
                    case 0:
                        abrirNuevoIntent = new Intent(ver_elementos.this,ver_elementos.class);
                        abrirNuevoIntent.putExtra("id", idProyecto);
                        abrirNuevoIntent.putExtra("tipo", 3);//Asociar Analistas
                        startActivity(abrirNuevoIntent);
                        break;
                    case 1:
                        abrirNuevoIntent = new Intent(ver_elementos.this,ver_elementos.class);
                        abrirNuevoIntent.putExtra("id", idProyecto);
                        abrirNuevoIntent.putExtra("tipo", 4);//Asociar Trabajadores
                        startActivity(abrirNuevoIntent);
                        break;
                    case 2:
                        //Crear una nueva operacion
                        abrirNuevoIntent = new Intent(ver_elementos.this,crear_operaciones.class);
                        abrirNuevoIntent.putExtra("id", idProyecto);
                        startActivity(abrirNuevoIntent);
                        break;
                    default:
                        break;
                }

            }
        });

        btn_verOperacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int myId = spinner_elementos.getSelectedItemPosition()-1;
                if(myId == -1){
                    errorMessageDialog("Seleccione una operación antes de continuar...");
                }else {
                    Intent abrirVerOperacion = new Intent(ver_elementos.this, ver_operaciones.class);
                    abrirVerOperacion.putExtra("id", idList.get(myId));
                    startActivity(abrirVerOperacion);
                }
            }
        });
        /*
        * El tipo me determina que elemento estoy viendo
        * 0 = Analistas
        * 1 = Trabajadores
        * 2 = Operaciones
        * */

    }

    private List<String> getElemento() {
        String URL = "";
        final List<String> arraySpinner = new ArrayList<>();
        arraySpinner.add("Seleccione un Elemento");
        switch (tipoElemento){
            case 0://Analistas
                URL = ClaseGlobal.Proyectos_Usuarios_Select + "?Id="+Integer.toString(idProyecto);
                tv_descSeleccion.setText("Analistas");
                break;
            case 1://Trabajadores
                URL = ClaseGlobal.Proyectos_Select + "?Id="+Integer.toString(idProyecto);
                tv_descSeleccion.setText("Trabajadores");
                break;
            case 2://Operaciones
                URL = ClaseGlobal.Proyectos_Operaciones_Select + "?Id="+Integer.toString(idProyecto) + "&IdOper=NULL";
                btn_verOperacion.setClickable(true);
                btn_verOperacion.setVisibility(View.VISIBLE);
                tv_descSeleccion.setText("Operaciones");
                break;
            case 3://Asociar Analistas
                URL = ClaseGlobal.Usuarios_Select;
                tv_descSeleccion.setText("Asociar Analistas");
                btn_nuevoElemento.setText("Asociar");
                break;
            case 4://Asociar Trabajadores
                URL = ClaseGlobal.Trabajadores_Select;
                tv_descSeleccion.setText("Asociar Trabajadores");
                btn_nuevoElemento.setText("Asociar");
                break;

        }
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("value");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String identificadorElemento="";
                        switch (tipoElemento){
                            case 0://Analistas
                            case 3:
                                identificadorElemento = jsonArray.getJSONObject(i).get("ID_USUARIO").toString() + "-" + jsonArray.getJSONObject(i).get("NOMBRE_USUARIO").toString();
                                break;
                            case 1://Trabajadores
                            case 4:
                                identificadorElemento = jsonArray.getJSONObject(i).get("ID_OPERADOR").toString() + "-" + jsonArray.getJSONObject(i).get("APODO").toString();

                                break;
                            case 2://Operaciones
                                identificadorElemento = jsonArray.getJSONObject(i).get("ID_OPERACION").toString() + "-" + jsonArray.getJSONObject(i).get("NOMBRE_OPERACION").toString();
                                idList.add(Integer.parseInt(jsonArray.getJSONObject(i).get("ID_OPERACION").toString()));
                                break;
                        }
                        arraySpinner.add(repairString(identificadorElemento));
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

    private String repairString (String s){
        if (s.contains("_"))
            s = s.replaceAll("_", " ");
        return s;
    }
}
