package br.veloce.api.api.services.company;

import br.veloce.api.api.dtos.company.details.CompanyDetail;
import br.veloce.api.api.dtos.company.summary.CompanyStoreSummary;
import br.veloce.api.api.exceptions.NotFoundException;
import br.veloce.api.domain.models.company.Company;
import br.veloce.api.domain.repositories.company.CompanyRepository;
import br.veloce.api.domain.repositories.company.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final StoreRepository storeRepository;
    private final StoreService storeService;

    @Transactional(readOnly = true)
    public Company findOrThrow(String id) {
        return Optional.of(companyRepository.getReferenceById(id)).orElseThrow(() -> new NotFoundException("Company not found"));
    }

    @Transactional(readOnly = true)
    public CompanyDetail findDetails(String id, Pageable pageable) {
        Company company = findOrThrow(id);
        Page<CompanyStoreSummary> storeSummaryPage = storeService.findByCompanyPaged(company, pageable);
        return new CompanyDetail(company, storeSummaryPage);
    }
}
