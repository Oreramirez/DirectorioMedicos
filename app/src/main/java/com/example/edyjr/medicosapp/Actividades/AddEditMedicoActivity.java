package com.example.edyjr.medicosapp.Actividades;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.edyjr.medicosapp.Fragmentos.AddEditMedicoFragment;
import com.example.edyjr.medicosapp.R;

public class AddEditMedicoActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_MEDICO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceSatate){
        super.onCreate(savedInstanceSatate);
        setContentView(R.layout.activity_add_edit_medico);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String medicoId = getIntent().getStringExtra(MainActivity.EXTRA_MEDICO_ID);

        setTitle(medicoId == null ?
                "Añadir medico":
                "Editar Medico");

        AddEditMedicoFragment addEditMedicoFragment = (AddEditMedicoFragment)getSupportFragmentManager().findFragmentById(R.id.add_edit_medico_container);
        if(addEditMedicoFragment == null){
            addEditMedicoFragment = AddEditMedicoFragment.newInstance(medicoId);
            getSupportFragmentManager().beginTransaction().add(R.id.add_edit_medico_container,addEditMedicoFragment).commit();
        }

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Snackbar
                        .make(view
                                , "Replace with your own action"
                                , Snackbar.LENGTH_LONG)
                        .setAction("Action",null)
                        .show();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

}
