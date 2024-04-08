package sea.cpsc233projectgui;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        if(args.length >2){
            System.err.println("Expected two command line argument for filename to load from");
        }
        if(args.length == 2){
            String bookFilename = args[0];
            String memberFilename = args[1];
            File bookFile = new File(args[0]);
            File memberFile = new File(args[1]);
            if(!bookFile.exists()||!bookFile.canRead()){
                System.err.println("Cannot load from the file " + bookFilename);
                System.exit(2);
            }
            if(!memberFile.exists()||!memberFile.canRead()){
                System.err.println("Cannot load from the file " + memberFilename);
                System.exit(2);
            }
            Menu.menuLoop(bookFile,memberFile);
        }else {
            Menu.menuLoop(null,null);
        }
    }
}