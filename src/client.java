import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class client implements ActionListener {
    private String attack;
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
    private String hitOrMiss;
    private String response;

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

            rows = 5;
            columns = 5;

            JPanel userGrid1 = new JPanel();
            JButton[][] receptions = new JButton[rows][columns];
            userGrid1.setLayout(new GridLayout(rows,columns));
            for(int row = 0; row < rows; row++){
                for( int column = 0; column < columns; column++){
                    JButton square = new JButton();
                    square.setBackground(Color.CYAN);
                    square.setText("ocean");
                    if(player1[row][column] == "1"){
                        square.setBackground(Color.BLACK);
                        square.setText("Ship!");
                        square.setForeground(Color.white);
                    }

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
                    square.setText("mystery");
                    square.setForeground(Color.yellow);
                    square.setOpaque(true);
                    square.setBorderPainted(false);

                    attacks[k][m] = square;
                    userGrid2.add(square);
                }
            }
            //userGrid2.setBackground(Color.GREEN);
            //attacks[1][2].setBackground(Color.red);
            //attacks[2][4].setBackground(Color.WHITE);

            //receptions[1][5].setText("HIT!!!");
            //receptions[2][5].setText("MISS");




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
                response = socketReader.readLine();
                System.out.println("the attack: "  + attack + "I sent was a ..." + response);
                String[] q = attack.trim().split(",");
                int x = Integer.parseInt(q[0]);
                int y = Integer.parseInt(q[1]);
                if(response.equals("hit")){
                    attacks[x-1][y-1].setBackground(Color.RED);
                    attacks[x-1][y-1].setText("HIT");
                }
                else {
                    attacks[x-1][y-1].setBackground(Color.white);
                    attacks[x-1][y-1].setText("MISS");

                }
                attacks[x-1][y-1].setForeground(Color.black);
                userGrid2.repaint();

                //update offense grid()

                String returnVal = " something broke in the socket";
                returnVal = socketReader.readLine();
                System.out.println("the attack I recieved is: "  + returnVal);
                recieve.setText(returnVal);
                recieve.repaint();
                hitOrMiss = grid.check_player_guess(returnVal);
                grid.print_grid();
                //determine hit or miss
                //update defense grid()
                String[] r = returnVal.trim().split(",");
                int user_x_coordinate = Integer.parseInt(r[0]);
                int user_y_coordinate = Integer.parseInt(r[1]);
                if (hitOrMiss.equals("hit")) {
                    receptions[user_x_coordinate-1][user_y_coordinate-1].setBackground(Color.RED);
                }
                else{
                    receptions[user_x_coordinate-1][user_y_coordinate-1].setBackground(Color.white);
                }
                userGrid1.repaint();
                //hitOrMiss = "miss"; // or hit
                //System.out.println("I have determined that it is a ..." + hitOrMiss);
                socketWriter.println(hitOrMiss);
                socketWriter.flush();

                butt.setEnabled(true);
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
        butt.setEnabled(false);
    }
}