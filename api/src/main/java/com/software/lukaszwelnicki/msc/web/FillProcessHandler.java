package com.software.lukaszwelnicki.msc.web;

import com.software.lukaszwelnicki.msc.service.DatabaseFillProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class FillProcessHandler {

    private final DatabaseFillProcessService fillProcessService;

    Mono<ServerResponse> startFillingDatabase(ServerRequest serverRequest) {
        return Optional.ofNullable(fillProcessService.startDatabaseFillProcess())
                .filter(d -> !d.isDisposed())
                .map(d -> ok().build())
                .orElse(badRequest().build());
    }

    Mono<ServerResponse> stopFillingDatabase(ServerRequest serverRequest) {
        fillProcessService.killDatabaseFillProcess();
        return ok().build();
    }

}