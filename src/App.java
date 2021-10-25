import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.PatternSyntaxException;

public class App {

    public static List<Transition> FuncReturnRead(String from, List<Transition> afdTransitions) {
        List<Transition> TransitionDoFrom = new ArrayList();

        for (int ac = 0; ac < afdTransitions.size(); ac++) {
            if (afdTransitions.get(ac).getFrom().equals(from)) {
                TransitionDoFrom.add(afdTransitions.get(ac));
            }
        }
        return TransitionDoFrom;
    }

    

    public static void main(String[] args) throws PatternSyntaxException {

        String[] ListTempRead;

        List<State> maquinaList = new ArrayList();
        List<String> ListaDeidComFinal = new ArrayList();
        List<State> ListaEstadosAFD = new ArrayList();

        List<Transition> Transition = new ArrayList();/// Lista de trasnsicao

        List<Transition> TransitionAFD = new ArrayList();

        Path fileName = Path.of("Dados/Input/Automato.txt");
        Path palavras = Path.of("Dados/Input/frases.txt");
        Path analise = Path.of("Dados/Output/AnaliseDeFrases.txt");
        Path AFD = Path.of("Dados/Output/AFD.txt");

        String actual;
        String frasesLidas;

        int indiceEstadoInicial = 0;
        try {
            File f = new File("Dados/Output/AnaliseDeFrases.txt");
            if (!f.exists()) {
                f.createNewFile();
            } else {
                f.delete();
                f.createNewFile();
            }
            File f1 = new File("Dados/Output/AFD.txt");
            if (!f1.exists()) {
                f1.createNewFile();
            } else {
                f1.delete();
                f1.createNewFile();
            }

            frasesLidas = Files.readString(palavras);
            actual = Files.readString(fileName);

            String[] pr = frasesLidas.split("\n");
            String[] ar = actual.split("&#13;");// RETIRA O &#13;

            Scanner in = new Scanner(new FileReader("Dados/Input/regexEstados.txt"));
            Scanner tr = new Scanner(new FileReader("Dados/Input/regexTransition.txt"));

            String id = "";

            String fromTransaction = "";
            String readTransaction = "";
            String toTransaction = "";

            String inicial = "";
            String final2 = "";

            String linha3 = tr.nextLine();
            String patternTransition = linha3;

            String linha2 = in.nextLine();
            String pattern = linha2;

            for (int plo = 0; plo < ar.length; plo++) {/// LE ESTADOS
                if (ar[plo].contains("<state")) {
                    String line = ar[plo].trim();
                    Pattern r = Pattern.compile(pattern);
                    Matcher mm = r.matcher(line);

                    if (mm.find()) {
                        id = mm.group(0);
                        id = id.replace("id=", "");// "0"

                    }
                }

                if (ar[plo].contains("<initial/>")) {
                    inicial = "I";
                }

                if (ar[plo].contains("<final/>")) {
                    final2 = "f";
                }

                if (ar[plo].contains("</state>")) {
                    maquinaList.add(new State(id, inicial, final2));
                    inicial = "";
                    final2 = "";
                }

                if (ar[plo].contains("<!--The list of transitions.-->&#13;")) {
                    plo = 0;
                    break;
                }

            }

            for (int plo = 0; plo < ar.length; plo++) {// LE TRANSIÇOES
                if (ar[plo].contains("<from>")) {
                    String lineTransition = ar[plo].trim();
                    Pattern r = Pattern.compile(patternTransition);
                    Matcher mm = r.matcher(lineTransition);

                    if (mm.find()) {
                        fromTransaction = mm.group(0);
                    }
                }
                if (ar[plo].contains("<read>")) {
                    String lineTransition = ar[plo].trim();
                    Pattern r = Pattern.compile(patternTransition);
                    Matcher mm = r.matcher(lineTransition);

                    if (mm.find()) {
                        readTransaction = mm.group(0);
                    }
                }
                if (ar[plo].contains("<to>")) {
                    String lineTransition = ar[plo].trim();
                    Pattern r = Pattern.compile(patternTransition);
                    Matcher mm = r.matcher(lineTransition);

                    if (mm.find()) {
                        toTransaction = mm.group(0);
                    }
                }

                if (ar[plo].contains("</transition>")) {
                    // to from read
                    Transition.add(new Transition(toTransaction, fromTransaction, readTransaction));
                }

                if (ar[plo].contains("</automaton>&#13;")) {
                    // to from read
                    plo = 0;
                    break;
                }

            }

            for (int r = 0; r < maquinaList.size(); r++) {// Check de tag de estado final
                if (maquinaList.get(r).getEnding().contains("f")) {
                    ListaDeidComFinal.add(maquinaList.get(r).getID());
                }
            }

            for (int p = 0; p < maquinaList.size(); p++) {// Converte afn para afd
                if (maquinaList.get(p).getID().toString().contains(",")) {
                    List<String> ListaAuxiliar = new ArrayList();
                    List<String> listaAuxiliarCopList = new ArrayList();
                    String[] separa = maquinaList.get(p).getID().split(",");

                    for (int j = 0; j < separa.length; j++) {/// INICIO_SEPARA_J
                        List<Integer> TempListIndeces = new ArrayList();
                        for (int k = 0; k < Transition.size(); k++) {/// for dentro da lista transetion
                            if (separa[j].toString().contains(Transition.get(k).getFrom())) {

                                TempListIndeces.add(k);

                            }
                        }

                        for (int z = 0; z < TempListIndeces.size(); z++) {/// KINICIO TEMPLIST Z

                            int n = TempListIndeces.get(z);

                            ListTempRead = Transition.get(n).getRead().split(",");

                            for (int i = 0; i < ListTempRead.length; i++) {

                                boolean flag = false;
                                for (int c = 0; c < ListaAuxiliar.size(); c++) {
                                    if (ListaAuxiliar.get(c).contains(ListTempRead[i])) {
                                        flag = true;
                                    }
                                }

                                if (!flag) {
                                    ListaAuxiliar.add(ListTempRead[i].concat("->"));// {"0->","1->"}
                                }
                            }

                            for (int d = 0; d < ListTempRead.length; d++) {
                                for (int m = 0; m < ListaAuxiliar.size(); m++) {
                                    if (ListaAuxiliar.get(m).contains(ListTempRead[d].concat("->"))) {
                                        ListaAuxiliar.set(m,
                                                ListaAuxiliar.get(m).concat("," + Transition.get(n).getTo()));
                                    }
                                }
                            }
                            listaAuxiliarCopList = List.copyOf(ListaAuxiliar);

                        } /// FIMTEMPLIST Z
                    } /// FIM_SEPARA_J

                    

                    for (int wy = 0; wy < listaAuxiliarCopList.size(); wy++) {
                        char auxREAD = listaAuxiliarCopList.get(wy).charAt(0);// read
                        String auxFrom = maquinaList.get(p).getID();// from
                        String auxto = ListaAuxiliar.get(wy).replace("0->,", "").replace("1->,", "");// TO
                        TransitionAFD.add(new Transition(auxto, auxFrom, Character.toString(auxREAD)));// adiciona a
                                                                                                       // nova transição

                    
                    }

                    for (int b = 0; b < ListaAuxiliar.size(); b++) {/// INICIO_LISTAAUX_B
                        ListaAuxiliar.set(b, ListaAuxiliar.get(b).replace("0->,", "").replace("1->,", ""));
                    } /// FIMLISTAAUX_B

                    boolean flag2 = false;
                    List<Integer> ListaDeIndiceMaquina = new ArrayList();

                    for (int x = 0; x < ListaAuxiliar.size(); x++) {/// INICIO_VERIFICA_SE_A_MAQUINA_EXISTE X
                        for (int a = 0; a < maquinaList.size(); a++) {
                            if (maquinaList.get(a).getID().contains(ListaAuxiliar.get(x))) {// verifica se o novo estado
                                                                                            // existe na lista de
                                                                                            // estados
                                flag2 = true;
                            }
                        }
                        if (!flag2) {

                            ListaDeIndiceMaquina.add(x);// se o estado não existir eu adiciono o indice nessa lista
                        }
                    } /// FIM_VERIFICA_SE_A_MAQUINA_EXISTE X
                      
                    List<String> ListaDeMaquinaRecebeFalse = new ArrayList();
                    boolean flag3 = false;
                    for (int s = 0; s < ListaDeIndiceMaquina.size(); s++) {
                        /// inicio_ADD_MAQUINA
                        for (int zz = 0; zz < ListaDeidComFinal.size(); zz++) {
                            flag3 = false;
                            if (ListaAuxiliar.get(ListaDeIndiceMaquina.get(s)).contains(ListaDeidComFinal.get(zz))) {
                                flag3 = true;
                            }

                        }

                        if (flag3) {
                            ListaDeMaquinaRecebeFalse.add(Integer.toString(s) + "-true");
                        } else {
                            ListaDeMaquinaRecebeFalse.add(Integer.toString(s) + "-false");
                        }

                     

                    } /// FIM_ADD_MAQUINA
                    for (int h = 0; h < ListaDeMaquinaRecebeFalse.size(); h++) {
                        if (ListaDeMaquinaRecebeFalse.get(h).contains("true")) {
                            int aux1 = 0;
                            aux1 = Integer.parseInt((ListaDeMaquinaRecebeFalse.get(h).replace("-true", "")));
                            maquinaList.add(new State(ListaAuxiliar.get(ListaDeIndiceMaquina.get(aux1)), "", "f"));
                            ListaEstadosAFD.add(new State(ListaAuxiliar.get(ListaDeIndiceMaquina.get(aux1)), "", "f"));

                            // ESTADO ANTERIOR AO FROM

                        } else if ((ListaDeMaquinaRecebeFalse.get(h).contains("false"))) {
                            int aux5 = 0;
                            aux5 = Integer.parseInt((ListaDeMaquinaRecebeFalse.get(h).replace("-false", "")));
                            maquinaList.add(new State(ListaAuxiliar.get(ListaDeIndiceMaquina.get(aux5)), "", ""));
                            ListaEstadosAFD.add(new State(ListaAuxiliar.get(ListaDeIndiceMaquina.get(aux5)), "", ""));

                        }
                    }

                } else {/// estado nao tem virgula
                    List<Integer> TempListIndeces = new ArrayList();
                    List<String> ListaAuxiliar = new ArrayList();
                    List<String> listaAuxiliarCopList = new ArrayList();
                    for (int k = 0; k < Transition.size(); k++) {/// for dentro da lista transetion
                        if (maquinaList.get(p).getID().contains(Transition.get(k).getFrom())) {

                            TempListIndeces.add(k);

                        }
                    }

                    for (int z = 0; z < TempListIndeces.size(); z++) {/// iNICIO TEMPLIST_Z

                        int n = TempListIndeces.get(z);

                        ListTempRead = Transition.get(n).getRead().split(",");

                        for (int i = 0; i < ListTempRead.length; i++) {

                            boolean flag = false;
                            for (int c = 0; c < ListaAuxiliar.size(); c++) {
                                if (ListaAuxiliar.get(c).contains(ListTempRead[i])) {
                                    flag = true;
                                }
                            }

                            if (!flag) {
                                ListaAuxiliar.add(ListTempRead[i].concat("->"));// {"0->","1->"}
                            }
                        }

                        for (int d = 0; d < ListTempRead.length; d++) {
                            for (int m = 0; m < ListaAuxiliar.size(); m++) {
                                if (ListaAuxiliar.get(m).contains(ListTempRead[d].concat("->"))) {
                                    ListaAuxiliar.set(m, ListaAuxiliar.get(m).concat("," + Transition.get(n).getTo()));
                                }
                            }
                        }

                        listaAuxiliarCopList = List.copyOf(ListaAuxiliar);

                    } /// FIMTEMPLIST Z

                    for (int wz = 0; wz < listaAuxiliarCopList.size(); wz++) {
                        if (maquinaList.get(p).getInicial().contains("I")) {
                            char auxREAD = listaAuxiliarCopList.get(wz).charAt(0);// read
                            String auxFrom = maquinaList.get(p).getID();// from
                            String auxto = ListaAuxiliar.get(wz).replace("0->,", "").replace("1->,", "");// TO
                            TransitionAFD.add(new Transition(auxto, auxFrom, Character.toString(auxREAD)));// adiciona a
                                                                                                           // nova
                                                                                                           // transiçao
                            if (wz == 0) {
                                ListaEstadosAFD.add(new State(maquinaList.get(p).getID(),
                                        maquinaList.get(p).getInicial(), maquinaList.get(p).getFinal()));
                            }
                        }

                        // char auxREAD ="";
                    }

                    for (int b = 0; b < ListaAuxiliar.size(); b++) {/// INICIO_LISTAAUX_B
                        ListaAuxiliar.set(b, ListaAuxiliar.get(b).replace("0->,", "").replace("1->,", ""));
                    } /// FIMLISTAAUX_B

                    // boolean flag2 = false;
                    List<Integer> ListaDeIndiceMaquina = new ArrayList();

                    for (int x = 0; x < ListaAuxiliar.size(); x++) {/// INICIO_VERIFICA_SE_A_MAQUINA_EXISTE X
                        boolean flag2 = false;
                        for (int a = 0; a < maquinaList.size(); a++) {
                            if (maquinaList.get(a).getID().contains(ListaAuxiliar.get(x))) {
                                flag2 = true;
                            } 
                        }
                        if (flag2 == false) {

                            ListaDeIndiceMaquina.add(x);/// adiciona os indices que não existem na maquina
                        }
                    } /// FIM_VERIFICA_SE_A_MAQUINA_EXISTE X

                    for (int s = 0; s < ListaDeIndiceMaquina.size(); s++) {/// inicio_ADD_MAQUINA
                        // id initial final
                        if (maquinaList.get(0).getFinal().contains("f")) {
                            maquinaList.add(new State(ListaAuxiliar.get(ListaDeIndiceMaquina.get(s)), "", "f"));
                            // id initial final
                            ListaEstadosAFD.add(new State(ListaAuxiliar.get(ListaDeIndiceMaquina.get(s)), "", "f"));
                        } else {
                            maquinaList.add(new State(ListaAuxiliar.get(ListaDeIndiceMaquina.get(s)), "", ""));
                            ListaEstadosAFD.add(new State(ListaAuxiliar.get(ListaDeIndiceMaquina.get(s)), "", ""));
                        }
                    } /// FIM_ADD_MAQUINA

                }
            }

            for (int ba = 0; ba < ListaEstadosAFD.size(); ba++) {
                if (ListaEstadosAFD.get(ba).getInicial().contains("I")) {
                    indiceEstadoInicial = ba;
                }
            }
            ListaEstadosAFD.get(indiceEstadoInicial).getID();/// estado inicial

            boolean flagPrimeiroEstado = false;
            String auxTo = "";
            List<String> PrintaAnalise = new ArrayList<>();
            for (int sp = 0; sp < pr.length; sp++) {/// VETOR DE FRASES

                String from = ListaEstadosAFD.get(indiceEstadoInicial).getID();
                int i = 0;
                do {
                    List<Transition> T = new ArrayList();
                    T = FuncReturnRead(from, TransitionAFD);
                    for (int rj = 0; rj < T.size(); rj++) {
                        if (Character.toString(pr[sp].charAt(i)).equals(T.get(rj).getRead())) {
                            // String aux = T.get(rj).getTo();
                            from = T.get(rj).getTo();
                        }
                    }

                    i++;
                } while (pr[sp].length() > i);

                for (int mt = 0; mt < ListaEstadosAFD.size(); mt++) {
                    if (from.equals(ListaEstadosAFD.get(mt).getID())) {
                        if (ListaEstadosAFD.get(mt).getFinal().contains("f")) {

                            String aux = pr[sp].replace("\n", "");
                            PrintaAnalise.add("ACEITA-> " + aux + "\n");
                            break;
                        } else {
                            String aux = pr[sp].replace("\n", "");
                            PrintaAnalise.add("NAO ACEITA-> " + aux + "\n");
                            break;
                        }
                    }
                }
            }
            String baesse = "";
            for (int lista = 0; lista < PrintaAnalise.size(); lista++) {
                baesse = baesse + PrintaAnalise.get(lista);
            }

            Files.writeString(analise, baesse);

            List<String> x = new ArrayList();
            List<String> y = new ArrayList();
            String yago = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 7.1.--><structure>&#13;\n\t<type>fa</type>&#13;\n\t<automaton>&#13;\n\t\t<!--The list of states.-->&#13;";
           

            for (int ll = 0;ll<ListaEstadosAFD.size();ll++ ){
               x.add(ListaEstadosAFD.get(ll).pString(ll));
               yago= yago+x.get(ll);
             
            }

            
            yago=yago+"\n\t\t<!--The list of transitions.-->&#13;";
            

            for(int lll=0;lll<TransitionAFD.size();lll++){
                y.add(TransitionAFD.get(lll).printTransition());
                yago = yago+y.get(lll);
            }

           
            yago=yago+"\n\t</automaton>&#13;\n</structure>";
            Files.writeString(AFD, yago);
            System.out.println("");
             

        } catch (IOException e) {
            e.printStackTrace();
        }



    }

}
