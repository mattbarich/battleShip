public class Grid {
    public static final int NUM_ROW = 3;
    public static final int NUM_COL = 3;

    String[][] grid = new String[NUM_ROW][NUM_COL];
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
    void place_ship(){
        int x_ship_starting_position = (int) (Math.random()*3 - 1) + 1;
        int y_ship_starting_position = (int) (Math.random()*3 - 1) + 1;
        for(int row = 0; row<NUM_ROW;row++){
            for(int col = 0; col<NUM_COL;col++){
                if(row == x_ship_starting_position && col == y_ship_starting_position){
                    grid[row-1][col-1] = "1";
                }
            }
        }
    }

    boolean check_player_guess(String returnVal){
        returnVal.trim().split(",");
        return false;
    }
}
