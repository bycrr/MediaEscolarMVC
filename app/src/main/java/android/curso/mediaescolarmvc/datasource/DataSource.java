package android.curso.mediaescolarmvc.datasource;

import android.content.ContentValues;
import android.content.Context;
import android.curso.mediaescolarmvc.datamodel.MediaEscolarDataModel;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataSource extends SQLiteOpenHelper {

  private static final String DB_NAME = "media_escolar.sqlite";
  private static final int DB_VERSION = 1;

  SQLiteDatabase db;

  public DataSource(Context context) {
    super(context, DB_NAME, null, DB_VERSION);
    db = getWritableDatabase();
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    try {
      db.execSQL(MediaEscolarDataModel.criarTabela());

    } catch (Exception e) {
      Log.e( "Media", "DB - erro: " + e.getMessage());
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
}

