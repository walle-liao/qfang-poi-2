package com.qfang.poi.excel.export.document;

import java.io.OutputStream;

import com.qfang.poi.excel.export.ExcelExportUtils;

/**
 * Created by walle on 2017/7/5.
 */
public class AsyncExcelExportDocument extends ExcelExportDocument {

    public AsyncExcelExportDocument(String fileName) {
        super(fileName);
    }

    public boolean doExport(OutputStream os) {
        return ExcelExportUtils.doExport(os, this);
    }

}
