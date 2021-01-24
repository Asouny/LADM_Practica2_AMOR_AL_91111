package mx.tecnm.tepic.ladm_practica2_amor_al_91111

import android.content.pm.PackageManager
import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val siPermiso = 1
    val siPermisoReceiver = 2
    val siPermisoLectura = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //permiso para recibir mensajes
        if(ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.RECEIVE_SMS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.RECEIVE_SMS),siPermisoReceiver)
        }

        if(ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.READ_SMS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.READ_SMS),siPermisoLectura)
        }

        if(ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED) {
            //solicitamos el permiso en caso de queno esté otorgado
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.SEND_SMS), siPermiso
            )
        }


        button.setOnClickListener {
            guardarEnBD()
        }

        button2.setOnClickListener {
            mostrar()
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==siPermiso){
            //al mandar el permiso de forma imediata llama el metodo

        }
        //evaluamos si se concedió el permiso para recibir
        if(requestCode==siPermisoReceiver){
            //se manda mensjae de que si se dió el permiso

        }
        if(requestCode==siPermisoLectura){

        }
    }

    fun guardarEnBD(){

        var horoscopo = txtHoroscopo.text.toString()
        var mes = txtMes.text.toString()
        try{
            var baseDatos = BaseDatos (this, "BaseHoroscopos", null,5)

            var insertar = baseDatos.writableDatabase
            var SQL = "INSERT INTO HOROSCOPOS VALUES('${horoscopo}','${mes}')"

            insertar.execSQL(SQL)
            baseDatos.close()

            mensaje("se insertó correctamente")
            txtHoroscopo.setText("")
            txtMes.setText("")


        }catch (error: SQLiteException){
            mensaje(error.message.toString())
        }
    }

    fun mostrar(){
        try {
            val cursor = BaseDatos(this, "BaseHoroscopos",null, 5)
                    .readableDatabase
                    .rawQuery("SELECT * FROM HOROSCOPOS",null)
            var resultado = ""

            if(cursor!!.moveToFirst()){
                //var h = cursor.getString(0)
                do{
                    resultado += cursor.getString(0)+"------"+cursor.getString(1)+"\n"

                }while (cursor.moveToNext())

            }else{
                resultado = "no hay horoscopos guardados"
            }
            textView2.setText(resultado)

        }catch(error:SQLiteException) {
            mensaje(error.message.toString())
        }
    }

    fun mensaje(mensaje:String){
        AlertDialog.Builder(this)
                .setMessage(mensaje)
                .show()

    }

}
