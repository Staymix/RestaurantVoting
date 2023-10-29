package ru.staymix.restaurantvoting.to;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.staymix.restaurantvoting.util.validation.NoHtml;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class RestaurantTo extends NamedTo {

    @NotBlank
    @Size(min = 10, max = 128)
    @NoHtml
    @Pattern(regexp = "^[a-zA-Z0-9,'\\s]+$", message = "Invalid address format")
    @Schema(example = "123 Example St, City, Country")
    String address;

    public RestaurantTo(Integer id, String name, String address) {
        super(id, name);
        this.address = address;
    }
}
