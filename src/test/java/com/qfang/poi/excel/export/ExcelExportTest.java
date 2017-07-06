package com.qfang.poi.excel.export;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import com.qfang.poi.excel.DataProvider;
import com.qfang.poi.excel.dto.UserAnnotationDto;
import com.qfang.poi.excel.dto.UserDto;
import com.qfang.poi.excel.export.data.ConcurrentPagingDataProvider;
import com.qfang.poi.excel.export.data.DefaultExportDataProvider;
import com.qfang.poi.excel.export.data.PagingDataProvider;
import com.qfang.poi.excel.export.data.PageDataLoader;
import com.qfang.poi.excel.export.document.ExcelExportDocument;
import com.qfang.poi.excel.export.document.LargeExcelExportDocument;
import com.qfang.poi.excel.support.DefaultSheetHeadBuilder;

/**
 * TODO
 *
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class ExcelExportTest {

    /**
     * 简单的导出功能
     *
     * @throws IOException
     */
    @Test
    public void simpleExportTest() throws IOException {
        OutputStream os = null;
        try {
            final String fileName = "simpleExport.xls";
            File file = new File("d:/temp/test/" + fileName);
            os = new BufferedOutputStream(new FileOutputStream(file));

            // 创建 excel 导出文档
            ExcelExportDocument excel = new ExcelExportDocument(fileName);

            // 获取导出数据，并且装配到 DataProvider 接口
            DataProvider<UserDto> datasProvider = new DefaultExportDataProvider<>(() -> getMockDatas());

            // 特殊列值处理
            datasProvider.registerValueHandler("age", value -> ((UserDto) value).getAge() + "1");

            // 创建表头构建器
            DefaultSheetHeadBuilder headBuilder = new DefaultSheetHeadBuilder();
            headBuilder.append("姓名", "name")
                    .append("身份证号", "idCard", 20)
                    .append("年龄", "age");

            // 创建 sheet 页，并且指定该 sheet 页的表头和数据
            excel.createSheet(headBuilder, datasProvider);

            // 执行导出
            excel.doExport(os);
        } finally {
            os.close();
        }
    }

	@Test
	public void simpleExportWithAnnotationTest() throws IOException {
		OutputStream os = null;
		try {
			final String fileName = "simpleExport2.xlsx"; // 导出07版本的 excel
			File file = new File("d:/temp/test/" + fileName);
			os = new BufferedOutputStream(new FileOutputStream(file));

			ExcelExportDocument excel = new ExcelExportDocument(fileName);

			DataProvider<UserAnnotationDto> dataProvider = new DefaultExportDataProvider<>(() -> getMockAnnotationDatas());

			// 该 sheet 页表头通过注解方式生成，具体的注解使用方式参考 UserDto.class
			excel.createSheetWithAnnotation(UserAnnotationDto.class, dataProvider);

			excel.doExport(os);
		} finally {
			os.close();
		}
	}

	@Test
	public void multiSheetExportTest() throws IOException {
		// 一个 excel 中同时导出多个 sheet 页
		OutputStream os = null;
		try {
			final String fileName = "multiSheetExport.xlsx";
			File file = new File("d:/temp/test/" + fileName);
			os = new BufferedOutputStream(new FileOutputStream(file));

			ExcelExportDocument excel = new ExcelExportDocument(fileName);

			// 创建第一个 sheet 页
			DataProvider<UserAnnotationDto> dataProvider = new DefaultExportDataProvider<>(() -> {
				return getMockAnnotationDatas();
			});
			excel.createSheetWithAnnotation(UserAnnotationDto.class, dataProvider);

			// 创建第二个 sheet 页
			DataProvider<UserAnnotationDto> dataProvider2 = new DefaultExportDataProvider<>(() -> {
				List<UserAnnotationDto> mockDatas2 = getMockAnnotationDatas();
				mockDatas2.add(new UserAnnotationDto("test", "361212199901013530", 50));
				return mockDatas2;
			});
			excel.createSheetWithAnnotation(UserAnnotationDto.class, dataProvider2);

			excel.doExport(os);
		} finally {
			os.close();
		}
	}

	@Test
	public void largeExcelExportTest() throws IOException {
		// -Xmx128m -Xms128m
		OutputStream os = null;
		try {
			final String fileName = "largeExcelExport.xlsx";
			Path path = Paths.get("d:/temp/test/", fileName);
			File file = path.toFile();

			os = new BufferedOutputStream(new FileOutputStream(file));

			long startTime = System.currentTimeMillis();
			
			ExcelExportDocument excel = new LargeExcelExportDocument(fileName);
			DataProvider<UserAnnotationDto> dataProvider = new PagingDataProvider<>(new PageDataLoader<UserAnnotationDto>() {
				// 查询导出的总记录数
				@Override
				public int selectTotalCount() {
					return 1000000;
				}

				// 分页加载数据方法，每次加载 pageSize 条数据
				@Override
				public Collection<UserAnnotationDto> loadPageDatas(int pageNum, int pageSize) {
					List<UserAnnotationDto> datas = new ArrayList<UserAnnotationDto>(pageSize);
					for(int i = 0; i < pageSize; i++) {
						UserAnnotationDto u1 = new UserAnnotationDto("zhangsan", "361212199901013530", i);
						datas.add(u1);
					}
					return datas;
				}
			}, 1000);

			// 该 sheet 页表头通过注解方式生成，具体的注解使用方式参考 UserAnnotationDto.class
			excel.createSheetWithAnnotation(UserAnnotationDto.class, dataProvider);

			excel.doExport(os);
			
			long endTime = System.currentTimeMillis();
			System.out.println("elapsed time : " + (endTime - startTime));
			
		} finally {
			os.close();
		}
	}

	@Test
	public void concurrentLargeExcelExportTest() throws InterruptedException {
		// -Xmx128m -Xms128m
		List<Thread> lt = new ArrayList<Thread>();
		// 模拟20个线程同时进行大批量数据导出
		for(int i = 0; i < 5; i++) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
//					try {
//						ExcelExportTest.executeConcurrentLargeExcelExport();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
				}
			});
			t.start();
			lt.add(t);
		}

		for(Thread t : lt) {
			t.join();
		}

		System.out.println("xxxx");
	}


	@Test
	public void executeConcurrentLargeExcelExport() throws IOException {
//		ContextHolder.setCity(CityEnum.SHENZHEN);
//		SessionPerson.set(new Person());

		OutputStream os = null;
		try {
			final String fileName = UUID.randomUUID().toString() + ".xlsx";
			File file = new File("d:/temp/test/" + fileName);
			os = new BufferedOutputStream(new FileOutputStream(file));

			ExcelExportDocument excel = new LargeExcelExportDocument(fileName);
			DataProvider<UserAnnotationDto> dataProvider = new ConcurrentPagingDataProvider<>(new PageDataLoader<UserAnnotationDto>() {
				// 查询导出的总记录数
				@Override
				public int selectTotalCount() {
					return 1000000;
				}

				// 分页加载数据方法，每次加载 pageSize 条数据
				@Override
				public Collection<UserAnnotationDto> loadPageDatas(int pageNum, int pageSize) {
					List<UserAnnotationDto> datas = new ArrayList<UserAnnotationDto>(pageSize);
					for(int i = 0; i < pageSize; i++) {
						UserAnnotationDto u1 = new UserAnnotationDto("zhangsan | " + pageNum, "361212199901013530", i);
						datas.add(u1);
					}

					try {
						Thread.sleep(300);  // 模拟数据加载 30ms
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					return datas;
				}
			}, 1000);

			// 该 sheet 页表头通过注解方式生成，具体的注解使用方式参考 UserAnnotationDto.class
			excel.createSheetWithAnnotation(UserAnnotationDto.class, dataProvider);

			excel.doExport(os);
		} finally {
			os.close();
		}
	}


//	@Test
//	public void largeExcelExportWithCommonModel() throws Exception {
//		// -Xmx128m -Xms128m 使用普通的 excel 导出功能导出 100W条记录，出现：java.lang.OutOfMemoryError
//		OutputStream os = null;
//		try {
//			final String fileName = "simpleExport2.xlsx"; // 导出07版本的 excel
//			File file = new File("d:/temp/test/" + fileName);
//			os = new BufferedOutputStream(new FileOutputStream(file));
//
//			ExcelExportDocument excel = new ExcelExportDocument(fileName);
//
//			DataProvider<UserAnnotationDto> dataProvider = new CommonSheetDataProvider<UserAnnotationDto>(() -> getLargeMockDatas());
//
//			// 该 sheet 页表头通过注解方式生成，具体的注解使用方式参考 UserDto.class
//			excel.createSheetWithAnnotation(UserAnnotationDto.class, dataProvider);
//
//			excel.doExport(os);
//		} finally {
//			os.close();
//		}
//	}
//

	private List<UserAnnotationDto> getMockAnnotationDatas() {
		List<UserAnnotationDto> datas = new ArrayList<UserAnnotationDto>();
		UserAnnotationDto u1 = new UserAnnotationDto("zhangsan", "361212199901013530", 17);
		UserAnnotationDto u2 = new UserAnnotationDto("李四", "361212199901013530", 15);
		datas.add(u1);
		datas.add(u2);
		return datas;
	}

    private List<UserDto> getMockDatas() {
        List<UserDto> datas = new ArrayList<UserDto>();
        UserDto u1 = new UserDto("张三", "361212199901013530", 17);
        UserDto u2 = new UserDto("lisi", "361212199901013530", 15);
        datas.add(u1);
        datas.add(u2);
        return datas;
    }

//	private List<UserAnnotationDto> getLargeMockDatas() {
//		int totalRecord = 1000000;
//		List<UserAnnotationDto> datas = new ArrayList<UserAnnotationDto>(totalRecord);
//		for(int i = 0; i < totalRecord; i++) {
//			UserAnnotationDto u1 = new UserAnnotationDto("zhangsan", "361212199901013530", i);
//			datas.add(u1);
//		}
//		System.out.println("data load over ..");
//		return datas;
//	}

}
