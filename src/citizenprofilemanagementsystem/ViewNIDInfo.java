package citizenprofilemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.*;

public class ViewNIDInfo extends JFrame implements ActionListener{

    JTable table;
    Choice NID;
    JButton search, print, update, back;
    
    ViewNIDInfo() {
        
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        JLabel searchlbl = new JLabel("Search by NID");
        searchlbl.setBounds(20, 20, 150, 20);
        add(searchlbl);
        
        NID = new Choice();
        NID.setBounds(180, 20, 150, 20);
        add(NID);
        
        try {
            ConnectMSSQL c = new ConnectMSSQL();
            ResultSet rs = c.s.executeQuery("select * from nationalIdCard");
            while(rs.next()) {
                NID.add(rs.getString("NID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        table = new JTable();
        
        try {
            ConnectMSSQL c = new ConnectMSSQL();
            ResultSet rs = c.s.executeQuery("select NID,FathersName,MothersName,CityOfBirth,PresentAddress from nationalIdCard");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(0, 100, 900, 600);
        add(jsp);
        
        search = new JButton("Search");
        search.setBounds(20, 70, 80, 20);
        search.addActionListener(this);
        add(search);
        
        print = new JButton("Print");
        print.setBounds(120, 70, 80, 20);
        print.addActionListener(this);
        add(print);
        
        update = new JButton("Update");
        update.setBounds(220, 70, 80, 20);
        update.addActionListener(this);
        add(update);
        
        back = new JButton("Back");
        back.setBounds(320, 70, 80, 20);
        back.addActionListener(this);
        add(back);
        
        setSize(900, 700);
        setLocation(300, 100);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == search) {
            String query = "select NID,FathersName,MothersName,CityOfBirth,PresentAddress from nationalIdCard where NID = '"+NID.getSelectedItem()+"'";
            try {
                ConnectMSSQL c = new ConnectMSSQL();
                ResultSet rs = c.s.executeQuery(query);
                table.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == print) {
            try {
                table.print();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == update) {
            setVisible(false);
            new UpdateNIDInfo(NID.getSelectedItem());
        } else {
            setVisible(false);
            new ViewProfile();
        }
    }

    public static void main(String[] args) {
        new ViewNIDInfo();
    }
}
