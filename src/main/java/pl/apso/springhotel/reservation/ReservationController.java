package pl.apso.springhotel.reservation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.apso.springhotel.hotel.Room;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

  private final static Logger LOGGER = LoggerFactory.getLogger(ReservationController.class);

  private final ReservationService reservationService;

  public ReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @GetMapping
  public List<Room> getAvail(
    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,
    @RequestParam String city,
    @RequestParam Integer min,
    @RequestParam Integer max) {
    LOGGER.info("Find avail room for params [{}, {}, {}, {}, {}]",
      start, end, city, min, max
    );
    return reservationService.getRooms(start, end, city, min, max);
  }

}
