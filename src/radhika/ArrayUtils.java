/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package radhika;

/**
 *
 * @author Puneet
 */
import java.util.Collection;


public class ArrayUtils {

	public static boolean isEmpty(byte [] array) {
		return array == null || array.length == 0;
	}

	public static boolean isEmpty(char [] array) {
		return array == null || array.length == 0;
	}

	public static boolean isEmpty(Object [] array) {
		return array == null || array.length == 0;
	}
	
	public static boolean isEmpty(Collection<?> collection) {
		return collection == null || collection.size() == 0;
	}
}
