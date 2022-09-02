package app.filatov.homeworkstatusesbot.service.loader;

import app.filatov.homeworkstatusesbot.dto.LoaderResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "loader",
        url = "${loader.url}",
        path = "${loader.path}"
)
public interface LoaderClient {
    @GetMapping("/")
    LoaderResponseDto getLoaderResponse(@RequestHeader String authorization, @RequestParam Long from_date);
}
