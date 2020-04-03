package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constants.GlobalConstants;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.service.PlaneService;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;

@Service
public class PlaneServiceImpl implements PlaneService {
    private final PlaneRepository planeRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

@Autowired
    public PlaneServiceImpl(PlaneRepository planeRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.planeRepository = planeRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.planeRepository.count()>0;
    }

    @Override
    public String readPlanesFileContent() throws IOException {
        return this.fileUtil.readFile(GlobalConstants.PLANES_PATH);
    }

    @Override
    public String importPlanes() {
        return null;
    }
}
