/*

    AdService to do CRUD operations on ad objects

*/


package com.bridgingcode.springbootactivemqdemo;

import com.bridgingcode.springbootactivemqdemo.model.Ad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdService {

    private final AdRepository adRepository;

    @Autowired
    public AdService(AdRepository adRepository) {
        this.adRepository = adRepository;
    }

    public Ad addAd(Ad ad) {
        return adRepository.save(ad);
    }

    public Ad getAd(Long adId) {
        return adRepository.findById(adId).get();
    }

    public List<Ad> getAllAds() {
        return adRepository.findAll();
    }

    public Ad updateAd(Ad ad) {
        if(adRepository.existsById(ad.getAdId())) {
            return adRepository.save(ad);
        }

        return null;
    }

    public void deleteAd(Long adId) {
        adRepository.deleteById(adId);
    }
}
