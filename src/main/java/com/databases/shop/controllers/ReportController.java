package com.databases.shop.controllers;

import com.databases.shop.services.interfaces.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@Validated
@RequestMapping("/reports")
public class ReportController {


    @Autowired
    private ReportService reportService;


    @GetMapping("/orders/generate")
    public void generateReport(@RequestParam(value = "dateStart", required = false) String dateStart, @RequestParam(value = "dateEnd",required = false) String dateEnd) {
        System.out.println(dateStart);
        System.out.println(dateEnd);
        reportService.generateOrdersReport(dateStart,dateEnd);
    }

    @GetMapping(value = "/orders", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseBody
    public byte[] getReport() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(new File("src/main/resources/static/ordersReport.pdf")));
            int read = 0;
            byte[] buff = new byte[1024];
            while ((read = inputStream.read(buff)) != -1) {
                bos.write(buff, 0, read);
            }
            return bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }










}
