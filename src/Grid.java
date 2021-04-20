import java.util.Random;

public class Grid {
    public static final int NUM_ROW=5;
    public static final int NUM_COL=5;

    String[][] grid = new String[NUM_ROW][NUM_COL];
    String default_value = "-";

    public static int getRandomInt(int max, int min){
        return ((int) Math.random()*(max - min)) + min;
    }

    String[][] populate_grid(){
        for(int row = 0; row<NUM_ROW; row++){
            for(int col = 0; col<NUM_COL; col++){
                grid[row][col] = default_value;
            }
        }
        return grid;
    }

    void print_grid(){
        for(int row = 0; row<grid.length; row++){
            for(int col = 0; col< grid.length; col++){
                System.out.print(grid[row][col]+" ");
            }
            System.out.println();
        }
    }

    void place_ships(){
        Random randomNumber = new Random();
        int ship_starting_position = randomNumber.nextInt(5 - 1) + 1;
        System.out.println("Ship starting position: " + ship_starting_position);
        int ship_row_placement = randomNumber.nextInt(4 - 1) + 1;
        System.out.println("Ship row placement: " + ship_row_placement);
        switch (ship_row_placement) {
            case 1:
                for(int row = 0; row<NUM_ROW; row++){
                    for(int col = 0; col<NUM_COL; col++){
                        if(col == ship_starting_position && row == ship_starting_position){
                            grid[row-1][col-1] = "1";
                        }
                    }
                }
                System.out.println("Case 1");
                break;
            case 2:
                for(int row = 0; row<NUM_ROW; row++){
                    for(int col = 0; col<NUM_COL; col++){
                        if(col == ship_starting_position && row == ship_starting_position){
                            grid[row-1][col-1] = "2";
                            grid[row-1][col] = "2";
                        }
                    }
                }
                System.out.println("Case 2");
                break;
            case 3:
                for(int row = 0; row<NUM_ROW; row++){
                    for(int col = 0; col<NUM_COL; col++){
                        if(col == ship_starting_position && row == ship_starting_position){
                            grid[row-1][col-1] = "3";
                            grid[row-1][col] = "3";
                            grid[row-1][col+1] = "3";
                        }
                    }
                }
                System.out.println("Case 3");
                break;
            case 4:
                for(int row = 0; row<NUM_ROW; row++){
                    for(int col = 0; col<NUM_COL; col++){
                        if(col == ship_starting_position && row == ship_starting_position){
                            grid[row-1][col-1] = "2";
                        }
                    }
                }

        }
    }
}
