package course.springdata.dvanced.entity;


import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "labels")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String title;
    private String subtitle;

    @ToString.Exclude
    @OneToMany(mappedBy = "label")
    private Set<Shampoo> shampoos;


}