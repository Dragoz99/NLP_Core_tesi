package ClientUS.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SchermataPrincipale extends JFrame {

    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SchermataPrincipale frame = new SchermataPrincipale();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**V
     * Create the frame.
     */
    public SchermataPrincipale() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("UserStoryGen");
        lblNewLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        lblNewLabel.setBounds(10, 41, 132, 43);
        contentPane.add(lblNewLabel);

        JButton btnNewButton = new JButton("New File");
        btnNewButton.setBounds(77, 195, 89, 23);
        btnNewButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new inputNewNameFile().setVisible(true);
                    }
                }
        );



        contentPane.add(btnNewButton);

        JButton btnNewButton_1 = new JButton("Load");
        btnNewButton_1.setBounds(176, 195, 89, 23);
        btnNewButton_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VisualizzaListaFile visualizzaListaFile = new VisualizzaListaFile();
                visualizzaListaFile.setVisible(true);
            }
        });
        contentPane.add(btnNewButton_1);

        JButton btnNewButton_2 = new JButton("Search");
        btnNewButton_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchTagUserStory searchTagUserStory = new SearchTagUserStory();
                searchTagUserStory.setVisible(true);
            }
        });
        btnNewButton_2.setBounds(275, 195, 89, 23);
        contentPane.add(btnNewButton_2);

        JLabel lblNewLabel_1 = new JLabel("Versione 0.0");
        lblNewLabel_1.setBounds(10, 236, 79, 14);
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Status");
        lblNewLabel_2.setBounds(332, 236, 37, 14);
        contentPane.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("On/Off");
        lblNewLabel_3.setBounds(379, 236, 46, 14);
        contentPane.add(lblNewLabel_3);

        JLabel lblNewLabel_4 = new JLabel("Descrizione uno due tre prova prova");
        lblNewLabel_4.setBackground(Color.GRAY);
        lblNewLabel_4.setBounds(10, 91, 227, 93);
        contentPane.add(lblNewLabel_4);
    }
}

