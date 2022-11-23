package org.mail.cache.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class PaginationDTO implements Serializable {
    private int page;
    private int perPage;
    private String folder;
}
