package com.example.DonationInUniversity.controller.admin;

import com.example.DonationInUniversity.model.*;
import com.example.DonationInUniversity.repository.ProjectDetailTextRepository;
import com.example.DonationInUniversity.service.admin.ProjectServiceAdmin;
import com.example.DonationInUniversity.service.api.TagService;
import com.example.DonationInUniversity.service.admin.UserAdminService;
import com.example.DonationInUniversity.service.api.ProjectService;
import com.example.DonationInUniversity.service.api.ProjectTagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/manager")
public class ProjectManagerController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectManagerController.class);

    @Autowired
    private ProjectServiceAdmin projectServiceAdmin;

    @Autowired
    private ProjectTagService projectTagService;

    @Autowired
    private ProjectDetailTextRepository projectDetailTextRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserAdminService userAdminService;

    @Value("${server.url}")
    private String serverUrl;

    // Project Management Home Page
    @GetMapping("")
    public String projectHomePage(Model model, @RequestParam(name = "page", defaultValue = "1") int pageNo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userAdminService.adminGetUserByUsername(username);
        if (user == null) {
            return "redirect:/admin/login";
        }

        try {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Page<DonationProject> pageDonation = projectServiceAdmin.getAllDonationProjectByManager(userDetails.getUserModel().getUserId(), pageNo);

            model.addAttribute("currentUrl", "DonationProject");
            model.addAttribute("totalPage", pageDonation.getTotalPages());
            model.addAttribute("listProjects", pageDonation);
            model.addAttribute("currentPage", pageNo);
            model.addAttribute("project", new DonationProject());

        } catch (Exception e) {
            // Log the error
            System.err.println("Error fetching projects for manager: " + e.getMessage());
            model.addAttribute("listProjects", Page.empty());  // Return an empty page
            model.addAttribute("totalPage", 0);
            model.addAttribute("currentPage", 1);
        }

        return "ProjectManager/DonationProject";
    }


    // Save or Update Project
    @PostMapping("/saveOrUpdateProject")
    public String addOrUpdateProject(@ModelAttribute("project") DonationProject project) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userAdminService.adminGetUserByUsername(username);
        project.setIsDeleted(1);
        project.setProjectManager(user);

        if (project.getProjectId() == null) {
            projectServiceAdmin.addProject(project);
        } else {
            projectServiceAdmin.updateProject(project);
        }
        return "redirect:/manager";
    }

    // Delete Project
    @PostMapping("/deleteProject/{id}")
    public String deleteProject(@PathVariable int id) {
        projectServiceAdmin.deleteProject(id);
        return "redirect:/manager";
    }

    // Tags Management Page
    // Tags Management Page
    @GetMapping("/TagsManagement")
    public String tagsManagementPage(Model model, @RequestParam(name = "page", defaultValue = "1") int pageNo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userAdminService.adminGetUserByUsername(username);

        if (user == null) {
            return "redirect:/admin/login";
        }

        try {
            // Fetch tags with associated projects for the current manager
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            List<ProjectTagDisplayTable> projectTagDisplayTableList = projectServiceAdmin.getAllProjectTagsByManager(userDetails.getUserModel().getUserId());

            // Fetch all available projects for the dropdown
            List<DonationProject> projects = projectServiceAdmin.getAllProjectsForManager(userDetails.getUserModel().getUserId());

            model.addAttribute("currentUrl", "TagsManagement");
            model.addAttribute("totalPage", projectTagDisplayTableList.size()); // Assuming pagination
            model.addAttribute("projectTagDisplayTableList", projectTagDisplayTableList); // The combined data for display
            model.addAttribute("projects", projects); // Add list of projects for the dropdown
            model.addAttribute("currentPage", pageNo);
            model.addAttribute("tag", new Tag());
            model.addAttribute("serverUrl", serverUrl);

        } catch (Exception e) {
            System.err.println("Error fetching tags: " + e.getMessage());
            model.addAttribute("projectTagDisplayTableList", List.of());  // Return an empty list
            model.addAttribute("totalPage", 0);
            model.addAttribute("currentPage", 1);
            model.addAttribute("serverUrl", serverUrl);
        }

        return "ProjectManager/TagsManagement";
    }


    // Save or Update Tag
    @PostMapping("/saveOrUpdateTag")
    public String addOrUpdateTag(
            @ModelAttribute("tag") Tag tag,
            @RequestParam("projectId") Integer projectId) {

        // Fetch the selected project by ID
        DonationProject project = projectService.getProjectById(projectId);

        if (project == null) {
            // Handle case where project is not found, e.g., log an error or redirect with a message
            return "redirect:/manager/TagsManagement?error=ProjectNotFound";
        }

        // Save the tag and associate it with the project
        tagService.saveTagWithProjectTag(tag, project.getProjectId());

        return "redirect:/manager/TagsManagement";
    }

    // Delete Tag
    @PostMapping("/deleteTag/{id}")
    public String deleteTag(@PathVariable int id) {
        tagService.deleteTag(id);
        return "redirect:/manager/TagsManagement";
    }

    // Auto Create Tags Handler
    @PostMapping("/autoCreateTags")
    public String autoCreateTags(RedirectAttributes redirectAttributes) {
        try {
            // Call service layer to auto-create tags
//            tagService.autoCreateTags();

            // Set a success message (optional)
            redirectAttributes.addFlashAttribute("successMessage", "Tags created successfully!");

        } catch (Exception e) {
            // Handle any exceptions and log the error
            System.err.println("Error during auto-creation of tags: " + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Error creating tags.");
        }

        // Redirect back to the tags management page
        return "redirect:/manager/TagsManagement";
    }

    @GetMapping("/getProjectDetails/{projectId}")
    @ResponseBody
    public StringBuilder getContent(@PathVariable("projectId") Integer projectId) {
        List<ProjectDetailText> result = projectDetailTextRepository.findByProjectId(projectId);
        StringBuilder content = new StringBuilder();
        for (ProjectDetailText p: result) {
            content.append(p.getContent());
        }
        return content;
    }
    @Value("${server.url}")
    private String BASE_URL;

    @PostMapping("/createTags")
    public ResponseEntity<String> createTagsFromModelOpenChat(@RequestBody Map<String, String> requestBody) {
        // The URL of the other controller (ControllerA)

        String url = BASE_URL + "/api/offline_open_chat/create_tags";

        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);  // Set the content type to JSON

        // Optionally, add other headers (e.g., Authorization)
        // headers.set("Authorization", "Bearer " + accessToken);

        // Prepare the body of the request
        Map<String, String> translateRequestBody = new HashMap<>();
        translateRequestBody.put("content", requestBody.get("content"));

        // Wrap the headers and body in an HttpEntity
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(translateRequestBody, headers);

        // Create a new RestTemplate instance (or use a bean)
        RestTemplate restTemplate = new RestTemplate();

        // Make the POST request
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                String.class
        );

        // Check the response status and return it
        if (response.getStatusCode() == HttpStatus.OK) {
            // Success, return the body of the response
            return ResponseEntity.ok(response.getBody());
        } else {
            // Something went wrong, handle the error
            return ResponseEntity.status(response.getStatusCode()).body("Failed to create tags.");
        }
    }

    @PostMapping("/saveProjectTags")
    public ResponseEntity<?> saveProjectTags(@RequestBody Map<String, Object> request) {
        // Log the incoming request
        logger.info("Received request to save project tags with request data: {}", request);

        // Safely retrieve the projectId from the request and handle conversion from String to Integer
        Object projectIdObject = request.get("projectId");
        int projectId;

        try {
            // Check if projectId is a String and convert it to Integer
            if (projectIdObject instanceof String) {
                projectId = Integer.parseInt((String) projectIdObject);
                logger.info("Parsed projectId from String: {}", projectId);
            } else if (projectIdObject instanceof Integer) {
                projectId = (Integer) projectIdObject;
                logger.info("Received projectId as Integer: {}", projectId);
            } else {
                logger.error("Invalid project ID format: {}", projectIdObject);
                return ResponseEntity.badRequest().body("Invalid project ID format");
            }
        } catch (NumberFormatException e) {
            logger.error("Error parsing projectId: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Invalid project ID format");
        }

        // Safely retrieve and cast the tags from the request
        Object tagsObject = request.get("tags");
        if (!(tagsObject instanceof List<?> tagsList)) {
            logger.error("Invalid tags format: {}", tagsObject);
            return ResponseEntity.badRequest().body("Invalid tags format");
        }

        List<String> tags = new ArrayList<>();

        // Convert the list of tags to List<String>, ensuring all elements are strings
        for (Object tag : tagsList) {
            if (tag instanceof String) {
                tags.add((String) tag);
            } else {
                logger.error("Invalid tag format: {}", tag);
                return ResponseEntity.badRequest().body("Invalid tag format");
            }
        }
        logger.info("Parsed tags: {}", tags);

        // Fetch the project using the project ID
        DonationProject project = projectService.getProjectById(projectId);
        if (project == null) {
            logger.error("Project not found for projectId: {}", projectId);
            return ResponseEntity.badRequest().body("Project not found");
        }
        logger.info("Found project: {}", project.getProjectName());

        // Save the tags associated with the project
        List<Tag> tagEntities = new ArrayList<>();
        for (String tagName : tags) {
            Tag tag = tagService.findOrCreateByName(tagName);
            tagEntities.add(tag);
            logger.info("Associated tag '{}' with project '{}'", tag.getTagName(), project.getProjectName());
        }

        projectTagService.saveProjectTags(project, tagEntities); // Save associations
        logger.info("Successfully saved tags for project: {}", project.getProjectName());

        Map<String, String> response = new HashMap<>();
        response.put("message", "Lưu (các) tag thành công!");

        return ResponseEntity.ok(response);
    }
}
