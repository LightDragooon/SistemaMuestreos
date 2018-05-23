package gruporeque.sistemamuestreos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Usuarios extends AppCompatActivity {

    Button btnCrearUsuario,btnAtrasUsuarios;
    ListView listvUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);

        btnAtrasUsuarios = (Button) findViewById(R.id.btn_AtrasUsuarios);
        btnCrearUsuario = (Button) findViewById(R.id.btn_UsuariosCrearUsuario);
        listvUsuarios = (ListView)findViewById(R.id.list_Usuarios);

        btnAtrasUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirMenuAdministrador = new Intent(Usuarios.this,MenuAdministrador.class);
                startActivity(abrirMenuAdministrador);
            }
        });

        btnCrearUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirCrearUsuario = new Intent(Usuarios.this,CrearUsuario.class);
                startActivity(abrirCrearUsuario);
            }
        });
    }
}
