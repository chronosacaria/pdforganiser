package me.chronosacaria.pdforganiser.controller;

import com.google.gson.reflect.TypeToken;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.skin.TableColumnHeader;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import me.chronosacaria.pdforganiser.model.PdfData;
import me.chronosacaria.pdforganiser.model.PdfDataBootstrap;
import me.chronosacaria.pdforganiser.utils.JsonUtils;
import me.chronosacaria.pdforganiser.utils.PdfOrganiser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    @SuppressWarnings("FieldMayBeFinal")
    private ObservableList<PdfData> pdfList = FXCollections.observableArrayList();
    @SuppressWarnings("FieldMayBeFinal")
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
            if (bootstrapList != null) {
                pdfList.addAll(PdfOrganiser.bootstrapListToPdfList(bootstrapList));
            }
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
                "/path/to/sample1.pdf"
                //, List.of("tagA", "tagB")
        ));

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
            try {
                String title;
                String author;
                String publisher;
                String isbn;
                int pageCount;
                try (PDDocument pdfDocument = PDDocument.load(selectedFile)) {
                    PDDocumentInformation pdfInfo = pdfDocument.getDocumentInformation();

                    title = pdfInfo.getTitle();
                    if (title == null || title.isEmpty()) {
                        TextInputDialog titleDialog = new TextInputDialog(selectedFile.getName().substring(0, selectedFile.getName().length() - 4));
                        titleDialog.setTitle("Enter PDF Title");
                        titleDialog.setHeaderText("No Title was found, please provide one:");
                        titleDialog.setContentText(selectedFile.getName());
                        Optional<String> result = titleDialog.showAndWait();
                        title = result.orElseGet(selectedFile::getName);
                    }
                    author = pdfInfo.getAuthor();
                    if (author == null || author.isEmpty()) {
                        TextInputDialog authorDialog = new TextInputDialog();
                        authorDialog.setTitle("Enter PDF Author");
                        authorDialog.setHeaderText("No Author was found, please provide one:");
                        authorDialog.setContentText("Unknown Author");
                        Optional<String> result = authorDialog.showAndWait();
                        author = result.orElse("Unknown Author");
                    }
                    publisher = pdfInfo.getProducer();
                    if (publisher == null || publisher.isEmpty()) {
                        TextInputDialog publisherDialog = new TextInputDialog();
                        publisherDialog.setTitle("Enter PDF Publisher");
                        publisherDialog.setHeaderText("No Publisher was found, please provide one:");
                        publisherDialog.setContentText("Unknown Publisher");
                        Optional<String> result = publisherDialog.showAndWait();
                        publisher = result.orElse("Unknown Publisher");
                    }

                    TextInputDialog isbnDialog = new TextInputDialog();
                    isbnDialog.setTitle("Enter PDF ISBN");
                    isbnDialog.setHeaderText("Enter the ISBN of the PDF:");
                    isbnDialog.getEditor().textProperty().addListener((_, oldValue, newValue) -> {
                        if (!newValue.matches("\\d*")) {
                            isbnDialog.getEditor().setText(oldValue);
                        }
                    });
                    isbnDialog.setOnCloseRequest(event -> {
                        String input = isbnDialog.getEditor().getText();
                        if (input.isEmpty() || !input.matches("\\d+")) {
                            event.consume(); // prevent dialog from closing
                            isbnDialog.setHeaderText("Invalid ISBN. Please enter a valid ISBN.");
                        }
                    });
                    isbn = isbnDialog.showAndWait().orElseThrow(() -> new RuntimeException("ISBN is required"));

                    pageCount = pdfDocument.getNumberOfPages();
                }

                TextInputDialog LibraryOfCongressClassificationDialog = new TextInputDialog();
                LibraryOfCongressClassificationDialog.setTitle("Enter PDF LOC Classification");
                LibraryOfCongressClassificationDialog.setHeaderText("Enter the LOC Classification of the PDF:");
                String loc = LibraryOfCongressClassificationDialog.showAndWait().orElse("");

                PdfData newPdf = new PdfData(
                        title,
                        author,
                        publisher,
                        Long.parseLong(isbn),
                        pageCount,
                        loc,
                        LocalDate.now(),
                        selectedFile.getAbsolutePath()
                );

                boolean pdfExists = pdfList.stream()
                        .anyMatch(pdf -> pdf.getIsbn() == newPdf.getIsbn());

                if (!pdfExists) {
                    pdfList.add(newPdf);

                    List<PdfDataBootstrap> bootstrapList = PdfOrganiser.pdfListToBootstrapList(pdfList);
                    JsonUtils.writeJson(bootstrapList, jsonFile.getAbsolutePath());
                    tableView.getItems().add(newPdf);
                } else {
                    System.out.println("PDF already exists in the list: " + newPdf.getTitle());
                }

            } catch (Exception e) {
                Logger.getLogger(PdfOrganiserController.class.getName()).log(Level.SEVERE, "Error adding PDF", e);

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

    @FXML
    private void onMouseClicked() {
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                if (event.getTarget() instanceof TableColumnHeader)
                    return;
                PdfData selectedPdf = tableView.getSelectionModel().getSelectedItem();
                if (selectedPdf != null) {
                    try {
                        Desktop.getDesktop().open(new File(selectedPdf.getFilePath()));
                    } catch (IOException e) {
                        Logger.getLogger(PdfOrganiserController.class.getName()).log(Level.SEVERE, "Error opening PDF file", e);
                    }
                }
            }
        });
    }
}
