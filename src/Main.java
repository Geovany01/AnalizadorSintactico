import java.util.*;

public class Main {
    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);

        // Define the alphabet
        System.out.println("Enter the alphabet symbols (without spaces, e.g., ab):");
//        String alphabetInput = scanner.nextLine();
//        Set<Character> alphabet = new HashSet<>();
//        for (char c : alphabetInput.toCharArray()) {
//            alphabet.add(c);
//        }
//
//        // Define the states
//        System.out.println("Enter the names of the states (separated by space):");
//        String[] statesInput = scanner.nextLine().split(" ");
//        Set<State> states = new HashSet<>();
//        for (String name : statesInput) {
//            states.add(new State(name, false));
//        }
//
//        // Define the initial state
//        System.out.println("Enter the name of the initial state:");
//        String initialStateInput = scanner.nextLine();
//        State initialState = states.stream().filter(e -> e.getName().equals(initialStateInput)).findFirst().orElse(null);
//
//        // Define the final states
//        System.out.println("Enter the names of the final states (separated by space):");
//        String[] finalStatesInput = scanner.nextLine().split(" ");
//        Set<State> finalStates = new HashSet<>();
//        for (String name : finalStatesInput) {
//            states.stream().filter(e -> e.getName().equals(name)).forEach(e -> {
//                e.isFinal = true;
//                finalStates.add(e);
//            });
//        }
//
//        // Define the transitions
//        System.out.println("Enter transitions (format: origin_state symbol destination_state), type 'end' to finish:");
//        List<Transition> transitions = new ArrayList<>();
//        while (true) {
//            String transitionInput = scanner.nextLine();
//            if (transitionInput.equalsIgnoreCase("end")) break;
//
//            String[] parts = transitionInput.split(" ");
//            State origin = states.stream().filter(e -> e.getName().equals(parts[0])).findFirst().orElse(null);
//            char symbol = parts[1].charAt(0);
//            State destination = states.stream().filter(e -> e.getName().equals(parts[2])).findFirst().orElse(null);
//
//            if (origin != null && destination != null) {
//                transitions.add(new Transition(origin, symbol, destination));
//            } else {
//                System.out.println("State not found, please try again.");
//            }
//        }
//
//        // Create the automaton
//        Automaton automaton = new Automaton(alphabet, states, initialState, finalStates, transitions);
//
//        // Validate strings
//        while (true) {
//            System.out.println("Enter a string to validate (type 'exit' to finish):");
//            String inputString = scanner.nextLine();
//            if (inputString.equalsIgnoreCase("exit")) break;
//
//            boolean isValid = automaton.isValidString(inputString);
//            System.out.println("The string is " + (isValid ? "valid" : "not valid") + " for the automaton.");
//        }
//
//        scanner.close();
    }
}
