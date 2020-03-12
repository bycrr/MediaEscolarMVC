package android.v1.mediaescolarmvc.datamodel;

public class MediaEscolarDataModel {

  // Dados para criar as tabelas no bando de dados
  // MOR - modelo objeto relacional
  // TUPLA ou Registros
  // finalidade desta classe: Criar dinamicamente uma query SQL p/criar a tabela MediaEscolar no BD

  private final static String TABELA = "media_escolar";
  private final static String id = "id";
  private final static String idPK = "idPK";
  private final static String materia = "materia";
  private final static String bimestre = "bimestre";
  private final static String situacao = "situacao";
  private final static String notaProva = "notaProva";
  private final static String notaTrabalho = "notaTrabalho";
  private final static String mediaFinal = "mediaFinal";
  private static String queryCriarTabela = "";

  public static String criarTabela() {
    queryCriarTabela = "CREATE TABLE " + TABELA;
    queryCriarTabela += " ( ";
    queryCriarTabela += id + " INTEGER PRIMARY KEY, ";
    queryCriarTabela += idPK + " INTEGER, ";
    queryCriarTabela += materia + " TEXT, ";
    queryCriarTabela += bimestre + " TEXT, ";
    queryCriarTabela += situacao + " TEXT, ";
    queryCriarTabela += notaProva + " REAL, ";
    queryCriarTabela += notaTrabalho + " REAL, ";
    queryCriarTabela += mediaFinal + " REAL ";
    queryCriarTabela += " ) ";
    return queryCriarTabela;
  }

  public static String getTABELA() {
    return TABELA;
  }

  public static String getId() {
    return id;
  }

  public static String getIdPK() {
    return idPK;
  }

  public static String getMateria() {
    return materia;
  }

  public static String getBimestre() {
    return bimestre;
  }

  public static String getSituacao() {
    return situacao;
  }

  public static String getNotaProva() {
    return notaProva;
  }

  public static String getNotaTrabalho() {
    return notaTrabalho;
  }

  public static String getMediaFinal() {
    return mediaFinal;
  }

  public static String getQueryCriarTabela() {
    return queryCriarTabela;
  }

  public static void setQueryCriarTabela(String queryCriarTabela) {
    MediaEscolarDataModel.queryCriarTabela = queryCriarTabela;
  }
}
