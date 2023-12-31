package dev.Service;

import dev.Repository.database.PostRepository;
import dev.domain.Post;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {


    public static Post create(Post post) throws SQLException, ClassNotFoundException {
        return PostRepository.create(post);
    }

    public static Post getById(int id) throws SQLException, ClassNotFoundException {
        return PostRepository.searchPost(id);
    }

    public static Post update(Integer id, Post post) throws SQLException, ClassNotFoundException {
        return PostRepository.update(id,post);
    }
    public static Post changePostStatus(Integer id, String newStatus) throws SQLException, ClassNotFoundException {

        return PostRepository.changePostStatus(id,newStatus);
    }
    public static void delete(Integer id) throws SQLException, ClassNotFoundException {
        PostRepository.deleteById(id);
    }

    public static List<Post> getAllPosts() throws SQLException, ClassNotFoundException {
        return PostRepository.getAllPosts();
    }
    public static List<Post> getPostsByStatus() throws SQLException, ClassNotFoundException {
        return PostRepository.getPostsByStatus();
    }
    public static List<Post> getPostsByStatus2() throws SQLException, ClassNotFoundException {
        return PostRepository.getPostsByStatus2();
    }
    public static int count() throws SQLException, ClassNotFoundException {
        return PostRepository.count();
    }
}
