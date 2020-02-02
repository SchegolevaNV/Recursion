import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ForkJoinPool;

public class Main {

    protected static final String URL = "https://skillbox.ru/";

    public static void main(String[] args) {

        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        TreeSet<String> visitedLinks = new TreeSet();
        Set<String> map = null;

        String root = URL;
        QueueOfLinks queueOfLinks = new QueueOfLinks(queue, visitedLinks);
        queueOfLinks.addLinkToQueue(root);
        queueOfLinks.setGeneralHost(root);

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
            Files.write(Paths.get("/home/ariwenn/Documents/map.txt"), mapToFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
