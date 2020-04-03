package softuni.exam.models.dtos.ticket_dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "ticket")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketImportDto {
    @XmlElement(name = "serial-number")
    private String serialNumber;
    @XmlElement
    private BigDecimal price;
    @XmlElement(name = "take-off")
    private String takeoff;
    @XmlElement(name = "from-town")
    private FromTownDto fromTownDto;
    @XmlElement(name = "to-town")
    private ToTownDto toTownDto;
    @XmlElement(name = "passenger")
    private PassengerTicketDto passenger;
    @XmlElement(name = "plane")
    private PlaneTicketDto plane;

    public TicketImportDto() {
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTakeoff() {
        return takeoff;
    }

    public void setTakeoff(String takeoff) {
        this.takeoff = takeoff;
    }

    public FromTownDto getFromTownDto() {
        return fromTownDto;
    }

    public void setFromTownDto(FromTownDto fromTownDto) {
        this.fromTownDto = fromTownDto;
    }

    public ToTownDto getToTownDto() {
        return toTownDto;
    }

    public void setToTownDto(ToTownDto toTownDto) {
        this.toTownDto = toTownDto;
    }

    public PassengerTicketDto getPassenger() {
        return passenger;
    }

    public void setPassenger(PassengerTicketDto passenger) {
        this.passenger = passenger;
    }

    public PlaneTicketDto getPlane() {
        return plane;
    }

    public void setPlane(PlaneTicketDto plane) {
        this.plane = plane;
    }
}
