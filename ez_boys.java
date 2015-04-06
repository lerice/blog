/**
 * Sequence models number sequences.
 * 
 * @author PENISVAGINA
 * @version 6/4/2015
 */
import java.util.ArrayList;

public class Sequence
{
	// the numbers in the sequence
	private double[] sequence;

	// sets up sequence by parsing s 
	// the numbers in s will be separated by commas 
	public Sequence(String s)
	{
		// First split the string, returning array of strings
		String[] stringSequence = s.split(",");

		// Define our double array, of length which will be the same as the length of the string array:
		sequence = new double[stringSequence.length];

		// Iterate through the string array. Nothing new here
		for (int i = 0; i < stringSequence.length - 1; i++) {
			// On each string, use parseDouble, to convert the string into a double, and store it in our new array
			sequence[i] = Double.parseDouble(stringSequence[i]);
		}
	}

	// returns sequence 
	public double[] getSequence()
	{
		return sequence;
	}

	// returns the product of 1..x 
	public int factorial(int x)
	{
		// Easy maths. Start with 1 and loop through, multiplying by the newest number
		int total = 1;

		// Loop, start index from 2 (skipping 0 and 1), but MAKE SURE TO INCLUDE x!
		for (int i = 2; i <= x; i++) {
			total = total * i;
		}

		return total;
	}

	// returns true iff all of the items on sequence are equal 
	public boolean allEqual()
	{
		// Same as trying to find the minimum/max, start with the first, compare with every single OTHER 
		// one in the list. 

		// Grab the first:
		double firstInSequence = sequence[0];

		// Go through the list, ignore the first:
		for (int i = 1; i < sequence.length - 1; i++) {
			// Grab the current double:
			double currentDouble = sequence[i];

			// Heres the kick, if the current double is NOT the same as the first, that means we KNOW
			// that all the terms are not the same. We can return false right here:
			if (currentDouble != firstInSequence) {
				return false;
			}
		}

		// If we made it here, that means we went through the entire list and never had 
		// currentDouble != firstInSequence. We can conclude the entire list is the same 
		// and thus return true.
		return true;
	}

	// returns a new array holding the differences between adjacent items on sequence 
	public double[] differences()
	{
		// Create the 'new' array which will hold the differences. It will need length of the 
		// length of the original sequence, minus 1 (because maths)
		double[] differences = new double[sequence.length - 1];

		// Storage variable. It will hold the 'previous' double in the sequence, starting with the first
		double previousDouble = sequence[0];

		// Loop! Start from index 1:
		for (int i = 1; i < sequence.length - 1; i++) {
			// Figure out the difference of the current and previous:
			double currentDouble = sequence[i];
			double currentDifference = currentDouble - previousDouble;

			// Save it in our array. MAKE SURE TO GET THE INDEX RIGHT!
			differences[i - 1] = currentDifference;

			// BEFORE WE GO. We need to update the 'previousDouble' to be correct for the next one.
			previousDouble = currentDouble;
		}

		// We out of here
		return differences;
	}

	// subtracts from each item in sequence the effect of the term t 
	// implements Step 4 of the algorithm description on the project web page 
	public void updateSequence(Term t)
	{
		// So, GUESS WHAT MORE LOOPS. Go through the sequence:
		for (int i = 0; i < sequence.length - 1; i++) {
			// NOW: tricky bit - REPLACE the 'currentDouble' with itself, minus the term thingy
			// We can't extract the currentDouble in a variable 
			//          like double currentDouble = sequence[i]
			// Because we need to replace the original value, not just store it
			// Also take careful note of the equation. This should be simple maths:

			sequence[i] = sequence[i] - (t.coefficient * Math.pow((i + 1), t.exponent));
		}
	}

	// returns the next term in the simplest polynomial that generates sequence 
	// implements Steps 1-3 of the algorithm description on the project web page 
	public Term nextTerm()
	{
		// Step 1: Repeatedly calculate the differences between adjacent numbers 
		// in the sequence, until every number in the sequence is the same: 

		// So, we want to count each time we go through and calculate the differences,
		// 'until the sequence is allEqual()'
		// Start from 0, obviously:
		int totalIterations = 0;

		// Our exit condition is that our sequence is all equal. Sounds familiar:
		// That means, WHILE IT ISNT EQUAL, KEEP GOING:
		while (!allEqual()) {
			// It obviously isn't equal. We are going through, so update our counter!
			totalIterations++;

			// And... calculate the differences thanks:
			// Note that we are replacing the current stored 'sequence' with its differences:
			// This is OK because we don't give a shit about the 'original' sequence anymore
			// BE VERY CAREFUL YOU UNDERSTAND THIS LINE. YOU MUST UNDERSTAND THIS LINE.
			sequence = differences();
		}

		// WE GOT OUT OF THE LOOP! THAT MEANS OUR current 'sequence' is ALL EQUAL.
		// What does that mean? The can extract the 'number' in our final sequence
		// Lets use the first one, because they are all the same
		double finalSequenceNumber = sequence[0];

		// And before we forget:
		// Step 2: The exponent of the new term is the number of steps above
		// So, the exponent = totalIterations; (we don't need to declare this. It's redundant)

		// Now, Step 3: The coefficient of the new term is the number on the final sequence divided 
		// by the factorial of the exponent, i.e. 4/2! = 2. Thus the new term is 2x^2.
		// So.... grab the factorial of our exponent!
		int factorialOfExponent = factorial(totalIterations);

		// And divide it you fucking genius:
		// Remember a (double / int) gives you a double, which is what we want
		double coefficient = finalSequenceNumber / factorialOfExponent; 

		// We did it.... confuckinggradulations
		return new Term(coefficient, totalIterations);
	}

	// returns the simplest polynomial that generates sequence and displays the polynomial as a String 
	// implements the algorithm description on the project web page 
	public Polynomial solveSequence()
	{
		// OK boys. We need a polynomial to begin with:
		Polynomial polynomial = new Polynomial(); // Please tell me you understand this.

		// The algorithm:
		// Repeatedly create terms and add them to the result until all the numbers on the 
		// sequence are equal. Don't forget the last term! 

		// So.... 'until our sequence is allEqual(), create terms'
		// Fucking easy

		while (!allEqual()) { // Nothing new here
			// Our sequence is obviously not all equal.
			// Create the term
			// Thankfully this is easy, because we did the hard work above:
			Term currentTerm = nextTerm();

			// Good shit. Add it to the polynomial:
			polynomial.add(currentTerm);

			// Before we 'finish' with this iteration of the loop
			// we can't leave the sequence in this state...what are we missing again?
			updateSequence(); // Oh yeah...
		}

		// So we got out of the loop. That means our sequence IS ALL EQUAL, and we are garunteed to have
		// iterated through the algorithm as many times as possible, generating our terms
		// WAIT. we forgot the last one.

		// Now all of the numbers in the sequence are the same, so the iteration terminates. The final 
		// term in the polynomial is the number on the final sequence:
		double finalSequenceNumber = sequence[0];

		// Create the term and chuck it in:
		Term lastTerm = new Term(finalSequenceNumber, 0); // exponent is 0. Maths.

		// Shit we out:
		return polynomial;
	}
}