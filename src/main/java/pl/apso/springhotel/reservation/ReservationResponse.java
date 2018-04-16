package pl.apso.springhotel.reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represent reservation response.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationResponse {
  private Long id;
  private Long roomId;
  private String status;
}
