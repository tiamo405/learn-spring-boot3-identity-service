package com.example.identity_service.repository;

import com.example.identity_service.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidatedTokemRepository  extends JpaRepository<InvalidatedToken, String> {
}
