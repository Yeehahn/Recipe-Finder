import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.event.WindowListener;

public class Window {
    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel();
    private JButton button = new JButton("Search");
    private JButton button2 = new JButton("Reset");
    private JTextField textField = new JTextField(20);
    private JTextArea textArea = new JTextArea(5, 20);
    private JTextArea instructionField = new JTextArea(5, 20);
    private JPanel buttonPanel = new JPanel(new GridLayout(2, 1));
    private int width, height;
    boolean open = false;
    boolean buttonPressed = false;
    boolean resetPressed = false;
    GroupLayout layout;

    private String text = "Enter each ingredient you want, pressing enter after each one. Press the search button when you are done to find your recipes.";

    public Window(int width, int height) {
        this.width = width;
        this.height = height;
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        instructionField.setEditable(false);
        instructionField.setLineWrap(true);
        instructionField.setWrapStyleWord(true);
        instructionField.setText(text);
        textField.addKeyListener(new KeyListen());
        button.addActionListener(new Action());
        button2.addActionListener(new ResetAction());
        buttonPanel.add(button);
        buttonPanel.add(button2);
    }

    // Window Methods

    public void openWindow() {
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowListen());

        createLayout();

        frame.add(panel);
        frame.setVisible(true);
        open = true;
    }

    public GroupLayout createLayout() {
        layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(textArea)
                                .addComponent(instructionField))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(textField)
                                .addComponent(buttonPanel)));
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(textField)
                                .addComponent(instructionField))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(textArea)
                                .addComponent(buttonPanel)));
        return layout;
    }

    public JTextField addText(int width) {
        textField = new JTextField(width);
        return textField;
    }

    public ArrayList<String> retrieveArrayListFromTextArea() {
        ArrayList<String> ingredientList = new ArrayList<String>();
        for (String ingredient : textArea.getText().split(", ")) {
            ingredientList.add(ingredient);
        }
        frame.pack();
        return ingredientList;
    }

    public void printArrayListToTextArea(ArrayList<String> list) {
        for (int i = 0; i < 5; i += 2) {
            textArea.append("\n" + list.get(i) + ": " + list.get(i + 1));
        }
    }

    public void resetAll() {
        textArea.setText("");
        textField.setText("");
    }

    public boolean isOpen() {
        if (!frame.isVisible()) {
            open = false;
        }
        return open;
    }

    public void showUserInput(String input) {
        textArea.setText(input);
    }

    class Action implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            buttonPressed = true;
        }
    }

    class ResetAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            resetPressed = true;
        }
    }

    class KeyListen implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_ENTER) {
                textArea.append(textField.getText() + ", ");
                textField.setText("");
                frame.pack();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

    }

    class WindowListen implements WindowListener {

        @Override
        public void windowOpened(WindowEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void windowClosing(WindowEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void windowClosed(WindowEvent e) {
            open = false;
        }

        @Override
        public void windowIconified(WindowEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void windowDeiconified(WindowEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void windowActivated(WindowEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void windowDeactivated(WindowEvent e) {
            // TODO Auto-generated method stub

        }

    }
}