package br.veloce.api.api.services.company;

import br.veloce.api.api.dtos.company.UserBasic;
import br.veloce.api.api.dtos.company.summary.CompanyStoreSummary;
import br.veloce.api.api.exceptions.NotFoundException;
import br.veloce.api.domain.models.company.Company;
import br.veloce.api.domain.models.company.Store;
import br.veloce.api.domain.models.company.UserStore;
import br.veloce.api.domain.repositories.company.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;

    @Transactional(readOnly = true)
    public Store findOrThrow(String id) {
        return storeRepository.findByIdWithCompany(id).orElseThrow(() -> new NotFoundException("Store not found"));
    }

    @Transactional(readOnly = true)
    public Page<CompanyStoreSummary> findByCompanyPaged(Company company, Pageable pageable) {
        Page<Store> storesPage = storeRepository.findByCompany(company, pageable);
        return storesPage.map(CompanyStoreSummary::new);
    }

    @Transactional(readOnly = true)
    public Page<UserBasic> findUsersOfStore(Store store, Pageable pageable) {
        Page<UserStore> usersOfStorePage = storeRepository.findUsersOfStore(store, pageable);
        return usersOfStorePage.map(UserBasic::new);
    }
}
