package com.mail.pdf.resource;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.mail.global.dto.Envelope;
import com.mail.global.clients.online.ClientDTO;


import com.mail.pdf.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("pdf")
public class PdfController {
    @Autowired
    private PdfService service;
    @GetMapping("/envelope")
    public ResponseEntity<?> pdfFromEnvelope(@RequestBody Envelope envelope,
                                          @AuthenticationPrincipal ClientDTO clientDTO){
        if(envelope == null){
            throw new NullPointerException("Envelope is null");
        }
        return ResponseEntity.ok().body(service.readEnvelope(envelope));
    }

    @GetMapping("/user/common")
    public byte[] commonUserStat(@AuthenticationPrincipal ClientDTO clientDTO){
        return null;
    }

    @PreAuthorize("hasAuthority('READ_SECRET')")
    @GetMapping("/user/admin")
    public byte[] adminStat(@AuthenticationPrincipal ClientDTO clientDTO) {
        return service.adminStatistics(clientDTO);
    }
}
