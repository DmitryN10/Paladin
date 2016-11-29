package com.netcracker.paladin.infrastructure.services.encryption.sessionkeygen;

import java.security.Key;

/**
 * Created by ivan on 27.11.16.
 */
public interface SessionKeygen {
    Key generateKey();
}
