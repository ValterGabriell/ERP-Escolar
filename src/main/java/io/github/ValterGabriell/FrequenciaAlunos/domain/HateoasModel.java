package io.github.ValterGabriell.FrequenciaAlunos.domain;

import org.springframework.hateoas.Links;

public class HateoasModel {
    private Links links;

    public HateoasModel(Links links) {
       this.links = links;
    }

    public HateoasModel() {
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }
}
