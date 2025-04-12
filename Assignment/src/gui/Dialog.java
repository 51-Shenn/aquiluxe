package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Dialog extends JDialog {

    private JFrame frame;
    private final File SUCCESS_ICON = new File("images/icons/success.png");
    private final File ERROR_ICON = new File("images/icons/error.png");
    private final File HAZARD_ICON = new File("images/icons/hazard.png");
    private boolean confirmation;

    public Dialog() {
        this.frame = new JFrame();
    }

    public Dialog(JFrame frame) {
        this.frame = frame;
    }

    public JFrame getFrame() {
        return frame;
    }

    public File getSUCCESS_ICON() {
        return SUCCESS_ICON;
    }

    public File getERROR_ICON() {
        return ERROR_ICON;
    }

    public File getHAZARD_ICON() {
        return HAZARD_ICON;
    }

    public boolean isConfirmation() {
        return confirmation;
    }

    public void setConfirmation(boolean confirmation) {
        this.confirmation = confirmation;
    }

    public boolean showDialog(String dialogType, String dialogTitle, String messageTitle, String messageContent, boolean closeOperation) {
        JDialog messageDialog = new JDialog(this.frame, dialogTitle, true); // true = modal dialog
        messageDialog.setLayout(new GridBagLayout());
        messageDialog.setSize(new Dimension(600, 450));
        messageDialog.setResizable(false);
        messageDialog.setBackground(Color.WHITE);
        if(closeOperation)
            messageDialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        else
            messageDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel messageIconPanel = new JPanel(new GridBagLayout());
        switch (dialogType) {
            case "SUCCESS" -> messageIconPanel.add(new JLabel(new ImageIcon(SUCCESS_ICON.toString())));
            case "ERROR" -> messageIconPanel.add(new JLabel(new ImageIcon(ERROR_ICON.toString())));
            case "HAZARD" -> messageIconPanel.add(new JLabel(new ImageIcon(HAZARD_ICON.toString())));
        }

        JPanel messageTextPanel = new JPanel(new GridBagLayout());
        JLabel messageTitleLabel = new JLabel(messageTitle);
        messageTitleLabel.setFont(CustomFonts.ROBOTO_BLACK.deriveFont(30f));
        JLabel messageContentLabel = new JLabel(messageContent);
        messageContentLabel.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(20f));

        gbc.weightx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, 20, 0);
        messageTextPanel.add(messageTitleLabel, gbc);
        messageTextPanel.add(messageContentLabel, gbc);

        gbc.weightx = 0;
        gbc.gridwidth = GridBagConstraints.NONE;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(30, 20, 0, 20);
        messageDialog.add(messageIconPanel, gbc);
        messageDialog.add(messageTextPanel, gbc);

        // Add a "Close" button
        JPanel messageButtonPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbcButton = new GridBagConstraints();
        gbcButton.insets = new Insets(0, 5, 0, 5);

        if(dialogType.equals("ERROR") || dialogType.equals("HAZARD")) {
            JButton cancelButton = createDialogButton("Cancel");
            cancelButton.addActionListener(e -> {
                messageDialog.dispose();
                confirmation = false;
            });

            String buttonText;
            if(dialogType.equals("ERROR")) buttonText = "OK";
            else buttonText = "Continue";

            JButton oKButton = createDialogButton(buttonText);
            oKButton.addActionListener(e -> {
                messageDialog.dispose();
                confirmation = true;
            });

            messageButtonPanel.add(cancelButton, gbcButton);
            messageButtonPanel.add(oKButton, gbcButton);
        }

        else if(dialogType.equals("SUCCESS")) {
            JButton oKButton = createDialogButton("OK");
            oKButton.addActionListener(e -> {messageDialog.dispose();});

            messageButtonPanel.add(oKButton, gbcButton);
        }

        messageDialog.add(messageButtonPanel, gbc);

        messageDialog.setLocationRelativeTo(this.frame);
        messageDialog.setVisible(true);
        return confirmation;
    }

    private JButton createDialogButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(100, 40));
        button.setBackground(Color.WHITE);
        button.setOpaque(false);
        button.setFocusable(false);

        return button;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
}
