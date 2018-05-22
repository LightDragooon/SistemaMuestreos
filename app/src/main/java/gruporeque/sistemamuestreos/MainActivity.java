package gruporeque.sistemamuestreos;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Connection;

public class MainActivity extends AppCompatActivity {

    Button btn_IniciarSesion;
    EditText txt_Usuario,txt_Password;

    // Variables para la conexión
   // Connection con;
    String un,pass,db,ip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_IniciarSesion = (Button)findViewById(R.id.btn_login);
        txt_Usuario= (EditText)findViewById(R.id.txt_usuario);
        txt_Password= (EditText)findViewById(R.id.txt_contraseña);

        ip="Server ip";
        db="";
        pass="";
        un="";



        btn_IniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent siguiente = new Intent(MainActivity.this, MenuAdministrador.class);
                startActivity(siguiente);
            }
        });
    }

   /* public class CheckLogin extends AsyncTask<String,String,String>{
        String z = "";
        boolean exito = false;

        protected String doInBackground(String... params){
            String userName = txt_Usuario.getText().toString();
            String password = txt_Password.getText().toString();
            if(userName.trim().equals("")||password.trim().equals("")){

            }
        }
    }*/
}
