package com.kushi.in.app.controller;

import com.kushi.in.app.entity.Admin;
import com.kushi.in.app.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*") // Update with actual frontend URL for production
public class AdminController {

    private  AdminService adminService;

    // Constructor injection is preferred for better testability and immutability
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public List<Admin> getAllBookings() {
        // Returns all booking records from the database
        return adminService.getAllBookings();
    }
    @PostMapping
    public Admin createBooking(@RequestBody Admin admin){
        // Saves a new booking record to the database
         return adminService.saveBooking(admin);
    }

    @PutMapping("/{id}/assign-worker")// Maps HTTP PUT requests sent to /api/bookings/{id}/assign-worker to this method

    // Indicates this method returns an HTTP response with a String message and status code
    // Extracts the {id} from the URL path and stores it in bookingId//@PathVariable
    // Extracts JSON data from the request body and puts it into a Map (key-value pairs)
    public ResponseEntity<String> assignWorker(@PathVariable("id") Long bookingId,
                                                @RequestBody Map<String, String> body){

        String workername = body.get("workername");// Gets the value of the "workername" field from the request body
        adminService.assignWorker(bookingId,workername); // Calls the service method to update the worker assignment in the database
        return ResponseEntity.ok("worker assigned successfully");// Returns an HTTP 200 OK response with a success message in the body

    }

    @GetMapping("/statistics")

        public ResponseEntity<Map<String , Object>> getbookingStatistics(
                @RequestParam(value="timePeriod",defaultValue = "all-time") String timePeriod){
               try{
                   Map<String,Object> status = adminService.getbookingStatistics(timePeriod);// Call the service to get statistics based on the timePeriod
                           return ResponseEntity.ok(status); // Return the statistics with HTTP 200 OK status
               }catch (Exception e){
                   e.printStackTrace(); // Print the error in case something goes wrong
                   return ResponseEntity.status(500).body(null);  // Return HTTP 500 Internal Server Error with no body
               }

        }

        @GetMapping("/overview")

        public ResponseEntity<Map<String,Object>> getbookingOverview(
               @RequestParam(value="timePeriod",defaultValue = "all-time") String timePeriod){
        try{
            Map<String,Object> overview = adminService.getOverview(timePeriod);
                  return ResponseEntity.ok(overview);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }

        }





}
