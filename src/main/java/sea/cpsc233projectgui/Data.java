package sea.cpsc233projectgui;
//Testing

import sea.cpsc233projectgui.objects.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Data {

    protected final ArrayList<Books> books;
    protected final ArrayList<Books> availableBooks;
    protected final ArrayList<Books> unAvailableBooks;
    protected final ArrayList<String> titles;
    protected final ArrayList<String> authors;

    public Data(){
        books = new ArrayList<>();
        availableBooks = new ArrayList<>();
        unAvailableBooks = new ArrayList<>();
        titles = new ArrayList<>();
        authors = new ArrayList<>();
    }

    /**
     * Adds new AudioBook to list of books and adds their title to titles and author to authors
     *
     * @param title of the book to be stored
     * @param author of the book to be stored
     * @param narrator of the audiobook to be stored
     * @param genre of the book to be stored
     * @param availabilityStatus of the book to be stored (default = available)
     * @return True if book stored successfully, otherwise False.
     */
    public boolean storeNewAudioBook(String title, String author, String narrator, String genre, String availabilityStatus) {
        if (title.isEmpty() || author.isEmpty()){
            System.out.println("Book cannot be stored.");
            return false;
        }

        if (!checkExistBook(title, author)) {
            Books book = new AudioBooks(title, author, narrator, genre, availabilityStatus);
            books.add(book); //adding to list of all books
            availableBooks.add(book); //since all books are by default available when created
            titles.add(title);
            authors.add(author);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Adds new PhysicalBook to list of books and adds their title to titles and author to authors
     *
     * @param title of the book to be stored
     * @param author of the book to be stored
     * @param genre of the book to be stored
     * @param availabilityStatus of the book to be stored (default = available)
     * @return True if book stored successfully, otherwise False.
     */
    public boolean storeNewPhysicalBook(String title, String author, String genre, String availabilityStatus) {
        if (title.isEmpty() || author.isEmpty()){
            System.out.println("Book cannot be stored.");
            return false;
        }

        if (!checkExistBook(title, author)) {
            Books book = new PhysicalBooks(title, author, genre, availabilityStatus);
            books.add(book); //adding to list of all books
            availableBooks.add(book); //since all books are by default available when created
            titles.add(title);
            authors.add(author);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if a book exists or not
     * @param title of the book to be checked
     * @param author of the book to be checked
     * @return whether the library has a book with that title and author
     */
    public boolean checkExistBook(String title , String author){
        for(Books book : books){
            if (book.getTitle().equals(title) && book.getAuthor().equals(author)){
                return true;
            }
        }
        return false;
    }

    /**
     * @param title of book to check status of
     * @param author of book to check status of
     * @return whether the book is in the available books list
     */

    public boolean checkBookAvailable(String title, String author){
        if (checkExistBook(title,author)) {
            for (Books book : availableBooks) {
                if (book.getTitle().equals(title) && book.getAuthor().equals(author)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return list of all books in library
     */
    public ArrayList <Books> getAllBooks(){
        return books;
    }

    /**
     * @return list of only the available books in library
     */
    public ArrayList <Books> getAvailableBooks(){
        return availableBooks;
    }

    /**
     * @return list of only the unavailable books in library
     */
    public ArrayList<Books> getUnavailableBooks() {
        return unAvailableBooks;
    }

    /**
     * @param title to search for
     * @return list of all books with that title
     */
    public ArrayList<Books> getBooksByTitle(String title) {
        ArrayList<Books> booksWithTitle = new ArrayList<>();
        for (Books book: books){
            if (book.getTitle().equals(title)) {
                booksWithTitle.add(book);
            }
        }
        return booksWithTitle;
    }

    /**
     * @param author to search for
     * @return list of all books by that author
     */
    public ArrayList<Books> getBooksByAuthor(String author) {
        ArrayList<Books> booksByAuthor = new ArrayList<>();
        for (Books book: books){
            if (book.getAuthor().equals(author)) {
                booksByAuthor.add(book);
            }
        }
        return booksByAuthor;
    }

    /**
     * @param genre to search for
     * @return list of all books in that genre
     */
    public ArrayList<Books> getBooksByGenre(String genre) {
        ArrayList<Books> booksInGenre = new ArrayList<>();
        for (Books book: books){
            if (book.getGenre().equals(genre)) {
                booksInGenre.add(book);
            }
        }
        return booksInGenre;
    }

    public ArrayList<Books> getBooksByBookType(String type) {
        ArrayList<Books> booksOfGivenType = new ArrayList<>();
        for (Books book: books){
            if (book.getType().toString().equals(type)) {
                booksOfGivenType.add(book);
            }
        }
        return booksOfGivenType;
    }

    /**
     * Changes the status of a book from available to unavailable
     * Removes it from availableBooks and adds it to unAvailableBooks
     * Adds the books as a String to the member's borrowed books list
     * @param id of member who is checking the book out
     * @param title of the book they are checking out
     * @param author of the book they are checking out
     */
    public void checkoutBook(Integer id, String title, String author){
        for (Books book: books){ //goes through every book until it finds one matching title and author
            if (book.getTitle().equals(title) && book.getAuthor().equals(author)){

                availableBooks.remove(book);
                book.setAvailabilityStatus("Unavailable");
                unAvailableBooks.add(book); // updates the book availability and add to unavailable list

                for (Member member: members){ // goes through members until it finds one with ID
                    if (member.getID()==id){
                        //saves to member's borrowed books list as a String
                        member.addBookToMember(title+" by "+author);
                    }
                }
            }
        }
    }

    /**
     * Changes status of book from unavailable to available
     * Removes book from unAvailableBooks and adds it availableBooks
     * Removes the corresponding String from the member's borrowed books list
     * Adds fines to the member's pre-existing fines
     * @param id of member returning book
     * @param title of book being returned
     * @param author of book being returned
     * @param daysOverdue is the days that the member kept the book past its due date
     */
    public void returnBook(Integer id, String title, String author, int daysOverdue) {
        for (Books book: books){ //goes through every book until it finds one with title and author
            daysOverDue.add(daysOverdue);
            if (book.getTitle().equals(title) && book.getAuthor().equals(author)){
                unAvailableBooks.remove(book);
                book.setAvailabilityStatus("Available");
                availableBooks.add(book);
                for (Member member: members){ //goes through members until it finds the one with ID
                    member.removeBookFromMember(title+" by "+author); //member no longer has book borrowed
                     if (member.getID()==id && member instanceof AdultMember adultMember){
                        adultMember.setFines(adultMember.getFines() + calculateFines(daysOverdue));  //adds fines
                    }
                }
            }
        }
    }

    /**
     * Removes book from every list of books it is a part of, including books,titles,availableBooks,unAvailableBooks
     * If book is checked out by someone, removes it from their borrowed books list without any fines
     * @param title of book to be removed from library
     * @param author of book to be removed from library
     * @return whether deletion was successful
     */
    public boolean removeBook(String title, String author) {
        if (checkExistBook(title,author)){ //can only remove a book that exists
            for (Books book: books){
                if (book.getTitle().equals(title)&&book.getAuthor().equals(author)){
                    if (checkBookAvailable(title,author)){
                        availableBooks.remove(book);
                    }
                    else {
                        unAvailableBooks.remove(book);
                        for (Member member:members){ //should be removed from borrowed list of any member who has it
                            returnBook(member.getID(),title,author,0);
                        }
                    }
                    books.remove(book);
                    titles.remove(book.getTitle());
                    authors.remove(book.getAuthor());
                    return true; // book has successfully been removed
                }
            }
        }
        return false; //book did not exist, so it can't be removed
    }

    /**
     * gets a list of books in desired genre and finds the most popular one
     * @param genre is the genre to find most popular book in
     * @return a string of the most popular book in genre
     */
    public String mostPopularBookByGenre(String genre){
        ArrayList<Books> books = getBooksByGenre(genre);
        int checkOutTotal=0;
        String bookString="\n--------------------\n";
        for (Books book:books){
            if (book.getNumOfCheckOuts()>=checkOutTotal){
                checkOutTotal=book.getNumOfCheckOuts();
                bookString=book.toString();
            }
        }
        return bookString;
    }

    // EVERYTHING BELOW HERE IS TO STORE MEMBER DATA

    protected final ArrayList<Member> members = new ArrayList<>();
    protected static final HashMap<Integer, Member> memberIDs = new HashMap<>();

    protected final ArrayList<Integer> daysOverDue = new ArrayList<>(); // is added to every time a book is returned late


    /**
     * Adds new ChildMember to list of members and adds their ID to memberIDs
     * @param id of member to be added
     * @param name of member to be added
     * @return whether addition of member was successful (only unsuccessful when member with same ID already exists)
     */

    public boolean storeNewChildMember(Integer id, String name) {
        if (id==null||name.isEmpty()){
            System.out.println("Member cannot be stored.");
            return false;
        }

        if (!checkExistMember(id)) {
            Member member = new ChildMember(id, name);
            memberIDs.put(id, member);
            members.add(member); //adding to list of all members
            return true;
        } else {
            return false;
        }
    }
    /**
     * Adds new AdultMember to list of members and adds their ID to memberIDs
     * @param id of member to be added
     * @param name of member to be added
     * @return whether addition of member was successful (only unsuccessful when member with same ID already exists)
     */
    public boolean storeNewAdultMember(Integer id, String name) {
        if (id==null||name.isEmpty()){
            System.out.println("Member cannot be stored.");
            return false;
        }

        if (!checkExistMember(id)) {
            Member member = new AdultMember(id, name);
            memberIDs.put(id, member);
            members.add(member); //adding to list of all members
            return true;
        } else {
            return false;
        }
    }


    /**
     * @param id of member to check
     * @return if ID already exists in list of memberIDs
     */
    public boolean checkExistMember(Integer id) {
        return memberIDs.containsKey(id);
    }

    /**
     * Removes member from list of members and removes their ID from memberIDs
     * @param id of member to be removed
     * @param name of member to be removed
     * @return whether member was successfully removed
     */
    public boolean removeMember(int id, String name) {
        if (checkExistMember(id)){
            Member member = memberIDs.get(id);
            if (member.getName().equals(name)){
                //technically an unnecessary check, but exists for security reasons since a member is being deleted
                members.remove(member);
                memberIDs.remove(member.getID(),member);
                return true;
            }
        }
        return false;
    }

    /**
     * @return list of all members in library
     */
    public ArrayList<Member> getAllMembers(){
        return members;
    }

    /**
     * @param name to search for
     * @return a list of members with that name
     */
    public ArrayList<Member> getMembersByName(String name) {
        ArrayList<Member> membersWithName = new ArrayList<>();
        for (Member member: members){
            if (member.getName().equals(name)) {
                membersWithName.add(member);
            }
        }
        return membersWithName;
    }

    /**
     * @param id to find the corresponding member for
     * @return the member with that ID as a single element list (to make printing easy in Menu.java)
     */
    public ArrayList<Member> getMembersById(int id) {
        ArrayList<Member> membersWithId = new ArrayList<>();
        membersWithId.add(memberIDs.get(id));
        return membersWithId;
    }

    public ArrayList<Member> getMembersByMemberType(String type) {
        ArrayList<Member> membersOfGivenType = new ArrayList<>();
        for (Member member: members){
            if (member.getMemberType().toString().equals(type)) {
                membersOfGivenType.add(member);
            }
        }
        return membersOfGivenType;
    }

    /**
     * Goes through daysOverdue list and calculates the average
     * @return the average amount of days overdue that books are returned
     */
    public int getAverageDaysOverdue(){
        int total=0;
        for (int days:daysOverDue){
            total+=days;
        }
        return (int)((total/daysOverDue.size())+0.5);
    }

    /**
     * Goes through all child members and finds the one with the highest read-count
     * @return a String with that child's information
     */
    public String getMostActiveChild(){
        int highestCount=0;
        String mostActiveChildString="";
        boolean existsChild=false; //in case there are no children at the library

        for (Member member:members){
            if (member instanceof ChildMember child){
                existsChild=true;
                if(child.getReadCount()>=highestCount){
                    highestCount= child.getReadCount();
                    mostActiveChildString=child.toString();
                }
            }
        }

        if (existsChild){
            return  mostActiveChildString;
        }
        return null; //if there are no children in library
    }

    /**
     * @param id of member to get books for
     * @return list of Strings representing books the member has checked out
     */
    public static ArrayList<String> getBorrowedBooks(Integer id){
        Member member = memberIDs.get(id);
        if (member != null) {
            return member.getBorrowed();
        } else {
            System.err.println("Member not found for ID: " + id);
            // Handle the case where member is not found, e.g., return an empty list or throw an exception
            return new ArrayList<>(); // Empty list as a fallback
        }
    }

    /**
     * @param daysOverDue is the number of days the user had the book past the due date
     * @return the fines accrued from being overdue
     */
    public double calculateFines(int daysOverDue){
        return ((double)daysOverDue)*0.05; //five cents are added to fines per day overdue
    }

    /**
     * Updates a members fines when they pay them
     * @param ID of member paying fines
     * @param payment is the amount the member is paying
     * @return whether it is a valid payment or not
     */
    public boolean payFines(Integer ID, Double payment){
        Member member = memberIDs.get(ID);
        if (member instanceof AdultMember adultMember && adultMember.getFines() >= payment){
            adultMember.setFines(adultMember.getFines()-payment);
            return true;
        }
        return false;
    }

}
