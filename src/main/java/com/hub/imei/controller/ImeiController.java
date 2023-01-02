package com.hub.imei.controller;

import com.hub.imei.entity.ImeiNumber;
import com.hub.imei.service.IImeiService;
import com.hub.imei.service.ImeiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/imei")
@RequiredArgsConstructor
public class ImeiController {
    private final IImeiService imeiService;

    @GetMapping("/inquire")
    public ResponseEntity<Boolean> inquire(@RequestBody String imei){
        return ResponseEntity.ok().body(imeiService.inquire(imei));
    }

    @PostMapping("/save")
    public ResponseEntity<Boolean> saveImei(@Valid @RequestBody ImeiNumber imeiNumber, BindingResult theBindingResult){
        if(theBindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(false);
        }else{
            return ResponseEntity.ok().body(imeiService.saveImei(imeiNumber));
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ImeiNumber>> getImeiNumbers(){
        return ResponseEntity.ok().body(imeiService.getAll());
    }

    @GetMapping("/getMyImei")
    public ResponseEntity<List<ImeiNumber>> getMyImeiNumbers(){
        return ResponseEntity.ok().body(imeiService.getMyImeiNumbers());
    }

    @GetMapping("/getCount")
    public ResponseEntity<Integer> getCountOfImeiRecords(){
        return ResponseEntity.ok().body(imeiService.countOfImeiRecords());
    }

    @GetMapping("/getBannedImei")
    public ResponseEntity<List<ImeiNumber>> findBannedImei(){
        return ResponseEntity.ok().body(imeiService.findBannedImei());
    }

    @PostMapping("/blockImei")
    public ResponseEntity<Boolean> blockImei(@RequestBody String imeiNumber){
        return ResponseEntity.ok().body(imeiService.blockImei(imeiNumber));
    }

    @GetMapping("/getCountOfBanned")
    public ResponseEntity<Long> getBannedCount(){
        return ResponseEntity.ok().body(imeiService.countOfBanned());
    }


}
