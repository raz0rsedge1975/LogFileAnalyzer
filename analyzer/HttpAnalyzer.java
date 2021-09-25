package analyzer;

import java.util.Map;
import java.util.TreeMap;

public class HttpAnalyzer implements Analyzer{
    @Override
    public String analyze(String logContent) {
        StringBuilder sb = new StringBuilder("HTTP-Protokoll Info:\n----------------------\n");
        int patternGroup = 6;
        Map<String, Integer> outMap = new TreeMap<>();
        //Analyze Http-Versions
        long lines = 0;
        lines = UserAgentAnalyzer.getLines(logContent, patternGroup, lines, outMap);


        for (Map.Entry<String, Integer> entry : outMap.entrySet()) {
            sb.append(entry.getKey()).append(" : ").append(entry.getValue())
            .append(" (").append(String.format("%.2f",((double)entry.getValue() / lines * 100.))).append("%)") //percentage
            .append(System.lineSeparator());
        }
        sb.append("---------Ende---------\n");

        return sb.toString();
    }

}
