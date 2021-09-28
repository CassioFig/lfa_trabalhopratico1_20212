package services;

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

}
