package com.ll.exam.damda.controller.design.map;

import com.ll.exam.damda.entity.design.map.Course;
import com.ll.exam.damda.entity.design.map.Plan;
import com.ll.exam.damda.entity.search.Spot;
import com.ll.exam.damda.service.design.map.CourseService;
import com.ll.exam.damda.service.design.map.PlanService;
import com.ll.exam.damda.service.design.map.SpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/search/design")
public class PlanController {
    private final PlanService planService;
    private final CourseService courseService;
    private final SpotService spotService;


    @GetMapping("/list")
    @ResponseBody
    public String list() {
        return "list";
    }
    @GetMapping("/new")
    public String createPlan() {
        return "/design/map/new_plan";
    }
    @PostMapping("/new")
    public String createPlan(@RequestParam(value = "title") String title, @RequestParam(value = "size") long size) {
        Plan plan = planService.create(title, size);
        return "redirect:/search/design/modification/%d?order=%d".formatted(plan.getId(), 1);
    }
    @GetMapping("/modification/{planId}")
    public String modifyPlan(Model model, @PathVariable("planId") long planId, @RequestParam(value = "order") long order) {
        Plan plan = planService.getPlan(planId);
        Course course = courseService.getCourse(plan, order);
        model.addAttribute("plan", plan);
        model.addAttribute("course", course);
        return "/design/map/modify_plan";
    }

    @PostMapping("/insertSpot/{courseid}")
    @ResponseBody
    public String insertBusket(@PathVariable long courseid,
                               @RequestParam(value = "name") String name,
                               @RequestParam(value = "address") String address,
                               @RequestParam(value = "x") String x,
                               @RequestParam(value = "y") String y) {
        System.out.println("insertBusket 수행");
//        Course course = courseService.getCourseById(courseId);
        Spot spot = spotService.create(name, address, x, y);

        return "success";

    }
}
