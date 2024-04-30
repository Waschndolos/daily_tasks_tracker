package de.waschndolos.tasks.controller;

import de.waschndolos.tasks.model.Task;
import de.waschndolos.tasks.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/submit")
    public String submitTask(@RequestParam("taskName") String taskName, @RequestParam("isHomeOffice") boolean isHomeOffice, Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("isHomeOffice", isHomeOffice);
            taskService.save(new Task(taskName, isHomeOffice));
            redirectAttributes.addFlashAttribute("success", "Successfully saved.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("fail", "Error while saving datapoint.");
        }
        return "redirect:/";
    }
}
