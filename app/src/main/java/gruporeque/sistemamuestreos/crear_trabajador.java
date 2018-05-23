package gruporeque.sistemamuestreos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class crear_trabajador extends AppCompatActivity {

    Button btnCrearTrabajador;
    Button btnAtrasCrearTrabajador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_trabajador);
        //getActionBar().hide();
        btnCrearTrabajador = (Button)findViewById(R.id.btn_CrearTrabajador);
        btnAtrasCrearTrabajador = (Button)findViewById(R.id.btn_AtrasCrearTrabajador);
        btnAtrasCrearTrabajador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirMenuAdministrador = new Intent(crear_trabajador.this,Trabajadores.class);
                startActivity(abrirMenuAdministrador);
            }
        });
    }
}
