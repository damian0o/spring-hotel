package pl.apso.springhotel.hotels;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(HotelController.class)
public class HotelControllerTest {

  @MockBean
  private HotelService hotelService;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void shouldReturnHotelList() throws Exception {
    Hotel gary = new Hotel(1L, "Gary Hotel", "Wroclaw");
    Hotel saint = new Hotel(2L, "Saint Inn", "Wroclaw");

    given(hotelService.getAll()).willReturn(Arrays.asList(gary, saint));

    mockMvc.perform(get("/hotel"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0].id").value(1))
      .andExpect(jsonPath("$[0].name").value("Gary Hotel"))
      .andExpect(jsonPath("$[0].city").value("Wroclaw"))
      .andExpect(jsonPath("$[1].id").value(2))
      .andExpect(jsonPath("$[1].name").value("Saint Inn"))
      .andExpect(jsonPath("$[1].city").value("Wroclaw"));
  }

  @Test
  public void shouldReturnHotel() throws Exception {
    Hotel hotel = new Hotel(1L, "PeterHotel", "Wroclaw");
    given(hotelService.getHotel(anyString())).willReturn(hotel);

    mockMvc.perform(get("/hotel/PeterHotel"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("name").value("PeterHotel"))
      .andExpect(jsonPath("city").value("Wroclaw"));
  }

  @Test
  public void shouldAddNewHotel() throws Exception {
    Hotel hotel = new Hotel(10L, "simple", "wroclaw");
    given(hotelService.create(eq(new Hotel("simple", "wroclaw")))).willReturn(hotel);

    String json = "{\"name\": \"simple\", \"city\": \"wroclaw\"}";
    mockMvc.perform(post("/hotel")
      .contentType(APPLICATION_JSON).content(json))
      .andExpect(status().isOk())
      .andExpect(jsonPath("id").value(10L))
      .andExpect(jsonPath("name").value("simple"))
      .andExpect(jsonPath("city").value("wroclaw"));
  }

  @Test
  public void whenHotelNameAlreadyExists() throws Exception {
    given(hotelService.create(any(Hotel.class))).willThrow(new HotelAlreadyExistsException());
    String json = "{\"name\": \"simple\", \"city\": \"wroclaw\"}";
    mockMvc.perform(post("/hotel")
      .contentType(APPLICATION_JSON)
      .content(json))
      .andExpect(status().isConflict());
  }

  @Test
  public void whenHotelNotFound() throws Exception {
    given(hotelService.getHotel(anyString())).willThrow(new HotelNotFoundException());
    mockMvc.perform(get("/hotel/any"))
      .andExpect(status().isNotFound());
  }

  @Test
  public void shouldReturnListOfRoomsForHotel() throws Exception {
    Hotel hotel = new Hotel(10L, "first", "warsaw");
    Room room1 = new Room(1L, 200, hotel);
    Room room2 = new Room(2L, 150, hotel);
    given(hotelService.getRooms(eq("first")))
      .willReturn(Arrays.asList(room1, room2));

    mockMvc.perform(get("/hotel/first/room"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0].id").value(1))
      .andExpect(jsonPath("$[0].price").value(200))
      .andExpect(jsonPath("$[0].hotel.name").value("first"))
      .andExpect(jsonPath("$[1].id").value(2))
      .andExpect(jsonPath("$[1].price").value(150))
      .andExpect(jsonPath("$[1].hotel.name").value("first"));
  }

  @Test
  public void shouldCreateRoomForGivenHotel() throws Exception {
    Hotel hotel = new Hotel(1L, "simple", "wroclaw");
    Room room = new Room(2L, 100, hotel);
    given(hotelService.addRoom(eq("simple"), any(Room.class))).willReturn(room);

    String json = "{\"price\": 100}";
    mockMvc.perform(post("/hotel/simple/room")
      .contentType(APPLICATION_JSON).content(json))
      .andExpect(status().isOk())
      .andExpect(jsonPath("id").value(2L))
      .andExpect(jsonPath("price").value(100))
      .andExpect(jsonPath("hotel.name").value("simple"))
      .andExpect(jsonPath("hotel.city").value("wroclaw"));
  }

}
