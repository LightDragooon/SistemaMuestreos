package gruporeque.sistemamuestreos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class Proyectos extends AppCompatActivity {

    Button btnCrearProyecto,btnAtrasProyecto;
    ListView listvProyectos;
    private ArrayAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proyectos);

        btnAtrasProyecto = (Button) findViewById(R.id.btn_AtrasProyectos);
        btnCrearProyecto = (Button) findViewById(R.id.btn_ProyectosCrearProyectos);
        listvProyectos = (ListView)findViewById(R.id.list_Proyectos);

        ArrayList<String> initialList = new ArrayList<String>();
        initialList.add("Proyecto Prueba");

        mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, initialList);
        listvProyectos.setAdapter(mAdapter);

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

        listvProyectos.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener()  {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) listvProyectos.getItemAtPosition(position);
                Log.d("yeah",item);
            }
        });
    }
}
