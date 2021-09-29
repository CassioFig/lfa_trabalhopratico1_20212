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
