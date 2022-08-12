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
 * AdjListOutputFile name is passed through the command line as args[1]
 * Output to AdjListOutputFile with the format:
 * 1. c lines, each starting with a different course ID, then 
 *    listing all of that course's prerequisites (space separated)
 */
public class AdjList {
    public static void main(String[] args) {

        if (args.length < 2) {
            StdOut.println("Execute: java -cp bin prereqchecker.AdjList <adjacency list INput file> <adjacency list OUTput file>");
            return;
        }

		HashMap<String, HashSet<String>> courses = populateGraph(args[0]);
		StdOut.setFile(args[1]);

		for(String course : courses.keySet()) {
			StdOut.print(course);
			for(String prereq : courses.get(course))
				StdOut.print(" " + prereq);
			StdOut.println();
		}
		
    }

	public static HashMap<String, HashSet<String>> populateGraph(String inputFile) {
		StdIn.setFile(inputFile);

		int a = StdIn.readInt();
        StdIn.readLine();
        HashMap<String, HashSet<String>> courses = new HashMap<String, HashSet<String>>();

        for(int i = 0; i < a; i++)
        {
			courses.put(StdIn.readLine(), new HashSet<String>());
        }

        int b = StdIn.readInt();
        StdIn.readLine();
		
        for(int i = 0; i < b; i++)
        {
            String target = StdIn.readString();
            String prereq = StdIn.readString();
            StdIn.readLine();

			HashSet<String> temp = courses.get(target);
			temp.add(prereq);
			courses.put(target, temp);
        }

		return courses;
	}
}
