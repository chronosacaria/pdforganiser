package me.chronosacaria.pdforganiser.model;

import java.time.LocalDate;
import java.util.List;

public class PdfData {
    private String title;
    private String author;
    private String publisher;
    private long isbn;
    private int pageCount;
    private String libraryOfCongressClassification;
    private LocalDate dateAdded;
    private String filePath;
    private List<String> contentTags;

    // Constructors
    public PdfData(
            String title,
            String author,
            String publisher,
            long isbn,
            int pageCount,
            String libraryOfCongressClassification,
            LocalDate date,
            String uri
            //, List<String> contentTags
    ) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.pageCount = pageCount;
        this.libraryOfCongressClassification = libraryOfCongressClassification;
        this.dateAdded = date;
        this.filePath = uri;
        //this.contentTags = contentTags;
    }

    public PdfData() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getLibraryOfCongressClassification() {
        return libraryOfCongressClassification;
    }

    public void setLibraryOfCongressClassification(String libraryOfCongressClassification) {
        this.libraryOfCongressClassification = libraryOfCongressClassification;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<String> getContentTags() {
        return contentTags;
    }

    public void setContentTags(List<String> contentTags) {
        this.contentTags = contentTags;
    }
}
