 
package indexers;


import Jama.Matrix;
import Jama.SingularValueDecomposition;
import org.apache.commons.collections15.Transformer;

 
public class LsiIndexer implements Transformer<Matrix,Matrix> {
    
    
    
    
    @Override
    public Matrix transform(Matrix matrix) {
    // phase 1: Singular value decomposition
    SingularValueDecomposition svd = new SingularValueDecomposition(matrix);
    Matrix wordVector = svd.getU();
    Matrix sigma = svd.getS();
    Matrix documentVector = svd.getV();
    // compute the value of k (ie where to truncate)
    int k = (int) Math.floor(Math.sqrt(matrix.getColumnDimension()));
    Matrix reducedWordVector = wordVector.getMatrix(
      0, wordVector.getRowDimension() - 1, 0, k - 1);
    Matrix reducedSigma = sigma.getMatrix(0, k - 1, 0, k - 1);
    Matrix reducedDocumentVector = documentVector.getMatrix(
      0, documentVector.getRowDimension() - 1, 0, k - 1);
    Matrix weights = reducedWordVector.times(
      reducedSigma).times(reducedDocumentVector.transpose());
    // Phase 2: normalize the word scrores for a single document
    for (int j = 0; j < weights.getColumnDimension(); j++) {
      double sum = sum(weights.getMatrix(
        0, weights.getRowDimension() - 1, j, j));
      for (int i = 0; i < weights.getRowDimension(); i++) {
        weights.set(i, j, Math.abs((weights.get(i, j)) / sum));
      }
    }
    return weights;
  }

  private double sum(Matrix colMatrix) {
    double sum = 0.0D;
    for (int i = 0; i < colMatrix.getRowDimension(); i++) {
      sum += colMatrix.get(i, 0);
    }
    return sum;
  }
}
    
    

