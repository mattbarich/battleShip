import java.time.temporal.ValueRange;
import java.util.ArrayList;

public class Grid {
    public static final int NUM_ROW = 5;
    public static final int NUM_COL = 5;

    String[][] grid = new String[NUM_ROW][NUM_COL];
    String[] line;
    String default_value = "-";

    ArrayList<Integer> row_coordinates = new ArrayList<Integer>();

    String[][] populate_grid() {
        for (int row = 0; row < NUM_ROW; row++) {
            for (int col = 0; col < NUM_COL; col++) {
                grid[row][col] = default_value;
            }
        }
        return grid;
    }

    private void ship_size(int row_coordinate, int col_coordinate, int count){
        switch (count){
            case 0:
                break;
            case 1:
                grid[row_coordinate-1][col_coordinate] = "2";
                break;
            case 2:
                grid[row_coordinate-1][col_coordinate] = "3";
                grid[row_coordinate-1][col_coordinate+1] = "3";
        }
    }

    void print_grid() {
        for (String[] strings : grid) {
            for (int col = 0; col < grid.length; col++) {
                System.out.print(strings[col] + " ");
            }
            System.out.println();
        }
    }

    private void place_user_ships(int row_coordinate, int col_coordinate, int count){
        switch (count){
            case 1:
                grid[row_coordinate-1][col_coordinate-1] = "2";
                break;
            case 2:
                grid[row_coordinate-1][col_coordinate-1] = "3";
                break;
            default:
                grid[row_coordinate-1][col_coordinate-1] = "1";
                break;
        }
    }

    void place_ship(){
        for(int i=0; i < 3; i++){
            int count = i;
            int row_coordinate = i+1;
            int col_coordinate = (int) (Math.random()*4 - 1) + 1;
            row_coordinates.add(row_coordinate);
            place_user_ships(row_coordinate, col_coordinate, count);
            ship_size(row_coordinate, col_coordinate, count);

        }
    }


    String check_player_guess(String returnVal){
        line = returnVal.trim().split(",");
        int user_x_coordinate = Integer.parseInt(line[0]);
        int user_y_coordinate = Integer.parseInt(line[1]);
        if(grid[user_x_coordinate-1][user_y_coordinate-1] == "1") {
            grid[user_x_coordinate-1][user_y_coordinate-1] = "H";
            returnVal = "hit";
        }else if(grid[user_x_coordinate-1][user_y_coordinate-1] == "2"){
            grid[user_x_coordinate-1][user_y_coordinate-1] = "H";
            returnVal = "hit";
        }else if(grid[user_x_coordinate-1][user_y_coordinate-1] == "3") {
            grid[user_x_coordinate-1][user_y_coordinate-1] = "H";
            returnVal = "hit";
        }else{
            returnVal = "miss";
        }
        return returnVal;
    }
    int getUserShips(){
        int shipsPlaced = 0;
        for (int row = 0; row < NUM_ROW; row++) {
            for (int col = 0; col < NUM_COL; col++) {
                if(grid[row][col] != "-"){
                    shipsPlaced++;
                }
            }
        }
        return shipsPlaced;
    }
}

