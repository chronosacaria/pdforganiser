package me.chronosacaria.pdforganiser.controller;

import com.google.gson.reflect.TypeToken;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import me.chronosacaria.pdforganiser.model.PdfData;
import me.chronosacaria.pdforganiser.model.PdfDataBootstrap;
import me.chronosacaria.pdforganiser.utils.JsonUtils;
import me.chronosacaria.pdforganiser.utils.PdfOrganiser;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PdfOrganiserController {

    @FXML
    private TableView<PdfData> tableView;
    @FXML
    private TableColumn<PdfData, String> titleColumn;
    @FXML
    private TableColumn<PdfData, String> authorColumn;
    @FXML
    private TableColumn<PdfData, String> publisherColumn;
    @FXML
    private TableColumn<PdfData, Long> isbnColumn;
    @FXML
    private TableColumn<PdfData, Integer> pageCountColumn;
    @FXML
    private TableColumn<PdfData, String> libraryOfCongressColumn;
    @FXML
    private TableColumn<PdfData, String> dateAddedColumn;
    @FXML
    private TableColumn<PdfData, String> filePathColumn;
    @FXML
    private TextField searchField;
    @FXML
    private Button addButton;

    private ObservableList<PdfData> pdfList = FXCollections.observableArrayList();
    private File jsonFile = new File("pdfs.json");

    public void initialize() {
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        authorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        publisherColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPublisher()));
        isbnColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getIsbn()).asObject());
        pageCountColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPageCount()).asObject());
        libraryOfCongressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLibraryOfCongressClassification()));
        dateAddedColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateAdded().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
        filePathColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFilePath()));

        if (jsonFile.exists()) {
            List<PdfDataBootstrap> bootstrapList = JsonUtils.readJson(jsonFile.getAbsolutePath(), new TypeToken<>() {
            });
            pdfList.addAll(PdfOrganiser.bootstrapListToPdfList(bootstrapList));
        } else {
            createDummyJsonFile();
        }

        tableView.setItems(pdfList);
    }

    private void createDummyJsonFile() {
        List<PdfData> dummyPdfs = new ArrayList<>();
        dummyPdfs.add(new PdfData(
                "Sample Title 1",
                "Author A",
                "Publisher X",
                9780123456789L,
                200,
                "A123",
                LocalDate.of(2022, 1, 1),
                "/path/to/sample1.pdf",
                List.of("tagA", "tagB")
        ));
        //dummyPdfs.add(new PdfData(
        //        "Sample Title 2",
        //        "Author B",
        //        "Publisher Y",
        //        9780234567890L,
        //        250,
        //        "B234",
        //        LocalDate.of(2022, 1, 2),
        //        "/path/to/sample2.pdf",
        //        List.of("tagC", "tagD")
        //));

        // Convert dummy PdfData to PdfDataBootstrap
        List<PdfDataBootstrap> bootstrapList = PdfOrganiser.pdfListToBootstrapList(dummyPdfs);
        JsonUtils.writeJson(bootstrapList, jsonFile.getAbsolutePath());
    }

    @FXML
    private void onAddButtonClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        Stage stage = (Stage) addButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {

            String filePath = selectedFile.getAbsolutePath();

            // Simulate adding a PDF
            PdfData newPdf = new PdfData(
                    "New Title",
                    "Author Name",
                    "Publisher Name",
                    1234567890L,
                    300,
                    "Classification",
                    LocalDate.now(),
                    "/path/to/file.pdf",
                    List.of("tag1", "tag2")
            );

            // Check if the PDF already exists
            boolean pdfExists = pdfList.stream()
                    .anyMatch(pdf -> pdf.getIsbn() == newPdf.getIsbn());

            if (!pdfExists) {
                pdfList.add(newPdf);

                // Convert the list to PdfDataBootstrap and write to JSON
                List<PdfDataBootstrap> bootstrapList = PdfOrganiser.pdfListToBootstrapList(pdfList);
                JsonUtils.writeJson(bootstrapList, jsonFile.getAbsolutePath());
            } else {
                System.out.println("PDF already exists in the list: " + newPdf.getTitle());
            }
        }
    }

    @FXML
    private void onSearchFieldChanged() {
        String searchQuery = searchField.getText().toLowerCase();
        tableView.setItems(pdfList.stream()
                .filter(pdf -> pdf.getTitle().toLowerCase().contains(searchQuery) ||
                        pdf.getAuthor().toLowerCase().contains(searchQuery))
                .collect(Collectors.toCollection(FXCollections::observableArrayList)));
    }

    public void setPdfs(ObservableList<PdfData> pdfs) {
        // set the pdfs to the tableView or any other component
        tableView.setItems(pdfs);
    }
}
