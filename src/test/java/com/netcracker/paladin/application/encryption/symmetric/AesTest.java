package com.netcracker.paladin.application.encryption.symmetric;

import com.netcracker.paladin.application.encryption.sessionkeygen.ChebiKeygen;
import com.netcracker.paladin.application.encryption.sessionkeygen.SessionKeygen;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Created by ivan on 28.11.16.
 */
@RunWith(value = Parameterized.class)
public class AesTest {

    private static SessionKeygen sessionKeygen;
    private static SymmetricEncryption symmetricEncryption;

    private String oldPlainText;

    public AesTest(String oldPlainText) {
        this.oldPlainText = oldPlainText;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Ivan molodec"},
                {"Pasha ne molodez"},
                {"Chebi horosho"},
                {"Шифрование отличное"},
        });
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        sessionKeygen = new ChebiKeygen();
        symmetricEncryption = new Aes();
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void fullCycle() throws Exception {
        Key sessionKey = sessionKeygen.generateKey();
        byte[] cipherText = symmetricEncryption.encrypt(oldPlainText.getBytes("UTF-8"), sessionKey.getEncoded());
        String newPlainText = new String(symmetricEncryption.decrypt(cipherText, sessionKey.getEncoded()));
        assertEquals(oldPlainText, newPlainText);
    }
}