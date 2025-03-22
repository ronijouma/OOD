import javax.swing.*;
import java.awt.*;

public class appTicTacToe {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tic Tac Toe");
        frame.setContentPane(new ticTacToe().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(168, 173, 180));
        frame.setSize(700, 700);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
