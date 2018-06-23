package ar.com.hipnos.leo.istudy;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ar.com.hipnos.leo.istudy.api.modell.Carrera;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CarreraAdapter extends RecyclerView.Adapter<CarreraAdapter.ViewHolder>{

    private List<Carrera> carreras;

    public CarreraAdapter(List<Carrera> carrerasList) {
        carreras = carrerasList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View view;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carrera_list_item, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CarreraAdapter.ViewHolder holder, int position) {
        final Carrera carrera = this.carreras.get(position);

        holder.titulo.setText(carrera.getNombre());
        holder.descripcion.setText("Codigo: "+carrera.getCodigo());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(view.getContext(),
                        "Item clickeado: " + carrera.getNombre(),
                        Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return carreras.size();
    }
}
