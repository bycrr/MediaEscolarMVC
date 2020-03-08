package android.v1.mediaescolarmvc.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.v1.mediaescolarmvc.R;
import android.v1.mediaescolarmvc.controller.MediaEscolarController;
import android.v1.mediaescolarmvc.datamodel.MediaEscolarDataModel;
import android.v1.mediaescolarmvc.fragments.BimestreAFragment;
import android.v1.mediaescolarmvc.fragments.BimestreBFragment;
import android.v1.mediaescolarmvc.fragments.BimestreCFragment;
import android.v1.mediaescolarmvc.fragments.BimestreDFragment;
import android.v1.mediaescolarmvc.fragments.ResultadoFinalFragment;
import android.v1.mediaescolarmvc.model.MediaEscolar;
import android.v1.mediaescolarmvc.util.UtilMediaEscolar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

public class MainActivity extends AppCompatActivity
  implements NavigationView.OnNavigationItemSelectedListener {

  // 1# passo
  FragmentManager fragmentManager;
  MediaEscolarController mediaEscolarController;
  Context context;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    context = getBaseContext();
    mediaEscolarController = new MediaEscolarController(context);
    FloatingActionButton fab = findViewById(R.id.fab);

    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
          .setAction("Action", null).show();
        // TODO: criar classe SincronizarSistema AsynkTask
        SincronizarSistema task = new SincronizarSistema();
        task.execute();
      }
    });

    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
      this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();
    NavigationView navigationView = findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

    // 2# passo
    fragmentManager = getSupportFragmentManager();

    // 4# passo
    //fragmentManager.beginTransaction().replace(R.id.content_fragment, new ModeloFragment()).commit();
  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = findViewById(R.id.drawer_layout);

    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);

    } else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == R.id.action_sair) {
      finish();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == R.id.nav_resultado_final) {
      setTitle("Resultado Final");
      fragmentManager.beginTransaction().replace(R.id.content_fragment, new ResultadoFinalFragment()).commit();

    } else if (id == R.id.nav_bimestre_a) {
      setTitle("Notas do 1º Bimestre");
      fragmentManager.beginTransaction().replace(R.id.content_fragment, new BimestreAFragment()).commit();

    } else if (id == R.id.nav_bimestre_b) {
      setTitle("Notas do 2º Bimestre");
      fragmentManager.beginTransaction().replace(R.id.content_fragment, new BimestreBFragment()).commit();

    } else if (id == R.id.nav_bimestre_c) {
      setTitle("Notas do 3º Bimestre");
      fragmentManager.beginTransaction().replace(R.id.content_fragment, new BimestreCFragment()).commit();

    } else if (id == R.id.nav_bimestre_d) {
      setTitle("Notas do 4º Bimestre");
      fragmentManager.beginTransaction().replace(R.id.content_fragment, new BimestreDFragment()).commit();

    } else if (id == R.id.nav_share) {
    }
    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  private class SincronizarSistema extends AsyncTask<String, String, String> {

    ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
    HttpURLConnection connection;
    URL url = null;
    Uri.Builder builder;

    public SincronizarSistema() {
      this.builder = new Uri.Builder();
      builder.appendQueryParameter("app", "MediaEscolar");
    }

    @Override
    protected void onPreExecute() {
      Log.i("WebService", "Sincronizando...");
      progressDialog.setMessage("Sincronizando Sistema, por favor espere...");
      progressDialog.setCancelable(false);
      progressDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {

      // montar a URL com o endereço do script PHP
      try {
        url = new URL(UtilMediaEscolar.URL_WEB_SERVICE + "APISincronizarSistema.php");

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

      // TODO: recebe JSON a resposta do servidor
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
          Log.i("WebService", "Sincronização com sucesso.");
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

      try {
        JSONArray jsonArray = new JSONArray(result);

        if (jsonArray.length() > 0) {
          // salvar os dados no DB SQLite
          mediaEscolarController.deletarTabela(MediaEscolarDataModel.getTABELA());
          mediaEscolarController.criarTabela(MediaEscolarDataModel.criarTabela());

          for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            MediaEscolar obj = new MediaEscolar();
            obj.setId(jsonObject.getInt(MediaEscolarDataModel.getId()));
            obj.setMateria(jsonObject.getString(MediaEscolarDataModel.getMateria()));
            obj.setBimestre(jsonObject.getString(MediaEscolarDataModel.getBimestre()));
            obj.setNotaProva(jsonObject.getDouble(MediaEscolarDataModel.getNotaProva()));
            obj.setNotaTrabalho(jsonObject.getDouble(MediaEscolarDataModel.getNotaMateria()));
            obj.setSituacao(jsonObject.getString(MediaEscolarDataModel.getSituacao()));
            obj.setMediaFinal(jsonObject.getDouble(MediaEscolarDataModel.getMediaFinal()));
            mediaEscolarController.incluir(obj);
          }
        } else {
          UtilMediaEscolar.showMessage(context, "Nenhum registro encontrado...");
        }

      } catch (JSONException e) {
        Log.e("WebService", "JSONException - " + e.getMessage());

      } finally {

        if (progressDialog != null && progressDialog.isShowing()) {
          progressDialog.dismiss();
        }
      }
    }
  }
}
