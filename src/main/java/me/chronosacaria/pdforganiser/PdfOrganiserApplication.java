package me.chronosacaria.pdforganiser;

import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.chronosacaria.pdforganiser.controller.PdfOrganiserController;
import me.chronosacaria.pdforganiser.model.PdfData;
import me.chronosacaria.pdforganiser.model.PdfDataBootstrap;
import me.chronosacaria.pdforganiser.utils.JsonUtils;
import me.chronosacaria.pdforganiser.utils.PdfOrganiser;

import java.io.File;
import java.util.List;

public class PdfOrganiserApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PdfOrganiser.fxml"));

        Parent root = loader.load();
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("PDF Organizer");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Load existing PDF data from JSON
        File jsonFile = new File("pdfs.json");
        List<PdfDataBootstrap> bootstraps = JsonUtils.readJson(jsonFile.getAbsolutePath(), new TypeToken<>() {});
        List<PdfData> pdfList = PdfOrganiser.bootstrapListToPdfList(bootstraps);

        // Set the PDF list to the controller
        PdfOrganiserController controller = loader.getController();

        if (controller != null) {
            controller.setPdfs(FXCollections.observableArrayList(pdfList));
        } else {
            System.err.println("Failed to load controller");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}


