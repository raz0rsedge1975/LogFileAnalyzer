package fx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Eine Klasse für den Abruf von Geoinformationen von <a href="https://db-ip.com/">https://db-ip.com/</a>.
 * <p>
 * Die freie API hat ein Aufruflimit von 1_000 Abrufen pro Tag und Client, also sparsam verwenden!
 * Wann immer möglich, Batch Queries nutzen, die geben 10% Rabatt (d.h. von 10 IPs in der Batch Query werden 9 gegen das Limit gerechnet).
 */
public class IPLocator {


    //FSBwIT-Proxy-Kram
    private static final String PROXY_USERNAME = "<Your username here>";
    private static final String PROXY_PASSWORD = "<Your password here>";

    //URL des REST-APIs : https://db-ip.com/api/doc.php
    private static final String BASE_URL = "http://api.db-ip.com/v2/free/";

    //Konstanten für das Parsen der JSON-Antwort
    /**
     * IP-Adresse
     */
    public static final String IP_ADRESS = "ipAddress";

    /**
     * Kontinent-Code nach ISO 3166-1
     */
    public static final String CONTINENT_CODE = "continentCode";

    /**
     * Name des Kontinents
     */
    public static final String CONTINENT_NAME = "continentName";

    /**
     * Ländercode nach ISO 3166-1
     */
    public static final String COUNTRY_CODE = "countryCode";

    /**
     * Name des Landes
     */
    public static final String COUNTRY_NAME = "countryName";

    /**
     * Name des Bundesstaates/Provinz
     */
    public static final String STATE_PROVINCE = "stateProv";

    /**
     * Name der Stadt
     */
    public static final String CITY = "city";

    /**
     * Liste aller Konstanten
     */
    public static final List<String> ALL_CONSTANTS = List.of(IP_ADRESS, CONTINENT_CODE, CONTINENT_NAME, COUNTRY_CODE, COUNTRY_NAME, STATE_PROVINCE, CITY);

    /**
     * Erzeugt eine Instanz. Hier muss das Proxy-Zeug stattfinden.
     */
    public IPLocator() {

        //todo Proxy-Zeug testen und ggf. anpassen
//        System.setProperty("http.auth.tunneling.disablesSchemes", "");
//        System.setProperty("http.proxyHost", "svfw006.fsbwit.intern");
//        System.setProperty("http.proxyPort", "9000");
//        System.setProperty("https.proxyHost", System.getProperty("http.proxyHost"));
//        System.setProperty("https.proxyPort", System.getProperty("http.proxyPort"));
//
//        Authenticator.setDefault(new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(PROXY_USERNAME, PROXY_PASSWORD.toCharArray());
//            }
//        });
    }

    /**
     * Lokalisiert eine als String übergebene <code>ip</code>.
     *
     * @param ip die IP-Adresse
     * @return ein JSON-String mit der Antwort
     */
    public String locate(String ip) {
        return doLocate(ip);
    }

    /**
     * Lokalisiert mehrere als Liste von Strings übergebene <code>ips</code>.
     *
     * @param ips die Liste mit IP-Adressen
     * @return ein JSON-String mit der Antwort
     */
    public String locate(List<String> ips) {
        return doLocate(list2String(ips));
    }

    /**
     * Macht die eigentliche Lokalisierungsarbeit.
     *
     * @param ips die IP oder die IPs
     * @return ein JSON-formatierter String mit der Antwort
     * @see IPLocator#list2String(List)
     */
    private String doLocate(String ips) {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader bfr = new BufferedReader(
                new InputStreamReader(
                        new URL(BASE_URL + ips).openStream()))) {
            String line = "";
            while ((line = bfr.readLine()) != null) {
                if (!line.isBlank()) {
                    sb.append(line.strip());
                    sb.append(System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.err.printf("Verbindung konnte nicht hergestellt werden: %s%n", e.getLocalizedMessage());
        }
        return sb.toString();
    }

    /**
     * Bereitet eine Liste von IPs für die Kommunikation mit der REST-API auf.
     *
     * @param ips eine Liste mit mindestens einer IP
     * @return einen String, korrekt formatiert für die API
     * @throws IllegalArgumentException wenn die Liste leer ist
     */
    private String list2String(List<String> ips) {
        if (ips.isEmpty()) {
            throw new IllegalArgumentException("Mindestens eine IP angeben!");
        }
        if (ips.size() == 1) {
            return ips.get(0);
        }
        return ips.stream().collect(Collectors.joining(",", "{", "}"));
    }
}
