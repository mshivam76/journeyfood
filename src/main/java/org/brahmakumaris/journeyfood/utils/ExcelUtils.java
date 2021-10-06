package org.brahmakumaris.journeyfood.utils;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.brahmakumaris.journeyfood.entity.AggregateJourneyFoodOrder;
import org.brahmakumaris.journeyfood.entity.JourneyFoodOrder;
import org.brahmakumaris.journeyfood.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class ExcelUtils {
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<JourneyFoodOrder> orders;
	private List<UserEntity> users;
	private AggregateJourneyFoodOrder totalQuantity;
	public ExcelUtils() {
		super();
		workbook = new XSSFWorkbook();
	}
	
	public List<JourneyFoodOrder> getOrders() {
		return orders;
	}

	public void setOrders(List<JourneyFoodOrder> orders) {
		this.orders = orders;
	}

	public List<UserEntity> getUsers() {
		return users;
	}

	public void setUsers(List<UserEntity> users) {
		this.users = users;
	}

	public AggregateJourneyFoodOrder getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(AggregateJourneyFoodOrder totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	private void writeUserHeaderLine() {
        sheet = workbook.createSheet("Users");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "User ID", style);      
        createCell(row, 1, "E-mail", style);       
        createCell(row, 2, "Name of Guide", style);  
        createCell(row, 3, "Name of Center", style);  
        createCell(row, 4, "Contact no of Guide", style);  
        createCell(row, 5, "Zone name", style);  
        createCell(row, 6, "Sub-zone", style);  
        createCell(row, 7, "Pincode", style);  
        createCell(row, 8, "User registration Date", style); 
        createCell(row, 9, "Blocked", style);
        createCell(row, 10, "Verified", style);
        createCell(row, 11, "Roles", style);
    }
     
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
     
    private void writeUserDataLines() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (UserEntity user : users) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, String.valueOf(user.getUserId()), style);
            createCell(row, columnCount++, user.getEmail(), style);
            createCell(row, columnCount++, user.getNameOfGuide(), style);
            createCell(row, columnCount++, user.getNameOfCenter(), style);
            createCell(row, columnCount++, user.getContactNoOfGuide(), style);
            createCell(row, columnCount++, user.getZone(), style);
            createCell(row, columnCount++, user.getSubZone(), style);
            createCell(row, columnCount++, user.getPincode(), style);
            createCell(row, columnCount++, user.getDateCreated().toString(), style);
            createCell(row, columnCount++, user.isDisabled(), style);
            createCell(row, columnCount++, user.isEnabled(), style);
            createCell(row, columnCount++, user.getRoles().toString(), style);
        }
    }
     
	private void writeOrderHeaderLine() {
        sheet = workbook.createSheet("Orders");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "Order ID", style);      
//        createCell(row, 1, "Name of Guide", style);       
        createCell(row, 1, "Order status", style);  
        createCell(row, 2, "Order Date", style);  
        createCell(row, 3, "Departure Date", style);  
        createCell(row, 4, "Meal retrieval Date", style);  
        createCell(row, 5, "Meal retrieval Time", style);  
        createCell(row, 6, "Head Count", style);  
        createCell(row, 7, "Thepla count", style); 
        createCell(row, 8, "Puri count", style);
        createCell(row, 9, "Roti count", style);
        createCell(row, 10, "Achar count", style);
        createCell(row, 11, "Jam count", style);
        createCell(row, 12, "Bread count", style);
        createCell(row, 13, "Other items", style);
    }
    
    private void writeOrderDataLines() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (JourneyFoodOrder order : orders) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, String.valueOf(order.getOrderId()), style);
//            createCell(row, columnCount++, order.getUser().getNameOfGuide(), style);
            createCell(row, columnCount++, order.getOrderStatus(), style);            
            createCell(row, columnCount++, order.getDateOfOrderPlaced().toString(), style);
            createCell(row, columnCount++, order.getDateOfDeparture().toString(), style);
            createCell(row, columnCount++, order.getMealRetrievalDate().toString(), style);
            createCell(row, columnCount++, order.getMealRetrievalTime().toString(), style);
            createCell(row, columnCount++, order.getHeadCount(), style);
            createCell(row, columnCount++, order.getThepla(), style);
            createCell(row, columnCount++, order.getPuri(), style);
            createCell(row, columnCount++, order.getRoti(), style);
            createCell(row, columnCount++, order.getAchar(), style);
            createCell(row, columnCount++, order.getJam(), style);
            createCell(row, columnCount++, order.getBread(), style);
            createCell(row, columnCount++, order.getOthers(), style);
        }
    }
    
    private void writeOrderWithTotalDataLines() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        int columnCount = 0;
        Row row1 = null;
        for (JourneyFoodOrder order : orders) {
        	Row row = sheet.createRow(rowCount++);
            createCell(row, columnCount++, String.valueOf(order.getOrderId()), style);
//            createCell(row, columnCount++, order.getUser().getNameOfGuide(), style);
            createCell(row, columnCount++, order.getOrderStatus(), style);
            createCell(row, columnCount++, order.getDateOfOrderPlaced().toString(), style);
            createCell(row, columnCount++, order.getDateOfDeparture().toString(), style);
            createCell(row, columnCount++, order.getMealRetrievalDate().toString(), style);
            createCell(row, columnCount++, order.getMealRetrievalTime().toString(), style);
            createCell(row, columnCount++, order.getHeadCount(), style);
            createCell(row, columnCount++, order.getThepla(), style);
            createCell(row, columnCount++, order.getPuri(), style);
            createCell(row, columnCount++, order.getRoti(), style);
            createCell(row, columnCount++, order.getAchar(), style);
            createCell(row, columnCount++, order.getJam(), style);
            createCell(row, columnCount++, order.getBread(), style);
            createCell(row, columnCount++, order.getOthers(), style);
            columnCount = 0;
        }
        row1=sheet.createRow(rowCount++);
//        font.setBold(true);
//        font.setFontHeight(16);
//        style.setFont(font);
        createCell(row1, 6, String.valueOf(totalQuantity.getTotalHeadCount()), style);
        createCell(row1, 7, String.valueOf(totalQuantity.getTotalThepla()), style);
        createCell(row1, 8, String.valueOf(totalQuantity.getTotalPuri()), style);
        createCell(row1, 9, String.valueOf(totalQuantity.getTotalRoti()), style);
        createCell(row1, 10, String.valueOf(totalQuantity.getTotalAchar()), style);
        createCell(row1, 11, String.valueOf(totalQuantity.getTotalJam()), style);
        createCell(row1, 12, String.valueOf(totalQuantity.getTotalBread()), style);
        createCell(row1, 13, "Total Quantity", style);
    }
     
    public void exportUsers(HttpServletResponse response) throws IOException {
        writeUserHeaderLine();
        writeUserDataLines();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
    
    public void exportOrders(HttpServletResponse response) throws IOException {
        writeOrderHeaderLine();
        writeOrderDataLines();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public void exportTotalOrders(HttpServletResponse response) throws IOException {
        writeOrderHeaderLine();
        writeOrderWithTotalDataLines();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
         
    }
}