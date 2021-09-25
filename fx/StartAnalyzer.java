package fx;

import analyzer.HTTPCommandAnalyzer;
import analyzer.HttpAnalyzer;
import analyzer.IPAnalyzer;
import analyzer.UserAgentAnalyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class StartAnalyzer {

    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";

    public static void main(String[] args) {
        //Input file from project root
        String path = System.getProperty("user.dir");
        String filename = "ssl_access.log";
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(path + "\\" + filename))) {
            String line;
            while ((line = br.readLine()) != null){
                sb.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //HTTP-Protokoll-Versionen Analyse
        HttpAnalyzer httpanalyzer = new HttpAnalyzer();
        HTTPCommandAnalyzer httpcommand = new HTTPCommandAnalyzer();
        UserAgentAnalyzer useragent = new UserAgentAnalyzer();
        IPAnalyzer ipaddress = new IPAnalyzer();

        System.out.println(ANSI_BLUE + httpanalyzer.analyze(sb.toString()));
        System.out.println(ANSI_GREEN + httpcommand.analyze(sb.toString()));
        System.out.println(ANSI_PURPLE + useragent.analyze(sb.toString()));
        System.out.println(ANSI_BLACK + ipaddress.analyze(sb.toString()));
    }
}
