import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.RecursiveTask;

public class MapCreator extends RecursiveTask<TreeSet<String>> {

    private Node node;
    private TreeSet<String> map = new TreeSet<>();

    public MapCreator(Node node) {
        this.node = node;
    }

    @Override
    protected TreeSet<String> compute() {

        String root = node.getValue();
        List<MapCreator> taskList = new ArrayList<>();

        Document doc = null;
        try {
            Thread.sleep(1000);
            doc = Jsoup.connect(root).maxBodySize(0).get();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        Elements elements = doc.select("a[abs:href^=https://skillbox.ru]").select("a[abs:href$=/]");

        for (Element element : elements) {
            String child = element.absUrl("href");
            if (addUrl(child)
                    && !child.equals("https://skillbox.ru/media/topic//")
                    && !child.equals("https://skillbox.ru/media/authors/eugenya-sycheva/")) {
                node.children.add(new Node(child));
            }
        }

        for (Node child : node.getChildren()) {
            map.add(child.getValue());
        }

        for (Node child : node.getChildren()) {
            MapCreator task = new MapCreator(child);
            task.fork();
            taskList.add(task);
        }

        for (MapCreator task : taskList)
        {
            map.addAll(task.join());
        }

        return map;
    }

    private synchronized boolean addUrl (String url) {
        return Main.copyUrl.add(url);
    }
}
