package ru.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import java.util.Map;

@Service
public class BookingClient extends BaseClient {
    private static final String API_PREFIX = "/bookings";

    @Autowired
    public BookingClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
            builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                .build()
        );
    }

    public Object create(long userId, BookingDto bookingDto) {
        return post("", userId, bookingDto);
    }

    public Object update(long bookingId, long userId, Boolean isApproved) {
        return patch("/" + bookingId + "?approved=" + isApproved, userId);
    }

    public Object get(long userId, long bookingId) {
        return get("/" + bookingId, userId);
    }

    public Object getAllByCurrentUser(String path, long userId, String state, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of(
            "state", state,
            "from", from,
            "size", size
        );
        return get(path + "?state={state}&from={from}&size={size}", userId, parameters);
    }


}
