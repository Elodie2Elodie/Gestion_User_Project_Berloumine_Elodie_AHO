package JavaMySql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JavaMySql {

    //Variable driver contenant le nom de la classe du pilote JDBC pour la base de donnée MySql.
    protected static String driver = "com.mysql.cj.jdbc.Driver";
    protected static String database = "jdbc:mysql://localhost:3306/gestion_utilisateurs";
    protected static String userName = "root";
    protected static String password = "";

    //Etablir une connexion avec la base de donnees
    public static Connection getConnection(){
        try {

            //Charge la classe du pilote de base de données.
            Class.forName(driver);
            //Etablit une connexion avec la base de donnee specifie ici dans database  et le retourne un objet de type Connexion
            return DriverManager.getConnection(database, userName, password);
//            System.out.println("Connecte");
        }catch (Exception e){
            //Affiche l'exception soulevée en cas d'echec de connexion
            System.out.println(e.getMessage());
        }
        return null;
    }

    //Fermeture de la connexion a la base de donnees
    public static void closeConnection(Connection connection) {
        try {
            //Si une connexion est etablit et si elle n'a pas déjà été fermé
            if (connection != null && !connection.isClosed()) {
                //Ferme la connexion
                connection.close();
//                System.out.println("Connexion fermée");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
        }
    }
}
