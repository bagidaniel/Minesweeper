import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Cell extends MouseAdapter {
    private final JButton button;
    public static JButton pressedButton;
    private final Board board;
    private int value;
    private int id;
    private boolean notChecked;
    private boolean flagged;

    public Cell(Board board){
        button = new JButton();
        button.setFocusable(false);
        button.setFont(new Font("Verdana", Font.BOLD, 35));
        button.setBackground(Color.DARK_GRAY);
        button.setBorder(BorderFactory.createBevelBorder(0));
        button.setPreferredSize(new Dimension(50,50));
        button.addMouseListener(this);
        this.board = board;
        notChecked = true;
        flagged = false;
    }

    public JButton getButton() {
        return button;
    }

    public int getValue() {
        return value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void displayValue(){
        if(value > 0){
            button.setText("" + value);
            switch (value) {
                case 1 -> button.setForeground(Color.BLUE);
                case 2 -> button.setForeground(Color.GREEN);
                case 3 -> button.setForeground(Color.RED);
                case 4 -> button.setForeground(Color.MAGENTA);
                case 5 -> button.setForeground(Color.ORANGE);
                default -> button.setForeground(Color.BLACK);
            }
        }
    }
    public void displayMine(){
        if(value==-1) {
            button.setFont(new Font("Arial Unicode MS", Font.BOLD, 35));
            button.setText("\u269B");
            button.setBackground(Color.RED);
        }
    }
    public void checkCell(){
        //button.setEnabled(false);
        button.setBackground(Color.WHITE);
        displayValue();
        notChecked = false;
        board.isWin();
        if(value == 0) {
            board.isWin();
            pressedButton = button;
            board.findEmptyCells();
        }
        if(value == -1) {
            board.isLose();
        }
    }

    public void incrementValue(){
        value++;
    }

    public boolean isNotChecked(){
        return notChecked;
    }

    public void reveal(){
        displayMine();
        button.setEnabled(false);
    }

    public void flag(){
        button.setBackground(Color.MAGENTA);
        button.setFont(new Font("Arial Unicode MS", Font.BOLD, 35));
        button.setText("\u2691");
        flagged = true;
    }

    public void reset(){
        button.setEnabled(true);
        button.setText("");
        button.setBackground(Color.DARK_GRAY);
        button.setFont(new Font("Verdana", Font.BOLD, 35));
        value = 0;
        notChecked = true;
        flagged = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON3){
            if (flagged){
                button.setEnabled(true);
                button.setText("");
                button.setBackground(Color.DARK_GRAY);
                button.setFont(new Font("Verdana", Font.BOLD, 35));
                flagged = false;
            }
            else{
                flag();
            }
        }
        else{
            pressedButton = (JButton) e.getSource();
            if (!flagged){
                checkCell();
            }
        }
    }
}