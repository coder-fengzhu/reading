package com.fengzhu.reading.controller;


import com.fengzhu.reading.CommonResponse;
import com.fengzhu.reading.dto.TagDTO;
import com.fengzhu.reading.dto.TagGroupDTO;
import com.fengzhu.reading.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class TagController {

    @Autowired
    private TagService tagService;

    @PostMapping("/tag")
    public CommonResponse<TagDTO> addNewTag(@RequestBody TagDTO tagDTO) {
        return CommonResponse.newSuccess(tagService.addNewTag(tagDTO));
    }

    @GetMapping("/tag")
    public CommonResponse<Map<TagGroupDTO, List<TagDTO>>> getAllTags() {
        return CommonResponse.newSuccess(tagService.getAllTags());
    }

}
