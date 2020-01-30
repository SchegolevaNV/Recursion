import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ForkJoinPool;

public class Main {

    protected static final String URL = "https://skillbox.ru/";

    public static void main(String[] args) {

        String root = URL;
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        TreeSet<String> visitedLinks = new TreeSet();
        QueueOfLinks queueOfLinks = new QueueOfLinks(queue, visitedLinks);
        Set<String> map = null;

        queueOfLinks.addLinkToQueue(root);
        queueOfLinks.addToVisitedLinks(root);

        if (queueOfLinks.getQueue().size() != 0)
        {
            map = new ForkJoinPool().invoke(new MapCreator(queueOfLinks));
        }

        ArrayList<String> mapToFile = new ArrayList<>();
        map.forEach(link -> {
            int count = (int) link.chars().filter(num -> num == '/').count();
            for (int i = 0; i < count - 3 ; i++) {
                link = "\t".concat(link);
            }
            mapToFile.add(link);
        });
        try {
            Files.write(Paths.get("C:/Users/SchegolevaNV2/Desktop/map.txt"), mapToFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
