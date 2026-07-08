package com.skillforge.backend.common.util;

import org.springframework.stereotype.Component;

@Component
public class SlugUtil {
    private SlugUtil(){

    }
    public static String toSlug(String title){
        return title
                .trim()
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s-]","")
                .replaceAll("\\s+","-");
    }
}
