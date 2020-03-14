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

public class ApagarAsyncTask extends AsyncTask<String, String, String> {

  ProgressDialog progressDialog;
  HttpURLConnection connection;
  URL url = null;
  Uri.Builder builder;
  private MediaEscolarController mediaEscolarController;
  Context context;

  public ApagarAsyncTask(MediaEscolar obj, Context context ) {
    this.builder = new Uri.Builder();
    this.context = context;

    // passagem de parâmetros p/o WS
    builder.appendQueryParameter("app", "MediaEscolarV1");

    // usar data model
    builder.appendQueryParameter(MediaEscolarDataModel.getIdPK(), String.valueOf(obj.getIdPK()));
  }

  @Override
  protected void onPreExecute() {
    Log.i("WebService", "ApagarAsyncTask()...");
    progressDialog = new ProgressDialog(context);
    progressDialog.setMessage("Apagando, por favor espere...");
    progressDialog.setCancelable(false);
    progressDialog.show();
  }

  @Override
  protected String doInBackground(String... strings) {

    // montar a URL com o endereço do script PHP
    try {
      url = new URL(UtilMediaEscolar.URL_WEB_SERVICE + "APIApagarDados.php");

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
        Log.i("WebService", "Exclusão com sucesso.");
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
