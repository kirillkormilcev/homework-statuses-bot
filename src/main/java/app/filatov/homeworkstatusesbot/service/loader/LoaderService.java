package app.filatov.homeworkstatusesbot.service.loader;

import app.filatov.homeworkstatusesbot.model.Homework;
import app.filatov.homeworkstatusesbot.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoaderService {
    private final LoaderClient loaderClient;
    private final ModelMapper modelMapper;

    public LoaderService(LoaderClient loaderClient, ModelMapper modelMapper) {
        this.loaderClient = loaderClient;
        this.modelMapper = modelMapper;
    }

    public List<Homework> getHomeworks(User user) {
        return loaderClient.getLoaderResponse(String.format("%s %s", user.getApiType(), user.getApiKey()), 0L)
                .getHomeworks()
                .stream()
                .map(homeworkDto -> modelMapper.map(homeworkDto, Homework.class))
                .peek(homework -> homework.setUser(user))
                .peek(homework -> homework.setHash(homework.hashCode()))
                .toList();
    }
}
