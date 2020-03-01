package android.curso.mediaescolarmvc.view;

import android.content.Intent;
import android.curso.mediaescolarmvc.R;
import android.curso.mediaescolarmvc.controller.MediaEscolarController;
import android.curso.mediaescolarmvc.model.MediaEscolar;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

/**
 * Tela Splash é uma tela de apresentação inicial do
 * aplicativo, pode ser utilizada para executar atualizações
 * enquanto carrega o app
 */
public class SplashActivity extends AppCompatActivity {
  private static final int SPLASH_TIME_OUT = 5000;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    apresentarTelaSplash();
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
}
