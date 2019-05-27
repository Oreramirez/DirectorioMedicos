package com.example.edyjr.medicosapp.Fragmentos;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.edyjr.medicosapp.Datos.Medicos;
import com.example.edyjr.medicosapp.Datos.MedicosDbHelper;
import com.example.edyjr.medicosapp.R;

public class AddEditMedicoFragment extends Fragment {
    private static final String ARG_MEDICO_ID = "arg_medico_id";

    private String mMedicoId;

    private MedicosDbHelper mMedicosDbHelper;

    private FloatingActionButton mSaveButton;
    private TextInputEditText mNameField;
    private TextInputEditText mPhoneNumberField;
    private TextInputEditText mSpecialtyField;
    private TextInputEditText mBioField;
    private TextInputLayout mNameLabel;
    private TextInputLayout mPhoneNumberLabel;
    private TextInputLayout mSpecialtyLabel;
    private TextInputLayout mBioLabel;

    public AddEditMedicoFragment(){
        //Required empty public constructor
    }

    public static AddEditMedicoFragment newInstance(String medicoId){
        AddEditMedicoFragment fragment = new AddEditMedicoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MEDICO_ID,medicoId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments() !=null){
            mMedicoId = getArguments().getString(ARG_MEDICO_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_add_edit_medico, container, false);

        //referencia UI
        mSaveButton = (FloatingActionButton)getActivity().findViewById(R.id.fab);
        mNameField = (TextInputEditText)root.findViewById(R.id.et_name);
        mPhoneNumberField = (TextInputEditText)root.findViewById(R.id.et_phone_number);
        mSpecialtyField = (TextInputEditText)root.findViewById(R.id.et_specialty);
        mBioField = (TextInputEditText)root.findViewById(R.id.et_bio);
        mNameLabel = (TextInputLayout)root.findViewById(R.id.til_name);
        mPhoneNumberLabel = (TextInputLayout)root.findViewById(R.id.til_phone_number);
        mSpecialtyLabel = (TextInputLayout)root.findViewById(R.id.til_specialty);
        mBioLabel = (TextInputLayout)root.findViewById(R.id.til_bio);

        mSaveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                addEditMedico();
            }
        });

        //eventos

        mMedicosDbHelper = new MedicosDbHelper(getActivity());

        //carga de datos
        if(mMedicoId != null){
            loadMedico();
        }
        return root;
    }

    private void loadMedico(){

        new GetMedicoByIdTask().execute();
    }

    private void showMedico(Medicos medico){
        mNameField.setText(medico.getName());
        mPhoneNumberField.setText(medico.getPhoneNumber());
        mSpecialtyField.setText(medico.getSpeciality());
        mBioField.setText(medico.getBio());
    }

    private void showLoadError(){
        Toast.makeText(getActivity(),"Error al editar medico",Toast.LENGTH_SHORT).show();
    }

    private class GetMedicoByIdTask extends AsyncTask<Void,Void,Cursor> {
        @Override
        protected Cursor doInBackground(Void...params){
            return mMedicosDbHelper.getMedicoById(mMedicoId);
        }

        @Override
        protected void onPostExecute(Cursor c){
            if(c != null && c.moveToLast()){
                showMedico(new Medicos(c));
            }else{
                showLoadError();
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        }
    }

    private class AddEditMedicoTask extends AsyncTask<Medicos,Void,Boolean>{

        @Override
        protected Boolean doInBackground(Medicos...medicos){
            if(mMedicoId != null){
                return mMedicosDbHelper.updateMedico(medicos[0],mMedicoId)>0;
            }else{
                return mMedicosDbHelper.saveMedico(medicos[0])>0;
            }
        }

        @Override
        protected void onPostExecute(Boolean result){

            showMedicosScreen(result);
        }
    }

    private void showMedicosScreen(Boolean requery){
        if(!requery) {
            showAddEditError();
            getActivity().setResult(Activity.RESULT_CANCELED);
        }else{
            getActivity().setResult(Activity.RESULT_OK);
        }
        getActivity().finish();
    }

    private void showAddEditError(){
        Toast.makeText(getActivity(),"Error al agregar nueva informacion",Toast.LENGTH_SHORT).show();
    }

    private void addEditMedico(){
        boolean error = false;

        String name = mNameField.getText().toString();
        String phoneNumber = mPhoneNumberField.getText().toString();
        String specialty = mSpecialtyField.getText().toString();
        String bio = mBioField.getText().toString();

        if(TextUtils.isEmpty(name)){
            mNameLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(TextUtils.isEmpty(phoneNumber)){
            mPhoneNumberLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(TextUtils.isEmpty(specialty)){
            mSpecialtyLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(TextUtils.isEmpty(bio)){
            mBioLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(error){
            return;
        }
        Medicos medico = new Medicos(name,specialty,phoneNumber,bio,"");
        new AddEditMedicoTask().execute(medico);
    }
}
