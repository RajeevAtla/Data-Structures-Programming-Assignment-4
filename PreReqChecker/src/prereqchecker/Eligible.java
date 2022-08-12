package prereqchecker;

import java.util.*;

/**
 * 
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
 * EligibleInputFile name is passed through the command line as args[1]
 * Read from EligibleInputFile with the format:
 * 1. c (int): Number of courses
 * 2. c lines, each with 1 course ID
 * 
 * Step 3:
 * EligibleOutputFile name is passed through the command line as args[2]
 * Output to EligibleOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class Eligible {
    public static void main(String[] args) {

        if (args.length < 3) {
            StdOut.println("Execute: java -cp bin prereqchecker.Eligible <adjacency list INput file> <eligible INput file> <eligible OUTput file>");
            return;
        }

		HashMap<String, HashSet<String>> courses = AdjList.populateGraph(args[0]);
		HashSet<String> taken = readToHashSet(args[1]);
		HashSet<String> flat = convertToFlat(taken, courses);
		HashSet<String> canTake = new HashSet<String>();
		
		for(String course: courses.keySet()) {
			boolean takeable = !flat.contains(course);
			HashSet<String> prereqs = courses.get(course);
			
			for(String prereq: prereqs) {
				if(!flat.contains(prereq)) {
					takeable = false;
				}
			}
			if(takeable) {
				canTake.add(course);
			}
		}

		writeFromHashSet(canTake, args[2]);
    }

	public static HashSet<String> readToHashSet(String file) {
		HashSet<String> taken = new HashSet<String>();
		StdIn.setFile(file);
		
		int c = StdIn.readInt();
		StdIn.readLine();
		for(int i = 0; i < c; i++) {
			String course = StdIn.readLine();
			taken.add(course);
		}

		return taken;
	}

	public static HashSet<String> convertToFlat(HashSet<String> taken, HashMap<String, HashSet<String>> courses) {
		HashSet<String> flat = new HashSet<String>();
		Queue<String> q = new LinkedList<String>();
		
		for(String course : taken) {
			q.add(course);
		}

		while(q.size() > 0) { // essentially BFS
			String course = q.remove();
			if(!flat.contains(course)) {
				flat.add(course);
				for(String prereq: courses.get(course)) {
					q.add(prereq);
				}
			}
		}
		
		return flat;
	}

	public static void writeFromHashSet(HashSet<String> canTake, String file) {
		StdOut.setFile(file);

		for(String course : canTake) {
			StdOut.println(course);
		}
	}

	
}
