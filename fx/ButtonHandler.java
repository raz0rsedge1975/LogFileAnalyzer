package fx;

import analyzer.HTTPCommandAnalyzer;
import analyzer.HttpAnalyzer;
import analyzer.IPAnalyzer;
import analyzer.UserAgentAnalyzer;
import cons.DateiVerbindung;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.text.*;

import java.io.File;
import java.io.IOException;
import java.time.Clock;

public class ButtonHandler implements EventHandler<ActionEvent> { //Handler für Button Action
    private final TextFlow textFlow;
    private final Stage window;

    public ButtonHandler(TextFlow textFlow, Stage window){
        this.textFlow = textFlow;
        this.window = window;
    }

    public void processFile() throws IOException {

        FileChooser fileChooser = new FileChooser();
        HttpAnalyzer httpanalyzer = new HttpAnalyzer();
        Text httpText = new Text();
        HTTPCommandAnalyzer httpcommand = new HTTPCommandAnalyzer();
        Text cmdText = new Text();
        UserAgentAnalyzer useragent = new UserAgentAnalyzer();
        Text userText = new Text();
        IPAnalyzer ipaddress = new IPAnalyzer();
        Text ipaddressText = new Text();

        textFlow.getChildren().removeAll();
        Text tmpText = new Text("Loading Log... please wait."+'\n');
        Font font2 = Font.font("Verdana", FontWeight.LIGHT, 12);
        tmpText.setFont(font2);

        textFlow.getChildren().add(tmpText);
        StringBuilder sbTmp;

        Clock clock = Clock.systemDefaultZone();
        long start = clock.millis();

        fileChooser.setTitle("Open Log File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Log Files", "*.log"));

        //Set to user directory or go to default
        String userDirectoryString = System.getProperty("user.home");
        File userDirectory = new File(userDirectoryString);
        if(!userDirectory.canRead()) {
            userDirectory = new File("c:/");
        }

        fileChooser.setInitialDirectory(userDirectory);
        File selectedFile = fileChooser.showOpenDialog(window);
        if (selectedFile != null) {
            String logFilename = selectedFile.getName();
            sbTmp = DateiVerbindung.liesLog(logFilename);
                httpText.setText(httpanalyzer.analyze(sbTmp.toString()) + '\n');
                httpText.setFill(Color.CORNFLOWERBLUE);
                httpText.setStrokeWidth(1);
                httpText.setStroke(Color.BLUE);

                cmdText.setText(httpcommand.analyze(sbTmp.toString()) + '\n');
                cmdText.setFill(Color.YELLOWGREEN);
                cmdText.setStrokeWidth(1);
                cmdText.setStroke(Color.RED);

                userText.setText(useragent.analyze(sbTmp.toString()) + '\n');
                userText.setFill(Color.YELLOWGREEN);
                userText.setStrokeWidth(1);
                userText.setStroke(Color.DARKGREEN);

                ipaddressText.setText(ipaddress.analyze(sbTmp.toString()) + '\n');
                ipaddressText.setFont(new Font("Verdana",14));
                ipaddressText.setFill(Color.BLACK);

                ipaddressText.setSelectionStart(0);
                ipaddressText.setSelectionEnd(10);
                ipaddressText.setStyle("-fx-highlight-fill: lightblue; -fx-highlight-text-fill: #ff3333");
        }
        textFlow.getChildren().clear();
        textFlow.getChildren().addAll(httpText, cmdText, userText, ipaddressText);
        long stop = clock.millis();
        System.out.println("Lese-Dauer: "+(stop-start)+" Millisekunden.");
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        try {
            processFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
