import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;

public class DirectedGraph {
    private LinkedList[] adjacencyList = null;
    private static Integer VERTICES;

    public DirectedGraph(Integer numerOfVertices) {
        VERTICES = numerOfVertices;
        adjacencyList = new LinkedList[VERTICES];
    }

    public void DFSUtil(Integer v, LinkedList<Integer> visited) {
        visited.add(v);
        System.out.print(v + "->");
        LinkedList<Integer> adjancyNodes = adjacencyList[v];
        adjancyNodes.stream().forEach(x -> {
            if (!visited.contains(x)) {
                DFSUtil(x, visited);
            }
        });
    }

    public Boolean isCyclic() {
        LinkedList<Integer> visited = new LinkedList<Integer>();
        for (Integer i = 0; i < VERTICES; i++)
            if (!visited.contains(i))
                if (isCyclicUtil(i, visited, -1))
                    return true;

        return false;
    }

    public void topologicalSortUtil(Integer v, LinkedList<Integer> visited, Stack<Integer> stack) {
        visited.add(v);
        LinkedList<Integer> adjancyNodes = adjacencyList[v];
        if (adjancyNodes != null)
            adjancyNodes.stream().forEach(x -> {
                if (!visited.contains(x)) {
                    topologicalSortUtil(x, visited, stack);
                }
            });
        stack.push(v);
    }

    public void topologicalSort() {
        LinkedList<Integer> visited = new LinkedList<Integer>();
        System.out.println("Following is a Topological sort of the given graph");
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < VERTICES; i++)
            if (!visited.contains(i))
                topologicalSortUtil(i, visited, stack);
        while (!stack.empty())
            System.out.print(stack.pop() + " ");

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

    public void DFS(Integer v) {
        LinkedList<Integer> visited = new LinkedList<Integer>();
        System.out.println("DFS Traversing");
        DFSUtil(v, visited);

    }

    public void BFS(Integer startingVertice) {
        LinkedList<Integer> visited = new LinkedList<Integer>();
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(startingVertice);
        visited.add(startingVertice);
        System.out.println("BFS Traversing");

        while (!queue.isEmpty()) {
            Integer temp = queue.poll();
            System.out.print("->" + temp);
            LinkedList<Integer> list = adjacencyList[temp];
            if (list != null)
                list.stream().filter(it -> it != null).forEach(item -> {
                    if (!visited.contains(item)) {
                        queue.add(item);
                        visited.add(item);
                    }
                });
        }
    }

    public void display() {
        int[] source = {1};
        System.out.println("Directed Graph using adjacency List");
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
        DirectedGraph graph = new DirectedGraph(6);
        graph.addEdge(1, 2);

        graph.addEdge(1, 3);
        graph.addEdge(1, 4);

        graph.addEdge(2, 3);
        graph.addEdge(3, 2);

        graph.addEdge(4, 4);
        graph.addEdge(5, 2);

        graph.display();
        graph.DFS(1);
    }
}
