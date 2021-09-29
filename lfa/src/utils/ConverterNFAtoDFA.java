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
