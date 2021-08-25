package springdataintro.entity;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "accounts")
public class Account{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @NonNull
    private BigDecimal balance;
//    @NonNull
//    private int age;

    @ManyToOne
    //@JoinColumn(name = "user_id", referencedColumnName = "id")
    @ToString.Exclude
    private User user;

}