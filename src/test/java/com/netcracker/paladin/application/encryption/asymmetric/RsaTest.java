package com.netcracker.paladin.application.encryption.asymmetric;

import com.netcracker.paladin.application.encryption.sessionkeygen.ChebiKeygen;
import com.netcracker.paladin.application.encryption.sessionkeygen.SessionKeygen;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.security.KeyPair;

import static org.junit.Assert.fail;

//@RunWith(value = Parameterized.class)
public class RsaTest {

    private static SessionKeygen sessionKeygen;
    private static AsymmetricEncryption asymmetricEncryption;

//    private byte[] sessionKey;

//    public RsaTest(byte[] sessionKey) {
//        this.sessionKey = sessionKey;
//    }

    public RsaTest() {
    }


//    @Parameterized.Parameters
//    public static Collection<Object[]> data() {
//        return Arrays.asList(new Object[][]{
//                {sessionKeygen.generateKey().getEncoded()},
//                {sessionKeygen.generateKey().getEncoded()},
//                {sessionKeygen.generateKey().getEncoded()},
//        });
//    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        sessionKeygen = new ChebiKeygen();
        asymmetricEncryption = new Rsa();
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void fullCycle() throws Exception {
        byte[] sessionKey = sessionKeygen.generateKey().getEncoded();
//        byte[] sessionKey = (new String("Pasha molodec")).getBytes("UTF-8");
        KeyPair keyPair = asymmetricEncryption.generateKeyPair();
        byte[] encryptedSessionKey = asymmetricEncryption.encrypt(sessionKey, keyPair.getPublic());
        byte[] decryptedSessionKey = asymmetricEncryption.decrypt(encryptedSessionKey, keyPair.getPrivate());

        if(sessionKey.length != decryptedSessionKey.length){
            fail();
        }
        System.out.println("Comparing two results");
        for(int i = 0; i < sessionKey.length; i++){
            System.out.println(sessionKey[i]+" "+decryptedSessionKey[i]);
            if(sessionKey[i] != decryptedSessionKey[i]){
                fail("Encryption fail");
            }
        }
    }
}