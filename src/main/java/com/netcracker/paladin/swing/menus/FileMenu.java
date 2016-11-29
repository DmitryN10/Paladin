package com.netcracker.paladin.swing.menus;

import com.netcracker.paladin.infrastructure.services.config.ConfigService;
import com.netcracker.paladin.infrastructure.services.email.EmailService;
import com.netcracker.paladin.infrastructure.services.encryption.EncryptionService;
import com.netcracker.paladin.infrastructure.services.encryption.exceptions.NoPrivateKeyException;
import com.netcracker.paladin.swing.dialogs.AddPublicKeyDialog;
import com.netcracker.paladin.swing.dialogs.SetPrivateKeyDialog;
import com.netcracker.paladin.swing.dialogs.SettingsDialog;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by ivan on 29.11.16.
 */
public class FileMenu extends JMenu {
    private final ConfigService configService;
    private final EmailService emailService;
    private final EncryptionService encryptionService;

    JMenuItem menuItemSetting = new JMenuItem("Settings..");
    JMenuItem menuItemSetPrivateKey = new JMenuItem("Set private key..");
    JMenuItem menuItemExportPublicKey = new JMenuItem("Export public key..");
    JMenuItem menuItemAddPublicKey = new JMenuItem("Add public key..");

    public FileMenu(JFrame frame, ConfigService configService, EmailService emailService, EncryptionService encryptionService) {
        super("File");
        this.configService = configService;
        this.emailService = emailService;
        this.encryptionService = encryptionService;

        menuItemSetting.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                SettingsDialog dialog = new SettingsDialog(frame, configService);
                dialog.setVisible(true);
            }
        });
        add(menuItemSetting);

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
                    String username = configService.loadProperties().getProperty("mail.user");
                    FileUtils.writeByteArrayToFile(new File(username+"_publicKey"), encryptionService.getOwnPublicKey());

                    JOptionPane.showMessageDialog(frame, "Public key was exported successfully!");
                } catch (NoPrivateKeyException npke) {
                    JOptionPane.showMessageDialog(frame,
                            "Please, set your private key first",
                            "No private key", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame,
                            "Error saving properties file: " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(menuItemExportPublicKey);

        menuItemAddPublicKey.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                AddPublicKeyDialog dialog = new AddPublicKeyDialog(frame, encryptionService);
                dialog.setVisible(true);
            }
        });
        add(menuItemAddPublicKey);
    }
}
