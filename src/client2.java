import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class client2 implements ActionListener {
    public PrintWriter socketWriter;
    public BufferedReader socketReader;
    public BufferedReader userReader;
    public String coordinates;
    public int turn = 1;
    private String hitOrMiss;
    private String response;

    private JTextField send;
    private JTextField receive;
    private JButton butt;
    private String attack;
    private JPanel myOcean;
    private JPanel opponentsOcean;
    private JButton[][] receivedStrikes;
    private JButton[][] sentStrikes;
    private String[] receivedStrike;
    private String[] sentStrike;

    private int rows = 9;
    private int columns = 9;
    private int enemyShips = 6;
    private int hits = 0;
    private int hitsOnMe = 0;
    private int myShips;

    public static void main(String[] args){
        (new client2()).go();
    }

    private void go() {

        try {
            Grid grid = new Grid();
            String[][] player2 = grid.populate_grid();
            grid.place_ship();
            grid.print_grid();
            myShips = grid.getUserShips();
            System.out.println(myShips);
            Socket sock = new Socket("127.0.0.1", 6969);
            socketWriter = new PrintWriter(sock.getOutputStream());
            socketReader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            userReader = new BufferedReader(new InputStreamReader(System.in));

            myOcean= new JPanel();
            receivedStrikes = new JButton[rows][columns];

            myOcean.setLayout(new GridLayout(rows,columns));
            for(int row = 0; row < rows; row++){
                for( int column = 0; column < columns; column++){
                    JButton square = new JButton();
                    square.setBackground(Color.CYAN);
                    square.setText("ocean");
                    if(player2[row][column] == "1"){
                        square.setBackground(Color.BLACK);
                        square.setText("My Dingy!");
                        square.setForeground(Color.white);
                    }else if(player2[row][column] == "2"){
                        square.setBackground(Color.yellow);
                        square.setText("My Submarine!");
                        square.setForeground(Color.white);
                    }else if(player2[row][column] == "3"){
                        square.setBackground(Color.GREEN);
                        square.setText("My Destroyer!");
                        square.setForeground(Color.white);
                    }else if(player2[row][column] == "4"){
                        square.setBackground(Color.ORANGE);
                        square.setText("My Carrier!");
                        square.setForeground(Color.white);
                    }
                    square.setOpaque(true);
                    square.setBorderPainted(false);
                    receivedStrikes[row][column] = square;
                    myOcean.add(square);
                }
            }

            opponentsOcean = new JPanel();
            sentStrikes = new JButton[rows][columns];

            opponentsOcean.setLayout(new GridLayout(rows,columns));
            for(int k = 0; k < rows; k++){
                for( int m = 0; m < columns; m++){
                    JButton square = new JButton();
                    square.setBackground(Color.BLUE);
                    square.setText("mystery");
                    square.setForeground(Color.yellow);
                    square.setOpaque(true);
                    square.setBorderPainted(false);

                    sentStrikes[k][m] = square;
                    opponentsOcean.add(square);
                }
            }

            send = new JTextField();
            send.setText("Enter the coordinates you want to strike");
            receive = new JTextField();
            butt = new JButton("Send Missile");
            butt.addActionListener(this);

            JFrame jframe = new JFrame();
            jframe.getContentPane().add(BorderLayout.NORTH, send);
            jframe.getContentPane().add(BorderLayout.EAST, myOcean);
            jframe.getContentPane().add(BorderLayout.WEST, opponentsOcean);
            jframe.getContentPane().add(BorderLayout.SOUTH, butt);
            //jframe.getContentPane().add(BorderLayout.SOUTH, recieve);
            jframe.setSize(500, 500);
            jframe.setVisible(true);

            while(true){
                try {
                    butt.setEnabled(false);
                    String returnVal = " something broke in the socket";
                    returnVal = socketReader.readLine();
                    System.out.println("The attack I recieved is: " + returnVal);
                    receive.setText(returnVal);
                    receive.repaint();
                    hitOrMiss = grid.check_player_guess(returnVal);
                    grid.print_grid();

                    receivedStrike = returnVal.trim().split(",");
                    int user_x_coordinate = Integer.parseInt(receivedStrike[0]);
                    int user_y_coordinate = Integer.parseInt(receivedStrike[1]);
                    if (hitOrMiss.equals("hit")) {
                        receivedStrikes[user_x_coordinate-1][user_y_coordinate-1].setBackground(Color.RED);
                    }
                    else{
                        receivedStrikes[user_x_coordinate-1][user_y_coordinate-1].setBackground(Color.white);
                    }
                    myOcean.repaint();
                    System.out.println("I have determined that it is a ..." + hitOrMiss);
                    socketWriter.println(hitOrMiss);
                    socketWriter.flush();
                    if(hitOrMiss.equals("hit")){
                        hitsOnMe++;
                    }
                    if(hitsOnMe == myShips){
                        butt.setEnabled(false);
                        send.setText("You Got Sunk!!!!");
                        butt.setEnabled(false);
                        TimeUnit.SECONDS.sleep(5);
                        System.exit(1);
                    }
                    butt.setEnabled(true);

                    response = socketReader.readLine();
                    System.out.println("my attack: "+ attack  +"was a ..." + response);
                    sentStrike = attack.trim().split(",");
                    int x = Integer.parseInt(sentStrike[0]);
                    int y = Integer.parseInt(sentStrike[1]);
                    if(response.equals("hit")){
                        sentStrikes[x-1][y-1].setBackground(Color.RED);
                        sentStrikes[x-1][y-1].setText("HIT");
                    }
                    else {
                        sentStrikes[x-1][y-1].setBackground(Color.white);
                        sentStrikes[x-1][y-1].setText("MISS");

                    }
                    sentStrikes[x-1][y-1].setForeground(Color.black);
                    opponentsOcean.repaint();

                    if(response.equals("hit")){
                        hits++;
                    }
                    if(hits == enemyShips){
                        butt.setEnabled(false);
                        send.setText("You won the game!!!!");
                        TimeUnit.SECONDS.sleep(5);
                        System.exit(1);
                    }
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