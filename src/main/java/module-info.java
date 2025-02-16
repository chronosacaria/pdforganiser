module me.chronosacaria.pdforganiser {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires com.google.gson;

    opens me.chronosacaria.pdforganiser to javafx.fxml;
    exports me.chronosacaria.pdforganiser;
    opens me.chronosacaria.pdforganiser.model to javafx.fxml, com.google.gson;
    exports me.chronosacaria.pdforganiser.model;
    exports me.chronosacaria.pdforganiser.controller;
    opens me.chronosacaria.pdforganiser.controller to javafx.fxml;
}