package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.exam.constants.GlobalConstants;
import softuni.exam.models.dtos.ticket_dtos.TicketImportDto;
import softuni.exam.models.dtos.ticket_dtos.TicketRootImportDto;
import softuni.exam.models.entities.Passenger;
import softuni.exam.models.entities.Plane;
import softuni.exam.models.entities.Ticket;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.repository.TicketRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TicketService;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final PlaneRepository planeRepository;
    private final PassengerRepository passengerRepository;
    private final TownRepository townRepository;
@Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser, PlaneRepository planeRepository, PassengerRepository passengerRepository, TownRepository townRepository) {
        this.ticketRepository = ticketRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;

    this.xmlParser = xmlParser;
    this.planeRepository = planeRepository;
    this.passengerRepository = passengerRepository;
    this.townRepository = townRepository;
}

    @Override
    public boolean areImported() {
        return this.ticketRepository.count()>0;
    }

    @Override
    public String readTicketsFileContent() throws IOException {
        return this.fileUtil.readFile(GlobalConstants.TICKETS_PATH);
    }
    @Transactional
    @Override
    public String importTickets() throws JAXBException, FileNotFoundException {
    StringBuilder result = new StringBuilder();
        TicketRootImportDto ticketRootImportDto = this.xmlParser.parseXml(TicketRootImportDto.class, GlobalConstants.TICKETS_PATH);
        List<TicketImportDto>ticketImportDtos = ticketRootImportDto.getTickets();

        for (TicketImportDto ticketImportDto : ticketImportDtos) {
            if(this.validationUtil.isValid(ticketImportDto)){
                Ticket ticket = this.ticketRepository.findBySerialNumber(ticketImportDto.getSerialNumber()).orElse(null);
                Plane plane = this.planeRepository.findByRegisterNumber(ticketImportDto.getPlane().getRegisterNumber()).orElse(null);
                Town fromTown =  this.townRepository.findByName(ticketImportDto.getFromTownDto().getName()).orElse(null);
                Town toTown = this.townRepository.findByName(ticketImportDto.getToTownDto().getName()).orElse(null);
                Passenger passenger = this.passengerRepository.findByEmail(ticketImportDto.getPassenger().getEmail()).orElse(null);

                if(ticket == null && plane !=null && fromTown != null && toTown!= null && passenger != null){
                    ticket = this.modelMapper.map(ticketImportDto,Ticket.class);
                    ticket.setTakeoff(LocalDateTime.parse(ticketImportDto.getTakeoff(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    ticket.setFromTown(fromTown);
                    ticket.setPassenger(passenger);
                    ticket.setPlane(plane);
                    ticket.setToTown(toTown);


                    this.ticketRepository.saveAndFlush(ticket);
                    result.append(String.format("Successfully imported Ticket %s - %s",
                            ticket.getFromTown().getName(), ticket.getToTown().getName()));
                }else {
                    result.append("Invalid Ticket");
                }
            }else {
                result.append("Invalid Ticket");
            }
            result.append(System.lineSeparator());
        }
   return result.toString();
    }
}
