import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

public class PercolationBFS extends PercolationDFSFast{
    public PercolationBFS(int n){
        super(n);
    }

    @Override
    protected void dfs(int row, int col) {
        Queue<Integer> qp = new LinkedList();
        myGrid[row][col] = FULL;
        qp.add(row*myGrid.length+col);

        while(qp.size() != 0){
            int keep = qp.remove();
            int currRow = keep/myGrid.length;
            int currCol = keep%myGrid.length;
            if(inBounds(currRow-1, currCol) && isOpen(currRow-1, currCol) && !isFull(currRow-1, currCol)) {
                qp.add(((currRow-1)*myGrid.length)+currCol);
                myGrid[currRow-1][currCol] = FULL;
            }
            if(inBounds(currRow+1, currCol) && isOpen(currRow+1, currCol) && !isFull(currRow+1, currCol)) {
                qp.add(((currRow+1)*myGrid.length)+currCol);
                myGrid[currRow+1][currCol] = FULL;
            }
            if(inBounds(currRow, currCol-1) && isOpen(currRow, currCol-1) && !isFull(currRow, currCol-1)) {
                qp.add((currRow*myGrid.length)+(currCol-1));
                myGrid[currRow][currCol-1] = FULL;
            }
            if(inBounds(currRow, currCol+1) && isOpen(currRow, currCol+1) && !isFull(currRow, currCol+1)) {
                qp.add((currRow*myGrid.length)+(currCol+1));
                myGrid[currRow][currCol+1] = FULL;
            }

        }

    }

    protected boolean inBounds(int row, int col) {
        if (row < 0 || row >= myGrid.length) return false;
        if (col < 0 || col >= myGrid[0].length) return false;
        return true;
    }
}
