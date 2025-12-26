package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class mini extends JFrame implements ActionListener {

    String pin;
    JButton button;

    mini(String pin){
        this.pin = pin;

        getContentPane().setBackground(new Color(255,204,204));
        setSize(400,600);
        setLocation(20,20);
        setLayout(null);

        JLabel l1 = new JLabel();
        l1.setBounds(20,140,350,200);
        add(l1);

        JLabel l2 = new JLabel("Bank System");
        l2.setFont(new Font("System",Font.BOLD,15));
        l2.setBounds(140,20,200,40);
        add(l2);

        JLabel l3 = new JLabel();
        l3.setBounds(20,80,300,20);
        add(l3);

        JLabel l4 = new JLabel();
        l4.setBounds(20,500,300,20);
        add(l4);

        // ---- CARD NUMBER ----
        try{
            Con c = new Con();
            ResultSet rs = c.statement.executeQuery("select * from login where pin='"+pin+"'");
            while (rs.next()){
                l3.setText("Card Number : " +
                        rs.getString("card_number").substring(0,4) +
                        "XXXXXXXXXXXX" +
                        rs.getString("card_number").substring(12)
                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        // ---- MINI STATEMENT + BALANCE ----
        try{
            int balance = 0;
            Con c = new Con();
            ResultSet resultSet = c.statement.executeQuery("select * from bank where pin='"+pin+"'");

            StringBuilder sb = new StringBuilder("<html>");

            while(resultSet.next()){
                sb.append(resultSet.getString("date"))
                        .append("&nbsp;&nbsp;&nbsp;")
                        .append(resultSet.getString("type"))
                        .append("&nbsp;&nbsp;&nbsp;")
                        .append(resultSet.getString("amount"))
                        .append("<br><br>");

                if(resultSet.getString("type").equals("Deposit")){
                    balance += Integer.parseInt(resultSet.getString("amount"));
                }else{
                    balance -= Integer.parseInt(resultSet.getString("amount"));
                }
            }

            sb.append("</html>");

            l1.setText(sb.toString());
            l4.setText("Your Total Balance is Rs " + balance);

        }catch (Exception e){
            e.printStackTrace();
        }

        // ---- EXIT BUTTON ----
        button = new JButton("Exit");
        button.setBounds(150,530,100,30);
        button.addActionListener(this);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        add(button);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button){
            setVisible(false);
        }
    }

    public static void main(String[] args){
        new mini("");
    }
}
