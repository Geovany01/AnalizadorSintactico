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
    private DrawPanel drawPanel;

    public AutomatonGUI() {
        setTitle("Automaton Validator");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(10, 1)); // Aumentar a 10 para incluir documentación

        alphabetField = new JTextField();
        inputPanel.add(new JLabel("Enter the alphabet symbols (e.g., ab):"));
        inputPanel.add(alphabetField);

        statesField = new JTextField();
        inputPanel.add(new JLabel("Enter the states (e.g., q0 q1 q2):"));
        inputPanel.add(statesField);

        initialStateField = new JTextField();
        inputPanel.add(new JLabel("Enter the initial state:"));
        inputPanel.add(initialStateField);

        finalStatesField = new JTextField();
        inputPanel.add(new JLabel("Enter the final states (e.g., q1 q2):"));
        inputPanel.add(finalStatesField);

        transitionsField = new JTextField();
        inputPanel.add(new JLabel("Enter transitions (e.g., q0 a q1, separate with commas):"));
        inputPanel.add(transitionsField);

        JButton createAutomatonButton = new JButton("Create Automaton");
        inputPanel.add(createAutomatonButton);

        inputStringField = new JTextField();
        inputPanel.add(new JLabel("Enter a string to validate:"));
        inputPanel.add(inputStringField);

        outputArea = new JTextArea();
        outputArea.setEditable(false);

        add(new JScrollPane(outputArea), BorderLayout.SOUTH);
        add(inputPanel, BorderLayout.WEST);

        drawPanel = new DrawPanel();
        add(drawPanel, BorderLayout.CENTER);

        createAutomatonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAutomaton();
                drawPanel.repaint();
            }
        });

        JButton validateStringButton = new JButton("Validate String");
        inputPanel.add(validateStringButton);

        validateStringButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateString();
            }
        });

        JButton checkDeterministicButton = new JButton("Check if Deterministic");
        inputPanel.add(checkDeterministicButton);

        checkDeterministicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkDeterministic();
            }
        });

//        JButton showDocumentationButton = new JButton("Show Documentation");
//        inputPanel.add(showDocumentationButton);
//
//        showDocumentationButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                showDocumentation();
//            }
//        });

        setVisible(true);
    }

    private void createAutomaton() {
        String alphabetInput = alphabetField.getText();
        Set<Character> alphabet = new HashSet<>();
        for (char c : alphabetInput.toCharArray()) {
            alphabet.add(c);
        }

        String[] statesInput = statesField.getText().split(" ");
        Set<State> states = new HashSet<>();
        for (String name : statesInput) {
            states.add(new State(name, false));
        }

        String initialStateInput = initialStateField.getText();
        State initialState = states.stream().filter(e -> e.getName().equals(initialStateInput)).findFirst().orElse(null);

        String[] finalStatesInput = finalStatesField.getText().split(" ");
        Set<State> finalStates = new HashSet<>();
        for (String name : finalStatesInput) {
            states.stream().filter(e -> e.getName().equals(name)).forEach(e -> {
                e.setFinal(true);
                finalStates.add(e);
            });
        }

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

        automaton = new Automaton(alphabet, states, initialState, finalStates, transitions);
        drawPanel.setAutomaton(automaton);
    }

//    private void validateString() {
//        String inputString = inputStringField.getText();
//        if (automaton != null) {
//            boolean isValid = automaton.isValidString(inputString);
//            outputArea.append("String '" + inputString + "' is " + (isValid ? "valid." : "invalid.") + "\n");
//        } else {
//            outputArea.append("Automaton not created.\n");
//        }
//    }
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
            String result = "The automaton is " + (isDeterministic ? "deterministic." : "non-deterministic.") + "\n";
            JOptionPane.showMessageDialog(this, result);
        } else {
            JOptionPane.showMessageDialog(this, "Automaton not created.\n");
//            outputArea.append("Automaton not created.\n");
        }
    }

//    private void showDocumentation() {
//        String documentation = "To enter an AFD or AFN:\n" +
//                "1. Enter the alphabet symbols (e.g., 'ab').\n" +
//                "2. Enter the states separated by spaces (e.g., 'q0 q1 q2').\n" +
//                "3. Specify the initial state.\n" +
//                "4. Enter the final states separated by spaces (e.g., 'q1').\n" +
//                "5. Specify transitions in the format 'origin symbol destination', separated by commas (e.g., 'q0 a q1, q1 b q2').\n" +
//                "6. Enter a string to validate against the automaton.";
//        outputArea.append(documentation + "\n");
//    }

    public static void main(String[] args) {
        new AutomatonGUI();
    }
}

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

//    private void drawAutomaton(Graphics g) {
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//        // Dibujar los estados como círculos
//        int radius = 30;
//        int xOffset = 100;
//        int yOffset = 100;
//        int gap = 100;
//        int i = 0;
//
//        Map<State, Point> statePositions = new HashMap<>();
//
//        for (State state : automaton.getStates()) {
//            int x = xOffset + i * gap;
//            int y = yOffset;
//            g2d.drawOval(x, y, radius * 2, radius * 2);
//            g2d.drawString(state.getName(), x + radius - 10, y + radius);
//
//            // Marcar el estado inicial con una flecha visible
//            if (state.equals(automaton.getInitialState())) {
//                // Cambiar el color de la flecha a rojo
//                g2d.setColor(Color.RED);
//
//                // Ajustamos la flecha para que se dibuje más a la izquierda del círculo del estado inicial
//                int arrowXStart = x - 70;  // Alejamos la flecha más a la izquierda del círculo
//                int arrowXEnd = x - radius;  // La flecha termina en el borde del círculo
//                int arrowY = y + radius;   // Alineamos verticalmente la flecha con el centro del círculo
//
//                // Dibujar la línea de la flecha
//                g2d.drawLine(arrowXStart, arrowY, arrowXEnd, arrowY);
//
//                // Dibujar la cabeza de la flecha como un triángulo
//                int[] arrowHeadX = {arrowXEnd, arrowXEnd + 10, arrowXEnd + 10};
//                int[] arrowHeadY = {arrowY, arrowY - 5, arrowY + 5};
//                g2d.fillPolygon(arrowHeadX, arrowHeadY, 3);
//
//                // Restablecer el color a negro después de dibujar la flecha
//                g2d.setColor(Color.BLACK);
//            }
//
//            // Dibujar estados finales con doble círculo
//            if (state.isFinal()) {
//                g2d.drawOval(x + 3, y + 3, radius * 2 - 6, radius * 2 - 6);
//            }
//
//            statePositions.put(state, new Point(x + radius, y + radius));
//            i++;
//        }
//
//        // Dibujar las transiciones
//        for (Transition transition : automaton.getTransitions()) {
//            Point origin = statePositions.get(transition.getOrigin());
//            Point destination = statePositions.get(transition.getDestination());
//            QuadCurve2D curve = new QuadCurve2D.Float(
//                    origin.x, origin.y,
//                    (origin.x + destination.x) / 2, origin.y - 50, // Punto de control para la curva
//                    destination.x, destination.y
//            );
//            g2d.draw(curve);
//            g2d.drawString(String.valueOf(transition.getSymbol()),
//                    (origin.x + destination.x) / 2,
//                    (origin.y + destination.y) / 2 - 10);
//        }
//    }

//    private void drawAutomaton(Graphics g) {
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//        // Dibujar los estados como círculos, dispersándolos en un patrón circular
//        int radius = 30;
//        int panelWidth = getWidth();
//        int panelHeight = getHeight();
//        int centerX = panelWidth / 2;
//        int centerY = panelHeight / 2;
//        int circleRadius = 200;  // Radio del círculo en el que se disponen los estados
//        int stateCount = automaton.getStates().size();
//        double angleStep = 2 * Math.PI / stateCount;  // Ángulo entre cada estado
//
//        Map<State, Point> statePositions = new HashMap<>();
//        int i = 0;
//
//        for (State state : automaton.getStates()) {
//            // Calcular la posición de cada estado en un círculo
//            double angle = i * angleStep;
//            int x = (int) (centerX + circleRadius * Math.cos(angle) - radius);
//            int y = (int) (centerY + circleRadius * Math.sin(angle) - radius);
//            g2d.drawOval(x, y, radius * 2, radius * 2);
//            g2d.drawString(state.getName(), x + radius - 10, y + radius);
//
//            // Marcar el estado inicial con una flecha visible
//            if (state.equals(automaton.getInitialState())) {
//                g2d.setColor(Color.RED);
//                int arrowXStart = x - 70;
//                int arrowXEnd = x - radius;
//                int arrowY = y + radius;
//                g2d.drawLine(arrowXStart, arrowY, arrowXEnd, arrowY);
//                int[] arrowHeadX = {arrowXEnd, arrowXEnd + 10, arrowXEnd + 10};
//                int[] arrowHeadY = {arrowY, arrowY - 5, arrowY + 5};
//                g2d.fillPolygon(arrowHeadX, arrowHeadY, 3);
//                g2d.setColor(Color.BLACK);
//            }
//
//            // Dibujar estados finales con doble círculo
//            if (state.isFinal()) {
//                g2d.drawOval(x + 3, y + 3, radius * 2 - 6, radius * 2 - 6);
//            }
//
//            statePositions.put(state, new Point(x + radius, y + radius));  // Guardar la posición
//            i++;
//        }
//
//        // Dibujar las transiciones
//        for (Transition transition : automaton.getTransitions()) {
//            Point origin = statePositions.get(transition.getOrigin());
//            Point destination = statePositions.get(transition.getDestination());
//
//            // Crear una curva entre el estado origen y destino
//            QuadCurve2D curve = new QuadCurve2D.Float(
//                    origin.x, origin.y,
//                    (origin.x + destination.x) / 2, (origin.y + destination.y) / 2 - 50,  // Ajustar la curvatura
//                    destination.x, destination.y
//            );
//            g2d.draw(curve);
//
//            // Dibujar el símbolo en el punto medio de la curva
//            int midX = (origin.x + destination.x) / 2;
//            int midY = (origin.y + destination.y) / 2;
//            g2d.drawString(String.valueOf(transition.getSymbol()), midX, midY - 15); // Elevar más el texto para evitar superposición
//        }
//    }

    private void drawAutomaton(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dibujar los estados como círculos, dispersándolos en un patrón circular
        int radius = 30;
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int centerX = panelWidth / 2;
        int centerY = panelHeight / 2;
        int circleRadius = 200;  // Radio del círculo en el que se disponen los estados
        int stateCount = automaton.getStates().size();
        double angleStep = 2 * Math.PI / stateCount;  // Ángulo entre cada estado

        Map<State, Point> statePositions = new HashMap<>();
        int i = 0;

        for (State state : automaton.getStates()) {
            // Calcular la posición de cada estado en un círculo
            double angle = i * angleStep;
            int x = (int) (centerX + circleRadius * Math.cos(angle) - radius);
            int y = (int) (centerY + circleRadius * Math.sin(angle) - radius);
            g2d.drawOval(x, y, radius * 2, radius * 2);
            g2d.drawString(state.getName(), x + radius - 10, y + radius);

            // Marcar el estado inicial con una flecha visible
            if (state.equals(automaton.getInitialState())) {
                g2d.setColor(Color.RED);

                // Calcular la posición de la flecha (fuera del círculo)
                int arrowXStart = x - 70;  // Mantener el inicio de la línea más a la izquierda
                int arrowXEnd = x - radius;  // La flecha termina en el borde del círculo
                int arrowY = y + radius;  // Alineada verticalmente con el centro del estado

                // Dibujar la línea de la flecha
                g2d.drawLine(arrowXStart, arrowY, arrowXEnd, arrowY);

                // Dibujar la cabeza de la flecha apuntando hacia la derecha
                int[] arrowHeadX = {arrowXEnd, arrowXEnd - 10, arrowXEnd - 10}; // Cambiamos el sentido de la punta para que apunte a la derecha
                int[] arrowHeadY = {arrowY, arrowY - 5, arrowY + 5};  // Mantener las coordenadas de la cabeza de la flecha

                g2d.fillPolygon(arrowHeadX, arrowHeadY, 3);  // Dibujar la cabeza de la flecha

                g2d.setColor(Color.BLACK);  // Restaurar el color original
            }

            // Dibujar estados finales con doble círculo
            if (state.isFinal()) {
                g2d.drawOval(x + 3, y + 3, radius * 2 - 6, radius * 2 - 6);
            }

            statePositions.put(state, new Point(x + radius, y + radius));  // Guardar la posición
            i++;
        }

        // Dibujar las transiciones
        for (Transition transition : automaton.getTransitions()) {
            Point origin = statePositions.get(transition.getOrigin());
            Point destination = statePositions.get(transition.getDestination());

            // Calcular los puntos en los bordes de los círculos
            Point originEdge = getEdgePoint(origin, destination, radius);
            Point destinationEdge = getEdgePoint(destination, origin, radius);

            // Crear una curva entre el borde del estado origen y destino
            QuadCurve2D curve = new QuadCurve2D.Float(
                    originEdge.x, originEdge.y,
                    (originEdge.x + destinationEdge.x) / 2, (originEdge.y + destinationEdge.y) / 2 - 50,  // Ajustar la curvatura
                    destinationEdge.x, destinationEdge.y
            );
            g2d.draw(curve);

            // Dibujar el símbolo en el punto medio de la curva
            int midX = (originEdge.x + destinationEdge.x) / 2;
            int midY = (originEdge.y + destinationEdge.y) / 2;
            g2d.drawString(String.valueOf(transition.getSymbol()), midX, midY - 15); // Elevar más el texto para evitar superposición
        }
    }

    /**
     * Método auxiliar para calcular el punto en el borde del círculo.
     */
    private Point getEdgePoint(Point origin, Point destination, int radius) {
        double dx = destination.x - origin.x;
        double dy = destination.y - origin.y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        double ratio = radius / distance;
        int edgeX = (int) (origin.x + dx * ratio);
        int edgeY = (int) (origin.y + dy * ratio);
        return new Point(edgeX, edgeY);
    }


    private void drawValidationResult(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(isValidString ? Color.GREEN : Color.RED);
        g2d.drawString("String \"" + inputString + "\" is " + (isValidString ? "VALID" : "INVALID"), 20, 20);
    }
}
