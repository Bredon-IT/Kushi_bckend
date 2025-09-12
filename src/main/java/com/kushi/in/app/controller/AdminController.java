package com.kushi.in.app.controller;


import com.kushi.in.app.entity.Customer;
import com.kushi.in.app.model.CustomerDTO;
import com.kushi.in.app.model.InvoiceDTO;
import com.kushi.in.app.model.ServiceDTO;
import com.kushi.in.app.service.AdminService;
import com.kushi.in.app.service.CustomerService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173") // Update with actual frontend URL for production
public class AdminController {

    private  AdminService adminService;
    private final CustomerService customerService;

    // Constructor injection is preferred for better testability and immutability
    public AdminController(AdminService adminService, CustomerService customerService) {
        this.adminService = adminService;
        this.customerService = customerService;
    }

    // =======================
    // 📌 Booking Management
    // =======================

    @GetMapping("/all-bookings")
    public List<Customer> getAllBookings() {
        // Returns all booking records from the database
        return adminService.getAllBookings();
    }
    @PostMapping("/new-booking")
    public Customer createBooking(@RequestBody Customer customer){
        // Saves a new booking record to the database
         return adminService.saveBooking(customer);
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

    // =======================
    // 📌 Booking Stats
    // =======================

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


    @GetMapping("/today-bookings")
    public ResponseEntity<Long> getTodayBookings() {
        long count = adminService.getTodayBookings();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/pending-approvals")
    public ResponseEntity<Long> getPendingApprovals() {
        long count = adminService.getPendingApprovals();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/recent-bookings")
    public ResponseEntity<List<Customer>> getRecentBookingsByDate() {
        List<Customer> recentBookings = adminService.getRecentBookingsByDate();
        return ResponseEntity.ok(recentBookings);
    }

        @GetMapping("/visit-status")
    public List<String> getVisitStatuses(){
        return adminService.getVisitStatuses();
        }

        @PutMapping("/update")
    public List<String> updateVisitStatuses(){
        return adminService.updateVisitStatuses();
        }



    @GetMapping("/revenue-by-service")
    public ResponseEntity<List<Map<String, Object>>> getRevenueByService() {
        try {
            List<Map<String, Object>> revenueData = adminService.getRevenueByService();
            return ResponseEntity.ok(revenueData);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }


// =======================
    // 📌 Top Customers & Services
    // =======================




    //Top booking customers
    @GetMapping("/top-booked-customers")
    public ResponseEntity<List<CustomerDTO>> getTopBookedCustomers() {
        return ResponseEntity.ok(adminService.getTopBookedCustomers());
    }

    //Top Services
    @GetMapping("/top-services")
    public ResponseEntity<Map<String, Object>> getTopServices() {
        List<Map<String, Object>> topServices = adminService.getTopServices();

        Map<String, Object> response = new HashMap<>();
        response.put("topServices", topServices);

        return ResponseEntity.ok(response);
    }

    // To fetch ratings

    @GetMapping("/top-rated-services")
    public ResponseEntity<List<ServiceDTO>> getTopRatedServices() {
        return ResponseEntity.ok(adminService.getTopRatedServices());
    }


    //to fetch recent/ new bookings

    // =======================
    // 📌 Invoice & Reports
    // =======================

    //to fetch invoices
    @GetMapping("/invoices")
    public ResponseEntity<List<InvoiceDTO>> getAllInvoices(){
        List<InvoiceDTO> invoices = adminService.getAllInvoices();

        if(invoices.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(invoices);
    }


    //financial management report
    @GetMapping(value = "/service-report/csv", produces = "text/csv")
    public void downloadServiceReportCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=service_report.csv");

        List<Map<String, Object>> reportData = adminService.getServiceReport();
        PrintWriter writer = response.getWriter();

        // Header
        writer.println("Service Name,Total Revenue,Booking Count");

        for (Map<String, Object> row : reportData) {
            writer.println(
                    row.get("booking_Service_name") + "," +
                            row.get("totalRevenue") + "," +
                            row.get("bookingCount")
            );
        }
    }


}
