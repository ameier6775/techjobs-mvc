package org.launchcode.controllers;

import org.launchcode.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("/search")
public class SearchController {

    @RequestMapping
    public String search(Model model) {
        model.addAttribute("columns", ListController.columnChoices);
        return "search";
    }

    // TODO #1 - Create handler to process search request and display results

    @RequestMapping(value = "/results", params = {"searchType", "searchTerm"})
    public String search(Model model, @RequestParam String searchType, @RequestParam String searchTerm) {

        model.addAttribute("columns", ListController.columnChoices);

        List<HashMap<String, String>> searchResults;

        if (searchType.equalsIgnoreCase("all")) {
            searchResults = JobData.findByValue(searchTerm);
        }
        else {
            searchResults = JobData.findByColumnAndValue(searchType, searchTerm);
        }

        List<SearchResult> results = addSearchResultsToModel(searchResults);

        model.addAttribute("results", results);
        model.addAttribute("size", results.size());

        return "search";
    }

    private List<SearchResult> addSearchResultsToModel(List<HashMap<String, String>> searchResults)
    {
        return searchResults.stream().map(result -> {

            SearchResult searchResultAdd = new SearchResult();

            searchResultAdd.setName(result.get("name"));
            searchResultAdd.setEmployerName(result.get("employer"));
            searchResultAdd.setEmployerPositionType(result.get("position type"));
            searchResultAdd.setEmployerLocation(result.get("location"));
            searchResultAdd.setEmployerCoreCompetency(result.get("core competency"));
            return searchResultAdd;
        }).collect(Collectors.toList());
    }

    private class SearchResult{

        private String name;
        private String employerName;
        private String employerPositionType;
        private String employerLocation;
        private String employerCoreCompetency;

        public String getEmployerName() {
            return employerName;
        }

        public void setEmployerName(String employerName) {
            this.employerName = employerName;
        }

        public String getEmployerPositionType() {
            return employerPositionType;
        }

        public void setEmployerPositionType(String employerPositionType) {
            this.employerPositionType = employerPositionType;
        }

        public String getEmployerLocation() {
            return employerLocation;
        }

        public void setEmployerLocation(String employerLocation) {
            this.employerLocation = employerLocation;
        }

        public String getEmployerCoreCompetency() {
            return employerCoreCompetency;
        }

        public void setEmployerCoreCompetency(String employerCoreCompetency) {
            this.employerCoreCompetency = employerCoreCompetency;
        }
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
