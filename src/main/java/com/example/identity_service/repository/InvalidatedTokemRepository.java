package com.example.identity_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.identity_service.entity.InvalidatedToken;

@Repository
public interface InvalidatedTokemRepository extends JpaRepository<InvalidatedToken, String> {}
