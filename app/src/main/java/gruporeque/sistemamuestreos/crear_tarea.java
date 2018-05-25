package gruporeque.sistemamuestreos;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class crear_tarea extends AppCompatActivity {

    Button btn_new;
    ImageButton btn_out;
    EditText txt_name,txt_desc;
    RadioButton radioButtonTP, radioButtonTC, radioButtonTI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_tarea);
        btn_out = findViewById(R.id.imageButtonSalir);
        btn_new = findViewById(R.id.buttonCrearTarea);
        radioButtonTP = findViewById(R.id.radioButtonTP);
        radioButtonTC = findViewById(R.id.radioButtonTC);
        radioButtonTI = findViewById(R.id.radioButtonTI);
        txt_name = findViewById(R.id.editTextNombreTarea);
        txt_desc = findViewById(R.id.editTextDescTarea);
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearTarea();
            }
        });

        btn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void crearTarea(){
        if(!txt_name.getText().toString().equals("")&&!txt_desc.getText().toString().equals("")){
            if(radioButtonTP.isChecked()) {
                createWork(ClaseGlobal.Tarea_Insert+
                        "?Nombre="+txt_name.getText().toString()+
                        "&Descripcion="+txt_desc.getText().toString()+
                        "&Tipo_Tarea=TP");
            }
            if(radioButtonTC.isChecked()) {
                createWork(ClaseGlobal.Tarea_Insert+
                        "?Nombre="+txt_name.getText().toString()+
                        "&Descripcion="+txt_desc.getText().toString()+
                        "&Tipo_Tarea=TC");
            }
            if(radioButtonTI.isChecked()) {
                createWork(ClaseGlobal.Tarea_Insert+
                        "?Nombre="+txt_name.getText().toString()+
                        "&Descripcion="+txt_desc.getText().toString()+
                        "&Tipo_Tarea=TI");
            }
        }else{
            errorMessageDialog("Llene todos las casillas para crear la tarea");
        }
    }

    private void createWork(String URL){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                createUserAux(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorMessageDialog("No se pudo conectar al servidor");
            }
        });queue.add(stringRequest);
    }

    private void createUserAux(String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            if(jsonObject.getString("status").equals("false") ) errorMessageDialog("No se pudo crear la tarea");
            else correctMessageDialog("Se ha creado la tarea exitosamente");
        }catch (JSONException e){
            e.printStackTrace();
        }
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
