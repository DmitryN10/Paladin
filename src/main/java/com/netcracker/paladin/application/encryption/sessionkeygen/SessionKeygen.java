package com.netcracker.paladin.application.encryption.sessionkeygen;

import java.security.Key;

/**
 * Created by ivan on 27.11.16.
 */
public interface SessionKeygen {
    Key generateKey();
}
