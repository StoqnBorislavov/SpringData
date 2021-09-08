package springdatajsonprocesing.exercise.domain.dtos.exportdtoxml;


import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarExportDtoXml {

    @XmlAttribute
    private String make;
    @XmlAttribute
    private String model;
    @XmlAttribute(name = "travelled-distance")
    private Long travelledDistance;

    @XmlElement(name = "parts" )
    private PartExportRootDto parts;

    public CarExportDtoXml() {
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(Long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    public PartExportRootDto getParts() {
        return parts;
    }

    public void setParts(PartExportRootDto parts) {
        this.parts = parts;
    }
}
