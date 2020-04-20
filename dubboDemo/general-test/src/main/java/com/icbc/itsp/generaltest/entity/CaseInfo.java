package com.icbc.itsp.generaltest.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 *
 * @author kfzx-ganhy
 * @since 2020-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CaseInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String caseName;

    private String caseDesc;

    private String interfaceIds;

    private String inputJson;

    private String assertIds;

    private String belongMan;

    private LocalDateTime updateOn;


}
