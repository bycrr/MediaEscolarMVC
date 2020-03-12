package android.v1.mediaescolarmvc.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.v1.mediaescolarmvc.controller.MediaEscolarController;
import android.v1.mediaescolarmvc.datamodel.MediaEscolarDataModel;
import android.v1.mediaescolarmvc.model.MediaEscolar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AlterarAsyncTask extends AsyncTask<String, String, String> {

  ProgressDialog progressDialog;
  HttpURLConnection connection;
  URL url = null;
  Uri.Builder builder;
  private MediaEscolarController mediaEscolarController;
  Context context;

  public AlterarAsyncTask(MediaEscolar obj, Context context ) {
    this.builder = new Uri.Builder();
    this.context = context;

    // passagem de parâmetros p/o WS
    builder.appendQueryParameter("app", "MediaEscolarV1");

    // usar data model
    builder.appendQueryParameter(MediaEscolarDataModel.getIdPK(), String.valueOf(obj.getIdPK()));
    builder.appendQueryParameter(MediaEscolarDataModel.getBimestre(), obj.getBimestre());
    builder.appendQueryParameter(MediaEscolarDataModel.getSituacao(), obj.getSituacao());
    builder.appendQueryParameter(MediaEscolarDataModel.getMediaFinal(), String.valueOf(obj.getMediaFinal()));
    builder.appendQueryParameter(MediaEscolarDataModel.getNotaTrabalho(), String.valueOf(obj.getNotaTrabalho()));
    builder.appendQueryParameter(MediaEscolarDataModel.getNotaProva(), String.valueOf(obj.getNotaProva()));
    builder.appendQueryParameter(MediaEscolarDataModel.getMateria(), obj.getMateria());
  }

  @Override
  protected void onPreExecute() {
    Log.i("WebService", "AltararAsyncTask()...");
    progressDialog = new ProgressDialog(context);
    progressDialog.setMessage("Alterando, por favor espere...");
    progressDialog.setCancelable(false);
    progressDialog.show();
  }

  @Override
  protected String doInBackground(String... strings) {

    // montar a URL com o endereço do script PHP
    try {
      url = new URL(UtilMediaEscolar.URL_WEB_SERVICE + "APIAlterarDados.php");

    } catch (MalformedURLException e) {
      Log.e("WebService", "MalformedURLException - " + e.getMessage());

    } catch (Exception erro) {
      Log.e("WebService", "Exception - " + erro.getMessage());
    }

    try {
      connection = (HttpURLConnection) url.openConnection();
      connection.setConnectTimeout(UtilMediaEscolar.CONNECTION_TIMEOUT);
      connection.setReadTimeout(UtilMediaEscolar.READ_TIMEOUT);
      connection.setRequestMethod("POST");
      connection.setRequestProperty("charset", "utf-8");
      connection.setDoInput(true);
      connection.setDoOutput(true);
      connection.connect();
      String query = builder.build().getEncodedQuery();
      OutputStream outputStream = connection.getOutputStream();
      BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
      bufferedWriter.write(query);
      bufferedWriter.flush();
      bufferedWriter.close();
      outputStream.close();
      connection.connect();

    } catch (IOException e) {
      Log.e("WebService", "IOException - " + e.getMessage());
    }

    // recebe JSON a resposta do servidor
    try {
      int response_code = connection.getResponseCode();
      // 200 ok
      // 403 forbideen
      // 404 not found
      // 500 server internal error
      if (response_code == HttpURLConnection.HTTP_OK) {
        InputStream inputStream = connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder result = new StringBuilder();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
          result.append(line);
        }
        Log.i("WebService", "Alteração com sucesso.");
        return result.toString();

      } else {
        Log.e("WebService", "response_code = " + response_code);
        return "Erro de conexão - response_code = " + response_code;
      }
    } catch (IOException e) {
      Log.e("WebService", "IOException - " + e.getMessage());
      return e.toString();

    } finally {
      connection.disconnect();
    }
    //return null;
  }

  @Override
  protected void onPostExecute(String result) {
    if (progressDialog != null && progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
  }
}
