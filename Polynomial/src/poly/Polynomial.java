package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		if(poly1 == null) {return poly2;}
		if(poly2 == null) {return poly1;}
		
		if(poly1.term.degree==poly2.term.degree) {
			Node poly3 = new Node(poly1.term.coeff+poly2.term.coeff,poly1.term.degree,null);
			poly3.next = add(poly1.next, poly2.next);
			return poly3;
		}
		
		if(poly1.term.degree>poly2.term.degree) {
			Node poly3 = new Node(poly2.term.coeff,poly2.term.degree,null);
			poly3.next = add(poly1, poly2.next);
			return poly3;
		}
		
		Node poly3 = new Node(poly1.term.coeff, poly1.term.degree, null);
		poly3.next = add(poly1.next, poly2);
		return poly3;
	}
	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		Node poly3 = new Node(poly1.term.coeff*poly2.term.coeff,poly1.term.degree+poly2.term.degree,null);
		Node polyOut = null;
		Node poly1temp = poly1;
		Node poly2temp = poly2;
		
		while(poly1temp!=null) {	
			while(poly2temp.next!=null) {
				poly2temp = poly2temp.next;
				Node ptr = poly3;
				while(ptr.next!=null) {
					ptr = ptr.next;
				}
				ptr.next = new Node(poly1temp.term.coeff*poly2temp.term.coeff,poly1temp.term.degree+poly2temp.term.degree,null);
				
			}
			poly2temp = poly2;
			poly1temp = poly1temp.next;

			polyOut = add(polyOut,poly3);

			if(poly1temp!=null) {
				poly3 = new Node(poly1temp.term.coeff*poly2temp.term.coeff,poly1temp.term.degree+poly2temp.term.degree,null);
			}
		}
		
		return polyOut;
	}
		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		if(poly==null) {
			return 0;
		}
		return (
				(float)(poly.term.coeff*(Math.pow(x, poly.term.degree))) 
				+ evaluate(poly.next,x)
				);
	}
		
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}
