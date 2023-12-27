package dao;

import JavaMySql.JavaMySql;
import entity.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDao {
    //Attributs
    private static Connection conn;

    //Methodes

    //Recuperer les roles depuis la base de donnees
    public List<Role> getRoles(){
        List<Role> RolesList = new ArrayList<>();
        try {
            //Connexion a la base donnees
            conn=JavaMySql.getConnection();
            Statement statement=conn.createStatement();
            ResultSet Roles = statement.executeQuery("SELECT * FROM role;");
            //Stockage des roles
            while (Roles.next()){
                Role role=new Role();
                role.setId(Roles.getInt("id"));
                role.setName(Roles.getString("name"));
                RolesList.add(role);
            }
        } catch (SQLException e) {
            System.err.println("Erreur : " + e.getMessage());
        }
        //Fermeture de la connexion a la base donnees
        JavaMySql.closeConnection(conn);
        return RolesList;
    }

    //Lister les roles
    public void listerRoles(List<Role> roleList){

        for (int i = 0; i < roleList.size(); i++) {
            //System.out.println(i+" id: " + roleList.get(i).getId());
            System.out.println((i+1)+"-> " + roleList.get(i).getName());
        }
    }

    //Recuperer un role en fonction de son id
    public Role getRoleById(int idRole){
        try {
            //Connexion a la base dee donnees
            conn=JavaMySql.getConnection();
            //Requete preparé pour trouver un role en fonction de son id
            PreparedStatement statement=conn.prepareStatement("SELECT * FROM role WHERE id=?");
            //Affectation du parametre idRole au parametre id de la requete préparé
            statement.setInt(1,idRole);
            //Exécution et Recupération du résultat de la requete
            ResultSet roleCorrespondant = statement.executeQuery();
            //Creation d'une variable role
            Role role=new Role();
            //Stockage du role trouve dans la variable role
            while (roleCorrespondant.next()){
                role.setId(roleCorrespondant.getInt("id"));
                role.setName(roleCorrespondant.getString("name"));
            }
            return role;
        } catch (SQLException e) {
            System.err.println("Erreur : " + e.getMessage());
        }
        //Fermeture de la connexion a la base dee donnees
        JavaMySql.closeConnection(conn);
        return null;
    }


}
