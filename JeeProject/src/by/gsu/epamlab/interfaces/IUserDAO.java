package by.gsu.epamlab.interfaces;
import by.gsu.epamlab.beans.User;
import by.gsu.epamlab.enums.Role;
import by.gsu.epamlab.exceptions.DAOException;

public interface IUserDAO {
       public User getUser(String name, String pass) throws DAOException;
       public void addUser(String login, String password,Role role, String email, String phone) throws DAOException;
}
