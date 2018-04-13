package pl.apso.springhotel.hotel;

import org.springframework.web.bind.annotation.*;

import java.util.Collection;
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

  private HotelManagementService hotelManagementService;

  private HotelController(HotelManagementService hotelManagementService) {
    this.hotelManagementService = hotelManagementService;
  }

  @GetMapping
  public List<Hotel> getAll() {
    return hotelManagementService.getAll();
  }

  @GetMapping("/{name}")
  public Hotel get(@PathVariable String name) throws HotelNotFoundException {
    return hotelManagementService.getHotel(name);
  }

  @PostMapping
  public Hotel create(@RequestBody Hotel hotel) throws HotelAlreadyExistsException {
    return hotelManagementService.create(hotel);
  }

  @GetMapping("/{name}/room")
  public Collection<Room> getRooms(@PathVariable String name) throws HotelNotFoundException {
    return hotelManagementService.getRooms(name);
  }

  @PostMapping("/{name}/room")
  public Room addRoom(@PathVariable String name, @RequestBody Room room) throws HotelNotFoundException {
    return hotelManagementService.addRoom(name, room);
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
