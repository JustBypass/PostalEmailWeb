package com.mail.pdf.service;


import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.mail.global.clients.authorities.Role;
import com.mail.global.clients.main.Client;
import com.mail.global.clients.online.ClientDTO;
import com.mail.global.dto.Envelope;
import com.mail.pdf.requests.DatabaseRequests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PdfService {
    @Autowired
    private DatabaseRequests requests;
    private final Logger logger = LoggerFactory.getLogger(PdfService.class);

    public byte[] readEnvelope(Envelope envelope)  {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(byteArrayOutputStream);

            PdfDocument document = new PdfDocument(writer);
            Document doc = new Document(document);

            String html = envelope.getBodyText();

            document.close();

            return byteArrayOutputStream.toByteArray();
        }
        catch (Exception e){
            return null;
        }
    }

    public byte[] adminStatistics(ClientDTO clientDTO)  {
       try {
            var online = requests.online();
            logger.info("Got online {} in {} ",online,this.getClass().getName());
            var all = requests.clients();
            logger.info("Got all {} in {} ",all,this.getClass().getName());

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter("app.pdf");


            PdfDocument document = new PdfDocument(writer);
            Document doc = new Document(document);
           ImageData imageData = ImageDataFactory.create("/home/nikita/postal/pdf/src/main/resources/static/images.jpeg");

           Image backgroundLogo = new Image(imageData);

           float x = (PageSize.A4.getWidth() - backgroundLogo.getImageScaledWidth()) /2;
           float y = (PageSize.A4.getHeight() - backgroundLogo.getImageScaledWidth()) / 2;

           backgroundLogo.setFixedPosition(x,y);
           backgroundLogo.setOpacity(0.1f);

           doc.add(backgroundLogo);
            float[] tableGrid = {
                    285f,
                    285f + 150f
            };
            Table header = new Table(tableGrid);
            header.addCell(new Cell().add("Postal").setBorder(Border.NO_BORDER).setBold());
            header.addCell(new Cell().add(("The most powerful main engine ever")).setBorder(Border.NO_BORDER));
            doc.add(header);
            Border upBorder = new SolidBorder(Color.GRAY,1f/20f);
            Table fullWidth = new Table(new float[]{570f});


            fullWidth.addCell(new Cell().setBorder(upBorder));
            doc.add(fullWidth);
            doc.add(formerTableOfUsers(all));
            document.close();

            return byteArrayOutputStream.toByteArray();
        }
        catch(Exception e){
            logger.warn(e.getMessage());
            return null;
        }
    }
    private Table formerTableOfUsers(List<Client> clientList){
        Table table = new Table(3);
        table.addHeaderCell(new Cell().add("Clients list")
                .setBorder(Border.NO_BORDER)
               // .setFont("Montserrat")
                .setBold());
        AtomicInteger i = new AtomicInteger();
        table.addCell(new Cell().add("Email").setBold());
        table.addCell(new Cell().add("App Password").setBold());
        table.addCell(new Cell().add("Password").setBold());
        clientList.forEach(
               el-> {
                   table.addCell(new Cell().add(el.getEmail()));
                   table.addCell(new Cell().add(el.getAppPassword()));
                   table.addCell(new Cell().add(el.getPassword()));
               }
        );
        return table;
    }
}
