package android.v1.mediaescolarmvc.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.v1.mediaescolarmvc.R;
import android.v1.mediaescolarmvc.controller.MediaEscolarController;
import android.v1.mediaescolarmvc.model.MediaEscolar;
import android.v1.mediaescolarmvc.util.IncluirAsyncTask;
import android.v1.mediaescolarmvc.util.UtilMediaEscolar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BimestreAFragment extends Fragment {

  MediaEscolar mediaEscolar;
  MediaEscolarController mediaEscolarController;
  View view;
  Button btnCalcular;
  EditText editMateria;
  EditText editNotaProva;
  EditText editNotaTrabalho;
  TextView txtResultado;
  TextView txtSituacaoFinal;
  boolean dadosValidados = true;
  double notaProva;
  double notaTrabalho;
  double media;
  long novoId;
  Context context;

  public BimestreAFragment() {

  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    context = getContext();
    mediaEscolarController = new MediaEscolarController(context);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    view = inflater.inflate(R.layout.fragment_bimestre_a, container, false);
    editMateria = view.findViewById(R.id.editMateria);
    editNotaProva = view.findViewById(R.id.editNotaProva);
    editNotaTrabalho = view.findViewById(R.id.editNotaTrabalho);
    btnCalcular = view.findViewById(R.id.btnCalcular);
    txtResultado = view.findViewById(R.id.txtResultado);
    txtSituacaoFinal = view.findViewById(R.id.txtSituacaoFinal);

    btnCalcular.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        try {

          if (editMateria.getText().toString().length() == 0) {
            editMateria.setError("*");
            editMateria.requestFocus();
            dadosValidados = false;
          }
          if (editNotaProva.getText().toString().length() > 0) {
            notaProva = Double.parseDouble(editNotaProva.getText().toString());

            if (mediaEscolarController.isNotaInformadaValida(notaProva)) {
              dadosValidados = false;
              //Toast.makeText(context, "Nota inválida!", Toast.LENGTH_SHORT).show();
              UtilMediaEscolar.showMessage(context, "Nota inválida!");
              editNotaProva.requestFocus();

            } else {
              dadosValidados = true;
            }
          } else {
            editNotaProva.setError("*");
            editNotaProva.requestFocus();
            dadosValidados = false;
          }
          if (editNotaTrabalho.getText().toString().length() > 0) {
            notaTrabalho = Double.parseDouble(editNotaTrabalho.getText().toString());

            if (mediaEscolarController.isNotaInformadaValida(notaTrabalho)) {
              dadosValidados = false;
              //Toast.makeText(context, "Nota inválida!", Toast.LENGTH_SHORT).show();
              UtilMediaEscolar.showMessage(context, "Nota inválida!");
              editNotaTrabalho.requestFocus();

            } else {
              dadosValidados = true;
            }
          } else {
            editNotaTrabalho.setError("*");
            editNotaTrabalho.requestFocus();
            dadosValidados = false;
          }
          // Após Validação
          if (dadosValidados) {
            mediaEscolar = new MediaEscolar();
            mediaEscolar.setMateria(editMateria.getText().toString());
            mediaEscolar.setNotaProva(Double.parseDouble(editNotaProva.getText().toString()));
            mediaEscolar.setNotaTrabalho(Double.parseDouble(editNotaTrabalho.getText().toString()));
            mediaEscolar.setBimestre("1º Bimestre");
            //media = (notaProva + notaTrabalho) / 2;
            media = mediaEscolarController.calcularMedia(mediaEscolar);
            mediaEscolar.setMediaFinal(media);
            mediaEscolar.setSituacao(mediaEscolarController.resultadoFinal(media));
            txtResultado.setText(UtilMediaEscolar.formatarValorDecimal(media));

            /*if (media >= 7) txtSituacaoFinal.setText("Aprovado");
            else txtSituacaoFinal.setText("Reprovado");*/
            txtSituacaoFinal.setText(mediaEscolar.getSituacao());

            editNotaProva.setText(UtilMediaEscolar.formatarValorDecimal(notaProva));
            editNotaTrabalho.setText(UtilMediaEscolar.formatarValorDecimal(notaTrabalho));

            //salvarSharedPreferences();
            novoId = mediaEscolarController.incluir(mediaEscolar);
            if ( novoId > 0) {
              // obj salvo c/sucesso no DB
              //Toast.makeText(context, "Dados salvos com sucesso...", Toast.LENGTH_LONG).show();
              mediaEscolar.setId(novoId);
              UtilMediaEscolar.showMessage(context, "Incluindo dados...");
              IncluirAsyncTask task = new IncluirAsyncTask(mediaEscolar, context);
              task.execute();

            } else {
              // falha ao salvar o obj no DB
              //Toast.makeText(context, "Falha ao salvar os dados...", Toast.LENGTH_LONG).show();
              UtilMediaEscolar.showMessage(context, "Falha ao salvar os dados...");
            }
          }
        } catch (Exception e) {
          //Toast.makeText(context, "Informe as notas...", Toast.LENGTH_LONG).show();
          UtilMediaEscolar.showMessage(context, "Informe as notas...");
        }
      }
    });
    return view;
  }
}
