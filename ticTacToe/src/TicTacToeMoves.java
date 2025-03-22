import javax.swing.*;

public interface TicTacToeMoves {
    void easyMove();
    void hardMove();
    void offensiveMove(int index1, int index2, int index3);
    void defensiveMove(int index1, int index2, int index3);
    void smartMove(JButton button);
}
