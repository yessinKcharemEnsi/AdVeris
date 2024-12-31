package com.bridgingcode.springbootactivemqdemo;

import com.bridgingcode.springbootactivemqdemo.model.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {

}
