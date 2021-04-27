import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class client implements ActionListener {
    public int rows;
    public int columns;
    public PrintWriter socketWriter;
    public BufferedReader socketReader;
    public BufferedReader userReader;
    private JTextField send;
    private JTextField recieve;
    //private JTextField userGrid;
    private JButton butt;
    private int count = 0;

    String[][] grid;

    public static void main(String[] args){
        (new client()).go();
    }

    private void go() {
        try {
            Grid grid = new Grid();
            String[][] player1 = grid.populate_grid();
            grid.place_ship();
            grid.print_grid();
            Socket sock = new Socket("127.0.0.1", 6969);
            socketWriter = new PrintWriter(sock.getOutputStream());
            socketReader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            userReader = new BufferedReader(new InputStreamReader(System.in));

            rows = 10;
            columns = 10;


            JPanel userGrid1 = new JPanel();
            JButton[][] receptions = new JButton[rows][columns];
            userGrid1.setLayout(new GridLayout(rows,columns));
            for(int row = 0; row < rows; row++){
                for( int column = 0; column < columns; column++){
                    JButton square = new JButton();
                    square.setBackground(Color.CYAN);
                    //square.setText("ocean");
                   // square.setForeground(Color.yellow);
                    square.setOpaque(true);
                    square.setBorderPainted(false);

                    receptions[row][column] = square;
                    userGrid1.add(square);
                }
            }

            JButton[][] attacks = new JButton[rows][columns];
            JPanel userGrid2 = new JPanel();
            userGrid2.setLayout(new GridLayout(rows,columns));
            for(int k = 0; k < rows; k++){
                for( int m = 0; m < columns; m++){
                    JButton square = new JButton();
                    square.setBackground(Color.BLUE);
                    square.setText("ocean");
                    square.setForeground(Color.yellow);
                    square.setOpaque(true);
                    square.setBorderPainted(false);

                    attacks[k][m] = square;
                    userGrid2.add(square);
                }
            }
            userGrid2.setBackground(Color.GREEN);
            attacks[1][2].setBackground(Color.red);
            attacks[2][7].setBackground(Color.WHITE);

            receptions[1][5].setText("HIT!!!");
            receptions[2][5].setText("MISS");




            send = new JTextField();
            recieve = new JTextField();
            butt = new JButton("COMPUTE!");
            butt.addActionListener(this);

            JFrame jframe = new JFrame();
            jframe.getContentPane().add(BorderLayout.NORTH, send);

            jframe.getContentPane().add(BorderLayout.EAST, userGrid1);
            jframe.getContentPane().add(BorderLayout.WEST, userGrid2);
            jframe.getContentPane().add(BorderLayout.CENTER, butt);
            jframe.getContentPane().add(BorderLayout.SOUTH, recieve);
            jframe.setSize(500, 500);
            jframe.setVisible(true);

            while(true){
                String returnVal = " something broke in the socket";
                returnVal = socketReader.readLine();
                System.out.println("Server Response: " + returnVal);
                recieve.setText(returnVal);
                count++;
                System.out.println(count);
                if(count > 1) {
                    grid.check_player_guess(returnVal);
                }
                grid.print_grid();
                recieve.repaint();
                butt.setEnabled(true);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String in = send.getText();
        socketWriter.println(in);
        socketWriter.flush();
       butt.setEnabled(false);
    }
}