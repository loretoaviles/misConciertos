package cl.inacap.misConciertos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cl.inacap.misConciertos.dao.EventosDAO;
import cl.inacap.misConciertos.dto.Evento;

public class MainActivity extends AppCompatActivity {
    private EditText nombreTxt;
    private Button fechaBtn;
    private Button registrarBtn;
    private TextView fechaTxt;
    private EditText valorTxt;
    private int dia, mes, anio;
    private Spinner generoSp;
    private Spinner calificacionSp;
    private ListView eventosLv;
    private CustomAdapter adaptador;
    EventosDAO eventos = new EventosDAO();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adaptador = new CustomAdapter(this, eventos);
        this.eventosLv = findViewById(R.id.eventosLv);
        eventosLv.setAdapter(adaptador);
        this.nombreTxt = findViewById(R.id.nombreTxt);
        this.valorTxt = findViewById(R.id.valorTxt);
        this.fechaBtn = findViewById(R.id.fechaBtn);
        this.fechaTxt = findViewById(R.id.fechaTxt);


        this.registrarBtn = findViewById(R.id.registrarBtn);
        //Spinner generos
        this.generoSp = findViewById(R.id.generoSp);
        String[] generos = {"Rock", "Jazz", "Pop", "Reguetoon", "Salsa", "Metal"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, generos);
        generoSp.setAdapter(adapter);
        generoSp.isShown();
        //Spinner calificacion
        this.calificacionSp = findViewById(R.id.calificacionSp);
        String[] calificaciones = {"1", "2", "3", "4", "5", "6", "7"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, calificaciones);
        calificacionSp.setAdapter(adapter2);
        //calendario
        this.fechaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == fechaBtn) {
                    final Calendar c = Calendar.getInstance();
                    dia = c.get(Calendar.DAY_OF_MONTH);
                    mes = c.get(Calendar.MONTH);
                    anio = c.get(Calendar.YEAR);

                    final Calendar calendario = Calendar.getInstance();
                    calendario.set(dia, mes, anio);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext()
                            , new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int d, int m, int y) {
                            Calendar calendario = Calendar.getInstance();
                            calendario.set(d, m, y);

                            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                            String strDate = format.format(calendario.getTime());
                            fechaTxt.setText(strDate);
                        }
                    }
                            , anio, mes, dia);
                    datePickerDialog.show();
                    fechaTxt.setVisibility(View.VISIBLE);

                }
            }

        });
        this.registrarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Evento e = new Evento();
                List<String> errores = new ArrayList<>();
                String nombreStr = nombreTxt.getText().toString().trim();
                if (nombreStr.length() == 0) {
                    errores.add("Debe Ingresar Nombre");
                }
                String fechaStr = fechaTxt.getText().toString();
                if (fechaStr.length() == 0) {
                    errores.add("Debe Ingresar Fecha");
                }
                String valorStr = valorTxt.getText().toString().trim();
                int valor = 0;
                try {
                    valor = Integer.parseInt(valorStr);
                    if (valor < 1) {
                        throw new NumberFormatException();
                    }

                } catch (Exception ex) {
                    errores.add("El valor debe ser mayor que 0");

                }


                if (errores.isEmpty()) {
                    e.setNombre(nombreTxt.getText().toString());
                    e.setFecha(fechaTxt.getText().toString());
                    e.setGenero(generoSp.getSelectedItem().toString());
                    e.setValor(valorTxt.getText().toString());
                    e.setCalificacion(Integer.parseInt(calificacionSp.getSelectedItem().toString()));


                    if (e.getCalificacion() <= 3) {
                        e.setCalificacion(R.drawable.triste);
                    } else if (e.getCalificacion() <= 5) {
                        e.setCalificacion(R.drawable.neutral);
                    } else if (e.getCalificacion() > 5)
                        e.setCalificacion(R.drawable.feliz);
                    eventos.add(e);
                    adaptador.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Concierto registrado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    mostrarErrores(errores);
                }
            }
        });
    }

    private void mostrarErrores(List<String> errores) {
        String mensaje = "";
        for (String e : errores) {
            mensaje += "" + e + "\n";
        }
        AlertDialog.Builder aleBuilder = new AlertDialog.Builder(MainActivity.this);
        aleBuilder.setTitle("Error de validación")//define el titulo
                .setMessage(mensaje)//define el mensaje del dialogo
                .setPositiveButton("Aceptar", null)//agrega el boton aceptar
                .create()//crea el alert
                .show();//lo muestra

    }
}