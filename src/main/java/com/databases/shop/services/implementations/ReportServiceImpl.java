package com.databases.shop.services.implementations;

import com.databases.shop.repositories.OrderRepository;
import com.databases.shop.repositories.ProductRepository;
import com.databases.shop.repositories.queryinterfaces.OrderGroupReportValues;
import com.databases.shop.repositories.queryinterfaces.OrderReportValues;
import com.databases.shop.repositories.queryinterfaces.ProductReportValues;
import com.databases.shop.services.interfaces.ReportService;
import com.databases.shop.utils.Utils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Stream;

@Service
public class ReportServiceImpl implements ReportService {


    @Autowired
    private Utils utils;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    private final float smallRowGap = 5f;
    private final float bigRowGap = 40f;
    private final float middleRowGap = 20f;

    @Override
    public void generateOrdersReport(String dateStart, String dateEnd) {
        try {
            LocalDate dStart = dateStart == null || dateStart.isEmpty() ? utils.convertToLocalDateViaInstant(orderRepository.getMinDate()) : LocalDate.parse(dateStart);
            LocalDate dEnd = dateEnd == null || dateEnd.isEmpty() ? utils.convertToLocalDateViaInstant(orderRepository.getMaxDate()): LocalDate.parse(dateEnd);

            dateStart = formatDate(utils.convertToDateViaSqlTimestamp(dStart));
            dateEnd = formatDate(utils.convertToDateViaSqlTimestamp(dEnd));

            String filename = "ordersReport.pdf";
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("src/main/resources/static/"+filename));

            PdfHeader event = new PdfHeader();
            writer.setPageEvent(event);

            document.open();

            BaseFont baseFont = BaseFont.createFont("src/main/resources/static/SFNSRounded.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

            Font headerFont = new Font(baseFont, 12);
            Font textFont = new Font(baseFont, 10);

            Paragraph p = new Paragraph("Звіт по замовленнях за період з " + dateStart + " до " + dateEnd + "\n", headerFont);
            p.setAlignment(Element.ALIGN_CENTER);
            p.setSpacingAfter(smallRowGap);

            String currentDate = formatDate(utils.getCurrentDate());
            Paragraph p1 = new Paragraph("Звіт складений "+currentDate, headerFont);
            p1.setAlignment(Element.ALIGN_LEFT);
            p1.setSpacingAfter(middleRowGap);

            double income = orderRepository.getFullIncome(dStart,dEnd);
            Paragraph p2 = new Paragraph("Загальна сума прибутку: "+income +" грн", headerFont);
            p2.setAlignment(Element.ALIGN_LEFT);
            p2.setSpacingAfter(smallRowGap);

            int orderNum = orderRepository.getOrdersNum(dStart,dEnd);
            Paragraph p3 = new Paragraph("Загальна кількість замовлень: "+orderNum, headerFont);
            p3.setAlignment(Element.ALIGN_LEFT);
            p3.setSpacingAfter(smallRowGap);

            int avgOrderCost = orderRepository.getAvgOrderCost(dStart,dEnd);
            Paragraph p4 = new Paragraph("Середня вартість 1 замовлення: "+avgOrderCost+" грн", headerFont);
            p4.setAlignment(Element.ALIGN_LEFT);
            p4.setSpacingAfter(bigRowGap);

            document.add(p);
            document.add(p1);
            document.add(p2);
            document.add(p3);
            document.add(p4);

            Paragraph tableHeader = new Paragraph("Інформація про замовлення за статусом", headerFont);
            tableHeader.setAlignment(Element.ALIGN_CENTER);
            tableHeader.setSpacingAfter(smallRowGap);
            document.add(tableHeader);

            Iterable<OrderGroupReportValues> orderGroupReportValues = orderRepository.getOrderGroupReportValues(dStart,dEnd);
            PdfPTable table = new PdfPTable(4);
            addTableHeader(Stream.of("Статус","Кількість замовлень", "Середня вартість 1 замовлення (грн)", "Вартість усіх замовлень (грн)"),table, headerFont);
            addRows(table, orderGroupReportValues, textFont);

            table.setWidthPercentage(100);
            table.setSpacingAfter(smallRowGap);

            Paragraph tableHeader1 = new Paragraph("Інформація про всі замовлення", headerFont);
            tableHeader1.setAlignment(Element.ALIGN_CENTER);
            tableHeader1.setSpacingAfter(smallRowGap);
            document.add(tableHeader1);

            Iterable<OrderReportValues> orderReportValues = orderRepository.getOrderReportValues(dStart,dEnd);
            PdfPTable table1 = new PdfPTable(9);
            addTableHeader(Stream.of("Номер замовлення","Дата","Статус", "К-сть найменувань товарів", "Загальна к-сть одиниць", "К-сть категорій товарів","К-сть постачальників", "Середня ціна на одиницю товару (грн)", "Вартість замовлення (грн)"),
                    table1, headerFont);
            addRows1(table1, orderReportValues, textFont);

            table1.setWidthPercentage(100);

            document.add(table);
            document.add(table1);

            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void generateProductsReport(String dateStart, String dateEnd) {
        try {
            //month/day/year
            Date d = new Date("11/08/2022");
            System.out.println(d);
            LocalDate startDate = dateStart == null || dateStart.isEmpty() ? utils.convertToLocalDateViaInstant(orderRepository.getMinDate()) : LocalDate.parse(dateStart);
            LocalDate endDate = dateEnd == null || dateEnd.isEmpty() ? utils.convertToLocalDateViaInstant(orderRepository.getMaxDate()): LocalDate.parse(dateEnd);

            dateStart = formatDate(utils.convertToDateViaSqlTimestamp(startDate));
            dateEnd = formatDate(utils.convertToDateViaSqlTimestamp(endDate));

            Document document = new Document(PageSize.A4.rotate());
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("src/main/resources/static/productsReport.pdf"));

            PdfHeader event = new PdfHeader();
            writer.setPageEvent(event);

            document.open();

            BaseFont baseFont = BaseFont.createFont("src/main/resources/static/SFNSRounded.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            Font headerFont = new Font(baseFont, 16); //, Font.BOLD);
            Font textFont = new Font(baseFont, 14);

            Paragraph p = new Paragraph("Звіт по товарах за період з " + dateStart + " до " + dateEnd + "\n", headerFont);
            p.setAlignment(Element.ALIGN_CENTER);
            p.setSpacingAfter(smallRowGap);

            String currentDate = formatDate(utils.getCurrentDate());
            Paragraph p1 = new Paragraph("Звіт складений "+ currentDate, headerFont);
            p1.setAlignment(Element.ALIGN_LEFT);
            p1.setSpacingAfter(middleRowGap);

            int soldProductsQuant = productRepository.getSoldProductsQuant(startDate,endDate);
            Paragraph p2 = new Paragraph("Загальна кількість проданих одиниць товарів: "+ soldProductsQuant, headerFont);
            p2.setAlignment(Element.ALIGN_LEFT);
            p2.setSpacingAfter(bigRowGap);

            document.add(p);
            document.add(p1);
            document.add(p2);

            Paragraph tableHeader = new Paragraph("Інформація про всі товари", headerFont);
            tableHeader.setAlignment(Element.ALIGN_CENTER);
            tableHeader.setSpacingAfter(smallRowGap);
            document.add(tableHeader);

            Iterable<ProductReportValues> productReportValues = productRepository.productReport(startDate, endDate);
            PdfPTable table = new PdfPTable(6);
            addTableHeader(Stream.of("Артикул", "Назва", "Продана к-ть", "Виручка (грн)", "Середня к-ть в замовленні", "К-ть покупців"),
                    table, headerFont);
            addRowsP(table, productReportValues, textFont);

            table.setWidthPercentage(100);

            document.add(table);
            document.close();
        }
        catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private String formatDate(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String dayStr = day < 10 ? "0"+day: day+"";
        int month = calendar.get(Calendar.MONTH)+1;
        String monthStr = month < 10 ? "0"+month: month+"";
        int year = calendar.get(Calendar.YEAR);
        return dayStr+'.'+monthStr+'.'+year;
    }

    private void addTableHeader(Stream<String> headers, PdfPTable table, Font font) {
        headers.forEach(columnTitle -> {
            PdfPCell header = new PdfPCell();
            // header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorderWidth(2);
            font.setStyle(Font.BOLD);
            header.setPhrase(new Phrase(columnTitle, font));
            table.addCell(header);
        });
    }

    private void addRows(PdfPTable table, Iterable<OrderGroupReportValues> orderGroupReportValues, Font textFont) {
        for (OrderGroupReportValues reportValues: orderGroupReportValues) {
            table.addCell(new Phrase(reportValues.getStatus()+"", textFont));
            table.addCell(new Phrase(reportValues.getOrderNum()+"", textFont));
            table.addCell(new Phrase(reportValues.getAvgOrderCost()+"", textFont));
            table.addCell(new Phrase(reportValues.getOrderGroupCost()+"", textFont));
        }
    }

    private void addRows1(PdfPTable table, Iterable<OrderReportValues> orderReportValues, Font font) {
        for (OrderReportValues reportValues: orderReportValues) {
            table.addCell(new Phrase(reportValues.getOrderId() + "", font));
            table.addCell(new Phrase(formatDate(reportValues.getDateDone()), font));
            table.addCell(new Phrase(reportValues.getOrderStatus(), font));
            table.addCell(new Phrase(reportValues.getProductNamesNum() + "", font));
            table.addCell(new Phrase(reportValues.getOverallProductsNum() + "", font));
            table.addCell(new Phrase(reportValues.getCategoriesNum() + "", font));
            table.addCell(new Phrase(reportValues.getProvidersNum() + "", font));
            table.addCell(new Phrase(reportValues.getAvgProductPrice() + "", font));
            table.addCell(new Phrase(reportValues.getCost() + "", font));
        }
    }

    private void addRowsP(PdfPTable table, Iterable<ProductReportValues> productReportValues, Font font) {
        for (ProductReportValues prRV: productReportValues) {
            table.addCell(new Phrase(prRV.getArticul() + "", font));
            table.addCell(new Phrase(prRV.getName(), font));
            table.addCell(new Phrase(prRV.getSoldQuantity() + "", font));
            table.addCell(new Phrase(prRV.getSoldCost() + "", font));
            table.addCell(new Phrase(prRV.getAverageQuantityInOrder() + "", font));
            table.addCell(new Phrase(prRV.getQuantityOfCustomers() + "", font));
        }
    }






    public static class PdfHeader extends PdfPageEventHelper {

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            try {
                Rectangle pageSize = document.getPageSize();
                ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT,
                        new Phrase(""+writer.getCurrentPageNumber()),
                        pageSize.getRight(30), pageSize.getBottom(15),0);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}
