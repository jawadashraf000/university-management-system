package gui;

import controller.AuthenticationController;
import persistence.AppState;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    public LoginFrame(AppState state) {

        AuthenticationController auth = new AuthenticationController(state);
        setTitle("University Management System");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel background = new JPanel(new GridBagLayout());
        background.setBackground(new Color(0, 0, 128));

        JPanel loginCard = new JPanel();
        loginCard.setPreferredSize(new Dimension(400, 300));
        loginCard.setBackground(Color.WHITE);
        loginCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        loginCard.setLayout(new GridLayout(7, 1, 10, 10));

        JLabel title = new JLabel("University Login", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 25));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(e -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());

            if (auth.login(user, pass)) {
                dispose();
                new MainDashboard(state, auth);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password");
            }
        });

        JLabel defaultTitle = new JLabel("Default : admin / admin", SwingConstants.CENTER);
        defaultTitle.setFont(new Font("Arial", Font.PLAIN, 13));

        JLabel error = new JLabel("", SwingConstants.CENTER);
        error.setForeground(Color.RED);

        loginCard.add(title);
        loginCard.add(new JLabel("Username"));
        loginCard.add(usernameField);
        loginCard.add(new JLabel("Password"));
        loginCard.add(passwordField);
        loginCard.add(loginBtn);
        loginCard.add(defaultTitle);

        background.add(loginCard);

        add(background);
        setVisible(true);
    }
}