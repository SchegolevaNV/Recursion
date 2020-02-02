import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.concurrent.RecursiveTask;

public class MapCreator extends RecursiveTask<TreeSet<String>> {

    private QueueOfLinks queueOfLinks;

    public MapCreator(QueueOfLinks queueOfLinks) {
        this.queueOfLinks = queueOfLinks;
    }

    @Override
    protected TreeSet<String> compute()
    {
        Set<String> buffer = Collections.synchronizedSet(new HashSet<>());
        List<MapCreator> taskList = new ArrayList<>();

        String generalHost = queueOfLinks.getGeneralHost();
        String root = queueOfLinks.getQueue().poll();

        if (root == null)
        {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            root = queueOfLinks.getQueue().poll();
        }

        if (root != null)
        {

            Document doc = null;
            try {
                Thread.sleep(1000);
                doc = Jsoup.connect(root).maxBodySize(0).get();
            } catch (Exception e) {
                e.getMessage();
            }

            if (doc != null)
            {
                queueOfLinks.getVisitedLinks().add(root);

                Elements elements = doc.select("a[abs:href$=/]");

                for (Element element : elements)
                {
                    String child = element.absUrl("href");
                    if (child.startsWith(generalHost)
                            && !queueOfLinks.getVisitedLinks().contains(child)
                            && !queueOfLinks.getQueue().contains(child))
                    {
                        queueOfLinks.addLinkToQueue(child);
                        buffer.add(child);
                    }
                }

                for (int i = 0; i < buffer.size(); i++) {
                    MapCreator task = new MapCreator(queueOfLinks);
                    task.fork();
                    taskList.add(task);
                }

                for (MapCreator task : taskList)
                {
                    task.join();
                }
            }
        }
        return queueOfLinks.getVisitedLinks();
    }
}
