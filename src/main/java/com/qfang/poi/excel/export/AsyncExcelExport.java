package com.qfang.poi.excel.export;

import java.io.OutputStream;

/**
 * Created by walle on 2017/7/5.
 */
public class AsyncExcelExport extends ExcelExportDocument {

    public AsyncExcelExport(String fileName) {
        super(fileName);
    }

    public boolean doExport(OutputStream os) {
        return ExcelExportUtils.doExport(os, this);
    }

}
