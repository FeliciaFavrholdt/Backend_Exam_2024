package dk.studiofavrholdt.security.daos;

import dk.studiofavrholdt.security.entities.User;
import dk.studiofavrholdt.security.exceptions.ValidationException;
import dk.bugelhartmann.UserDTO;

public interface ISecurityDAO {
    UserDTO getVerifiedUser(String username, String password) throws ValidationException;
    User createUser(String username, String password);
    User addRole(UserDTO user, String newRole);
}
