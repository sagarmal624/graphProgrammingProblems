import java.util.Arrays;
import java.util.LinkedList;

public class UnDirectedGraph {
    private LinkedList[] adjacencyList = null;
    private static Integer VERTICES;

    public UnDirectedGraph(Integer numerOfVertices) {
        VERTICES = numerOfVertices;
        adjacencyList = new LinkedList[VERTICES];
    }

    public Boolean isCyclic() {
        LinkedList<Integer> visited = new LinkedList<Integer>();
        for (Integer i = 0; i < VERTICES; i++)
            if (!visited.contains(i))
                if (isCyclicUtil(i, visited, -1))
                    return true;

        return false;
    }

    public Boolean isCyclicUtil(Integer v, LinkedList<Integer> visited, Integer parent) {
        visited.add(v);
        LinkedList<Integer> lst = adjacencyList[v];
        if (lst != null) {
            for (Integer item : lst) {
                if (!visited.contains(item)) {
                    if (isCyclicUtil(item, visited, v))
                        return true;
                } else if (item.equals(parent))
                    return true;
            }
        }
        return false;
    }

    public void display() {
        int[] source = {0};
        System.out.println("Graph");
        Arrays.stream(adjacencyList).filter(it -> it != null).forEach(x -> {
            System.out.print(source[0]++ + "=>");
            x.stream().forEach(item -> System.out.print("->" + item));
            System.out.println();
        });
    }

    public void addEdge(Integer src, Integer dest) {
        if (adjacencyList[src] == null) {
            LinkedList linkedList = new LinkedList();
            linkedList.add(dest);
            adjacencyList[src] = linkedList;
        } else {
            adjacencyList[src].add(dest);
        }
    }

    public static void main(String[] args) {
        UnDirectedGraph g1 = new UnDirectedGraph(5);
        g1.addEdge(1, 0);
        g1.addEdge(0, 2);
        g1.addEdge(2, 0);
        g1.addEdge(0, 3);
        g1.addEdge(3, 4);
        g1.display();

        if (g1.isCyclic())
            System.out.println("Graph contains cycle");
        else
            System.out.println("Graph doesn't contains cycle");


    }
}
