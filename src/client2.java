import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class client2 implements ActionListener {
    public PrintWriter socketWriter;
    public BufferedReader socketReader;
    public BufferedReader userReader;
    public String coordinates;
    public int turn = 1;
    private String hitOrMiss;
    private String response;
    private int rows;
    private int columns;
    private JTextField send;
    private JTextField recieve;
    private JButton butt;
    private String attack;

    public static void main(String[] args){
        (new client2()).go();
    }

    private void go() {

        try {
            Grid grid = new Grid();
            String[][] player2 = grid.populate_grid();
            grid.place_ship();
            grid.print_grid();
            Socket sock = new Socket("127.0.0.1", 6969);
            socketWriter = new PrintWriter(sock.getOutputStream());
            socketReader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            userReader = new BufferedReader(new InputStreamReader(System.in));

            rows = 5;
            columns = 5;

            JPanel myGrid = new JPanel();
            JButton[][] receptions = new JButton[rows][columns];
            myGrid.setLayout(new GridLayout(rows,columns));
            for(int row = 0; row < rows; row++){
                for( int column = 0; column < columns; column++){
                    JButton square = new JButton();
                    square.setBackground(Color.CYAN);
                    square.setText("ocean");
                    if(player2[row][column] == "1"){
                        square.setBackground(Color.BLACK);
                        square.setText("My Ship!");
                        square.setForeground(Color.white);
                    }
                    square.setOpaque(true);
                    square.setBorderPainted(false);
                    receptions[row][column] = square;
                    myGrid.add(square);
                }
            }

            JButton[][] attacks = new JButton[rows][columns];
            JPanel myAttacks = new JPanel();
            myAttacks.setLayout(new GridLayout(rows,columns));
            for(int k = 0; k < rows; k++){
                for( int m = 0; m < columns; m++){
                    JButton square = new JButton();
                    square.setBackground(Color.BLUE);
                    square.setText("mystery");
                    square.setForeground(Color.yellow);
                    square.setOpaque(true);
                    square.setBorderPainted(false);

                    attacks[k][m] = square;
                    myAttacks.add(square);
                }
            }

            send = new JTextField();
            send.setText("Enter the coordinates you want to strike");
            recieve = new JTextField();
            butt = new JButton("Send Missile");
            butt.addActionListener(this);


            JFrame jframe = new JFrame();
            jframe.getContentPane().add(BorderLayout.NORTH, send);
            jframe.getContentPane().add(BorderLayout.EAST, myGrid);
            jframe.getContentPane().add(BorderLayout.WEST, myAttacks);
            jframe.getContentPane().add(BorderLayout.SOUTH, butt);
            //jframe.getContentPane().add(BorderLayout.SOUTH, recieve);
            jframe.setSize(500, 500);
            jframe.setVisible(true);

            while(true){
                try {
                    String returnVal = " something broke in the socket";
                    returnVal = socketReader.readLine();
                    System.out.println("The attack I recieved is: " + returnVal);
                    recieve.setText(returnVal);
                    recieve.repaint();
                    //recieved attack
                    hitOrMiss = grid.check_player_guess(returnVal);
                    grid.print_grid();
                    //check if hit or miss()
                    //update defense grid()
                    //hitOrMiss = "hit";
                    String[] recievedStrike = returnVal.trim().split(",");
                    int user_x_coordinate = Integer.parseInt(recievedStrike[0]);
                    int user_y_coordinate = Integer.parseInt(recievedStrike[1]);
                    if (hitOrMiss.equals("hit")) {
                        receptions[user_x_coordinate-1][user_y_coordinate-1].setBackground(Color.RED);
                    }
                    else{
                        receptions[user_x_coordinate-1][user_y_coordinate-1].setBackground(Color.white);
                    }
                    myGrid.repaint();

                    System.out.println("I have determined that it is a ..." + hitOrMiss);
                    socketWriter.println(hitOrMiss);
                    socketWriter.flush();
                    butt.setEnabled(true);

                    response = socketReader.readLine();
                    System.out.println("my attack: "+ attack  +"was a ..." + response);
                    //update my offense grid()
                    String[] tac = attack.trim().split(",");
                    int x = Integer.parseInt(tac[0]);
                    int y = Integer.parseInt(tac[1]);
                    if(response.equals("hit")){
                        attacks[x-1][y-1].setBackground(Color.RED);
                        attacks[x-1][y-1].setText("HIT");
                    }
                    else {
                        attacks[x-1][y-1].setBackground(Color.white);
                        attacks[x-1][y-1].setText("MISS");

                    }
                    attacks[x-1][y-1].setForeground(Color.black);
                    myAttacks.repaint();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        attack = send.getText();
        socketWriter.println(attack);
        socketWriter.flush();
        turn = 1;
        butt.setEnabled(false);
    }
}