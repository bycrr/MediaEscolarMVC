package android.v1.mediaescolarmvc.controller;

import android.content.ContentValues;
import android.content.Context;
import android.v1.mediaescolarmvc.datamodel.MediaEscolarDataModel;
import android.v1.mediaescolarmvc.datasource.DataSource;
import android.v1.mediaescolarmvc.model.MediaEscolar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcomaddo on 04/11/2017.
 */

public class MediaEscolarController extends DataSource {
  ContentValues dados;

  public MediaEscolarController(Context context) {
    super(context);
  }

  public double calcularMedia(MediaEscolar obj) {
    return (obj.getNotaProva() + obj.getNotaTrabalho()) / 2;
  }

  public String resultadoFinal(double media) {
    return media >= 7 ? "Aprovado" : "Reprovado";
  }

  public boolean incluir(MediaEscolar obj) {
    boolean sucesso = true;
    dados = new ContentValues();
    dados.put(MediaEscolarDataModel.getMateria(), obj.getMateria());
    dados.put(MediaEscolarDataModel.getBimestre(), obj.getBimestre());
    dados.put(MediaEscolarDataModel.getSituacao(), obj.getSituacao());
    dados.put(MediaEscolarDataModel.getNotaProva(), obj.getNotaProva());
    dados.put(MediaEscolarDataModel.getNotaMateria(), obj.getNotaTrabalho());
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
    dados.put(MediaEscolarDataModel.getNotaMateria(), obj.getNotaTrabalho());
    dados.put(MediaEscolarDataModel.getMediaFinal(), obj.getMediaFinal());
    sucesso = update(MediaEscolarDataModel.getTABELA(), dados);
    return sucesso;
  }

  public List<MediaEscolar> listar() {
    return getAllMediaEscolar();
  }

  public ArrayList<MediaEscolar> getResultadoFinal() {
    return getAllResultadoFinal();
  }

  public boolean isNotaInformadaValida(Double nota) {
    return nota > 10;
  }
}
