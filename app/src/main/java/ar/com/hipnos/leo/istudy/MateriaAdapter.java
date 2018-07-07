package ar.com.hipnos.leo.istudy;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ar.com.hipnos.leo.istudy.api.modell.Carrera;
import ar.com.hipnos.leo.istudy.api.modell.Materia;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MateriaAdapter extends RecyclerView.Adapter<MateriaAdapter.ViewHolder>{

    private List<Materia> materias = new ArrayList<Materia>();

    public MateriaAdapter(List<Materia> materiasList) {

        if(materiasList != null) {
            materias = materiasList;
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View view;

        @BindView(R.id.imagen)
        ImageView imagen;

        @BindView(R.id.titulo)
        TextView titulo;

        @BindView(R.id.descripcion)
        TextView descripcion;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.view = itemView;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.materia_list_item, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MateriaAdapter.ViewHolder holder, int position) {
        final Materia materia = this.materias.get(position);


        Picasso.with(holder.imagen.getContext()).setIndicatorsEnabled(true);

        Picasso.with(holder.imagen.getContext())
                .load(materia.getImagen())
                .into(holder.imagen);

//        final JSONObject jObject;
//        try {
//            if(materia.getCorrelativas() != null)
//                jObject = new JSONObject(materia.getCorrelativas());
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        holder.titulo.setText(materia.getNombre());
        holder.descripcion.setText("Codigo: "+materia.getCodigo());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(view.getContext(),
                        "Item clickeado: " + materia.getNombre(),
                        Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return materias.size();
    }
}
