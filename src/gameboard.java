import java.io.BufferedReader;
import java.io.InputStreamReader;

public class gameboard {

    public static final int NUM_ROWS = 5;
    public static final int NUM_COLS = 5;

   private String[][] game_grid;

   public gameboard(){
       game_grid = new String[NUM_ROWS][NUM_COLS];
       for(int row = 0; row < NUM_ROWS; row++){
           for(int col = 0; col < NUM_COLS; col++){
               game_grid[row][col] = "-";
           }
       }
       /*for(int row = 0; row < NUM_ROWS; row++){
           for(int col = 0; col < NUM_COLS; col++){
               System.out.println(game_grid[row][col]+ " ");
           }
       }*/
   }
   public void placeShips(){
       try{
           System.out.println("Place ship coordinates");
           System.out.println("Example 1,1 (this would be the top left corner of the gameboard)");
           System.out.println("Please enter numbers between 1-5");
           for(int i =0; i < 3; i++){
               InputStreamReader inputStreamReader = new InputStreamReader(System.in);
               BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
               String player_ship = bufferedReader.readLine();
               String[] coordinates;
               coordinates=player_ship.trim().split(",");
               int x = Integer.parseInt(coordinates[0]);
               int y = Integer.parseInt(coordinates[1]);
               if(x > 5 && y > 5){
                   x %= 5;
                   y %= 5;
                   System.out.println(x+","+y);
               }
               for(int row = 0; row <= NUM_ROWS; row++){
                   for(int col = 0; col <= NUM_COLS; col++){
                       if(row==x && col == y){
                           game_grid[row-1][col-1] = "S";
                       }
                   }
               }
           }
           for(int row = 0; row < game_grid.length; row++){
               for(int col = 0; col < game_grid[row].length; col++){
                   System.out.println(game_grid[row][col]+ " ");
               }
               System.out.println();
           }

       } catch (Exception e) {
           e.printStackTrace();
       }

   }

    public static void main(String[] args) {
        gameboard g = new gameboard();
        g.placeShips();
    }
}
