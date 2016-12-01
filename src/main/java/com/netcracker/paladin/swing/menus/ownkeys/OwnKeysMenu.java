package com.netcracker.paladin.swing.menus.ownkeys;

import com.netcracker.paladin.infrastructure.services.config.ConfigService;
import com.netcracker.paladin.infrastructure.services.email.EmailService;
import com.netcracker.paladin.infrastructure.services.encryption.EncryptionService;
import com.netcracker.paladin.infrastructure.services.encryption.exceptions.NoPrivateKeyException;
import com.netcracker.paladin.swing.exceptions.NoFileSelectedException;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by ivan on 29.11.16.
 */
public class OwnKeysMenu extends JMenu {
    private final ConfigService configService;
    private final EmailService emailService;
    private final EncryptionService encryptionService;

    //    private final JFrame frame;
    private final JMenuItem menuItemSetPrivateKey = new JMenuItem("Set private key..");
    private final JMenuItem menuItemExportPublicKey = new JMenuItem("Export public key..");

    public OwnKeysMenu(final JFrame frame, final ConfigService configService, final EmailService emailService, final EncryptionService encryptionService) {
        super("Own keys");
        this.configService = configService;
        this.emailService = emailService;
        this.encryptionService = encryptionService;
//        this.frame = frame;

        menuItemSetPrivateKey.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                SetPrivateKeyDialog dialog = new SetPrivateKeyDialog(frame, encryptionService);
                dialog.setVisible(true);
            }
        });
        add(menuItemSetPrivateKey);

        menuItemExportPublicKey.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    File selectedFile = showSaveFileDialog();

                    String username = configService.loadProperties().getProperty("mail.user");
                    FileUtils.writeByteArrayToFile(selectedFile, encryptionService.getOwnPublicKey());

                    JOptionPane.showMessageDialog(frame, "Public key was exported successfully!");
                } catch (NoPrivateKeyException npke) {
                    JOptionPane.showMessageDialog(frame,
                            "Please, set your private key first",
                            "No private key", JOptionPane.ERROR_MESSAGE);
                } catch (NoFileSelectedException nfse){
                    JOptionPane.showMessageDialog(frame,
                            "No file was selected. Key was not generated.",
                            "No file selected", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame,
                            "Error saving properties file: " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(menuItemExportPublicKey);
    }

    private File showSaveFileDialog() throws NoFileSelectedException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save public key");

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }else{
            throw new NoFileSelectedException();
        }
    }
}
