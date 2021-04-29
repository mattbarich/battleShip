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
            String[][] player1 = grid.populate_grid();
            grid.place_ship();
            grid.print_grid();
            Socket sock = new Socket("127.0.0.1", 6969);
            socketWriter = new PrintWriter(sock.getOutputStream());
            socketReader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            userReader = new BufferedReader(new InputStreamReader(System.in));

            send = new JTextField();
            recieve = new JTextField();
            butt = new JButton("COMPUTE!");
            butt.addActionListener(this);

            JFrame jframe = new JFrame();
            jframe.getContentPane().add(BorderLayout.NORTH, send);
            jframe.getContentPane().add(BorderLayout.CENTER, butt);
            jframe.getContentPane().add(BorderLayout.SOUTH, recieve);
            jframe.setSize(500, 500);
            jframe.setVisible(true);
<<<<<<< HEAD
=======
            //String response = socketReader.readLine();
            //System.out.println("Response from player 1:" + response);

            //socketWriter.println(coordinates);
            //socketWriter.flush();

>>>>>>> 871dd521dd2005a6c13e87b1ee4b47569b2f08db
            while(true){
                try {

                    String returnVal = " something broke in the socket";
                    returnVal = socketReader.readLine();
                    System.out.println("The attack I recieved is: " + returnVal);
                    recieve.setText(returnVal);
                    recieve.repaint();

                    //recieved attack
                    //check if hit or miss()
                    //update defense grid()
                    hitOrMiss = "hit";
                    System.out.println("I have determined that it is a ..." + hitOrMiss);
                    String response = hitOrMiss;
                    socketWriter.println(response);
                    socketWriter.flush();
                    butt.setEnabled(true);
<<<<<<< HEAD
=======


                    response = socketReader.readLine();
                    System.out.println("my attack: "+ attack  +"was a ..." + response);
                    //update my offense grid()



                    //turn = 2;

>>>>>>> 871dd521dd2005a6c13e87b1ee4b47569b2f08db
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