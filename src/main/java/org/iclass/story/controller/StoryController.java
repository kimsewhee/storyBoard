package org.iclass.story.controller;

import lombok.RequiredArgsConstructor;
import org.iclass.story.response.StoryResponse;
import org.iclass.story.response.StoryWithCommentsResponse;
import org.iclass.story.service.PaginationService;
import org.iclass.story.service.StoryService;
import org.iclass.story.type.SearchType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/stories")
@Controller
public class StoryController {
    private final StoryService storyService;
    private final PaginationService paginationService;

    public String stories_(ModelMap map) {
        map.addAttribute("stories", List.of());
        return "stories/index";
    }
    @GetMapping
    public String stories(
            @RequestParam(required = false)SearchType searchType,
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size = 10,sort = "createdAt",direction = Sort.Direction.DESC)Pageable
             pageable,ModelMap map){

        Page<StoryResponse> stories = storyService.searhStories(searchType,searchValue,pageable)
                .map(StoryResponse::from);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(),stories.getTotalPages());
        map.addAttribute("stories",stories);
        map.addAttribute("paginationBarNumbers",barNumbers);
        return "stories/index";
    }

    @GetMapping("/{storyId}")
    public String story(@PathVariable Long storyId, ModelMap map){
     //    map.addAttribute("story", "story");
     //    map.addAttribute("storyComments", List.of());
        StoryWithCommentsResponse story = StoryWithCommentsResponse.from(storyService.getStory(storyId));
        map.addAttribute("story", story);
        map.addAttribute("storyComments",story.storyCommentsResponse());

        return "stories/detail";
    }

}
