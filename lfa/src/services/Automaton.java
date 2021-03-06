package services;

import utils.AutomatonReader;
import utils.ConverterNFAtoDFA;
import utils.Parameters;

import java.io.FileNotFoundException;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class Automaton {
    
    private AutomatonReader automatonFile;
    private ArrayList<State> states = new ArrayList<>();
    private State initial;
    private ArrayList<State> accepting = new ArrayList<>();
    private ArrayList<String> alphabet = new ArrayList<>();
    private ArrayList<Transition> transitions = new ArrayList<>();

    public Automaton() {
        this.automatonFile = new AutomatonReader();
    }

    
    public void load() {
        try {
            this.initial = new State(automatonFile.getValues(Parameters.INITIAL).get(0).toString());
            automatonFile.getValues(Parameters.STATES)
                    .forEach(item -> this.states.add(new State(item.toString())));
            automatonFile.getValues(Parameters.ACCEPTING)
                    .forEach(item -> this.accepting.add(new State(item.toString())));
            automatonFile.getValues(Parameters.ALPHABET)
                    .forEach(item -> this.alphabet.add(item.toString()));

            this.loadTransitions();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Erro na execução do algoritimo, verifique se os dados passados estão corretos!");
        }
    }
    private void loadTransitions() throws FileNotFoundException {
        ArrayList<String> transitions_ = new ArrayList<>();
        ArrayList<String> to = new ArrayList<>();
        ArrayList<String> from = new ArrayList<>();
        ArrayList<String> symbols = new ArrayList<>();

        automatonFile.getValues(Parameters.TRANSITIONS).forEach(item -> transitions_.add(item.toString()));
        transitions_.forEach(item -> to.add(item.split(":")[0]));
        transitions_.forEach(item -> symbols.add(item.split(":")[1].split(">")[0]));
        transitions_.forEach(item -> from.add(item.split(":")[1].split(">")[1]));

        for (int i = 0; i < transitions_.toArray().length; i++) {
            State to_ = new State(to.get(i));
            State from_ = new State(from.get(i));

            this.transitions.add(new Transition(to_, from_, symbols.get(i)));
        }
    }

    public String testString(String string) {
        if (isNFA()) {
            this.NFAtoDFA();
            System.out.println("Autômato:");
            this.printAutomaton();
        }
        return test(string);
    }

    private boolean isNFA() {
        for (Transition transition : transitions) {
            if (transition.getTo().getValue().contains(",")) {
                return true;
            }
        }
        return false;
    }

    private void NFAtoDFA() {
        ConverterNFAtoDFA converter = new ConverterNFAtoDFA(accepting, alphabet, transitions, states);
        converter.execute();

        this.accepting.clear();
        this.transitions.clear();
        this.states.clear();

        this.accepting.addAll(converter.getNewAcceptings());
        this.transitions.addAll(converter.getNewTransitions());
        this.states.addAll(converter.getNewStates());
    }

    private State getNextState(State to, String symbol) {
        for (Transition transition: transitions) {
            if (transition.getFrom().getValue().equals(to.getValue())
                    && transition.getAlphabet().equals(symbol)) {
                return transition.getTo();
            }
        }
        return null;
    }

    private boolean isAccepted(State state) {
        for (State state_ : accepting) {
            if (state.getValue().equals(state_.getValue())) {
                return true;
            }
        }
        return false;
    }

    // Função para testar a string
    private String test(String string) {
        State state = this.initial;
        int index = 0;
        while (index < string.length()) {
            if (stringNotInAlphabet(string)) {
                return "Palavra não existe no alfabeto!";
            }
            String symbol = String.valueOf(string.charAt(index));
            state = getNextState(state, symbol);

            if (state == null) {
                return "String recusada!";
            }
            index++;
        }

        if (isAccepted(state)) {
            return "String aceita!";
        } else {
            return "String recusada!";
        }
    }

    // Função para testar se a string está no alfabeto
    private boolean stringNotInAlphabet(String string) {
        boolean isValid = true;
        int index = 0;
        while (index < string.length()) {
            isValid = !alphabet.contains(String.valueOf(string.charAt(index)));
            index++;
        }
        return isValid;
    }

    private void printAutomaton() {
        System.out.println("#transitions");
        this.transitions.forEach(transition -> {
                String transition_ = transition.getFrom().getValue() +
                        ":" + transition.getAlphabet() +
                        ">" + transition.getTo().getValue();

                if (transition_.contains(",")) {
                    System.out.println(transition_.replace(",", ""));
                } else {
                    System.out.println(transition_);
                }
        });
    }

}
