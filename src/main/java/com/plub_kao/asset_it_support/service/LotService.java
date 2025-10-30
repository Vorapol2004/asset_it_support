package com.plub_kao.asset_it_support.service;

import com.plub_kao.asset_it_support.repository.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LotService {

    @Autowired
    private LotRepository lotRepository;

    
}
