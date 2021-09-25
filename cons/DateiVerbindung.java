package cons;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import fx.StartAnalyzer;
import javafx.scene.image.Image;

public class DateiVerbindung {

    private static final File jarfile = new File(StartAnalyzer.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    private DateiVerbindung(){}

    public static List<String> liesZeilenweise(String dateiname){
        List<String> listOut = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(dateiname))){
            String line;
            while ((line = br.readLine()) != null){
                listOut.add(line);
            }
        } catch (IOException e){
            System.out.println(dateiname);
            e.printStackTrace();
        }

        return listOut;
    }

    public static StringBuilder liesLog(String dateiname){
        //List<String> listOut = new ArrayList<>();
        StringBuilder listOut = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(dateiname))){
            String line;
            while ((line = br.readLine()) != null){
                listOut.append(line);
            }
        } catch (IOException e){
            System.out.println(dateiname);
            e.printStackTrace();
        }

        return listOut;
    }

    public static Image ladeBild(String bildname) throws IOException {

        String address = jarfile.getParent() + "\\res\\" + bildname + ".gif";
        Image image = null; //init
        try (FileInputStream fis = new FileInputStream(address)) {
            image = new Image(fis);
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            //debug ;)
            System.out.println("Filepath: " + address);
        }
        return image;
    }

    public static File liesFileVomJARpfad(String filename) throws IOException {
        //Path inside jar-file -> Class_jar/Class_jar/..
        //one folder up is the corresponding Class_jar folder, so you can put all ressources in a subfolder (e.g. res/)
        //used: out\artifacts\Class_jar\res here for the pics and csv files inside res folder
        return new File(jarfile.getParent() + "\\res\\" + filename);
    }
}
