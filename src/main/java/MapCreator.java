import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MapCreator extends RecursiveTask<String> {

    private Node node;

    public MapCreator(Node node) {
        this.node = node;
    }

    @Override
    protected String compute() {

        String root = node.getValue();
        List<MapCreator> taskList = Collections.synchronizedList(new ArrayList<>());

        try {
            for (Node child : node.getChildren())
                {
                    MapCreator task = new MapCreator(child);
                    task.fork();
                    taskList.add(task);
                }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (MapCreator task : taskList)
        {
            root = root.concat(task.join());
        }

        return root;
   }
}
