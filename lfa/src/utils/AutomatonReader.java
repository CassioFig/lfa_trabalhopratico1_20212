package utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class AutomatonReader<T> {
    public ArrayList<T> getValues(Parameters parameter) throws FileNotFoundException {
        Scanner in = new Scanner(new FileReader("entry.txt"));
        ArrayList<T> values = new ArrayList<>();

        while (in.hasNextLine()) {
            String line = in.nextLine();
            if (line.equals(parameter.value)) {
                line = in.nextLine();

                while (!line.equals(parameter.next().value) && in.hasNextLine()) {
                    values.add((T) line);
                    line = in.nextLine();
                }

                if (parameter == Parameters.TRANSITIONS) {
                    values.add((T) line);
                }
                break;
            }
        }
        return values;
    }
}
