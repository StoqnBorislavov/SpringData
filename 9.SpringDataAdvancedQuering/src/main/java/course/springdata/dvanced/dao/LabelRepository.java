package course.springdata.dvanced.dao;

import course.springdata.dvanced.entity.Label;
import course.springdata.dvanced.entity.Shampoo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {

    Optional<Label> findByTitle(String title);

}
