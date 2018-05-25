package gruporeque.sistemamuestreos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class Proyectos extends AppCompatActivity {

    Button btnCrearProyecto,btnAtrasProyecto;
    ListView listvProyectos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proyectos);

        btnAtrasProyecto = (Button) findViewById(R.id.btn_AtrasProyectos);
        btnCrearProyecto = (Button) findViewById(R.id.btn_ProyectosCrearProyectos);
        listvProyectos = (ListView)findViewById(R.id.list_Proyectos);

        btnAtrasProyecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirMenuAdministrador = new Intent(Proyectos.this,MenuAdministrador.class);
                startActivity(abrirMenuAdministrador);
            }
        });

        btnCrearProyecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirCrearProyecto = new Intent(Proyectos.this,crear_proyecto.class);
                startActivity(abrirCrearProyecto);
            }
        });
    }
}
