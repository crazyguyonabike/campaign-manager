package com.example.campaign.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.campaign.domain.Identified;

public interface IdentifiedRepository extends JpaRepository<Identified, Long> {
    public Identified findByIdentifier(String identifier);
}
