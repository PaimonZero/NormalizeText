
package executed;

import controller.NormalizeText;
import model.ReadAndWriteFile;
import view.Menu;

public class Main extends Menu{
    NormalizeText nText = new NormalizeText();
    ReadAndWriteFile file = new ReadAndWriteFile();
    
    public Main(){
        super("\n-*-*-*-*-*Normalize Text*-*-*-*-*-", 
             new String[] {"Use default file in/out in executed!","Use your file in/out", "Exit!"});
    }
    
    public static void main(String[] args) {
        Main hi = new Main();
        hi.run();
        
    }
    
//    public static void main(String[] args) {
//        NormalizeText nText = new NormalizeText();
//        ReadAndWriteFile file = new ReadAndWriteFile();
//        nText.loadData();
//        nText.normalizeText();
//        nText.writeData();
//    }

    @Override
    public void execute(int choice) {
        switch (choice) {
            case 1:
                nText.loadData();
                nText.normalizeText();
                nText.writeData();
                break;
            case 2:
                nText.getPathFromUser();
                nText.loadData();
                nText.normalizeText();
                nText.writeData();
                break;
            case 3:
                System.out.println("Program closing!");
                break;
            default:
                throw new AssertionError();
        }
    }
}
