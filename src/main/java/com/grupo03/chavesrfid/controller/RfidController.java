package com.grupo03.chavesrfid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo03.chavesrfid.dto.RfidScanRequestDTO;
import com.grupo03.chavesrfid.dto.RfidScanResponseDTO;
import com.grupo03.chavesrfid.service.RfidService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/rfid")
@CrossOrigin(origins = "*")
public class RfidController {

    @Autowired
    private RfidService rfidService;

    @PostMapping("/scan")
    public ResponseEntity<RfidScanResponseDTO> scan(@RequestBody @Valid RfidScanRequestDTO dto) {
        RfidScanResponseDTO response = rfidService.processar(dto.getUid());

        if ("NEGADO".equals(response.getTipo())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        return ResponseEntity.ok(response);
    }
}
