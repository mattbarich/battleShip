public class Grid {
    public static final int NUM_ROW=5;
    public static final int NUM_COL=5;

    String[][] grid = new String[NUM_ROW][NUM_COL];
    String defualt_value = "-";

    void populate_grid(){
        for(int row = 0; row<NUM_ROW; row++){
            for(int col = 0; col<NUM_COL; col++){
                grid[row][col] = defualt_value;
            }
        }
    }

    void print_grid(){
        for(int row = 0; row<grid.length; row++){
            for(int col = 0; col< grid.length; col++){
                System.out.print(grid[row][col]+" ");
            }
            System.out.println();
        }
    }

    void place_ships() {
        System.out.println("Hello");
    }
}
