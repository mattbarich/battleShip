import java.time.temporal.ValueRange;

public class Grid {
    public static final int NUM_ROW = 5;
    public static final int NUM_COL = 5;

    String[][] grid = new String[NUM_ROW][NUM_COL];
    String[] line;
    String default_value = "-";

    String[][] populate_grid() {
        for (int row = 0; row < NUM_ROW; row++) {
            for (int col = 0; col < NUM_COL; col++) {
                grid[row][col] = default_value;
            }
        }
        return grid;
    }

    private int ship_size(int row_coordinate, int col_coordinate, int count){
        switch (count){
            case 0:
                break;
            case 1:
                if(grid[row_coordinate][col_coordinate] != "1" || grid[row_coordinate][col_coordinate] != "3"){
                    grid[row_coordinate-1][col_coordinate] = "A";
                }else{
                    grid[row_coordinate+1][col_coordinate] = "A";
                }
                break;
            case 2:
                System.out.println(row_coordinate);
                System.out.println(col_coordinate);
                break;
        }
        return count;
    }



    private void place_user_ships(int row_coordinate, int col_coordinate, int count){
        System.out.println(count);
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

    void print_grid() {
        for (String[] strings : grid) {
            for (int col = 0; col < grid.length; col++) {
                System.out.print(strings[col] + " ");
            }
            System.out.println();
        }
    }

    private int get_random_coordinate(int row_coordinate){
        row_coordinate = (int) (Math.random()*5 - 1) + 1;
        return row_coordinate;
    }

    private boolean validate_ship(int row_coordinate, int col_coordinate){
        return grid[row_coordinate-1][col_coordinate-1] != "1" && grid[row_coordinate-1][col_coordinate-1] != "2" && grid[row_coordinate-1][col_coordinate-1] != "3";
    }

    void place_ship(){
        for(int i=0; i < 3; i++){
            int count = i;
            int row_coordinate = 0;
            int col_coordinate = (int) (Math.random()*5 - 1) + 1;
            row_coordinate = get_random_coordinate(row_coordinate);
            System.out.println("("+row_coordinate+","+col_coordinate+")");
            if(validate_ship(row_coordinate,col_coordinate)== true){
                System.out.println("valid");
                place_user_ships(row_coordinate, col_coordinate, count);
            }else{
                row_coordinate = get_random_coordinate(row_coordinate);
                validate_ship(row_coordinate, col_coordinate);
                System.out.println("("+row_coordinate+","+col_coordinate+")");
                place_user_ships(row_coordinate, col_coordinate, count);
            }

            //ship_size(row_coordinate, col_coordinate, count);
        }
    }


    String check_player_guess(String returnVal){
        line = returnVal.trim().split(",");
        int user_x_coordinate = Integer.parseInt(line[0]);
        int user_y_coordinate = Integer.parseInt(line[1]);
        if(grid[user_x_coordinate-1][user_y_coordinate-1] == "1") {
            System.out.println("HIT");
            grid[user_x_coordinate-1][user_y_coordinate-1] = "H";
            returnVal = "hit";
        }else if(grid[user_x_coordinate-1][user_y_coordinate-1] == "2"){
            System.out.println("HIT");
            grid[user_x_coordinate-1][user_y_coordinate-1] = "H";
            returnVal = "hit";
        }else if(grid[user_x_coordinate-1][user_y_coordinate-1] == "3") {
            System.out.println("HIT");
            grid[user_x_coordinate-1][user_y_coordinate-1] = "H";
            returnVal = "hit";
        }else{
            returnVal = "miss";
        }
        return returnVal;
    }
}