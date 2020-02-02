import java.util.TreeSet;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueOfLinks {

      private ConcurrentLinkedQueue<String> queue;
      private TreeSet<String> visitedLinks;
      private String generalHost;

    public QueueOfLinks(ConcurrentLinkedQueue<String> queue, TreeSet<String> visitedLinks) {
        this.queue = queue;
        this.visitedLinks = visitedLinks;
    }

    public ConcurrentLinkedQueue<String> getQueue() {
        return queue;
    }

    public TreeSet<String> getVisitedLinks() {
        return visitedLinks;
    }

    public void addLinkToQueue(String link) {
        queue.add(link);
    }

    public void setGeneralHost(String generalHost)
    {
        this.generalHost = generalHost;
    }

    public String getGeneralHost()
    {
        return generalHost;
    }
}
