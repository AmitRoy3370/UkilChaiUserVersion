package com.example.demo700.Controllers.UserControllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo700.Model.UserModels.UserLocation;
import com.example.demo700.Services.UserServices.UserlocationService;

@RestController
@RequestMapping("/api/userLocation")
public class UserLocationController {

    @Autowired
    private UserlocationService userlocationService;

    // ------------------------------------------------------------
    // 1️⃣ Add User Location
    // ------------------------------------------------------------
    @PostMapping("/add")
    public UserLocation addUserLocation(@RequestBody UserLocation userLocation) {
        return userlocationService.addUserLocation(userLocation);
    }

    // ------------------------------------------------------------
    // 2️⃣ Get All User Locations
    // ------------------------------------------------------------
    @GetMapping("/all")
    public List<UserLocation> seeAll() {
        return userlocationService.seeAllUserLocation();
    }

    // ------------------------------------------------------------
    // 3️⃣ Update User Location
    // ------------------------------------------------------------
    @PutMapping("/update/{userLocationId}")
    public UserLocation updateUserLocation(
            @RequestBody UserLocation userLocation,
            @RequestParam("userId") String userId,
            @PathVariable("userLocationId") String userLocationId) {

        return userlocationService.updateUserLocation(userLocation, userId, userLocationId);
    }

    // ------------------------------------------------------------
    // 4️⃣ Delete User Location
    // ------------------------------------------------------------
    @DeleteMapping("/delete/{userLocationId}")
    public boolean deleteUserLocation(
            @PathVariable("userLocationId") String userLocationId,
            @RequestParam("userId") String userId) {

        return userlocationService.deleteUserLocation(userLocationId, userId);
    }

    // ------------------------------------------------------------
    // 5️⃣ Find User Location by User ID
    // ------------------------------------------------------------
    @GetMapping("/findByUserId/{userId}")
    public UserLocation findByUserId(@PathVariable("userId") String userId) {
        return userlocationService.findByUserId(userId);
    }

    // ------------------------------------------------------------
    // 6️⃣ Find User Locations by Location Name
    // ------------------------------------------------------------
    @GetMapping("/findByLocationName/{locationName}")
    public List<UserLocation> findByLocationName(@PathVariable("locationName") String locationName) {
        return userlocationService.findByLocationName(locationName);
    }

    // ------------------------------------------------------------
    // 7️⃣ Find User Locations by Latitude and Longitude
    // ------------------------------------------------------------
    @GetMapping("/findByLatLong")
    public List<UserLocation> findByLatLong(
            @RequestParam("lattitude") double lattitude,
            @RequestParam("longitude") double longitude) {

        return userlocationService.findByLattitudeAndLongitude(lattitude, longitude);
    }
}
