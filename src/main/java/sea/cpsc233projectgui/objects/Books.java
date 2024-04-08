package sea.cpsc233projectgui.objects;

import sea.cpsc233projectgui.util.BookType;

public abstract class Books{
    private final String title, author, genre;
    private String availabilityStatus;
    private final BookType type;
    private int numOfCheckOuts;
    public Books(String title, String author, String genre, String availabilityStatus, BookType type){
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.availabilityStatus = availabilityStatus;
        this.type = type;
        this.numOfCheckOuts=0;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public BookType getType() {
        return type;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
        numOfCheckOuts++;
    }
    public int getNumOfCheckOuts(){
        return numOfCheckOuts;
    }

    @Override
    public abstract String toString();
}