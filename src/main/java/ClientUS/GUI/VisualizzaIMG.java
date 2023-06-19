package ClientUS.GUI;

import javax.swing.*;

public class VisualizzaIMG extends JFrame {

    public VisualizzaIMG() {
        getContentPane().setLayout(null);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("My first JFrame");
        frame.setSize(600, 600);
        ImageIcon imag1 = new ImageIcon("C:\\Users\\loren\\eclipse-workspace\\ScheletriFinestreUserStory\\Img\\imgEsempio.png");
        frame.getContentPane().add(new JLabel(imag1));
        JButton btnNewButton = new JButton("New button");

        frame.pack();



        frame.setVisible(true);
    }
}
