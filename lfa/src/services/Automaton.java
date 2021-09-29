package services;

import utils.AutomatonReader;
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

}
