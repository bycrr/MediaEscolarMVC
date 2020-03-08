package android.v1.mediaescolarmvc.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.v1.mediaescolarmvc.R;
import android.v1.mediaescolarmvc.controller.AplicativoController;
import android.v1.mediaescolarmvc.controller.MediaEscolarController;
import android.v1.mediaescolarmvc.model.MediaEscolar;
import android.v1.mediaescolarmvc.util.UtilMediaEscolar;

import java.util.List;

/**
 * Tela Splash é uma tela de apresentação inicial do
 * aplicativo, pode ser utilizada para executar atualizações
 * enquanto carrega o app
 */
public class SplashActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

  private static final int SPLASH_TIME_OUT = 5000;
  private TextToSpeech textToSpeech;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    // verificar se Google Play Service Client está instalado

    if (AplicativoController.verificarGooglePlayServices(SplashActivity.this)) {
      apresentarTelaSplash();

    } else {
      //Toast.makeText(getApplicationContext(), "Google Play Services não configurado!", Toast.LENGTH_LONG).show();
      UtilMediaEscolar.showMessage(getApplicationContext(), "Google Play Services não configurado!");
    }
  }

  private void apresentarTelaSplash() {
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        //DataSource ds = new DataSource(getApplicationContext());

        // TODO: remover este bloco de código
        MediaEscolar obj = new MediaEscolar();

        // teste incluir
        /*obj.setMateria("Matematica");
        obj.setNotaProva(5);
        obj.setNotaMateria(5);
        obj.setMediaFinal(2);
        obj.setSituacao("Reprovado");
        MediaEscolarController mediaEscolarController = new MediaEscolarController(getBaseContext());*/
        /*for (int i = 0; i < 5; i++) {
          obj.setBimestre(i + "# Bimestre");
          mediaEscolarController.incluir(obj);
        }*/

        // teste apagar
        /*obj.setId(1);
        mediaEscolarController.apagar(obj);
        obj.setId(2);
        mediaEscolarController.apagar(obj);*/

        // teste alterar
        /*obj.setId(5);
        obj.setBimestre("3# Bimestre");
        mediaEscolarController.alterar(obj);*/

        // teste listar
        MediaEscolarController mediaEscolarController = new MediaEscolarController(getBaseContext());
        List<MediaEscolar> listaMediaEscolar = mediaEscolarController.listar();
        for (MediaEscolar mediaEscolar : listaMediaEscolar) {
          Log.i("CRUD Listar", "ID: " + mediaEscolar.getId() + " - Matéria: " + mediaEscolar.getMateria());
        }

        Intent telaPrincipal = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(telaPrincipal);
        finish();
      }
    }, SPLASH_TIME_OUT);
  }

  @Override
  public void onInit(int i) {

  }
}
