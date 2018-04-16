package pl.apso.springhotel.hotel;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

/**
 * Represent database hotel entity.
 */
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "rooms")
@Builder
@Entity
public class Hotel {
  @Id
  @SequenceGenerator(name = "hotel_gen", sequenceName = "hotel_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hotel_gen")
  private Long id;
  @NonNull
  @Column(unique = true)
  private String name;
  @NonNull
  private String city;
  @OneToMany(mappedBy = "hotel", cascade = ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<Room> rooms;
}
