package gruporeque.sistemamuestreos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Trabajadores extends AppCompatActivity {

    Button btnAtrasTrabajadores;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trabajadores);
        btnAtrasTrabajadores = (Button)findViewById(R.id.btn_AtrasTrabajadores);

        btnAtrasTrabajadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirMenuAdministrador = new Intent(Trabajadores.this,MenuAdministrador.class);
                startActivity(abrirMenuAdministrador);
            }
        });
    }
}
