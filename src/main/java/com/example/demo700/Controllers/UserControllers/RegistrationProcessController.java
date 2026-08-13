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

import com.example.demo700.Model.UserModels.RegistrationProcess;
import com.example.demo700.Services.UserServices.RegistrationProcessService;

@RestController
@RequestMapping("/api/registration-process")
public class RegistrationProcessController {

    @Autowired
    private RegistrationProcessService registrationProcessService;

    // ==================== ADD REGISTRATION PROCESS ====================
    @PostMapping("/add")
    public ResponseEntity<?> addRegistrationProcess(
            @RequestBody RegistrationProcess process,
            @RequestParam("userId") String userId) {

        try {
            RegistrationProcess createdProcess = registrationProcessService.addRegistrationProcess(process, userId);
            return new ResponseEntity<>(createdProcess, HttpStatus.CREATED);
        } catch (NullPointerException | NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ArithmeticException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT); // 409 Conflict for duplicates
        } catch (Exception e) {
            return new ResponseEntity<>("Error adding registration process: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ==================== UPDATE REGISTRATION PROCESS ====================
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRegistrationProcess(
            @PathVariable String id,
            @RequestBody RegistrationProcess process,
            @RequestParam("userId") String userId) {

        try {
            RegistrationProcess updatedProcess = registrationProcessService.updateRegistrationprocess(process, userId, id);
            return new ResponseEntity<>(updatedProcess, HttpStatus.OK);
        } catch (NullPointerException | NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ArithmeticException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating registration process: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ==================== ADD STEPS TO PROCESS ====================
    @PutMapping("/add-step/{id}")
    public ResponseEntity<?> addStepToProcess(
            @PathVariable String id,
            @RequestParam("step") String step,
            @RequestParam("userId") String userId) {

        try {
            RegistrationProcess updatedProcess = registrationProcessService.addSteps(id, step, userId);
            return new ResponseEntity<>(updatedProcess, HttpStatus.OK);
        } catch (NullPointerException | NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error adding step: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ==================== GET PROCESS BY ID ====================
    @GetMapping("/{id}")
    public ResponseEntity<?> getProcessById(@PathVariable String id) {
        try {
            RegistrationProcess process = registrationProcessService.findById(id);
            return new ResponseEntity<>(process, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching process: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ==================== GET ALL PROCESSES ====================
    @GetMapping("/all")
    public ResponseEntity<?> getAllProcesses() {
        try {
            List<RegistrationProcess> processes = registrationProcessService.findAll();
            return new ResponseEntity<>(processes, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching all processes: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ==================== GET BY COMPANY ID ====================
    @GetMapping("/company/{companyId}")
    public ResponseEntity<?> getProcessesByCompanyId(@PathVariable String companyId) {
        try {
            List<RegistrationProcess> processes = registrationProcessService.findByCompanyId(companyId);
            return new ResponseEntity<>(processes, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching processes by company: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ==================== GET BY ADVOCATE ID ====================
    @GetMapping("/advocate/{advocateId}")
    public ResponseEntity<?> getProcessesByAdvocateId(@PathVariable String advocateId) {
        try {
            List<RegistrationProcess> processes = registrationProcessService.findByAdvocateId(advocateId);
            return new ResponseEntity<>(processes, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching processes by advocate: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ==================== GET BY USER ID ====================
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getProcessesByUserId(@PathVariable String userId) {
        try {
            List<RegistrationProcess> processes = registrationProcessService.findByUserId(userId);
            return new ResponseEntity<>(processes, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching processes by user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ==================== GET BY STATUS ====================
    @GetMapping("/status")
    public ResponseEntity<?> getProcessesByStatus(@RequestParam("status") boolean status) {
        try {
            List<RegistrationProcess> processes = registrationProcessService.findByStatus(status);
            return new ResponseEntity<>(processes, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching processes by status: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ==================== GET BY SHARE VALUE (LTE & GTE) ====================
    @GetMapping("/share-value/lte")
    public ResponseEntity<?> getProcessesByShareValueLte(@RequestParam("value") double shareValue) {
        try {
            List<RegistrationProcess> processes = registrationProcessService.findByShareValuePerShareLte(shareValue);
            return new ResponseEntity<>(processes, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching processes: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/share-value/gte")
    public ResponseEntity<?> getProcessesByShareValueGte(@RequestParam("value") double shareValue) {
        try {
            List<RegistrationProcess> processes = registrationProcessService.findByShareValuePerShareGte(shareValue);
            return new ResponseEntity<>(processes, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching processes: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ==================== DELETE REGISTRATION PROCESS ====================
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRegistrationProcess(
            @PathVariable String id,
            @RequestParam("userId") String userId) {

        try {
            boolean deleted = registrationProcessService.deleteRegistrationProcess(id, userId);
            if (deleted) {
                return new ResponseEntity<>("Registration process deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Registration process could not be deleted", HttpStatus.BAD_REQUEST);
            }
        } catch (NullPointerException | NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ArithmeticException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting registration process: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}