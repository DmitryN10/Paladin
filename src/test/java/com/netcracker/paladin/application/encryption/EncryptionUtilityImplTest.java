package com.netcracker.paladin.application.encryption;

import com.netcracker.paladin.application.encryption.asymmetric.Rsa;
import com.netcracker.paladin.application.encryption.sessionkeygen.ChebiKeygen;
import com.netcracker.paladin.application.encryption.symmetric.Aes;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Created by ivan on 28.11.16.
 */
@RunWith(value = Parameterized.class)
public class EncryptionUtilityImplTest {

    EncryptionUtility encryptionUtility = new EncryptionUtilityImpl(null,
                                                                    new Rsa(),
                                                                    new Aes(),
                                                                    new ChebiKeygen());

    private String oldPlainText;

    public EncryptionUtilityImplTest(String oldPlainText) {
        this.oldPlainText = oldPlainText;
        encryptionUtility.generatePrivateKey();
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
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void fullCycle() throws Exception {
        byte[] encryptedMail = encryptionUtility.encryptEmail(oldPlainText, "pasha@gmail.com");
        String newPlainText = encryptionUtility.decryptEmail(encryptedMail);
        System.out.println("Before: "+oldPlainText);
        System.out.println("After: "+newPlainText);
        assertEquals(oldPlainText, newPlainText);
    }
}