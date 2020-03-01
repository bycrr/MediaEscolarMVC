package android.curso.mediaescolarmvc.controller;

import android.content.ContentValues;
import android.content.Context;
import android.curso.mediaescolarmvc.datamodel.MediaEscolarDataModel;
import android.curso.mediaescolarmvc.datasource.DataSource;
import android.curso.mediaescolarmvc.model.MediaEscolar;

/**
 * Created by marcomaddo on 04/11/2017.
 */

public class MediaEscolarController extends DataSource {
  ContentValues dados;

  public MediaEscolarController(Context context) {
    super(context);
  }

  // TODO: implementar calculo da média
  public void calcularMedia() {

  }

  // TODO: implementar calculo do resultado final
  public void resultadoFinal() {

  }

  public boolean incluir(MediaEscolar obj) {
    boolean sucesso = true;
    dados = new ContentValues();
    dados.put(MediaEscolarDataModel.getMateria(), obj.getMateria());
    dados.put(MediaEscolarDataModel.getBimestre(), obj.getBimestre());
    dados.put(MediaEscolarDataModel.getSituacao(), obj.getSituacao());
    dados.put(MediaEscolarDataModel.getNotaProva(), obj.getNotaProva());
    dados.put(MediaEscolarDataModel.getNotaMateria(), obj.getNotaMateria());
    dados.put(MediaEscolarDataModel.getMediaFinal(), obj.getMediaFinal());
    sucesso = insert(MediaEscolarDataModel.getTABELA(), dados);
    return sucesso;
  }

  public boolean apagar(MediaEscolar obj) {
    boolean sucesso = true;
    sucesso = delete(MediaEscolarDataModel.getTABELA(), obj.getId());
    return sucesso;
  }

  public boolean alterar(MediaEscolar obj) {
    boolean sucesso = true;
    dados = new ContentValues();
    dados.put(MediaEscolarDataModel.getId(), obj.getId());
    dados.put(MediaEscolarDataModel.getMateria(), obj.getMateria());
    dados.put(MediaEscolarDataModel.getBimestre(), obj.getBimestre());
    dados.put(MediaEscolarDataModel.getSituacao(), obj.getSituacao());
    dados.put(MediaEscolarDataModel.getNotaProva(), obj.getNotaProva());
    dados.put(MediaEscolarDataModel.getNotaMateria(), obj.getNotaMateria());
    dados.put(MediaEscolarDataModel.getMediaFinal(), obj.getMediaFinal());
    sucesso = update(MediaEscolarDataModel.getTABELA(), dados);
    return sucesso;
  }
}
