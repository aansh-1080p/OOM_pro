// import java.awt.*;
// import java.awt.event.*;
// import java.text.DecimalFormat;
// import java.util.ArrayList;
// import java.util.List;
// import javax.swing.*;
// import javax.swing.border.EmptyBorder;

// class User {
//     String firstName;
//     String lastName;

//     User(String firstName, String lastName) {
//         this.firstName = firstName;
//         this.lastName = lastName;
//     }
// }

// class Account {
//     public static final int INSUFFICIENT_BALANCE = 1;
//     public static final int WITHDRAWAL_LIMIT_UNDER = 2;
//     public static final int WITHDRAWAL_LIMIT_OVER = 3;

//     double balance;
//     double minWithdrawal = 100.0;
//     double maxWithdrawal = 10000.0;
//     User user;
//     private String password;
//     private List<String> transactionHistory = new ArrayList<>();
//     private String accountNumber;
//     private double loanLimit = 50000.0; // Maximum loan limit

//     Account(User user, double initialBalance, String accountNumber, String password) {
//         this.user = user;
//         this.balance = initialBalance;
//         this.accountNumber = accountNumber;
//         this.password = password;
//     }

//     public String getAccountNumber() {
//         return accountNumber;
//     }

//     public boolean checkPassword(String inputPassword) {
//         return this.password.equals(inputPassword);
//     }

//     public double getBalance() {
//         return balance;
//     }

//     public boolean payBill(double amount) {
//         if (balance >= amount) {
//             balance -= amount;
//             addTransaction("Paid loan EMI: ₹" + amount);
//             return true;
//         }
//         return false;
//     }

//     public boolean transferMoney(Account otherAccount, double amount) {
//         if (balance >= amount) {
//             balance -= amount;
//             otherAccount.depositMoney(amount);
//             addTransaction("Transferred ₹" + amount + " to account: " + otherAccount.user.firstName);
//             return true;
//         }
//         return false;
//     }

//     public void depositMoney(double amount) {
//         balance += amount;
//         addTransaction("Deposited: ₹" + amount);
//     }

//     public int withdrawMoney(double amount) {
//         if (amount < minWithdrawal) return WITHDRAWAL_LIMIT_UNDER;
//         if (amount > maxWithdrawal) return WITHDRAWAL_LIMIT_OVER;
//         if (balance >= amount) {
//             balance -= amount;
//             addTransaction("Withdrew: ₹" + amount);
//             return 0;
//         }
//         return INSUFFICIENT_BALANCE;
//     }

//     public List<String> getTransactionHistory() {
//         return transactionHistory;
//     }

//     private void addTransaction(String transaction) {
//         transactionHistory.add(transaction);
//     }

//     public boolean takeLoan(double amount) {
//         if (amount > 0 && amount <= loanLimit) {
//             balance += amount;
//             addTransaction("Took a loan of ₹" + amount);
//             return true;
//         }
//         return false;
//     }
// }

// class Database {
//     private static Database instance = new Database();
//     private List<Account> accounts = new ArrayList<>();

//     private Database() {}

//     public static Database getInstance() {
//         return instance;
//     }

//     public void saveData() {
//         // Save accounts to persistent storage (mock implementation)
//     }

//     public Account getAccount(String accountNo) {
//         for (Account account : accounts) {
//             if (account.getAccountNumber().equals(accountNo)) {
//                 return account;
//             }
//         }
//         return null;
//     }

//     public void addAccount(Account account) {
//         accounts.add(account);
//     }

//     public List<Account> getAllAccounts() {
//         return accounts;
//     }
// }

// public class AccountDashboard2 extends JFrame implements ActionListener {
//     Database db = Database.getInstance();
//     Account ac;

//     JLabel welcomeText = new JLabel();
//     JTextField accountNoT = new JTextField("", 15);
//     JTextField amountT = new JTextField("", 10);

//     JButton transferB = new JButton("Transfer Money");
//     JButton withdrawB = new JButton("Withdraw Money");
//     JButton depositB = new JButton("Deposit Money");
//     JButton balanceB = new JButton("Balance Check");
//     JButton payBillB = new JButton("Pay Loan EMI");
//     JButton transactionHistoryB = new JButton("Transaction History");
//     JButton takeLoanB = new JButton("Take Loan");
//     JButton logoutB = new JButton("Log Out");

//     JButton transferSB = new JButton("Transfer");
//     JButton withdrawalSB = new JButton("Withdraw");
//     JButton depositSB = new JButton("Deposit");
//     JButton payBillSB = new JButton("Pay");
//     JButton takeLoanSB = new JButton("Apply Loan");

//     JPanel center = new JPanel();

//     public AccountDashboard2(Account ac) {
//         this.ac = ac;
//         this.setTitle("Dashboard");
//         this.setSize(700, 500);
//         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         this.setLayout(new BorderLayout());

//         // Top panel
//         JPanel top = new JPanel();
//         welcomeText.setText("Welcome, " + ac.user.firstName + " " + ac.user.lastName);
//         top.add(welcomeText);
//         this.add(top, BorderLayout.NORTH);

//         // Left panel
//         JPanel left = new JPanel(new GridLayout(7, 1, 5, 10));
//         left.add(transferB);
//         left.add(withdrawB);
//         left.add(depositB);
//         left.add(balanceB);
//         left.add(payBillB);
//         left.add(transactionHistoryB);
//         left.add(takeLoanB);
//         this.add(left, BorderLayout.WEST);

//         // Center panel
//         center.setBorder(new EmptyBorder(20, 20, 20, 20));
//         this.add(center, BorderLayout.CENTER);

//         // Bottom panel
//         JPanel bottom = new JPanel();
//         bottom.add(logoutB);
//         this.add(bottom, BorderLayout.SOUTH);

//         // Add action listeners
//         transferB.addActionListener(this);
//         withdrawB.addActionListener(this);
//         depositB.addActionListener(this);
//         balanceB.addActionListener(this);
//         payBillB.addActionListener(this);
//         transactionHistoryB.addActionListener(this);
//         takeLoanB.addActionListener(this);
//         logoutB.addActionListener(this);
//         transferSB.addActionListener(this);
//         withdrawalSB.addActionListener(this);
//         depositSB.addActionListener(this);
//         payBillSB.addActionListener(this);
//         takeLoanSB.addActionListener(this);

//         this.setVisible(true);
//     }

//     public void actionPerformed(ActionEvent e) {
//         if (e.getSource() == transferB) generateTransferPanel();
//         else if (e.getSource() == withdrawB) generateWithdrawalPanel();
//         else if (e.getSource() == depositB) generateDepositPanel();
//         else if (e.getSource() == balanceB) generateBalancePanel();
//         else if (e.getSource() == payBillB) generatePayBillPanel();
//         else if (e.getSource() == transactionHistoryB) generateTransactionHistoryPanel();
//         else if (e.getSource() == takeLoanB) generateTakeLoanPanel();
//         else if (e.getSource() == logoutB) {
//             db.saveData();
//             this.dispose();
//             new MainApp();
//         } else if (e.getSource() == transferSB) transfer();
//         else if (e.getSource() == withdrawalSB) withdraw();
//         else if (e.getSource() == depositSB) deposit();
//         else if (e.getSource() == payBillSB) payBill();
//         else if (e.getSource() == takeLoanSB) takeLoan();
//     }

//     void generateTransferPanel() {
//         clearPanel();
//         center.add(new JLabel("Enter Account:"));
//         center.add(accountNoT);
//         center.add(new JLabel("Enter Amount:"));
//         center.add(amountT);
//         center.add(transferSB);
//         revalidate();
//         repaint();
//     }

//     void generateWithdrawalPanel() {
//         clearPanel();
//         center.add(new JLabel("Enter Amount:"));
//         center.add(amountT);
//         center.add(withdrawalSB);
//         revalidate();
//         repaint();
//     }

//     void generateDepositPanel() {
//         clearPanel();
//         center.add(new JLabel("Enter Amount:"));
//         center.add(amountT);
//         center.add(depositSB);
//         revalidate();
//         repaint();
//     }

//     void generateBalancePanel() {
//         clearPanel();
//         DecimalFormat df = new DecimalFormat("0.00");
//         center.add(new JLabel("Current Balance: ₹" + df.format(ac.getBalance())));
//         revalidate();
//         repaint();
//     }

//     void generatePayBillPanel() {
//         clearPanel();
//         center.add(new JLabel("Enter Loan Number:"));
//         center.add(accountNoT);
//         center.add(new JLabel("Enter Amount:"));
//         center.add(amountT);
//         center.add(payBillSB);
//         revalidate();
//         repaint();
//     }

//     void generateTakeLoanPanel() {
//         clearPanel();
//         center.add(new JLabel("Enter Loan Amount:"));
//         center.add(amountT);
//         center.add(takeLoanSB);
//         revalidate();
//         repaint();
//     }

//     void generateTransactionHistoryPanel() {
//         clearPanel();
//         List<String> history = ac.getTransactionHistory();
//         if (history.isEmpty()) {
//             center.add(new JLabel("No transactions available."));
//         } else {
//             for (String record : history) {
//                 center.add(new JLabel(record));
//             }
//         }
//         revalidate();
//         repaint();
//     }

//     void clearPanel() {
//         center.removeAll();
//         center.setLayout(new GridLayout(0, 1, 5, 10));
//         accountNoT.setText("");
//         amountT.setText("");
//     }

//     void transfer() {
//         try {
//             double amount = Double.parseDouble(amountT.getText());
//             Account otherAccount = db.getAccount(accountNoT.getText());
//             if (otherAccount != null) {
//                 if (ac.transferMoney(otherAccount, amount)) {
//                     JOptionPane.showMessageDialog(this, "Transferred ₹" + amount + " successfully!");
//                 } else {
//                     JOptionPane.showMessageDialog(this, "Insufficient balance!");
//                 }
//             } else {
//                 JOptionPane.showMessageDialog(this, "Invalid account number!");
//             }
//         } catch (NumberFormatException ex) {
//             JOptionPane.showMessageDialog(this, "Enter a valid amount.");
//         }
//     }

//     void withdraw() {
//         try {
//             double amount = Double.parseDouble(amountT.getText());
//             int result = ac.withdrawMoney(amount);
//             if (result == 0) {
//                 JOptionPane.showMessageDialog(this, "Withdrawal of ₹" + amount + " successful!");
//             } else if (result == Account.INSUFFICIENT_BALANCE) {
//                 JOptionPane.showMessageDialog(this, "Insufficient balance!");
//             } else if (result == Account.WITHDRAWAL_LIMIT_UNDER) {
//                 JOptionPane.showMessageDialog(this, "Withdrawal must be above ₹" + ac.minWithdrawal);
//             } else if (result == Account.WITHDRAWAL_LIMIT_OVER) {
//                 JOptionPane.showMessageDialog(this, "Withdrawal must be below ₹" + ac.maxWithdrawal);
//             }
//         } catch (NumberFormatException ex) {
//             JOptionPane.showMessageDialog(this, "Enter a valid amount.");
//         }
//     }

//     void deposit() {
//         try {
//             double amount = Double.parseDouble(amountT.getText());
//             ac.depositMoney(amount);
//             JOptionPane.showMessageDialog(this, "Deposited ₹" + amount + " successfully!");
//         } catch (NumberFormatException ex) {
//             JOptionPane.showMessageDialog(this, "Enter a valid amount.");
//         }
//     }

//     void payBill() {
//         try {
//             double amount = Double.parseDouble(amountT.getText());
//             if (ac.payBill(amount)) {
//                 JOptionPane.showMessageDialog(this, "Loan EMI of ₹" + amount + " paid successfully!");
//             } else {
//                 JOptionPane.showMessageDialog(this, "Insufficient balance!");
//             }
//         } catch (NumberFormatException ex) {
//             JOptionPane.showMessageDialog(this, "Enter a valid amount.");
//         }
//     }

//     void takeLoan() {
//         try {
//             double amount = Double.parseDouble(amountT.getText());
//             if (ac.takeLoan(amount)) {
//                 JOptionPane.showMessageDialog(this, "Loan of ₹" + amount + " successfully added to your account!");
//             } else {
//                 JOptionPane.showMessageDialog(this, "Loan amount exceeds limit or is invalid!");
//             }
//         } catch (NumberFormatException ex) {
//             JOptionPane.showMessageDialog(this, "Enter a valid amount.");
//         }
//     }

// public static void main(String[] args) {
//         new MainApp();
//     }
// }


// class MainApp extends JFrame implements ActionListener {
//     Database db = Database.getInstance();

//     JTextField accountNoT = new JTextField("", 15);
//     JPasswordField passwordT = new JPasswordField("", 15);
//     JTextField firstNameT = new JTextField("", 15);
//     JTextField lastNameT = new JTextField("", 15);
//     JTextField initialBalanceT = new JTextField("", 15);
//     JPasswordField newPasswordT = new JPasswordField("", 15);

//     JButton loginB = new JButton("Login");
//     JButton createAccountB = new JButton("Create Account");

//     JPanel center = new JPanel();

//     public MainApp() {
//         this.setTitle("Banking Application");
//         this.setSize(400, 400);
//         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         this.setLayout(new BorderLayout());

//         // Center panel
//         center.setLayout(new GridLayout(0, 1, 5, 10));
//         center.setBorder(new EmptyBorder(20, 20, 20, 20));

//         center.add(new JLabel("Account Number:"));
//         center.add(accountNoT);
//         center.add(new JLabel("Password:"));
//         center.add(passwordT);
//         center.add(loginB);

//         center.add(new JLabel("New Account"));
//         center.add(new JLabel("First Name:"));
//         center.add(firstNameT);
//         center.add(new JLabel("Last Name:"));
//         center.add(lastNameT);
//         center.add(new JLabel("Initial Balance:"));
//         center.add(initialBalanceT);
//         center.add(new JLabel("Password:"));
//         center.add(newPasswordT);
//         center.add(createAccountB);

//         this.add(center, BorderLayout.CENTER);

//         // Add action listeners
//         loginB.addActionListener(this);
//         createAccountB.addActionListener(this);

//         this.setVisible(true);
//     }

//     public void actionPerformed(ActionEvent e) {
//         if (e.getSource() == loginB) {
//             String accountNo = accountNoT.getText();
//             String password = new String(passwordT.getPassword());
//             Account account = db.getAccount(accountNo);
//             if (account != null && account.checkPassword(password)) {
//                 this.dispose();
//                 new AccountDashboard2(account);
//             } else {
//                 JOptionPane.showMessageDialog(this, "Invalid account number or password!");
//             }
//         } else if (e.getSource() == createAccountB) {
//             try {
//                 String firstName = firstNameT.getText();
//                 String lastName = lastNameT.getText();
//                 double initialBalance = Double.parseDouble(initialBalanceT.getText());
//                 String password = new String(newPasswordT.getPassword());
//                 String accountNo = "AC" + (db.getAllAccounts().size() + 1);
//                 User newUser = new User(firstName, lastName);
//                 Account newAccount = new Account(newUser, initialBalance, accountNo, password);
//                 db.addAccount(newAccount);
//                 JOptionPane.showMessageDialog(this, "Account created successfully! Your account number is: " + accountNo);
//             } catch (NumberFormatException ex) {
//                 JOptionPane.showMessageDialog(this, "Enter a valid initial balance.");
//             }
//         }
//     }
// }
