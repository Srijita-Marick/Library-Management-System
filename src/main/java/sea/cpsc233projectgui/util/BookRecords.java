package sea.cpsc233projectgui.util;

import sea.cpsc233projectgui.Data;
import sea.cpsc233projectgui.objects.Books;
import sea.cpsc233projectgui.objects.PhysicalBooks;
import sea.cpsc233projectgui.objects.AudioBooks;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class BookRecords {
    /**
     * Saves current Book information to a file
     * @param file  the file that information is being saved to
     * @param data  the data object that is being saved
     * @return      whether saving was successful
     */
    public static boolean save(File file, Data data) {
        try (FileWriter fw = new FileWriter(file)) {
            fw.write("Books\n");

            for (Books book : data.getAllBooks()) {
                if (book instanceof PhysicalBooks physicalBooks) {
                    fw.write(String.format("PHYSICAL,%s,%s,%s,%s", physicalBooks.getTitle(), physicalBooks.getAuthor(), physicalBooks.getGenre(), physicalBooks.getAvailabilityStatus()));

                    fw.write("\n"); //new line after each book
                } else if (book instanceof AudioBooks audioBooks) {
                    fw.write(String.format("AUDIO,%s,%s,%s,%s,%s", audioBooks.getTitle(), audioBooks.getAuthor(), audioBooks.getGenre(), audioBooks.getAvailabilityStatus(), audioBooks.getNarrator()));
                   fw.write("\n"); //new line after each book
                }
            }
            fw.flush();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Updates Data object based on new Book information from a file
     * @param file  the file being loaded from
     * @return      the new Data object with information from the file (returns null if loading unsuccessful)
     */
    public static Data load(File file, Data data) {
        try (Scanner scanner = new Scanner(file)) {
            String line = scanner.nextLine();
            if (!line.equals("Books")) {
                System.err.println("File did not have correct header, so loading failed.");
                return null;
            }

            for (Books book : data.getAllBooks()) {
                data.removeBook(book.getTitle(), book.getAuthor());
            }

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                String[] parts = line.split(",");
                String type = parts[0];
                String title = parts[1];
                String author = parts[2];
                String genre = parts[3];
                String availabilityStatus = parts[4];

                if (type.equals("PHYSICAL")) { // adds new PhysicalBook
                    data.storeNewPhysicalBook(title, author, genre, availabilityStatus);

                } else if (type.equals("AUDIO")) { // adds new AudioBook
                    String narrator = parts[5];
                    data.storeNewAudioBook(title, author, narrator, genre, availabilityStatus);
                }
            }
            scanner.close();
        } catch (IOException e) {
            System.err.println("Incorrect file format. Loading failed.");
            return null;
        }
        return data;
    }

}
