package com.acrolinx.client.sidebar.sample.swing;

import com.acrolinx.sidebar.*;
import com.acrolinx.sidebar.document.CheckResult;
import com.acrolinx.sidebar.settings.AcrolinxPluginConfiguration;
import com.acrolinx.sidebar.settings.AcrolinxSidebarInitParemeters;
import com.acrolinx.sidebar.settings.SoftwareComponent;
import com.acrolinx.sidebar.settings.SoftwareComponentCategory;
import com.acrolinx.sidebar.swing.AcrolinxSidebarSwing;
import com.acrolinx.sidebar.swing.adapter.InvocationAdapterSwing;
import com.acrolinx.sidebar.swing.adapter.TextAreaAdapter;
import javafx.embed.swing.JFXPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;

class AcrolinxSwingIntegration implements AcrolinxIntegration
{
    private final Logger logger = LoggerFactory.getLogger(AcrolinxSwingIntegration.class);
    private final JTextArea textArea = new JTextArea();
    private JFrame frame = new JFrame("Acrolinx Demo Swing");

    AcrolinxSwingIntegration()
    {
        //
    }

    void initAcrolinxIntegration()
    {
        this.textArea.setPreferredSize(new Dimension(550, 600));

        JFrame.setDefaultLookAndFeelDecorated(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);

        Container contentPanel = frame.getContentPane();
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new FlowLayout());
        innerPanel.setPreferredSize(new Dimension(870, 600));
        innerPanel.add(this.textArea);

        JFXPanel sidebar = new AcrolinxSidebarSwing(this, 600);
        innerPanel.add(sidebar);

        innerPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        contentPanel.add(innerPanel);

        frame.pack();
        frame.setVisible(true);
    }

    @Override public InputAdapterInterface getEditorAdapter()
    {
        return new TextAreaAdapter(this.textArea);
    }

    @Override public InvocationAdapterInterface getInvocationAdapter()
    {
        return new InvocationAdapterSwing();
    }

    @Override public AcrolinxSidebarInitParemeters getInitParameters()
    {
        logger.debug("Getting InitParams");
        ArrayList<SoftwareComponent> softwareComponents = new ArrayList<>();
        softwareComponents.add(new SoftwareComponent("com.acrolinx.sample", "Acrolinx Demo Client Swing", "1.0.0.1",
                Optional.of(SoftwareComponentCategory.MAIN)));
        return new AcrolinxSidebarInitParemeters.AcrolinxSidebarInitParemetersBuilder(
                "SW50ZWdyYXRpb25EZXZlbG9wbWVudERlbW9Pbmx5", softwareComponents).
                withShowServerSelector(true).build();
    }

    @Override public void onCheckResult(CheckResult checkResult)
    {

    }

    @Override public void openWindow(String url)
    {
        SidebarUtils.openWebpageInDefaultBrowser(url);
    }

    @Override public void configure(AcrolinxPluginConfiguration pluginConfiguration)
    {

    }

    @Override public void onInitFinished(InitResult initResult)
    {
        logger.debug("Finished init!");
    }

}