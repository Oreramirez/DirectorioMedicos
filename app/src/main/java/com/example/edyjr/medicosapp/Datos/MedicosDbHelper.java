package com.example.edyjr.medicosapp.Datos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MedicosDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Medicos.db";

    public MedicosDbHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public long mockMedicos(SQLiteDatabase db, Medicos medicos){
        return db.insert(MedicosContract.MedicoEntry.TABLE_NAME,null,medicos.toContentValues());
    }
    private void mockData(SQLiteDatabase sqLiteDatabase){

        mockMedicos(sqLiteDatabase,new Medicos("Jhosmell Alfaro","Medico Emergencista","300 200 1111","Gran profesional con experiencia de 5 años en servicio de emergencia","jhosmell.jpg"));

        mockMedicos(sqLiteDatabase,new Medicos("Jose Luis Quispe","Medico Internista","300 200 2222","Gran profesional con experiencia de 15 años en servicio de hospitalizacion","jose.jpg"));

        mockMedicos(sqLiteDatabase,new Medicos("Flor Condori","Medico Internista","300 200 3333","Gran profesional con experiencia de 25 años en servicio de hospitalizacion","flor.jpg"));

        mockMedicos(sqLiteDatabase,new Medicos("Guimer Coaquira","Medico Genicólogo","300 200 4444","Gran profesional con experiencia de 25 años en servicio de Genicología","guimer.jpg"));

        mockMedicos(sqLiteDatabase,new Medicos("Diana Costa","Medico Internista","300 200 5555","Gran profesional con experiencia de 25 años en servicio de hospitalizacion","diana.jpg"));

        mockMedicos(sqLiteDatabase,new Medicos("Mariluz Diaz","Medico Internista","300 200 6666","Gran profesional con experiencia de 25 años en servicio de hospitalizacion","mariluz.jpg"));

        mockMedicos(sqLiteDatabase,new Medicos("Melisa Olivares","Medico Internista","300 200 7777","Gran profesional con experiencia de 25 años en servicio de hospitalizacion","melisa.jpg"));

        mockMedicos(sqLiteDatabase,new Medicos("Kendy Chambilla","Medico Internista","300 200 8888","Gran profesional con experiencia de 25 años en servicio de hospitalizacion","kendy.jpg"));

        mockMedicos(sqLiteDatabase,new Medicos("Hugo Miranda","Medico Internista","300 200 9999","Gran profesional con experiencia de 25 años en servicio de hospitalizacion","hugo.jpg"));
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "
                + MedicosContract.MedicoEntry.TABLE_NAME + "("
                + MedicosContract.MedicoEntry._ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MedicosContract.MedicoEntry.ID
                + " TEXT NOT NULL,"
                + MedicosContract.MedicoEntry.NAME
                + " TEXT NOT NULL,"
                + MedicosContract.MedicoEntry.SPECIALTY
                + " TEXT NOT NULL,"
                + MedicosContract.MedicoEntry.PHONE_NUMBER
                + " TEXT NOT NULL,"
                + MedicosContract.MedicoEntry.BIO
                + " TEXT NOT NULL,"
                + MedicosContract.MedicoEntry.AVATAR_URI
                + " TEXT,"
                + "UNIQUE (" + MedicosContract.MedicoEntry.ID +
                "))"
        );
        //realizar inserción
        mockData(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //Implementacion del CRUD
    public Cursor getAllMedicos(){
        return getReadableDatabase()
                .query(MedicosContract.MedicoEntry.TABLE_NAME
                        ,null // columnas
                        ,null // WHERE
                        ,null // valores WHERE
                        ,null // GROUP BY
                        ,null // HAVING
                        ,null); // OREDER BY
    }
    public Cursor getMedicoById(String medicoId){
        return getReadableDatabase()
                .query(MedicosContract.MedicoEntry.TABLE_NAME
                        ,null
                        , MedicosContract.MedicoEntry.ID + " LIKE ? "
                        ,new String[] {medicoId}
                        ,null
                        ,null
                        ,null);
    }

    //insertar
    public long saveMedico(Medicos medicos){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(
                MedicosContract.MedicoEntry.TABLE_NAME
                ,null
                ,medicos.toContentValues());
    }

    //actualizar
    public int updateMedico(Medicos medicos
            ,String medicoId){
        return getWritableDatabase().update(
                MedicosContract.MedicoEntry.TABLE_NAME
                ,medicos.toContentValues()
                , MedicosContract.MedicoEntry.ID+" LIKE ? "
                ,new String[]{medicoId}
        );
    }

    //eliminar
    public int deleteMedico(String medicoId){
        return getWritableDatabase().delete(
                MedicosContract.MedicoEntry.TABLE_NAME
                , MedicosContract.MedicoEntry.ID+" LIKE ? "
                ,new String[]{medicoId}
        );
    }
}
