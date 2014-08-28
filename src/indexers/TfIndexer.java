/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package indexers;

/**
 *
 * @author Puneet
 */

import org.apache.commons.collections15.Transformer;
import Jama.Matrix;

public class TfIndexer implements Transformer<Matrix,Matrix> {

  public Matrix transform(Matrix matrix) {
    for (int j = 0; j < matrix.getColumnDimension(); j++) {
      double sum = sum(matrix.getMatrix(
        0, matrix.getRowDimension() -1, j, j));
      for (int i = 0; i < matrix.getRowDimension(); i++) {
        matrix.set(i, j, (matrix.get(i, j) / sum));
      }
    }
    return matrix;
  }

  private double sum(Matrix colMatrix) {
    double sum = 0.0D;
    for (int i = 0; i < colMatrix.getRowDimension(); i++) {
      sum += colMatrix.get(i, 0);
    }
    return sum;
  }
}
    

