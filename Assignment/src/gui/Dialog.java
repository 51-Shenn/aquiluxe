package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.UUID;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Dialog extends JDialog {

    private JFrame frame;
    private boolean confirmation;

    public Dialog() {
        this.frame = new JFrame();
    }

    public Dialog(JFrame frame) {
        this.frame = frame;
        setBackground(Theme.getBackground());
    }

    public JFrame getFrame() {
        return frame;
    }

    public boolean isConfirmation() {
        return confirmation;
    }

    public void setConfirmation(boolean confirmation) {
        this.confirmation = confirmation;
    }

    public void adminPortalDialog() {
        JDialog adminDialog = new JDialog(this.frame, "Admin Portal", false);
        adminDialog.setLayout(new GridBagLayout());
        adminDialog.setSize(new Dimension(800, 650));
        adminDialog.setResizable(false);
        adminDialog.setBackground(Theme.getBackground());
        adminDialog.getContentPane().setBackground(Theme.getBackground());

        JLabel dialogTitle = new JLabel("Admin Portal");
        dialogTitle.setFont(CustomFonts.INSTRUMENT_SANS_BOLD.deriveFont(50f));
        dialogTitle.setForeground(Theme.getForeground());

        JPanel adminPassPanel = new JPanel(new GridBagLayout());
        adminPassPanel.setBackground(Theme.getBackground());
        
        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setForeground(Theme.getForeground());
        passwordLabel.setFont(CustomFonts.INSTRUMENT_SANS_BOLD.deriveFont(20f));

        JTextField adminPassField = new JTextField();
        adminPassField.setPreferredSize(new Dimension(400, 50));
        adminPassField.setCaretColor(Theme.getForeground());
        adminPassField.setBackground(Theme.getBackground());
        adminPassField.setForeground(Theme.getForeground());
        adminPassField.setBorder(BorderFactory.createCompoundBorder(adminPassField.getBorder(), new EmptyBorder(0, 10, 0, 10)));
        adminPassField.setFont(CustomFonts.INSTRUMENT_SANS_BOLD.deriveFont(20f));

        GridBagConstraints gbcPanel = new GridBagConstraints();
        gbcPanel.weightx = 1;
        gbcPanel.fill = GridBagConstraints.HORIZONTAL;
        gbcPanel.anchor = GridBagConstraints.WEST;
        gbcPanel.gridwidth = GridBagConstraints.REMAINDER;

        adminPassPanel.add(passwordLabel, gbcPanel);
        adminPassPanel.add(adminPassField, gbcPanel);

        JTextField uuidField = new JTextField();
        uuidField.setEditable(false);
        uuidField.setCaretColor(Theme.getForeground());
        uuidField.setBackground(Theme.getBackground());
        uuidField.setForeground(Theme.getForeground());
        uuidField.setFont(CustomFonts.INSTRUMENT_SANS_BOLD.deriveFont(18f));
        uuidField.setBorder(new LineBorder(Theme.getBackground()));

        RoundedButton getPassKeyButton = new RoundedButton(10, Theme.getSuccess());
        getPassKeyButton.setText("Get Pass Key");
        getPassKeyButton.setForeground(Theme.getSuccessForeground());
        getPassKeyButton.setFont(CustomFonts.INSTRUMENT_SANS_BOLD.deriveFont(20f));
        getPassKeyButton.setPreferredSize(new Dimension(150, 50));
        getPassKeyButton.setOpaque(false);
        getPassKeyButton.setBorderPainted(false);
        getPassKeyButton.setFocusable(false);
        getPassKeyButton.addActionListener(e -> {
            UUID uuid = UUID.randomUUID();
            String uuidString = uuid.toString();
            uuidField.setEditable(true);
            uuidField.setText(uuidString);
            uuidField.setEditable(false);
            OverflowMenu.setGeneratedUUID(uuidString);
            adminDialog.revalidate();
            adminDialog.repaint();
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.NONE;
        adminDialog.add(dialogTitle, gbc);

        gbc.weightx = 0;
        gbc.insets = new Insets(80, 0, 0, 0);
        adminDialog.add(adminPassPanel, gbc);
        gbc.insets = new Insets(10, 0, 50, 0);
        adminDialog.add(uuidField, gbc);

        adminDialog.add(getPassKeyButton, gbc);


        adminDialog.setLocationRelativeTo(this.frame);
        adminDialog.setVisible(true);
    }

    public boolean showDialog(String dialogType, String dialogTitle, String messageTitle, String messageContent, boolean closeOperation) {
        JDialog messageDialog = new JDialog(this.frame, dialogTitle, true); // true = modal dialog
        messageDialog.setLayout(new GridBagLayout());
        messageDialog.setSize(new Dimension(600, 450));
        messageDialog.setResizable(false);
        messageDialog.setBackground(Theme.getBackground());
        messageDialog.getContentPane().setBackground(Theme.getBackground());

        // if true make the dialog not closable unless a input is received
        if(closeOperation)
            messageDialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        else
            messageDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel messageIconPanel = new JPanel(new GridBagLayout());
        messageIconPanel.setBackground(Theme.getBackground());
        switch (dialogType) {
            case "SUCCESS" -> messageIconPanel.add(new JLabel(IconLoader.getDialogSuccessIcon()));
            case "ERROR" -> messageIconPanel.add(new JLabel(IconLoader.getDialogErrorIcon()));
            case "HAZARD" -> messageIconPanel.add(new JLabel(IconLoader.getDialogHazardIcon()));
        }

        JPanel messageTextPanel = new JPanel(new GridBagLayout());
        messageTextPanel.setBackground(Theme.getBackground());

        JLabel messageTitleLabel = new JLabel(messageTitle);
        messageTitleLabel.setForeground(Theme.getForeground());
        messageTitleLabel.setFont(CustomFonts.ROBOTO_BLACK.deriveFont(30f));

        JLabel messageContentLabel = new JLabel(messageContent);
        messageContentLabel.setForeground(Theme.getForeground());
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

        JPanel messageButtonPanel = new JPanel(new GridBagLayout());
        messageButtonPanel.setBackground(Theme.getBackground());

        GridBagConstraints gbcButton = new GridBagConstraints();
        gbcButton.insets = new Insets(0, 5, 0, 5);

        if(dialogType.equals("ERROR") || dialogType.equals("HAZARD")) {
            RoundedButton cancelButton = createDialogButton("Cancel");
            cancelButton.addActionListener(e -> {
                messageDialog.dispose();
                confirmation = false;
            });

            String buttonText;
            if(dialogType.equals("ERROR")) buttonText = "OK";
            else buttonText = "Continue";

            RoundedButton oKButton = createDialogButton(buttonText);
            oKButton.addActionListener(e -> {
                messageDialog.dispose();
                confirmation = true;
            });

            messageButtonPanel.add(cancelButton, gbcButton);
            messageButtonPanel.add(oKButton, gbcButton);
        }

        else if(dialogType.equals("SUCCESS")) {
            RoundedButton OkButton = createDialogButton("OK");
            OkButton.addActionListener(e -> {messageDialog.dispose();});

            messageButtonPanel.add(OkButton, gbcButton);
        }

        messageDialog.add(messageButtonPanel, gbc);

        messageDialog.setLocationRelativeTo(this.frame);
        messageDialog.setVisible(true);
        return confirmation;
    }

    private RoundedButton createDialogButton(String text) {
        RoundedButton button = new RoundedButton(5, Theme.getHoverBackground());
        button.setText(text);
        button.setPreferredSize(new Dimension(110, 40));
        button.setForeground(Theme.getForeground());
        button.setFont(CustomFonts.INSTRUMENT_SANS_REGULAR.deriveFont(17f));
        button.setBorderPainted(false);
        button.setFocusable(false);

        return button;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
}
