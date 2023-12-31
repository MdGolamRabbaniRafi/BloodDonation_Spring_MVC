package Rest;

import dev.domain.User;
import dev.Service.UserService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class RestControllerOfUser {


    @GetMapping("/users")
    public List<User> getUsers() throws SQLException, ClassNotFoundException {
        return UserService.getAlluser();
    }
    @GetMapping("/users/count")
    public int countUsers() throws SQLException, ClassNotFoundException {
        return UserService.countAlluser();
    }
    @PutMapping("/users")
    public User updateUser(@RequestBody User user) throws SQLException, ClassNotFoundException {
        return UserService.update(user);
    }
    @PostMapping("/users")
    public User addUser(@RequestBody User user) throws SQLException, ClassNotFoundException {
        return UserService.create(user);
    }
    @GetMapping("/users/{id}")
    public User addUser(@PathVariable("id") Integer id) throws SQLException, ClassNotFoundException {
        return UserService.get(id);
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable("id") Integer id) throws SQLException, ClassNotFoundException {
        UserService.delete(id);
        return "Deleted";
    }
}