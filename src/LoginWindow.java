import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.FileNotFoundException;

public class LoginWindow extends JFrame implements ActionListener {
    JPasswordField passF = new JPasswordField("", 20);
    JTextField accountNoT = new JTextField("", 20);
    JButton loginB = new JButton("Login"), signUpB = new JButton("Create a New Account"),
            userDetailsB = new JButton("User Details");

    JPanel jp = new JPanel(new GridBagLayout());
    JPanel jp2 = new JPanel(new GridBagLayout());
    JPanel jp3 = new JPanel(new GridBagLayout());
    Database db = Database.getInstance();

    public LoginWindow() {

        Database.getInstance().loadData();
        db.printAccounts();

        this.setSize(500, 300); // Set a reasonable size, adjust as needed
        this.setLocationRelativeTo(null); // Center the frame on the screen
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        loginB.addActionListener(this);
        signUpB.addActionListener(this);
        userDetailsB.addActionListener(this);

        this.add(jp, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Set insets for spacing

        gbc.gridx = 0;
        gbc.gridy = 0;
        jp.add(new JLabel("Account Number: "), gbc);

        gbc.gridx = 1;
        jp.add(accountNoT, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        jp.add(new JLabel("Password: "), gbc);

        gbc.gridx = 1;
        jp.add(passF, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        jp.add(loginB, gbc);

        gbc.gridy = 3;
        jp.add(signUpB, gbc);

        gbc.gridy = 4;
        jp.add(userDetailsB, gbc);

        this.revalidate();
    }

    public static void main(String args[]) {

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Login")) {
            Account ac;
            if ((ac = db.getAccount(accountNoT.getText(), passF.getText())) != null) {
                this.dispose();
                new AccountDashboard(ac);
            } else {
                JOptionPane.showMessageDialog(this, "Account Number & Password didn't Match!", "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getActionCommand().equals("Create a New Account")) {
            this.dispose();
            new SignUpWindow();
        } else if (e.getActionCommand().equals("User Details")) {
            this.dispose();
            try {
                new UserDetails();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }
}
