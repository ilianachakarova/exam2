package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constants.GlobalConstants;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.service.PassengerService;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;

@Service
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
@Autowired
    public PassengerServiceImpl(PassengerRepository passengerRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.passengerRepository = passengerRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.passengerRepository.count()>0;
    }

    @Override
    public String readPassengersFileContent() throws IOException {
        return this.fileUtil.readFile(GlobalConstants.PASSENGERS_PATH);
    }

    @Override
    public String importPassengers() {
        return null;
    }
}
