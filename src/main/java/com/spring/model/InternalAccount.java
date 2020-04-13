package com.spring.model;

import lombok.Data;

import java.util.List;

@Data
public class InternalAccount {
    String accountName;

    String sourceId;

    String type;

    List<String> children;

    String productAssociation;

    String externalAccountAssociation;

    Boolean hasParents;

}
