package first.second.third.fuckmylife.service;

import first.second.third.fuckmylife.Entity.LoginData;
import first.second.third.fuckmylife.Entity.User;

import java.util.List;

public interface UserService {
    void save(User user);
    User auth(LoginData loginData);
    User getByUsername(String username);
    void addToBlackList(Long id);
    List<User> getAllPrivUsers();
    List<User> getAllBlacklisted();
    void changeUserRole(String username, String role);
}
