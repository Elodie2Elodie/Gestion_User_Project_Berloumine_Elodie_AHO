package main;

import dao.UserDao;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        //Variables
        int choix=0;
        Scanner scanner=new Scanner(System.in);
        UserDao userdao = new UserDao();
        boolean isValid;

        do {
            //MENU
            System.out.println("-----------------------------");
            System.out.println("Menu");
            System.out.println("-----------------------------");
            do {
                isValid = false;
                System.out.println("1-Se connecter");
                System.out.println("2-Ajouter un utilisateur");
                System.out.println("3-Lister les utilisateurs");
                System.out.println("4-Quitter");
                //S'assure que un seul un entier sera pris en compte
                while (!isValid){
                    try {
                        System.out.println("Faites un choix: ");
                        //scanner.next();
                        choix=scanner.nextInt();
                        //Indique que un entier a été saisie et permet de sortir de la boucle
                        isValid = true;
                    }catch (java.util.InputMismatchException e){
                        System.out.println("Erreur : Veuillez saisir un entier valide.");
                        scanner.next();
                    }
                }
            }while (choix<1 || choix>4);

            switch (choix){
                case 1:
                    scanner.nextLine();
                    System.out.println("---------------------------");
                    System.out.println("CONNEXION");
                    System.out.println("---------------------------");
                    System.out.println("Email: ");
                    String email = scanner.nextLine();
                    System.out.println("Mot de passe: ");
                    String password = scanner.nextLine();
                    if (userdao.foundUser(email,password)!=null && userdao.foundUser(email,password).verifPassword(password)){
                        userdao.welcomeUser(userdao.foundUser(email,password));
                    }else {
                        System.out.println("----------------------------");
                        System.out.println("Aucun utilisateur correspondant!");
                        System.out.println("----------------------------");
                    }

                    break;
                case 2:
                    userdao.insertUser(userdao.saisieUser());
                    break;
                case 3:
                    userdao.listerUsers(userdao.getUsers());
                    break;
                case 4:
                    System.exit(0);
                    break;

            }
        }while (true);

    }


}