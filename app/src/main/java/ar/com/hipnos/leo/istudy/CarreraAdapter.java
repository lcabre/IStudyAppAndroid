package ar.com.hipnos.leo.istudy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ar.com.hipnos.leo.istudy.api.ApiService;
import ar.com.hipnos.leo.istudy.api.ErrorService;
import ar.com.hipnos.leo.istudy.api.modell.Carrera;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class CarreraAdapter extends RecyclerView.Adapter<CarreraAdapter.ViewHolder>{

    private List<Carrera> carreras = new ArrayList<Carrera>();
    private Context context;

    public CarreraAdapter(Context contexto, List<Carrera> carrerasList) {//ver esto porque es una negrada

        context = contexto;
        if(carrerasList != null) {
            carreras = carrerasList;
        }

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


                if(context instanceof ListaCarrerasActivity){
//                    Toast.makeText(view.getContext(),
//                            "Carrera Seleccionada: " + carrera.getNombre(),
//                            Toast.LENGTH_LONG)
//                            .show();

                    //pasar getAplicationContext()
                    SharedPreferences prefs = context.getSharedPreferences("token", MODE_PRIVATE);
                    String accessToken = prefs.getString("access_token", null);
                    String tokenType = prefs.getString("token_type", null);
                    String authorization = tokenType+" "+accessToken;

                    ApiService.joinCarrera( authorization, carrera.getId(), new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()){

                                ((ListaCarrerasActivity) context).loading.setVisibility(View.GONE);

                                Intent i = new Intent(context, CarrerasActivity.class);
                                context.startActivity(i);

                            }else{

                                ((ListaCarrerasActivity) context).loading.setVisibility(View.GONE);

                                String error = response.message().equals("Unauthenticated")?"No esta autenticado":"Se produjo un error, intente nuevamente";
                                ErrorService.showError(((ListaCarrerasActivity) context).error_message, error );

                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                            ((ListaCarrerasActivity) context).loading.setVisibility(View.GONE);

                            ErrorService.showError(((ListaCarrerasActivity) context).error_message, "No fue posible cominicarse con el servidor. Verifique si tiene internet." );
                        }
                    });
                }

                if (context instanceof CarrerasActivity) {
//                    Toast.makeText(view.getContext(),
//                            "Item clickeado: " + carrera.getNombre(),
//                            Toast.LENGTH_LONG)
//                            .show();

                    Intent i = new Intent(context, MateriasActivity.class);
                    i.putExtra("carreraId", carrera.getId());
                    context.startActivity(i);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return carreras.size();
    }
}
