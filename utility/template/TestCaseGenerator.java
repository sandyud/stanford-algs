// Be sure to put the appropriate package name
package utility.template;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;

import utility.ClassCaller;
import utility.AbstractTestCaseGenerator;

/**
 * An test case generator for courseX assignmentX.
 *
 * <p>Using this class's main method should be parameter driven. The convention is as follows:
 *
 * <p>java testCases.course4.assignment1AllPairsShortestPath [method to call] [solver class] {[args
 * to method]}
 *
 * <p>The [solver class] is used to ensure that test cases with unique solutions are produced.
 */
public class TestCaseGenerator extends AbstractTestCaseGenerator {
  public TestCaseGenerator(String methodToUse, String solverClassName, String[] args) {
    super(methodToUse, solverClassName, args);
  }

  /**
   * YOUR CODE GOES HERE. Simply add a non-static method with the following signature: <code>
   * public void yourMethodName(Method solverMainMethod, String[] args)</code> Within your method,
   * you need to do the following:
   *
   * <ol>
   *   <li>Create input files.
   *   <li>Find the solutions.
   *   <li>Call <code>AbstractTestCaseGenerator.generateOutputFile(inputFilename, solutionAsString)
   *       </code> for each input file.
   * </ol>
   *
   * @param solverMainMethod the main method that gives a solution with the given file
   * @param args the filenameStartingIndex, followed by an array of {WHAT DOES YOUR ARGUMENT ARRAY
   *     LOOK LIKE?}
   */
  public void yourMethodName(Method solverMainMethod, String[] args) {
    int filenameStartingIndex = Integer.parseInt(args[0]);

    // Iterate through the command line arguments
    int argPointer = 1;
    while (argPointer < args.length) {

      System.out.println("Processing problem size => " + args[argPointer]);

      // A set of solutions.
      // Used to ensure 4 unique test case files are generated.
      HashSet<String> solutions = new HashSet<String>();

      while (solutions.size() < 4) {

        // Initialize the list of file lines
        ArrayList<String> lines = new ArrayList<String>();

        // Create the filename for the test case.
        String inputFilename = "input_YOUR DESCRIPTION";
        inputFilename += "_" + filenameStartingIndex;
        inputFilename += "_" + "WHAT IS THE PROBLEM SIZE?";
        inputFilename += ".txt";

        // Write the input file.
        Path file = Paths.get(inputFilename);
        try {
          Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException exception) {
          exception.printStackTrace();
        }

        String[] filenames = {inputFilename};

        String solution = ClassCaller.callMethod(solverMainMethod, filenames);

        // create an output file for this solution only if it is a unique solution.
        if (!solutions.contains(solution)) {
          // Add this solution to the set of solutions so far.
          solutions.add(solution);
          AbstractTestCaseGenerator.generateOutputFile(inputFilename, solution);
          System.out.println("\t" + solutions.size() + " unique file(s) generated.");
          filenameStartingIndex++;
        }
      }
    }
  }
}
