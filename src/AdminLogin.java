import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

class AdminLogin extends JFrame implements ActionListener {

    JFrame frame = new JFrame();

    JPasswordField password = new JPasswordField(15); // Increased the width
    JTextField username = new JTextField(15); // Increased the width
    JButton myButton = new JButton("Login");

    JPanel panel = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    JPanel panel4 = new JPanel();

    AdminLogin() {
        setTitle("Login_Page");
        myButton.addActionListener(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new GridLayout(1, 1));

        panel.setLayout(new BorderLayout());
        panel.add(new JLabel(), BorderLayout.NORTH);
        panel.add(panel3, BorderLayout.CENTER);
        panel.add(new JLabel(), BorderLayout.SOUTH);

        panel2.setLayout(new GridLayout(2, 2));
        panel2.add(new JLabel("Admin Username: "));
        
        // Set the number of columns for the username field to increase its width
        username.setColumns(15);
        panel2.add(username);

        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        
        // Set the number of columns for the password field to increase its width
        password.setColumns(15);
        panel2.add(passwordLabel);
        panel2.add(password);

        panel3.setLayout(new GridBagLayout()); // Use GridBagLayout for better control
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Add some padding

        panel3.add(panel2, gbc);

        panel4.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel4.add(myButton);

        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10); // Resetting inset for the button
        panel3.add(panel4, gbc);

        frame.add(panel);
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == myButton && isFormFilled()) {
            frame.dispose();
            new LoginWindow(); // You may want to replace this with your actual logic
        } else {
            JOptionPane.showMessageDialog(null, "Please fill up all the fields", "Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    boolean isFormFilled() {
        return !username.getText().isEmpty() && !password.getText().isEmpty();
    }
}
