package com.example.edyjr.medicosapp.Fragmentos;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.edyjr.medicosapp.Actividades.AddEditMedicoActivity;
import com.example.edyjr.medicosapp.Actividades.MainActivity;
import com.example.edyjr.medicosapp.Datos.Medicos;
import com.example.edyjr.medicosapp.Datos.MedicosDbHelper;
import com.example.edyjr.medicosapp.Pacientes.GestionarPacientes;
import com.example.edyjr.medicosapp.R;

public class MedicoDetailFragment extends Fragment {

    private static final String ARG_MEDICO_ID = "medicoId";
    private String mMedicoId;

    private CollapsingToolbarLayout mCollapsingView;
    private ImageView mAvatar;
    private TextView mPhoneNumber;
    private TextView mSpeciality;
    private TextView mBio;

    private MedicosDbHelper mMedicosDbHelper;

    public MedicoDetailFragment(){
        //Required empty public constructor
    }

    public static MedicoDetailFragment newInstance(String medicoId){
        MedicoDetailFragment fragment = new MedicoDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MEDICO_ID,medicoId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            mMedicoId = getArguments().getString(ARG_MEDICO_ID);
        }
        setHasOptionsMenu(true);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_medico_detail, container, false);
        //Gestionar Pacientes
        Button btnopen = (Button) root.findViewById(R.id.btnAgregar);
        btnopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), GestionarPacientes.class);
                in.putExtra("some", mMedicoId);
                startActivity(in);
            }
        });

        //actividad
        mCollapsingView = (CollapsingToolbarLayout)getActivity().findViewById(R.id.toolbar_layout);
        mAvatar = (ImageView)getActivity().findViewById(R.id.iv_avatar);
        //fragmento
        mPhoneNumber = (TextView)root.findViewById(R.id.tv_phone_number);
        mSpeciality = (TextView)root.findViewById(R.id.tv_specialty);
        mBio = (TextView)root.findViewById(R.id.tv_bio);
        mMedicosDbHelper = new MedicosDbHelper(getActivity());
        loadMedico();
        return root;
    }


    private void showLoadError(){
        Toast.makeText(getActivity(),"Error al cargar informacion",Toast.LENGTH_SHORT).show();
    }


    private void showMedico(Medicos medicos){
        mCollapsingView.setTitle(medicos.getName());
        Glide
                .with(this)
                .load(Uri.parse("file:///android_asset/"+ medicos.getAvatarUri()))
                //.centerCrop()
                .into(mAvatar);
        mPhoneNumber.setText(medicos.getPhoneNumber());
        mSpeciality.setText(medicos.getSpeciality());
        mBio.setText(medicos.getBio());

    }


    private class GetMedicoByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids){
            return mMedicosDbHelper.getMedicoById(mMedicoId);
        }

        @Override
        protected void onPostExecute(Cursor c){
            if(c != null && c.moveToLast()){
                showMedico(new Medicos(c));
            }else{
                showLoadError();
            }
        }
    }

    private void loadMedico() {
        new GetMedicoByIdTask().execute();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_edit:
                showEditScreen();
                break;
            case R.id.action_delete:
                new DeleteMedicoTask().execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void showDeleteError() {
        Toast.makeText(getActivity(), "Error al eliminar médico", Toast.LENGTH_SHORT).show();
    }

    private void showMedicoScreen(boolean requery){
        if(!requery){
            showDeleteError();
        }
        getActivity().setResult(requery? Activity.RESULT_OK: Activity.RESULT_CANCELED);
        getActivity().finish();
    }
    private class DeleteMedicoTask extends AsyncTask<Void,Void,Integer> {
        @Override
        protected Integer doInBackground(Void... voids) {
            return mMedicosDbHelper.deleteMedico(mMedicoId);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            showMedicoScreen(integer > 0);
        }
    }

    private void showEditScreen(){
        Intent intent = new Intent(getActivity(),AddEditMedicoActivity.class);
        intent.putExtra(MainActivity.EXTRA_MEDICO_ID,mMedicoId);
        startActivityForResult(intent, MedicosFragment.REQUEST_UPDATE_DELETE_MEDICO);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Acciones
        if(requestCode == MedicosFragment.REQUEST_UPDATE_DELETE_MEDICO){
            if(resultCode == Activity.RESULT_OK){
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        }
    }
}
