package com.chen17.domain.temporary;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author yd
 * @version 1.0
 * @date 2021-06-02 15:09
 */
@Data
@Component
public class HkMainPageSearch {

    private String orgname;

    private int allnum;

    private int donenum;

    private int errnum;

    private int nornum;

    private String rate;
}
