package cl.inacap.misConciertos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cl.inacap.misConciertos.dao.EventosDAO;
import cl.inacap.misConciertos.dto.Evento;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    EventosDAO eventos = new EventosDAO();

    public CustomAdapter(Context context, EventosDAO eventos) {
        this.context = context;
        this.eventos = eventos;
    }

    @Override
    public int getCount() {
        return eventos.getAll().size();
    }

    @Override
    public Object getItem(int i) {
        return eventos.getAll().get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Evento item = (Evento)getItem(i);

        view = LayoutInflater.from(context).inflate(R.layout.listview_evento,null);
        ImageView iconoImg = (ImageView)view.findViewById(R.id.iconoImg);
        TextView fechaListTxt = (TextView)view.findViewById(R.id.fechaListTxt);
        TextView nombreListTxt = (TextView)view.findViewById(R.id.nombreListTxt);
        TextView valorListTxt = (TextView)view.findViewById(R.id.valorListTxt);

        iconoImg.setImageResource(item.getCalificacion());
        fechaListTxt.setText("Fecha: "+item.getFecha());
        nombreListTxt.setText("Nombre: "+item.getNombre());
        valorListTxt.setText("Valor: "+item.getValor());



        return view;
    }
}