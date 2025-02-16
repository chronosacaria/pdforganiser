package me.chronosacaria.pdforganiser.model;

import java.util.List;

public class PdfDataBootstrap {
    private String titleProperty;
    private String authorProperty;
    private String publisherProperty;
    private long isbnProperty;
    private int pageCountProperty;
    private String libraryOfCongressClassificationProperty;
    private String dateAddedProperty;
    private String filePathProperty;
    private List<String> contentTagsProperty;

    // Constructors
    public PdfDataBootstrap(
            String title,
            String author,
            String publisher,
            long isbn,
            int pageCount,
            String libraryOfCongressClassification,
            String date,
            String uri,
            List<String> contentTags) {
        this.titleProperty = title;
        this.authorProperty = author;
        this.publisherProperty = publisher;
        this.isbnProperty = isbn;
        this.pageCountProperty = pageCount;
        this.libraryOfCongressClassificationProperty = libraryOfCongressClassification;
        this.dateAddedProperty = date;
        this.filePathProperty = uri;
        this.contentTagsProperty = contentTags;
    }

    public PdfDataBootstrap() {
    }

    // Getters and Setters
    public String getTitleProperty() {
        return titleProperty;
    }

    public void setTitleProperty(String titleProperty) {
        this.titleProperty = titleProperty;
    }

    public String getAuthorProperty() {
        return authorProperty;
    }

    public void setAuthorProperty(String authorProperty) {
        this.authorProperty = authorProperty;
    }

    public String getPublisherProperty() {
        return publisherProperty;
    }

    public void setPublisherProperty(String publisherProperty) {
        this.publisherProperty = publisherProperty;
    }

    public long getIsbnProperty() {
        return isbnProperty;
    }

    public void setIsbnProperty(long isbnProperty) {
        this.isbnProperty = isbnProperty;
    }

    public int getPageCountProperty() {
        return pageCountProperty;
    }

    public void setPageCountProperty(int pageCountProperty) {
        this.pageCountProperty = pageCountProperty;
    }

    public String getLibraryOfCongressClassificationProperty() {
        return libraryOfCongressClassificationProperty;
    }

    public void setLibraryOfCongressClassificationProperty(String libraryOfCongressClassificationProperty) {
        this.libraryOfCongressClassificationProperty = libraryOfCongressClassificationProperty;
    }

    public String getDateAddedProperty() {
        return dateAddedProperty;
    }

    public void setDateAddedProperty(String dateAddedProperty) {
        this.dateAddedProperty = dateAddedProperty;
    }

    public String getFilePathProperty() {
        return filePathProperty;
    }

    public void setFilePathProperty(String filePathProperty) {
        this.filePathProperty = filePathProperty;
    }

    public List<String> getContentTagsProperty() {
        return contentTagsProperty;
    }

    public void setContentTagsProperty(List<String> contentTagsProperty) {
        this.contentTagsProperty = contentTagsProperty;
    }
}
