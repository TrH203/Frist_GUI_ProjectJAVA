/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project_cuoiky;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author hin03
 */
public class Add_Task extends mainJava{
    JFrame f_add = new JFrame();
    JComboBox time_l;
    JComboBox round_l;
    JComboBox break_combo;
    Object combox_list_round[]={1,2,3,4,5,6,7,8,9,10};
    Object combox_list_break[]={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
    Object combox_time[]={1,2,3,4,5,10,15,20,25,30,35,40,45,50,55,60,65,70,75,80,85,90};
    JTextField task_l = new JTextField(); 
    
    void mainF_add(){             
        f_add.setLayout(new GridLayout(5,2));
        f_add.add(new JLabel("\tTask Name : "));
        //task_l.setSize(100, 30);
        f_add.add(task_l);
        f_add.add(new JLabel("\tTime per Round(minute) : "));
        time_l = new JComboBox(combox_time);
        f_add.add(time_l);
        f_add.add(new JLabel("\tRound : "));
        round_l = new JComboBox(combox_list_round);
        f_add.add(round_l);
        f_add.add(new JLabel("\tBreak time(minute) :"));
        break_combo = new JComboBox(combox_list_break);
        f_add.add(break_combo);     
        JButton b1 = new JButton("Add");
        JButton b2 = new JButton("Cancel"); 
        b1.addActionListener(new myLis_Button());
        b2.addActionListener(new myLis_Button());
        f_add.add(b1);
        f_add.add(b2);
        f_add.setSize(350, 280);
        f_add.setLocationRelativeTo(null);
        f_add.setVisible(true);
    }    
    class myLis_Button implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {        
            if (e.getActionCommand().equals("Add")){
                if (!task_l.getText().equals("")){
                    md.addRow(obj());                    
                    f_add.setVisible(false);
                }else{
                    JOptionPane.showMessageDialog(f_add, "Hãy nhập đầy đủ thông tin");
                }
            }
            else{
                f_add.setVisible(false);
            }
            
            
        }    
        
    }
    Object[] obj(){
        Object[] a = {task_l.getText(),(int) time_l.getSelectedItem(),(int) round_l.getSelectedItem(),(int) break_combo.getSelectedItem()};
        return a;
    }
}
