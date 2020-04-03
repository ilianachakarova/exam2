package softuni.exam.models.dtos.ticket_dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "passenger")
@XmlAccessorType(XmlAccessType.FIELD)
public class PassengerTicketDto {
    @XmlElement(name = "email")
    private String email;

    public PassengerTicketDto() {
    }
    @NotNull
    @Pattern(regexp = ".+@.+\\.+.+")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
