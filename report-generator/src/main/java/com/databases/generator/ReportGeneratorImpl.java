package com.databases.generator;

import com.databases.model.OrderGroupReportValues;
import com.databases.model.OrderReportValues;
import com.databases.model.ProductReportValues;
import com.databases.utils.Utils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.stream.Stream;

@Service
public class ReportGeneratorImpl implements ReportGenerator {

    private final float SMALL_ROW_GAP = 5f;
    private final float BIG_ROW_GAP = 40f;
    private final float MIDDLE_ROW_GAP = 20f;

    private Utils utils;

    @Autowired
    public ReportGeneratorImpl(Utils utils) {
        this.utils = utils;
    }

    @Override
    public void generateOrdersReport(Date dateStart,
                                     Date dateEnd,
                                     String filePath,
                                     double fullIncome,
                                     int ordersQuantity,
                                     double averageOrderCost,
                                     Iterable<OrderGroupReportValues> orderGroupReportValues,
                                     Iterable<OrderReportValues> orderReportValues) {
        try {

            String dateStartString = utils.formatDate(dateStart);
            String dateEndString = utils.formatDate(dateEnd);

            Document document = new Document(PageSize.A4.rotate());
            PdfWriter writer = PdfWriter.getInstance(document, Files.newOutputStream(Paths.get(filePath)));

            PdfHeader event = new PdfHeader();
            writer.setPageEvent(event);

            document.open();

            BaseFont baseFont = BaseFont.createFont("report-generator/src/main/resources/static/SFNSRounded.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

            Font headerFont = new Font(baseFont, 12);
            Font textFont = new Font(baseFont, 10);

            Paragraph p = new Paragraph("Звіт по замовленнях за період з " + dateStartString + " до " + dateEndString + "\n", headerFont);
            p.setAlignment(Element.ALIGN_CENTER);
            p.setSpacingAfter(SMALL_ROW_GAP);

            String currentDate = utils.formatDate(utils.getCurrentDate());
            Paragraph p1 = new Paragraph("Звіт складений " + currentDate, headerFont);
            p1.setAlignment(Element.ALIGN_LEFT);
            p1.setSpacingAfter(MIDDLE_ROW_GAP);

            Paragraph p2 = new Paragraph("Загальна сума прибутку: " + fullIncome + " грн", headerFont);
            p2.setAlignment(Element.ALIGN_LEFT);
            p2.setSpacingAfter(SMALL_ROW_GAP);

            Paragraph p3 = new Paragraph("Загальна кількість замовлень: " + ordersQuantity, headerFont);
            p3.setAlignment(Element.ALIGN_LEFT);
            p3.setSpacingAfter(SMALL_ROW_GAP);

            Paragraph p4 = new Paragraph("Середня вартість 1 замовлення: " + averageOrderCost + " грн", headerFont);
            p4.setAlignment(Element.ALIGN_LEFT);
            p4.setSpacingAfter(BIG_ROW_GAP);

            document.add(p);
            document.add(p1);
            document.add(p2);
            document.add(p3);
            document.add(p4);

            Paragraph tableHeader = new Paragraph("Інформація про замовлення за статусом", headerFont);
            tableHeader.setAlignment(Element.ALIGN_CENTER);
            tableHeader.setSpacingAfter(SMALL_ROW_GAP);
            document.add(tableHeader);

            PdfPTable table = new PdfPTable(4);
            addTableHeader(Stream.of("Статус", "Кількість замовлень", "Середня вартість 1 замовлення (грн)", "Вартість усіх замовлень (грн)"), table, headerFont);
            addRows(table, orderGroupReportValues, textFont);

            table.setWidthPercentage(100);
            table.setSpacingAfter(SMALL_ROW_GAP);

            Paragraph tableHeader1 = new Paragraph("Інформація про всі замовлення", headerFont);
            tableHeader1.setAlignment(Element.ALIGN_CENTER);
            tableHeader1.setSpacingAfter(SMALL_ROW_GAP);
            document.add(tableHeader1);

            PdfPTable table1 = new PdfPTable(9);
            addTableHeader(Stream.of("Номер замовлення", "Дата", "Статус", "К-сть найменувань товарів", "Загальна к-сть одиниць", "К-сть категорій товарів", "К-сть постачальників", "Середня ціна на одиницю товару (грн)", "Вартість замовлення (грн)"),
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
    public void generateProductsReport(Date dateStart,
                                       Date dateEnd,
                                       String filePath,
                                       int soldProductsQuantity,
                                       Iterable<ProductReportValues> productReportValues) {
        try {
            String dateStartString = utils.formatDate(dateStart);
            String dateEndString = utils.formatDate(dateEnd);

            Document document = new Document(PageSize.A4.rotate());
            PdfWriter writer = PdfWriter.getInstance(document, Files.newOutputStream(Paths.get(filePath)));

            PdfHeader event = new PdfHeader();
            writer.setPageEvent(event);

            document.open();

            BaseFont baseFont = BaseFont.createFont("report-generator/src/main/resources/static/SFNSRounded.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            Font headerFont = new Font(baseFont, 16);
            Font textFont = new Font(baseFont, 14);

            Paragraph p = new Paragraph("Звіт по товарах за період з " + dateStartString + " до " + dateEndString + "\n", headerFont);
            p.setAlignment(Element.ALIGN_CENTER);
            p.setSpacingAfter(SMALL_ROW_GAP);

            String currentDate = utils.formatDate(utils.getCurrentDate());
            Paragraph p1 = new Paragraph("Звіт складений " + currentDate, headerFont);
            p1.setAlignment(Element.ALIGN_LEFT);
            p1.setSpacingAfter(MIDDLE_ROW_GAP);

            Paragraph p2 = new Paragraph("Загальна кількість проданих одиниць товарів: " + soldProductsQuantity, headerFont);
            p2.setAlignment(Element.ALIGN_LEFT);
            p2.setSpacingAfter(BIG_ROW_GAP);

            document.add(p);
            document.add(p1);
            document.add(p2);

            Paragraph tableHeader = new Paragraph("Інформація про всі товари", headerFont);
            tableHeader.setAlignment(Element.ALIGN_CENTER);
            tableHeader.setSpacingAfter(SMALL_ROW_GAP);
            document.add(tableHeader);

            PdfPTable table = new PdfPTable(6);
            addTableHeader(Stream.of("Артикул", "Назва", "Продана к-ть", "Виручка (грн)", "Середня к-ть в замовленні", "К-ть покупців"),
                    table, headerFont);
            addRowsP(table, productReportValues, textFont);

            table.setWidthPercentage(100);

            document.add(table);
            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
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
        for (OrderGroupReportValues reportValues : orderGroupReportValues) {
            table.addCell(new Phrase(reportValues.getStatus() + "", textFont));
            table.addCell(new Phrase(reportValues.getOrderNum() + "", textFont));
            table.addCell(new Phrase(reportValues.getAvgOrderCost() + "", textFont));
            table.addCell(new Phrase(reportValues.getOrderGroupCost() + "", textFont));
        }
    }

    private void addRows1(PdfPTable table, Iterable<OrderReportValues> orderReportValues, Font font) {
        for (OrderReportValues reportValues : orderReportValues) {
            table.addCell(new Phrase(reportValues.getOrderId() + "", font));
            table.addCell(new Phrase(utils.formatDate(reportValues.getDateDone()), font));
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
        for (ProductReportValues prRV : productReportValues) {
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
                        new Phrase("" + writer.getCurrentPageNumber()),
                        pageSize.getRight(30), pageSize.getBottom(15), 0);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }



}
