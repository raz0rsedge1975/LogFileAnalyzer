package analyzer;

import java.util.Map;
import java.util.TreeMap;

public class HTTPCommandAnalyzer implements Analyzer{

    @Override
    public String analyze(String logContent) {
        StringBuilder sb = new StringBuilder("HTTP-Command Info:\n----------------------\n");
        int patternGroup = 4;
        long lines = 0;
        Map<String, Integer> outMap = new TreeMap<>();
        int counter = outMap.getOrDefault("none", 0);
        //Analyze Http-Commands
        lines = UserAgentAnalyzer.getLines(logContent, patternGroup, lines, outMap);


        for (Map.Entry<String, Integer> entry : outMap.entrySet()) {
            sb.append(entry.getKey()).append(" : ").append(entry.getValue())
                    .append(" (").append(String.format("%.2f",((double)entry.getValue() / lines * 100.))).append("%)")
                    .append(System.lineSeparator());
        }
        sb.append("----------Ende--------\n");

        return sb.toString();
    }
}
