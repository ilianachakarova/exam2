package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constants.GlobalConstants;
import softuni.exam.models.dtos.PlaneImportDto;
import softuni.exam.models.dtos.PlaneRootImportDto;
import softuni.exam.models.entities.Plane;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.service.PlaneService;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

@Service
public class PlaneServiceImpl implements PlaneService {
    private final PlaneRepository planeRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

@Autowired
    public PlaneServiceImpl(PlaneRepository planeRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.planeRepository = planeRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    this.xmlParser = xmlParser;
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
    public String importPlanes() throws IOException, JAXBException {
    StringBuilder result = new StringBuilder();
        PlaneRootImportDto planeRootImportDto =
                this.xmlParser.parseXml(PlaneRootImportDto.class, GlobalConstants.PLANES_PATH);
        List<PlaneImportDto> planeImportDtos = planeRootImportDto.getPlanes();
        for (PlaneImportDto planeImportDto : planeImportDtos) {
            if(this.validationUtil.isValid(planeImportDto)){
                Plane plane = this.planeRepository.findByRegisterNumber(planeImportDto.getRegisterNumber()).orElse(null);
                if(plane == null){
                    plane = this.modelMapper.map(planeImportDto,Plane.class);


                   this.planeRepository.saveAndFlush(plane);
                    result.append("Successfully imported Plane ").append(plane.getRegisterNumber());
                }else{
                    result.append("Invalid Plane");
                }
            }else {
                result.append("Invalid Plane");
            }
            result.append(System.lineSeparator());
        }
        System.out.println();
        return result.toString();
    }
}
