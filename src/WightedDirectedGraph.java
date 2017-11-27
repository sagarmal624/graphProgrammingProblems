import java.util.*;

public class WightedDirectedGraph {
    private Node[] nodes = null;
    private static Integer VERTICES;

    public WightedDirectedGraph(Integer numerOfVertices) {
        VERTICES = numerOfVertices;
        nodes = new Node[numerOfVertices];
    }

    public void maxDistancePath() {
        Double max = Double.MIN_VALUE;
        String from = null, src = null, destination = null;
        for (int i = 1; i < VERTICES - 1; i++) {
            from = nodes[i].getLabel();
            for (Edge edge : nodes[i].getEdges()) {
                if (max < edge.getWeight()) {
                    max = edge.getWeight();
                    destination = edge.getDestination().getLabel();
                    src = nodes[i].getLabel();
                }
            }
        }
        System.out.println(src + "--|" + max + "|--" + destination);

    }

    public void topologicalSortUtil(Integer i, LinkedList<String> visited, Stack<Node> stack) {
        Node node = nodes[i];
        if (node != null) {
            visited.add(node.getLabel());
            List<Edge> edges = node.getEdges();
            if (edges != null) {
                for (Edge edge : edges) {
                    if (!visited.contains(edge.getDestination().getLabel()))
                        topologicalSortUtil(Integer.parseInt(edge.getDestination().getLabel()), visited, stack);
                }
            }
            stack.push(node);
        }
    }

    public void topologicalSort() {
        LinkedList<String> visited = new LinkedList<String>();
        Stack<Node> stack = new Stack<Node>();
        for (int i = 1; i < VERTICES - 1; i++) {
            topologicalSortUtil(i, visited, stack);
        }
        while (!stack.isEmpty()) {
            System.out.println(stack.pop().getLabel());
        }
    }


    public void display() {
        for (int i = 1; i < VERTICES - 1; i++) {
            String from = nodes[i].getLabel();
            nodes[i].getEdges().stream().forEach(item -> {
                System.out.print(from + "--|" + item.getWeight() + "|--" + item.getDestination().getLabel() + " ,");
            });
            System.out.println();
        }
    }

    public Edge addEdge(Node dest, Integer weight) {
        Edge edge = new Edge();
        edge.setWeight(weight);
        edge.setDestination(dest);
        return edge;
    }

    public void createNode(Integer src, Integer dest, Integer weight) {
        Node srcNode = nodes[src];
        if (srcNode == null) {
            srcNode = new Node();
            srcNode.setLabel("" + src);
            nodes[src] = srcNode;
        }
        Node destNode = new Node();
        destNode.setLabel("" + dest);

        List<Edge> edges = srcNode.getEdges();
        if (edges == null)
            edges = new ArrayList<>();
        edges.add(addEdge(destNode, weight));
        srcNode.setEdges(edges);
    }

    public static void main(String[] args) {
        WightedDirectedGraph graph = new WightedDirectedGraph(5);
        graph.createNode(1, 2, 5);
        graph.createNode(1, 3, 4);
        graph.createNode(2, 4, 1);
        graph.createNode(3, 4, 2);
//        graph.display();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        graph.topologicalSort();
    }
}
