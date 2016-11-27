package com.netcracker.paladin.infrastructure.repositories;

import com.netcracker.paladin.domain.PublicKeyEntry;

/**
 * Created by ivan on 27.11.16.
 */
public interface PublicKeyEntryRepository {
    public void insert(PublicKeyEntry PublicKeyEntry);

    public PublicKeyEntry findByEmail(String email);
}
