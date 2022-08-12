package prereqchecker;

import java.util.*;

/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * AdjListInputFile name is passed through the command line as args[0]
 * Read from AdjListInputFile with the format:
 * 1. a (int): number of courses in the graph
 * 2. a lines, each with 1 course ID
 * 3. b (int): number of edges in the graph
 * 4. b lines, each with a source ID
 * 
 * Step 2:
 * ValidPreReqInputFile name is passed through the command line as args[1]
 * Read from ValidPreReqInputFile with the format:
 * 1. 1 line containing the proposed advanced course
 * 2. 1 line containing the proposed prereq to the advanced course
 * 
 * Step 3:
 * ValidPreReqOutputFile name is passed through the command line as args[2]
 * Output to ValidPreReqOutputFile with the format:
 * 1. 1 line, containing either the word "YES" or "NO"
 */
public class ValidPrereq {
	public static void main(String[] args) {

		if (args.length < 3) {
			StdOut.println(
					"Execute: java -cp bin prereqchecker.ValidPrereq <adjacency list INput file> <valid prereq INput file> <valid prereq OUTput file>");
			return;
		}
		HashMap<String, HashSet<String>> courses = AdjList.populateGraph(args[0]);

		StdIn.setFile(args[1]);
		String course1 = StdIn.readLine();
		String course2 = StdIn.readLine();

		boolean valid;

		if (course1 == course2) {
			valid = false;
		} else {
			valid = checkPrereqs(courses, course1, course2);
		}

		StdOut.setFile(args[2]);
		if (valid) {
			StdOut.print("YES");
		} else {
			StdOut.print("NO");
		}

	}

	// check if course 2 is in the prerequisites for course 1
	public static boolean checkPrereqs(HashMap<String, HashSet<String>> courses, String course1, String course2) {
		if (course1.equals(course2)) {
			return true;
		} else {
			for (String c : courses.get(course1)) {
				if (checkPrereqs(courses, c, course2)) {
					return true;
				}
			}
		}

		return false;
	}

}
