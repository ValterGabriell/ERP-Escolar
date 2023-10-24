package io.github.ValterGabriell.FrequenciaAlunos.mapper;

import org.springframework.hateoas.Links;

public class PatternResponse<T> {
    private T skid;
    private Links links;
    public PatternResponse(T skid, Links links) {
        this.skid = skid;
        this.links = links;
    }

    public T getSkid() {
        return skid;
    }

    public Links getLinks() {
        return links;
    }
}
