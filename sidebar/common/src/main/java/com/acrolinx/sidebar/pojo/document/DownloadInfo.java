/*
 * Copyright (c) 2016-2017 Acrolinx GmbH
 */

package com.acrolinx.sidebar.pojo.document;

public class DownloadInfo
{
    private String url;
    private String filename;

    public DownloadInfo()
    {
        // for GSON
    }

    public String getUrl()
    {
        return url;
    }

    public String getFilename()
    {
        return filename;
    }
}
