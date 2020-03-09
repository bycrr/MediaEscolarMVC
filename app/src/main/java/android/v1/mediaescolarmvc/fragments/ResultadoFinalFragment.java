package android.v1.mediaescolarmvc.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.v1.mediaescolarmvc.R;
import android.v1.mediaescolarmvc.adapter.ResultadoFinalListAdapter;
import android.v1.mediaescolarmvc.controller.MediaEscolarController;
import android.v1.mediaescolarmvc.model.MediaEscolar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ResultadoFinalFragment extends Fragment {

  // Preparação do layout p/suporte a listview
  //  - fragment já temos
  //  - layout p/a linha (imagem, textos)
  // adapter
  // dataset c/os dados (arraylist abaixo)
  // listview p/apresentar os dados
  // controler p/buscar os dados no DB
  // novo método na controler getAll (vai ser getReulstadoFinal) devolvendo um arraylist
  // efeito de animação da lista

  ArrayList<MediaEscolar> datasetMediaEscolar;
  ListView listView;
  MediaEscolarController mediaEscolarController;
  View view;

  public ResultadoFinalFragment() {

  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    view = inflater.inflate(R.layout.fragment_resultado_final, container, false);
    mediaEscolarController = new MediaEscolarController(getContext());
    listView = view.findViewById(R.id.listview);
    datasetMediaEscolar = mediaEscolarController.getAllResultadoFinal();
    final ResultadoFinalListAdapter adapter = new ResultadoFinalListAdapter(datasetMediaEscolar, getContext());
    listView.setAdapter(adapter);

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        MediaEscolar mediaEscolar = datasetMediaEscolar.get(position);
        Snackbar.make(view, mediaEscolar.getMateria() + "\n Média Final: " + mediaEscolar.getMediaFinal(), Snackbar.LENGTH_LONG).setAction("No action", null).show();
        //datasetMediaEscolar = mediaEscolarController.getAllResultadoFinal();
        //adapter.atualizarLista(datasetMediaEscolar);
      }
    });

    return view;
  }
}
