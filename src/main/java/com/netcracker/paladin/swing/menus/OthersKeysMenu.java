package com.netcracker.paladin.swing.menus;

import com.netcracker.paladin.infrastructure.services.config.ConfigService;
import com.netcracker.paladin.infrastructure.services.email.EmailService;
import com.netcracker.paladin.infrastructure.services.encryption.EncryptionService;
import com.netcracker.paladin.swing.dialogs.AddPublicKeyDialog;
import com.netcracker.paladin.swing.dialogs.DeletePublicKeyDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ivan on 29.11.16.
 */
public class OthersKeysMenu extends JMenu {
    private final ConfigService configService;
    private final EmailService emailService;
    private final EncryptionService encryptionService;

    //    private final JFrame frame;
    private final JMenuItem menuItemAddPublicKey = new JMenuItem("Add public key..");
    private final JMenuItem menuItemDeletePublicKey = new JMenuItem("Delete public key..");

    public OthersKeysMenu(final JFrame frame, final ConfigService configService, final EmailService emailService, final EncryptionService encryptionService) {
        super("Others keys");
        this.configService = configService;
        this.emailService = emailService;
        this.encryptionService = encryptionService;
//        this.frame = frame;

        menuItemAddPublicKey.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                AddPublicKeyDialog dialog = new AddPublicKeyDialog(frame, encryptionService);
                dialog.setVisible(true);
            }
        });
        add(menuItemAddPublicKey);

        menuItemDeletePublicKey.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                DeletePublicKeyDialog dialog = new DeletePublicKeyDialog(frame, encryptionService);
                dialog.setVisible(true);
            }
        });
        add(menuItemDeletePublicKey);
    }
}
