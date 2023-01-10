package project_cuoiky;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class mainJava extends Thread {
    
    int round_total;
    JPopupMenu pp = new JPopupMenu("Edit");
    int round_current = 1;
    int break_time;
    boolean process = false;
    boolean process_break = false;
    int time;
    JFrame f = new JFrame();
    JLabel l = new JLabel("", SwingConstants.CENTER);
    JPanel ct = new JPanel(new GridLayout(4, 1));
    JLabel round = new JLabel("Round " + round_current + "/1", SwingConstants.CENTER);
    JLabel task_name = new JLabel("Let's prepare for your work", SwingConstants.CENTER);
    JLabel label_time;
    JLabel break_time_l;
    JButton start_button;
    String col[] = {"\tTASK NAME\t", "TIME(minute)", "ROUND", "BREAK TIME"};
    JTable tb;
    JScrollPane sp;
    static DefaultTableModel md;
    int process_index=0;
    public void run() {
        try {            
            mainF();
            while (true) {
                for (int i = 0; i < md.getRowCount(); i++) {
                    process_index = i;
                    if (i == 0) {
                        task_name.setText("Let's prepare now!!!");
                    } else {
                        task_name.setText("Current Task : " + md.getValueAt(i, 0));
                    }
                    round_current = 1;                    
                    process = false;
                    time = (int) md.getValueAt(i, 1) * 60;
                    round_total = (int) md.getValueAt(i, 2);
                    round.setText("Round " + round_current + "/" + round_total);
                    label_time.setText("  Time " + time / 60 + ":00");
                    break_time_l.setText(" Break " + md.getValueAt(i, 3) + ":00");
                    label_time.setForeground(new Color(20,78,81));
                    break_time_l.setForeground(new Color(20,78,81));
                    while (time >= 0) {
                        l.setText(Convert_TimeFormat(time));                        
                        Thread.sleep(1000);
                        if (process) {
                            time--;
                            if (!process_break) {
                                //label_time.setFont(new Font("Serif",Font.BOLD,20));
                                break_time_l.setForeground(new Color(20,58,81));
                                label_time.setForeground(new Color(213, 170, 114));                                
                            } else {
                                //break_time_l.setFont(new Font("Serif",Font.BOLD,20));
                                label_time.setForeground(new Color(20,58,81));
                                break_time_l.setForeground(new Color(213, 170, 114));
                            }
                        }
                        if (time == -1 && round_current <= round_total) {
                            if (process_break) {
                                time = (int) md.getValueAt(i, 1) * 60;
                                process = false;
                                start_button.setIcon(set_Ic("icon/sa.png"));
                                round.setText("Round " + round_current + "/" + round_total);
                                process_break = false;                                
                                playSound("/System/Library/Sounds/Hero.aiff");
                            } else {
                                playSound("/System/Library/Sounds/Hero.aiff");
                                if (round_current < round_total) {                                 
                                    JOptionPane.showMessageDialog(f, "Relax in " + md.getValueAt(i, 3) + " minute");
                                }
                                time = (int) md.getValueAt(i, 3) * 60;
                                start_button.setIcon(set_Ic("icon/sa.png"));
                                round.setText("Break Time !!!");
                                process_break = true;
                                process = false;
                                round_current += 1;  
                                
                            }
                        }
                        else if (time == -1 && round_current == round_total + 1) {
                            playSound("/System/Library/Sounds/Glass.aiff");
                            start_button.setIcon(set_Ic("icon/sa.png"));
                            JOptionPane.showMessageDialog(f, "Hết thời gian cho " + md.getValueAt(i, 0));                            
                            process_break = false;
                            
                            break;
                        }
                    }
                }
                int aa = JOptionPane.showConfirmDialog(f, "Bạn đã hết lịch, Bạn có muốn lặp lại không?","Message",JOptionPane.YES_NO_OPTION);
                if (aa != 0){
                    System.exit(0);
                }
            }
        } catch (InterruptedException ex) {
        }
    }
    String mini, second;
    
    String Convert_TimeFormat(int t) {
        if (t < 600) {
            mini = "0" + (time / 60);
        } else {
            mini = "" + (time / 60);
        }
        if (time % 60 < 10) {
            second = "0" + (time % 60);
        } else {
            second = "" + (time % 60);
        }
        return mini + " : " + second;
    }
    
    void mainF() {
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        round.setForeground(new Color(255, 214, 109));       
        JMenuItem del = new JMenuItem("Delete");       
        del.addActionListener(new menuItem_Lis());
        pp.add(del);
        l.setForeground(new Color(255, 214, 109));
        f.setLayout(new BorderLayout());
        l.setFont(new Font("Serif", Font.BOLD, 65));
        task_name.setFont(new Font("Serif", Font.ITALIC, 22));        
        task_name.setForeground(new Color(213, 170, 114));
        l.setText("" + (time / 60) + " : 00");        
        ct.add(round);
        ct.add(l);
        ct.setBackground(new Color(11, 31, 58));
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(FlowLayout.CENTER);
        flowLayout.setHgap(15);
        JPanel info = new JPanel();
        info.setLayout(flowLayout);
        info.setBackground(new Color(11, 31, 58));
        label_time = new JLabel("  Time " + time % 60 + ":00");        
        break_time_l = new JLabel(" Break " + break_time + ":00");
        start_button = make_Button("icon/sa.png", "Start", "start");
        start_button.addActionListener(new myLis_Button());
        info.add(label_time, BorderLayout.NORTH);
        info.add(start_button, BorderLayout.NORTH);
        info.add(break_time_l, BorderLayout.NORTH);
        ct.add(info);
        ct.add(task_name);
        JButton reset = make_Button("icon/reset.png", "Reset", "reset");
        reset.addActionListener(new myLis_Button());
        JButton exit = make_Button("icon/exit1.png", "Exit", "exit");
        exit.addActionListener(new myLis_Button());      
        JButton add_task = make_Button("icon/add.png", "Add", "add");
        add_task.addActionListener(new myLis_Button());      
        f.add(ct, BorderLayout.PAGE_START);
        JPanel combo_Button = new JPanel();
        combo_Button.setLayout(flowLayout);
        combo_Button.add(add_task);
        combo_Button.add(reset);      
        combo_Button.add(exit);
        combo_Button.setBackground(new Color(11, 31, 58));
        f.add(combo_Button, BorderLayout.PAGE_END);
        md = new DefaultTableModel(col, 0);
        defualt_setting();
        tb = new JTable(md);
        tb.setRowHeight(25);
        tb.setBackground(new Color(213, 170, 114));
        tb.setSelectionBackground(new Color(116,108,107));
        tb.setRowHeight(40);
        tb.addMouseListener(new mouse_Lis());
        sp = new JScrollPane(tb);
        sp.setBackground(new Color(213, 170, 114));
        f.add(sp, BorderLayout.CENTER);
        f.setSize(321, 456);
        f.setLocationRelativeTo(null);
        f.setBackground(new Color(11, 31, 58));
        f.setVisible(true);
    }
    
    JButton make_Button(String path, String name, String name2) {
        ImageIcon ic = new ImageIcon(path);
        Image ic1 = ic.getImage();
        Image ic2 = ic1.getScaledInstance(19, 19, Image.SCALE_SMOOTH);
        ic = new ImageIcon(ic2);
        JButton b = new JButton(ic);
        b.setToolTipText(name);
        b.setActionCommand(name2);
        return b;
    }
    
    ImageIcon set_Ic(String path) {
        ImageIcon ic = new ImageIcon(path);
        Image ic1 = ic.getImage();
        Image ic2 = ic1.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ic = new ImageIcon(ic2);
        return ic;
    }
    class myLis_Button implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "start":
                    if (!process) {
                        process = true;
                        start_button.setIcon(set_Ic("icon/pause.png"));
                    } else {
                        process = false;
                        start_button.setIcon(set_Ic("icon/sa.png"));
                    }
                    break;
                case "add":
                    Add_Task temp = new Add_Task();
                    temp.mainF_add();
                    break;
                case "reset":
                    f.setVisible(false);
                    mainJava x = new mainJava();
                    x.start();                    
                    break;
                case "exit":
                    int a = JOptionPane.showConfirmDialog(f, "Bạn thật sự muốn thoát chứ ?","Exit Message",JOptionPane.YES_NO_OPTION);
                    if (a == 0)
                        System.exit(0);
                    
                    
            }
        }
    }
    
    void defualt_setting() {
        Object[] a = {"Prepare", 1, 1, 0, 1};
        md.addRow(a);
    }
    class mouse_Lis extends MouseAdapter{
        public void mouseClicked(MouseEvent e){
            if (e.getButton() == 3){
                pp.show(tb,e.getX(),e.getY());
            }
        }
    } 
    class menuItem_Lis implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Delete")){
                if (tb.getSelectedRow() > process_index)
                    md.removeRow(tb.getSelectedRow());
            }
        }    
        
    }
    void playSound(String s){
    try{
        File f = new File(s);
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());  
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        clip.start();
    }
    catch (Exception e){}
    }
    public static void main(String[] args){
        mainJava x = new mainJava();
        x.start();
    }
    
}
