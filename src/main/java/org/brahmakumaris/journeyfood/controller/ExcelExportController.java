package org.brahmakumaris.journeyfood.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.brahmakumaris.journeyfood.entity.AggregateJourneyFoodOrder;
import org.brahmakumaris.journeyfood.entity.JourneyFoodOrder;
import org.brahmakumaris.journeyfood.entity.UserEntity;
import org.brahmakumaris.journeyfood.security.UserService;
import org.brahmakumaris.journeyfood.service.JourneyFoodService;
import org.brahmakumaris.journeyfood.utils.ExcelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
	private JourneyFoodService journeyFoodServiceImpl;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ExcelUtils excelUtils;

	@GetMapping("/users/export/excel")
    public void exportUsersToExcel(HttpServletResponse response) throws IOException {
		LOGGER.info("ExcelExportController exportUsersToExcel method - Enter ");
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        List<UserEntity> listUsers = userService.getUsers();
        try {
        	DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());
            String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
            response.setHeader(headerKey, headerValue);
            ExcelUtils excelExporter = new ExcelUtils();
            excelExporter.setUsers(listUsers);
            excelExporter.exportUsers(response);
            LOGGER.info("ExcelExportController exportUsersToExcel method - Exist- successfully ");
			/*
			 * String filename = "tutorials.xlsx"; InputStreamResource file = new
			 * InputStreamResource(fileService.load());
			 * 
			 * return ResponseEntity.ok() .header(HttpHeaders.CONTENT_DISPOSITION,
			 * "attachment; filename=" + filename)User 
			 * .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
			 * .body(file);
			 */
        }catch (Exception e) {
        	// TODO: handle exception
        	LOGGER.error("ERROR: ExcelExportController  User exportUsersToExcel method - "+ e.getMessage());
		}
        
	}
	
	@GetMapping("/orders/export/excel/{date}")
    public void exportOrdersToExcel(@PathVariable("date")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,HttpServletResponse response) throws IOException {
		LOGGER.info("ExcelExportController exportOrdersToExcel method - Enter ");
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        List<JourneyFoodOrder> listOrders = journeyFoodServiceImpl.getOrdersByDate(date);
        try {
        	DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());
            String headerValue = "attachment; filename=OrdersFor_" +date+"_"+ currentDateTime + ".xlsx";
            response.setHeader(headerKey, headerValue);
            excelUtils.setOrders(listOrders);
            excelUtils.exportOrders(response);
            LOGGER.info("ExcelExportController exportOrdersToExcel method - Exist- successfully ");
        }catch (Exception e) {
        	LOGGER.error("ERROR: ExcelExportController  Order exportOrdersToExcel method - "+ e.getMessage());
		}
    }  
	
	@GetMapping("/orders/placed/export/excel/{date}")
    public void exportPlacedOrdersToExcel(@PathVariable("date")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,HttpServletResponse response) throws IOException {
		LOGGER.info("ExcelExportController exportPlacedOrdersToExcel method - Enter ");
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        List<JourneyFoodOrder> listOrders = journeyFoodServiceImpl.getOrdersByDate(date, "PLACED");
        try {
        	DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());
            String headerValue = "attachment; filename=PlacedOrdersFor_" + date+"_"+currentDateTime + ".xlsx";
            response.setHeader(headerKey, headerValue);
            excelUtils.setOrders(listOrders);
            excelUtils.exportOrders(response);
            LOGGER.info("ExcelExportController exportPlacedOrdersToExcel method - Exist- successfully ");
        }catch (Exception e) {
        	LOGGER.error("ERROR: ExcelExportController  Order exportPlacedOrdersToExcel method - "+ e.getMessage());
		}
    }
	
	@GetMapping("/orders/delivered/export/excel/{date}")
    public void exportDeliveredOrdersToExcel(@PathVariable("date")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,HttpServletResponse response) throws IOException {
		LOGGER.info("ExcelExportController exportDeliveredOrdersToExcel method - Enter ");
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        List<JourneyFoodOrder> listOrders = journeyFoodServiceImpl.getOrdersByDate(date, "DELIVERED");
        try {
        	DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());
            String headerValue = "attachment; filename=DeliveredOrdersFor_" + date+"_"+currentDateTime + ".xlsx";
            response.setHeader(headerKey, headerValue);
            excelUtils.setOrders(listOrders);
            excelUtils.exportOrders(response);
            LOGGER.info("ExcelExportController exportDeliveredOrdersToExcel method - Exist- successfully ");
        }catch (Exception e) {
        	LOGGER.error("ERROR: ExcelExportController  Order exportDeliveredOrdersToExcel method - "+ e.getMessage());
		}
    }
    
	@GetMapping("/orders/canceledOrders/export/excel/{date}")
    public void exportCanceledOrdersForADateToExcel(@PathVariable("date")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, HttpServletResponse response) throws IOException {
		LOGGER.info("ExcelExportController exportCanceledOrdersForADateToExcel method - Enter ");
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        List<JourneyFoodOrder> listOrders = journeyFoodServiceImpl.getOrdersByDate(date, "CANCELLED");
        try {
        	DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());
            String headerValue = "attachment; filename=CanceledOrdersFor_" + date+"_"+currentDateTime + ".xlsx";
            response.setHeader(headerKey, headerValue);
            excelUtils.setOrders(listOrders);
            excelUtils.exportOrders(response);
            LOGGER.info("ExcelExportController exportCanceledOrdersForADateToExcel method - Exist- successfully ");
        }catch (Exception e) {
        	LOGGER.error("ERROR: ExcelExportController  Order exportCanceledOrdersForADateToExcel method - "+ e.getMessage());
		}
    }
	
	@GetMapping("/orders/totalPlacedOrders/export/excel/{date}")
    public void exportTotalOrdersForADateToExcel(@PathVariable("date")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, HttpServletResponse response) throws IOException {
		LOGGER.info("ExcelExportController exportTotalOrdersForADateToExcel method - Enter ");
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        List<JourneyFoodOrder> listOrders = journeyFoodServiceImpl.getOrdersByDate(date, "PLACED");
        AggregateJourneyFoodOrder totalOrder=journeyFoodServiceImpl.getOrdersByDateAndNotDisabled(date);
        try {
        	DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());
            String headerValue = "attachment; filename=totalOrdersFor_" + date+"_"+currentDateTime + ".xlsx";
            response.setHeader(headerKey, headerValue);
            excelUtils.setOrders(listOrders);
            excelUtils.setTotalQuantity(totalOrder);
            excelUtils.exportTotalOrders(response);
            LOGGER.info("ExcelExportController exportTotalOrdersForADateToExcel method - Exist- successfully ");
        }catch (Exception e) {
        	LOGGER.error("ERROR: ExcelExportController  Order exportTotalOrdersForADateToExcel method - "+ e.getMessage());
		}
    }
	
	@GetMapping("/orders/dateRangeOrdersWithOrderStatus/export/excel")
    public void exportFromDate2EndDateOrdersWithOrderStatusToExcel(
    		@RequestParam(name = "startDate")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, 
    		@RequestParam(name = "endDate")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
    		@RequestParam(name = "orderStatus") String orderStatus,
    		HttpServletResponse response) throws IOException {
		LOGGER.info("ExcelExportController exportFromDate2EndDateOrdersWithOrderStatusToExcel method - Enter ");
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        List<JourneyFoodOrder> listOrders = journeyFoodServiceImpl.getOrdersByDateRangeAndOrderStatus(startDate,endDate,orderStatus);
        try {
        	DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());
            String headerValue = "attachment; filename=OrdersWithStatusFrom_" + startDate+"to"+endDate+"_"+currentDateTime+ ".xlsx";
            response.setHeader(headerKey, headerValue);
            excelUtils.setOrders(listOrders);
            excelUtils.exportOrders(response);
            LOGGER.info("ExcelExportController exportFromDate2EndDateOrdersWithOrderStatusToExcel method - Exist- successfully ");
        }catch (Exception e) {
        	LOGGER.error("ERROR: ExcelExportController  Order exportFromDate2EndDateOrdersWithOrderStatusToExcel method - "+ e.getMessage());
		}
    }
	
	@GetMapping("/orders/dateRangeOrders/export/excel")
    public void exportFromDate2EndDateOrdersToExcel(
    		@RequestParam(name = "startDate")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, 
    		@RequestParam(name = "endDate")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
    		HttpServletResponse response) throws IOException {
		LOGGER.info("ExcelExportController exportFromDate2EndDateOrdersToExcel method - Enter ");
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        List<JourneyFoodOrder> listOrders = journeyFoodServiceImpl.getOrdersByDateRange(startDate,endDate);
        try {
        	DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());
            String headerValue = "attachment; filename=AllOrdersFrom_" + startDate+"to"+endDate+"_"+currentDateTime + ".xlsx";
            response.setHeader(headerKey, headerValue);
            excelUtils.setOrders(listOrders);
            excelUtils.exportOrders(response);
            LOGGER.info("ExcelExportController exportFromDate2EndDateOrdersToExcel method - Exist- successfully ");
        }catch (Exception e) {
        	LOGGER.error("ERROR: ExcelExportController  Order exportFromDate2EndDateOrdersToExcel method - "+ e.getMessage());
		}
    }
}
