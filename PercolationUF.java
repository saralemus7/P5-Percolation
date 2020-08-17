import javax.xml.stream.events.EndElement;

/**
 * Class PercolationUF uses Union and Find to make a
 * system percolate. Each grid cell has its own set,
 * and is initially blocked. Once open, it will check
 * surrounding cells to see if any are opened, and if
 * they are, will merge the two sets. An artificial VTOP
 * source and VBOTTOM receiver dictate if the system percolates.
 * If they happen to be in the same set, the system percolates.
 * VTOP is above the first row and VBOTTOm below the last row.
 * @author Hector de Galard (hsd8)
 */

public class PercolationUF implements IPercolate {

    private IUnionFind myFinder;
    private boolean[][] myGrid;
    private final int VTOP;
    private final int VBOTTOM;
    private int myOpenCount;
    private int mySize;

    /**
     * Initialize the grid and the methods
     * @param finder stores the IUnionFinder methods and objects
     * @param size dictates the size of the grid by
     *             size*size
     */

    public PercolationUF(IUnionFind finder, int size){
        myGrid = new boolean[size][size];
        finder.initialize(size*size+2);
        myFinder = finder;
        myOpenCount = 0;
        VTOP = size*size;
        VBOTTOM = size*size + 1;
        mySize = size;


    }

    /**
     * Helper method to find the index of each cell
     * @param row row number of the grid
     * @param col column number of the grid
     * @return unique index corresponding to
     * that specific cell
     */
    public int getIndex(int row, int col){
        int index = row*mySize + col;
        return index;
    }

    /**
     * Method which opens the desired cell
     * and checks for adjacent open cells. If any
     * merges the set to create a large set
     *
     */
    @Override
    public void open(int row, int col) {
        if (! inBounds(row,col)) {
            throw new IndexOutOfBoundsException(
                    String.format("(%d,%d) not in bounds", row,col));
        }
        if (!myGrid[row][col]) {
            myOpenCount += 1;
            myGrid[row][col] = true;
            if (inBounds(row + 1, col) && myGrid[row + 1][col]) {
                myFinder.union(getIndex(row,col), getIndex(row + 1, col));
            }
            if (inBounds(row - 1, col) && myGrid[row - 1][col]) {
                myFinder.union(getIndex(row,col), getIndex(row - 1, col));
            }
            if (inBounds(row, col + 1) && myGrid[row][col + 1]) {
                myFinder.union(getIndex(row,col), getIndex(row, col + 1));
            }
            if (inBounds(row, col - 1) && myGrid[row][col - 1]) {
                myFinder.union(getIndex(row,col), getIndex(row, col - 1));
            }
            if (row == 0){
                myFinder.union(VTOP, getIndex(row, col));
            }
            if (row == mySize - 1){
                myFinder.union(VBOTTOM, getIndex(row, col));
            }
        }
    }

    /**
     * Checks to see if desired cell is open or closed
     *
     * @return true for open or false for closed
     */
    @Override
    public boolean isOpen(int row, int col) {
        if (! inBounds(row,col)) {
            throw new IndexOutOfBoundsException(
                    String.format("(%d,%d) not in bounds", row,col));
        }
        return myGrid[row][col];
    }

    /**
     * Method to check if cell is full or not
     *
     * @return true for full or false for blocked or open
     */
    @Override
    public boolean isFull(int row, int col) {
        if (!inBounds(row, col)) {
            throw new IndexOutOfBoundsException(
                    String.format("(%d,%d) not in bounds", row, col));
        }
        if (myFinder.connected(getIndex(row,col), VTOP )){
            return true;
        }
        return false;
    }

    /**
     * Method to check if system percolates
     * @return true for percolate
     */
    @Override
    public boolean percolates() {
        if (myFinder.connected(VTOP, VBOTTOM)){
            return true;
        }
        return false;
    }

    /**
     * Method that counts the number of open sites
     * @return count of open sites
     */
    @Override
    public int numberOfOpenSites() {
        return myOpenCount;
    }

    protected boolean inBounds(int row, int col) {
        if (row < 0 || row >= myGrid.length) return false;
        if (col < 0 || col >= myGrid[0].length) return false;
        return true;
    }
}
