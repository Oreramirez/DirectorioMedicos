package com.example.edyjr.medicosapp.Fragmentos;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.edyjr.medicosapp.Actividades.AddEditMedicoActivity;
import com.example.edyjr.medicosapp.Actividades.MainActivity;
import com.example.edyjr.medicosapp.Actividades.MedicoDetailActivity;
import com.example.edyjr.medicosapp.Datos.MedicosContract;
import com.example.edyjr.medicosapp.Datos.MedicosCursorAdapter;
import com.example.edyjr.medicosapp.Datos.MedicosDbHelper;
import com.example.edyjr.medicosapp.R;

public class MedicosFragment extends Fragment {
    public static final int REQUEST_UPDATE_DELETE_MEDICO = 2;

    private MedicosDbHelper mMedicosDbHelper;

    private ListView mMedicosList;
    private MedicosCursorAdapter mMedicosAdapter;
    private FloatingActionButton mAddButton;

    public MedicosFragment() {
        // Required empty public constructor
    }

    public static MedicosFragment newInstance(){

        return new MedicosFragment();
    }

    // TODO: Rename and change types and number of parameters
    public static MedicosFragment newInstance(String param1, String param2) {
        return new MedicosFragment();
    }

    private void showDetailScreen(String medicoId){
        Intent intent = new Intent(getActivity(), MedicoDetailActivity.class);
        intent.putExtra(MainActivity.EXTRA_MEDICO_ID,medicoId);
        startActivityForResult(intent,REQUEST_UPDATE_DELETE_MEDICO);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Log.e("Aqui","Aqui2");
        View root = inflater.inflate(R.layout.fragment_medicos, container, false);

        //referencia UI
        mMedicosList = (ListView)root.findViewById(R.id.medicos_list);
        mMedicosAdapter = new MedicosCursorAdapter(getActivity(),null);
        mAddButton = (FloatingActionButton)getActivity().findViewById(R.id.fab);

        mAddButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                showAddScreen();
            }
        });

        //configurar
        mMedicosList.setAdapter(mMedicosAdapter);

        // Eventos
        mMedicosList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor)mMedicosAdapter.getItem(i);

                String currentMedicoId = currentItem.getString(currentItem.getColumnIndex(MedicosContract.MedicoEntry.ID));
                Log.e("Aqui",currentMedicoId);
                showDetailScreen(currentMedicoId);
            }
        });


        //Instancia de helper
        mMedicosDbHelper = new MedicosDbHelper(getActivity());

        //cargar datos

        //Cursor cursor = mMedicosDbHelper.getAllMedicos();
        loadMedicos();

        return root;
    }

    private void loadMedicos(){
        //carga de datos
        new MedicosLoadTask().execute();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(Activity.RESULT_OK == resultCode){
            switch (requestCode){
                case AddEditMedicoActivity.REQUEST_ADD_MEDICO : showSuccessfullSavedMessage();
                    loadMedicos();
                    break;
                case REQUEST_UPDATE_DELETE_MEDICO:
                    loadMedicos();
                    break;
            }
        }
    }

    public class MedicosLoadTask extends AsyncTask<Void,Void,Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mMedicosDbHelper.getAllMedicos();
        }

        @Override
        protected void onPostExecute(Cursor c){
            if(c != null && c.getCount()>0){
                mMedicosAdapter.swapCursor(c);
            }
            else{
                //Mostrar un estado vacio nullo
            }
        }
    }

    private void showAddScreen(){
        Intent intent = new Intent(getActivity(), AddEditMedicoActivity.class);
        startActivityForResult(intent, AddEditMedicoActivity.REQUEST_ADD_MEDICO);
    }

    private void showSuccessfullSavedMessage(){
        Toast.makeText(getActivity(),"Medico guardado correctamente", Toast.LENGTH_SHORT).show();
    }
}
