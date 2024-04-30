package de.waschndolos.tasks.controller;

import de.waschndolos.tasks.model.Task;
import de.waschndolos.tasks.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/interruptions")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/buttonClicked")
    public String handleButtonAction(@RequestParam("actionId") int actionId, @RequestParam("isHomeOffice") boolean isHomeOffice, RedirectAttributes redirectAttributes) {        try {
            taskService.save(new Task(actionId, isHomeOffice));
            redirectAttributes.addFlashAttribute("success", "Successfully saved.");
            redirectAttributes.addFlashAttribute("isHomeOffice", isHomeOffice);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("fail", "Error while saving datapoint.");
        }
        return "redirect:/";
    }

}
