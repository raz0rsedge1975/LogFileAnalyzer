package analyzer;

public interface Analyzer {
    public String analyze (String logContent);

    public static String pattern(){
//        return "(^(\\d{1,3}\\.){3}\\d{1,3}).+(\\[.+\\])\\s\\W([A-Z]{3,4}) \\/.+(HTTP\\/\\d\\.?\\d?).+(\\\".+\\\")$";
        return "(^(\\d{1,3}\\.){3}\\d{1,3}).+\\[" +     //erste Gruppe, sucht nach IP-Adressen
                "(.+)\\]\\s\\W" +                       //dritte Gruppe, extrahiert das Datum ohne []
                "([A-Z]{3,4}) " +                       //vierte Gruppe, extrahiert die Zugriffsmethode, String muss Großbuchstaben enthalten, zwischen 3 & 4 Zeichen, gibt auch mehr als 4, z.B, REPORT o. PROPFIND
                "(\\/.*?)(?=HTTP\\/\\d\\.?\\d?)?\\s" +  //extrahiert den Pfad auf den zugegriffen wurde, beliebige Zeichenkette VOR dem HTTP-Protokoll
                "(HTTP\\/\\d\\.?\\d?)\\W " +            //extrahiert die HTTP Protokoll Version
                "(\\d+)\\s" +                           //extrahiert den Status Code
                "(\\d+)\\s" +                           //extrahiert die übertragenen Bytes
                "(\".+\"\\s)?" +                        //extrahiert den Referer, also woher wir kommen (wenn übermittelt)
                "\"(.+)\"$";                            //extrahiert den UserAgent ohne "" & markiert das Ende der Zeile. \n
        /*
        * "Masterpattern" für die ganze Zeile, teilt in folgende Groups:
        * Group 1: Ip-Adresse
        * Group 2: - (sinnlos, nur 3. Oktet der IP-Adresse)
        * Group 3: Datum
        * Group 4: Access-Method (POST|GET|HEAD|PUT|...)
        * Group 5: Zugegriffen auf Pfad
        * Group 6: HTTP Protokoll-Version
        * Group 7: HTTP Status Code
        * Group 8: übertragene Bytes
        * Group 9: Referer - woher kommt der User Achtung: " noch vorhanden, müssen gestripped werden
        * Group 10: UserAgent Information
        * */
    }
}
