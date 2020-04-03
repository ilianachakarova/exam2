package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import softuni.exam.constants.GlobalConstants;
import softuni.exam.repository.TicketRepository;
import softuni.exam.service.TicketService;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;

public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
@Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.ticketRepository = ticketRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.ticketRepository.count()>0;
    }

    @Override
    public String readTicketsFileContent() throws IOException {
        return this.fileUtil.readFile(GlobalConstants.TICKETS_PATH);
    }

    @Override
    public String importTickets() {
        return null;
    }
}
