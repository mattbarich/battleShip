
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
    private String attack = "first round";
    private String outcome = "hit";
    private int turn = 1;
    public static void main(String[] args) {
        (new server()).go();
    }
    Object lock = new Object();
    public void go(){
        //player 1
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(7070);
            System.out.println("waiting for client to connect");

            Socket socket1 = serverSocket.accept();
            Runnable player1 = new p1(socket1);
            Thread thread1 = new Thread(player1);
            thread1.start();

            //player 2
            Socket socket2 = serverSocket.accept();
            Runnable player2 = new p2(socket2);
            Thread thread2 = new Thread(player2);
            thread2.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public class p1 implements Runnable{
        public Socket socket;
        public p1(Socket socket1) {
            socket = socket1;
        }
        @Override
        public void run() {
            //communicate

            try {
                BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter print = new PrintWriter(socket.getOutputStream());
                while(true) {

                    /*System.out.println("player 1's turn to run");
                    print.println(attack);
                    print.flush();
                    */
                    String input = buffer.readLine();
                    System.out.println("p1's attack is: " + input);
                    attack = input;
                    //turn = 2;
                    //System.out.println(turn);

                    synchronized (lock){
                        lock.notify();
                    }
                    synchronized (lock) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    //update outcome grid
                    print.println(outcome);
                    print.flush();
                    System.out.println("P1 has updated its grid to reflect the " + outcome);
                    System.out.println("\n~~~~~~~~~Player 2's turn~~~~~~~\n");

                    synchronized (lock){
                        lock.notify();
                    }
                    synchronized (lock) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //receive attack
                    System.out.println("p1 recieved '" + attack +"' from p2");
                    print.println(attack);
                    print.flush();
                    //
                    //determine hit or not
                    outcome = buffer.readLine();
                    System.out.println("p1 deemed it was a " + outcome);

                    synchronized (lock){
                        lock.notify();
                    }
                    synchronized (lock) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }//while

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public class p2 implements Runnable{
        public Socket socket;

        public p2(Socket socket2) {
            socket = socket2;
        }

        @Override
        public void run() {
            //communicate
            try {
                BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter print = new PrintWriter(socket.getOutputStream());
                while(true) {
                    synchronized(lock){
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.println("p2 recieved '" + attack +"' from p1");
                    print.println(attack);
                    print.flush();

                    // update grid
                    //determine if it is hit
                    outcome = buffer.readLine();
                    System.out.println("p2 deemed that the attack is a" +outcome);


                    synchronized (lock){
                        lock.notify();
                    }
                    synchronized (lock) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    String input = buffer.readLine();
                    System.out.println("P2 sends attack '" + input + "' to p1");

                    //send back
                    attack = input;
                    turn = 1;

                    synchronized (lock) {
                        lock.notify();
                    }
                    synchronized (lock) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //update outcome grid
                    print.println(outcome);
                    print.flush();
                    System.out.println("P2 has updated its grid to reflect the " + outcome);
                    System.out.println("\n~~~~~~~~~Player 1's turn~~~~~~~\n");
                    synchronized (lock){
                        lock.notify();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
