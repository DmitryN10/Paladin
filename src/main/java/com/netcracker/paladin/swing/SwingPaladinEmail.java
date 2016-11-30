package com.netcracker.paladin.swing;

/**
 * Created by ivan on 26.11.16.
 */

import com.netcracker.paladin.infrastructure.services.config.ConfigService;
import com.netcracker.paladin.infrastructure.services.email.EmailService;
import com.netcracker.paladin.infrastructure.services.encryption.EncryptionService;
import com.netcracker.paladin.swing.dialogs.StartDialog;
import com.netcracker.paladin.swing.menus.FileMenu;
import com.netcracker.paladin.swing.menus.OthersKeysMenu;
import com.netcracker.paladin.swing.menus.OwnKeysMenu;
import com.netcracker.paladin.swing.tabs.TabRead;
import com.netcracker.paladin.swing.tabs.TabSend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class SwingPaladinEmail extends JFrame {
    private final ConfigService configService;
    private final EmailService emailService;
    private final EncryptionService encryptionService;

    private final static String TABSEND = "Send an email";
    private final static String TABREAD = "Read emails";

    private final TabSend tabSend;
    private final TabRead tabRead;

    private JFrame frame;

    public SwingPaladinEmail(ConfigService configService, EmailService emailService, EncryptionService encryptionService) {
        super("Paladin Email");
        this.configService = configService;
        this.emailService = emailService;
        this.encryptionService = encryptionService;

        tabSend = new TabSend(this, emailService, encryptionService);
        tabRead = new TabRead(emailService, encryptionService);
    }

    public void addComponentToPane(Container pane) {
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab(TABSEND, tabSend);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        tabbedPane.addTab(TABREAD, tabRead);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        pane.add(tabbedPane);
    }

    private void createAndShowGUI() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SwingPaladinEmail swingEmailSender = new SwingPaladinEmail(configService, emailService, encryptionService);
        swingEmailSender.addComponentToPane(this.getContentPane());

        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(new FileMenu(this, configService, emailService, encryptionService));
        jMenuBar.add(new OwnKeysMenu(this, configService, emailService, encryptionService));
        jMenuBar.add(new OthersKeysMenu(this, configService, emailService, encryptionService));
        this.setJMenuBar(jMenuBar);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        StartDialog startDialog = new StartDialog(this, encryptionService);
        startDialog.setVisible(true);
    }

    public void launch() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    public TabSend getTabSend() {
        return tabSend;
    }

    public TabRead getTabRead() {
        return tabRead;
    }
}
