package com.example.demo700.DTOFiles;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class NotificationRequest {

  private List<String> destinations = new ArrayList<>();
  private Map<String, String> params = new HashMap<>();

  public NotificationRequest() {

  }

  public NotificationRequest(List<String> destinations, Map<String, String> params) {
         
       this.destinations = destinations;
       this.params = params;

  }

  public List<String> getDistinations() {

       return this.distinations;

  }

  public Map<String, String> getParams() {

       return params;

  }


}