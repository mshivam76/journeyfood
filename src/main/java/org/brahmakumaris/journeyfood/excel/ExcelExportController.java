package org.brahmakumaris.journeyfood.excel;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class ExcelExportController {
private Logger LOGGER = LoggerFactory.getLogger(ExcelExportController.class);
	
	@Autowired
	private ExcelService excelService;

	@GetMapping("/users/export/excel")
    public ResponseEntity<Resource> exportUsersToExcel() throws IOException {
		LOGGER.info("ExcelExportController exportUsersToExcel method - Enter ");
    	DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
		String filename = "users_" + currentDateTime; 
		InputStreamResource file = new	InputStreamResource(excelService.loadUsers());
		LOGGER.info("ExcelExportController exportUsersToExcel method - Exist- successfully ");
		return ResponseEntity.ok() .header(HttpHeaders.CONTENT_DISPOSITION,
			"attachment; filename=" + filename) 
			.contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
			.body(file);
	}
	
	@GetMapping("/orders/export/excel")
    public ResponseEntity<Resource> exportOrdersToExcel() throws IOException {
		LOGGER.info("ExcelExportController exportOrdersToExcel method - Enter ");
    	DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String filename = "orders_" + currentDateTime;
        InputStreamResource file = new	InputStreamResource(excelService.loadOrders());
		LOGGER.info("ExcelExportController exportOrdersToExcel method - Exist- successfully ");
		return ResponseEntity.ok() .header(HttpHeaders.CONTENT_DISPOSITION,
			"attachment; filename=" + filename) 
			.contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
			.body(file);
    }  
	
	@GetMapping("/orders/export/excel/{date}")
    public ResponseEntity<Resource> exportOrdersForADateToExcel(@PathVariable("date")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
		LOGGER.info("ExcelExportController exportOrdersForADateToExcel method - Enter ");
    	DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String filename = "orders_for_" +date+"_"+ currentDateTime;
        InputStreamResource file = new	InputStreamResource(excelService.loadOrdersForADate(date));
		LOGGER.info("ExcelExportController exportOrdersToExcel method - Exist- successfully ");
		return ResponseEntity.ok() .header(HttpHeaders.CONTENT_DISPOSITION,
			"attachment; filename=" + filename) 
			.contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
			.body(file);
    }  
	
	@GetMapping("/orders/placed/export/excel/{date}")
    public ResponseEntity<Resource> exportPlacedOrdersToExcel(@PathVariable("date")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) throws IOException {
		LOGGER.info("ExcelExportController exportPlacedOrdersToExcel method - Enter ");
    	DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String filename = "placedOrders_for_"+date+"_" + currentDateTime;
        InputStreamResource file = new	InputStreamResource(excelService.loadPlacedOrdersForADate(date));
		LOGGER.info("ExcelExportController exportOrdersToExcel method - Exist- successfully ");
		return ResponseEntity.ok() .header(HttpHeaders.CONTENT_DISPOSITION,
			"attachment; filename=" + filename) 
			.contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
			.body(file);
    }
	
	@GetMapping("/orders/delivered/export/excel/{date}")
    public ResponseEntity<Resource> exportDeliveredOrdersToExcel(@PathVariable("date")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,HttpServletResponse response) throws IOException {
		LOGGER.info("ExcelExportController exportDeliveredOrdersToExcel method - Enter ");
    	DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String filename = "DeliveredOrders_for_" + currentDateTime;
        InputStreamResource file = new	InputStreamResource(excelService.loadDeliveredOrdersForADate(date));
		LOGGER.info("ExcelExportController exportOrdersToExcel method - Exist- successfully ");
		return ResponseEntity.ok() .header(HttpHeaders.CONTENT_DISPOSITION,
			"attachment; filename=" + filename) 
			.contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
			.body(file);
    }
    
	@GetMapping("/orders/canceledOrders/export/excel/{date}")
    public ResponseEntity<Resource> exportCanceledOrdersForADateToExcel(@PathVariable("date")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) throws IOException {
		LOGGER.info("ExcelExportController exportCanceledOrdersForADateToExcel method - Enter ");
    	DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String filename = "CancelledOrders_for_"+date+"_" + currentDateTime;
        InputStreamResource file = new	InputStreamResource(excelService.loadCancelledOrdersForADate(date));
		LOGGER.info("ExcelExportController exportOrdersToExcel method - Exist- successfully ");
		return ResponseEntity.ok() .header(HttpHeaders.CONTENT_DISPOSITION,
			"attachment; filename=" + filename) 
			.contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
			.body(file);
    }
	
	@GetMapping("/orders/totalPlacedOrders/export/excel/{date}")
    public ResponseEntity<Resource> exportTotalOrdersForADateToExcel1(@PathVariable("date")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) throws IOException {
		LOGGER.info("ExcelExportController exportTotalOrdersForADateToExcel method - Enter ");
    	DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String filename = "totalOrdersFor_" + date+"_"+currentDateTime;
        InputStreamResource file = new	InputStreamResource(excelService.loadTotalQuantityOrdersForDate(date));
		LOGGER.info("ExcelExportController exportTotalOrdersForADateToExcel method - Exist- successfully ");
        return ResponseEntity.ok() .header(HttpHeaders.CONTENT_DISPOSITION,
    			"attachment; filename=" + filename) 
    			.contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
    			.body(file);
    }
	
	@GetMapping("/orders/dateRangeOrdersWithOrderStatus/export/excel")
    public ResponseEntity<Resource> exportFromDate2EndDateOrdersWithOrderStatusToExcel(
    		@RequestParam(name = "startDate")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, 
    		@RequestParam(name = "endDate")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
    		@RequestParam(name = "orderStatus") String orderStatus) throws IOException {
		LOGGER.info("ExcelExportController exportFromDate2EndDateOrdersWithOrderStatusToExcel method - Enter ");
    	DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String filename = "AllOrdersFrom_for_" + startDate+"to"+endDate+"_"+orderStatus+currentDateTime;
        InputStreamResource file = new	InputStreamResource(excelService.loadDeliveredOrdersFromDate2EndDateWithOrderStatus(startDate, endDate, orderStatus));
		LOGGER.info("ExcelExportController exportOrdersToExcel method - Exist- successfully ");
		return ResponseEntity.ok() .header(HttpHeaders.CONTENT_DISPOSITION,
			"attachment; filename=" + filename) 
			.contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
			.body(file);
    }
	
	@GetMapping("/orders/dateRangeOrders/export/excel")
    public ResponseEntity<Resource> exportFromDate2EndDateOrdersToExcel(
    		@RequestParam(name = "startDate")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, 
    		@RequestParam(name = "endDate")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) throws IOException {
		LOGGER.info("ExcelExportController exportFromDate2EndDateOrdersToExcel method - Enter ");
    	DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String filename = "AllOrdersFrom_for_" + startDate+"to"+endDate+"_"+currentDateTime;
        InputStreamResource file = new	InputStreamResource(excelService.loadDeliveredOrdersFromDate2EndDate(startDate, endDate));
		LOGGER.info("ExcelExportController exportOrdersToExcel method - Exist- successfully ");
		return ResponseEntity.ok() .header(HttpHeaders.CONTENT_DISPOSITION,
			"attachment; filename=" + filename) 
			.contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
			.body(file);
    }
}
