package gruporeque.sistemamuestreos;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class modificar_trabajador extends AppCompatActivity {

    Button btnAtras;
    Button btnModificar;
    EditText txtApodo, txtOcupacion;
    String Id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_trabajador);

        btnAtras = (Button)findViewById(R.id.btn_AtrasModificarTrabajador);
        btnModificar = (Button)findViewById(R.id.btn_ModificarTrabajador);
        txtApodo = (EditText)findViewById(R.id.txt_ModificarApodo);
        txtOcupacion= (EditText)findViewById(R.id.txt_ModificarOcupacion);
        Id = Trabajadores.OperadorAModificar;
        errorMessageDialog(Id);
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarTrabajador1();
            }
        });


        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirTrabajadores = new Intent(modificar_trabajador.this,Trabajadores.class);
                startActivity(abrirTrabajadores);
            }
        });
    }

    private void modificarTrabajador1(){
        if(!txtOcupacion.getText().equals("")&&!txtApodo.getText().equals("")){
            modificarTrabajador(ClaseGlobal.Modificar_Trabajador + "?IdOperador="+Id+"&Apodo="+txtApodo.getText().toString()+
            "&Ocupacion="+txtOcupacion.getText().toString());
        }
        else{
            errorMessageDialog("Rellene todas las casillas para modificar el trabajador");
        }
    }

    private void modificarTrabajador(String URL){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                modificarTrabajadorAux(response);
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

    private void modificarTrabajadorAux(String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            if(jsonObject.getString("status").equals("false") ) errorMessageDialog("No se ha podido modificar el trabajador");
            else correctMessageDialog("Se ha modificado el trabajador exitosamente");
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
