package gruporeque.sistemamuestreos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MenuAdministrador extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    ImageButton btn_usuarios,btn_trabajadores, btn_proyectos, btnTareas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_administrador);
        btn_usuarios = (ImageButton)findViewById(R.id.btn_Usuarios);
        //btn_salir = (ImageButton)findViewById(R.id.btn_SalirAdm);
        btn_trabajadores=(ImageButton)findViewById(R.id.btn_Trabajadores);
        btn_proyectos = (ImageButton)findViewById(R.id.btn_Proyectos);
        btnTareas = (ImageButton)findViewById(R.id.btn_Tareas);

        btnTareas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirTareas = new Intent(MenuAdministrador.this,crear_tarea.class);
                startActivity(abrirTareas);
            }
        });

        btn_usuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirCrearUsuario = new Intent(MenuAdministrador.this,Usuarios.class);
                startActivity(abrirCrearUsuario);

            }
        });
        btn_trabajadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirTrabajadores=new Intent(MenuAdministrador.this,Trabajadores.class);
                startActivity(abrirTrabajadores);
            }
        });

        btn_proyectos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirCrearProyecto = new Intent(MenuAdministrador.this,Proyectos.class);
                startActivity(abrirCrearProyecto);

            }
        });




    }
}
