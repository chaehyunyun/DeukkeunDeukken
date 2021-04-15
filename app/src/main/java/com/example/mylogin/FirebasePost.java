package com.example.mylogin;

import java.util.HashMap;
import java.util.Map;

public class FirebasePost {
    // firebase가 인식하기 위해선 public으로 설정
    public String height;
    public String weight;
    public String BMI;

    public FirebasePost(String height, String weight, String BMI) {
        this.height = height;
        this.weight = weight;
        this.BMI = BMI;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("height", height);
        result.put("weight", weight);
        result.put("BMI", BMI);
        return result;
    }

}