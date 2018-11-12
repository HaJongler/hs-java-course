import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MultiThreadCrawler {

    public LinkedList<Webpage> toVisit = new LinkedList<>();
    public ConcurrentHashMap<URL, String> alreadyVisited = new ConcurrentHashMap<>();

    public MultiThreadCrawler(String[] webPages) {
        for (String page : webPages) {
            try {
                URL urlAddress = new URL(page);
                toVisit.offer(new Webpage(urlAddress));
            } catch (MalformedURLException e) {
            }
        }
    }

    public static void main(String[] args) {

        MultiThreadCrawler crawler = new MultiThreadCrawler(args);
        while (!crawler.toVisit.isEmpty()) {
            ExecutorService table = Executors.newSingleThreadExecutor();
            table.execute(crawler.toVisit.poll());
            try {
                Thread.sleep(5000);
            }
            catch (InterruptedException e) {
                //s
            }
        }
        System.out.println(crawler.alreadyVisited.keySet().toString());
    }

    public class Webpage implements Runnable {

        public URL url;

        public Webpage(URL url) {
            this.url = url;
        }

        private void extractUrls(String text) {
            String urlRegex = "((https?):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
            Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
            Matcher urlMatcher = pattern.matcher(text);

            while (urlMatcher.find()) {
                String address = text.substring(urlMatcher.start(0), urlMatcher.end(0));
                try {
                    URL urlAddress = new URL(address.replace("http:", "https:"));
                    // This is required for the HW website or else I get HTTP 301 response
                    if (alreadyVisited.containsKey(urlAddress)) continue;
                    toVisit.add(new Webpage(urlAddress));
                } catch (MalformedURLException e) {
                    System.out.println("Bad");
                }
            }
        }

        public String getContentOfWebPage(URL url) {
            final StringBuilder content = new StringBuilder();

            try (InputStream is = url.openConnection().getInputStream();
                 InputStreamReader in = new InputStreamReader(is, "UTF-8");
                 BufferedReader br = new BufferedReader(in);) {
                String inputLine;
                while ((inputLine = br.readLine()) != null)
                    content.append(inputLine);
            } catch (IOException e) {
                System.out.println("Failed to retrieve content of " + url.toString());
                e.printStackTrace();
            }
            System.out.println(content.toString());
            return content.toString();
        }

        @Override
        public void run() {
            alreadyVisited.put(this.url, "OK");
            String siteText = getContentOfWebPage(this.url);
            extractUrls(siteText);
        }
    }
}
