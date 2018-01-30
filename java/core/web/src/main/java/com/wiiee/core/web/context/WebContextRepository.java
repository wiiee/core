package com.wiiee.core.web.context;

import com.wiiee.core.platform.context.BaseContextRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by wang.na on 2017/03/20.
 */
@Repository
public class WebContextRepository extends BaseContextRepository {
    public WebContextRepository() {
        super("WebContextRepository");
    }
}
