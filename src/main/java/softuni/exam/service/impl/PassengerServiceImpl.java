package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.exam.constants.GlobalConstants;
import softuni.exam.models.dtos.PassengerImportDto;
import softuni.exam.models.entities.Passenger;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.PassengerService;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidationUtil;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Service
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final TownRepository townRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;
@Autowired
    public PassengerServiceImpl(PassengerRepository passengerRepository, TownRepository townRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.passengerRepository = passengerRepository;
    this.townRepository = townRepository;
    this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    this.gson = gson;
}

    @Override
    public boolean areImported() {
        return this.passengerRepository.count()>0;
    }

    @Override
    public String readPassengersFileContent() throws IOException {
        return this.fileUtil.readFile(GlobalConstants.PASSENGERS_PATH);
    }
    @Transactional
    @Override
    public String importPassengers() throws FileNotFoundException {
    StringBuilder result = new StringBuilder();
        PassengerImportDto[] passengerImportDtos =
                this.gson.fromJson(new FileReader(GlobalConstants.PASSENGERS_PATH),PassengerImportDto[].class);
        for (PassengerImportDto passengerImportDto : passengerImportDtos) {
            if(this.validationUtil.isValid(passengerImportDto)){
                Passenger passenger = this.passengerRepository.findByEmail(passengerImportDto.getEmail()).orElse(null);
                Town town = this.townRepository.findByName(passengerImportDto.getTown()).orElse(null);
                if(passenger == null && town !=null){
                    passenger = this.modelMapper.map(passengerImportDto, Passenger.class);
                    passenger.setTown(town);

                    this.passengerRepository.saveAndFlush(passenger);
                    result.append(String.format("Successfully imported %s - %s", passenger.getLastName(), passenger.getEmail()));
                }else {
                    result.append("Invalid passenger");
                }

            }else {
                result.append("Invalid passenger");
            }

            result.append(System.lineSeparator());
        }
        return result.toString();
    }
    @Transactional
    @Override
    public String getPassengersOrderByTicketsCountDescendingThenByEmail() {
        StringBuilder result = new StringBuilder();
        List<Passenger> passengers = this.passengerRepository.findAllByTicketsCount();
        for (Passenger passenger : passengers) {
            result.append(String.format("Passenger %s %s", passenger.getFirstName(), passenger.getLastName())).append(System.lineSeparator());
            result.append("\tEmail - ").append(passenger.getEmail()).append(System.lineSeparator());
            result.append("\tPhone - ").append(passenger.getPhoneNumber()).append(System.lineSeparator());
            result.append("\tNumber of tickets - ").append(passenger.getTickets().size()).append(System.lineSeparator());
        }
        return result.toString();
    }
}
