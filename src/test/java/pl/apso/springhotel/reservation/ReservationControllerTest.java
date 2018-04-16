package pl.apso.springhotel.reservation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.apso.springhotel.hotel.Hotel;
import pl.apso.springhotel.hotel.Room;

import java.time.LocalDate;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ReservationService reservationService;

  @Test
  public void shouldReturnAvailRoomsList() throws Exception {
    // given
    LocalDate start = LocalDate.of(2016, 2, 1);
    LocalDate end = LocalDate.of(2016, 2, 5);
    String city = "Warsaw";
    int priceMin = 200;
    int priceMax = 250;

    Hotel hotel = new Hotel(1L, "simple", "poznan", null);
    Room room = Room.builder().id(1L).price(200).hotel(hotel).build();
    given(reservationService.getRooms(
        start, end, city, priceMin, priceMax)
    ).willReturn(asList(room));

    // when + then
    mockMvc.perform(get("/reservation")
        .param("start", "2016-02-01")
        .param("end", "2016-02-05")
        .param("city", "Warsaw")
        .param("min", "200")
        .param("max", "250"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].price").value(200));
  }

  @Test
  public void shouldAcceptReservationRequest() throws Exception {
    // given
    String json = "{\"start\":\"2016-02-01\", \"end\":\"2016-02-05\", \"roomId\":\"1\"}";

    given(reservationService.submitReservation(any(ReservationRequest.class)))
        .willReturn(new ReservationResponse(1L, 1L, "PENDING"));

    // when + then
    mockMvc.perform(post("/reservation")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isOk())
        .andExpect(jsonPath("id").value(1))
        .andExpect(jsonPath("roomId").value(1))
        .andExpect(jsonPath("status").value("PENDING"));

  }

}
