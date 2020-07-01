package com.cskaoyan.mall.service;

import java.util.List;
import java.util.Map;

public interface SearchService {
    Map<String, Object> index(String username);

    void clearhistory(String username);

    List<String> helper(String keyword);
}
