import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.QuadCurve2D;
import java.util.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

// Clase principal de la interfaz gráfica
class AutomatonGUI extends JFrame {
    private JTextField alphabetField, statesField, initialStateField, finalStatesField, transitionsField, inputStringField;
    private JTextArea outputArea;
    private Automaton automaton;
    private DrawPanel drawPanel; // Panel para dibujar el autómata

    public AutomatonGUI() {
        setTitle("Automaton Validator");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(9, 1));

        // Campo de entrada para el alfabeto
        alphabetField = new JTextField();
        inputPanel.add(new JLabel("Enter the alphabet symbols (e.g., ab):"));
        inputPanel.add(alphabetField);

        // Campo de entrada para los estados
        statesField = new JTextField();
        inputPanel.add(new JLabel("Enter the states (e.g., q0 q1 q2):"));
        inputPanel.add(statesField);

        // Campo de entrada para el estado inicial
        initialStateField = new JTextField();
        inputPanel.add(new JLabel("Enter the initial state:"));
        inputPanel.add(initialStateField);

        // Campo de entrada para los estados finales
        finalStatesField = new JTextField();
        inputPanel.add(new JLabel("Enter the final states (e.g., q1 q2):"));
        inputPanel.add(finalStatesField);

        // Campo de entrada para las transiciones
        transitionsField = new JTextField();
        inputPanel.add(new JLabel("Enter transitions (e.g., q0 a q1, separate with commas):"));
        inputPanel.add(transitionsField);

        // Botón para crear el autómata
        JButton createAutomatonButton = new JButton("Create Automaton");
        inputPanel.add(createAutomatonButton);

        // Campo de entrada para la cadena de validación
        inputStringField = new JTextField();
        inputPanel.add(new JLabel("Enter a string to validate:"));
        inputPanel.add(inputStringField);

        // Área de salida
        outputArea = new JTextArea();
        outputArea.setEditable(false);

        add(new JScrollPane(outputArea), BorderLayout.SOUTH);
        add(inputPanel, BorderLayout.WEST);

        // Panel para dibujar el autómata
        drawPanel = new DrawPanel();
        add(drawPanel, BorderLayout.CENTER);

        // Action listener para crear el autómata
        createAutomatonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAutomaton();
                drawPanel.repaint(); // Actualizar el panel de dibujo
            }
        });

        // Botón para validar la cadena
        JButton validateStringButton = new JButton("Validate String");
        inputPanel.add(validateStringButton);

        // Action listener para validar la cadena
        validateStringButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateString();
            }
        });

        // Botón para mostrar si el autómata es un AFD
        JButton checkDeterministicButton = new JButton("Check if Deterministic");
        inputPanel.add(checkDeterministicButton);

        // Action listener para verificar si el autómata es un AFD
        checkDeterministicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkDeterministic();
            }
        });

        // Botón para mostrar la expresión regular
        JButton showExpressionButton = new JButton("Show Expression");
        inputPanel.add(showExpressionButton);

        // Action listener para mostrar la expresión regular
        showExpressionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showExpression();
            }
        });

        setVisible(true);
    }

    private void createAutomaton() {
        // Parsear el alfabeto
        String alphabetInput = alphabetField.getText();
        Set<Character> alphabet = new HashSet<>();
        for (char c : alphabetInput.toCharArray()) {
            alphabet.add(c);
        }

        // Parsear los estados
        String[] statesInput = statesField.getText().split(" ");
        Set<State> states = new HashSet<>();
        for (String name : statesInput) {
            states.add(new State(name, false));
        }

        // Parsear el estado inicial
        String initialStateInput = initialStateField.getText();
        State initialState = states.stream().filter(e -> e.getName().equals(initialStateInput)).findFirst().orElse(null);

        // Parsear los estados finales
        String[] finalStatesInput = finalStatesField.getText().split(" ");
        Set<State> finalStates = new HashSet<>();
        for (String name : finalStatesInput) {
            states.stream().filter(e -> e.getName().equals(name)).forEach(e -> {
                e.setFinal(true);
                finalStates.add(e);
            });
        }

        // Parsear las transiciones
        String[] transitionsInput = transitionsField.getText().split(",");
        List<Transition> transitions = new ArrayList<>();
        for (String tInput : transitionsInput) {
            String[] parts = tInput.trim().split(" ");
            State origin = states.stream().filter(e -> e.getName().equals(parts[0])).findFirst().orElse(null);
            char symbol = parts[1].charAt(0);
            State destination = states.stream().filter(e -> e.getName().equals(parts[2])).findFirst().orElse(null);

            if (origin != null && destination != null) {
                transitions.add(new Transition(origin, symbol, destination));
            } else {
                outputArea.append("Invalid transition: " + tInput + "\n");
            }
        }

        // Crear el autómata
        automaton = new Automaton(alphabet, states, initialState, finalStates, transitions);
        drawPanel.setAutomaton(automaton); // Pasar el autómata al panel de dibujo
        outputArea.append("Automaton created successfully!\n");
    }

    private void validateString() {
        String inputString = inputStringField.getText();
        if (automaton != null) {
            boolean isValid = automaton.isValidString(inputString);
            drawPanel.setInputString(inputString);
            JOptionPane.showMessageDialog(this, "The string \"" + inputString + "\" is " + (isValid ? "VALID" : "INVALID"));
        } else {
            JOptionPane.showMessageDialog(this, "Automaton is not created yet.");
        }
    }

    private void checkDeterministic() {
        if (automaton != null) {
            boolean isDeterministic = automaton.isDeterministic();
            JOptionPane.showMessageDialog(this, "The automaton is " + (isDeterministic ? "DETERMINISTIC" : "NON-DETERMINISTIC"));
        } else {
            JOptionPane.showMessageDialog(this, "Automaton is not created yet.");
        }
    }

    private void showExpression() {
        if (automaton != null) {
            String expression = automaton.getExpression();
            JOptionPane.showMessageDialog(this, "Regular Expression: " + expression);
        } else {
            JOptionPane.showMessageDialog(this, "Automaton is not created yet.");
        }
    }

    public static void main(String[] args) {
        new AutomatonGUI();
    }
}

// Clase para dibujar el diagrama del autómata
class DrawPanel extends JPanel {
    private Automaton automaton;
    private String inputString;
    private boolean isValidString;

    public void setAutomaton(Automaton automaton) {
        this.automaton = automaton;
        repaint();
    }

    public void setInputString(String inputString) {
        this.inputString = inputString;
        if (automaton != null) {
            this.isValidString = automaton.isValidString(inputString);
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (automaton != null) {
            drawAutomaton(g);
            drawValidationResult(g);
        }
    }

    private void drawAutomaton(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dibujar los estados como círculos
        int radius = 30;
        int xOffset = 100;
        int yOffset = 100;
        int gap = 100;
        int i = 0;

        Map<State, Point> statePositions = new HashMap<>();

        for (State state : automaton.getStates()) {
            int x = xOffset + i * gap;
            int y = yOffset;
            g2d.drawOval(x, y, radius * 2, radius * 2);
            g2d.drawString(state.getName(), x + radius - 10, y + radius);

            // Marcar el estado inicial con una flecha
            if (state.equals(automaton.getInitialState())) {
                g2d.drawLine(x - 20, y + radius, x, y + radius);
            }

            // Dibujar estados finales con doble círculo
            if (state.isFinal()) {
                g2d.drawOval(x + 3, y + 3, radius * 2 - 6, radius * 2 - 6);
            }

            statePositions.put(state, new Point(x + radius, y + radius));
            i++;
        }

        // Dibujar las transiciones
        for (Transition transition : automaton.getTransitions()) {
            Point origin = statePositions.get(transition.getOrigin());
            Point destination = statePositions.get(transition.getDestination());
            QuadCurve2D curve = new QuadCurve2D.Float(
                    origin.x, origin.y,
                    (origin.x + destination.x) / 2, origin.y - 50, // Control point for the curve
                    destination.x, destination.y
            );
            g2d.draw(curve);
            g2d.drawString(String.valueOf(transition.getSymbol()),
                    (origin.x + destination.x) / 2,
                    (origin.y + destination.y) / 2 - 10);
        }
    }

    private void drawValidationResult(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(isValidString ? Color.GREEN : Color.RED);
        g2d.drawString("String \"" + inputString + "\" is " + (isValidString ? "VALID" : "INVALID"), 20, 20);
    }
}
