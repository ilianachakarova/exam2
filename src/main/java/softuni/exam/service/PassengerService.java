package softuni.exam.service;


import java.io.FileNotFoundException;
import java.io.IOException;

//ToDo - Before start App implement this Service and set areImported to return false
public interface PassengerService {

    boolean areImported();

    String readPassengersFileContent() throws IOException;
	
	String importPassengers() throws FileNotFoundException;

	String getPassengersOrderByTicketsCountDescendingThenByEmail();

}
