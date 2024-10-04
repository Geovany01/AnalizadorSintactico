import java.util.*;

//public class Automaton {
//    Set<Character> alphabet;
//    Set<State> states;
//    State initialState;
//    Set<State> finalStates;
//    List<Transition> transitions;
//
//    public Automaton(Set<Character> alphabet, Set<State> states, State initialState, Set<State> finalStates, List<Transition> transitions) {
//        this.alphabet = alphabet;
//        this.states = states;
//        this.initialState = initialState;
//        this.finalStates = finalStates;
//        this.transitions = transitions;
//    }
//
//    // Methods to add states, transitions, and validate strings
//    public boolean isValidString(String input) {
//        State currentState = initialState;
//
//        for (char symbol : input.toCharArray()) {
//            if (!alphabet.contains(symbol)) {
//                System.out.println("Symbol not allowed in the alphabet: " + symbol);
//                return false;
//            }
//
//            boolean transitionFound = false;
//            for (Transition t : transitions) {
//                if (t.getOrigin().equals(currentState) && t.getSymbol() == symbol) {
//                    currentState = t.getDestination();
//                    transitionFound = true;
//                    break;
//                }
//            }
//
//            if (!transitionFound) {
//                return false;
//            }
//        }
//
//        return currentState.isFinalState();  // Using the isFinalState() method here
//    }
//
//}

//class Automaton {
//    private Set<Character> alphabet;
//    private Set<State> states;
//    private State initialState;
//    private Set<State> finalStates;
//    private List<Transition> transitions;
//
//    public Automaton(Set<Character> alphabet, Set<State> states, State initialState, Set<State> finalStates, List<Transition> transitions) {
//        this.alphabet = alphabet;
//        this.states = states;
//        this.initialState = initialState;
//        this.finalStates = finalStates;
//        this.transitions = transitions;
//    }
//
//    public Set<State> getStates() {
//        return states;
//    }
//
//    public State getInitialState() {
//        return initialState;
//    }
//
//    public Set<State> getFinalStates() {
//        return finalStates;
//    }
//
//    public List<Transition> getTransitions() {
//        return transitions;
//    }
//
//    public boolean isValidString(String inputString) {
//        State currentState = initialState;
//        for (char symbol : inputString.toCharArray()) {
//            boolean transitionFound = false;
//            for (Transition transition : transitions) {
//                if (transition.getOrigin().equals(currentState) && transition.getSymbol() == symbol) {
//                    currentState = transition.getDestination();
//                    transitionFound = true;
//                    break;
//                }
//            }
//            if (!transitionFound) {
//                return false; // No se encontró una transición válida
//            }
//        }
//        return finalStates.contains(currentState); // Verificar si terminó en un estado final
//    }
//}

public class Automaton {
    private Set<Character> alphabet;
    private Set<State> states;
    private State initialState;
    private Set<State> finalStates;
    private List<Transition> transitions;

    public Automaton(Set<Character> alphabet, Set<State> states, State initialState, Set<State> finalStates, List<Transition> transitions) {
        this.alphabet = alphabet;
        this.states = states;
        this.initialState = initialState;
        this.finalStates = finalStates;
        this.transitions = transitions;
    }

    public Set<State> getStates() {
        return states;
    }

    public State getInitialState() {
        return initialState;
    }

    public Set<State> getFinalStates() {
        return finalStates;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public boolean isValidString(String inputString) {
        State currentState = initialState;
        for (char symbol : inputString.toCharArray()) {
            boolean transitionFound = false;
            for (Transition transition : transitions) {
                if (transition.getOrigin().equals(currentState) && transition.getSymbol() == symbol) {
                    currentState = transition.getDestination();
                    transitionFound = true;
                    break;
                }
            }
            if (!transitionFound) {
                return false; // No se encontró una transición válida
            }
        }
        return finalStates.contains(currentState); // Verificar si terminó en un estado final
    }

    // Método para determinar si el autómata es un AFD
    public boolean isDeterministic() {
        Map<State, Map<Character, State>> transitionMap = new HashMap<>();
        for (Transition transition : transitions) {
            State origin = transition.getOrigin();
            char symbol = transition.getSymbol();
            State destination = transition.getDestination();

            if (!transitionMap.containsKey(origin)) {
                transitionMap.put(origin, new HashMap<>());
            }

            Map<Character, State> symbolMap = transitionMap.get(origin);
            if (symbolMap.containsKey(symbol)) {
                return false; // Más de una transición para el mismo símbolo en un estado
            }

            symbolMap.put(symbol, destination);
        }

        return true;
    }

    // Método para obtener una expresión regular (simplificada)
    public String getExpression() {
        // Este método requiere un algoritmo complejo para obtener la expresión regular
        // Por simplicidad, retorna un mensaje indicando que la funcionalidad no está implementada
        return "Expression regular generation is not implemented.";
//        TODO: CONTINUAR CON LA GENRACIÓN DE EXPRESIONES REGULARES
    }
}


