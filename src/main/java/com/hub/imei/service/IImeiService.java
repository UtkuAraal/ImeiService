package com.hub.imei.service;

import com.hub.imei.entity.ImeiNumber;

import java.util.List;

public interface IImeiService {
    boolean inquire(String imei);
    boolean saveImei(ImeiNumber imeiNumber);
    List<ImeiNumber> getAll();
    List<ImeiNumber> getMyImeiNumbers();
    Integer countOfImeiRecords();
    List<ImeiNumber> findBannedImei();
    boolean blockImei(String imei);
    Long countOfBanned();
}
