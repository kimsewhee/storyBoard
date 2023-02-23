package org.iclass.story.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/stories")
@Controller
public class StoryController {

    @GetMapping
    public String stories(ModelMap map) {
        map.addAttribute("stories", List.of());
        return "stories/index";
    }

    @GetMapping("/{storyId}")
    public String story(@PathVariable Long storyId, ModelMap map){
     map.addAttribute("story", "story");
     map.addAttribute("storyComments", List.of());
        return "stories/detail";
    }

}
