
class Transition {
    private State origin;
    private char symbol;
    private State destination;

    public Transition(State origin, char symbol, State destination) {
        this.origin = origin;
        this.symbol = symbol;
        this.destination = destination;
    }

    public State getOrigin() {
        return origin;
    }

    public char getSymbol() {
        return symbol;
    }

    public State getDestination() {
        return destination;
    }
}