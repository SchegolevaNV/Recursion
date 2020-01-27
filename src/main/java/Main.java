import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    private static final Marker CORRECT_PICTURE_MARKER = MarkerManager.getMarker("CORRECT_PICTURE");
    private static final Marker INVALID_PICTURE_MARKER = MarkerManager.getMarker("INVALID_PICTURE");
    private static final String URL = "https://skillbox.ru/media";

    public static void main(String[] args) throws IOException {

        Node root = new Node(URL);

        String list = new ForkJoinPool().invoke(new MapCreator(root));
        System.out.println(list);

    }
}
