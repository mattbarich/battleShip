import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class client2 {
    public PrintWriter socketWriter;
    public BufferedReader socketReader;
    public BufferedReader userReader;
    public String coordinates;
    public int turn = 1;
    public static void main(String[] args){
        (new client2()).go();
    }

    private void go() {

        try {
            Socket sock = new Socket("127.0.0.1", 6969);
            socketWriter = new PrintWriter(sock.getOutputStream());
            socketReader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            userReader = new BufferedReader(new InputStreamReader(System.in));
            while((coordinates = userReader.readLine()) != null){
                /*if(turn == 2) {
                    socketWriter.println(coordinates);
                    socketWriter.flush();
                    turn = 1;
                }
                if (turn == 1){
                    String response = socketReader.readLine();
                    System.out.println("Response from player 1:" + response);
                    turn = 2;
                }

                 */
                String response = socketReader.readLine();
                System.out.println("Response from player 1:" + response);

                socketWriter.println(coordinates);
                socketWriter.flush();
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}