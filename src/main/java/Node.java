import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Node {

    private String url;

    public Node(String url) {
        this.url = url;
    }

    public List<Node> getChildren() throws IOException {

        List<Node> linkCollection = Collections.synchronizedList(new ArrayList<>());

        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.select("a[href^=https://skillbox.ru]").select("a[href$=/]");

        for (Element element : elements) {
            linkCollection.add(new Node(element.attr("href")));
        }

        return linkCollection;
    }

    public String getValue() {

        return url;
    }
}
