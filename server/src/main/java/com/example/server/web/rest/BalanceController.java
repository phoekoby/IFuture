package com.example.server.web.rest;

import com.example.server.dto.ChangeBalanceDTO;
import com.example.server.service.BalanceService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BalanceController {
    BalanceService balanceService;

    @GetMapping("/balance")
    public ResponseEntity<?> getBalance(@RequestParam("id") Long id){
        return ResponseEntity.ok(balanceService.getBalance(id));
    }

    @PutMapping("/balance")
    public ResponseEntity<?> change(@RequestBody ChangeBalanceDTO changeBalanceDTO){
        balanceService.changeBalance(changeBalanceDTO.getId(), changeBalanceDTO.getAmount());
        return ResponseEntity.ok().build();
    }
}
