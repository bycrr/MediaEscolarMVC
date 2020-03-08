package android.v1.mediaescolarmvc.util;

import android.content.Context;
import android.widget.Toast;

import java.text.DecimalFormat;

public class UtilMediaEscolar {

  // URL do servidor apache
  // informe o enderço IP se estiver rodando em seu computador
  // informe a URL se estiver rodando em uma hospedagem
  public static final String URL_WEB_SERVICE = "http://192.168.1.141/mediaescolar/";

  // tempo máximo p/considerar um TIMEOUT p/conectar ao apache (conectando)
  public static final int CONNECTION_TIMEOUT = 10000; // 10 segundos

  // tempo máximo para considerar erro de resposta do apache (lendo dados)
  public static final int READ_TIMEOUT = 15000; // 15 segundos

  public static String formatarValorDecimal(Double valor) {
    DecimalFormat df = new DecimalFormat("#,###,##0.00");
    return df.format(valor);
  }

  public static void showMessage(Context context, String message) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
  }
}
