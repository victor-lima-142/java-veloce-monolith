package br.veloce.api.api.controllers;

import br.veloce.api.api.dtos.company.details.CompanyDetail;
import br.veloce.api.api.services.company.CompanyService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping("/{company_id}")
    private ResponseEntity<CompanyDetail> getCompany(
            @Valid @NotNull @NotBlank @PathVariable(name = "company_id") String companyId,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        CompanyDetail response = companyService.findDetails(companyId, pageable);
        return ResponseEntity.ok(response);
    }
}
