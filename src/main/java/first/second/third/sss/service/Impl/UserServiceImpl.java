package first.second.third.fuckmylife.service.Impl;

import first.second.third.fuckmylife.Entity.LoginData;
import first.second.third.fuckmylife.Entity.User;
import first.second.third.fuckmylife.dao.UserDao;
import first.second.third.fuckmylife.service.UserService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    @Override
    public void save(User user) {
        try {
            user.setRole("USER");
            userDao.save(user);
        } catch (Exception e){
            throw new ServiceException("User with this username already exists");
        }
    }

    @Transactional
    @Override
    public User auth(LoginData loginData) {
        try{
            return userDao.auth(loginData);
        } catch (Exception e) {
            throw new ServiceException("Wrong login or password");
        }
    }

    @Transactional
    @Override
    public User getByUsername(String username){
        try{
        return userDao.getByUsername(username);
        } catch (Exception e) {
            throw new ServiceException("User not found");
        }
    }

    @Transactional
    @Override
    public void addToBlackList(Long id) {
        try {
            userDao.addToBlackList(id);
        } catch (Exception e) {
            throw new ServiceException("User not found");
        }
    }

    @Transactional
    @Override
    public List<User> getAllPrivUsers() {
        try {
            return userDao.getAllPrivUsers();
        } catch (Exception e) {
            throw new ServiceException("Users not found");
        }
    }

    @Transactional
    @Override
    public List<User> getAllBlacklisted() {
        try {
            return userDao.getAllBlacklisted();
        } catch (Exception e) {
            throw new ServiceException("Users not found");
        }
    }

    @Transactional
    @Override
    public void changeUserRole(String username, String role){
        try {
            userDao.changeUserRole(username, role);
        } catch (Exception e) {
            throw new ServiceException("User not found");
        }
    }
}
