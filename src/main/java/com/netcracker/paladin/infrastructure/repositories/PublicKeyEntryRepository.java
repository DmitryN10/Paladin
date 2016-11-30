package com.netcracker.paladin.infrastructure.repositories;

import com.netcracker.paladin.domain.PublicKeyEntry;

import java.util.List;

/**
 * Created by ivan on 27.11.16.
 */
public interface PublicKeyEntryRepository {
    public void insert(PublicKeyEntry PublicKeyEntry);

    public PublicKeyEntry findByEmail(String email);

    List<PublicKeyEntry> findAll();
}
