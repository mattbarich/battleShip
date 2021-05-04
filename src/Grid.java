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

    private int ship_size(int x_ship_starting_position, int y_ship_starting_position, int count){
        switch (count){
            case 0:
                break;
            case 1:
                if(grid[x_ship_starting_position][y_ship_starting_position] != "1" || grid[x_ship_starting_position][y_ship_starting_position] != "3"){
                    grid[x_ship_starting_position-1][y_ship_starting_position] = "A";
                }else{
                    grid[x_ship_starting_position+1][y_ship_starting_position] = "A";
                }
                break;
            case 2:
                System.out.println(x_ship_starting_position);
                System.out.println(y_ship_starting_position);
                break;
        }
        return count;
    }



    private void place_user_ships(int x_ship_starting_position, int y_ship_starting_position, int count){
        System.out.println(count);
        switch (count){
            case 1:
                grid[x_ship_starting_position][y_ship_starting_position] = "2";
                break;
            case 2:
                grid[x_ship_starting_position][y_ship_starting_position] = "3";
                break;
            default:
                grid[x_ship_starting_position][y_ship_starting_position] = "1";
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

    private boolean validate_ship(int x_ship_starting_position, int y_ship_starting_position){
        return grid[x_ship_starting_position][y_ship_starting_position] != "1" && grid[x_ship_starting_position][y_ship_starting_position] != "2" &&grid[x_ship_starting_position][y_ship_starting_position] != "3";
    }

    void place_ship(){
        for(int i=0; i < 3; i++){
            int count = i;
            int x_ship_starting_position = (int) (Math.random()*5 - 1) + 1;
            int y_ship_starting_position = (int) (Math.random()*5 - 1) + 1;
            System.out.println("("+x_ship_starting_position+","+y_ship_starting_position+")");
            if(validate_ship(x_ship_starting_position,y_ship_starting_position)== true){
                place_user_ships(x_ship_starting_position, y_ship_starting_position, count);
            }else{
            }

            //ship_size(x_ship_starting_position, y_ship_starting_position, count);
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
        }else{
            returnVal = "miss";
        }
        return returnVal;
    }
}