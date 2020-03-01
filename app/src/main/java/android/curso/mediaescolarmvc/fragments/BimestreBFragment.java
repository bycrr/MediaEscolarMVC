package android.curso.mediaescolarmvc.fragments;

import android.content.Context;
import android.curso.mediaescolarmvc.R;
import android.curso.mediaescolarmvc.view.MainActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BimestreBFragment extends Fragment {

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
  Context context;

  public BimestreBFragment() {

  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    context = getContext();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    view = inflater.inflate(R.layout.fragment_bimestre_b, container, false);
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

            if (notaProva > 10) {
              dadosValidados = false;
              Toast.makeText(context, "Nota inválida!", Toast.LENGTH_SHORT).show();
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

            if (notaTrabalho > 10) {
              dadosValidados = false;
              Toast.makeText(context, "Nota inválida!", Toast.LENGTH_SHORT).show();
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
            media = (notaProva + notaTrabalho) / 2;
            txtResultado.setText(MainActivity.formatarValorDecimal(media));

            if (media >= 7) txtSituacaoFinal.setText("Aprovado");
            else txtSituacaoFinal.setText("Reprovado");

            editNotaProva.setText(MainActivity.formatarValorDecimal(notaProva));
            editNotaTrabalho.setText(MainActivity.formatarValorDecimal(notaTrabalho));

            //salvarSharedPreferences();
          }
        } catch (Exception e) {
          Toast.makeText(context, "Informe as notas...", Toast.LENGTH_LONG).show();
        }
      }
    });
    return view;
  }}
