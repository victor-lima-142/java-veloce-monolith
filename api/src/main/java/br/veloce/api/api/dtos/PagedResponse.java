package br.veloce.api.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagedResponse<T> {
    @Getter
    @JsonProperty(required = true)
    private PageMetadata<T> metadata;

    @Getter
    @JsonProperty(required = true)
    private List<T> data;

    private Pageable pageable;

    public PagedResponse(Page<T> page) {
        this.metadata = new PageMetadata<T>(page);
        this.data = page.stream().filter(Objects::nonNull).toList();
        this.pageable = page.getPageable();
    }

    private PagedResponse(List<T> data, Pageable pageable, PageMetadata<T> metadata) {
        this.metadata = metadata;
        this.data = data.stream().filter(Objects::nonNull).toList();
        this.pageable = pageable;
    }

    public <R> PagedResponse<R> map(java.util.function.Function<T, R> to) {
        List<R> transformedData = this.data.stream()
                .map(to)
                .collect(Collectors.toList());
        PageMetadata<R> transformedMetadata = new PageMetadata<R>(this.pageable, transformedData.size(), this.metadata.getTotalCount());
        return new PagedResponse<R>(transformedData, this.pageable, transformedMetadata);
    }

}
