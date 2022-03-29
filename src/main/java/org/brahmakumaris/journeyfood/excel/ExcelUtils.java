package org.brahmakumaris.journeyfood.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.brahmakumaris.journeyfood.entity.AggregateJourneyFoodOrder;
import org.brahmakumaris.journeyfood.entity.JourneyFoodOrder;
import org.brahmakumaris.journeyfood.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class ExcelUtils {
	static String[] ORDER_HEADERs = { "Order ID", "Order Date","Title", "Departure Date","Meal retrieval Date", "Meal retrieval Time","Head Count", "Thepla count", "Puri count", "Roti count", "Achar count", "Jam count", "Bread count","Other items"};
	static String[] USER_HEADERs = { "User ID", "E-mail","Name of Guide", "Name of Center","Contact no of Guide", "Zone name","Sub-zone", "Pincode", "Registration Date", "Blocked", "Verified", "Roles"};
	
	public static ByteArrayInputStream usersToExcel(List<UserEntity> users) {
	
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet("Users");
			int rowCount = 1;
			CellStyle style = workbook.createCellStyle();
	        XSSFFont font = (XSSFFont) workbook.createFont();
	        font.setFontHeight(14);
	        style.setFont(font);
			  // Header
			Row headerRow = sheet.createRow(0);
			
			for (int col = 0; col < USER_HEADERs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(USER_HEADERs[col]);
			}
			
			for (UserEntity user: users) {
				Row row = sheet.createRow(rowCount++);
	            row.createCell(0).setCellValue(String.valueOf(user.getUserId()));
	            row.createCell(1).setCellValue(user.getEmail());
	            row.createCell(2).setCellValue(user.getNameOfGuide());
	            row.createCell(3).setCellValue(user.getNameOfCenter());
	            row.createCell(4).setCellValue(user.getContactNoOfGuide());
	            row.createCell(5).setCellValue(user.getZone());
	            row.createCell(6).setCellValue(user.getSubZone());
	            row.createCell(7).setCellValue(user.getPincode());
	            row.createCell(8).setCellValue(user.getDateCreated().toString());
	            row.createCell(9).setCellValue(user.isDisabled());
	            row.createCell(10).setCellValue(user.isEnabled());
	            row.createCell(11).setCellValue(user.getRoles().toString());
			}
			
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
		}
	}
	
	public ByteArrayInputStream ordersToExcel(List<JourneyFoodOrder> orders) {
		
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet("Orders");
			int rowCount = 1;
			CellStyle style = workbook.createCellStyle();
	        XSSFFont font = (XSSFFont) workbook.createFont();
	        font.setFontHeight(14);
	        style.setFont(font);
			  // Header
			Row headerRow = sheet.createRow(0);
			
			for (int col = 0; col < ORDER_HEADERs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(ORDER_HEADERs[col]);
			}
			for (JourneyFoodOrder order : orders) {
	            Row row = sheet.createRow(rowCount++);
	            row.createCell(0).setCellValue(String.valueOf(order.getOrderId()));
	            row.createCell(1).setCellValue(order.getOrderStatus());            
	            row.createCell(2).setCellValue(order.getDateOfOrderPlaced().toString());
	            row.createCell(3).setCellValue(order.getDateOfDeparture().toString());
	            row.createCell(4).setCellValue(order.getMealRetrievalDate().toString());
	            row.createCell(5).setCellValue(order.getMealRetrievalTime().toString());
	            row.createCell(6).setCellValue(order.getHeadCount());
	            row.createCell(7).setCellValue(order.getThepla());
	            row.createCell(8).setCellValue(order.getPuri());
	            row.createCell(9).setCellValue(order.getRoti());
	            row.createCell(10).setCellValue(order.getAchar());
	            row.createCell(11).setCellValue(order.getJam());
	            row.createCell(12).setCellValue(order.getBread());
	            row.createCell(13).setCellValue(order.getItems());
	        }
			
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
		}
	}
	
	public ByteArrayInputStream totalOrdersToExcel(List<JourneyFoodOrder> orders, AggregateJourneyFoodOrder totalOrder) {
		
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet("Orders");
			int rowCount = 1;
			CellStyle style = workbook.createCellStyle();
	        XSSFFont font = (XSSFFont) workbook.createFont();
	        font.setFontHeight(14);
	        style.setFont(font);
			  // Header
			Row headerRow = sheet.createRow(0);
	        Row row1 = null;
			for (int col = 0; col < ORDER_HEADERs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(ORDER_HEADERs[col]);
			}
			for (JourneyFoodOrder order : orders) {
	            Row row = sheet.createRow(rowCount++);
	            row.createCell(0).setCellValue(String.valueOf(order.getOrderId()));
	            row.createCell(1).setCellValue(order.getOrderStatus());            
	            row.createCell(2).setCellValue(order.getDateOfOrderPlaced().toString());
	            row.createCell(3).setCellValue(order.getDateOfDeparture().toString());
	            row.createCell(4).setCellValue(order.getMealRetrievalDate().toString());
	            row.createCell(5).setCellValue(order.getMealRetrievalTime().toString());
	            row.createCell(6).setCellValue(order.getHeadCount());
	            row.createCell(7).setCellValue(order.getThepla());
	            row.createCell(8).setCellValue(order.getPuri());
	            row.createCell(9).setCellValue(order.getRoti());
	            row.createCell(10).setCellValue(order.getAchar());
	            row.createCell(11).setCellValue(order.getJam());
	            row.createCell(12).setCellValue(order.getBread());
	            row.createCell(13).setCellValue(order.getItems());
	        }
			row1=sheet.createRow(rowCount++);
			row1.createCell(6).setCellValue(totalOrder.getTotalHeadCount());
            row1.createCell(7).setCellValue(totalOrder.getTotalThepla());
            row1.createCell(8).setCellValue(totalOrder.getTotalPuri());
            row1.createCell(9).setCellValue(totalOrder.getTotalRoti());
            row1.createCell(10).setCellValue(totalOrder.getTotalAchar());
            row1.createCell(11).setCellValue(totalOrder.getTotalJam());
            row1.createCell(12).setCellValue(totalOrder.getTotalBread());
            row1.createCell(13).setCellValue("Total Quantity");
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
		}
	}
}