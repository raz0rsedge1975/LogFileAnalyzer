package analyzer;

import java.util.Map;
import java.util.TreeMap;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserAgentAnalyzer implements Analyzer{
    @Override
    public String analyze(String logContent) {
        StringBuilder sb = new StringBuilder("\nUserAgent Stats Info:\n----------------------\n");
        int patternGroup = 10;
        long lines = 0;
        Map<String, Integer> outMap = new TreeMap<>();
        Map<String, Integer> tmpMap = new TreeMap<>();

        //Analyze User Agents
        lines = getLines(logContent, patternGroup, lines, outMap);

        for (Map.Entry<String, Integer> entry : outMap.entrySet()){
            if (Pattern.compile("[Bb]ot").matcher(entry.getKey()).find()) {
                tmpMap.put("Bots", tmpMap.getOrDefault("Bots", 0) + 1);
                //System.out.println(entry.getKey());
            }
            else if (Pattern.compile("[Mm]obile").matcher(entry.getKey()).find())
                tmpMap.put("Mobile", tmpMap.getOrDefault("Mobile", 0)+1);
            else if (Pattern.compile("Mozilla|Opera|Firefox|Chrome|Safari").matcher(entry.getKey()).find())
                tmpMap.put("Normal", tmpMap.getOrDefault("Normal", 0)+1);
            else {
                tmpMap.put("Sonstige", tmpMap.getOrDefault("Sonstige", 0) + 1);
                System.out.print("Sonstige Agents: ");
                System.out.println(entry.getKey());
            }
        }

        for (Map.Entry<String, Integer> entry : tmpMap.entrySet()) {
            sb.append(entry.getKey()).append(" : ").append(entry.getValue())
                    .append(" (").append(String.format("%.2f",((double)entry.getValue() / lines * 100.))).append("%)")
                    .append(System.lineSeparator());
        }
        sb.append("----------Ende--------\n");

        return sb.toString();
    }

    static long getLines(String logContent, int patternGroup, long lines, Map<String, Integer> outMap) {
        Pattern p = Pattern.compile(Analyzer.pattern(), Pattern.MULTILINE);
        Matcher m = p.matcher(logContent);
        while (m.find()){
            MatchResult mr = m.toMatchResult();
            outMap.put(mr.group(patternGroup), outMap.getOrDefault(mr.group(patternGroup),0)+ 1);
            lines++;
        }
        return lines;
    }
}
