package vn.iotstar.models;

import java.time.LocalDate;

public class Author_24110373 {

	private int authorId;
    private String authorName;
    private LocalDate dateOfBirth;

    public Author_24110373() {
    }

    public Author_24110373(int authorId, String authorName, LocalDate dateOfBirth) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.dateOfBirth = dateOfBirth;
    }

    public int getAuthorId() { return authorId; }
    public void setAuthorId(int authorId) { this.authorId = authorId; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
}
