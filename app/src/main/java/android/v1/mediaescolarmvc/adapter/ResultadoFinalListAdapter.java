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
import android.widget.EditText;
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
  ViewHolder linha;

  private static class ViewHolder {
    TextView txtBimestre;
    TextView txtSituacao;
    TextView txtMateria;
    TextView txtMedia;
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
        builder.setTitle("CONSULTA");
        builder.setMessage("Bimestre: " + mediaEscolar.getBimestre() + "\nMatéria: " + mediaEscolar.getMateria() + "\nSituação: " + mediaEscolar.getSituacao() + "\n\nMédia Final: " + mediaEscolar.getMediaFinal());
        builder.setCancelable(true);
        builder.setIcon(R.mipmap.ic_launcher);

        builder.setPositiveButton("VOLTAR", new Dialog.OnClickListener(){
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
            // caso for preciso desabilitar algum dos botõeszinhos =>
            // linha.imgDeletar.setEnabled(false);
            atualizarLista(mediaEscolarController.getAllResultadoFinal());
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
        // layout
        // criar alertDialog p/editar os dados
        // consumir MVC
        // atualizar dataset/listview
        View alertView = view.inflate(getContext(), R.layout.alert_dialog_editar_listview, null);
        final EditText editMateria = alertView.findViewById(R.id.editMateria);
        final EditText editNotaTrabalho = alertView.findViewById(R.id.editNotaTrabalho);
        final EditText editNotaProva = alertView.findViewById(R.id.editNotaProva);
        editMateria.setText(mediaEscolar.getMateria());
        editNotaTrabalho.setText(String.valueOf(mediaEscolar.getNotaTrabalho()));
        editNotaProva.setText(String.valueOf(mediaEscolar.getNotaProva()));
        AlertDialog.Builder alertbox = new AlertDialog.Builder(alertView.getRootView().getContext());
        alertbox.setMessage(mediaEscolar.getBimestre());
        alertbox.setTitle("Editando");
        alertbox.setView(alertView);

        alertbox.setNeutralButton("Salvar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
              // TODO: implementar validação
              mediaEscolar.setMateria(editMateria.getText().toString());
              mediaEscolar.setNotaProva(Double.parseDouble(editNotaProva.getText().toString()));
              mediaEscolar.setNotaTrabalho(Double.parseDouble(editNotaTrabalho.getText().toString()));
              Double mediaFinal = mediaEscolarController.calcularMedia(mediaEscolar);
              mediaEscolar.setMediaFinal(mediaFinal);
              mediaEscolar.setSituacao(mediaEscolarController.resultadoFinal(mediaFinal));
              mediaEscolarController.alterar(mediaEscolar);
              atualizarLista(mediaEscolarController.getAllResultadoFinal());
            }
          });
        alertbox.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {

          }
        });
        alertbox.show();

        /*builder = new AlertDialog.Builder(getContext());
        builder.setTitle("ALERTA");
        builder.setMessage("Deseja editar este registro?");
        builder.setCancelable(true);
        builder.setIcon(R.mipmap.ic_launcher);

        builder.setPositiveButton("SIM", new Dialog.OnClickListener(){
          @Override
          public void onClick(DialogInterface dialog, int which) {
            // editar o registro
            mediaEscolar.setMediaFinal(5);
            mediaEscolar.setSituacao("Reprovado");
            mediaEscolarController.alterar(mediaEscolar);
            atualizarLista(mediaEscolarController.getAllResultadoFinal());
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
        alertDialog.show();*/
        break;

      /*case R.id.imgSalvar:
        builder = new AlertDialog.Builder(getContext());
        builder.setTitle("ALERTA");
        builder.setMessage("Deseja salvar este registro?");
        builder.setCancelable(true);
        builder.setIcon(R.mipmap.ic_launcher);

        builder.setPositiveButton("SIM", new Dialog.OnClickListener(){
          @Override
          public void onClick(DialogInterface dialog, int which) {
            // salvar o registro
            mediaEscolarController.alterar(mediaEscolar);
            atualizarLista(mediaEscolarController.getAllResultadoFinal());
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
        break;*/
    }
  }

  @NonNull
  @Override
  public View getView(int position, View linhaDataSet, @NonNull ViewGroup parent) {
    mediaEscolar = getItem(position);
    //ViewHolder linha;
    linha = new ViewHolder();

    if (linhaDataSet == null) {
      LayoutInflater layoutResultadoFinalList = LayoutInflater.from(getContext());
      linhaDataSet = layoutResultadoFinalList.inflate(R.layout.lisview_resultado_final, parent, false);
      linha.txtMateria = linhaDataSet.findViewById(R.id.txtMateria);
      linha.txtBimestre = linhaDataSet.findViewById(R.id.txtBimestre);
      linha.txtSituacao = linhaDataSet.findViewById(R.id.txtSituacao);
      linha.txtMedia = linhaDataSet.findViewById(R.id.txtMedia);
      linha.imgLogo = linhaDataSet.findViewById(R.id.imgLogo);
      linha.imgConsultar = linhaDataSet.findViewById(R.id.imgConsultar);
      linha.imgDeletar = linhaDataSet.findViewById(R.id.imgDeletar);
      linha.imgEditar = linhaDataSet.findViewById(R.id.imgEditar);
      //linha.imgSalvar = linhaDataSet.findViewById(R.id.imgSalvar);
      linhaDataSet.setTag(linha);

    } else {
      linha = (ViewHolder) linhaDataSet.getTag();
    }
    linha.txtMateria.setText(mediaEscolar.getMateria());
    linha.txtBimestre.setText(mediaEscolar.getBimestre());
    linha.txtSituacao.setText(mediaEscolar.getSituacao());
    linha.txtMedia.setText(String.valueOf(mediaEscolar.getMediaFinal()));
    linha.imgLogo.setOnClickListener(this);
    linha.imgConsultar.setOnClickListener(this);
    linha.imgDeletar.setOnClickListener(this);
    linha.imgEditar.setOnClickListener(this);
    //linha.imgSalvar.setOnClickListener(this);
    linha.imgLogo.setTag(position);
    linha.imgConsultar.setTag(position);
    linha.imgDeletar.setTag(position);
    linha.imgEditar.setTag(position);
    //linha.imgSalvar.setTag(position);

    //return super.getView(position, convertView, parent);
    return linhaDataSet;
  }
}
