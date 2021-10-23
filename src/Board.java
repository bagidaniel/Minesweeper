import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class Board implements ActionListener {

        private Cell[][] cells;
        private int cellID = 0;
        private final int rowSize = 10;
        private final int colSize = 10;
        JFrame frame = new JFrame("Minesweeper");
        JPanel gamePanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JButton reset = new JButton("RESET");

        public Board(){
            reset.setPreferredSize(new Dimension(100,40));
            reset.setFont(new Font("Verdana",Font.BOLD,25));
            reset.setBorder(BorderFactory.createEtchedBorder(1));
            reset.setBackground(Color.LIGHT_GRAY);
            reset.setFocusable(false);
            reset.addActionListener(this);

            buttonPanel.add(reset);
            gamePanel.add(addCells());
            frame.add(buttonPanel,BorderLayout.NORTH);
            frame.add(gamePanel,BorderLayout.CENTER);

            setMines();
            setNeighbours();

            frame.pack();
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        }

        public JPanel addCells(){
            JPanel panel = new JPanel(new GridLayout(rowSize,colSize));
            cells = new Cell[rowSize][colSize];
            for(int i = 0; i < rowSize; i++){
                for(int j = 0; j < colSize; j++){
                    cells[i][j] = new Cell(this);
                    cells[i][j].setId(getID());
                    panel.add(cells[i][j].getButton());
                }
            }
            return panel;
        }

        public void setMines(){
            int MINE = 10;
            Random random = new Random();
            ArrayList<Integer> list = new ArrayList<>();
            for(int i = 0; i < MINE;){
                int minePosition = (int)(random.nextDouble() * (rowSize*colSize));
                if(!list.contains(minePosition)){
                    list.add(minePosition);
                    i++;
                }
            }
            for(int i : list){
                getCell(i).setValue(-1);
            }
        }

        public void setNeighbours(){
            for(int i = 0; i < rowSize; i++){
                for(int j = 0; j < colSize; j++){
                    if(cells[i][j].getValue() != -1){
                        if(j>0 && cells[i][j-1].getValue() == -1){
                            cells[i][j].incrementValue(); //left
                        }
                        if(j<colSize-1 && cells[i][j+1].getValue() == -1){
                            cells[i][j].incrementValue(); //right
                        }
                        if(i>0 && cells[i-1][j].getValue() == -1){
                            cells[i][j].incrementValue(); //top
                        }
                        if(i<rowSize-1 && cells[i+1][j].getValue() == -1){
                            cells[i][j].incrementValue(); //bot
                        }
                        if(i>0 && j>0 && cells[i-1][j-1].getValue() == -1){
                            cells[i][j].incrementValue(); //top left
                        }
                        if(i<rowSize-1 && j<colSize-1 && cells[i+1][j+1].getValue() == -1){
                            cells[i][j].incrementValue(); //bot right
                        }
                        if(i>0 && j<colSize-1 && cells[i-1][j+1].getValue() == -1){
                            cells[i][j].incrementValue(); //top right
                        }
                        if(i<rowSize-1 && j>0 && cells[i+1][j-1].getValue() == -1){
                            cells[i][j].incrementValue(); //bot left
                        }
                    }
                }
            }
        }

        public void findEmptyCells(){
            for(int i = 0; i < rowSize; i++){
                for(int j = 0; j < colSize; j++){
                    if(cells[i][j].getButton()==Cell.pressedButton){
                        if(j>0 && cells[i][j-1].isNotChecked()){
                            cells[i][j-1].checkCell(); //left
                        }
                        if(j<colSize-1 && cells[i][j+1].isNotChecked()){
                            cells[i][j+1].checkCell(); //right
                        }
                        if(i>0 && cells[i-1][j].isNotChecked()){
                            cells[i-1][j].checkCell(); //top
                        }
                        if(i<rowSize-1 && cells[i+1][j].isNotChecked()){
                            cells[i+1][j].checkCell(); //bot
                        }
                        if(i>0 && j>0 && cells[i-1][j-1].isNotChecked()) {
                            cells[i-1][j-1].checkCell(); //top left
                        }
                        if(i<rowSize-1 && j<colSize-1 && cells[i+1][j+1].isNotChecked()){
                            cells[i+1][j+1].checkCell(); //bot right
                        }
                        if(i>0 && j<colSize-1 && cells[i-1][j+1].isNotChecked()){
                            cells[i-1][j+1].checkCell(); //top right
                        }
                        if(i<rowSize-1 && j>0 && cells[i+1][j-1].isNotChecked()){
                            cells[i+1][j-1].checkCell(); //bot left
                        }
                    }
                }
            }
        }

        public int getID(){
            int id = cellID;
            cellID++;
            return id;
        }

        public Cell getCell(int id){
            for(int i = 0; i < rowSize; i++){
                for(int j = 0; j < colSize; j++){
                    if(cells[i][j].getId() == id) return cells[i][j];
                }
            }
            return null;
        }

        public void isWin(){
            boolean win = true;
            for (int i = 0; i < rowSize; i++){
                for (int j = 0; j < colSize; j++){
                    if (cells[i][j].getValue() != -1 && cells[i][j].isNotChecked()) {
                        win = false;
                        break;
                    }
                }
            }
            if (win){
                JOptionPane.showMessageDialog(null,"You have won!",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        public void isLose(){
            for(int i = 0; i < rowSize; i++){
                for(int j = 0; j < colSize; j++){
                    cells[i][j].reveal();
                }
            }
            JOptionPane.showMessageDialog(null,"You have lost!",
                    "Game Over", JOptionPane.ERROR_MESSAGE);
        }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==reset){
            for(int i = 0; i < rowSize; i++){
                for(int j = 0; j < colSize; j++){
                    cells[i][j].reset();
                }
            }
            setMines();
            setNeighbours();
        }
    }
}

