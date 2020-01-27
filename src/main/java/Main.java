import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.concurrent.ForkJoinPool;

public class Main {

    protected static HashSet<String> copyUrl = new HashSet<>();
    protected static final String URL = "https://skillbox.ru/";

    public static void main(String[] args) {

        Node root = new Node(URL);

        TreeSet<String> map = new ForkJoinPool().invoke(new MapCreator(root));
        map.add(URL);

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
