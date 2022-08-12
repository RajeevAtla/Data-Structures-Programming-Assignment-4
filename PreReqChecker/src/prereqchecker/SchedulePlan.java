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
 * SchedulePlanInputFile name is passed through the command line as args[1]
 * Read from SchedulePlanInputFile with the format:
 * 1. One line containing a course ID
 * 2. c (int): number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * SchedulePlanOutputFile name is passed through the command line as args[2]
 * Output to SchedulePlanOutputFile with the format:
 * 1. One line containing an int c, the number of semesters required to take the
 * course
 * 2. c lines, each with space separated course ID's
 */

public class SchedulePlan {

    public static int[] bfs(Node[] courses, int startind)
    {
        boolean[] visited = new boolean[courses.length];
        int[] length = new int[courses.length];
        Queue<Integer> s = new LinkedList<Integer>();
        s.add(startind);
        visited[startind]=true;
        while(!s.isEmpty())
        {
            int parentind = s.remove();
            Node tmp = courses[parentind].next;
            while(tmp!=null)
            {
                int ind = getCourseIndex(courses,tmp.getData());
                s.add(ind);
                length[ind]=length[parentind]+1;
                visited[ind]=true;
                tmp=tmp.next;
            }
        }

        return length;
    }

    public static int getCourseIndex(Node[] courses, String course)
    {
        for(int i=0;i<courses.length;i++)
        {
            if(courses[i].getData().equals(course)) return i;
        }
        return -1;
    }
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.SchedulePlan <adjacency list INput file> <schedule plan INput file> <schedule plan OUTput file>");
            return;
        }

        StdIn.setFile(args[0]);

        int a = StdIn.readInt();
        StdIn.readLine();
        Node[] courses = new Node[a];

        for(int i=0;i<a;i++)
        {
            courses[i] = new Node(StdIn.readLine());
        }

        int b = StdIn.readInt();
        StdIn.readLine();
        for(int i=0;i<b;i++)
        {
            String target = StdIn.readString();
            String prereq = StdIn.readString();
            StdIn.readLine();
            for(int j=0;j<a;j++)
            {
                if(courses[j].getData().equals(target))
                {
                    Node append = courses[j].next;
                    courses[j].next = new Node(prereq);
                    courses[j].next.next=append;
                    break;
                }
            }
        }

        StdIn.setFile(args[1]);
        StdOut.setFile(args[2]);
        String goal = StdIn.readLine();
        int e = StdIn.readInt();
        StdIn.readLine();

        boolean[] taken = new boolean[a];

        for(int i=0;i<e;i++)
        {
            int ind = getCourseIndex(courses,StdIn.readLine());
            Stack<Integer> s = new Stack<Integer>();
            s.push(ind);
            while(!s.isEmpty())
            {
                int v = s.pop();
                if(taken[v]) continue;
                taken[v]=true;
                Node tmp = courses[v].next;
                while(tmp!=null)
                {
                    int n = getCourseIndex(courses,tmp.getData());
                    if(!taken[n]) s.push(n);
                    tmp=tmp.next;
                }
            }
        }

        boolean[] prereqs = new boolean[a];

        int ind = getCourseIndex(courses,goal);
        Stack<Integer> s = new Stack<Integer>();
        s.push(ind);
        while(!s.isEmpty())
        {
            int v = s.pop();
            if(prereqs[v]) continue;
            prereqs[v]=true;
            Node tmp = courses[v].next;
            while(tmp!=null)
            {
                int n = getCourseIndex(courses,tmp.getData());
                if(!prereqs[n]) s.push(n);
                tmp=tmp.next;
            }
        }

        boolean[] willtake = new boolean[a];

        for(int i=0;i<a;i++)
        {
            if(prereqs[i] && !taken[i]) willtake[i]=true;
        }

        int[] length = bfs(courses,getCourseIndex(courses, goal));

        int max = -1;
        for(int i=0;i<a;i++)
        {
            if(length[i]>max && willtake[i]) max=length[i];
        }

        StdOut.println(max);

        for(int i=max;i>0;i--)
        {
            for(int j=0;j<a;j++)
            {
                if(length[j]==i && willtake[j]) StdOut.print(courses[j].getData()+" ");
            }
            StdOut.println();
        }

    }
}
