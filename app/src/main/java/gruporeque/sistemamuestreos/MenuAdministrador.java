package gruporeque.sistemamuestreos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuAdministrador extends AppCompatActivity {

    Button btn_usuarios,btn_salir,btn_trabajadores, btn_proyectos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_administrador);
        btn_usuarios = (Button)findViewById(R.id.btn_Usuarios);
        btn_salir = (Button)findViewById(R.id.btn_SalirAdm);
        btn_trabajadores=(Button)findViewById(R.id.btn_Trabajadores);
        btn_proyectos = (Button)findViewById(R.id.btn_Proyectos);
        btn_usuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirCrearUsuario = new Intent(MenuAdministrador.this,Usuarios.class);
                startActivity(abrirCrearUsuario);

            }
        });
        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirLogin = new Intent(MenuAdministrador.this,MainActivity.class);
                startActivity(abrirLogin);
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
