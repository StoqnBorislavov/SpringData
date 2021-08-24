package springdata.restmvc.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import springdata.restmvc.dao.PostRepository;
import springdata.restmvc.entity.Post;

import java.util.List;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {
        if (postRepository.count() == 0) {


            List.of(new Post("New in Spring",
                                    "Spring Boot is fancy...",
                                    "Stoyan Stoyanov", Set.of("Spring", "Java", "Spring Boot")),
                            new Post("Reactive Spring",
                                    "Web flux is here...",
                                    "Stoyan Stoyanov", Set.of("Spring", "Java", "reactor")),
                            new Post("Easy to Test",
                                    "WebTestClients is easy...",
                                    "Stoyan Stoyanov", Set.of("Spring", "Java", "web test client")))
                    .forEach(postRepository::save);

        }
    }
}
