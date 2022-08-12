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
 * NeedToTakeInputFile name is passed through the command line as args[1]
 * Read from NeedToTakeInputFile with the format:
 * 1. One line, containing a course ID
 * 2. c (int): Number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * NeedToTakeOutputFile name is passed through the command line as args[2]
 * Output to NeedToTakeOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class NeedToTake {
    public static void main(String[] args) {

        if (args.length < 3) {
            StdOut.println("Execute: java NeedToTake <adjacency list INput file> <need to take INput file> <need to take OUTput file>");
            return;
        }

		HashMap<String, HashSet<String>> courses = AdjList.populateGraph(args[0]);

		StdIn.setFile(args[1]);
		String target = StdIn.readLine();
		HashSet<String> taken = Eligible.readToHashSet(args[1], true);
		HashSet<String> flat = Eligible.convertToFlat(taken, courses);

		HashSet<String> totalNeedToTake = new HashSet<String>();
		totalNeedToTake.add(target);
		totalNeedToTake = Eligible.convertToFlat(totalNeedToTake, courses);

		HashSet<String> needToTake = new HashSet<String>();
		for(String course : totalNeedToTake) {
			boolean takeable = !flat.contains(course) && !course.equals(target);
			if(takeable) needToTake.add(course);
		}
		Eligible.writeFromHashSet(needToTake, args[2]);
    }
}
