package com.example.edyjr.medicosapp.Pacientes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.edyjr.medicosapp.R;

public class GestionarPacientes extends AppCompatActivity {

    String docod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_pacientes);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            if(bundle.getString("some")!= null){
                //Toast.makeText(getApplicationContext(), "data:" + bundle.getString("some"),Toast.LENGTH_SHORT).show();
                docod = bundle.getString("some");
            }
        }

    }

    public void AcAgregar(View view){
        Intent miIntent=new Intent(GestionarPacientes.this,AgregarPaciente.class);
        miIntent.putExtra("some", docod);
        startActivity(miIntent);
    }
    public void AcBuscar(View view){
        Intent miIntent=new Intent(GestionarPacientes.this,BuscarPaciente.class);
        startActivity(miIntent);
    }

    public void Aclistar(View view){
        Intent miIntent=new Intent(GestionarPacientes.this,ListarPaciente.class);
        miIntent.putExtra("some", docod);
        startActivity(miIntent);
    }

    public void Aceliminar(View view){
        Intent miIntent=new Intent(GestionarPacientes.this,EliminarPaciente.class);

        startActivity(miIntent);
    }
    public void AcMod(View view){
        Intent miIntent=new Intent(GestionarPacientes.this,ModificarPaciente.class);
        startActivity(miIntent);
    }
}
