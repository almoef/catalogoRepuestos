package com.example.cagalogorepuestos.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cagalogorepuestos.Model.Model;
import com.example.cagalogorepuestos.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

public class Adapter extends FirestoreRecyclerAdapter<Model,Adapter.ViewHolder> {

  /**
   * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
   * FirestoreRecyclerOptions} for configuration options.
   *
   * @param options+
   */
  public Adapter(@NonNull FirestoreRecyclerOptions<Model> options) {
    super(options);
  }

  @Override
  protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int position, @NonNull Model model) {
    viewHolder.material.setText(model.getMaterial());
    //viewHolder.equipo.setText(model.getEquipo());
    viewHolder.descripcionSap.setText(model.getDescripcionSap());
    viewHolder.fabricanteComponente.setText(model.getFabricanteComponente());
    //viewHolder.linea.setText(model.getLinea());
    viewHolder.medidas.setText(model.getMedidas());
    viewHolder.npFabricante.setText(model.getNpFabricante());
    viewHolder.npProveedor.setText(model.getNpProveedor());
    viewHolder.proveedorMaquina.setText(model.getProveedorMaquina());
    viewHolder.textoExtendido.setText(model.getTextoExtendido());
    //viewHolder.tipoDeMaterial.setText(model.getTipoDeMaterial());
    Picasso.get().load(model.getUrlImagen()).into(viewHolder.imageView);
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.index_view,parent,false);
    return new ViewHolder(v);
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    TextView material, descripcionSap, equipo,fabricanteComponente,linea,medidas,npFabricante,npProveedor,proveedorMaquina,textoExtendido,tipoDeMaterial;
    ImageView imageView;
    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      material = itemView.findViewById(R.id.material);
      equipo = itemView.findViewById(R.id.equipo);
      descripcionSap = itemView.findViewById(R.id.descripcionSap);
      fabricanteComponente = itemView.findViewById(R.id.fabricanteComponente);
      linea = itemView.findViewById(R.id.linea);
      medidas = itemView.findViewById(R.id.medidas);
      npFabricante = itemView.findViewById(R.id.npFabricante);
      npProveedor = itemView.findViewById(R.id.npProveedor);
      proveedorMaquina = itemView.findViewById(R.id.proveedorMaquina);
      textoExtendido = itemView.findViewById(R.id.textoExtendido);
      //tipoDeMaterial = itemView.findViewById(R.id.tipoDeMaterial);
      imageView = itemView.findViewById(R.id.imageView);
    }
  }
}
