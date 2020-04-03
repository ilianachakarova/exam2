package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constants.GlobalConstants;
import softuni.exam.models.dtos.TownImportDto;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidationUtil;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@Service
public class TownServiceImpl implements TownService {
    private final TownRepository townRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    @Autowired
    public TownServiceImpl(TownRepository townRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.townRepository = townRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() >0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return this.fileUtil.readFile(GlobalConstants.TOWNS_PATH);
    }

    @Override
    public String importTowns() throws FileNotFoundException {
        StringBuilder result = new StringBuilder();
        TownImportDto[] townImportDtos = this.gson.fromJson(new FileReader(GlobalConstants.TOWNS_PATH), TownImportDto[].class);
        for (TownImportDto townImportDto : townImportDtos) {
            if(this.validationUtil.isValid(townImportDto)){
                Town town = this.townRepository.findByName(townImportDto.getName()).orElse(null);
                if(town == null){
                    town = this.modelMapper.map(townImportDto, Town.class);
                    System.out.println();
                    this.townRepository.saveAndFlush(town);
                    result.append("Successfully imported town "+ town.getName()+ " - " + town.getPopulation());
                }else {
                    result.append("Invalid town");
                }
            }else {
                result.append("Invalid town");
            }
            result.append(System.lineSeparator());
        }
        return result.toString();
    }
}
