import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class client {
    public PrintWriter socketWriter;
    public BufferedReader socketReader;
    public BufferedReader userReader;
    public String coordinates;
    public int turn = 1;

    public static void main(String[] args){
        (new client()).go();
    }

    private void go() {
        try {
            Socket sock = new Socket("127.0.0.1", 6969);
            socketWriter = new PrintWriter(sock.getOutputStream());
            socketReader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            userReader = new BufferedReader(new InputStreamReader(System.in));
            Grid grid = new Grid();
            grid.populate_grid();
            grid.print_grid();
            while((coordinates = userReader.readLine()) != null){
                if(turn == 1) {
                    socketWriter.println(coordinates);
                    socketWriter.flush();
                    turn = 2;
                }
                if(turn == 2){
                    String response = socketReader.readLine();
                    System.out.println("Response from player 2:" + response);
                    turn = 1;
                }
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}