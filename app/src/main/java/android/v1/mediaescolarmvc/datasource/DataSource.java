package android.v1.mediaescolarmvc.datasource;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.v1.mediaescolarmvc.datamodel.MediaEscolarDataModel;
import android.v1.mediaescolarmvc.model.MediaEscolar;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DataSource extends SQLiteOpenHelper {

  private static final String DB_NAME = "media_escolar.sqlite";
  private static final int DB_VERSION = 1;

  SQLiteDatabase db;
  Cursor cursor;

  public DataSource(Context context) {
    super(context, DB_NAME, null, DB_VERSION);
    db = getWritableDatabase();
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    try {
      db.execSQL(MediaEscolarDataModel.criarTabela());

    } catch (Exception e) {
      Log.e("Media", "DB - erro: " + e.getMessage());
    }
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // backup dos dados
    // onCreate()

  }

  public boolean insert(String tabela, ContentValues dados) {
    boolean sucesso = true;

    try {
      sucesso = db.insert(tabela, null, dados) > 0;

    } catch (Exception e) {
      sucesso = false;
    }
    return sucesso;
  }

  public boolean delete(String tabela, int id) {
    boolean sucesso = true;

    try {
      sucesso = db.delete(tabela, "id=?", new String[]{Integer.toString(id)}) > 0;

    } catch (Exception e) {
      sucesso = false;
    }
    return sucesso;
  }

  public boolean update(String tabela, ContentValues dados) {
    boolean sucesso = true;
    int id = dados.getAsInteger("id");

    try {
      sucesso = db.update(tabela, dados, "id=?", new String[]{Integer.toString(id)}) > 0;

    } catch (Exception e) {
      sucesso = false;
    }
    return sucesso;
  }

  /*public MediaEscolar buscarObjPeloID(String tabela, MediaEscolar obj) {
    // select * from componente where numeroOS = 20
    MediaEscolar mediaEscolar = new MediaEscolar();
    String consultaSQL = "SELECT * FROM " + tabela + " WHERE id = '" + obj.getId() + "'";

  }*/

  public List<MediaEscolar> getAllMediaEscolar() {
    MediaEscolar obj;
    List<MediaEscolar> lista = new ArrayList<>();
    String sql = "SELECT * FROM " + MediaEscolarDataModel.getTABELA() + " ORDER BY materia";
    cursor = db.rawQuery(sql, null);

    /*if (cursor.moveToFirst()) {
      do {
        obj = new MediaEscolar();
        obj.setId(cursor.getInt(cursor.getColumnIndex(MediaEscolarDataModel.getId())));
        obj.setMateria(cursor.getString(cursor.getColumnIndex(MediaEscolarDataModel.getMateria())));
        lista.add(obj);
      } while (cursor.moveToNext());
    }*/
    while (cursor.moveToNext()) {
      obj = new MediaEscolar();
      obj.setId(cursor.getInt(cursor.getColumnIndex(MediaEscolarDataModel.getId())));
      obj.setBimestre(cursor.getString(cursor.getColumnIndex(MediaEscolarDataModel.getBimestre())));
      obj.setMateria(cursor.getString(cursor.getColumnIndex(MediaEscolarDataModel.getMateria())));
      obj.setMediaFinal(cursor.getDouble(cursor.getColumnIndex(MediaEscolarDataModel.getMediaFinal())));
      obj.setNotaProva(cursor.getDouble(cursor.getColumnIndex(MediaEscolarDataModel.getNotaProva())));
      obj.setNotaTrabalho(cursor.getDouble(cursor.getColumnIndex(MediaEscolarDataModel.getNotaMateria())));
      obj.setSituacao(cursor.getString(cursor.getColumnIndex(MediaEscolarDataModel.getSituacao())));
      lista.add(obj);
    }
    return lista;
  }

  public ArrayList<MediaEscolar> getAllResultadoFinal() {
    MediaEscolar obj;
    ArrayList<MediaEscolar> lista = new ArrayList<>();
    String sql = "SELECT * FROM " + MediaEscolarDataModel.getTABELA() + " ORDER BY materia";
    cursor = db.rawQuery(sql, null);

    /*if (cursor.moveToFirst()) {
      do {
        obj = new MediaEscolar();
        obj.setId(cursor.getInt(cursor.getColumnIndex(MediaEscolarDataModel.getId())));
        obj.setMateria(cursor.getString(cursor.getColumnIndex(MediaEscolarDataModel.getMateria())));
        lista.add(obj);
      } while (cursor.moveToNext());
    }*/
    while (cursor.moveToNext()) {
      obj = new MediaEscolar();
      obj.setId(cursor.getInt(cursor.getColumnIndex(MediaEscolarDataModel.getId())));
      obj.setBimestre(cursor.getString(cursor.getColumnIndex(MediaEscolarDataModel.getBimestre())));
      obj.setMateria(cursor.getString(cursor.getColumnIndex(MediaEscolarDataModel.getMateria())));
      obj.setMediaFinal(cursor.getDouble(cursor.getColumnIndex(MediaEscolarDataModel.getMediaFinal())));
      obj.setNotaProva(cursor.getDouble(cursor.getColumnIndex(MediaEscolarDataModel.getNotaProva())));
      obj.setNotaTrabalho(cursor.getDouble(cursor.getColumnIndex(MediaEscolarDataModel.getNotaMateria())));
      obj.setSituacao(cursor.getString(cursor.getColumnIndex(MediaEscolarDataModel.getSituacao())));
      lista.add(obj);
    }
    return lista;
  }

  public void deletarTabela(String tabela) {
    try {
      db.execSQL("DROP TABLE IF EXISTS " + tabela);

    } catch (Exception e) {

    }
  }

  public void criarTabela(String queryCriarTabela) {
    try {
      db.execSQL(queryCriarTabela);

    } catch (SQLiteCantOpenDatabaseException e) {
    }
  }
}

