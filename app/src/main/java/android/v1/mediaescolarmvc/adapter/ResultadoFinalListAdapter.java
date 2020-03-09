package android.v1.mediaescolarmvc.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.v1.mediaescolarmvc.R;
import android.v1.mediaescolarmvc.controller.MediaEscolarController;
import android.v1.mediaescolarmvc.model.MediaEscolar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultadoFinalListAdapter extends ArrayAdapter<MediaEscolar> implements View.OnClickListener {

  // herdar arrayadapter - MediaEscolar
  // implementar OnClickListener
  // contexto
  // classe ViewHolder para os componentes ImageView e TextView
  // atributo p/conhecer a posição no array - animação
  // construtor q receba o dataset
  // onClick do elemento na lista
  // devolver via getView linha por linha p/o ListView

  Context context;
  private int ultimaPosicao = -1;
  AlertDialog.Builder builder;
  AlertDialog alertDialog;
  ArrayList<MediaEscolar> dados;
  MediaEscolar mediaEscolar;
  MediaEscolarController mediaEscolarController;

  private static class ViewHolder {
    TextView txtBimestre;
    TextView txtSituacao;
    TextView txtMateria;
    ImageView imgLogo;
    ImageView imgConsultar;
    ImageView imgEditar;
    ImageView imgDeletar;
    ImageView imgSalvar;
  }

  public ResultadoFinalListAdapter(ArrayList<MediaEscolar> datasetMediaEscolar, Context context) {
    super(context, R.layout.lisview_resultado_final, datasetMediaEscolar);
    this.dados = datasetMediaEscolar;
    this.context = context;
  }

  public void atualizarLista(ArrayList<MediaEscolar> novosDados) {
    this.dados.clear();
    this.dados.addAll(novosDados);
    notifyDataSetChanged();
  }

  @Override
  public void registerDataSetObserver(DataSetObserver observer) {
    super.registerDataSetObserver(observer);
  }

  @Override
  public void onClick(View view) {
    int posicao = (Integer) view.getTag();
    Object object = getItem(posicao);
    mediaEscolar = (MediaEscolar) object;
    mediaEscolarController = new MediaEscolarController(getContext());

    switch (view.getId()) {
      case R.id.imgLogo:
        // apresentar os dados detalhados
        Snackbar.make(view, "Nota da Prova " + mediaEscolar.getNotaProva(), Snackbar.LENGTH_LONG).setAction("No action", null).show();
        break;

      case R.id.imgConsultar:
        builder = new AlertDialog.Builder(getContext());
        builder.setTitle("ALERTA");
        builder.setMessage("Deseja consultar este registro?");
        builder.setCancelable(true);
        builder.setIcon(R.mipmap.ic_launcher);

        builder.setPositiveButton("SIM", new Dialog.OnClickListener(){
          @Override
          public void onClick(DialogInterface dialog, int which) {
            // consultar o registro
          }
        });

        builder.setNegativeButton("CANCELAR", new Dialog.OnClickListener(){
          @Override
          public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
          }
        });
        alertDialog = builder.create();
        alertDialog.show();
        break;

      case R.id.imgDeletar:
        builder = new AlertDialog.Builder(getContext());
        builder.setTitle("ALERTA");
        builder.setMessage("Deseja apagar este registro?");
        builder.setCancelable(true);
        builder.setIcon(R.mipmap.ic_launcher);

        builder.setPositiveButton("SIM", new Dialog.OnClickListener(){
          @Override
          public void onClick(DialogInterface dialog, int which) {
            // deletar o registro
            mediaEscolarController.apagar(mediaEscolar);
            ArrayList<MediaEscolar> datasetMediaEscolar = mediaEscolarController.getAllResultadoFinal();
            atualizarLista(datasetMediaEscolar);
            notifyDataSetChanged();
          }
        });

        builder.setNegativeButton("CANCELAR", new Dialog.OnClickListener(){
          @Override
          public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
          }
        });
        alertDialog = builder.create();
        alertDialog.show();
        break;

      case R.id.imgEditar:
        builder = new AlertDialog.Builder(getContext());
        builder.setTitle("ALERTA");
        builder.setMessage("Deseja editar este registro?");
        builder.setCancelable(true);
        builder.setIcon(R.mipmap.ic_launcher);

        builder.setPositiveButton("SIM", new Dialog.OnClickListener(){
          @Override
          public void onClick(DialogInterface dialog, int which) {
            // editar o registro
          }
        });

        builder.setNegativeButton("CANCELAR", new Dialog.OnClickListener(){
          @Override
          public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
          }
        });
        alertDialog = builder.create();
        alertDialog.show();
        break;

      case R.id.imgSalvar:
        builder = new AlertDialog.Builder(getContext());
        builder.setTitle("ALERTA");
        builder.setMessage("Deseja salvar este registro?");
        builder.setCancelable(true);
        builder.setIcon(R.mipmap.ic_launcher);

        builder.setPositiveButton("SIM", new Dialog.OnClickListener(){
          @Override
          public void onClick(DialogInterface dialog, int which) {
            // salvar o registro
          }
        });

        builder.setNegativeButton("CANCELAR", new Dialog.OnClickListener(){
          @Override
          public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
          }
        });
        alertDialog = builder.create();
        alertDialog.show();
        break;
    }
  }

  @NonNull
  @Override
  public View getView(int position, View linhaDataSet, @NonNull ViewGroup parent) {
    mediaEscolar = getItem(position);
    ViewHolder linha;
    linha = new ViewHolder();

    if (linhaDataSet == null) {
      LayoutInflater layoutResultadoFinalList = LayoutInflater.from(getContext());
      linhaDataSet = layoutResultadoFinalList.inflate(R.layout.lisview_resultado_final, parent, false);
      linha.txtMateria = linhaDataSet.findViewById(R.id.txtMateria);
      linha.txtBimestre = linhaDataSet.findViewById(R.id.txtBimestre);
      linha.txtSituacao = linhaDataSet.findViewById(R.id.txtSituacao);
      linha.imgLogo = linhaDataSet.findViewById(R.id.imgLogo);
      linha.imgConsultar = linhaDataSet.findViewById(R.id.imgConsultar);
      linha.imgDeletar = linhaDataSet.findViewById(R.id.imgDeletar);
      linha.imgEditar = linhaDataSet.findViewById(R.id.imgEditar);
      linha.imgSalvar = linhaDataSet.findViewById(R.id.imgSalvar);
      linhaDataSet.setTag(linha);

    } else {
      linha = (ViewHolder) linhaDataSet.getTag();
    }
    linha.txtMateria.setText(mediaEscolar.getMateria().toString());
    linha.txtBimestre.setText(mediaEscolar.getBimestre().toString());
    linha.txtSituacao.setText(mediaEscolar.getSituacao().toString());
    linha.imgLogo.setOnClickListener(this);
    linha.imgConsultar.setOnClickListener(this);
    linha.imgDeletar.setOnClickListener(this);
    linha.imgEditar.setOnClickListener(this);
    linha.imgSalvar.setOnClickListener(this);
    linha.imgLogo.setTag(position);
    linha.imgConsultar.setTag(position);
    linha.imgDeletar.setTag(position);
    linha.imgEditar.setTag(position);
    linha.imgSalvar.setTag(position);

    //return super.getView(position, convertView, parent);
    return linhaDataSet;
  }
}
