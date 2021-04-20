import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
    public String attack = "first round";
    public int turn = 1;
    public static void main(String[] args) {
        (new server()).go();
    }
    Object lock = new Object();
    public void go(){
        //player 1
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(6969);
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

                    System.out.println("player 1's turn to run");
                    print.println(attack);
                    print.flush();

                    String input = buffer.readLine();
                    System.out.println("received: " + input);
                    attack = input;
                    turn = 2;
                    System.out.println(turn);

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
                }

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
                    System.out.println("in p2");
                    System.out.println("Player 2's turn to run");
                    print.println(attack);
                    print.flush();

                    String input = buffer.readLine();
                    System.out.println("received: " + input);

                    //send back
                    attack = input;
                    turn = 1;
                    synchronized (lock) {
                        lock.notify();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}//big main

//use inner classes
