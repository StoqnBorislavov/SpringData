package softuni.exam.repository;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.domain.entity.Picture;

import java.util.Map;

@Repository
public interface PictureRepository  extends JpaRepository<Picture, Long> {

    Picture findByUrl(String url);
}