package com.example.weatherforecast.models.current;

import com.google.gson.annotations.SerializedName;

public class CurrentAirQuality {

    /* Get "us-epa-index" for air quality */
    @SerializedName("us-epa-index")
    private int usEpaIndex;

    /* Getter and Setter */
    public int getUsEpaIndex() {
        return usEpaIndex;
    }

    public void setUsEpaIndex(int usEpaIndex) {
        this.usEpaIndex = usEpaIndex;
    }
}
