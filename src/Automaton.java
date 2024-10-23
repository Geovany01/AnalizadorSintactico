import java.util.*;

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
        // Modificación para aceptar AFN
        return isValidStringHelper(initialState, inputString, 0);
    }


    private boolean isValidStringHelper(State currentState, String inputString, int position) {
        // Si hemos procesado toda la cadena
        if (position == inputString.length()) {
            // Aceptar si estamos en un estado final o si podemos llegar a un estado final mediante transiciones epsilon
            if (finalStates.contains(currentState)) {
                return true;
            }
            // Explorar transiciones epsilon desde el estado actual
            boolean accepted = false;
            for (Transition transition : transitions) {
                if (transition.getOrigin().equals(currentState) && transition.getSymbol() == 'ε') {
                    accepted |= isValidStringHelper(transition.getDestination(), inputString, position);
                }
            }
            return accepted;
        }

        char currentSymbol = inputString.charAt(position);
        boolean accepted = false;

        // Recorre todas las transiciones desde el estado actual
        for (Transition transition : transitions) {
            if (transition.getOrigin().equals(currentState)) {
                if (transition.getSymbol() == currentSymbol) {
                    // Transición consumiendo un símbolo
                    accepted |= isValidStringHelper(transition.getDestination(), inputString, position + 1);
                } else if (transition.getSymbol() == 'ε') {
                    // Transición epsilon sin consumir un símbolo
                    accepted |= isValidStringHelper(transition.getDestination(), inputString, position);
                }
            }
        }

        return accepted;
    }

    public boolean isDeterministic() {
        Map<State, Map<Character, List<State>>> transitionMap = new HashMap<>();
        for (Transition transition : transitions) {
            State origin = transition.getOrigin();
            char symbol = transition.getSymbol();
            State destination = transition.getDestination();

            // Si hay una transición epsilon, el autómata es no determinista
            if (symbol == 'ε') {
                return false;
            }

            if (!transitionMap.containsKey(origin)) {
                transitionMap.put(origin, new HashMap<>());
            }

            Map<Character, List<State>> symbolMap = transitionMap.get(origin);
            symbolMap.putIfAbsent(symbol, new ArrayList<>());
            symbolMap.get(symbol).add(destination);
        }

        for (Map<Character, List<State>> symbolMap : transitionMap.values()) {
            for (List<State> destinations : symbolMap.values()) {
                if (destinations.size() > 1) {
                    return false; // Más de una transición para el mismo símbolo en un estado
                }
            }
        }

        return true;
    }

    // Método para obtener una expresión regular (simplificada)
    public String getExpression() {
        return "Expression regular generation is not implemented.";
    }
}


