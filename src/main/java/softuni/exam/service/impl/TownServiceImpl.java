package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constants.GlobalConstants;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;

@Service
public class TownServiceImpl implements TownService {
    private final TownRepository townRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    @Autowired
    public TownServiceImpl(TownRepository townRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.townRepository = townRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
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
    public String importTowns() {
        return null;
    }
}
