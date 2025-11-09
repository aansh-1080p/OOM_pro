import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class UserDetails extends JFrame implements ActionListener {

    UserDetails() throws FileNotFoundException {

        this.setSize(1440, 1440);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(8, 6));

        try (BufferedReader reader = new BufferedReader(new FileReader("AccountList.txt"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                this.add(new JLabel(line));
            }
            reader.close();
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JButton myButton1 = new JButton("Back");
        JButton myButton2 = new JButton("Print");
        myButton1.addActionListener(this);

        this.add(myButton1);
        this.add(myButton2);

        this.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            new UserDetails();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getActionCommand().equals("Back")) {
            this.dispose();
            new LoginWindow();
        }

    }
}
