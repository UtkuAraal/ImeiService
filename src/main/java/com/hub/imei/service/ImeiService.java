package com.hub.imei.service;

import com.hub.imei.entity.AppUser;
import com.hub.imei.entity.ImeiNumber;
import com.hub.imei.repo.AppUserRepo;
import com.hub.imei.repo.ImeiNumberRepo;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ImeiService  implements IImeiService{
    private final ImeiNumberRepo imeiRepo;
    private final AppUserRepo userRepo;

    @Override
    public boolean inquire(String imei){
        ImeiNumber imeiRecord = imeiRepo.findByImei(imei);
        if(imeiRecord == null){
            return false;
        }else{
            imeiRepo.increaseCount(imeiRecord.getImei());
            return true;
        }
    }

    @Override
    public boolean saveImei(ImeiNumber imeiNumber){
        try{
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username;
            if (principal instanceof UserDetails) {
                username = ((UserDetails)principal).getUsername();
            } else {
                username = principal.toString();
            }

            AppUser loggedUser = userRepo.findByUsername(username);
            imeiNumber.setBanned(false);
            imeiNumber.setSearchCount(0);
            imeiNumber.setUser(loggedUser);
            imeiRepo.save(imeiNumber);
            return true;
        }catch (Exception e){
            return false;
        }

    }

    @Override
    public List<ImeiNumber> getAll(){
        return imeiRepo.findAll();
    }

    @Override
    public List<ImeiNumber> getMyImeiNumbers(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        AppUser loggedUser = userRepo.findByUsername(username);

        return imeiRepo.findAllByUser(loggedUser);
    }

    @Override
    public Integer countOfImeiRecords(){
        return imeiRepo.countAll();
    }

    @Override
    public List<ImeiNumber> findBannedImei(){
        return imeiRepo.findAllByBanned(true);
    }

    @Override
    public boolean blockImei(String imei){
        try{
            imeiRepo.blockImei(imei);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Long countOfBanned(){
        return imeiRepo.countAllByBanned(true);
    }


}
