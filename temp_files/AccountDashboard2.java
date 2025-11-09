import bankmanagement_system.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AccountDashboard2 extends JFrame implements ActionListener {
    Database db = Database.getInstance();
    Account ac;

    JLabel welcomeText = new JLabel();
    JTextField accountNoT = new JTextField("", 15);
    JTextField amountT = new JTextField("", 10);

    JButton transferB = new JButton("Transfer Money");
    JButton withdrawB = new JButton("Withdraw Money");
    JButton depositB = new JButton("Deposit Money");
    JButton balanceB = new JButton("Balance Check");
    JButton payBillB = new JButton("Pay Loan EMI");
    JButton transactionHistoryB = new JButton("Transaction History");
    JButton logoutB = new JButton("Log Out");

    JButton transferSB = new JButton("Transfer");
    JButton withdrawalSB = new JButton("Withdraw");
    JButton depositSB = new JButton("Deposit");
    JButton payBillSB = new JButton("Pay");

    JPanel center = new JPanel();

    public AccountDashboard2(Account ac) {
        this.ac = ac;
        this.setTitle("Dashboard");
        this.setSize(700, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // Top panel
        JPanel top = new JPanel();
        welcomeText.setText("Welcome, " + ac.user.firstName + " " + ac.user.lastName);
        top.add(welcomeText);
        this.add(top, BorderLayout.NORTH);

        // Left panel
        JPanel left = new JPanel(new GridLayout(6, 1, 5, 10));
        left.add(transferB);
        left.add(withdrawB);
        left.add(depositB);
        left.add(balanceB);
        left.add(payBillB);
        left.add(transactionHistoryB);
        this.add(left, BorderLayout.WEST);

        // Center panel
        center.setBorder(new EmptyBorder(20, 20, 20, 20));
        this.add(center, BorderLayout.CENTER);

        // Bottom panel
        JPanel bottom = new JPanel();
        bottom.add(logoutB);
        this.add(bottom, BorderLayout.SOUTH);

        // Add action listeners
        transferB.addActionListener(this);
        withdrawB.addActionListener(this);
        depositB.addActionListener(this);
        balanceB.addActionListener(this);
        payBillB.addActionListener(this);
        transactionHistoryB.addActionListener(this);
        logoutB.addActionListener(this);
        transferSB.addActionListener(this);
        withdrawalSB.addActionListener(this);
        depositSB.addActionListener(this);
        payBillSB.addActionListener(this);

        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == transferB) generateTransferPanel();
        else if (e.getSource() == withdrawB) generateWithdrawalPanel();
        else if (e.getSource() == depositB) generateDepositPanel();
        else if (e.getSource() == balanceB) generateBalancePanel();
        else if (e.getSource() == payBillB) generatePayBillPanel();
        else if (e.getSource() == transactionHistoryB) generateTransactionHistoryPanel();
        else if (e.getSource() == logoutB) {
            db.saveData();
            this.dispose();
            new MainApp();
        } else if (e.getSource() == transferSB) transfer();
        else if (e.getSource() == withdrawalSB) withdraw();
        else if (e.getSource() == depositSB) deposit();
        else if (e.getSource() == payBillSB) payBill();
    }

    void generateTransferPanel() {
        clearPanel();
        center.add(new JLabel("Enter Account:"));
        center.add(accountNoT);
        center.add(new JLabel("Enter Amount:"));
        center.add(amountT);
        center.add(transferSB);
        revalidate();
        repaint();
    }

    void generateWithdrawalPanel() {
        clearPanel();
        center.add(new JLabel("Enter Amount:"));
        center.add(amountT);
        center.add(withdrawalSB);
        revalidate();
        repaint();
    }

    void generateDepositPanel() {
        clearPanel();
        center.add(new JLabel("Enter Amount:"));
        center.add(amountT);
        center.add(depositSB);
        revalidate();
        repaint();
    }

    void generateBalancePanel() {
        clearPanel();
        DecimalFormat df = new DecimalFormat("0.00");
        center.add(new JLabel("Current Balance: ₹" + df.format(ac.getBalance())));
        revalidate();
        repaint();
    }

    void generatePayBillPanel() {
        clearPanel();
        center.add(new JLabel("Enter Loan Number:"));
        center.add(accountNoT);
        center.add(new JLabel("Enter Amount:"));
        center.add(amountT);
        center.add(payBillSB);
        revalidate();
        repaint();
    }

    void generateTransactionHistoryPanel() {
        clearPanel();
        List<String> history = ac.getTransactionHistory();
        if (history.isEmpty()) {
            center.add(new JLabel("No transactions available."));
        } else {
            for (String record : history) {
                center.add(new JLabel(record));
            }
        }
        revalidate();
        repaint();
    }

    void clearPanel() {
        center.removeAll();
        center.setLayout(new GridLayout(0, 1, 5, 10));
        accountNoT.setText("");
        amountT.setText("");
    }

    void transfer() {
        try {
            double amount = Double.parseDouble(amountT.getText());
            Account otherAccount = db.getAccount(accountNoT.getText());
            if (otherAccount != null) {
                if (ac.transferMoney(otherAccount, amount)) {
                    JOptionPane.showMessageDialog(this, "Transfer successful!");
                } else {
                    JOptionPane.showMessageDialog(this, "Insufficient balance!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Account not found!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter a valid amount.");
        }
    }

    void withdraw() {
        try {
            double amount = Double.parseDouble(amountT.getText());
            int result = ac.withdrawMoney(amount);
            if (result == 0) {
                JOptionPane.showMessageDialog(this, "Withdrawal successful!");
            } else if (result == Account.INSUFFICIENT_BALANCE) {
                JOptionPane.showMessageDialog(this, "Insufficient balance!");
            } else if (result == Account.WITHDRAWAL_LIMIT_UNDER) {
                JOptionPane.showMessageDialog(this, "Amount below withdrawal limit!");
            } else if (result == Account.WITHDRAWAL_LIMIT_OVER) {
                JOptionPane.showMessageDialog(this, "Amount exceeds withdrawal limit!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter a valid amount.");
        }
    }

    void deposit() {
        try {
            double amount = Double.parseDouble(amountT.getText());
            ac.depositMoney(amount);
            JOptionPane.showMessageDialog(this, "Deposit successful!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter a valid amount.");
        }
    }

    void payBill() {
        try {
            double amount = Double.parseDouble(amountT.getText());
            if (ac.payBill(amount)) {
                JOptionPane.showMessageDialog(this, "Loan EMI paid successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Insufficient balance!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter a valid amount.");
        }
    }

    public static void main(String[] args) {
        new MainApp();
    }
}


class MainApp extends JFrame implements ActionListener {
    Database db = Database.getInstance();

    JTextField accountNoT = new JTextField("", 15);
    JPasswordField passwordT = new JPasswordField("", 15);
    JTextField firstNameT = new JTextField("", 15);
    JTextField lastNameT = new JTextField("", 15);
    JTextField initialBalanceT = new JTextField("", 15);
    JPasswordField newPasswordT = new JPasswordField("", 15);

    JButton loginB = new JButton("Login");
    JButton createAccountB = new JButton("Create Account");

    JPanel center = new JPanel();

    public MainApp() {
        this.setTitle("Banking Application");
        this.setSize(400, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // Center panel
        center.setLayout(new GridLayout(0, 1, 5, 10));
        center.setBorder(new EmptyBorder(20, 20, 20, 20));

        center.add(new JLabel("Account Number:"));
        center.add(accountNoT);
        center.add(new JLabel("Password:"));
        center.add(passwordT);
        center.add(loginB);

        center.add(new JLabel("New Account"));
        center.add(new JLabel("First Name:"));
        center.add(firstNameT);
        center.add(new JLabel("Last Name:"));
        center.add(lastNameT);
        center.add(new JLabel("Initial Balance:"));
        center.add(initialBalanceT);
        center.add(new JLabel("Password:"));
        center.add(newPasswordT);
        center.add(createAccountB);

        this.add(center, BorderLayout.CENTER);

        // Add action listeners
        loginB.addActionListener(this);
        createAccountB.addActionListener(this);

        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginB) {
            String accountNo = accountNoT.getText();
            String password = new String(passwordT.getPassword());
            Account account = db.getAccount(accountNo);
            if (account != null && account.checkPassword(password)) {
                this.dispose();
                new AccountDashboard2(account);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid account number or password!");
            }
        } else if (e.getSource() == createAccountB) {
            try {
                String firstName = firstNameT.getText();
                String lastName = lastNameT.getText();
                double initialBalance = Double.parseDouble(initialBalanceT.getText());
                String password = new String(newPasswordT.getPassword());
                String accountNo = "AC" + (db.getAllAccounts().size() + 1);
                User newUser = new User(firstName, lastName);
                Account newAccount = new Account(newUser, initialBalance, accountNo, password);
                db.addAccount(newAccount);
                JOptionPane.showMessageDialog(this, "Account created successfully! Your account number is: " + accountNo);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter a valid initial balance.");
            }
        }
    }
}
