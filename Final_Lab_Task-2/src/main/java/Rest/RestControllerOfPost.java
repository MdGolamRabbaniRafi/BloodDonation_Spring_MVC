package Rest;

import dev.Service.PostService;
import dev.domain.Post;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class RestControllerOfPost {

    @GetMapping(("/allPost"))
    public List<Post> getAllPosts() throws SQLException, ClassNotFoundException {
        return PostService.getAllPosts();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable("id") Integer id) throws SQLException, ClassNotFoundException {
        return PostService.getById(id);
    }
    @GetMapping("/pendingPosts")
    public List<Post> getPendingPosts() throws SQLException, ClassNotFoundException {
        return PostService.getPostsByStatus();
    }

    @GetMapping("/openPosts")
    public List<Post> getOpenPosts() throws SQLException, ClassNotFoundException {
        return PostService.getPostsByStatus2();
    }

    @PostMapping("/add")
    public Post addPost(@RequestBody Post post) throws SQLException, ClassNotFoundException {
        return PostService.create(post);
    }
    @PostMapping("/help/{id}/{ID}")
    public Post HelpPost(@RequestBody Post post) throws SQLException, ClassNotFoundException {
        return PostService.create(post);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable("id") Integer id, @RequestBody Post post) throws SQLException, ClassNotFoundException {
        return PostService.update(id, post);
    }

    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable("id") Integer id) throws SQLException, ClassNotFoundException {
        PostService.delete(id);
        return "Deleted";
    }
    @PostMapping("/changeStatus/{id}")
    public String changePostStatus(@PathVariable("id") Integer id, @RequestBody String newStatus) throws SQLException, ClassNotFoundException {
        // Fetch the post by ID from the database

        PostService.changePostStatus(id,newStatus);

        return "ok";
    }

    @GetMapping("/count")
    public int countPosts() throws SQLException, ClassNotFoundException {
        return PostService.count();
    }
}
