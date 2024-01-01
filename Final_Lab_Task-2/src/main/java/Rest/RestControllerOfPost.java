package Rest;

import dev.Service.PostService;
import dev.Service.UserService;
import dev.domain.AvailableForDonation;
import dev.domain.CompletePost;
import dev.domain.Post;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/posts")
public class RestControllerOfPost {

    @GetMapping(("/allPost"))
    public List<Post> getAllPosts() throws SQLException, ClassNotFoundException {
        return PostService.getAllPosts();
    }
    @GetMapping("/totaldonation/count")
    public int countUsers() throws SQLException, ClassNotFoundException {
        return PostService.countTotalDonation();
    }
    @GetMapping("/{id}")
    public Post getPostById(@PathVariable("id") Integer id) throws SQLException, ClassNotFoundException {
        return PostService.getById(id);
    }
    @GetMapping("/availablefordonation/{id}")
    public AvailableForDonation availablefordonation(@PathVariable("id") Integer id) throws SQLException, ClassNotFoundException {
        return PostService.availablefordonation(id);
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
    @PostMapping("/help/user/{id}/post/{PostId}")
    public CompletePost HelpPost(@PathVariable("id") Integer id,@PathVariable("PostId") Integer PostId) throws SQLException, ClassNotFoundException {
        return PostService.help(PostId,id);
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
  /*  @PutMapping("/changeStatus/{id}")
    public String changePostStatus(@PathVariable("id") Integer id, @RequestBody String newStatus) throws SQLException, ClassNotFoundException {
        Post post= PostService.getById(id);
        post.setStatusOfPost(Post.StatusOfPost.valueOf(newStatus));
        PostService.changePostStatus(id,post);

        return "ok";
    }*/
   @PutMapping("/changeStatus")
   public String changePostStatus(@RequestBody Post post) throws SQLException, ClassNotFoundException {
       post.setStatusOfPost(Post.StatusOfPost.valueOf(post.getStatusOfPost().toString()));
       PostService.changePostStatus(post);

       return "ok";
   }
    @GetMapping("/count")
    public int countPosts() throws SQLException, ClassNotFoundException {
        return PostService.count();
    }
}
