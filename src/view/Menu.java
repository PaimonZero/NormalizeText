
package view;

import java.util.ArrayList;
import java.util.Scanner;

    public abstract class Menu<T> {
    
    protected String title;
    protected ArrayList<String> list = new ArrayList();
    
    public Menu(){}
    public Menu(String title, String[] s) {
        this.title = title;
        for (String item : s) {
            list.add(item);
        }
    }

    public void display() {
        System.out.println(title);
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i));
        }
    }

    public int getChoice() {  
        System.out.print("Enter selection: ");
        int choice = inputIntNoMsg();
        return choice;
    }
    
    // Get an user choice as a object in the list 
    public T getObjectChoice(ArrayList<T> list){
        Scanner sc = new Scanner(System.in);
        int n = list.size();
        for(int i = 0; i < n;i++){
            System.out.println((i+1) + " - " + list.get(i));
        }
        System.out.println("------------------------------------");
        System.out.println("Please choose  1.." + list.size() + ": ");
        int choiceNo = Integer.parseInt(sc.nextLine());
        return (choiceNo > 0 && choiceNo <= n) ? list.get(choiceNo - 1) : null;
    }

    public abstract void execute(int choice);

    public void run() {
        int choice;
        do {
            display();                      //list the content of menu
            choice = getChoice();
            execute(choice); 
        } while (choice != list.size());   //choice > 0 && choice < list.size()

    }
    
    public int inputIntNoMsg() {
        Scanner sc = new Scanner(System.in);
        int result;
        do {  
            try {
                result = Integer.parseInt(sc.nextLine());
                if(result <= 0) {            //Xử lý yêu cầu chi tiết, ngoài luồng
                    System.out.println("Value must be larger than 0");
                } else {return result;}     //break;
            } catch (NumberFormatException e) {
                System.err.println("Error input! Pls try again!");
                System.out.println("Enter selection: ");
            }
        } while (true);
    }
}

    