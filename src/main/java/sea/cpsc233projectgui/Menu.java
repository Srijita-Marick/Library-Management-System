package sea.cpsc233projectgui;
/**
 * CPSC 233 W24 Group Project
 * Library Management System
 * @author Anna Littkemann, Emily Ng Kwong Sang, Srijita Marick
 * @email anna.littkemann@ucalgary.ca, emily.ngkwongsang@ucalgary.ca,
 * srijita.marick@ucalgary.ca
 * @UCID 30212220, 30230290, 30230880
 * @date Feb 21-27 2024 (DEMO 1)
 * @date Mar 16-19 2024 (DEMO 2)
 * @tutorial T03 Tue  3:00PM -  4:50PM
 * Used "Group Project Part 1 Long Example" by Jonathan Hudson as code source
 * Used "Group Project Part 2 Long Example" by Jonathan Hudson as code source
 */

import sea.cpsc233projectgui.comparators.MemberNameComparator;
import sea.cpsc233projectgui.objects.*;
import sea.cpsc233projectgui.util.BookRecords;
import sea.cpsc233projectgui.util.MemberRecords;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
public class Menu {
    private static Data data = new Data();
    private static final Scanner scanner = new Scanner(System.in);
    private static final ArrayList<String> options = new ArrayList<>();

    static {
        options.add("Blank"); //placeholder for indexing purposes that is never seen by user
        options.add("Library Data");
        options.add("Member Data");
        options.add("Load All Data");
        options.add("Save All Data");
        options.add("Exit");
    }
    private static String optMessage = """
            -------------WELCOME TO LIBRARY-------------
            """;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(optMessage);
        for (int i = 1; i < options.size(); i++) {
            sb.append(String.format("\t%d.%s\n", i, options.get(i)));
        }
        optMessage = sb.toString();
    }

    /**
     * Used to prompt the user to input a non-empty String and trim any leading or trailing whitespace
     * @return non-empty String obtained from user input
     */
    private static String getOption(){
        String option;
        do {
            option = scanner.nextLine().trim();
        }while(option.isEmpty());
        return option;
    }

    /**
     * Used in Main.java to run the main program
     */
    protected static void menuLoop(File bookFile, File memberFile) {
        if(bookFile!=null && memberFile!=null){
            menuLoadAllData();
        }
        else {
            File savedBooks = new File("savedBooks.csv");
            File savedMembers = new File("savedMembers.csv");
            menuLoadAllData(savedBooks,savedMembers);
        }
        int option = 0;
        boolean run = true; // Flag to control the loop
        while (run) {
            System.out.println(optMessage);
            String choice = getOption();
            if (!choice.isEmpty() && !choice.equals("0")) {
                option = Integer.parseInt(choice); // Convert user's choice to an integer
            }
            if (option > 0 && option < options.size()) {
                System.out.printf("Selected option %d.%s%n", option, options.get(option));
                switch (option) {
                    case 1 -> menuLibraryData();
                    case 2 -> menuMemberData();
                    case 3 -> menuLoadAllData();
                    case 4 -> menuSaveAllData();
                    case 5 -> run = false; // Exit the loop if user chooses to quit
                    default -> System.out.printf("Option %d not recognized!%n", option);
                }
            } else {
                System.out.printf("Option %d not recognized!%n", option);
            }
        }
        System.out.println("Exiting program...");
    }

    /**
     * Used in menuLoop to save all books' information to a library and all members' information to a particular file
     */
    private static void menuSaveAllData() {
        System.out.println("Saving data to library...");
        String bookFilename;
        File bookFile;
        do {
            do {
                System.out.println("Enter a filename for books: ");
                bookFilename = scanner.nextLine().trim();
            } while (bookFilename.isEmpty());
            bookFile = new File(bookFilename);
        } while (!bookFile.exists()||!bookFile.canWrite());
        if (BookRecords.save(bookFile,data)){
            System.out.printf("Saved Book Data to file %s%n",bookFilename);
        }
        else {
            System.err.printf("Failed to save file %s%n",bookFilename);
        }
        String memFilename;
        File memFile;
        do {
            do {
                System.out.println("Enter a filename for members: ");
                memFilename = scanner.nextLine().trim();
            } while (memFilename.isEmpty());
            memFile = new File(memFilename);
        } while (!memFile.exists()||!memFile.canWrite());
        if (MemberRecords.save(memFile,data)){
            System.out.printf("Saved Member Data to file %s%n",memFilename);
        }
        else {
            System.err.printf("Failed to save file %s%n",memFilename);
        }
    }

    /**
     * Used in menuLoop to load all books' information from library and all members' information in a particular file
     */
    private static void menuLoadAllData() {
        System.out.println("Loading data from library...");
        //book file
        String bookfilename;
        File bookfile;
        do {
            do {
                System.out.println("Enter a filename for books: ");
                bookfilename = scanner.nextLine().trim();
            } while (bookfilename.isEmpty());
            bookfile = new File(bookfilename);
        } while (!bookfile.exists()||!bookfile.canRead());
        //member file now
        String memfilename;
        File memfile;
        do {
            do {
                System.out.println("Enter a filename for members: ");
                memfilename = scanner.nextLine().trim();
            } while (memfilename.isEmpty());
            memfile = new File(memfilename);
        } while (!memfile.exists()||!memfile.canRead());
        menuLoadAllData(bookfile, memfile);

    }

    /**
     * Used in menuLoop to load book data and member data from a particular file
     */
    private static void menuLoadAllData(File bookFile, File memberFile) {
        Data data = BookRecords.load(bookFile,new Data());
        data = MemberRecords.load(memberFile,data);
        System.out.printf("Loaded book data from file %s%n",bookFile);
        System.out.printf("Loaded member data from file %s%n",memberFile);
        Menu.data = data;

    }
    private static final ArrayList<String> options1 = new ArrayList<>();

    static {
        options1.add("Blank"); //placeholder for indexing purposes that is never seen by user
        options1.add("Add Book");
        options1.add("Remove Book");
        options1.add("Checkout Books");
        options1.add("Return Books");
        options1.add("View All Books");
        options1.add("View Available Books");
        options1.add("View Unavailable Books");
        options1.add("Search");
        options1.add("Most Popular Book by Genre");
        options1.add("Exit to Main Menu");
    }
    private static String optMessage1 = """
            -------------LIBRARY DATA-------------
           """;

    static {
        StringBuilder sb1 = new StringBuilder();
        sb1.append(optMessage1);
        for (int i = 1; i < options1.size(); i++) {
            sb1.append(String.format("\t%d.%s\n", i, options1.get(i)));
        }
        optMessage1 = sb1.toString();
    }

    /**
     * Used in menuLoop as a sub-menu to access Library resources
     */
    private static void menuLibraryData() {
        int option1 = 0;
        boolean run = true;
        while (run) {
            System.out.println(optMessage1);
            String libchoice = scanner.nextLine();
            if (!libchoice.isEmpty() && !libchoice.equals("0")) {
                option1 = Integer.parseInt(libchoice);
            }
            if (option1 > 0 && option1 < options1.size()) {
                System.out.printf("Selected option %d.%s%n", option1, options1.get(option1));
                switch (option1) {
                    case 1 -> menuEnterNewBook();
                    case 2 -> menuRemoveBook();
                    case 3 -> menuCheckoutBooks();
                    case 4 -> menuReturnBooks();
                    case 5 -> menuViewAllBooks();
                    case 6 -> menuViewAvailable();
                    case 7 -> menuViewUnavailable();
                    case 8 -> menuSearchBooks();
                    case 9 -> menuMostPopularBookByGenre();
                    //case 10 -> menuLoadBooks();
                    //case 11 -> menuSaveBooks();
                    case 10 -> run = false;
                    default -> System.out.printf("Option %d not recognized!%n", option1);
                }
            } else{
                System.out.printf("Option %d not recognized!%n", option1);
            }
        }
    }

    /**
     * sends user to their desired type of search
     */
    private static void menuSearchBooks() {
        ArrayList<String> searchOptions = new ArrayList<>(); //each search option is a string in this list
        searchOptions.add("Blank"); //this is a placeholder that is never displayed to user
        searchOptions.add("Title");
        searchOptions.add("Author");
        searchOptions.add("Genre");
        searchOptions.add("Book Type");
        StringBuilder searchString = new StringBuilder(); // formatted list of the search options with instruction
        searchString.append("How would you like to search for books?\n");
        for (int i = 1; i < searchOptions.size(); i++) {
            searchString.append(String.format("\t%d.%s\n", i, searchOptions.get(i)));
        }
        System.out.println(searchString);
        String choice = ""; // will be used to store user input for genre index
        boolean run = true; // tracks when the while loop should run or not
        while (run) {
            choice = scanner.nextLine(); // gets input from user
            int option = Integer.parseInt(choice); // converts into to an int
            if (option > 0 && option < searchOptions.size()) { // checks if selection is valid
                System.out.printf("Searching by %d.%s%n", option, searchOptions.get(option));
                run = false; // stop while-loop
            }
            switch(option){
                case 1 -> searchByTitle();//title
                case 2 -> searchByAuthor();//author
                case 3 -> searchByGenre();//genre
                case 4 -> searchByBookType();//book type
                default -> System.out.printf("Option %d not recognized!%n", option);
            }
        }
    }
    /**
     * Prints all books with selected title using menuViewAnyBookList
     */
    private static void searchByTitle(){
        String title = getTitle();
        String viewBooksMessage = """
             All books with that title:""";
        ArrayList<Books> bookList = data.getBooksByTitle(title);
        System.out.println(viewAnyBookList(viewBooksMessage, bookList));
    }
    /**
     * Prints all books by selected author using menuViewAnyBookList
     */
    private static void searchByAuthor(){
        String author = getAuthor();
        String viewBooksMessage = """
             All books by that author:""";
        ArrayList<Books> bookList = data.getBooksByAuthor(author);
        System.out.println(viewAnyBookList(viewBooksMessage, bookList));
    }

    /**
     * Prints all books in selected genre using menuViewAnyBookList
     */
    private static void searchByGenre(){
        String genre = getGenre();
        String viewBooksMessage = """
             All books in that genre:""";
        ArrayList<Books> bookList = data.getBooksByGenre(genre);
        System.out.println(viewAnyBookList(viewBooksMessage, bookList));
    }

    /**
     * Prints all books of selected type using menuViewAnyBookList
     */
    private static void searchByBookType(){
        String type = getBookType();
        String viewBooksMessage = """
             All books of that type:""";
        ArrayList<Books> bookList = data.getBooksByBookType(type);
        System.out.println(viewAnyBookList(viewBooksMessage, bookList));
    }

    /**
     * Prints all unavailable books in library using menuViewAnyBookList
     */
    private static void menuViewUnavailable() {
        String viewBooksMessage = """
            Unavailable Books:""";
        ArrayList<Books> bookList = data.getUnavailableBooks();
        System.out.println(viewAnyBookList(viewBooksMessage, bookList));
    }

    /**
     * Prints all available books in library using menuViewAnyBookList
     */
    private static void menuViewAvailable() {
        String viewBooksMessage = """
            Available Books:""";
        ArrayList<Books> bookList = data.getAvailableBooks();
        System.out.println(viewAnyBookList(viewBooksMessage, bookList));
    }

    /**
     * Prints all book in library using menuViewAnyBookList
     */
    private static void menuViewAllBooks() {
        String viewBooksMessage= """
            ALl library Books:\n""";
        ArrayList<Books> bookList = data.getAllBooks();
        System.out.println(viewAnyBookList(viewBooksMessage, bookList));
    }


    /**
     * called on by menuViewAllBooks, menuViewAvailableBooks, etc.
     * Any time a list of books is required, it returns the desired type of list
     * @param viewBooksMessage is the instructions messages
     * @param listOfBooks is the list of books being displayed
     * @return String containing all the required books
     */
    private static String viewAnyBookList(String viewBooksMessage, ArrayList<Books> listOfBooks){
        StringBuilder booksString = new StringBuilder();
        booksString.append(viewBooksMessage);
        for (Books book :listOfBooks){
            if(book instanceof PhysicalBooks physicalBook){
                booksString.append(physicalBook.toString());
                booksString.append("\n");
            } else if(book instanceof AudioBooks audioBook){
                booksString.append(audioBook.toString());
                booksString.append("\n");
            }
        }
        return (booksString.toString());
    }

    /**
     * Used in menuLibraryData to check out books
     */
    private static void menuCheckoutBooks() {
        int id = getId();
        while (!data.checkExistMember(id)){
            System.out.println("Member does not exist. Please try again.\n");
            id = getId();
        }
        String title = getTitle();
        String author = getAuthor();
        while (!data.checkExistBook(title,author)){
            System.out.printf("The library does not own %s by %s. Please try again.\n",title,author);
            title = getTitle();
            author = getAuthor();
        }
        if (data.checkBookAvailable(title,author)){
            data.checkoutBook(id, title, author);
        }
        else {
            System.out.printf("%s by %s is currently checked out by another user.\n" ,title,author);
        }

    }

    /**
     * Used in menuLibraryData to return books
     */
    private static void menuReturnBooks(){
        int id = getId();
        while (!data.checkExistMember(id)){  //makes sure that this id corresponds to an existing member
            System.out.println("Member does not exist. Please try again.\n");
            id = getId();
        }
        String title = getTitle();
        String author = getAuthor();
        if (!data.checkExistBook(title,author)){ //if the books isn't in the system, no action is taken
            System.out.printf("The library does not own %s by %s. Please try again.\n",title,author);
        }
        else if (data.checkBookAvailable(title,author)){ //if the book has already been returned, no action is taken
            System.out.printf("%s by %s is not currently checked out by anyone. That is not our book.\n",title,author);
        }
        else { //if the book both exists and is not checked out, then it is returned to the library
            int daysOverdue = getDaysOverdue();
            System.out.printf("%s by %s has been returned to the library.\n",title,author);
            data.returnBook(id,title,author,daysOverdue);
        }
    }

    /**
     * Used in menuLibraryData to remove books
     */
    private static void menuRemoveBook() {
        boolean success;
        do {
            String title = getTitle();
            String author = getAuthor();
            success = data.removeBook(title, author);
            if (!success){
                System.out.printf("%s by %s does not exist in library. Please try again.\n",title,author);
            }
            else {
                System.out.printf("\n%s by %s was deleted from the library!\n",title,author);
            }
        } while (!success);
    }

    /**
     * Used in menuLibraryData to display the most popular book by genre based on user input and library data
     */
    private static void menuMostPopularBookByGenre(){
        String genre = getGenre();
        System.out.printf("The most popular %s book is....",genre);
        System.out.println(data.mostPopularBookByGenre(genre));
    }

    /**
     * Used in menuLibraryData to load books from a specified file.
     */
    private static void menuLoadBooks(){
        String filename;
        File file;
        do {
            do {
                System.out.println("Enter a filename: ");
                filename = scanner.nextLine().trim();
            } while (filename.isEmpty());
            file = new File(filename);
        } while (!file.exists()||!file.canRead());
        Data newData = BookRecords.load(file, data);
        if (newData != null) {
            data = newData;
        }
    }

    /**
     * Used in menuLibraryData to save books to a specified file.
     */
    private static void menuSaveBooks(){
        String filename;
        File file;
        do {
            do {
                System.out.println("Enter a filename: ");
                filename = scanner.nextLine().trim();
            } while (filename.isEmpty());
            file = new File(filename);
        } while (!file.exists()||!file.canWrite());
        if (BookRecords.save(file,data)){
            System.out.printf("Saved Book Data to file %s%n",filename);
        }
        else {
            System.err.printf("Failed to save file %s%n",filename);
        }
}

    /**
     * Used in menuLibraryData to add books to the Library's main dataset
     */
    private static void menuEnterNewBook() {
        boolean success = false;
        do {
            String title = getTitle();
            String author = getAuthor();
            String genre = getGenre();
            String availabilityStatus = getAvailabilityStatus();
            String type = getBookType();
            if(type.equals("AUDIO")){
                String narrator = getNarrator();
                success = data.storeNewAudioBook(title, author, narrator, genre, availabilityStatus);
            } else if(type.equals("PHYSICAL")){
                success = data.storeNewPhysicalBook(title, author, genre, availabilityStatus);
            }
            if (!success){
                System.out.println("Book already exists in library. Try again.");
            }
        } while (!success);
        System.out.println("Stored a new book!");
    }

    /**
     * Used in menuEnterNewBook
     * @return the narrator of the book
     */
    private static String getNarrator() {
        System.out.println("Enter Narrator Name: ");
        String narrator;
        do {
            narrator = scanner.nextLine().trim();
        } while (narrator.isEmpty());
        return narrator;
    }

    /**
     * Used in searchByBookType, menuEnterNewBook
     * @return the type of the book (physical or audio)
     */
    private static String getBookType(){
        System.out.println("Enter Book Type (PHYSICAL/AUDIO): ");
        String bookType;
        do {
            bookType = scanner.nextLine().trim().toUpperCase();
        } while (bookType.isEmpty()|| (!bookType.equals("PHYSICAL") && !bookType.equals("AUDIO")));
        return bookType;
    }

    /**
     * Used in menuEnterNewBook
     * @return the availability status of the book
     */
    private static String getAvailabilityStatus() {
        return "Available";  // by default, new books should be available
    }

    /** Called on by menuReturnBooks
     * Used to get the number of days a book is overdue from user input
     * @return integer value of days obtained from user input
     */
    public static int getDaysOverdue(){
        System.out.println("How many days overdue is the book? ");
        String days;
        do {
            days = scanner.nextLine().trim();
        } while (days.isEmpty()||days.contains("-")||days.contains(".")); //continues while selections are invalid
        return Integer.parseInt(days);
    }

    /**
     * Used in searchByGenre, menuMostPopularBookByGenre, menuEnterNewBook
     * @return the genre of the book
     */
    private static String getGenre() {
        ArrayList<String> genres = new ArrayList<>(); //each genre option is a string in this list
            genres.add("Blank"); //this is a placeholder that is never displayed to user
            genres.add("Fantasy");
            genres.add("General Fiction");
            genres.add("Historical Fiction");
            genres.add("Horror");
            genres.add("Literary");
            genres.add("Mystery");
            genres.add("Non-fiction");
            genres.add("Poetry");
            genres.add("Romance");
            genres.add("Science Fiction");
            genres.add("Thriller");
        StringBuilder genreList = new StringBuilder(); // formatted list of the genres with instructions
        genreList.append("Select a Genre from the Following List:\n");
        for (int i = 1; i < genres.size(); i++) {
            genreList.append(String.format("\t%d.%s\n", i, genres.get(i)));
        }
        System.out.println(genreList);
        String choice = ""; // will be used to store user input for genre index
        boolean run = true; // tracks when the while loop should run or not
        while (run) {
            choice = scanner.nextLine(); // gets input from user
            int option = Integer.parseInt(choice); // converts into to an int
            if (option > 0 && option < genres.size()) { // checks if selection is valid
                System.out.printf("Selected genre %d.%s%n", option, genres.get(option));
                run = false; // stop while-loop to return value
            }
            else { // iterates through loop again if the selection was not valid
                System.out.printf("There is no genre %d! %n", option);
            }
        }
        return genres.get(Integer.parseInt(choice)); // returns the selected genre
    }

    /**
     * Used in searchByAuthor, menuCheckoutBooks, menuReturnBooks, etc.
     * @return the author of the book
     */
    private static String getAuthor() {
        System.out.println("Enter Author Name: ");
        String author;
        do {
            author = scanner.nextLine().trim();
        } while (author.isEmpty());
        return author;
    }

    /**
     * Used in menuCheckoutBooks, menuReturnBooks, etc.
     * @return the name of the book
     */
    private static String getTitle() {
        System.out.println("Enter Book Title: ");
        String title;
        do {
            title = scanner.nextLine().trim();
        } while (title.isEmpty());
        return title;
    }

    //BELOW THIS POINT IS MEMBER MENU

    private static final ArrayList<String> options2 = new ArrayList<>();

    static {
        options2.add("Blank"); //placeholder for indexing purposes that is never seen by user
        options2.add("Add Member");
        options2.add("Remove Member");
        options2.add("View All Members");
        options2.add("Search Members");
        options2.add("Pay fines");
        options2.add("Average Days Overdue");
        options2.add("Most Active Child");
        options2.add("Exit to Main Menu");
    }
    private static String optMessage2 = """
    -------------MEMBER DATA-------------
    """;

    static {
        StringBuilder sb2 = new StringBuilder();
        sb2.append(optMessage2);
        for (int i = 1; i < options2.size(); i++) {
            sb2.append(String.format("\t%d.%s\n", i, options2.get(i)));
        }
        optMessage2 = sb2.toString();
    }

    /**
     * Used in menuLoop as a sub-menu to access Member Data
     */
    private static void menuMemberData() {
        int option2 = 0;
        boolean run = true;
        while (run) {
            System.out.println(optMessage2);
            String memchoice = scanner.nextLine();
            if(!memchoice.isEmpty() && !memchoice.equals("0")){
                option2 = Integer.parseInt(memchoice);
            }
            if (option2 > 0 && option2 < options2.size()) {
                System.out.printf("Selected option %d.%s%n", option2, options2.get(option2));
                switch (option2) {
                    case 1 -> menuAddMember();
                    case 2 -> menuRemoveMember();
                    case 3 -> menuViewAllMembers();
                    case 4 -> menuSearchMembers();
                    case 5 -> menuPayFines();
                    case 6 -> menuAverageDaysOverdue();
                    case 7 -> menuMostActiveChild();
                    //case 8 -> menuLoadMember();
                    //case 9 -> menuSaveMember();
                    case 8 -> run = false;
                    default -> System.out.printf("Option %d not recognized!%n\n", option2);
                }
            } else {
                System.out.printf("Option %d not recognized!%n\n", option2);
            }
        }
    }

    /**
     * Used in menuMemberData to add a new member to the Library's member list
     */
    private static void menuAddMember(){
        boolean success=false;
        do {
            Integer ID = getId();
            String name = getMember();
            String type = getMemberType();
            if (type.equals("ADULT")) {
                success = data.storeNewAdultMember(ID, name);
            } else if (type.equals("CHILD")) {
                success = data.storeNewChildMember(ID, name);
            }
            if (!success) {
                System.out.println("You cannot have two members with the same information.\n");
            }
        } while(!success);
        System.out.println("Stored a new member!\n");
    }

    /**
     * Used in menuMemberData to remove a member from the member list
     */
    private static void menuRemoveMember(){
        boolean success;
        do {
            int ID = getId();
            String name = getMember();
            success = data.removeMember(ID, name);
            if (!success){
                System.out.println("Member does not exist in Membership List. Please try again.\n");
            }
        } while (!success);
        System.out.println("Deleted a new member!\n");
    }

    /**
     * Used in menuMemberData to print the entire list of library members
     */
    private static void menuViewAllMembers(){
        String memMessage = """
            All Library Members:\n""";
        ArrayList<Member> members = data.getAllMembers();
        members.sort(new MemberNameComparator());
        System.out.println(viewAnyMemberList(memMessage, members));
    }

    /**
     * Used in menuMemberData to search for a member in the member list
     */
    private static void menuSearchMembers() {
        ArrayList<String> searchOptions = new ArrayList<>(); //each search option is a string in this list
        searchOptions.add("Blank"); //this is a placeholder that is never displayed to user
        searchOptions.add("Member Name");
        searchOptions.add("Member ID");
        searchOptions.add("Member Type");
        StringBuilder searchString = new StringBuilder(); // formatted list of the search options with instruction
        searchString.append("How would you like to search for members?\n");
        for (int i = 1; i < searchOptions.size(); i++) {
            searchString.append(String.format("\t%d.%s\n", i, searchOptions.get(i)));
        }
        System.out.println(searchString);
        String choice; // will be used to store user input
        boolean run = true; // tracks when the while loop should run or not
        while (run) {
            choice = scanner.nextLine(); // gets input from user
            int option = Integer.parseInt(choice); // converts into to an int
            if (option > 0 && option < searchOptions.size()) { // checks if selection is valid
                System.out.printf("Searching by %d.%s%n", option, searchOptions.get(option));
                run = false; // stop while-loop
            }
            switch(option){
                case 1 -> searchByName();//member name
                case 2 -> searchById();//member id
                case 3 -> searchByMemberType();//member type
                default -> System.out.printf("Option %d not recognized!%n", option);
            }
        }
    }

    /**
     * Used in menuSearchMembers to look for a member by name only
     */
    private static void searchByName() {
        String name = getMember();
        String viewMembersMessage = """
                All members with that name:""";
        ArrayList<Member> memberList = data.getMembersByName(name);
        memberList.sort(new MemberNameComparator());
        System.out.println(viewAnyMemberList(viewMembersMessage, memberList));
    }

    /**
     * Used in menuSearchMembers to look for a member by ID only
     */
    private static void searchById(){
        int id = getId();
        String viewMembersMessage = """
             Member with given ID:""";
        ArrayList<Member> memberList = data.getMembersById(id);
        memberList.sort(new MemberNameComparator());
        System.out.println(viewAnyMemberList(viewMembersMessage, memberList));
    }

    /**
     * Used in menuSearchMembers to look for a member by ID only
     */
    private static void searchByMemberType(){
        String type = getMemberType();
        String viewMembersMessage = """
             All members of that type:""";
        ArrayList<Member> memberList = data.getMembersByMemberType(type);
        memberList.sort(new MemberNameComparator());
        System.out.println(viewAnyMemberList(viewMembersMessage, memberList));
    }

    /**
     * called on by searchByName and searchById
     * @param viewMembersMessage is the instructions message
     * @param listOfMembers is the list of members being displayed
     * @return String containing all the required members
     */
    private static String viewAnyMemberList(String viewMembersMessage, ArrayList<Member> listOfMembers) {
        StringBuilder membersString = new StringBuilder();
        membersString.append(viewMembersMessage);
        for (Member member :listOfMembers){
            if(member instanceof AdultMember adultMember){
                membersString.append(adultMember.toString());
                membersString.append("\n");
            }else if(member instanceof ChildMember childMember){
                membersString.append(childMember.toString());
                membersString.append("\n");
            }
        }
        return (membersString.toString());
    }

    /**
     * Passes information to data.payFines so that a member's fines can be updated when they pay it
     * Prints member's information before and after they pay the fine
     */
    private static void menuPayFines(){
        int ID = getId();
        String viewMembersMessage = """
             Member's Information:""";
        System.out.println(viewAnyMemberList(viewMembersMessage,data.getMembersById(ID)));

        String input; //temporarily saving payment amount as a string for looping purposes
        do {
            System.out.println("\nEnter amount to be paid: ");
            input = scanner.nextLine().trim();
        }while (input.isEmpty()||!input.contains(".")||input.contains("-")||input.contains("$"));
        input = String.format("%.2f",Double.parseDouble(input));
        Double payment = Double.parseDouble(input);
        if(data.payFines(ID,payment)){ //calls of data.payFines, which updates the fine info as long as it's valid
            System.out.println("Payment successful!\n");
            viewMembersMessage = """
             Member's Updated Information:""";
            System.out.println(viewAnyMemberList(viewMembersMessage,data.getMembersById(ID)));
        }
        else{
            System.out.println("Invalid payment. You cannot pay more than the amount owed.\n");
        }
    }

    /**
     * Used in menuLoadAllData , menuMemberData to load member information from a specified filename
     */
    private static void menuLoadMember(){
        String filename;
        File file;
        do {
            do {
                System.out.println("Enter a filename: ");
                filename = scanner.nextLine().trim();
            } while (filename.isEmpty());
            file = new File(filename);
        } while (!file.exists()||!file.canRead());
        Data newData = MemberRecords.load(file, data);
        if (newData != null){
            data = newData;
        }
    }

    /**
     * Used in menuSaveAllData , menuMemberData to save member information to a specified filename
     */
    private static void menuSaveMember(){
        String filename;
        File file;
        do {
            do {
                System.out.println("Enter a filename: ");
                filename = scanner.nextLine().trim();
            } while (filename.isEmpty());
            file = new File(filename);
        } while (!file.exists()||!file.canWrite());
        if (MemberRecords.save(file,data)){
            System.out.printf("Saved Member Data to file %s%n",filename);
        }
        else {
            System.err.printf("Failed to save file %s%n",filename);
        }
    }

    /**
     * Used in menuMemberData to calculate and display the average days overdue by the member
     */
    private static void menuAverageDaysOverdue(){
        System.out.printf("On average, books are returned %d days late.%n",data.getAverageDaysOverdue());
    }


    /**
     * Used in menuMemberData to get the most active child reader in the library system
     */
    private static void menuMostActiveChild(){
        String childString = data.getMostActiveChild();
        if (childString != null){
            System.out.print("Congratulations to our Most Active Child Reader!");
            System.out.println(data.getMostActiveChild());
        } else {
            System.out.println("There are currently no children at the library.");
        }

    }


    /** called by menuAddMember, menuRemoveMember, searchByName
     * Used to get the member name
     * @return member name obtained from user input
     */
    private static String getMember() {
        String member;
        do {
            System.out.println("Enter Member Name: ");
            member = scanner.nextLine().trim();
        } while (member.isEmpty());
        return member;
    }

    /** called by menuCheckoutBooks, menuReturnBooks, menuAddMember, menuRemoveMember, searchById
     * Used to get the member Id
     * @return return Integer value of member ID obtained from user input
     */
    private static int getId() {
        String memberId;
        do {
            System.out.println("Enter Member ID (integer value): ");
            memberId = scanner.nextLine().trim();
        }while (memberId.isEmpty());
        return Integer.parseInt(memberId);
    }

    /**
     * Used to get the member age category (child or adult)
     * @return String value of member type obtained from user input
     */
    private static String getMemberType() {
        String memberType;
        do {
            System.out.println("Is this member a child or adult? ");
            memberType = scanner.nextLine().trim().toUpperCase();
        } while (memberType.isEmpty()||(!memberType.equals("CHILD")&&!memberType.equals("ADULT")));
        return memberType;
    }


}