package dev.Service;

import dev.Repository.database.DBUtil;
import dev.domain.User;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserService {

    private DBUtil dbUtil;

    public UserService(DBUtil dbUtil) {
        this.dbUtil = dbUtil;
    }

    public static User create(User user) throws SQLException, ClassNotFoundException {
        DBUtil.create(user);
        return user;
    }

    public static User update(User user) throws SQLException, ClassNotFoundException {
        DBUtil.update(user);
        return user;
    }

    public static User get(int Id) throws SQLException, ClassNotFoundException {
        return DBUtil.searchUser(Id);
    }

    public static void delete(Integer user) throws SQLException, ClassNotFoundException {
        DBUtil.delete(user);
    }

    public static List<User> getAlluser() throws SQLException, ClassNotFoundException {
       return DBUtil.getAllUsers();
    }
    public static int countAlluser() throws SQLException, ClassNotFoundException {
        return DBUtil.countAlluser();
    }
}