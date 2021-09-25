package analyzer;

import fx.IPLocator;

import java.util.*;

public class IPAnalyzer implements Analyzer{
    @Override
    public String analyze(String logContent) {
        IPLocator ipLocator = new IPLocator();
        StringBuilder sb = new StringBuilder("IP-Adress-Info:\n----------------------\n");
        String jsonText = null;
        int patternGroup = 1;
        long lines = 0;
        Map<String, Integer> outMap = new TreeMap<>();

        //Analyze Ip-Adresses
        lines = UserAgentAnalyzer.getLines(logContent, patternGroup, lines, outMap);
        analyzer.ValueComparator comparator = new analyzer.ValueComparator(outMap);
        SortedMap<String, Integer> sMap = new TreeMap<>(comparator);
        sMap.putAll(outMap);
        List<String> ipList = new ArrayList<>(sMap.keySet()); //fuer bulk uebergabe aber funzt nicht, 1. und letzte ip wird nicht erkannt.
        //String jsonRawString = ipLocator.locate(ipList); //is gar kein richtiges JSON!!1!

        for (Map.Entry<String, Integer> entry : sMap.entrySet()) {
            jsonText = ipLocator.locate(entry.getKey());
            String infoString = jsonText.split("^(\".?)(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])(}?\": .)$", 1)[0]; //IP aus Antwort wegop

            String stateString = infoString.split(",",10)[5].replace("[\n]","").replace("\"","").replace("}","").trim().strip(); //state
            String cityString = infoString.split(",",10)[6].replace("[\n]","").replace("\"","").replace("}","").trim().strip(); //city
            String countryString = infoString.split(",",10)[4].replace("[\n]","").replace("\"","").replace("}","").trim().strip(); //country

            sb.append(entry.getKey()).append(" : ").append(entry.getValue())
                    .append(" (").append(String.format("%.2f", ((double) entry.getValue() / lines * 100.)))
                    .append("%)").append(" -> ").append(stateString)
                    .append(" -> ").append(cityString)
                    .append(" -> ").append(countryString)
                    .append(System.lineSeparator());
        }
        sb.append("---------------EOF-----------------\n");

        return sb.toString();
    }
}
