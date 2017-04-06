/*
 * Copyright (c) 2016-2017 Acrolinx GmbH
 */

package com.acrolinx.sidebar.utils;

import java.awt.*;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
public class SidebarUtils
{
    private static final Logger logger = LoggerFactory.getLogger(SidebarUtils.class);

    /**
     * Opens the given URL in the default Browser of the current OS.
     * Note that this method is likely to cause JVM crashes within swt based applications!
     *
     * @param url
     */
    public static void openWebPageInDefaultBrowser(String url)
    {

        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            new Thread(() -> {
                try {
                    URI uri = new URI(url);
                    Desktop.getDesktop().browse(uri);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }).start();
        } else {
            logger.error("Desktop is not available to get systems default browser.");
        }
    }

    public static String getSidebarUrl(String serverAddress)
    {
        return serverAddress + (serverAddress.endsWith("/") ? "sidebar/v14/index.html" : "/sidebar/v14/index.html");

    }

    public static String getCurrentSDKImplementationVersion()
    {
        return SidebarUtils.class.getPackage().getImplementationVersion();
    }

    public static String getCurrentSDKName()
    {
        return SidebarUtils.class.getPackage().getName();
    }
}
