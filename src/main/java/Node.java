import java.util.ArrayList;
import java.util.List;

public class Node {

    private String url;
    public List<Node> children = new ArrayList<>();

    public Node(String url) {
        this.url = url;
    }

    public List<Node> getChildren() {
        return children;
    }

    public String getValue() {
        return url;
    }
}
