package com.acrolinx.client.sidebar.sample.swt;

import com.acrolinx.sidebar.pojo.settings.AcrolinxSidebarInitParameter;
import com.acrolinx.sidebar.pojo.settings.InputFormat;
import com.acrolinx.sidebar.pojo.settings.SoftwareComponent;
import com.acrolinx.sidebar.pojo.settings.SoftwareComponentCategory;
import com.acrolinx.sidebar.swt.AcrolinxSidebarSWT;
import com.acrolinx.sidebar.swt.adapter.TextAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import java.util.ArrayList;

public class AcrolinxDemoClientSWT
{

    final Display display = new Display();

    AcrolinxDemoClientSWT()
    {
        Shell shell = new Shell(display, SWT.CLOSE | SWT.TITLE);
        shell.setSize(930, 620);
        shell.setText("Acrolinx Demo Client SWT");

        GridLayout gridLayout = new GridLayout(2, true);
        shell.setLayout(gridLayout);
        Image small = new Image(display, getClass().getClassLoader().getResourceAsStream("iconAcrolinx.png"));
        shell.setImage(small);

        Text textArea = new Text(shell, SWT.MULTI | SWT.BORDER | SWT.WRAP);
        GridData left = new GridData();
        left.widthHint = 600;
        left.heightHint = 600;
        left.grabExcessVerticalSpace = true;

        textArea.setLayoutData(left);

        GridData right = new GridData();
        right.widthHint = 300;
        right.heightHint = 600;
        right.grabExcessVerticalSpace = true;

        ArrayList<SoftwareComponent> softwareComponents = new ArrayList<>();
        softwareComponents.add(new SoftwareComponent("com.acrolinx.sample", "Acrolinx Demo Client SWT", "1.0.0.1",
                SoftwareComponentCategory.MAIN));
        AcrolinxSidebarInitParameter initParemeters = new AcrolinxSidebarInitParameter.AcrolinxSidebarInitParameterBuilder(
                "SW50ZWdyYXRpb25EZXZlbG9wbWVudERlbW9Pbmx5", softwareComponents).withShowServerSelector(true).build();

        TextAdapter textAdapter = new TextAdapter(textArea, InputFormat.TEXT, "samplefilename");

        AcrolinxSWTIntegration client = new AcrolinxSWTIntegration(initParemeters, textAdapter);

        AcrolinxSidebarSWT sidebarSWT = new AcrolinxSidebarSWT(shell, 600, client);
        sidebarSWT.getSidebarBrowser().setLayoutData(right);

        /*
        Floating Sidebar is here:
        ChildShell childShell = new ChildShell(shell, display);
        AcrolinxSidebarSWT sidebarSWT = new AcrolinxSidebarSWT(childShell.getShell(), 600);
        childShell.getShell().open();*/

        shell.layout();
        shell.open();

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        display.dispose();
    }

    public static void main(String[] args)
    {
        new AcrolinxDemoClientSWT();
    }
}

