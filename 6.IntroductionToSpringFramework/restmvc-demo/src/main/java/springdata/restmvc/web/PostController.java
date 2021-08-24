package springdata.restmvc.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springdata.restmvc.dao.PostRepository;
import springdata.restmvc.entity.Post;

import java.util.Collection;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostRepository postRepo;

    @GetMapping
    public Collection<Post> getAllPost(){
        return postRepo.findAll();
    }

    @GetMapping("/{id}")
    public Post getAllPost(@PathVariable Long id){
        return postRepo.findById(id).orElseThrow();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post addPost(@RequestBody Post post){
        return postRepo.save(post);
    }
}
