package utils;

import services.State;
import services.Transition;

import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings("DuplicatedCode")
public class ConverterNFAtoDFA {

    // Construtor e variáveis
    private ArrayList<State> accepting = new ArrayList<>();
    private ArrayList<String> alphabet = new ArrayList<>();
    private ArrayList<Transition> transitions = new ArrayList<>();
    private ArrayList<State> states = new ArrayList<>();

    private ArrayList<Transition> newTransitions = new ArrayList<>();
    private ArrayList<State> newStates = new ArrayList<>();
    private ArrayList<State> newAcceptings = new ArrayList<>();

    public ConverterNFAtoDFA(ArrayList<State> accepting, ArrayList<String> alphabet, ArrayList<Transition> transitions,
                             ArrayList<State> states) {
        this.accepting.addAll(accepting);
        this.alphabet.addAll(alphabet);
        this.transitions.addAll(transitions);
        this.states.addAll(states);
    }

    // Algoritmo
    public void execute() {
        ArrayList<Transition> auxTransitions = new ArrayList<>();

        // Pega as transições do primeiro estado.
        auxTransitions.addAll(this.getStateTransitions(this.states.get(0).getValue(), this.transitions));

        // Adiciona as transições no array list das novas transições.
        auxTransitions.forEach(transition -> {
            State to = transition.getFrom();
            State from = transition.getTo();
            String symbol = transition.getAlphabet();
            this.newTransitions.add(new Transition(to, from, symbol));
        });

        // Obs: O while roda ate dar um erro de index
        int index = 0;
        while (true) {
            try {
                // Pega os próximos estados da nova lista de transições.
                State state = this.newTransitions.get(index).getTo();

                // Verifica se o estado já foi lido, caso ele já tenha sido lido, irá possuir transições.
                ArrayList<Transition> transitions = this.getStateTransitions(state.getValue(), this.newTransitions);
                if (transitions == null) {
                    // Separa os estados por vírgula.
                    String[] split_string = state.getValue().split(",");

                    // Percorre os estados pelo alfabeto do automato.
                    for (int i = 0; i < this.alphabet.toArray().length; i++) {
                        StringBuilder stringBuilder = new StringBuilder();

                        // Percorre os estados que foram separados por vírgula.
                        for (String string : split_string) {

                            // Pega as transições do estado no alfabeto.
                            Transition to =
                                    getStateTransitionsWithSymbol(string, this.transitions, this.alphabet.get(i));

                            // Se não tiver transições ele vai para o descarte, senão ele pega o destino daquele estado.
                            String from;
                            if (to == null) {
                                from = "@";
                            } else {
                                from = to.getTo().getValue();
                            }

                            // Verifica se o destino já foi adicionada na string.
                            if (!String.valueOf(stringBuilder).contains(from)) {
                                // Se a string estiver vazia ele não coloca vírgula, senão coloca e adiciona o destino.
                                if (stringBuilder.length() == 0) {
                                    stringBuilder.append(from);
                                } else {
                                    stringBuilder.append(",").append(from);
                                }
                            }

                            // Remove palavras repetidas que sobraram e ordena.
                            stringBuilder = new StringBuilder(
                                    this.removeWordsRepeatedAndOrder(String.valueOf(stringBuilder))
                            );
                        }

                        // Remove alguns destinos de descarte.
                        if (String.valueOf(stringBuilder).contains("@,")) {
                            String string = String.valueOf(stringBuilder);
                            stringBuilder = new StringBuilder(string.replaceAll("@,", ""));
                        } else if (String.valueOf(stringBuilder).contains(",@")) {
                            String string = String.valueOf(stringBuilder);
                            stringBuilder = new StringBuilder(string.replaceAll(",@", ""));
                        }

                        // Adiciona as novas transições.
                        this.newTransitions
                                .add(new Transition(state, new State(stringBuilder.toString()), this.alphabet.get(i)));
                    }

                }
                index++;
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }
    }

private ArrayList<Transition> getStateTransitions(String state, ArrayList<Transition> transitions) {
    ArrayList<Transition> stateTransitions = new ArrayList<>();

    for (Transition transition : transitions) {
        if (transition.getFrom().getValue().equals(state)) {
            stateTransitions.add(transition);
        }
    }
    if (stateTransitions.toArray().length == 0) {
        return null;
    }
    return stateTransitions;
}

private Transition getStateTransitionsWithSymbol(
        String state, ArrayList<Transition> transitions, String symbol
) {
    ArrayList<Transition> stateTransitions = new ArrayList<>();

    for (Transition transition : transitions) {
        if (transition.getFrom().getValue().equals(state) && transition.getAlphabet().equals(symbol)) {
            stateTransitions.add(transition);
        }
    }

    if (stateTransitions.toArray().length == 0) {
        return null;
    }
    return stateTransitions.get(0);

}

    // Função para remover palavras repetidas
    private String removeWordsRepeatedAndOrder(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] strings = string.split(",");
        Arrays.sort(strings);
        for (String str : strings) {
            if (!String.valueOf(stringBuilder).contains(str)) {
                if (stringBuilder.length() == 0) {
                    stringBuilder.append(str);
                } else {
                    stringBuilder.append(",").append(str);
                }
            }
        }

        return stringBuilder.toString();
    }

    // Funções para atualizar os estados, transições e estados de aceitação
    public ArrayList<State> getNewStates() {
        ArrayList<String> read = new ArrayList<>();
        for (Transition transition : this.newTransitions) {
            if (!read.contains(transition.getFrom().getValue())) {
                this.newStates.add(transition.getFrom());
                read.add(transition.getFrom().getValue());
            }
        }
        return this.newStates;
    }

    public ArrayList<Transition> getNewTransitions() {
        return newTransitions;
    }

    public ArrayList<State> getNewAcceptings() {
        for (State accepting : this.accepting) {
            for (State state : this.getNewStates()) {
                if (state.getValue().contains(accepting.getValue())) {
                    this.newAcceptings.add(state);
                }
            }
        }
        return newAcceptings;
    }
}

