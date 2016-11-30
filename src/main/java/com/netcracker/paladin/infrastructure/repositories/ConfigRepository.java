package com.netcracker.paladin.infrastructure.repositories;

import com.netcracker.paladin.infrastructure.repositories.exceptions.NoSavedConfigPropertiesException;

import java.util.Properties;

/**
 * Created by ivan on 30.11.16.
 */
public interface ConfigRepository {
    public Properties loadProperties() throws NoSavedConfigPropertiesException;

    public void saveProperties(Properties properties);
}
