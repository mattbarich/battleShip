import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class client implements ActionListener {
    public PrintWriter socketWriter;
    public BufferedReader socketReader;
    public BufferedReader userReader;
    public String coordinates;
    public int turn = 1;
    private JTextField send;
    private JTextField recieve;
    private JButton butt;
    public static void main(String[] args){
        (new client()).go();
    }

    private void go() {
        try {
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

            while(true){
                String returnVal = " something broke in the socket";
                returnVal = socketReader.readLine();
                System.out.println("Server Response: " + returnVal);
                recieve.setText(returnVal);
                recieve.repaint();
                //turn = 1;
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //if(turn == 1){
            String in = send.getText();
            socketWriter.println(in);
            socketWriter.flush();
            //turn = 2;
        //}

/*        String returnVal = " something broke in the socket";

        if (turn == 2){
            try {
                returnVal = socketReader.readLine();
                System.out.println("Server Response: " + returnVal);
                recieve.setText(returnVal);
                turn = 1;
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }

 */
    }
}