package com.acrolinx.sidebar;

import com.acrolinx.sidebar.pojo.document.AcrolinxMatch;
import com.acrolinx.sidebar.pojo.document.AcrolinxMatchWithReplacement;
import com.acrolinx.sidebar.pojo.settings.InputFormat;

import java.util.List;

/**
 * This interface serves to interact with the current editor.
 */
public interface InputAdapterInterface
{

    /**
     * Receives the current input format.
     *
     * @return Returns the current input format.
     * @see InputFormat
     */
    InputFormat getInputFormat();

    /**
     * Receives the current text from the editor.
     * @return Returns the text to be checked.
     */
    String getContent();

    /**
     * Is called by the Acrolinx Integration to highlight current issues in the text editor.
     * @param checkId The current check id.
     * @param matches The ranges to be highlighted.
     */
    void selectRanges(String checkId, List<AcrolinxMatch> matches);

    /**
     * Is called by the Acrolinx Integration to replace found issues with suggestions from the Acrolinx Sidebar.
     * If the Acrolinx Sidebar is configured as read only this method wont be called.
     * @param checkId The current check id.
     * @param matchesWithReplacement The ranges to be replaced.
     */
    void replaceRanges(String checkId, List<AcrolinxMatchWithReplacement> matchesWithReplacement);

    //registerCheckCall(checkInfo: Check): void;
    //registerCheckResult(checkResult: CheckResult): void;
}
