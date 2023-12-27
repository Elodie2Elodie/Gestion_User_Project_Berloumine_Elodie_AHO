package entity;

import dao.RoleDao;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class User {
    //Attributs
    private int id;

    private String email;

    private String password;

    private String passwordHashed;

    private int idRole;

    //Constructeurs
    public User() {
    }

    public User(String email, String password, String passwordHashed, int idRole) {
        //this.id = id;
        this.email = email;
        this.password = password;
        this.passwordHashed = passwordHashed;
        this.idRole = idRole;
    }

    //Getters ou Accesseurs
    public int getId() {
        return id;
    }


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordHashed() {
        return passwordHashed;
    }

    public int getIdRole() {
        return idRole;
    }

    //Setters ou Modificateurs
    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordHashed(String passwordHashed) {
        this.passwordHashed = passwordHashed;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    //Methodes
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", passwordHashed='" + passwordHashed + '\'' +
                ", idRole=" + idRole +
                '}';
    }

    //saisie d'un utilisateur
    public void saisie() {
        Scanner scanner = new Scanner(System.in);
        List<Role> roles = new ArrayList<>();
        RoleDao role = new RoleDao();
        roles = role.getRoles();
        boolean isValid;
        //Verifie qu'il existe au moins un role dans le tableau de role
        if (!roles.isEmpty()) {
            System.out.println("Email: ");
            email = scanner.nextLine();
            System.out.println("Mot de passe: ");
            password = scanner.nextLine();
            passwordHashed = hashagePasswordWithBcrypt(password);

            //Controle de la saisie de l'id du role
            do {
                isValid = false;
                System.out.println("Roles disponibles");
                System.out.println("--------------------");
                role.listerRoles(roles);
                //S'assure que la vleur saisie sera un entier
                while (!isValid) {
                    try {
                        System.out.println("Choisissez un role: ");
                        idRole = scanner.nextInt();
                        isValid = true;
                    } catch (java.util.InputMismatchException e) {
                        System.out.println("Erreur : Veuillez saisir un entier valide.");
                        scanner.next();
                    }
                }

            } while (idRole < 1 || idRole > roles.size());
        } else {
            System.out.println("Enregistrement impossible: Aucun roles disponibles");
            System.out.println("--------------------");
        }
    }

    //Hashage du mot de passe
    public String hashagePasswordWithBcrypt(String password) {

        // Génération d'un sel avec BCrypt
        //Permet d'avoir des cryptage différent pour un même mot de passe
        String sel = BCrypt.gensalt();

        // Hachage du mot de passe avec BCrypt et le sel généré
        return BCrypt.hashpw(password, sel);

    }

    //Verification du mot de passe
    public boolean verifPassword(String password) {
        //Verifie si le mot de passe donnee correspond au mot de passe crypte de l'utilisateur
        //Fonctionne en extrayant le sel du mot de passe crypte, l'utilise pour crypter le mot de passe donner puis effectue une comparaison
        //Renvoie true si la comparaison indique qu'ils sont identiques sinon false
        return BCrypt.checkpw(password, this.passwordHashed);
    }

    //Cryptage en s'inspirant du chiffrement de César
    public String encryptWithCesar(String password, int decalage) {
        StringBuilder resultat = new StringBuilder();
        //Parcours le password lettre par lettre
        for (int i = 0; i < password.length(); i++) {
            //Recupere le caractere a la position i actuelle et le stocke dans la variable caractere
            char caractere = password.charAt(i);

            // Vérifie si le caractère est une lettre
            if (Character.isLetter(caractere)) {
                //Verifie si le caractere est en majuscule ou en minuscule
                char base = Character.isUpperCase(caractere) ? 'A' : 'a';
                //Effectue le décalage circulaire sur l'alphabet.
                caractere = (char) ((caractere - base + decalage) % 26 + base);
                resultat.append(caractere);
            } else {
                // Ajoute le caractère tel quel s'il n'est pas une lettre
                resultat.append(caractere);
            }
        }

        return resultat.toString();
    }

    public String decryptWithCesar(String encryptedPassword, int decalage) {
        StringBuilder resultat = new StringBuilder();

        for (int i = 0; i < encryptedPassword.length(); i++) {
            char caractere = encryptedPassword.charAt(i);

            // Vérifie si le caractère est une lettre
            if (Character.isLetter(caractere)) {
                // Verifie si le caractere est en majuscule ou en minuscule
                char base = Character.isUpperCase(caractere) ? 'A' : 'a';

                // Effectue le décalage inverse sur l'alphabet
                caractere = (char) ((caractere - base - decalage + 26) % 26 + base);
                // Ajoute le caractere correspondant quel s'il n'est pas une lettre
                resultat.append(caractere);
            } else {
                // Ajoute le caractère tel quel s'il n'est pas une lettre
                resultat.append(caractere);
            }
        }
        return resultat.toString();
    }
}
