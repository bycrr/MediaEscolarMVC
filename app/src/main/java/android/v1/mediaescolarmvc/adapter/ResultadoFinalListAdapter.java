package android.v1.mediaescolarmvc.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.v1.mediaescolarmvc.R;
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

  private static class ViewHolder {
    TextView txtBimestre;
    TextView txtSituacao;
    TextView txtMateria;
    ImageView imgLogo;
  }

  public ResultadoFinalListAdapter(ArrayList<MediaEscolar> datasetMediaEscolar, Context context) {
    super(context, R.layout.lisview_resultado_final, datasetMediaEscolar);
    ArrayList<MediaEscolar> dados = datasetMediaEscolar;
    this.context = context;
  }

  @Override
  public void onClick(View view) {
    int posicao = (Integer) view.getTag();
    Object object = getItem(posicao);
    MediaEscolar mediaEscolar = (MediaEscolar) object;

    switch (view.getId()) {
      case R.id.imgLogo:
        // apresentar os dados detalhados
        Snackbar.make(view, "Nota da Prova " + mediaEscolar.getNotaProva(), Snackbar.LENGTH_LONG).setAction("No action", null).show();
        break;
    }
  }

  @NonNull
  @Override
  public View getView(int position, View linhaDataSet, @NonNull ViewGroup parent) {
    MediaEscolar mediaEscolar = getItem(position);
    ViewHolder linha;
    linha = new ViewHolder();

    if (linhaDataSet == null) {
      LayoutInflater layoutResultadoFinalList = LayoutInflater.from(getContext());
      linhaDataSet = layoutResultadoFinalList.inflate(R.layout.lisview_resultado_final, parent, false);
      linha.txtMateria = linhaDataSet.findViewById(R.id.txtMateria);
      linha.txtBimestre = linhaDataSet.findViewById(R.id.txtBimestre);
      linha.txtSituacao = linhaDataSet.findViewById(R.id.txtSituacao);
      linha.imgLogo = linhaDataSet.findViewById(R.id.imgLogo);
      linhaDataSet.setTag(linha);

    } else {
      linha = (ViewHolder) linhaDataSet.getTag();
    }
    linha.txtMateria.setText(mediaEscolar.getMateria().toString());
    linha.txtBimestre.setText(mediaEscolar.getBimestre().toString());
    linha.txtSituacao.setText(mediaEscolar.getSituacao().toString());
    linha.imgLogo.setOnClickListener(this);
    linha.imgLogo.setTag(position);

    //return super.getView(position, convertView, parent);
    return linhaDataSet;
  }
}
