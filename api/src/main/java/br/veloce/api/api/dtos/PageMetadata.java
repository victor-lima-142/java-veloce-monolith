package br.veloce.api.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageMetadata<T> {
    @JsonProperty(value = "total_count", required = true)
    private Long totalCount = 0L;

    @JsonProperty(value = "page", required = true)
    private Long page = 0L;

    @JsonProperty(value = "size", required = true)
    private Long size = 0L;

    @JsonProperty(value = "total_pages", required = true)
    private Long totalPages = 0L;

    @JsonProperty(value = "has_next", required = true)
    private Boolean hasNext = false;

    @JsonProperty(value = "has_previous", required = true)
    private Boolean hasPrevious = false;

    public PageMetadata(Page<T> page) {
        this.page = (long) page.getPageable().getPageNumber();
        this.totalPages = (long) page.getTotalPages();
        this.totalCount = page.getTotalElements();
        this.size = (long) page.getSize();
        this.hasPrevious = page.hasPrevious();
        this.hasNext = page.hasNext();
    }

    public PageMetadata(Pageable pageable, int newDataSize, long totalElements) {
        this.page = (long) pageable.getPageNumber();
        this.size = (long) newDataSize;
        this.totalCount = totalElements;
        this.totalPages = (long) Math.ceil((double) totalElements / pageable.getPageSize());
        this.hasPrevious = pageable.getPageNumber() > 0;
        this.hasNext = pageable.getPageNumber() < totalPages - 1;
    }
}