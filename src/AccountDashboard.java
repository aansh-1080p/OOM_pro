import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class AccountDashboard extends JFrame implements ActionListener {
    Database db = Database.getInstance();
    Account ac;

    JLabel welcomeText = new JLabel();
    JTextField accountNoT = new JTextField("", 20);
    JTextField amountT = new JTextField("", 10);

    JButton transferB = new JButton("Transfer Money");
    JButton withdrawB = new JButton("Withdraw Money");
    JButton depositB = new JButton("Deposit Money");
    JButton balanceB = new JButton("Check Balance");
    JButton payBillB = new JButton("Pay Loan EMI");
    JButton transactionHistoryB = new JButton("Transaction History");
    JButton logoutB = new JButton("Log Out");

    JButton transferSB = new JButton("Confirm Transfer");
    JButton withdrawalSB = new JButton("Confirm Withdrawal");
    JButton depositSB = new JButton("Confirm Deposit");
    JButton payBillSB = new JButton("Confirm Payment");

    JPanel left = new JPanel();
    JPanel center = new JPanel();
    JPanel top = new JPanel();
    JPanel bottom = new JPanel();

    public AccountDashboard(Account ac) {
        this.ac = ac;

        // Window settings
        this.setTitle("Dashboard");
        this.setSize(700, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setResizable(false);

        // Save data on window close
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                db.saveData();
                System.exit(0);
            }
        });

        // Top Panel: Welcome message
        top.setBorder(new EmptyBorder(10, 0, 10, 0));
        welcomeText.setText("Welcome, " + ac.user.firstName + " " + ac.user.lastName);
        top.add(welcomeText);

        // Left Panel: Main menu
        left.setLayout(new GridLayout(7, 1, 5, 10));
        addButtonToPanel(left, transferB);
        addButtonToPanel(left, withdrawB);
        addButtonToPanel(left, depositB);
        addButtonToPanel(left, balanceB);
        addButtonToPanel(left, payBillB);
        addButtonToPanel(left, transactionHistoryB);

        // Bottom Panel: Logout
        bottom.setLayout(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(logoutB);

        // Add panels to frame
        this.add(top, BorderLayout.NORTH);
        this.add(left, BorderLayout.WEST);
        this.add(center, BorderLayout.CENTER);
        this.add(bottom, BorderLayout.SOUTH);

        // Action Listeners
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

    private void addButtonToPanel(JPanel panel, JButton button) {
        button.addActionListener(this);
        panel.add(button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "Transfer Money":
                generateTransferPanel();
                break;
            case "Withdraw Money":
                generateWithdrawalPanel();
                break;
            case "Deposit Money":
                generateDepositPanel();
                break;
            case "Check Balance":
                generateBalancePanel();
                break;
            case "Pay Loan EMI":
                generatePayBillPanel();
                break;
            case "Transaction History":
                generateTransactionHistoryPanel();
                break;
            case "Log Out":
                this.dispose();
                new LoginWindow();
                break;
            case "Confirm Transfer":
                transfer();
                break;
            case "Confirm Withdrawal":
                withdraw();
                break;
            case "Confirm Deposit":
                deposit();
                break;
            case "Confirm Payment":
                payBill();
                break;
        }
    }

    private void generateTransferPanel() {
        updateCenterPanel("Enter Account Number:", accountNoT, "Enter Amount:", amountT, transferSB);
    }

    private void generateWithdrawalPanel() {
        updateCenterPanel("Enter Amount:", amountT, "", null, withdrawalSB);
    }

    private void generateDepositPanel() {
        updateCenterPanel("Enter Amount:", amountT, "", null, depositSB);
    }

    private void generateBalancePanel() {
        panelClear();
        DecimalFormat df = new DecimalFormat("0.00");
        center.add(new JLabel("Current Balance:"));
        center.add(new JLabel(df.format(ac.getBalance())));
        this.revalidate();
    }

    private void generatePayBillPanel() {
        updateCenterPanel("Enter Loan Number:", accountNoT, "Enter Amount:", amountT, payBillSB);
    }

    private void generateTransactionHistoryPanel() {
        panelClear();
        center.setLayout(new BorderLayout());

        String history = ac.getTransactionHistory();
        if (history == null || history.isEmpty()) {
            center.add(new JLabel("No transaction history available."), BorderLayout.NORTH);
        } else {
            JTextArea historyArea = new JTextArea(history);
            historyArea.setEditable(false);
            historyArea.setLineWrap(true);
            historyArea.setWrapStyleWord(true);
            JScrollPane scrollPane = new JScrollPane(historyArea);
            scrollPane.setPreferredSize(new Dimension(400, 200));
            center.add(scrollPane, BorderLayout.CENTER);
        }

        this.revalidate();
    }

    private void updateCenterPanel(String label1, JTextField field1, String label2, JTextField field2, JButton button) {
        panelClear();
        center.add(new JLabel(label1));
        center.add(field1);
        if (!label2.isEmpty() && field2 != null) {
            center.add(new JLabel(label2));
            center.add(field2);
        }
        center.add(new JLabel());
        center.add(button);
        this.revalidate();
    }

    private void panelClear() {
        center.removeAll();
        center.setLayout(new GridLayout(3, 2, 5, 10));
        accountNoT.setText("");
        amountT.setText("");
    }

    private void transfer() {
        try {
            double amount = Double.parseDouble(amountT.getText());
            Account target = db.getAccount(accountNoT.getText());
            if (target != null && ac.transferMoney(target, amount)) {
                JOptionPane.showMessageDialog(this, "Transfer successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Transfer failed: Check details or balance.");
            }
            generateTransactionHistoryPanel();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount.");
        }
    }

    private void withdraw() {
        try {
            double amount = Double.parseDouble(amountT.getText());
            if (ac.withdrawMoney(amount) == 0) {
                JOptionPane.showMessageDialog(this, "Withdrawal successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Withdrawal failed: Insufficient balance.");
            }
            generateTransactionHistoryPanel();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount.");
        }
    }

    private void deposit() {
        try {
            double amount = Double.parseDouble(amountT.getText());
            ac.depositMoney(amount);
            JOptionPane.showMessageDialog(this, "Deposit successful!");
            generateTransactionHistoryPanel();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount.");
        }
    }

    private void payBill() {
        try {
            double amount = Double.parseDouble(amountT.getText());
            if (ac.payBill(amount)) {
                JOptionPane.showMessageDialog(this, "Bill payment successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Bill payment failed: Insufficient balance.");
            }
            generateTransactionHistoryPanel();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount.");
        }
    }
}
