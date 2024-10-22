import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.QuadCurve2D;
import java.util.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import javax.swing.table.DefaultTableModel;

// Clase principal de la interfaz gráfica
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.QuadCurve2D;
import java.util.*;
import javax.swing.table.DefaultTableModel;

class AutomatonGUI extends JFrame {
    private JTextField alphabetField, statesField, initialStateField, finalStatesField, inputStringField;
    private JTextArea outputArea;
    private Automaton automaton;
    private DrawPanel drawPanel;
    private JTable transitionsTable;

    public AutomatonGUI() {
        setTitle("Automaton Validator");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Colores y fuente personalizados
        Color backgroundColor = new Color(245, 245, 245);
        Color buttonColor = new Color(100, 149, 237);
        Color panelColor = new Color(230, 230, 250);
        Font font = new Font("SansSerif", Font.PLAIN, 14);
        Font buttonFont = new Font("SansSerif", Font.BOLD, 14);

        // Panel superior para título
        JLabel titleLabel = new JLabel("Automaton Validator", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        titleLabel.setForeground(new Color(60, 63, 65));
        add(titleLabel, BorderLayout.NORTH);

        // Panel izquierdo para entrada de datos
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(panelColor);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        alphabetField = new JTextField();
        addLabeledField("Alphabet (e.g., ab):", alphabetField, inputPanel, gbc, 0);

        statesField = new JTextField();
        addLabeledField("States (e.g., q0 q1 q2):", statesField, inputPanel, gbc, 1);

        initialStateField = new JTextField();
        addLabeledField("Initial State:", initialStateField, inputPanel, gbc, 2);

        finalStatesField = new JTextField();
        addLabeledField("Final States (e.g., q1 q2):", finalStatesField, inputPanel, gbc, 3);

// Aquí movemos el campo inputStringField a la fila 4.
        inputStringField = new JTextField();
        addLabeledField("Validate String:", inputStringField, inputPanel, gbc, 4);

// Transitions table and buttons go below this
        String[] columnNames = {"Origin", "Symbol", "Destination"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        transitionsTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(transitionsTable);
        scrollPane.setPreferredSize(new Dimension(300, 100));
        gbc.gridx = 0;
        gbc.gridy = 5;  // Mueve la tabla de transiciones a la fila 5 para que siga debajo
        gbc.gridwidth = 2;
        inputPanel.add(new JLabel("Transitions:"), gbc);
        gbc.gridy++;
        inputPanel.add(scrollPane, gbc);

        JButton addRowButton = new JButton("Add Transition");
        addRowButton.setBackground(buttonColor);
        addRowButton.setFont(buttonFont);
        addRowButton.setForeground(Color.WHITE);
        addRowButton.setFocusPainted(false);
        gbc.gridy++;
        inputPanel.add(addRowButton, gbc);

        addRowButton.addActionListener(e -> model.addRow(new Object[]{"", "", ""}));

        JButton createAutomatonButton = new JButton("Create Automaton");
        createAutomatonButton.setBackground(buttonColor);
        createAutomatonButton.setFont(buttonFont);
        createAutomatonButton.setForeground(Color.WHITE);
        createAutomatonButton.setFocusPainted(false);
        gbc.gridy++;
        inputPanel.add(createAutomatonButton, gbc);

//        inputStringField = new JTextField();
//        addLabeledField("Validate String:", inputStringField, inputPanel, gbc, 6);

        add(inputPanel, BorderLayout.WEST);

        // Panel central para dibujo del autómata
        drawPanel = new DrawPanel();
        drawPanel.setBackground(Color.WHITE);
        add(drawPanel, BorderLayout.CENTER);

        // Panel inferior para resultados y botones adicionales
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setBackground(backgroundColor);

        JButton validateStringButton = new JButton("Validate String");
        customizeButton(validateStringButton, buttonColor, buttonFont);
        bottomPanel.add(validateStringButton);

        JButton checkDeterministicButton = new JButton("Check Determinism");
        customizeButton(checkDeterministicButton, buttonColor, buttonFont);
        bottomPanel.add(checkDeterministicButton);

        outputArea = new JTextArea(3, 40);
        outputArea.setFont(font);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setEditable(false);
        JScrollPane outputScrollPane = new JScrollPane(outputArea);
        outputScrollPane.setPreferredSize(new Dimension(800, 80));
        bottomPanel.add(outputScrollPane);

        add(bottomPanel, BorderLayout.SOUTH);

        createAutomatonButton.addActionListener(e -> {
            createAutomaton();
            drawPanel.repaint();
        });

        validateStringButton.addActionListener(e -> validateString());
        checkDeterministicButton.addActionListener(e -> checkDeterministic());

        setVisible(true);
    }

    private void addLabeledField(String labelText, JTextField textField, JPanel panel, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(new JLabel(labelText), gbc);
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(textField, gbc);
    }

    private void customizeButton(JButton button, Color bgColor, Font font) {
        button.setBackground(bgColor);
        button.setFont(font);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
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

        List<Transition> transitions = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) transitionsTable.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            String originName = (String) model.getValueAt(i, 0);
            String symbol = (String) model.getValueAt(i, 1);
            String destinationName = (String) model.getValueAt(i, 2);

            if (symbol.isEmpty()) {
                symbol = "ε";
            }

            State origin = states.stream().filter(e -> e.getName().equals(originName)).findFirst().orElse(null);
            State destination = states.stream().filter(e -> e.getName().equals(destinationName)).findFirst().orElse(null);

            if (origin != null && destination != null && symbol.length() == 1) {
                transitions.add(new Transition(origin, symbol.charAt(0), destination));
            } else {
                outputArea.append("Invalid transition: " + originName + " " + symbol + " " + destinationName + "\n");
            }
        }

        automaton = new Automaton(alphabet, states, initialState, finalStates, transitions);
        drawPanel.setAutomaton(automaton);
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
            String result = "The automaton is " + (isDeterministic ? "Deterministic (AFD)" : "Non-Deterministic (AFN)") + "\n";
            JOptionPane.showMessageDialog(this, result);
        } else {
            JOptionPane.showMessageDialog(this, "Automaton not created.\n");
        }
    }

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

    private void drawAutomaton(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int radius = 30; // Radio de los círculos que representan los estados
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int centerX = panelWidth / 2;
        int centerY = panelHeight / 2;
        int circleRadius = 200; // Radio del círculo en el que se distribuyen los estados
        int stateCount = automaton.getStates().size();
        double angleStep = 2 * Math.PI / stateCount;

        Map<State, Point> statePositions = new HashMap<>();
        Map<State, Integer> loopCounts = new HashMap<>(); // Para contar auto-transiciones

        // Dibujar los estados
        for (int i = 0; i < stateCount; i++) {
            State state = automaton.getStates().toArray(new State[0])[i];
            double angle = i * angleStep;
            int x = (int) (centerX + circleRadius * Math.cos(angle) - radius);
            int y = (int) (centerY + circleRadius * Math.sin(angle) - radius);
            g2d.drawOval(x, y, radius * 2, radius * 2);
            g2d.drawString(state.getName(), x + radius - 10, y + radius);

            // Flecha de estado inicial
            if (state.equals(automaton.getInitialState())) {
                g2d.setColor(Color.RED);
                int arrowXStart = x - 70;
                int arrowXEnd = x - radius;
                int arrowY = y + radius;
                g2d.drawLine(arrowXStart, arrowY, arrowXEnd, arrowY);
                int[] arrowHeadX = {arrowXEnd, arrowXEnd - 10, arrowXEnd - 10};
                int[] arrowHeadY = {arrowY, arrowY - 5, arrowY + 5};
                g2d.fillPolygon(arrowHeadX, arrowHeadY, 3);
                g2d.setColor(Color.BLACK);
            }

            // Doble círculo para estados finales
            if (state.isFinal()) {
                g2d.drawOval(x + 3, y + 3, radius * 2 - 6, radius * 2 - 6);
            }

            statePositions.put(state, new Point(x + radius, y + radius));
            loopCounts.put(state, 0); // Inicializar contador de auto-transiciones
        }

        for (Transition transition : automaton.getTransitions()) {
            Point origin = statePositions.get(transition.getOrigin());
            Point destination = statePositions.get(transition.getDestination());

            if (origin.equals(destination)) {

                if (transition.getSymbol() == 'ε') {
                    g2d.setColor(Color.GRAY); // Color diferente para transiciones epsilon
                } else {
                    g2d.setColor(Color.BLUE);
                }

                // Auto-transición: dibujar un bucle alrededor del estado
                int loopCount = loopCounts.get(transition.getOrigin()); // Obtener el contador
                loopCounts.put(transition.getOrigin(), loopCount + 1); // Incrementar el contador

                int loopRadius = 40 + loopCount * 15;  // Ajusta el tamaño del bucle en función de loopCount
                double angle = Math.toRadians(45 + loopCount * 45); // Ajustar el ángulo para distribuir bucles

                // Calcular las coordenadas en el borde del estado
                int stateRadius = 30;  // El radio del estado (el tamaño del círculo)
                int arcX = (int) (origin.x + Math.cos(angle) * (stateRadius + loopRadius));
                int arcY = (int) (origin.y + Math.sin(angle) * (stateRadius + loopRadius));

                // Dibujar el arco de la auto-transición desde el borde del estado
                g2d.drawArc(arcX - loopRadius, arcY - loopRadius, 2 * loopRadius, 2 * loopRadius, 0, 360);

                // Dibujar el símbolo de la transición cerca del arco
                g2d.drawString(String.valueOf(transition.getSymbol()), arcX, arcY - 10);
            } else {
                // Calcular los puntos de inicio y fin en el borde de los estados para transiciones normales
                double angle = Math.atan2(destination.y - origin.y, destination.x - origin.x);
                int offsetX = (int) (radius * Math.cos(angle));
                int offsetY = (int) (radius * Math.sin(angle));

                Point start = new Point(origin.x + offsetX, origin.y + offsetY);
                Point end = new Point(destination.x - offsetX, destination.y - offsetY);

                if (transition.getSymbol() == 'ε') {
                    g2d.setColor(Color.GRAY); // Color diferente para transiciones epsilon
                } else {
                    g2d.setColor(Color.BLUE);
                }

                // Dibujar la curva entre los estados ajustada a los bordes
                QuadCurve2D curve = new QuadCurve2D.Float(
                        start.x, start.y,
                        (start.x + end.x) / 2, (start.y + end.y) / 2 - 50,  // Punto de control para la curva
                        end.x, end.y
                );
                g2d.draw(curve);

                // Dibujar el símbolo de la transición en la curva
                g2d.drawString(String.valueOf(transition.getSymbol()),
                        (start.x + end.x) / 2,
                        (start.y + end.y) / 2 - 10);

                // Añadir una flecha al final de la transición
                drawArrow(g2d, start, end);
            }
        }

        g2d.setColor(Color.BLACK); // Restablecer el color original para otros elementos
    }

    private void drawArrow(Graphics2D g2d, Point start, Point end) {
        int arrowSize = 10; // Tamaño de la flecha
        double angle = Math.atan2(end.y - start.y, end.x - start.x);

        int xArrow1 = (int) (end.x - arrowSize * Math.cos(angle - Math.PI / 6));
        int yArrow1 = (int) (end.y - arrowSize * Math.sin(angle - Math.PI / 6));
        int xArrow2 = (int) (end.x - arrowSize * Math.cos(angle + Math.PI / 6));
        int yArrow2 = (int) (end.y - arrowSize * Math.sin(angle + Math.PI / 6));

        int[] xPoints = {end.x, xArrow1, xArrow2};
        int[] yPoints = {end.y, yArrow1, yArrow2};

        g2d.fillPolygon(xPoints, yPoints, 3);
    }

    private void drawValidationResult(Graphics g) {
        // Lógica para mostrar el resultado de la validación de la cadena, si es necesario
    }
}
