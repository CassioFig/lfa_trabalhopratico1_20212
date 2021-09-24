package services;

public class Transition {
    private State from;
    private State to;
    private String alphabet;

    public Transition(State _from, State to, String alphabet) {
        this.from = _from;
        this.to = to;
        this.alphabet = alphabet;
    }

    public State getFrom() {
        return from;
    }

    public State getTo() {
        return to;
    }

    public String getAlphabet() {
        return alphabet;
    }
}
