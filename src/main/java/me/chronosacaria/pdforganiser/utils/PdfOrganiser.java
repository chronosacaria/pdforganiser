package me.chronosacaria.pdforganiser.utils;

import me.chronosacaria.pdforganiser.model.PdfData;
import me.chronosacaria.pdforganiser.model.PdfDataBootstrap;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

public class PdfOrganiser {

    // Convert PdfData to PdfDataBootstrap
    public static PdfDataBootstrap pdfToBootstrap(PdfData pdf) {
        PdfDataBootstrap bootstrap = new PdfDataBootstrap();
        bootstrap.setTitleProperty(pdf.getTitle());
        bootstrap.setAuthorProperty(pdf.getAuthor());
        bootstrap.setPublisherProperty(pdf.getPublisher());
        bootstrap.setIsbnProperty(pdf.getIsbn());
        bootstrap.setPageCountProperty(pdf.getPageCount());
        bootstrap.setLibraryOfCongressClassificationProperty(pdf.getLibraryOfCongressClassification());
        bootstrap.setDateAddedProperty(pdf.getDateAdded().toString());
        bootstrap.setFilePathProperty(pdf.getFilePath());
        bootstrap.setContentTagsProperty(pdf.getContentTags());
        return bootstrap;
    }

    // Convert PdfDataBootstrap to PdfData
    public static PdfData bootstrapToPdf(PdfDataBootstrap bootstrap) {
        PdfData pdf = new PdfData();
        pdf.setTitle(bootstrap.getTitleProperty());
        pdf.setAuthor(bootstrap.getAuthorProperty());
        pdf.setPublisher(bootstrap.getPublisherProperty());
        pdf.setIsbn(bootstrap.getIsbnProperty());
        pdf.setPageCount(bootstrap.getPageCountProperty());
        pdf.setLibraryOfCongressClassification(bootstrap.getLibraryOfCongressClassificationProperty());
        pdf.setDateAdded(LocalDate.parse(bootstrap.getDateAddedProperty()));
        pdf.setFilePath(bootstrap.getFilePathProperty());
        pdf.setContentTags(bootstrap.getContentTagsProperty());
        return pdf;
    }

    // Convert List<PdfData> to List<PdfDataBootstrap>
    public static List<PdfDataBootstrap> pdfListToBootstrapList(List<PdfData> pdfList) {
        List<PdfDataBootstrap> bootstrapList = new ArrayList<>();
        for (PdfData pdf : pdfList) {
            bootstrapList.add(pdfToBootstrap(pdf));
        }
        return bootstrapList;
    }

    // Convert List<PdfDataBootstrap> to List<PdfData>
    public static List<PdfData> bootstrapListToPdfList(List<PdfDataBootstrap> bootstrapList) {
        List<PdfData> pdfList = new ArrayList<>();
        for (PdfDataBootstrap bootstrap : bootstrapList) {
            pdfList.add(bootstrapToPdf(bootstrap));
        }
        return pdfList;
    }
}
