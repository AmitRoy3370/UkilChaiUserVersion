package com.example.demo700.Controllers.UserControllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo700.Model.UserModels.Capital;
import com.example.demo700.Services.UserServices.CapitalService;

@RestController
@RequestMapping("/api/capitals")
public class CapitalController {

    @Autowired
    private CapitalService capitalService;

    // ==================== ADD CAPITAL (POST) ====================
    @PostMapping("/add")
    public ResponseEntity<?> addCapital(
            @RequestBody Capital capital,
            @RequestParam("userId") String userId) {

        try {
            Capital created = capitalService.addCapital(capital, userId);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (NullPointerException | NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error adding capital: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ==================== UPDATE CAPITAL (PUT) ====================
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCapital(
            @PathVariable String id,
            @RequestBody Capital capital,
            @RequestParam("userId") String userId) {

        try {
            Capital updated = capitalService.updateCapital(capital, id, userId);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (NullPointerException | NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating capital: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ==================== GET CAPITAL BY ID ====================
    @GetMapping("/{id}")
    public ResponseEntity<?> getCapitalById(@PathVariable String id) {
        try {
            Capital capital = capitalService.findById(id);
            return new ResponseEntity<>(capital, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching capital", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ==================== GET ALL CAPITALS ====================
    @GetMapping("/all")
    public ResponseEntity<?> getAllCapitals() {
        try {
            List<Capital> list = capitalService.findAll();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching capitals", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ==================== GET CAPITALS BY COMPANY ID ====================
    @GetMapping("/company/{companyId}")
    public ResponseEntity<?> getCapitalsByCompanyId(@PathVariable String companyId) {
        try {
            List<Capital> list = capitalService.findByCompanyId(companyId);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching capitals by company", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ==================== FILTER: Authorized Capital <= (LTE) ====================
    @GetMapping("/filter/authorized-capital/lte")
    public ResponseEntity<?> findByAuthorizedCapitalLte(@RequestParam("value") double value) {
        try {
            List<Capital> list = capitalService.findByAuthorizedCapitalLte(value);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error filtering capitals", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ==================== FILTER: Authorized Capital >= (GTE) ====================
    @GetMapping("/filter/authorized-capital/gte")
    public ResponseEntity<?> findByAuthorizedCapitalGte(@RequestParam("value") double value) {
        try {
            List<Capital> list = capitalService.findByAuthorizedCapitalGte(value);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error filtering capitals", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ==================== FILTER: Total Share <= (LTE) ====================
    @GetMapping("/filter/total-share/lte")
    public ResponseEntity<?> findByTotalShareLte(@RequestParam("value") int value) {
        try {
            List<Capital> list = capitalService.findByTotalShareLte(value);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error filtering capitals", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ==================== FILTER: Total Share >= (GTE) ====================
    @GetMapping("/filter/total-share/gte")
    public ResponseEntity<?> findByTotalShareGte(@RequestParam("value") int value) {
        try {
            List<Capital> list = capitalService.findByTotalShareGte(value);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error filtering capitals", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ==================== FILTER: Number of Share <= (LTE) ====================
    @GetMapping("/filter/number-of-share/lte")
    public ResponseEntity<?> findByNumberOfShareLte(@RequestParam("value") int value) {
        try {
            List<Capital> list = capitalService.findByNumberOfShareLte(value);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error filtering capitals", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ==================== FILTER: Number of Share >= (GTE) ====================
    @GetMapping("/filter/number-of-share/gte")
    public ResponseEntity<?> findByNumberOfShareGte(@RequestParam("value") int value) {
        try {
            List<Capital> list = capitalService.findByNumberOfShareGte(value);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error filtering capitals", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ==================== FILTER: Share Value <= (LTE) ====================
    @GetMapping("/filter/share-value/lte")
    public ResponseEntity<?> findByShareValueLte(@RequestParam("value") double value) {
        try {
            List<Capital> list = capitalService.findByShareValueLte(value);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error filtering capitals", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ==================== FILTER: Share Value >= (GTE) ====================
    @GetMapping("/filter/share-value/gte")
    public ResponseEntity<?> findByShareValueGte(@RequestParam("value") double value) {
        try {
            List<Capital> list = capitalService.findByShareValueGte(value);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error filtering capitals", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ==================== DELETE CAPITAL (DELETE) ====================
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCapital(
            @PathVariable String id,
            @RequestParam("userId") String userId) {

        try {
            boolean deleted = capitalService.deleteCapital(id, userId);
            if (deleted) {
                return new ResponseEntity<>("Capital deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Could not delete capital", HttpStatus.BAD_REQUEST);
            }
        } catch (NullPointerException e) {
            return new ResponseEntity<>("Invalid request", HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting capital: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}