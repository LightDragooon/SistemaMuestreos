package gruporeque.sistemamuestreos;

import android.app.DatePickerDialog;;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class crear_proyecto extends AppCompatActivity implements View.OnClickListener{

    private static final String CERO = "0";
    private static final String GUION = "-";

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    //Widgets
    EditText et_identificador, et_desc, et_cliente, et_fechaI;
    ImageButton ibObtenerFecha;
    Button btnCrearProyecto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_proyecto);

        et_identificador = (EditText)findViewById(R.id.et_crearProyecto_identificador);
        et_desc = (EditText)findViewById(R.id.et_crearProyecto_desc);
        et_cliente = (EditText)findViewById(R.id.et_crearProyecto_cliente);


        //Widget EditText donde se mostrara la fecha obtenida
        et_fechaI = (EditText) findViewById(R.id.et_crearProyecto_finicio);
        //Widget ImageButton del cual usaremos el evento clic para obtener la fecha
        ibObtenerFecha = (ImageButton) findViewById(R.id.ibtn_crearProyecto_calendario);
        //Evento setOnClickListener - clic
        ibObtenerFecha.setOnClickListener(this);

        btnCrearProyecto = (Button) findViewById(R.id.btn_crearNuevoProyecto);
        btnCrearProyecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearNuevoProyecto();
            }
        });
    }

    private void crearNuevoProyecto(){
        if(!et_identificador.getText().toString().equals("")&&
                !et_desc.getText().toString().equals("")&&
                !et_cliente.getText().toString().equals("")&&
                !et_fechaI.getText().toString().equals("")){
            //Hacer el php
            guardarProyecto(ClaseGlobal.Proyecto_Insert+
                    "?NOMBRE_PROYECTO="+repairStringReverse(et_identificador.getText().toString())+
                    "&DESCRIPCION="+repairStringReverse(et_desc.getText().toString())+
                    "&CLIENTE="+repairStringReverse(et_cliente.getText().toString())+
                    "&FECHA_INICIO="+repairStringReverse(et_fechaI.getText().toString())+
                    "&FECHA_FIN="+repairStringReverse(et_fechaI.getText().toString()));
        }else{
            errorMessageDialog("Llene todos las casillas para crear el proyecto");
        }
    }

    private void guardarProyecto(String URL){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getString("status").equals("false") ) errorMessageDialog("No se podido crear el proyecto");
                    else correctMessageDialog("Se ha creado el proyecto exitosamente");
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorMessageDialog("No se pudo conectar al servidor");
            }
        });queue.add(stringRequest);
    }

    private String repairStringReverse (String s){
        if (s.contains(" "))
            s = s.replaceAll(" ", "_");
        return s;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ibtn_crearProyecto_calendario:
                obtenerFecha();
                break;
        }
    }

    private void obtenerFecha(){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                et_fechaI.setText(year + GUION + mesFormateado + GUION + diaFormateado);


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        },anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();

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
