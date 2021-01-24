package mx.tecnm.tepic.ladm_practica2_amor_al_91111

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.os.FileObserver.CREATE


class BaseDatos(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase) {
        try {
            db.execSQL("CREATE TABLE HOROSCOPOS(NOMBRE VARCHAR(50),MES VARCHAR(40))")
        } catch (error: SQLiteException) {
            //SE DISPARA SI HAY UN ERROR EN EXECSQL
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}