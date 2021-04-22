import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class client implements ActionListener {
    public PrintWriter socketWriter;
    public BufferedReader socketReader;
    public BufferedReader userReader;
    private JTextField send;
    private JTextField recieve;
    private JTextField userGrid;
    private JButton butt;

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
    }
}