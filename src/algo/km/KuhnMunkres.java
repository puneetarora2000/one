/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algo.km;

/**
 *
 * @author Ecologic
 */
public class KuhnMunkres {
    
    /**
     * Takes in a matrix of non-negative values where the row indices represent
     * possible <b>executors</b> of the task and the column indices represent
     * possible <b>tasks</b>.  The returned matrix contains a 1 for entries
     * corresponding to an assignment of executors to tasks.
     *
     * Each executor will be assigned up to one task. The algorithm attempts
     * to maximize the total value of the assignment.
     *
     * @param values a non-negative matrix of values.
     * @return an indicator matrix of assignments from executors to tasks.
     */
    public static double[][] computeValueAssignment(double[][] valuesRaw) {

        int numRows = valuesRaw.length;
        if (numRows <= 0)
            throw new IllegalArgumentException("Matrix has zero rows.");

        int numColumns = valuesRaw[0].length;
        if (numColumns <= 0)
            throw new IllegalArgumentException("Matrix has zero columns");

        // Pad until the matrix is square
        int numPairs = Math.max(numRows, numColumns);
        double[][] values = new double[numPairs][numPairs];
        for (int row = 0; row < numRows; ++row)
            System.arraycopy(valuesRaw[row], 0, values[row], 0, numColumns);
        for (int row = numRows; row < numPairs; ++row)
            values[row] = new double[numPairs];
 
        
        System.out.println("===========================================");
        System.out.println("Step 1: From each row subtract off the row min");
       
        // Step 1: From each row subtract oï¬€ the row min.
        for (int row = 0; row < numPairs; ++row) {
            double rowMin = Double.MIN_VALUE;
            for (int col = 0; col < numPairs; ++col)
                rowMin = Math.min(rowMin, values[row][col]);
            for (int col = 0; col < numPairs; ++col)
                values[row][col] -= rowMin;
        }

         System.out.println("=================================================");
         System.out.println(" ");
        System.out.println("Step 2: From each column subtract off the column min.");
        
        // Step 2: From each column subtract off the column min.
        for (int col = 0; col < numPairs; ++col) {
            double colMin = Double.MIN_VALUE;
            for (int row = 0; row < numPairs; ++row)
                colMin = Math.min(colMin, values[row][col]);
            for (int row = 0; row < numPairs; ++row)
                values[row][col] -= colMin;
        }
        
         System.out.println("===========================================");
       
        
        // Step 3: Use as few lines as possible to cover all the zeros in the 
        // matrix. There is no easy rule to do this â€“ basically trial and error.
        // Suppose you use k lines.
        // â€¢ If k < n, let m be the minimum uncovered number. Subtract m from every
        // uncovered number. Add m to every number covered with two lines. Go back
        // to the start of step 3.
        // â€¢ If k = n, goto step 4.

        // Step 4: Starting with the top row, work your way downwards as you make assignments.
        // An assignment can be (uniquely) made when there is exactly one zero in a row. Once
        // an assignment it made, delete that row and column from the matrix.
        // If you cannot make all n assignments and all the remaining rows contain more than
        // one zero, switch to columns. Starting with the left column, work your way rightwards
        // as you make assignments.
        // Iterate between row assignments and column assignments until youâ€™ve made as many
        // unique assignments as possible. If still havenâ€™t made n assignments and you cannot
        // make a unique assignment either with rows or columns, make one arbitrarily by
        // selecting a cell with a zero in it. Then try to make unique row and/or column
        // assignments. (See the examples below).

        // TODO; finish the Kuhn-Munkres algorithm
    //    throw new UnsupportedOperationException("Not implemented yet.");
    
        return values;
    
    }

    /**
     * Takes in a matrix of non-negative costs where the row indices represent
     * possible <b>executors</b> of the task and the column indices represent
     * possible <b>tasks</b>.  The returned matrix contains a 1 for entries
     * corresponding to an assignment of executors to tasks.
     *
     * Each executor will be assigned up to one task.  The algorithm attempts
     * to minimize the total cost of the assignment.
     *
     * @param costs a non-negative matrix of costs.
     * @return an indicator matrix of assignments from executors to tasks.
     */
    public static double[][] computeCostAssignment(double[][] costs) {

        // Verify the matrix has reasonable size
        int numRows = costs.length;
        if (numRows <= 0)
            throw new IllegalArgumentException("Matrix has zero rows.");

        int numColumns = costs[0].length;
        if (numColumns <= 0)
            throw new IllegalArgumentException("Matrix has zero columns");
        
        // Find the largest number in the entire matrix
        double maxValue = Double.MIN_VALUE;
        for (double[] row : costs) {
            for (double val : row) {
                if (maxValue < val)
                    maxValue = val;
            }
        }

        // Subtract the value of the largest element from each cost, to get
        // positive "value" (which is what will be maximized)
        for (int row = 0; row < numRows; ++row) {
            for (int col = 0; col < numColumns; ++col) {
                costs[row][col] = maxValue - costs[row][col];
            }
        }

        // Find the solution for the value-maximization problem
        return computeValueAssignment(costs);
    }

    /**
     * Takes in a matrix of non-negative values where the row indices represent
     * possible <b>executors</b> of the task and the column indices represent
     * possible <b>tasks</b>.  The returned matrix contains a 1 for entries
     * corresponding to an assignment of executors to tasks.
     *
     * Each executor will be assigned at least one task, but possibly more.
     * Every task will be assigned to exactly one executor. The algorithm
     * attempts to maximize the total value of the assignment by augmenting
     * the optimal matching greedily to compute a maximal edge cover.
     *
     * @param values a non-negative matrix of values.
     * @return an indicator matrix of assignments from executors to tasks.
     */
    public static double[][] computeValueMultiAssignment(double[][] values) {

        // Verify the matrix has reasonable size
        int numRows = values.length;
        if (numRows <= 0)
            throw new IllegalArgumentException("Matrix has zero rows.");

        int numColumns = values[0].length;
        if (numColumns <= numRows)
            throw new IllegalArgumentException("Matrix has zero columns");

        // Find initial matching using Hungarian algorithm
        double[][] assignment = computeValueAssignment(values);

        // Greedily allocate remaining tasks
        for (int col = 0; col < numColumns; ++col) {

            // Find the highest value executor for each task
            double maxValue = Double.MIN_VALUE;
            int maxRow = -1;
            boolean isAssigned = false;

            for (int row = 0; row < numRows && !isAssigned; ++row) {

                if (assignment[row][col] > 0)
                    isAssigned = true;

                if (values[row][col] >= maxValue) {
                    maxValue = values[row][col];
                    maxRow = row;
                }
            }

            // If a task is unallocated, assign it here
            if (!isAssigned)
                assignment[maxRow][col] = 1;
        }

        return assignment;
    }

    /**
     * Takes in a matrix of non-negative costs where the row indices represent
     * possible <b>executors</b> of the task and the column indices represent
     * possible <b>tasks</b>.  The returned matrix contains a 1 for entries
     * corresponding to an assignment of executors to tasks.
     *
     * Each executor will be assigned at least one task, but possibly more.
     * Every task will be assigned to exactly one executor. The algorithm
     * attempts to maximize the total value of the assignment by augmenting
     * the optimal matching greedily to compute a maximal edge cover.
     *
     * @param costs a non-negative matrix of costs.
     * @return an indicator matrix of assignments from executors to tasks.
     */
    public static double[][] computeCostMultiAssignment(double[][] costs) {

        // Verify the matrix has reasonable size
        int numRows = costs.length;
        if (numRows <= 0)
            throw new IllegalArgumentException("Matrix has zero rows.");

        int numColumns = costs[0].length;
        if (numColumns <= 0)
            throw new IllegalArgumentException("Matrix has zero columns");

        // Find the largest number in the entire matrix
        double maxValue = Double.MIN_VALUE;
        for (double[] row : costs) {
            for (double val : row) {
                if (maxValue < val)
                    maxValue = val;
            }
        }

        // Subtract the value of the largest element from each cost, to get
        // positive "value" (which is what will be maximized)
        for (int row = 0; row < numRows; ++row) {
            for (int col = 0; col < numColumns; ++col) {
                costs[row][col] = maxValue - costs[row][col];
            }
        }

        // Find the solution for the value-maximization problem
        return computeValueMultiAssignment(costs);
    }
    
}
