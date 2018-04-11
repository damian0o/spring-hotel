package pl.apso.springhotel.hotels;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Controller which defined api
 * for operate on hotels and rooms.
 */
@RestController
@RequestMapping("/hotel")
public class HotelController {

  private HotelService hotelService;

  private HotelController(HotelService hotelService) {
    this.hotelService = hotelService;
  }

  @GetMapping
  public List<Hotel> getAll() {
    return hotelService.getAll();
  }

  @GetMapping("/{name}")
  public Hotel get(@PathVariable String name) {
    return hotelService.getHotel(name);
  }

  @PostMapping
  public Hotel create(@RequestBody Hotel hotel) {
    return hotelService.create(hotel);
  }

  @GetMapping("/{name}/room")
  public List<Room> getRooms(@PathVariable String name) {
    return hotelService.getRooms(name);
  }

  @PostMapping("/{name}/room")
  public Room addRoom(@PathVariable String name, @RequestBody Room room) {
    return hotelService.addRoom(name, room);
  }

  @ResponseStatus(CONFLICT)
  @ExceptionHandler(HotelAlreadyExistsException.class)
  public void hotelAlreadyExistsHandler() {
  }

  @ResponseStatus(NOT_FOUND)
  @ExceptionHandler(HotelNotFoundException.class)
  public void hotelNotFoundHandler() {
  }


}
