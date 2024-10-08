import java.util.Objects;

class State {
    private String name;
    private boolean isFinal;

    public State(String name, boolean isFinal) {
        this.name = name;
        this.isFinal = isFinal;
    }

    public String getName() {
        return name;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(name, state.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}