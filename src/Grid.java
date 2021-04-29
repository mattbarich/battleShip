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

    void print_grid() {
        for (String[] strings : grid) {
            for (int col = 0; col < grid.length; col++) {
                System.out.print(strings[col] + " ");
            }
            System.out.println();
        }
    }

    void place_ship() {
        int x_ship_starting_position = (int) (Math.random() * 3 - 1) + 1;
        int y_ship_starting_position = (int) (Math.random() * 3 - 1) + 1;
        for (int row = 0; row < NUM_ROW; row++) {
            for (int col = 0; col < NUM_COL; col++) {
                if (row == x_ship_starting_position && col == y_ship_starting_position) {
                    grid[row - 1][col - 1] = "1";
                }
            }
        }
    }

    String check_player_guess(String returnVal) {
        line = returnVal.trim().split(",");
        int user_x_coordinate = Integer.parseInt(line[0]);
        int user_y_coordinate = Integer.parseInt(line[1]);
        if (grid[user_x_coordinate - 1][user_y_coordinate - 1] == "1") {
            System.out.println("HIT");
            grid[user_x_coordinate - 1][user_y_coordinate - 1] = "H";
            returnVal = "hit";
        } else {
            returnVal = "miss";
        }
        return returnVal;
    }


}