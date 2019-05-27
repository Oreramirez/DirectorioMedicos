package com.example.edyjr.medicosapp.Pacientes;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.edyjr.medicosapp.R;

import java.util.ArrayList;

public class ListarPaciente extends AppCompatActivity {

    private ListView lv;
    String codoc;

    ArrayList<String> lista;
    ArrayAdapter adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_paciente);
        lv = (ListView)findViewById(R.id.lv);



        PacientesDbHelper db = new PacientesDbHelper(getApplicationContext(),"administracion",null,1);
        lista = llenar_lv();
        adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1,lista);
        lv.setAdapter(adaptador);
    }

    public ArrayList llenar_lv(){

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            if(bundle.getString("some")!= null){
                //Toast.makeText(getApplicationContext(), "data:" + bundle.getString("some"),Toast.LENGTH_SHORT).show();
                codoc = bundle.getString("some");
            }
        }


        PacientesDbHelper admin = new PacientesDbHelper(ListarPaciente.this,"administracion",null,1);
        ArrayList<String> lista = new ArrayList<>();
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String q = "Select * from pacientes where codoc='"+codoc+"'";
        //String q = "Select * from pacientes";
        Cursor registro = BaseDeDatos.rawQuery(q,null);
        if(registro.moveToFirst()){
            do{
                lista.add(registro.getString(1));
            }while(registro.moveToNext());
        }
        return lista;
    }
}
