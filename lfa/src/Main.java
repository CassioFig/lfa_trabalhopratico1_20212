import services.Automaton;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Automaton automaton = new Automaton();
        automaton.load();

        String string = JOptionPane.showInputDialog("Digite uma palavra");
        JOptionPane.showMessageDialog(null, automaton.testString(string));
    }
}