package dao;

import JavaMySql.JavaMySql;
import entity.Role;
import entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserDao {
    //Attributs
    private static Connection conn;

    //Methodes

    //Saisie d'un utilisateur
    public User saisieUser(){
        Scanner scanner=new Scanner(System.in);
        User user = new User();
        user.saisie();
        return user;
    }
    //Inserer un nouvel utilisateur dans la base de donnee
    public void insertUser(User user){
        try {
            //connection a la base de donnees
            conn = JavaMySql.getConnection();

            //Prepare une requete sql insert afin d'ajouter un nouvel utilisateur
            PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO user(email, password, passwordHashed, idRole) VALUES (?,?,?,?);");

            //Remplace le premier paramètre de la déclaration par la valeur de l'email de l'utilisateur.
            statement.setString(1,user.getEmail());
            //Remplace le second paramètre de la déclaration par la valeur du mot de passe de l'utilisateur.
            statement.setString(2,user.getPassword());
            //Remplace le troisieme paramètre de la déclaration par la valeur du mot de passe haché de l'utilisateur.
            statement.setString(3,user.getPasswordHashed());
            //Remplace le dernier paramètre de la déclaration par lid du rôle de l'utilisateur.
            statement.setInt(4,user.getIdRole());
            //Execute la requête prepare stocke dans la variable statement
            statement.execute();
            System.out.println("Utilisateur enregistre avec succes!");
            System.out.println("-----------------------------------------");
        } catch (SQLException e) {
            //Affiche le message d'erreur en cas d'echec d'insertion
            System.err.println("Erreur : " + e.getMessage());
        }
        //fermeture de la connexion
        JavaMySql.closeConnection(conn);
    }

    //Recuperation des utilisateurs enregistres depuis la base de donnees
    public List<User> getUsers(){
        //Connexion a la base de donnees
        conn = JavaMySql.getConnection();
        //Liste devant garder les utilisateurs récupérés
        List<User> usersList = new ArrayList<>();
        try {
            //Creation d'un objet statement
            //Permet de creer des requetes sql basiques
            Statement statement = conn.createStatement();
            //Execution de la requete de selection de tous les utilisateurs de la table user.
            //Stockage du resultat de la requete dans la variable users
            ResultSet users = statement.executeQuery("SELECT * FROM user");
            //Parcours continuant tant que la variable users contient des lignes à traiter
            while (users.next()){
                //Creation d'un utilisateur et stockage des donnees correspondant
                User user=new User();
                user.setId(users.getInt("idUser"));
                user.setEmail(users.getString("email"));
                user.setPassword(users.getString("password"));
                user.setPasswordHashed(users.getString("passwordHashed"));
                user.setIdRole(users.getInt("idRole"));
                //Ajout des utilisateurs dans la liste users
                usersList.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Erreur : " + e.getMessage());
        }
        //Fermeture de la connexion
        JavaMySql.closeConnection(conn);
        //Retourner la liste contenant les utilisateurs
       return usersList;
    }

    //Affichage des donnees d'un utilisateur
    public void showUser(User user){
        RoleDao role = new RoleDao();
        if (user!=null){
            System.out.println("Id: " + user.getId());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Mot de passe: "+ user.getPassword());
            System.out.println("Mot de passe hashe: " + user.getPasswordHashed());
            System.out.println("idRole: "+ role.getRoleById(user.getIdRole()).getName());
        }

    }

    //Affichage des donnees de tous les utilisateurs
    public void listerUsers(List<User> userList){
        if (!userList.isEmpty()){
            System.out.println("----------------------------");
            System.out.println("Liste des Utilisateurs");
            System.out.println("----------------------------");
            for (int i = 0; i < userList.size(); i++) {
                //System.out.println(i+" id: " + roleList.get(i).getId());
                showUser(userList.get(i));
                System.out.println();
            }
        }else{
            System.out.println("----------------------------");
            System.out.println("Aucun utilisateur enregistre!");
            System.out.println("----------------------------");
        }

    }

    //Affichage des informations de l'utilisateur connecte
    public void welcomeUser(User user){
        if (user!=null){
            System.out.println("----------------------------");
            System.out.println("BIENVENUE");
            System.out.println("----------------------------");
            showUser(user);
        }else{
            System.out.println("----------------------------");
            System.out.println("Aucun utilisateur correspondant!");
            System.out.println("----------------------------");
        }

    }


    //Rechercher un utilisateur en fonction d'un email et d'un mot de passe donné
    public User foundUser(String email, String password){
        try {
            //Connexion a la base de donnees
            conn=JavaMySql.getConnection();
            //Prepare une requete sql select en fonction de l'email et du mot de passe
            //Stockage de la requete dans la variable statement
            PreparedStatement statement=conn.prepareStatement("SELECT * FROM user WHERE email=? and password=?");
            //Affectation des valeurs de la requete prepare
            statement.setString(1,email);
            statement.setString(2,password);
            ResultSet userCorrespondant = statement.executeQuery();
            //Creation d'un utilisateur
            // stockage dans la variable user
            User user=new User();

            while (userCorrespondant.next()){
                //Affectation des donnees de l'utilisateur correspondant
                user.setId(userCorrespondant.getInt("idUser"));
                user.setEmail(userCorrespondant.getString("email"));
                user.setPassword(userCorrespondant.getString("password"));
                user.setPasswordHashed(userCorrespondant.getString("passwordHashed"));
                user.setIdRole(userCorrespondant.getInt("idRole"));
            }
            //Verifie qu'un utilisateur a bien été trouvé
            if (user.getId() != 0){
                return user;
            }
            return null;
        } catch (SQLException e) {
            //Affichage de l'erreur si il y'a une erreur lors de l'execution de la requete
            System.err.println("Erreur : " + e.getMessage());
        }
        //Fermeture de la connexion
        JavaMySql.closeConnection(conn);
        return null;
    }

}
