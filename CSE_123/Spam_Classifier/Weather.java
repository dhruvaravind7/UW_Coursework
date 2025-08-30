import java.util.*;

// This class represents the Weather and contains information about humidity and temperature
public class Weather implements Classifiable {
    public static final Set<String> FEATURES = Set.of("Humidity", "Temperature (C)");
    private static final double GAP = 0.2;
    private double humidity;
    private double temperature;

    // Constructor that creates a new Weather with the provided double inputs.
    public Weather(double humidity, double temperature) {
        this.humidity = humidity;
        this.temperature = temperature;
    }

    // Returns a Set of all the features that can be used.
    public Set<String> getFeatures() {
        return FEATURES;
    }

    // Returns the stored numeric value for the provided feature. 'feature' should be non-null
    // Throws IllegalArgumentException if provided feature is invalid, not contained within 
    // getFeatures()
    public double get(String feature) {
        String[] splitted = feature.split(Classifiable.SPLITTER);

        if (!FEATURES.contains(splitted[0])) {
            throw new IllegalArgumentException("Invalid feature");
        }

        if (splitted[0].equals("Humidity")) {
            return humidity;
        } else if (splitted[0].equals("Temperature (C)")) {
            return temperature;
        }
        return 0.0; 
    }

    // Returns a Split representing the midpoint between this and the other classifiable object 
    // that was inputted. Uses the humidity as the first check and temperature as the secondary
    // check if the humidities are too similar. Throws Illegal Argument Exception if the inputted
    // other isn't an instance of the weather class.
    public Split partition(Classifiable other) {
        if (!(other instanceof Weather)) {
            throw new IllegalArgumentException("Provided 'other' not instance of Weather.java");
        }

        Weather otherWeather = (Weather) other;

        if (Math.abs(otherWeather.get("Humidity") - this.get("Humidity")) > GAP){
            return (new Split("Humidity" + Classifiable.SPLITTER + this.humidity,
                    Split.midpoint(this.humidity, otherWeather.humidity)));
        } else {
            return (new Split("Temperature (C)" + Classifiable.SPLITTER + this.temperature,
                    Split.midpoint(this.temperature, otherWeather.temperature)));
        }
    }

    // Creates and returns a Classifiable object from the provided weather data.
    public static Classifiable toClassifiable(List<String> row) {
        return (new Weather(Double.parseDouble(row.get(5)), Double.parseDouble(row.get(3))));
    }

}
