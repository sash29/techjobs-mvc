package org.launchcode.controllers;

import org.launchcode.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    static HashMap<String, String> columnChoices = new HashMap<>();

    public SearchController() {
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");
    }

    @RequestMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", ListController.columnChoices);
        return "search";
    }
    // TODO #1 - Create handler to process search request and display results
    @RequestMapping(value = "results", method = RequestMethod.POST)
    //uses the query parameter passed in as column to determine which values to fetch from JobData
    public String searchJobsByColumnAndValue(Model model, @RequestParam String searchType, @RequestParam String searchTerm) {
        if (searchType.toLowerCase().equals("all")&& searchTerm != ""){
            ArrayList<HashMap<String, String>> jobs = JobData.findByValue(searchTerm);
            model.addAttribute("title", "Jobs with " + columnChoices.get(searchType) + ": " + searchTerm);
            model.addAttribute("jobs", jobs);
            System.out.println("findByValue");
        }
        else if (searchType.toLowerCase().equals("all") && searchTerm == "") {
            ArrayList<HashMap<String, String>> jobs = JobData.findAll();
            model.addAttribute("title", "All Jobs");
            model.addAttribute("jobs", jobs);
            System.out.println("findAll");
        } else {

            ArrayList<HashMap<String, String>> jobs = JobData.findByColumnAndValue(searchType, searchTerm);
            model.addAttribute("title", "Jobs with " + columnChoices.get(searchType) + ": " + searchTerm);
            model.addAttribute("jobs", jobs);
            System.out.println("findByColandVal");
        }
            return "results";
        }


    }

