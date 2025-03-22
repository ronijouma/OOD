import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import java.util.List;

public class ticTacToe implements TicTacToeMonitor,TicTacToeMoves {
    JPanel mainPanel;
    private JButton btnIndex0,btnIndex1, btnIndex2;
    private JButton btnIndex3, btnIndex4, btnIndex5;
    private JButton btnIndex6, btnIndex7, btnIndex8;
    private final JButton[] buttons = {btnIndex0, btnIndex1, btnIndex2,btnIndex3, btnIndex4, btnIndex5,btnIndex6, btnIndex7, btnIndex8};
    private JLabel myLabel;
    private JButton hardModeButton;
    private JButton easyModeButton;
    private int btnPressCount = 0;
    private int choice = 0;
    boolean player_turn;
    boolean mode = false;
    Random random;
    private audioExitScheduler monitoring;
    List<JButton> randomSquaresEdge = Arrays.asList(btnIndex0, btnIndex2, btnIndex6, btnIndex8);
    List<JButton> randomSquaresMiddle = Arrays.asList(btnIndex1, btnIndex5, btnIndex7, btnIndex3);

    public ticTacToe() {
        for (JButton button : buttons) {
            // to not put bullshit around
            button.setFocusPainted(false);
        }

        player_turn = true;
        easyModeButton.setFocusPainted(false);
        hardModeButton.setFocusPainted(false);

        monitoring = new audioExitScheduler();

        ActionListener buttonClickListener = e -> {
            easyModeButton.setEnabled(false);
            hardModeButton.setEnabled(false);

            if (player_turn) {
                JButton clickedButton = (JButton) e.getSource();
                if (clickedButton.getText().isEmpty()) {
                    clickedButton.setText("X");
                    clickedButton.setForeground(new Color(255, 0, 0));
                    player_turn = false;
                    myLabel.setText("O turn");
                    btnPressCount++;

                    if (btnPressCount >= 3) {
                        check();
                    }

                    new Thread(() -> {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ticTacToe.class.getName()).log(Level.WARNING, "Exception in thread sleep", ex);
                        }

                        SwingUtilities.invokeLater(() -> {
                            if (!mode) {
                                easyMove();
                            } else {
                                hardMove();
                            }
                        });
                    }).start();
                }
            }
        };

        btnIndex0.addActionListener(buttonClickListener);
        btnIndex1.addActionListener(buttonClickListener);
        btnIndex2.addActionListener(buttonClickListener);
        btnIndex3.addActionListener(buttonClickListener);
        btnIndex4.addActionListener(buttonClickListener);
        btnIndex5.addActionListener(buttonClickListener);
        btnIndex6.addActionListener(buttonClickListener);
        btnIndex7.addActionListener(buttonClickListener);
        btnIndex8.addActionListener(buttonClickListener);

        easyModeButton.addActionListener(e -> {
            mode = false;
            easyModeButton.setEnabled(false);
            hardModeButton.setEnabled(false);
        });
        hardModeButton.addActionListener(e -> {
            mode = true;
            easyModeButton.setEnabled(false);
            hardModeButton.setEnabled(false);
        });
    }
    @Override
    public void easyMove() {
        if (player_turn) {
            return;
        }

        random = new Random();
        int choice;
        do {
            choice = random.nextInt(9);
        } while (!buttons[choice].getText().isEmpty());

        buttons[choice].setForeground(new Color(0,0,255));
        myLabel.setText("X turn");
        buttons[choice].setText("O");
        player_turn=true;

        if (btnPressCount >= 3) {
            check();
        }
    }
    @Override
    public void hardMove() {
        if (player_turn) {
            return;
        }
        //first offensive
        offensiveMove(2, 1, 0);
        offensiveMove(3, 6, 0);
        offensiveMove(8, 4, 0);

        offensiveMove(0, 2, 1);
        offensiveMove(7, 4, 1);

        offensiveMove(0, 1, 2);
        offensiveMove(4, 6, 2);
        offensiveMove(5, 8, 2);

        offensiveMove(0, 6, 3);
        offensiveMove(4, 5, 3);

        offensiveMove(0, 8, 4);
        offensiveMove(1, 7, 4);
        offensiveMove(2, 6, 4);
        offensiveMove(3, 5, 4);

        offensiveMove(2, 8, 5);
        offensiveMove(3, 4, 5);

        offensiveMove(0, 3, 6);
        offensiveMove(7, 8, 6);
        offensiveMove(2, 4, 6);

        offensiveMove(6, 8, 7);
        offensiveMove(1, 4, 7);

        offensiveMove(2, 5, 8);
        offensiveMove(6, 7, 8);
        offensiveMove(0, 4, 8);

        //second defence
        defensiveMove(2, 1, 0);
        defensiveMove(3, 6, 0);
        defensiveMove(8, 4, 0);

        defensiveMove(0, 2, 1);
        defensiveMove(7, 4, 1);

        defensiveMove(0, 1, 2);
        defensiveMove(4, 6, 2);
        defensiveMove(5, 8, 2);

        defensiveMove(0, 6, 3);
        defensiveMove(4, 5, 3);

        defensiveMove(0, 8, 4);
        defensiveMove(1, 7, 4);
        defensiveMove(2, 6, 4);
        defensiveMove(3, 5, 4);

        defensiveMove(2, 8, 5);
        defensiveMove(3, 4, 5);

        defensiveMove(0, 3, 6);
        defensiveMove(7, 8, 6);
        defensiveMove(2, 4, 6);

        defensiveMove(6, 8, 7);
        defensiveMove(1, 4, 7);

        defensiveMove(2, 5, 8);
        defensiveMove(6, 7, 8);
        defensiveMove(0, 4, 8);

        //second hand defensive
        defensiveMove(1, 3, 0);
        defensiveMove(1, 5, 2);
        defensiveMove(5, 7, 8);
        defensiveMove(3, 7, 6);

        /*
            2  5  8
            1  4  7
            0  3  6
         */

        //Take most powerful square
        smartMove(btnIndex4);

        // specific situation
        Collections.shuffle(randomSquaresMiddle);

        if ((btnIndex0.getText().equals("X") && btnIndex8.getText().equals("X") || btnIndex2.getText().equals("X") && btnIndex6.getText().equals("X")) && btnIndex4.getText().equals("O")){
            for (JButton button : randomSquaresMiddle) {
                smartMove(button);
                return;
            }
        }

        if (btnIndex0.getText().equals("X") && btnIndex5.getText().equals("X")){
            smartMove(btnIndex2);
        }else if (btnIndex6.getText().equals("X") && btnIndex5.getText().equals("X")){
            smartMove(btnIndex8);
        }else if (btnIndex3.getText().equals("X") && btnIndex8.getText().equals("X")){
            smartMove(btnIndex6);
        }else if (btnIndex3.getText().equals("X") && btnIndex2.getText().equals("X")){
            smartMove(btnIndex0);
        }else if (btnIndex1.getText().equals("X") && btnIndex8.getText().equals("X")){
            smartMove(btnIndex2);
        }else if (btnIndex1.getText().equals("X") && btnIndex6.getText().equals("X")){
            smartMove(btnIndex0);
        }else if (btnIndex7.getText().equals("X") && btnIndex0.getText().equals("X")){
            smartMove(btnIndex6);
        }else if (btnIndex7.getText().equals("X") && btnIndex2.getText().equals("X")){
            smartMove(btnIndex8);
        }

        //just take random square
        Collections.shuffle(randomSquaresEdge);

        for (JButton button : randomSquaresEdge) {
            if (button.getText().isEmpty()) {
                smartMove(button);
                return;
            }
        }
        //random square
        if (!player_turn) {
            random = new Random();
            do {
                choice = random.nextInt(9);
            } while (!buttons[choice].getText().isEmpty());
            buttons[choice].setText("O");
            buttons[choice].setForeground(new Color(0, 0, 255));
            myLabel.setText("X turn");
            player_turn=true;
        }
        if (btnPressCount >= 3) {
            check();
        }
    }
    @Override
    public void offensiveMove(int index1, int index2, int index3){
        if (player_turn) {
            return;
        }
        if (buttons[index1].getText().equals("O") && buttons[index2].getText().equals("O") && buttons[index3].getText().isEmpty() && !player_turn) {

            buttons[index3].setForeground(new Color(0, 0, 255));
            buttons[index3].setText("O");
            myLabel.setText("X turn");
            player_turn=true;
            if (btnPressCount >= 3) {
                check();
            }
        }
    }
    @Override
    public void defensiveMove(int index1, int index2, int index3){
        if (player_turn) {
            return;
        }
        if (buttons[index1].getText().equals("X") && buttons[index2].getText().equals("X") && buttons[index3].getText().isEmpty() && !player_turn) {
            buttons[index3].setForeground(new Color(0, 0, 255));
            buttons[index3].setText("O");
            myLabel.setText("X turn");
            player_turn=true;
            if (btnPressCount >= 3) {
                check();
            }
        }
    }
    @Override
    public void smartMove(JButton button){
        if (player_turn) {
            return;
        }
        if (button.getText().isEmpty() && !player_turn) {
            button.setText("O");
            button.setForeground(new Color(0, 0, 255));
            myLabel.setText("X turn");
            player_turn = true;
        }
    }
    @Override
    public void check() {
        boolean xWins = false;
        boolean oWins = false;

        //check X win conditions
        if(
                (btnIndex0.getText().equals("X")) &&
                        (btnIndex1.getText().equals("X")) &&
                        (btnIndex2.getText().equals("X"))
        ) {
            xWins(0,1,2);
            xWins = true;
        }
        if(
                (btnIndex3.getText().equals("X")) &&
                        (btnIndex4.getText().equals("X")) &&
                        (btnIndex5.getText().equals("X"))
        ) {
            xWins(3,4,5);
            xWins = true;
        }
        if(
                (btnIndex6.getText().equals("X")) &&
                        (btnIndex7.getText().equals("X")) &&
                        (btnIndex8.getText().equals("X"))
        ) {
            xWins(6,7,8);
            xWins = true;
        }
        if(
                (btnIndex0.getText().equals("X")) &&
                        (btnIndex3.getText().equals("X")) &&
                        (btnIndex6.getText().equals("X"))
        ) {
            xWins(0,3,6);
            xWins = true;
        }
        if(
                (btnIndex1.getText().equals("X")) &&
                        (btnIndex4.getText().equals("X")) &&
                        (btnIndex7.getText().equals("X"))
        ) {
            xWins(1,4,7);
            xWins = true;
        }
        if(
                (btnIndex2.getText().equals("X")) &&
                        (btnIndex5.getText().equals("X")) &&
                        (btnIndex8.getText().equals("X"))
        ) {
            xWins(2,5,8);
            xWins = true;
        }
        if(
                (btnIndex0.getText().equals("X")) &&
                        (btnIndex4.getText().equals("X")) &&
                        (btnIndex8.getText().equals("X"))
        ) {
            xWins(0,4,8);
            xWins = true;
        }
        if(
                (btnIndex2.getText().equals("X")) &&
                        (btnIndex4.getText().equals("X")) &&
                        (btnIndex6.getText().equals("X"))
        ) {
            xWins(2,4,6);
            xWins = true;
        }


        if(
                (btnIndex0.getText().equals("O")) &&
                        (btnIndex1.getText().equals("O")) &&
                        (btnIndex2.getText().equals("O"))
        ) {
            oWins(0,1,2);
            oWins = true;
        }
        if(
                (btnIndex3.getText().equals("O")) &&
                        (btnIndex4.getText().equals("O")) &&
                        (btnIndex5.getText().equals("O"))
        ) {
            oWins(3,4,5);
            oWins = true;
        }
        if(
                (btnIndex6.getText().equals("O")) &&
                        (btnIndex7.getText().equals("O")) &&
                        (btnIndex8.getText().equals("O"))
        ) {
            oWins(6,7,8);
            oWins = true;
        }
        if(
                (btnIndex0.getText().equals("O")) &&
                        (btnIndex3.getText().equals("O")) &&
                        (btnIndex6.getText().equals("O"))
        ) {
            oWins(0,3,6);
            oWins = true;
        }
        if(
                (btnIndex1.getText().equals("O")) &&
                        (btnIndex4.getText().equals("O")) &&
                        (btnIndex7.getText().equals("O"))
        ) {
            oWins(1,4,7);
            oWins = true;
        }
        if(
                (btnIndex2.getText().equals("O")) &&
                        (btnIndex5.getText().equals("O")) &&
                        (btnIndex8.getText().equals("O"))
        ) {
            oWins(2,5,8);
            oWins = true;
        }
        if(
                (btnIndex0.getText().equals("O")) &&
                        (btnIndex4.getText().equals("O")) &&
                        (btnIndex8.getText().equals("O"))
        ) {
            oWins(0,4,8);
            oWins = true;
        }
        if(
                (btnIndex2.getText().equals("O")) &&
                        (btnIndex4.getText().equals("O")) &&
                        (btnIndex6.getText().equals("O"))
        ) {
            oWins(2,4,6);
            oWins = true;
        }

        if (!xWins && !oWins) {
            boolean isFull = true; // Assume the board is full

            for (int i = 0; i < 9; i++) {
                if (buttons[i].getText().isEmpty()) {
                    isFull = false;
                    break;
                }
            }

            if (isFull) {
                // All cells are filled, and no player has won
                draw();
            }
        }
    }
    @Override
    public void xWins(int a,int b,int c) {

        buttons[a].setBackground(Color.GREEN);
        buttons[b].setBackground(Color.GREEN);
        buttons[c].setBackground(Color.GREEN);

        for(int i=0;i<9;i++) {
            buttons[i].setEnabled(false);
        }

        myLabel.setText("X wins");
        player_turn= true;

        monitoring.playAudio("victory.wav");
        monitoring.scheduleExit();
    }
    @Override
    public void oWins(int a,int b,int c) {
        buttons[a].setBackground(Color.red);
        buttons[b].setBackground(Color.red);
        buttons[c].setBackground(Color.red);

        for(int i=0;i<9;i++) {
            buttons[i].setEnabled(false);
        }

        myLabel.setText("O wins");
        player_turn= true;

        monitoring.playAudio("losing.wav");
        monitoring.scheduleExit();
    }
    @Override
    public void draw() {
        for (int i = 0; i < 9; i++) {
            buttons[i].setBackground(Color.gray);
            buttons[i].setEnabled(false);
        }
        myLabel.setText("draw");
        player_turn= true;

        monitoring.playAudio("draw.wav");

        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ticTacToe.class.getName()).log(Level.WARNING, "Exception in thread sleep", ex);
            }

            SwingUtilities.invokeLater(() -> {
                for (int i = 0; i < 9; i++) {
                    buttons[i].setBackground(new Color(150, 150, 150));
                    buttons[i].setEnabled(true);
                    buttons[i].setText("");
                }
            });
        }).start();
    }
}