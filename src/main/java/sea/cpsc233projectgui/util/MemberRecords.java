package sea.cpsc233projectgui.util;

import sea.cpsc233projectgui.Data;
import sea.cpsc233projectgui.objects.AdultMember;
import sea.cpsc233projectgui.objects.ChildMember;
import sea.cpsc233projectgui.objects.Member;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class MemberRecords {

    /**
     * Saves current Member information to a file
     * @param file  the file that information is being saved to
     * @param data  the data object that is being saved
     * @return      whether saving was successful
     */

    public static boolean save(File file, Data data) {
        try (FileWriter fw = new FileWriter(file)) {
            fw.write("Members\n");
            for (Member member : data.getAllMembers()) {
                if (member instanceof AdultMember adultMember) {
                    fw.write(String.format("ADULT,%s,%s,%s", adultMember.getID(), adultMember.getName(), adultMember.getFines()));
                    for (String book : adultMember.getBorrowed()) {
                        fw.write(String.format(",%s", book));
                    }
                    fw.write("\n"); //new line after each member
                } else if (member instanceof ChildMember childMember) {
                    fw.write(String.format("CHILD,%s,%s", childMember.getID(), childMember.getName()));
                    for (String book : childMember.getBorrowed()) {
                        fw.write(String.format(",%s", book));
                    }
                    fw.write("\n"); //new line after each member
                }
            }
            fw.flush();
            return true;
        } catch (IOException e) {
            return false;
        }

    }

    /**
     * Updates Data object based on new Member information from a file
     * @param file  the file being loaded from
     * @return      the new Data object with information from the file (returns null if loading unsuccessful)
     */
    public static Data load(File file, Data data) {

        try (Scanner scanner = new Scanner(file)) {
            String line = scanner.nextLine();
            if (!line.equals("Members")) {
                System.err.println("File did not have correct header, so loading failed.");
                return null;
            }

            for (Member member:data.getAllMembers()){ //removes pre-existing member information
                data.removeMember(member.getID(), member.getName());
            }

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                String[] parts = line.split(",");
                String type = parts[0];
                int id = Integer.parseInt(parts[1]);
                String name = parts[2];

                if (type.equals("ADULT")){ // adds new AdultMember
                    data.storeNewAdultMember(id,name);
                    AdultMember adultMember = (AdultMember) (data.getMembersById(id)).getFirst();
                    double fines =  Double.parseDouble(parts[3]);
                    adultMember.setFines(fines);
                    for (int i=4;i<parts.length;i++){
                        adultMember.addBookToMember(parts[i]);
                    }
                } else if (type.equals("CHILD")) { //adds new ChildMember
                    data.storeNewChildMember(id,name);
                    ChildMember childMember = (ChildMember) (data.getMembersById(id)).getFirst();
                    for (int i=3;i<parts.length;i++){
                        childMember.addBookToMember(parts[i]);
                    }
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