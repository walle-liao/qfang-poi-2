package com.qfang.poi.excel;

/**
 * Created by walle on 2017/7/1.
 */
public interface SheetBuildInterceptor {

    default void beforeSheetHeadBuild() {
        // default do nothing...
    }

    default void afterSheetHeadBuild() {
        // default do nothing..
    }

    default void beforeSheetBodyBuild() {
        // default do nothing...
    }

    default void afterSheetBodyBuild() {
        // default do nothing...
    }

}
