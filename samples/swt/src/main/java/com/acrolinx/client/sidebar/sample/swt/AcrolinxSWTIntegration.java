package com.acrolinx.client.sidebar.sample.swt;

import com.acrolinx.sidebar.AcrolinxIntegration;
import com.acrolinx.sidebar.InputAdapterInterface;
import com.acrolinx.sidebar.pojo.SidebarError;
import com.acrolinx.sidebar.pojo.document.CheckResult;
import com.acrolinx.sidebar.pojo.settings.AcrolinxSidebarInitParameter;
import com.acrolinx.sidebar.utils.SidebarUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType") public class AcrolinxSWTIntegration
        implements AcrolinxIntegration
{
    final private AcrolinxSidebarInitParameter initParameters;
    final private InputAdapterInterface inputAdapterInterface;

    private final Logger logger = LoggerFactory.getLogger(AcrolinxSWTIntegration.class);

    @SuppressWarnings("WeakerAccess")
    public AcrolinxSWTIntegration(AcrolinxSidebarInitParameter initParameters,
            InputAdapterInterface inputAdapterInterface)
    {
        this.initParameters = initParameters;
        this.inputAdapterInterface = inputAdapterInterface;
    }

    @Override
    public InputAdapterInterface getEditorAdapter()
    {
        return this.inputAdapterInterface;
    }

    @Override
    public AcrolinxSidebarInitParameter getInitParameters()
    {
        return this.initParameters;
    }

    @Override
    public void onCheckResult(CheckResult checkResult)
    {
        logger.debug(checkResult.getCheckedDocumentPart().getCheckId());

    }

    @Override
    public void openWindow(String url)
    {
        SidebarUtils.openWebPageInDefaultBrowser(url);
    }

    @Override
    public void onInitFinished(Optional<SidebarError> initResult)
    {
        logger.debug(initResult.toString());

    }
}
